package pl.kreft.thesis.ecr.centralsystem.calendar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kreft.thesis.ecr.centralsystem.calendar.model.DayType;
import pl.kreft.thesis.ecr.centralsystem.calendar.service.config.PermanentHolidayConfiguration;

import java.time.LocalDate;

@Service
public class HolidayCalendarService {

    private static int VALUE_OF_SUNDAY = 7;
    private static int VALUE_OF_SATURDAY = 6;

    private PermanentHolidayConfiguration permanentHolidayConfiguration;
    private MovingHolidayService movingHolidayService;

    @Autowired
    public HolidayCalendarService(PermanentHolidayConfiguration permanentHolidayConfiguration,
            MovingHolidayService movingHolidayService) {
        this.permanentHolidayConfiguration = permanentHolidayConfiguration;
        this.movingHolidayService = movingHolidayService;
    }

    public DayType checkDay(LocalDate date) {
        if (checkIfIsMovingHoliday(date) || checkIfIsPermanentHoliday(date)
                || date.getDayOfWeek().getValue() == VALUE_OF_SUNDAY) {
            return DayType.HOLIDAY;
        } else if (date.getDayOfWeek().getValue() == VALUE_OF_SATURDAY) {
            return DayType.SATURDAY;
        }
        return DayType.WORKDAY;
    }

    private boolean checkIfIsMovingHoliday(LocalDate date) {
        return movingHolidayService.getAllMovingHolidays(date.getYear())
                                   .stream()
                                   .anyMatch(holidayDate -> holidayDate.isEqual(date));
    }

    private boolean checkIfIsPermanentHoliday(LocalDate date) {
        return permanentHolidayConfiguration.getPermanentHolidays()
                                            .stream()
                                            .anyMatch(holidayDate -> holidayDate.equals(date));
    }
}