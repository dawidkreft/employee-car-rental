package pl.kreft.thesis.ecr.centralsystem.car.model;

public enum CarStatus {
    LOAN("Wypożyczony"),
    DAMAGED("Uszkodzony"),
    AVAILABLE("Dostępny"),
    IN_SERVICE("W serwisie");

    private final String displayValue;

    private CarStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
