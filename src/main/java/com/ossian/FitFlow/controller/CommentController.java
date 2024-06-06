package com.ossian.FitFlow.controller;


import com.ossian.FitFlow.model.Comments;
import com.ossian.FitFlow.serviceImpl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
@CrossOrigin(origins = "http://localhost:8080")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping
    public ResponseEntity<List<Comments>> getAllComment() {
        List<Comments> comments = commentService.getAllComment();
        return ResponseEntity.ok(comments);
    }

}
