package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "creationDate")
    private Date creationDate;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_comment")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="post_comment")
    private Post post;

}
