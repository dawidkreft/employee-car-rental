package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.inspection.model.Inspection;
import pl.kreft.thesis.ecr.centralsystem.inspection.model.VisualCondition;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import java.time.Instant;

public class InspectionFactory {
    public static Inspection getRandomInspection(User inspector, Car car){
        return new Inspection(
                inspector,
                car,
                Instant.now(),
                "OK",
                VisualCondition.GOOD,
                VisualCondition.GREAT,
                Instant.now(),
                false
        );
    }
}
