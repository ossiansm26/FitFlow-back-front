package com.ossian.FitFlow.serviceImpl;


import com.ossian.FitFlow.model.Exercices;
import com.ossian.FitFlow.model.Material;
import com.ossian.FitFlow.model.MuscleGroup;
import com.ossian.FitFlow.repository.ExercicesRepository;
import com.ossian.FitFlow.repository.MaterialRepository;
import com.ossian.FitFlow.repository.MuscleGroupRepository;
import com.ossian.FitFlow.service.ExercicesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExercicesServiceImpl implements ExercicesService {
    @Autowired
    ExercicesRepository exercicesRepository;
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    MuscleGroupRepository muscleGroupRepository;
    
    @Transactional
    public Exercices saveExercices(Exercices exercices) {
        return exercicesRepository.save(exercices);
    }

    public List<Exercices> getAllExercices(){return exercicesRepository.findAll();};
    @Transactional
    public void deleteExercices(Long id) {
        exercicesRepository.deleteById(id);
    }
    @Transactional
    public Exercices updateExercices(Exercices exercices) {
        return exercicesRepository.save(exercices);
    }
    @Transactional
    public Exercices addMaterialToExercices(Long id, Long idMaterial) {
        Exercices exercices = exercicesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercices not found"));

        Material material = materialRepository.findById(idMaterial)
                .orElseThrow(() -> new RuntimeException("Material not found"));

        exercices.getMaterial().add(material);
        material.getExercices().add(exercices);

        materialRepository.save(material);
        return exercicesRepository.save(exercices);
    }
    @Transactional
    public Exercices addMuscleGroupToExercices(Long id, Long idMuscleGroup) {
        Exercices exercices = exercicesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercices not found"));

        MuscleGroup muscleGroup = muscleGroupRepository.findById(idMuscleGroup)
                .orElseThrow(() -> new RuntimeException("MuscleGroup not found"));

        exercices.getMuscleGroup().add(muscleGroup);
        muscleGroup.getExercices().add(exercices);

        muscleGroupRepository.save(muscleGroup);
        return exercicesRepository.save(exercices);
    }

    public  List<Exercices> getExercicesByDuration(int duration){
        return exercicesRepository.findByDurationIsAfter(duration);
    }

}
