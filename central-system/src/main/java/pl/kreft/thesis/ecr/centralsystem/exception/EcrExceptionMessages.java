package pl.kreft.thesis.ecr.centralsystem.exception;

public class EcrExceptionMessages {

    // User
    public static final String userNotExistsException = "Użytkownik ne istnieje";
    public static final String userIncorrectPasswords = "Podane hasła nie pasują do siebie";
    public static final String userIncorrectSchemaPassword = "Nie poprawny format hasła";
    public static final String userIncorrectEmail = "Podany email jest nie poprawny";

    // Rental
    public static final String employeeRentalException = "Pracownik może wypożyć tylko jeden pojazd jednoceśnie";
    public static final String rentalNotFoundException = "Nie znaleziono wypożyczenia";
    public static final String rentalCarException = "Nie można wypożyczyć wybranego pojazdu";
    public static final String rentalOnlyWorkDay = "Nie można wypożyczyć pojazdu w święta i w soboty.";
    public static final String rentalIncorrectDate = "Data zwrotu nie może być wcześniejsza niż wypożyczenia";
    public static final String incorrectNumberKilometer = "Nie poprawna wartość kilometrów z licznika";
    public static final String incorrectTraveledDistance = "Nie poprawna wartość przebytych kilometrów";
    public static final String incorrectFuelLevel = "Nie poprawna wartość stanu paliwa";
}
