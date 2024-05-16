package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.serviceImpl.SetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/set")
@CrossOrigin(origins = "http://localhost:8080")
public class SetController {
    @Autowired
    private SetServiceImpl setService;

}
