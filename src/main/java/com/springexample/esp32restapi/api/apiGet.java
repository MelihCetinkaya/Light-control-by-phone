package com.springexample.esp32restapi.api;

import com.springexample.esp32restapi.Service.GetService;
import com.springexample.esp32restapi.entity.Message;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/light")
@RequiredArgsConstructor
public class apiGet {

    @GetMapping("/toggle")
    public String toggleLamp() {

        String esp32Url = "http://192.168.52.57:8086/toggle";


        RestTemplate restTemplate = new RestTemplate();

        try {
            String response = restTemplate.getForObject(esp32Url, String.class);  // ESP32'ye istek gönder
            return "Lamba Durumu: " + response;
        } catch (Exception e) {
            return "ESP32'ye ulaşılamadı: " + e.getMessage();
        }
    }

}
