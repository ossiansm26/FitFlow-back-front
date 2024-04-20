package com.ossian.FitFlow.service;


import com.ossian.FitFlow.model.Exercices;
import com.ossian.FitFlow.model.Material;
import com.ossian.FitFlow.model.MuscleGroup;
import com.ossian.FitFlow.repository.ExercicesRepository;
import com.ossian.FitFlow.repository.MaterialRepository;
import com.ossian.FitFlow.repository.MuscleGroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ExercicesService {


    Exercices saveExercices(Exercices exercices);

   List<Exercices> getAllExercices();

    public void deleteExercices(Long id);

    public Exercices updateExercices(Exercices exercices);

    public Exercices addMaterialToExercices(Long id, Long idMaterial);

    public Exercices addMuscleGroupToExercices(Long id, Long idMuscleGroup);

    public  List<Exercices> getExercicesByDuration(int duration);
}
