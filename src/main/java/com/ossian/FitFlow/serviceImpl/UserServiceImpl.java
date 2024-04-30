package com.ossian.FitFlow.serviceImpl;

import com.ossian.FitFlow.model.Routine;
import com.ossian.FitFlow.model.User;
import com.ossian.FitFlow.repository.PostRepository;
import com.ossian.FitFlow.repository.RoutineRepository;
import com.ossian.FitFlow.repository.UserRepository;
import com.ossian.FitFlow.service.UserService;
import jakarta.transaction.Transactional;
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
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.getRoutinesCreated().add(routine);
        routine.getUserCreated().add(user);

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




}