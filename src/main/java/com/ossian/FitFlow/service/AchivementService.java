package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Achivement;

import java.util.List;

public interface AchivementService {
    Achivement addAchivementToUser(Long userId, Long achivementId) ;
    List<Achivement> getAllAchivement();

    void deleteAchivement(Long id);

    Achivement addAchivement(Achivement achivement);
}
