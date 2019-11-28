package pl.kreft.thesis.ecr.centralsystem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
