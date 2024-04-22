package com.ossian.FitFlow.repository;

import com.ossian.FitFlow.model.Achivement;
import com.ossian.FitFlow.model.CollectionExercices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchivementRepository  extends JpaRepository<Achivement, Long> {

}
