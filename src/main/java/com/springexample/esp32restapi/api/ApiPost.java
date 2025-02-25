package com.springexample.esp32restapi.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/notify")
public class ApiPost {

    @PostMapping("/heat")
    public ResponseEntity<String> receiveNotification(@RequestBody Map<String, Object> payload) {// esneklik için veri
     // değişebilcekse ,eğer sabitse normal java sınıfı oluşturulabilir.string json alanı,object karşılık gelen değer
        double temperature = (double) payload.get("temperature");
        System.out.println("Gelen sıcaklık bildirimi: " + temperature);
        return ResponseEntity.ok("Bildirim alındı.");
    }


}
