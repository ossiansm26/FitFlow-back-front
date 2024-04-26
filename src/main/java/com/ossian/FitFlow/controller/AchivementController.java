package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.Achivement;
import com.ossian.FitFlow.serviceImpl.AchivementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achivement")
public class AchivementController {
    @Autowired
    private AchivementServiceImpl achivementService;

    @GetMapping
    public ResponseEntity<List<Achivement>> getAllAchivement() {
        List<Achivement> achivement = achivementService.getAllAchivement();
        return ResponseEntity.ok(achivement);
    }
}
