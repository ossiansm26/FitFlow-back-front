package com.ossian.FitFlow.service;


import com.ossian.FitFlow.model.MuscleGroup;
import com.ossian.FitFlow.repository.MuscleGroupRepository;


import java.util.List;


public interface MuscleGroupService {

    MuscleGroup saveMuscleGroup(MuscleGroup muscleGroup);
    List<MuscleGroup> getAllMuscleGroup();

    void deleteMuscleGroup(Long id);

    MuscleGroup updateMuscleGroup(MuscleGroup muscleGroup);

    MuscleGroup getMuscleGroupById(Long id);
}
