package pl.kreft.thesis.ecr.centralsystem.inspection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kreft.thesis.ecr.centralsystem.inspection.model.Inspection;

import java.util.UUID;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, UUID> {
}

