# "NFC Terminal"  ESP32 NFC Reader with MQTT

This is the part of the project that uses the NFC ESP32 capabilities and connects to the MQTT broker to publish the UIDs of the scanned NFC cards. It uses the PN532 NFC module and connects to the MQTT broker using SSL.

### Requirements

 - An ESP32 board
 - A PN532 NFC module
 - A WiFi network
 - An MQTT broker with SSL capabilities (like HiveMQ Cloud)

### Libraries Used

- WiFi
- WiFiClientSecure
- PubSubClient
 - Wire
- Adafruit_PN532
- LittleFS
- ArduinoJson


### Hardware Connections

Connect the PN532 module to the ESP32 via I2C. By default, the SDA and SCL pins are set to 21 and 22, respectively. You can change these pins in the source code.

### Configuring the Project

Before running the project, you need to set up your WiFi and MQTT broker credentials. These are stored in a file named config.json which must be uploaded to the ESP32's file system (LittleFS) before running the code.

The `config.json` file should have the following structure:

 
```json
{
  "ssid": "your_wifi_ssid",
  "password": "your_wifi_password",
  "mqtt_server": "your_mqtt_broker_address",
  "mqtt_port": your_mqtt_broker_port,
  "mqtt_user": "your_mqtt_username",
  "mqtt_password": "your_mqtt_password",
  "mqtt_topic": "your_mqtt_topic",
  "uuid": "your_uuid"
} 
``` 
Replace all the placeholders with your actual credentials and upload it to the ESP32 using the LittleFS data upload tool in the Arduino IDE.

### Running the Project

Once you've uploaded the config.json file and the Arduino sketch to your ESP32, it will start scanning for NFC cards. When it finds one, it reads the UID, constructs a message in the format uuid:card_uid, and publishes that message to the specified MQTT topic. ( The backend part of the project uses the "nfc/scan" topic ). )

