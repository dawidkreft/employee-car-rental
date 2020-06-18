package pl.kreft.thesis.ecr.centralsystem.calendar.service;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovingHolidayServiceTest {

    @Autowired
    MovingHolidayService movingHolidayService;

    @AfterEach
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initialize db exception");
        }
    }

    @Test
    public void shouldReturnCorrectSundayEasterDatePerYear() {
        LocalDate easterDateIn2002 = movingHolidayService.calculateEasterSundayDate(2002);
        LocalDate easterDateIn2019 = movingHolidayService.calculateEasterSundayDate(2019);
        LocalDate easterDateIn2090 = movingHolidayService.calculateEasterSundayDate(2090);

        assertEquals(easterDateIn2002, LocalDate.of(2002, 3, 31));
        assertEquals(easterDateIn2019, LocalDate.of(2019, 4, 21));
        assertEquals(easterDateIn2090, LocalDate.of(2090, 4, 16));
    }

    @Test
    public void shouldReturnCorrectPentecostDatePerYear() {
        LocalDate pentecostIn2040 = movingHolidayService.calculatePentecostDate(2040);
        LocalDate pentecostIn2090 = movingHolidayService.calculatePentecostDate(2090);

        assertEquals(pentecostIn2040, LocalDate.of(2040, 5, 20));
        assertEquals(pentecostIn2090, LocalDate.of(2090, 6, 4));
    }

    @Test
    public void shouldReturnCorrectCorpusChristiDatePerYear() {
        LocalDate corpusChristiIn2018 = movingHolidayService.calculateCorpusChristiDate(2018);
        LocalDate corpusChristiIn2099 = movingHolidayService.calculateCorpusChristiDate(2099);

        assertEquals(corpusChristiIn2018, LocalDate.of(2018, 5, 31));
        assertEquals(corpusChristiIn2099, LocalDate.of(2099, 6, 11));
    }
}
