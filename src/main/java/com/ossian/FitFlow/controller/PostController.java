package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.Post;
import com.ossian.FitFlow.serviceImpl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostServiceImpl postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, Long idUser, Long idCommunity) {
        Post newPost = postService.createPost(post,idUser,idCommunity);
        return ResponseEntity.ok(newPost);
    }


}
