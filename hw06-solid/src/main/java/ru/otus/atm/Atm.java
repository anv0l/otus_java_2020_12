package ru.otus.atm;

import ru.otus.atm.notes.BaseNote;

import java.util.Map;

public class Atm {
    private final Vault vault = new Vault();

    public Atm() {
    }

    public void addCartridge(CashCartridge cartridge) {
        this.vault.addCartridge(cartridge);
    }

    public Float getVaultRemainder() {
        return this.vault.getVaultRemainder();
    }

    public void withdraw(Float cash) {
        this.vault.withdraw(cash);
    }

    public void deposit(Map<BaseNote, Integer> cashMap) {
        for (Map.Entry<BaseNote, Integer> cash : cashMap.entrySet()) {
            this.vault.addNote(cash.getKey(), cash.getValue());
        }
    }
}
