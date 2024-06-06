package com.ossian.FitFlow.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {
    private Long id;
    private String content;
    private Long senderId;
    private Long recipientId;
    private Date Timestamp;
    private UserDTO sender;
    private UserDTO recipient;

}
