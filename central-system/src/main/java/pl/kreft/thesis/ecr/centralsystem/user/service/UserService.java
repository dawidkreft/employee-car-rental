package pl.kreft.thesis.ecr.centralsystem.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionMessages;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserDetailsProvider;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserRequest;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserResponse;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserRole;
import pl.kreft.thesis.ecr.centralsystem.user.repository.UserRepository;
import pl.kreft.thesis.ecr.centralsystem.user.service.exception.UserIncorrectEmail;
import pl.kreft.thesis.ecr.centralsystem.user.service.exception.UserIncorrectPassword;
import pl.kreft.thesis.ecr.centralsystem.user.service.exception.UserNotExistsException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.kreft.thesis.ecr.centralsystem.common.Validators.validateEmail;
import static pl.kreft.thesis.ecr.centralsystem.common.Validators.validatePassword;
import static pl.kreft.thesis.ecr.centralsystem.config.GlobalConfiguration.defaultTimeZone;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse getUser(UUID userId) {
        return mapToUserResponse(find(userId));
    }

    public User find(UUID id) {
        Optional<User> user = userRepository.findByIdAndRemovedFalse(id);
        if (user.isPresent()) {
            log.info("Found user by id: " + id);
            return user.get();
        }
        throw new UserNotExistsException(
                new ErrorMessage(EcrExceptionMessages.userNotExistsException));
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmailAndRemovedFalse(email);
        if (user.isPresent()) {
            log.info("Found user by email: " + email);
            return user.get();
        }
        throw new UserNotExistsException(
                new ErrorMessage(EcrExceptionMessages.userNotExistsException));
    }

    public List<UserResponse> getAdmins() {
        return userRepository.findAllByRoleAndRemovedFalse(UserRole.ADMIN)
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addNew(UserRequest userRequest, UserDetailsProvider boos) {
        log.info("Method addNew called by : " + boos.toString());
        if (userRequest.getPassword().equals(userRequest.getRePassword())) {
            validateEmailOrThrow(userRequest.getEmail());
            validatePasswordOrThrow(userRequest.getPassword());
            User newUser = User.builder()
                    .id(UUID.randomUUID())
                    .name(userRequest.getName())
                    .surname(userRequest.getSurname())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .bossId(boos.getId())
                    .email(userRequest.getEmail())
                    .isActive(true)
                    .creationDate(Instant.now())
                    .removed(false)
                    .role(UserRole.EMPLOYEE)
                    .build();
            save(newUser);
            return;
        }
        throw new UserIncorrectPassword(
                new ErrorMessage(EcrExceptionMessages.userIncorrectPasswords));
    }

    public User save(User user) {
        Optional<User> userInDbOptional = userRepository.findByIdAndRemovedFalse(user.getId());
        User savedUser;
        if (userInDbOptional.isPresent()) {
            User oldUser = userInDbOptional.get();
            oldUser.setBossId(user.getBossId());
            oldUser.setEmail(user.getEmail());
            oldUser.setIsActive(user.getIsActive());
            oldUser.setName(user.getName());
            oldUser.setRemoved(user.getRemoved());
            oldUser.setPassword(user.getPassword());
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

    public List<UserResponse> getAllUsersByBossId(UUID bossId) {
        log.info("Method getAllUsersByBossId called for boos with id: " + bossId.toString());
        return userRepository.findAllByBossIdAndRemovedIsFalseAndIsActiveTrue(bossId)
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public List<User> getAllActiveUser() {
        log.info("Returning all active users");
        List<User> allUsers = userRepository.findAll();
        allUsers = allUsers.stream()
                .filter(item -> !item.getRemoved() && item.getIsActive())
                .collect(Collectors.toList());
        return allUsers;
    }

    @Transactional
    public void remove(UUID userId) {
        User user = find(userId);
        user.setRemoved(true);
        userRepository.save(user);
        log.info("Removed user by id: " + userId);
    }

    public void disableUser(UUID userId) {
        User user = find(userId);
        user.setIsActive(false);
        userRepository.save(user);
        log.info("Removed user by id: " + userId);
    }

    public UserResponse mapToUserResponse(User user) {
        if (user.getBossId() == null) {
            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .surname(user.getSurname())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .creationDate(LocalDateTime.ofInstant(user.getCreationDate(),
                            ZoneId.of(defaultTimeZone)))
                    .build();
        }

        User boos = find(user.getBossId());
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .creationDate(LocalDateTime.ofInstant(user.getCreationDate(),
                        ZoneId.of(defaultTimeZone)))
                .bossId(user.getBossId())
                .boosName(boos.getName())
                .boosSurname(boos.getSurname())
                .build();
    }

    private void validatePasswordOrThrow(String password) {
        if (!validatePassword(password)) {
            throw new UserIncorrectPassword(
                    new ErrorMessage(EcrExceptionMessages.userIncorrectSchemaPassword));
        }
    }

    private void validateEmailOrThrow(String email) {
        if (!validateEmail(email)) {
            throw new UserIncorrectEmail(
                    new ErrorMessage(EcrExceptionMessages.userIncorrectEmail));
        }
    }
}