package ru.otus.atm.exceptions;

public class WithdrawalExceededVaultRemainderException extends RuntimeException {
    public WithdrawalExceededVaultRemainderException(String errorMessage) {
        super(errorMessage);
    }
}
