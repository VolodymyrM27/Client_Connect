#include <WiFi.h>
#include <WiFiClientSecure.h>
#include <PubSubClient.h>
#include <Wire.h>
#include <Adafruit_PN532.h>
#include <LittleFS.h>
#include <ArduinoJson.h>

// Wi-Fi credentials
char* ssid;
char* password;

// MQTT credentials
char* mqtt_server;
int mqtt_port;
char* mqtt_user;
char* mqtt_password;
char* mqtt_topic;
// Terminal UUID
String uuid;

// NFC PN532 configuration
#define SDA_PIN 21
#define SCL_PIN 22

Adafruit_PN532 nfc(SDA_PIN, SCL_PIN);

WiFiClientSecure espClient;
PubSubClient client(espClient);

void setup() {
  Serial.begin(115200);
  delay(10);

  if (!LittleFS.begin()) {
    Serial.println("An Error has occurred while mounting LittleFS");
    return;
  }

  if (!readConfigFile()) {
    Serial.println("Failed to read configuration");
    // Handle error 
  } 

  // Connect to Wi-Fi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Wi-Fi connected. IP address: ");
  Serial.println(WiFi.localIP());

  // Connect to MQTT broker
  espClient.setInsecure();
  client.setServer(mqtt_server, mqtt_port);
  client.setCallback(callback);
  reconnect();
  // Initialize NFC
  nfc.begin();
  uint32_t versiondata = nfc.getFirmwareVersion();
  if (!versiondata) {
    Serial.print("Didn't find PN53x board");
    while (1);
  }
  nfc.SAMConfig();
  Serial.println("PN53x board found");
}

void loop() {
  if(!client.connected()){
    reconnect();
    }
  client.loop();

  // Check if a card is present
  uint8_t success;
  uint8_t uid[] = { 0, 0, 0, 0, 0, 0, 0 }; // Buffer to store the UID
  uint8_t uidLength; // Length of the UID (4 or 7 bytes depending on ISO14443A card type)

  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);

  if (success) {
    String uidString = byteArrayToHexString(uid, uidLength);
    String message = String(uuid) + ":" + uidString;

    // Publish the message to the MQTT topic
    if (client.publish(mqtt_topic, message.c_str())) {
      Serial.println("Message sent: " + message);
    } else {
      Serial.println("Failed to send message");
    }

    // Wait a bit before scanning for another card
    delay(2000);
  }
}


String byteArrayToHexString(uint8_t* byteArray, uint8_t length) {
  String hexString = "";
  for (uint8_t i = 0; i < length; i++) {
    if (byteArray[i] < 0x10) {
      hexString += "0";
    }
    hexString += String(byteArray[i], HEX);
  }
  return hexString;
}

void reconnect() {
  while (!client.connected()) {
    Serial.println("Attempting MQTT connection...");
    if (client.connect("ESP32Client", mqtt_user, mqtt_password)) {
      Serial.println("Connected to MQTT broker");
    } else {
      Serial.print("Failed to connect to MQTT broker. Retrying in 5 seconds...");
      delay(5000);
    }
  }
}

bool readConfigFile() {
  File file = LittleFS.open("/config.json", "r");
  if (!file) {
    Serial.println("Failed to open config file");
    return false;
  }

  size_t size = file.size();
  std::unique_ptr<char[]> buf(new char[size]);

  file.readBytes(buf.get(), size);
  DynamicJsonDocument doc(1024);
  auto error = deserializeJson(doc, buf.get());
  if (error) {
    Serial.println("Failed to parse config file");
    return false;
  }

  ssid = strdup(doc["ssid"]);
  password = strdup(doc["password"]);
  mqtt_server = strdup(doc["mqtt_server"]);
  mqtt_port = doc["mqtt_port"];
  mqtt_user = strdup(doc["mqtt_user"]);
  mqtt_password = strdup(doc["mqtt_password"]);
  mqtt_topic = strdup(doc["mqtt_topic"]);
  uuid = doc["uuid"].as<String>();

  file.close();
  return true;
}
