package com.ossian.FitFlow.serviceImpl;


import com.ossian.FitFlow.model.CollectionExercices;
import com.ossian.FitFlow.model.Routine;
import com.ossian.FitFlow.repository.CollectionExercicesRepository;
import com.ossian.FitFlow.repository.RoutineRepository;
import com.ossian.FitFlow.service.RoutineService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoutineServiceImpl implements RoutineService {

    @Autowired
    RoutineRepository routineRepository;
    @Autowired
    CollectionExercicesRepository collectionExercicesRepository;


    public Routine saveRoutine(Routine routine){return routineRepository.save(routine);};
    public List<Routine> getAllRoutine(){return routineRepository.findAll();};

    public void deleteRoutine(Long id) {
        routineRepository.deleteById(id);
    }

    public Routine updateRoutine(Routine routine) {
        return routineRepository.save(routine);
    }


    public Routine addCollectionExercicesToRoutine(Long id, Long idCollectionExercices) {
        Routine routine = routineRepository.findById(id).orElseThrow(() -> new RuntimeException("Routine not found"));

        CollectionExercices collectionExercices = collectionExercicesRepository.findById(idCollectionExercices)
                .orElseThrow(() -> new RuntimeException("Collection Exercices not found"));

        routine.getExercicesCollection().add(collectionExercices);
        collectionExercices.getRoutine().add(routine);

        collectionExercicesRepository.save(collectionExercices);
        return routineRepository.save(routine);
    }

    public Routine removeCollectionExercicesToRoutine(Long id, Long idCollectionExercices) {
        Routine routine = routineRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Routine not found"));
        CollectionExercices collectionExercices = collectionExercicesRepository.findById(idCollectionExercices).orElseThrow(
                () -> new RuntimeException("Collection Exercices not found"));

        routine.getExercicesCollection().remove(collectionExercices);
        collectionExercices.getRoutine().remove(routine);

        collectionExercicesRepository.save(collectionExercices);
        return routineRepository.save(routine);
    }

    public List<Routine> searchRoutineByStartDateIsBetween(Date date1, Date date2) {
        return routineRepository.findAll().stream()
                .filter(routine -> routine.getStart().compareTo(date1) >= 0 && routine.getStart().compareTo(date2) <= 0)
                .toList();

    }

    public Routine getRoutineById(Long id) {
        return routineRepository.findById(id).orElse(null);
    }


}




