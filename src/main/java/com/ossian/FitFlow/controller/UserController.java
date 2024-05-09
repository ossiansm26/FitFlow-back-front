package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.*;
import com.ossian.FitFlow.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> user = userService.getAllUser();
        return ResponseEntity.ok(user);
    }
    @GetMapping("/getAllAchievementsById/{id}")
    public ResponseEntity<List<Achievement>> getAllAchievements(@PathVariable Long id) {
        List<Achievement> user = userService.getAllAchievements(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/findPostById/{id}")
    public ResponseEntity<List<Post>> findPostById(@PathVariable Long id) {
        List<Post> posts = userService.findPostById(id);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/getUserRoutines/{id}")
    public ResponseEntity<List<Routine>> getUserRoutines(@PathVariable Long id) {
        List<Routine> routine = userService.getUserRoutines(id);
        return ResponseEntity.ok(routine);
    }
    @GetMapping("/getUserCreatedRoutines/{id}")
    public ResponseEntity<List<Routine>> getUserCreatedRoutines(@PathVariable Long id) {
        List<Routine> routine = userService.getUserCreatedRoutines(id);
        return ResponseEntity.ok(routine);
    }
    @GetMapping("/findUserByAgeIsGreaterThan/{age}")
    public ResponseEntity<List<User>> findUserByAgeIsGreaterThan(@PathVariable Date age) {
        List<User> user = userService.findUserByAgeIsGreaterThan(age);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findByEmail/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }
    @GetMapping("/getCommunity/{id}")
    public ResponseEntity<List<Community>> getCommunity(@PathVariable Long id) {
        List<Community> community = userService.getCommunity(id);
        return ResponseEntity.ok(community);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    @DeleteMapping("/{id}/removeCommunity/{idCommunity}")
    public ResponseEntity<User> removeCommunityToUser(@PathVariable Long id,
                                                      @PathVariable Long idCommunity) {
        User user = userService.removeCommunityToUser(id, idCommunity);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/{id}/removeRoutine/{idRoutine}")
    public ResponseEntity<User> removeRoutineToUser(@PathVariable Long id,
                                                    @PathVariable Long idRoutine) {
        User user = userService.removeRoutineToUser(id, idRoutine);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/{id}/removeAchievement/{idAchievement}")
    public ResponseEntity<User> removeAchievementToUser(@PathVariable Long id,
                                                       @PathVariable Long idAchievement) {
        User user = userService.removeAchievementToUser(id, idAchievement);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User userUpdated = userService.updateUser(user);
        return ResponseEntity.ok(userUpdated);
    }
    @PutMapping("/{id}/addRoutine/{idRoutine}")
    public ResponseEntity<User> addRoutineToUser(@PathVariable Long id,
                                                 @PathVariable Long idRoutine) {
        User user = userService.addRoutineToUser(id, idRoutine);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/{id}/deleteRoutine/{idRoutine}")
    public ResponseEntity<User> deleteRoutine(@PathVariable Long id,
                                              @PathVariable Long idRoutine) {
        User user = userService.deleteCreatedRoutine(id, idRoutine);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/{id}/addAchievement/{idAchievement}")
    public ResponseEntity<User> addAchievementToUser(@PathVariable Long id,
                                                    @PathVariable Long idAchievement) {
        User user = userService.addAchievementToUser(id, idAchievement);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/addCommunity/{idCommunity}")
    public ResponseEntity<User> addCommunityToUser(@PathVariable Long id,
                                                   @PathVariable Long idCommunity) {
        User user = userService.addCommunityToUser(id, idCommunity);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User userLogin = userService.findUserByEmail(user.getEmail());
        if (userLogin.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(userLogin);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/createRoutine/{id}")
    public ResponseEntity<User> createRoutine(@PathVariable Long id,@RequestBody Routine routine) {
        User user = userService.createRoutine(id,routine);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/createUser")
    public ResponseEntity<User> createUsuario(@RequestBody User user) {
        if (userService.findUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }
        User newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }



}
