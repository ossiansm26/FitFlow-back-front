package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post, Long idUser, Long idCommunity);
    Post getPost(Long id);

    void deletePost(Long id);

    List<Post> getAllPost();
}
