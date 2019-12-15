package pl.kreft.thesis.ecr.centralsystem.car.model;

public enum CarType {
    CAR("Samochód osobowy"),
    TRACK("Samochód ciężarowy"),
    VAN("Samochód typu VAN"),
    PREMIUM_CAR("Samochód premium");

    private final String displayValue;

    private CarType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
