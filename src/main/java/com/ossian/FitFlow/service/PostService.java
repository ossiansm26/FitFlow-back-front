package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Post;

public interface PostService {
    Post createPost(Post post, Long idUser, Long idCommunity);
}
