package pl.kreft.thesis.ecr.centralsystem.exception;

public class EcrExceptionMessages {

    // User
    public static final String UserNotExistsException = "Użytkownik ne istnieje";

    // Rental
    public static final String EmployeeRentalException = "Pracownik może wypożyć tylko jeden pojazd jednoceśnie";
    public static final String RentalNotFoundException = "Nie znaleziono wypożyczenia";
    public static final String RentalCarException = "Nie można wypożyczyć wybranego pojazdu";
    public static final String RentalOnlyWorkDay = "Nie można wypożyczyć pojazdu w święta i w soboty.";
    public static final String RentalIncorrectDate= "Data zwrotu nie może być wcześniejsza niż wypożyczenia";
    public static final String IncorrectNumberKilometer= "Nie poprawna wartość kilometrów z licznika";
    public static final String IncorrectTraveledDistance= "Nie poprawna wartość przebytych kilometrów";
    public static final String IncorrectFuelLevel= "Nie poprawna wartość stanu paliwa";
}
