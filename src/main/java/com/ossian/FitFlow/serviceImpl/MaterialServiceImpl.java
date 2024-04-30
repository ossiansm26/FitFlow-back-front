package com.ossian.FitFlow.serviceImpl;


import com.ossian.FitFlow.model.Material;
import com.ossian.FitFlow.repository.MaterialRepository;
import com.ossian.FitFlow.service.MaterialService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    MaterialRepository materialRepository;


    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }

    public List<Material> getAllMaterial(){return materialRepository.findAll();};

    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }

    public Material updateMaterial(Material material) {
        return materialRepository.save(material);
    }

    public List<Material> findByLastMantenainceDate() {
        return materialRepository.findAll().stream()
                .filter(material -> material.getLastMaintence() != null)
                .sorted(Comparator.comparing(Material::getLastMaintence))
                .toList();
    }

    public Material findById(Long id) {
        return materialRepository.findById(id).orElseThrow(()->
                new RuntimeException("Material not found"));
    }
}

