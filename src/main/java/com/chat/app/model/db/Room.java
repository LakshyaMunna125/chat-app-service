package com.chat.app.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "rooms")
public class Room {
    private String id;
    @Indexed(unique = true)
    private String roomId;
    private String roomName;
    private List<User> users;
    private List<Message> messages;
    private LocalDateTime createAt;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Message{
      private String sender;
      private String content;
      private LocalDateTime timeStamp;

      public Message(String sender, String content){
          this.sender=sender;
          this.content=content;
          this.timeStamp=LocalDateTime.now();
      }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class User{
        private String name;
        private LocalDateTime joinedTime;

        public User(String name){
            this.name = name;
            this.joinedTime = LocalDateTime.now();
        }
    }
}
