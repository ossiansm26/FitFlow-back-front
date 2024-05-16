package com.ossian.FitFlow.controller;



import com.ossian.FitFlow.model.Routine;

import com.ossian.FitFlow.serviceImpl.RoutineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ossian.FitFlow.model.CollectionExercices;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/routine")
@CrossOrigin(origins = "http://localhost:8080")
public class RoutineController {
    @Autowired
    private RoutineServiceImpl routineService;
    


    @GetMapping("/getAllRoutine")
    public ResponseEntity<List<Routine>> getAllRoutine() {
        List<Routine> Routine = routineService.getAllRoutine();
        return ResponseEntity.ok(Routine);
    }
    @GetMapping("/getRoutineById/{id}")
    public ResponseEntity<Routine> getRoutineById(@PathVariable Long id) {
        Routine Routine = routineService.getRoutineById(id);
        return ResponseEntity.ok(Routine);
    }
    @GetMapping("/getRoutineByDate/{startDate}/{endDate}")
    public ResponseEntity<List<Routine>> searchRoutineByStartDateIsBetween(@PathVariable Date startDate,
                                                                           @PathVariable Date endDate) {
        List<Routine> Routine = routineService.searchRoutineByStartDateIsBetween(startDate, endDate);
        return ResponseEntity.ok(Routine);
    }
    @GetMapping("/{routineId}/getExercices")
    public ResponseEntity<List<CollectionExercices>> getExercicesByRoutine(@PathVariable Long routineId) {
        List<CollectionExercices> exercicesCollection = routineService.getExercicesByRoutine(routineId);
        return ResponseEntity.ok(exercicesCollection);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable Long id) {
        routineService.deleteRoutine(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}/removeExercices/{idCollectionExercices}")
    public ResponseEntity<Routine> removeCollectionExercicesToRoutine(@PathVariable Long id,
                                                                      @PathVariable Long idCollectionExercices) {
        Routine Routine = routineService.removeCollectionExercicesToRoutine(id, idCollectionExercices);
        return ResponseEntity.ok(Routine);
    }

    @PostMapping("/createRoutine")
    public ResponseEntity<Routine> createRoutine(@RequestBody Routine Routine) {
        Routine newRoutine = routineService.saveRoutine(Routine);
        return ResponseEntity.ok(newRoutine);
    }
    @PostMapping("/{id}/addExercices/{idCollectionExercices}")
    public ResponseEntity<Routine> addCollectionExercicesToRoutine(@PathVariable Long id,
                                                                   @PathVariable Long idCollectionExercices) {
        Routine Routine = routineService.addCollectionExercicesToRoutine(id, idCollectionExercices);
        return ResponseEntity.ok(Routine);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Routine> updateRoutine(@RequestBody Routine Routine) {
        Routine RoutineUpdated = routineService.updateRoutine(Routine);
        return ResponseEntity.ok(RoutineUpdated);
    }


}
