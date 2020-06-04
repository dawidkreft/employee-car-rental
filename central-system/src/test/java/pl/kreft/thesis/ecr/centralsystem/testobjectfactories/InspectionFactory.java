package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.inspection.model.Inspection;
import pl.kreft.thesis.ecr.centralsystem.inspection.model.VisualCondition;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import java.time.Instant;

public class InspectionFactory {
    public static Inspection getRandomInspection(User inspector, Car car) {
        return Inspection.builder()
                         .carCondition(VisualCondition.GOOD)
                         .inspectedCar(car)
                         .description("OK")
                         .inspector(inspector)
                         .inspectionDate(Instant.now())
                         .tireCondition(VisualCondition.GOOD)
                         .creationDate(Instant.now())
                         .removed(false)
                         .build();
    }
}
