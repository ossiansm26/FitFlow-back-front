package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.Routine;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<User> createUsuario(@RequestBody User user) {
        User newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> user = userService.getAllUser();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User userUpdated = userService.updateUser(user);
        return ResponseEntity.ok(userUpdated);
    }

    @PostMapping("/{id}/addRoutine/{idRoutine}")
    public ResponseEntity<User> addRoutineToUser(@PathVariable Long id,
                                                 @PathVariable Long idRoutine) {
        User user = userService.addRoutineToUser(id, idRoutine);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/removeRoutine/{idRoutine}")
    public ResponseEntity<User> removeRoutineToUser(@PathVariable Long id,
                                                    @PathVariable Long idRoutine) {
        User user = userService.removeRoutineToUser(id, idRoutine);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/{id}/createRoutine/{description}/{category}")
    public ResponseEntity<User> createRoutine(@PathVariable Long id,
                                              @PathVariable String description ,
                                              @PathVariable String category) {
        Routine routine = new Routine();
        routine.setDescription(description);
        routine.setCategory(category);
        User user = userService.createRoutine(id,routine);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findUserByAgeIsGreaterThan/{age}")
    public ResponseEntity<List<User>> findUserByAgeIsGreaterThan(@PathVariable Integer age) {
        List<User> user = userService.findUserByAgeIsGreaterThan(age);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findByEmail/{email}")
    public User findByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }
}
