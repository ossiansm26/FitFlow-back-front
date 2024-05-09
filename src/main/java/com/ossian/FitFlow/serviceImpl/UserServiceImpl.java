package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.model.*;
import com.ossian.FitFlow.repository.*;
import com.ossian.FitFlow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoutineRepository routineRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private CommunityRepository communityRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User removeRoutineToUser(Long id, Long idRoutine) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Routine routine = routineRepository.findById(idRoutine)
                .orElseThrow(() -> new RuntimeException("routine not found"));

        user.getRoutinesAssociated().remove(routine);
        routine.getUserAdded().remove(user);

        routineRepository.save(routine);
        return userRepository.save(user);
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
       /* return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
        */
    }


    public void deleteUser(Long id) {
      User user = userRepository.findById(id)
              .orElseThrow(() -> new RuntimeException("User not found"));
        user.getPost().forEach(post -> post.setUser(null));
        user.getRoutinesAssociated().forEach(routine -> routine.getUserAdded().remove(user));
        user.getRoutinesCreated().forEach(routine -> routine.getUserCreated().remove(user));
        userRepository.deleteById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User addRoutineToUser(Long id, Long idRoutine) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Routine routine = routineRepository.findById(idRoutine)
                .orElseThrow(() -> new RuntimeException("Routine not found"));

        user.getRoutinesAssociated().add(routine);
        routine.getUserAdded().add(user);

        routineRepository.save(routine);
        return userRepository.save(user);
    }


    public User createRoutine(Long id, Routine routine) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getRoutinesCreated().add(routine);
        routine.getUserCreated().add(user);
        user.getRoutinesAssociated().add(routine);
        routine.getUserAdded().add(user);
        routineRepository.save(routine);
        return userRepository.save(user);
    }

    public List<User> findUserByAgeIsGreaterThan(Date age) {
        return userRepository.findAll().stream()
                .filter(user -> user.getAge().after(age))
                .collect(Collectors.toList());
    }
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Routine> getUserRoutines(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRoutinesAssociated();
    }


    public List<Routine> getUserCreatedRoutines(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRoutinesCreated();
    }


    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }


    public List<Achievement> getAllAchievements(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getAchievement();
    }


    public List<Post> findPostById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getPost();
    }


    public User addAchievementToUser(Long id, Long idAchievement) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Achievement achievement = achievementRepository.findById(idAchievement)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));

        user.getAchievement().add(achievement);
        achievement.getUser().add(user);

        achievementRepository.save(achievement);
        return userRepository.save(user);
    }


    public User removeAchievementToUser(Long id, Long idAchievement) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Achievement achievement = achievementRepository.findById(idAchievement)
                .orElseThrow(() -> new RuntimeException("Achievement not found"));

        user.getAchievement().remove(achievement);
        achievement.getUser().remove(user);

        achievementRepository.save(achievement);
        return userRepository.save(user);
    }


    public User deleteCreatedRoutine(Long id, Long idRoutine) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Routine routine = routineRepository.findById(idRoutine)
                .orElseThrow(() -> new RuntimeException("Routine not found"));

        user.getRoutinesCreated().remove(routine);
       routineRepository.delete(routine);
        return userRepository.save(user);
    }


    public List<Community> getCommunity(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getCommunityAssociated();
    }


    public User removeCommunityToUser(Long id, Long idCommunity) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Community community = communityRepository.findById(idCommunity)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        user.getCommunityAssociated().remove(community);
        community.getUserAdded().remove(user);
        communityRepository.save(community);
        return userRepository.save(user);
    }


    public User addCommunityToUser(Long id, Long idCommunity) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Community community = communityRepository.findById(idCommunity)
                .orElseThrow(() -> new RuntimeException("Community not found"));
        user.getCommunityAssociated().add(community);
        community.getUserAdded().add(user);
        communityRepository.save(community);
        return userRepository.save(user);
    }


}