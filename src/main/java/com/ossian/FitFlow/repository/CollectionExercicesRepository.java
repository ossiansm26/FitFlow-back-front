package com.ossian.FitFlow.repository;

import com.ossian.FitFlow.model.CollectionExercices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionExercicesRepository extends JpaRepository<CollectionExercices, Long> {
    List<CollectionExercices> findByCollectionName(String fieldName);


}
