package pl.kreft.thesis.ecr.centralsystem.rental.service.config;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kreft.thesis.ecr.centralsystem.rental.model.dto.UsersRentalHistoryResponse;

import java.time.LocalDateTime;
import java.util.List;

@Component
@NoArgsConstructor
public class RentalReportComponent {

    String NEW_LINE_SEPARATOR = "\n";
    String NO_DATA_VALUE = "Brak danych";
    String COLUMN_SEPARATOR = ";";
    String HEADER = "Lp;Imie;Nazwisko;Marka pojazdu;Model;Typ;Stan pojazdu;Przebyty dystans;Start podróży;Koniec podróży;Data aplikacji";

    public String prepareReport(List<UsersRentalHistoryResponse> rentals) {
        StringBuilder results = new StringBuilder().append(HEADER).append(NEW_LINE_SEPARATOR);
        rentals.forEach(item -> {
            results.append(item.getOrdinalNumber())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getLenderName())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getLenderSurname())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getCarBrand())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getCarModel())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getCarType().getDisplayValue())
                    .append(COLUMN_SEPARATOR)
                    .append(checkIfNotNullOrReturnNoDataValue(item.getCarCondition()))
                    .append(COLUMN_SEPARATOR)
                    .append(convertDistance(item.getDistanceTraveled()))
                    .append(COLUMN_SEPARATOR)
                    .append(item.getPlannedRentalStart())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getPlannedRentalEnd())
                    .append(COLUMN_SEPARATOR)
                    .append(prepareApplicationDate(item.getApplicationDate()))
                    .append(NEW_LINE_SEPARATOR);
        });
        return results.toString();
    }

    private String convertDistance(Long distanceTraveled) {
        if (distanceTraveled == null) {
            return NO_DATA_VALUE;
        }
        return distanceTraveled.toString();
    }

    private String prepareApplicationDate(LocalDateTime applicationDate) {
        if (applicationDate == null) {
            return NO_DATA_VALUE;
        }
        return applicationDate.toString();
    }

    private String checkIfNotNullOrReturnNoDataValue(String value) {
        if (value == null) {
            return NO_DATA_VALUE;
        }
        return value;
    }
}
