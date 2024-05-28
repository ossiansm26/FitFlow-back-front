package com.ossian.FitFlow.controller;


import com.ossian.FitFlow.model.Exercices;
import com.ossian.FitFlow.model.Material;
import com.ossian.FitFlow.model.MuscleGroup;
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

    @PostMapping("/create")
    public ResponseEntity<Exercices> createExercices(@RequestBody Exercices exercices) {
        Exercices newExercices = exercicesService.saveExercices(exercices);
        return ResponseEntity.ok(newExercices);
    }

    @GetMapping
    public ResponseEntity<List<Exercices>> getAllExercices() {
        List<Exercices> Routine = exercicesService.getAllExercices();
        return ResponseEntity.ok(Routine);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercices(@PathVariable Long id) {
        exercicesService.deleteExercices(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/update/{id}")
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
    @GetMapping("/{id}/materials")
    public ResponseEntity<List<Material>> getMaterialsFromExercices(@PathVariable Long id) {
        List<Material> exercices = exercicesService.getMaterialsFromExercices(id);
        return ResponseEntity.ok(exercices);
    }
    @GetMapping("/{id}/muscleGroups")
    public ResponseEntity<List<MuscleGroup>> getMuscleGroupsFromExercices(@PathVariable Long id) {
        List<MuscleGroup> exercices = exercicesService.getMuscleGroupFromExercices(id);
        return ResponseEntity.ok(exercices);
    }

    @PostMapping("/{id}/addMuscleGroup/{idMuscleGroup}")
    public ResponseEntity<Exercices> addMuscleGroupToExercices(@PathVariable Long id,
                                                              @PathVariable Long idMuscleGroup) {
        Exercices exercices = exercicesService.addMuscleGroupToExercices(id, idMuscleGroup);
        return ResponseEntity.ok(exercices);
    }
    @DeleteMapping("/{id}/deleteMaterial/{idMaterial}")
    public ResponseEntity<Exercices> deleteMaterialFromExercices(@PathVariable Long id,
                                                                 @PathVariable Long idMaterial) {
        Exercices exercices = exercicesService.deleteMaterialFromExercices(id, idMaterial);
        return ResponseEntity.ok(exercices);
    }
    @DeleteMapping("/{id}/deleteMuscleGroup/{idMuscleGroup}")
    public ResponseEntity<Exercices> deleteMuscleGroupFromExercices(@PathVariable Long id,
                                                                    @PathVariable Long idMuscleGroup) {
        Exercices exercices = exercicesService.deleteMuscleGroupFromExercices(id, idMuscleGroup);
        return ResponseEntity.ok(exercices);
    }

    @GetMapping ("/getExerciceByDuration/{duration}")
    public ResponseEntity<List<Exercices>> getExercicesByDuration(@PathVariable int duration) {
        List<Exercices> exercices = exercicesService.getExercicesByDuration(duration);
        return ResponseEntity.ok(exercices);
    }
    @GetMapping ("/getExerciseById/{id}")
    public ResponseEntity<Exercices> getExercicesById(@PathVariable Long id) {
        Exercices exercices = exercicesService.getExercicesById(id);
        return ResponseEntity.ok(exercices);
    }


}
