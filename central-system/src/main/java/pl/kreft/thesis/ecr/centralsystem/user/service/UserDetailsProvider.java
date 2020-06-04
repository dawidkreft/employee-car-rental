package pl.kreft.thesis.ecr.centralsystem.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.repository.UserRepository;

import java.util.Optional;

import static pl.kreft.thesis.ecr.centralsystem.user.service.UserDetailsMapper.getUserDetails;

@Slf4j
@Service
public class UserDetailsProvider implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmailAndRemovedFalse(email);
        return getUserDetails(user.orElseThrow());
    }
}
