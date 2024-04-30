package com.ossian.FitFlow.service;


import com.ossian.FitFlow.model.Material;
import com.ossian.FitFlow.repository.MaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


public interface MaterialService {

     Material saveMaterial(Material material);

     List<Material> getAllMaterial();

     void deleteMaterial(Long id);

    Material updateMaterial(Material material);

    List<Material> findByLastMantenainceDate() ;
    Material findById(Long id);
}

