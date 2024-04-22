package com.ossian.FitFlow.serviceImpl;


import com.ossian.FitFlow.model.MuscleGroup;
import com.ossian.FitFlow.repository.MuscleGroupRepository;
import com.ossian.FitFlow.service.MuscleGroupService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MuscleGroupServiceImpl implements MuscleGroupService {
    @Autowired
    MuscleGroupRepository muscleGroupRepository;


    public MuscleGroup saveMuscleGroup(MuscleGroup muscleGroup){return muscleGroupRepository.save(muscleGroup);};
    public List<MuscleGroup> getAllMuscleGroup(){return muscleGroupRepository.findAll();};

    public void deleteMuscleGroup(Long id) {
        muscleGroupRepository.deleteById(id);
    }

    public MuscleGroup updateMuscleGroup(MuscleGroup muscleGroup) {
        return muscleGroupRepository.save(muscleGroup);
    }
}
