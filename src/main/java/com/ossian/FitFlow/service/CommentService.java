package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Comments;

import java.util.List;

public interface CommentService {
    Comments addCommentToPost(Long idComment, Long idPost , Long idUser);
    List<Comments> getAllComment();

}
