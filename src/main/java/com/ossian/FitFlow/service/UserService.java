package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.Routine;
import com.ossian.FitFlow.model.User;


import java.util.Date;
import java.util.List;


public interface UserService {
     User findById(Long id);

     User removeRoutineToUser(Long id, Long idRoutine);
     User saveUser(User user);
     List<User> getAllUser();
     void deleteUser(Long id);
     User updateUser(User user);
     User addRoutineToUser(Long id, Long idRoutine);
     User createRoutine(Long id, Routine routine);
     List<User> findUserByAgeIsGreaterThan(Date age);
     User findUserByEmail(String email);
    List<Routine> getUserRoutines(Long id);
    List<Routine> getUserCreatedRoutines(Long id);

     User findUserByName(String name);
}
