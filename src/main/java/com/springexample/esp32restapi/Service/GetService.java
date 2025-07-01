package com.springexample.esp32restapi.Service;

import com.springexample.esp32restapi.entity.Message;
import com.springexample.esp32restapi.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetService {

    private final MessageRepo messageRepo;

    public void  receiveNotification(double temperature){
        Message message = new Message();
        message.setContent("Temperature: " + temperature);
        messageRepo.save(message);
    }
}
