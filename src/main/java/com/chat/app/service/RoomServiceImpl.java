package com.chat.app.service;

import com.chat.app.model.db.Room;
import com.chat.app.model.request.RoomCreationRequest;
import com.chat.app.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired private RoomRepository roomRepository;

    @Override
    public Room createRoom(final RoomCreationRequest request) {

        if(!Objects.isNull(checkRoomExist(request.getRoomId()))){
            throw new RuntimeException("room already exists.");
        }
        final Room room = roomMapper(request);
        return roomRepository.save(room);
    }

    @Override
    public String joinRoom(String roomId, String userName) {
        final Room room = checkRoomExist(roomId);
        if(Objects.nonNull(room)){
            room.getUsers().add(new Room.User(userName));
            roomRepository.save(room);
        }else{
            throw new RuntimeException("room not exists.");
        }
        return "Joined";
    }

    @Override
    public List<Room.Message> getAllMessagesOfRoom(String roomId, int page , int size){
        final Room room = checkRoomExist(roomId);
        if(Objects.isNull(room)){
            throw new RuntimeException("room not exists.");
        }
        final List<Room.Message> messages = room.getMessages();
        int start = Math.max(0,messages.size()-(page+1)*size);
        int end = Math.min(messages.size(), start+size);
        return messages.subList(start,end);
    }


    private Room roomMapper(final RoomCreationRequest request){
        return Room.builder()
                .id(UUID.randomUUID().toString())
                .roomId(request.getRoomId())
                .roomName(request.getRoomName())
                .users(List.of(new Room.User(request.getUserName())))
                .createAt(LocalDateTime.now())
                .build();
    }

    private Room checkRoomExist(final String roomId){
        return roomRepository.findByRoomId(roomId);
    }
}
