package com.ossian.FitFlow.service;


import com.ossian.FitFlow.model.CollectionExercices;
import com.ossian.FitFlow.model.Exercices;
import com.ossian.FitFlow.repository.CollectionExercicesRepository;
import com.ossian.FitFlow.repository.ExercicesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


public interface CollectionExercicesService {

    CollectionExercices saveCollectionExercices(CollectionExercices collectionExercices);
    List<CollectionExercices> getAllCollectionExercices();

    void deleteCollectionExercices(Long id);

    CollectionExercices updateCollectionExercices(CollectionExercices collectionExercices);
    CollectionExercices findByName(String name);

    CollectionExercices addExercicesToCollectionExercices(Long id, Long idExercices);

    CollectionExercices removeExercicesToCollectionExercices(Long id, Long idExercices);

    List<CollectionExercices> findByDifficulty(int difficulty);


    List<Exercices> getExercicesByCollectionExercices(Long id);
}
