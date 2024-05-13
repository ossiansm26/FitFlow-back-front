package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.serviceImpl.ExerciceLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exerciceLog")
@CrossOrigin(origins = "http://localhost:8080")
public class ExerciceLogController {
    @Autowired
    private ExerciceLogServiceImpl exerciceLogService;


}
