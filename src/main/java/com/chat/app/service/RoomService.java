package com.chat.app.service;

import com.chat.app.model.db.Room;
import com.chat.app.model.request.RoomCreationRequest;

import java.util.List;

public interface RoomService {

    Room createRoom(RoomCreationRequest request);
    String joinRoom(String roomId, String userName);
    List<Room.Message> getAllMessagesOfRoom(String roomId, int page , int size);
}
