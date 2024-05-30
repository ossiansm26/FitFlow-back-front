package com.ossian.FitFlow.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false) // Different column name for sender
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false) // Different column name for recipient
    private User recipient;

    @Column(name = "content", nullable = false)
    private String content;
}