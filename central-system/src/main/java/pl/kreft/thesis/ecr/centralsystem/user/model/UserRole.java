package pl.kreft.thesis.ecr.centralsystem.user.model;

public enum UserRole {
    ADMIN("Administrator"),
    EMPLOYEE("Pracownik"),
    BOSS("Dyrektor"),
    DISPATCHER("Dyspozytor");

    private final String displayValue;

    private UserRole(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
