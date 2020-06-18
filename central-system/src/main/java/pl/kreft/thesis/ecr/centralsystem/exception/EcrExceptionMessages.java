package pl.kreft.thesis.ecr.centralsystem.exception;

public class EcrExceptionMessages {

    // User
    public static final String UserNotExistsException = "Użytkownik ne istnieje";

    // Rental
    public static final String EmployeeRentalException = "Pracownik może wypożyć tylko jeden pojazd jednoceśnie";
    public static final String RentalNotFoundException = "Nie znaleziono wypożyczenia";
    public static final String RentalCarException = "Nie można wypożyczyć wybranego pojazdu";
}
