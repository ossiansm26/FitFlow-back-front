package com.ossian.FitFlow.controller;


import com.ossian.FitFlow.model.Exercices;
import com.ossian.FitFlow.serviceImpl.ExercicesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercices")
@CrossOrigin(origins = "http://localhost:8080")
public class ExercicesController {
    @Autowired
    private ExercicesServiceImpl exercicesService;

    @PostMapping
    public ResponseEntity<Exercices> createRoutine(@RequestBody Exercices exercices) {
        Exercices newExercices = exercicesService.saveExercices(exercices);
        return ResponseEntity.ok(newExercices);
    }

    @GetMapping
    public ResponseEntity<List<Exercices>> getAllRoutine() {
        List<Exercices> Routine = exercicesService.getAllExercices();
        return ResponseEntity.ok(Routine);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercices(@PathVariable Long id) {
        exercicesService.deleteExercices(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}")
    public ResponseEntity<Exercices> updateExercices(@RequestBody Exercices exercices) {
        Exercices exercicesUpdated = exercicesService.updateExercices(exercices);
        return ResponseEntity.ok(exercicesUpdated);
    }
    @PostMapping("/{id}/addMaterial/{idMaterial}")
    public ResponseEntity<Exercices> addMaterialToExercices(@PathVariable Long id,
                                                           @PathVariable Long idMaterial) {
        Exercices exercices = exercicesService.addMaterialToExercices(id, idMaterial);
        return ResponseEntity.ok(exercices);
    }
    @PostMapping("/{id}/addMuscleGroup/{idMuscleGroup}")
    public ResponseEntity<Exercices> addMuscleGroupToExercices(@PathVariable Long id,
                                                              @PathVariable Long idMuscleGroup) {
        Exercices exercices = exercicesService.addMuscleGroupToExercices(id, idMuscleGroup);
        return ResponseEntity.ok(exercices);
    }

    @GetMapping ("/getExerciceByDuration/{duration}")
    public ResponseEntity<List<Exercices>> getExercicesByDuration(@PathVariable int duration) {
        List<Exercices> exercices = exercicesService.getExercicesByDuration(duration);
        return ResponseEntity.ok(exercices);
    }


}
