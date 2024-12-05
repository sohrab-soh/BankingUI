package org.example;


public final class MoneyTransferForm {
    private final String sourceAccountNumber;
    private final String destinationAccountNumber;
    private final String amount;

    public MoneyTransferForm(String sourceAccountNumber, String destinationAccountNumber, String amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.amount = amount;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public String getAmount() {
        return amount;
    }
}
    