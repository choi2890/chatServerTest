package com.chatserver.repository;


import com.chatserver.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {


        List<Message> findByReceiverName(String receiverName);
        List<Message> findByRoomId(String roomId);

}
