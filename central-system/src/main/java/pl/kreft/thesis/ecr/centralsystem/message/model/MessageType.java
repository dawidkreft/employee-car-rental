package pl.kreft.thesis.ecr.centralsystem.message.model;

public enum MessageType {
    WEBSITE_PROBLEM("Problem dotyczący serwisu www"),
    COMPANY_PROBLEM("Problem dotyczący firmy");

    private final String displayValue;

    private MessageType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
