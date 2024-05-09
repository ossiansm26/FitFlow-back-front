package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.Achievement;
import com.ossian.FitFlow.serviceImpl.AchievementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievement")
@CrossOrigin(origins = "http://localhost:8080")
public class AchievementController {
    @Autowired
    private AchievementServiceImpl achievementService;


    @GetMapping("/getAll")
    public ResponseEntity<List<Achievement>> getAllAchievement() {
        List<Achievement> achievement = achievementService.getAllAchievement();
        return ResponseEntity.ok(achievement);
    }
    @GetMapping("/getAchievementById/{id}")
    public ResponseEntity<Achievement> getAchievementById(@PathVariable Long id) {
        Achievement achievement = achievementService.getAchievementById(id);
        return ResponseEntity.ok(achievement);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        achievementService.deleteAchievement(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Achievement> updateAchievement(@PathVariable Long id, @RequestBody Achievement achievement) {
        Achievement updatedAchievement = achievementService.updateAchievement(id, achievement);
        return ResponseEntity.ok(updatedAchievement);
    }
    @PostMapping("/add")
    public ResponseEntity<Achievement> addAchievement(@RequestBody Achievement achievement) {
        Achievement newAchievement = achievementService.addAchievement(achievement);
        return ResponseEntity.ok(newAchievement);
    }

}
