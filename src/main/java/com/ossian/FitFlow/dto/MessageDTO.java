package com.ossian.FitFlow.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {
    String content;
    Long senderId;
    Long recipientId;
    Date Timestamp;
}
