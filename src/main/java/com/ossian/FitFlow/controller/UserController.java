package com.ossian.FitFlow.controller;

import com.ossian.FitFlow.model.Routine;
import com.ossian.FitFlow.model.User;
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

    @PostMapping("/createUser")
    public ResponseEntity<User> createUsuario(@RequestBody User user) {
        if (userService.findUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }
        User newUser = userService.saveUser(user);
        return ResponseEntity.ok(newUser);
    }
    @GetMapping("/GetById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> user = userService.getAllUser();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
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
    @PutMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User userLogin = userService.findUserByEmail(user.getEmail());
        if (userLogin.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(userLogin);
        } else {
            return ResponseEntity.badRequest().build();
        }
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
}
