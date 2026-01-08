package com.sk.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.event.PatientEvent;

@Service
public class KafkaConsumer {
    private static final Logger logger =   LoggerFactory.getLogger(KafkaConsumer.class);
    @KafkaListener(topics = "patient",groupId = "analytics-service")
    public void ConsumeEvent(byte[] event){
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            // ... perform any business related to analytics here

            logger.info("Received Patient Event: [PatientId={},PatientName={},PatientEmail={}]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail());

        } catch (InvalidProtocolBufferException e) {
            logger.error("Error deserializing event {}", e.getMessage());
        }
    }
}
