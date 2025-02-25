package com.springexample.esp32restapi.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Message {

    @Id
    private Long messageId;

    private String content;
}
