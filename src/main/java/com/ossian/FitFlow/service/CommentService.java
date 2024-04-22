package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Comment;

import java.util.List;

public interface CommentService {
    Comment addCommentToPost(Long idComment, Long idPost , Long idUser);
    List<Comment> getAllComment();

}
