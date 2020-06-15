package pl.kreft.thesis.ecr.centralsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByIdAndRemovedFalse(UUID id);

    Optional<User> findByEmailAndRemovedFalse(String email);

    List<User> findAllByRoleAndRemovedFalse(UserRole role);
}
