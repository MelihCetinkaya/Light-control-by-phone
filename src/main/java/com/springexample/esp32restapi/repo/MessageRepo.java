package com.springexample.esp32restapi.repo;

import com.springexample.esp32restapi.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Integer> {
}
