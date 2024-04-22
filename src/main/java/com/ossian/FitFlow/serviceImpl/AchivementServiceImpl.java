package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.model.Achivement;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.repository.AchivementRepository;
import com.ossian.FitFlow.repository.UserRepository;
import com.ossian.FitFlow.service.AchivementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchivementServiceImpl implements AchivementService {

    @Autowired
    private AchivementRepository achivementRepository;
    @Autowired
    private UserRepository userRepository;

    public Achivement addAchivementToUser(Long userId, Long achivementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

            Achivement achivement = achivementRepository.findById(achivementId)
                .orElseThrow(()-> new RuntimeException("Achivement not found"));

        user.getAchivement().add(achivement);
        achivement.getUser().add(user);
        userRepository.save(user);
        return achivementRepository.save(achivement);
    }
    public List<Achivement> getAllAchivement(){return achivementRepository.findAll();};




}
