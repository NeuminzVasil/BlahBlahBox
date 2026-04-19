package com.home.repository;

import com.home.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByToUserIsNullOrToUserOrderByTimestampAsc(String toUser);
}