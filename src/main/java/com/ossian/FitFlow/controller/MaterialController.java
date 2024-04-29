package com.ossian.FitFlow.controller;


import com.ossian.FitFlow.model.Material;

import com.ossian.FitFlow.serviceImpl.MaterialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material")
@CrossOrigin(origins = "http://localhost:8080")
public class MaterialController {
    @Autowired
    private MaterialServiceImpl materialService;

    @PostMapping
    public ResponseEntity<Material> createRoutine(@RequestBody Material material) {
        Material newMaterial = materialService.saveMaterial(material);
        return ResponseEntity.ok(newMaterial);
    }

    @GetMapping
    public ResponseEntity<List<Material>> getAllRoutine() {
        List<Material> Routine = materialService.getAllMaterial();
        return ResponseEntity.ok(Routine);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}")
    public ResponseEntity<Material> updateMaterial(@RequestBody Material material) {
        Material materialUpdated = materialService.updateMaterial(material);
        return ResponseEntity.ok(materialUpdated);
    }
    @GetMapping("/findByLastMantenainceDate")
    public ResponseEntity<List<Material>> findByLastMantenainceDate() {
        List<Material> material = materialService.findByLastMantenainceDate();
        return ResponseEntity.ok(material);
    }
}
