package com.ossian.FitFlow.repository;

import com.ossian.FitFlow.model.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {

}
