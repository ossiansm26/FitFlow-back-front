package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.service.ExerciceLogService;
import com.ossian.FitFlow.repository.ExerciceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciceLogServiceImpl implements ExerciceLogService {

    @Autowired
    private ExerciceLogRepository exerciceLogRepository;

    
}
