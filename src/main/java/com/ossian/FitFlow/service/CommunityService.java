package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Comments;
import com.ossian.FitFlow.model.Community;
import com.ossian.FitFlow.model.Post;

import java.util.List;

public interface CommunityService  {
    Community removeUserFromCommunity(Long id, Long idUser);
    Community addUserToCommunity(Long id, Long idUser);
    Community createCommunity(Long idUser,Community community);
    List<Community> getAllCommunity();

    void deleteCommunity(Long id);

    Community getCommunityById(Long id);

    Community updateCommunity(Community community ,Long id);

    Community addPostToCommunity(Long idCommunity, Long idUser, Post post);

    Post addReplyToPost(Long idPost,Long idUser, Comments comment);
}
