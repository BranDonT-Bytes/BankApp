package com.bankapp.dto;

public class TransferRequest {

    private String username;
    private Double amount;

    public TransferRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}