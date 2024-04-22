package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "creationDate")
    private Date creationDate;
    @Column(name = "likes")
    private Long likes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_comment")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="post_comment")
    private Post post;

}
