package com.henry.atm.dto;

import java.time.LocalDateTime;

public record TransactionGetResponse(String transactionId, String accountId, String type, double amount, LocalDateTime timestamp) {
}
