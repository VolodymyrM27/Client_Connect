package com.motrechko.clientconnect.config;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.codec.Utf8;

@Configuration
@Slf4j
public class MqttConfiguration {
    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.port}")
    private short port;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Bean
    public Mqtt5BlockingClient client() {
        log.info("Initializing MQTT client...");
        Mqtt5BlockingClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(brokerUrl)
                .serverPort(port)
                .sslWithDefaultConfig().buildBlocking();
        client.connectWith()
                .simpleAuth()
                .username(username)
                .password(Utf8.encode(password))
                .applySimpleAuth()
                .send();
        log.info("MQTT client successfully connected to the broker at {}", brokerUrl);
        return client;
    }
}
