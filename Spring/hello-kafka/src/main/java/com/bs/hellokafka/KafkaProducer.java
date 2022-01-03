package com.bs.hellokafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    public void sendTestMessage(String message) {
        kafkaTemplate.send("park", message);
        log.info("Kafka Producer Message : "+ message);
    }

}

