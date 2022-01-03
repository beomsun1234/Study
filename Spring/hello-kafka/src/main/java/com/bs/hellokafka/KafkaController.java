package com.bs.hellokafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaProducer kafkaProducer;


    @GetMapping("/message")
    public String sendMessage(@RequestParam String message){
        kafkaProducer.sendTestMessage(message);
        return "sendMessage :" +message;
    }
}
