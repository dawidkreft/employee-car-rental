package pl.kreft.thesis.ecr.centralsystem.calendar.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class MovingHolidayService {

    private static final int INTERVAL_BETWEEN_EASTER_SUNDAY_AND_EASTER_MONDAY = 1;
    private static final int INTERVAL_BETWEEN_EASTER_SUNDAY_AND_PENTECOST = 49;
    private static final int INTERVAL_BETWEEN_EASTER_SUNDAY_AND_CORPUS_CHRISTI = 60;

    protected LocalDate calculateEasterSundayDate(int year) {
        int century = year / 100;
        int decimalYears = year % 100;
        int firstParameter =
                (19 * (year % 19) + century - (century / 4) - ((8 * century + 13) / 25) + 15) % 30;
        int secondParameter = ((year % 19) + 11 * firstParameter) / 319;
        int thirdParameter =
                (2 * (century % 4) + 2 * (decimalYears / 4) - (decimalYears % 4) - firstParameter
                        + secondParameter + 32) % 7;

        int month = (firstParameter - secondParameter + thirdParameter + 90) / 25;
        int day = (firstParameter - secondParameter + thirdParameter + month + 19) % 32;
        return LocalDate.of(year, month, day);
    }

    protected LocalDate calculateEasterMondayDate(int year) {
        return calculateEasterSundayDate(year).plusDays(
                INTERVAL_BETWEEN_EASTER_SUNDAY_AND_EASTER_MONDAY);
    }

    protected LocalDate calculatePentecostDate(int year) {
        return calculateEasterSundayDate(year).plusDays(
                INTERVAL_BETWEEN_EASTER_SUNDAY_AND_PENTECOST);
    }

    protected LocalDate calculateCorpusChristiDate(int year) {
        return calculateEasterSundayDate(year).plusDays(
                INTERVAL_BETWEEN_EASTER_SUNDAY_AND_CORPUS_CHRISTI);
    }

    public List<LocalDate> getAllMovingHolidays(int year) {
        List<LocalDate> movingHolidayList = new LinkedList<>();
        movingHolidayList.add(calculateEasterSundayDate(year));
        movingHolidayList.add(calculateEasterMondayDate(year));
        movingHolidayList.add(calculatePentecostDate(year));
        movingHolidayList.add(calculateCorpusChristiDate(year));
        return movingHolidayList;
    }
}
