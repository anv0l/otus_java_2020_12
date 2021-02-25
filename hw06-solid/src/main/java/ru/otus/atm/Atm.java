package ru.otus.atm;

import ru.otus.atm.notes.BaseNote;

import java.util.Map;

public class Atm {
    private final Vault vault = new Vault();

    public Atm() {
    }

    public Vault getVault() {
        return this.vault;
    }

    public void withdraw(Float cash) {
        getVault().checkAvailableCash(cash);
        getVault().withdraw(cash);
    }

    public void deposit(Map<BaseNote, Integer> cashMap) {
        for (Map.Entry<BaseNote, Integer> cash : cashMap.entrySet()) {
            this.getVault().addNote(cash.getKey(), cash.getValue());
        }
    }
}
