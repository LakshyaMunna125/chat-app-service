package com.chat.app.model.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequest {
    private String sender;
    private String roomId;
    private String content;
    private LocalDateTime messageTime;
}
