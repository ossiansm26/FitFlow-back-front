package com.ossian.FitFlow.service;


import com.ossian.FitFlow.model.MuscleGroup;
import com.ossian.FitFlow.repository.MuscleGroupRepository;


import java.util.List;


public interface MuscleGroupService {

    public MuscleGroup saveMuscleGroup(MuscleGroup muscleGroup);
    public List<MuscleGroup> getAllMuscleGroup();

    public void deleteMuscleGroup(Long id);

    public MuscleGroup updateMuscleGroup(MuscleGroup muscleGroup);

    MuscleGroup getMuscleGroupById(Long id);
}
