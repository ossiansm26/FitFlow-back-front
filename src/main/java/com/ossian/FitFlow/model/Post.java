package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "creationDate", nullable = false)
    private Date creationDate;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "likes", nullable = false)
    private Long likes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="community_id")
    private Community community;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="User_id")
    private User user;

}
