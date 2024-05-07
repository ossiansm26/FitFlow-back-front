package com.ossian.FitFlow.service;


import com.ossian.FitFlow.model.CollectionExercices;
import com.ossian.FitFlow.model.Routine;
import com.ossian.FitFlow.repository.CollectionExercicesRepository;
import com.ossian.FitFlow.repository.RoutineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


public interface RoutineService {


    Routine saveRoutine(Routine routine);
    List<Routine> getAllRoutine();
    void deleteRoutine(Long id);

    Routine updateRoutine(Routine routine);

    public Routine addCollectionExercicesToRoutine(Long id, Long idCollectionExercices);

     Routine removeCollectionExercicesToRoutine(Long id, Long idCollectionExercices);

    public List<Routine> searchRoutineByStartDateIsBetween(Date date1, Date date2);


    Routine getRoutineById(Long id);
}




