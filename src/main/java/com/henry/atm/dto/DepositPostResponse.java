package com.henry.atm.dto;

public record DepositPostResponse(String transactionId, String accountId, String type, double amount) {
}
