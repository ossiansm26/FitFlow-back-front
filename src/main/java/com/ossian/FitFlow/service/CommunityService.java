package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Community;
import com.ossian.FitFlow.model.User;

import java.util.List;

public interface CommunityService  {
    Community removeUserFromCommunity(Long id, Long idUser);
    Community addUserToCommunity(Long id, Long idUser);
    Community createCommunity(Long idUser,Community community);
    List<Community> getAllCommunity();

}
