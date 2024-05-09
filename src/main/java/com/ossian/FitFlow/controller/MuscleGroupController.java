package com.ossian.FitFlow.controller;


import com.ossian.FitFlow.model.MuscleGroup;
import com.ossian.FitFlow.serviceImpl.MuscleGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/muscleGroup")
@CrossOrigin(origins = "http://localhost:8080")
public class MuscleGroupController {

    @Autowired
    private MuscleGroupServiceImpl muscleGroupService;

    @GetMapping("/getAll")
    public ResponseEntity<List<MuscleGroup>> getAllMuscleGroup() {
        List<MuscleGroup> muscleGroups = muscleGroupService.getAllMuscleGroup();
        return ResponseEntity.ok(muscleGroups);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<MuscleGroup> getMuscleGroupById(@PathVariable Long id) {
        MuscleGroup muscleGroup = muscleGroupService.getMuscleGroupById(id);
        return ResponseEntity.ok(muscleGroup);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMuscleGroup(@PathVariable Long id) {
        muscleGroupService.deleteMuscleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<MuscleGroup> updateMuscleGroup(@RequestBody MuscleGroup muscleGroup) {
        MuscleGroup muscleGroupUpdated = muscleGroupService.updateMuscleGroup(muscleGroup);
        return ResponseEntity.ok(muscleGroupUpdated);
    }
    @PostMapping("/create")
    public ResponseEntity<MuscleGroup> createMuscleGroup(@RequestBody MuscleGroup muscleGroup) {
        MuscleGroup newMuscleGroup = muscleGroupService.saveMuscleGroup(muscleGroup);
        return ResponseEntity.ok(newMuscleGroup);
    }

}