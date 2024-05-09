package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Community;

import java.util.List;

public interface CommunityService  {
    Community removeUserFromCommunity(Long id, Long idUser);
    Community addUserToCommunity(Long id, Long idUser);
    Community createCommunity(Long idUser,Community community);
    List<Community> getAllCommunity();

    void deleteCommunity(Long id);

    Community getCommunityById(Long id);

    Community updateCommunity(Community community ,Long id);
}
