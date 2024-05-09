package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Achievement;

import java.util.List;

public interface AchievementService {
    Achievement addAchievementToUser(Long userId, Long achievementId) ;
    List<Achievement> getAllAchievement();

    void deleteAchievement(Long id);

    Achievement addAchievement(Achievement achievement);

    Achievement getAchievementById(Long id);

    Achievement updateAchievement(Long id, Achievement achievement);
}
