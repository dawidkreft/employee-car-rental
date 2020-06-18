package pl.kreft.thesis.ecr.centralsystem.calendar.service.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "holiday-calendar")
public class PermanentHolidayConfiguration {

    private List<HolidayDate> permanentHolidays;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HolidayDate {
        private int day;
        private int month;

        public boolean equals(LocalDate date) {
            return day == date.getDayOfMonth() && month == date.getMonthValue();
        }
    }
}
