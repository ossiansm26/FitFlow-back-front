package com.ossian.FitFlow.repository;

import com.ossian.FitFlow.model.Routine;
import com.ossian.FitFlow.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetRepository extends JpaRepository<Set, Long> {
}
