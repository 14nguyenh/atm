package com.henry.atm.dto;

public record WithdrawPostResponse(String transactionId, String accountId, String type, double amount) {
}
