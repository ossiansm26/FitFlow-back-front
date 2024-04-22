package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.Comment;
import com.ossian.FitFlow.serviceImpl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComment() {
        List<Comment> comments = commentService.getAllComment();
        return ResponseEntity.ok(comments);
    }

}
