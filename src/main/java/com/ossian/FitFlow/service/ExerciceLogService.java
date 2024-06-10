package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.ExerciceLog;

import java.util.List;

public interface ExerciceLogService {
    List<ExerciceLog> getAllExerciceLog();

    ExerciceLog saveExerciceLog(ExerciceLog exerciceLog, Long idUser);

    List<ExerciceLog> findById(Long id);
}
