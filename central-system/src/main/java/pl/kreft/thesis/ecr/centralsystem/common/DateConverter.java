package pl.kreft.thesis.ecr.centralsystem.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static pl.kreft.thesis.ecr.centralsystem.config.GlobalConfiguration.defaultTimeZone;

public class DateConverter {

    public static LocalDateTime convertInstantToLocalDateTime(Instant instant, String timeZone) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.of(defaultTimeZone));
    }
}