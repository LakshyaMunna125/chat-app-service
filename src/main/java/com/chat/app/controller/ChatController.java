package com.chat.app.controller;

import com.chat.app.model.db.Room;
import com.chat.app.model.request.MessageRequest;
import com.chat.app.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:3000")
public class ChatController {

    @Autowired private RoomRepository roomRepository;


    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Room.Message sendMessage(@DestinationVariable String roomId, @RequestBody MessageRequest messageRequest){
        Room room = roomRepository.findByRoomId(roomId);
        Room.Message message = Room.Message.builder().
                sender(messageRequest.getSender()).
                content(messageRequest.getContent())
                .timeStamp(messageRequest.getMessageTime())
                .build();
        if(room!=null){
            if(room.getMessages()==null || room.getMessages().isEmpty()){
                room.setMessages(List.of(message));
            }else {
                room.getMessages().add(message);
            }
            roomRepository.save(room);
        }else {
            throw new RuntimeException("Room Not Found!!");
        }
        return message;
    }
}
