package ru.otus.atm.exceptions;

public class AtmException extends RuntimeException {
    private final ErrorCode errorCode;

    public AtmException(String errorMessage, ErrorCode errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
