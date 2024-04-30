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

    @PostMapping
    public ResponseEntity<MuscleGroup> createMuscleGroup(
            @RequestParam("groupName") String groupName,
            @RequestParam("bodyPart") String bodyPart,
            @RequestPart("muscleImage") String muscleImage) {
        MuscleGroup newMuscleGroup = new MuscleGroup();
        newMuscleGroup.setGroupName(groupName);
        newMuscleGroup.setBodyPart(bodyPart);
        newMuscleGroup.setMuscleImage(muscleImage);
        muscleGroupService.saveMuscleGroup(newMuscleGroup);
        return ResponseEntity.ok(newMuscleGroup);
    }

    @GetMapping
    public ResponseEntity<List<MuscleGroup>> getAllMuscleGroup() {
        List<MuscleGroup> muscleGroups = muscleGroupService.getAllMuscleGroup();
        return ResponseEntity.ok(muscleGroups);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMuscleGroup(@PathVariable Long id) {
        muscleGroupService.deleteMuscleGroup(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}")
    public ResponseEntity<MuscleGroup> updateMuscleGroup(@RequestBody MuscleGroup muscleGroup) {
        MuscleGroup muscleGroupUpdated = muscleGroupService.updateMuscleGroup(muscleGroup);
        return ResponseEntity.ok(muscleGroupUpdated);
    }
}
