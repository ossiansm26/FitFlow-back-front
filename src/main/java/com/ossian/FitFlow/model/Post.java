package com.ossian.FitFlow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Column(name = "creationDate")
    private Date creationDate;
    @Column(name = "category")
    private String category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="community_id")
    private Community community;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="User_Post")
    private User user;

    @OneToMany(mappedBy="post" , cascade = CascadeType.ALL)
    private List<Comments> comment = new ArrayList<>();

}
