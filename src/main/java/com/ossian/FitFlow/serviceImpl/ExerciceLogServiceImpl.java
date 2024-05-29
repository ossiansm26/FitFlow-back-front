package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.model.ExerciceLog;
import com.ossian.FitFlow.model.Exercices;
import com.ossian.FitFlow.model.Set;
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

    @Override
    public List<ExerciceLog> getAllExerciceLog() {
        return exerciceLogRepository.findAll();
    }

    @Override
    public ExerciceLog saveExerciceLog(ExerciceLog exerciceLog) {
        for (Set set : exerciceLog.getSets()) {
            set.setExerciceLog(exerciceLog);
        }
        return exerciceLogRepository.save(exerciceLog);
    }
}
