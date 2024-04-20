package com.ossian.FitFlow.repository;

import com.ossian.FitFlow.model.CollectionExercices;
import com.ossian.FitFlow.model.Exercices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExercicesRepository extends JpaRepository<Exercices, Long> {
    List<Exercices> findByDurationIsAfter(int duration);

}
