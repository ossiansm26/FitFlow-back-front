package com.ossian.FitFlow.controller;


import com.ossian.FitFlow.model.Material;

import com.ossian.FitFlow.serviceImpl.MaterialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/material")
@CrossOrigin(origins = "http://localhost:8080")
public class MaterialController {
    @Autowired
    private MaterialServiceImpl materialService;

    @PostMapping
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
        Material newMaterial = materialService.saveMaterial(material);
        return ResponseEntity.ok(newMaterial);
    }

    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterial() {
        List<Material> material= materialService.getAllMaterial();
        return ResponseEntity.ok(material);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Material> updateMaterial(@RequestBody Material material) {
        Material materialUpdated = materialService.updateMaterial(material);
        return ResponseEntity.ok(materialUpdated);
    }
    @GetMapping("/findByLastMantenainceDate")
    public ResponseEntity<List<Material>> findByLastMantenainceDate() {
        List<Material> material = materialService.findByLastMantenainceDate();
        return ResponseEntity.ok(material);
    }
    @GetMapping("/buscarID/{id}")
    public ResponseEntity<Material> findById(@PathVariable Long id) {
        Material material = materialService.findById(id);
        return ResponseEntity.ok(material);
    }
}
