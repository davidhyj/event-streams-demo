package com.ibm.garage.dev.eventstreamsdemo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventStreamsDemoController {
    private KafkaTemplate<String, String> template;
    private List<String> messages = new CopyOnWriteArrayList<>();

    public EventStreamsDemoController(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @KafkaListener(topics = "${listener.topic}")
    public void listen(ConsumerRecord<String, String> cr) throws Exception {
        if (cr.key().equals("patientInfo")) {
            sendMsg("apptSched", "1");
            messages.add(cr.value());
        }
        else {
            System.out.println(cr.key());
            System.out.println("Skipping");
        }
    }

    public void sendMsg(String key, String msg) throws Exception {
        template.sendDefault(key, msg);
    }

    @GetMapping(value = "send/{key}/{msg}")
    public void send(@PathVariable String key, @PathVariable String msg) throws Exception {
        template.sendDefault(key, msg);
    }

    @GetMapping("received")
    public String recv() throws Exception {
        String result = messages.toString();
        // messages.clear();
        return result;
    }
}