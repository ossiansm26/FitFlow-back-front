package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.Post;
import com.ossian.FitFlow.serviceImpl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "http://localhost:8080")
public class PostController {
    @Autowired
    private PostServiceImpl postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, Long idUser, Long idCommunity) {
        Post newPost = postService.createPost(post,idUser,idCommunity);
        return ResponseEntity.ok(newPost);
    }
    @GetMapping
    public ResponseEntity<List<Post>> getAllPost() {
        return ResponseEntity.ok(postService.getAllPost());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        Post post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/like/{idPost}/{idUser}")
    public ResponseEntity<Post> likePost(@PathVariable Long idPost, @PathVariable Long idUser) {
        Post post = postService.likePost(idPost, idUser);
        return ResponseEntity.ok(post);
    }



}
