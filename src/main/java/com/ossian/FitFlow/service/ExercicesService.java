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

   void deleteExercices(Long id);

   Exercices updateExercices(Exercices exercices);

   Exercices addMaterialToExercices(Long id, Long idMaterial);

    Exercices addMuscleGroupToExercices(Long id, Long idMuscleGroup);

    List<Exercices> getExercicesByDuration(int duration);

    Exercices getExercicesById(Long id);

    Exercices deleteMaterialFromExercices(Long id, Long idMaterial);

    List<Material> getMaterialsFromExercices(Long id);

    List<MuscleGroup> getMuscleGroupFromExercices(Long id);

    Exercices deleteMuscleGroupFromExercices(Long id, Long idMuscleGroup);
}
