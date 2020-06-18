package pl.kreft.thesis.ecr.centralsystem.calendar.service;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.calendar.model.DayType;

import static org.junit.Assert.assertEquals;
import static pl.kreft.thesis.ecr.centralsystem.calendar.service.HolidayCalendarServiceTestHelper.christmasDate;
import static pl.kreft.thesis.ecr.centralsystem.calendar.service.HolidayCalendarServiceTestHelper.easterDate;
import static pl.kreft.thesis.ecr.centralsystem.calendar.service.HolidayCalendarServiceTestHelper.mondayDate;
import static pl.kreft.thesis.ecr.centralsystem.calendar.service.HolidayCalendarServiceTestHelper.saturdayDate;
import static pl.kreft.thesis.ecr.centralsystem.calendar.service.HolidayCalendarServiceTestHelper.sundayDate;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HolidayCalendarServiceTest {

    @Autowired
    HolidayCalendarService holidayCalendarService;

    @AfterEach
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initialize db exception");
        }
    }

    @Test
    public void shouldReturnHoliday() {
        assertEquals(holidayCalendarService.checkDay(easterDate), DayType.HOLIDAY);
        assertEquals(holidayCalendarService.checkDay(christmasDate), DayType.HOLIDAY);
        assertEquals(holidayCalendarService.checkDay(sundayDate), DayType.HOLIDAY);
    }

    @Test
    public void shouldReturnWorkDay() {
        assertEquals(holidayCalendarService.checkDay(mondayDate), DayType.WORKDAY);
    }

    @Test
    public void shouldReturnSaturday() {
        assertEquals(holidayCalendarService.checkDay(saturdayDate), DayType.SATURDAY);
    }
}
