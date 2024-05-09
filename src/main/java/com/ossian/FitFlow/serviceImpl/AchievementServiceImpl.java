package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.model.Achievement;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.repository.AchievementRepository;
import com.ossian.FitFlow.repository.UserRepository;
import com.ossian.FitFlow.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementServiceImpl implements AchievementService {

    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private UserRepository userRepository;

    public Achievement addAchievementToUser(Long userId, Long achievementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

            Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(()-> new RuntimeException("Achievement not found"));

        user.getAchievement().add(achievement);
        achievement.getUser().add(user);
        userRepository.save(user);
        return achievementRepository.save(achievement);
    }
    public List<Achievement> getAllAchievement(){return achievementRepository.findAll();};

    public void deleteAchievement(Long id) {
        achievementRepository.deleteById(id);
    }
    public Achievement addAchievement(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    @Override
    public Achievement getAchievementById(Long id) {
        return achievementRepository.findById(id).orElse(null);
    }

    @Override
    public Achievement updateAchievement(Long id, Achievement achievement) {
        return achievementRepository.save(achievement);

    }


}
