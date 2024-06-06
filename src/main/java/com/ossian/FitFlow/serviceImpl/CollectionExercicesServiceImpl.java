package com.ossian.FitFlow.serviceImpl;


import com.ossian.FitFlow.model.CollectionExercices;
import com.ossian.FitFlow.model.Exercices;
import com.ossian.FitFlow.repository.CollectionExercicesRepository;
import com.ossian.FitFlow.repository.ExercicesRepository;
import com.ossian.FitFlow.service.CollectionExercicesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionExercicesServiceImpl implements CollectionExercicesService {
    @Autowired
    CollectionExercicesRepository collectionCollectionExercicesRepository;

    @Autowired
    ExercicesRepository exercicesRepository;


    public CollectionExercices saveCollectionExercices(CollectionExercices collectionExercices) {
        return collectionCollectionExercicesRepository.save(collectionExercices);
    }
    public List<CollectionExercices> getAllCollectionExercices(){return collectionCollectionExercicesRepository.findAll();};


    public void deleteCollectionExercices(Long id) {
        collectionCollectionExercicesRepository.deleteById(id);
    }

    public CollectionExercices updateCollectionExercices(CollectionExercices collectionExercices) {
        return collectionCollectionExercicesRepository.save(collectionExercices);
    }
    public CollectionExercices findByName(String name) {
        return collectionCollectionExercicesRepository.findByCollectionName(name).get(0);
    }
    public CollectionExercices addExercicesToCollectionExercices(Long id, Long idExercices) {
        CollectionExercices collectionExercices = collectionCollectionExercicesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection Exercices not found"));

        Exercices exercices = exercicesRepository.findById(idExercices)
                .orElseThrow(() -> new RuntimeException("Exercices not found"));

        collectionExercices.getExercices().add(exercices);
        collectionExercices.setTotalExercices(collectionExercices.getTotalExercices()+1);
        exercices.getCollectionExercices().add(collectionExercices);

        exercicesRepository.save(exercices);
        return collectionCollectionExercicesRepository.save(collectionExercices);

    }

    public CollectionExercices removeExercicesToCollectionExercices(Long id, Long idExercices) {
        CollectionExercices collectionExercices = collectionCollectionExercicesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection Exercices not found"));
        Exercices exercices = exercicesRepository.findById(idExercices)
                .orElseThrow(() -> new RuntimeException("Exercices not found"));

        collectionExercices.getExercices().remove(exercices);
        collectionExercices.setTotalExercices(collectionExercices.getTotalExercices()-1);
        exercices.getCollectionExercices().remove(collectionExercices);

        exercicesRepository.save(exercices);
        return collectionCollectionExercicesRepository.save(collectionExercices);
    }

    public List<CollectionExercices> findByDifficulty(int difficulty) {
        return collectionCollectionExercicesRepository.findAll().stream()
                .filter(collectionExercices -> collectionExercices.getDifficultyLevel() == difficulty)
                .collect(Collectors.toList());
    }

    @Override
    public List<Exercices> getExercicesByCollectionExercices(Long id) {
        CollectionExercices collectionExercices = collectionCollectionExercicesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection Exercices not found"));
        return collectionExercices.getExercices();
    }

    @Override
    public CollectionExercices findById(Long id) {
        return collectionCollectionExercicesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection Exercices not found"));

    }


}
