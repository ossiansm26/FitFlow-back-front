package com.ossian.FitFlow.service;


import com.ossian.FitFlow.model.CollectionExercices;
import com.ossian.FitFlow.model.Routine;

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

    List<CollectionExercices> getExercicesByRoutine(Long routineId);
}




