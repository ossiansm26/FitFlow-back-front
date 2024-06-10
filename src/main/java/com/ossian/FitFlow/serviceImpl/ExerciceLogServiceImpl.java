package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.model.ExerciceLog;
import com.ossian.FitFlow.model.Exercices;
import com.ossian.FitFlow.model.Set;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.repository.ExercicesRepository;
import com.ossian.FitFlow.repository.SetRepository;
import com.ossian.FitFlow.repository.UserRepository;
import com.ossian.FitFlow.service.ExerciceLogService;
import com.ossian.FitFlow.repository.ExerciceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ExerciceLogServiceImpl implements ExerciceLogService {

    @Autowired
    private ExerciceLogRepository exerciceLogRepository;
    @Autowired
    private SetRepository setRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<ExerciceLog> getAllExerciceLog() {
        return exerciceLogRepository.findAll();
    }

    @Override
    public ExerciceLog saveExerciceLog(ExerciceLog exerciceLog, Long idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));
        for (Set set : exerciceLog.getSets()) {
            set.setExerciceLog(exerciceLog);
        }
        exerciceLog.setUser(user);
        return exerciceLogRepository.save(exerciceLog);
    }

    @Override
    public List<ExerciceLog> findById(Long id) {
        List<ExerciceLog> exerciceLogs = exerciceLogRepository.findAll();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return exerciceLogs.stream()
                .filter(exercice -> exercice.getUser().equals(user))
                .toList();
    }
}
