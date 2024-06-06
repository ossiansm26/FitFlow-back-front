package com.ossian.FitFlow.service;

import com.ossian.FitFlow.model.*;


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
     List<Achievement> getAllAchievements(Long id);
     List<Post> findPostById(Long id);
     User addAchievementToUser(Long id, Long idAchievement);
     User removeAchievementToUser(Long id, Long idAchievement);
     User deleteCreatedRoutine(Long id, Long idRoutine);
     List<Community> getCommunity(Long id);
     User removeCommunityToUser(Long id, Long idCommunity);
     User addCommunityToUser(Long id, Long idCommunity);
     User removeExerciseLogToUser(Long id, Long idExerciseLog);
     User addExerciseLogToUser(Long id, ExerciceLog exerciseLog);
     List<ExerciceLog> getExerciseLog(Long id);


    User authenticate(String email, String password);
}
