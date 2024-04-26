/**
 * Represents the transaction amount with amount, currency, and debit/credit indicator.
 * This class captures the data related to a transaction amount.
 */

 package dev.com.models;

import java.util.Objects;

public class Amount {
    private String amount;
    private String currency;
    private DebitCredit debitOrCredit;

    public Amount() {
    }

    public Amount(String amount, String currency, DebitCredit debitOrCredit) {
        this.amount = amount;
        this.currency = currency;
        this.debitOrCredit = debitOrCredit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public DebitCredit getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(DebitCredit debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount1 = (Amount) o;
        return Objects.equals(amount, amount1.amount) &&
                Objects.equals(currency, amount1.currency) &&
                debitOrCredit == amount1.debitOrCredit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency, debitOrCredit);
    }
}
