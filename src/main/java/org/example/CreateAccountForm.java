package org.example;

public class CreateAccountForm {
    public CreateAccountForm(String accountNumber, String holderName) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
    }

    private String accountNumber;
    private String holderName;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }
}