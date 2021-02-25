package ru.otus.atm.exceptions;

public class NoSuchCartridgeForNoteException extends RuntimeException {
    public NoSuchCartridgeForNoteException(String errorMessage) {
        super(errorMessage);
    }
}
