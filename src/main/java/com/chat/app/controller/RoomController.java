package com.chat.app.controller;

import com.chat.app.model.db.Room;
import com.chat.app.model.request.RoomCreationRequest;
import com.chat.app.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/room")
@CrossOrigin
public class RoomController {

    private static final String ROOM_CREATED = "room created";
    private static final String ROOM_NOT_CREATED = "room already exists";
    @Autowired private RoomService roomService;


    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody RoomCreationRequest request){
        try {
            Room room = roomService.createRoom(request);
            return new ResponseEntity<>(ROOM_CREATED, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(ROOM_NOT_CREATED,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId, @RequestParam String name){
        return ResponseEntity.ok(roomService.joinRoom(roomId,name));
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<?> getAllMessages(@PathVariable String roomId, @RequestParam(name = "page", defaultValue = "0",required = false) int page, @RequestParam(name = "size",defaultValue = "20", required = false) int size){
        return ResponseEntity.ok(roomService.getAllMessagesOfRoom(roomId,page,size));
    }
}
