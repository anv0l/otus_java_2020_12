package ru.otus.atm.exceptions;

public class NotEnoughNotesInCartridgesException extends RuntimeException {
    public NotEnoughNotesInCartridgesException(String errorMessage) {
        super(errorMessage);
    }
}
