package com.ossian.FitFlow.controller;


import com.ossian.FitFlow.model.CollectionExercices;
import com.ossian.FitFlow.model.Exercices;
import com.ossian.FitFlow.serviceImpl.CollectionExercicesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collectionExercices")
@CrossOrigin(origins = "http://localhost:8080")
public class CollectionExercicesController {
    @Autowired
    private CollectionExercicesServiceImpl collectionExercicesService;

    @PostMapping("/add")
    public ResponseEntity<CollectionExercices> createCollectionRoutine(@RequestBody CollectionExercices collectionExercices) {
        CollectionExercices newExercices = collectionExercicesService.saveCollectionExercices(collectionExercices);
        return ResponseEntity.ok(newExercices);
    }

    @GetMapping
    public ResponseEntity<List<CollectionExercices>> getAllCollectionRoutine() {
        List<CollectionExercices> collectionExercices =  collectionExercicesService.getAllCollectionExercices();
        return ResponseEntity.ok(collectionExercices);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionExercices(@PathVariable Long id) {
        collectionExercicesService.deleteCollectionExercices(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<CollectionExercices> updateCollectionExercices(@RequestBody CollectionExercices collectionExercices) {
        CollectionExercices collectionExercicesUpdated = collectionExercicesService.updateCollectionExercices(collectionExercices);
        return ResponseEntity.ok(collectionExercicesUpdated);
    }
    @GetMapping("/{name}")
    public ResponseEntity<CollectionExercices> getCollectionExercicesByName(@PathVariable String name) {
        CollectionExercices collectionExercices = collectionExercicesService.findByName(name);
        return ResponseEntity.ok(collectionExercices);
    }
    @PostMapping("/{id}/addExercices/{idExercices}")
    public ResponseEntity<CollectionExercices> addExercicesToCollectionExercices(@PathVariable Long id,
                                                                               @PathVariable Long idExercices) {
        CollectionExercices collectionExercices = collectionExercicesService.addExercicesToCollectionExercices(id, idExercices);
        return ResponseEntity.ok(collectionExercices);
    }
    @DeleteMapping("/{id}/removeExercices/{idExercices}")
    public ResponseEntity<CollectionExercices> removeExercicesToCollectionExercices(@PathVariable Long id,
                                                                                  @PathVariable Long idExercices) {
        CollectionExercices collectionExercices = collectionExercicesService.removeExercicesToCollectionExercices(id, idExercices);
        return ResponseEntity.ok(collectionExercices);
    }
    @GetMapping("/getExercicesByDifficulty/{difficulty}")
    public ResponseEntity<List<CollectionExercices>> searchExercicesByDifficulty(@PathVariable int difficulty) {
        List<CollectionExercices> collectionExercices = collectionExercicesService.findByDifficulty(difficulty);
        return ResponseEntity.ok(collectionExercices);
    }
    @GetMapping("{id}/getExercices")
    public ResponseEntity<List<Exercices>> getExercicesByCollectionExercices(@PathVariable Long id) {
        List<Exercices> collectionExercices = collectionExercicesService.getExercicesByCollectionExercices(id);
        return ResponseEntity.ok(collectionExercices);
    }


}
