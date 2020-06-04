package pl.kreft.thesis.ecr.centralsystem.user.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User find(UUID id) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findByIdAndRemovedFalse(id);
        if (user.isPresent()) {
            log.info("Found user by id: " + id);
            return user.get();
        }
        throw new ObjectNotFoundException("Unable to locate user with id: " + id);
    }

    public User findByEmail(String email) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findByEmailAndRemovedFalse(email);
        if (user.isPresent()) {
            log.info("Found user by email: " + email);
            return user.get();
        }
        throw new ObjectNotFoundException("Unable to locate user with email: " + email);
    }

    public User save(User user) {
        Optional<User> userInDbOptional = userRepository.findByIdAndRemovedFalse(user.getId());
        User savedUser;
        if (userInDbOptional.isPresent()) {
            User oldUser = userInDbOptional.get();
            oldUser.setBoss(user.getBoss());
            oldUser.setEmail(user.getEmail());
            oldUser.setIsActive(user.getIsActive());
            oldUser.setName(user.getName());
            oldUser.setRemoved(user.getRemoved());
            oldUser.setPassword(user.getPassword()); // TODO
            oldUser.setRole(user.getRole());
            oldUser.setSurname(user.getSurname());
            savedUser = userRepository.save(oldUser);
            log.info("Update old car in db to : " + user.toString());
        } else {
            savedUser = userRepository.save(user);
            log.info("Saved new user in db : " + user.toString());
        }
        return savedUser;
    }

    public List<User> getAll() {
        log.info("Returning all users");
        List<User> allUsers = userRepository.findAll();
        allUsers = allUsers.stream()
                           .filter(item -> !item.getRemoved())
                           .collect(Collectors.toList());
        return allUsers;
    }

    public List<User> getAllActiveUser() {
        log.info("Returning all active users");
        List<User> allUsers = userRepository.findAll();
        allUsers = allUsers.stream()
                           .filter(item -> !item.getRemoved() && item.getIsActive())
                           .collect(Collectors.toList());
        return allUsers;
    }

    public void remove(UUID userId) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findByIdAndRemovedFalse(userId);
        if (user.isPresent()) {
            User userInDb = user.get();
            userInDb.setRemoved(true);
            userRepository.save(userInDb);
            log.info("Removed user by id: " + userId);
        } else {
            throw new ObjectNotFoundException("Unable to locate user with id: " + userId);
        }
    }

    public void disableUser(UUID userId) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findByIdAndRemovedFalse(userId);
        if (user.isPresent()) {
            User userInDb = user.get();
            userInDb.setIsActive(false);
            userRepository.save(userInDb);
            log.info("Removed user by id: " + userId);
        } else {
            throw new ObjectNotFoundException("Unable to locate user with id: " + userId);
        }
    }

}