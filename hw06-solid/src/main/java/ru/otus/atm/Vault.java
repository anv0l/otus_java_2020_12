package ru.otus.atm;

import ru.otus.atm.exceptions.*;
import ru.otus.atm.notes.Banknotes;

import java.util.*;

public class Vault {
    private final Set<CashCartridge> vault;

    public Vault() {
        // reverse sort by note value
        this.vault = new TreeSet<>((o1, o2) -> o2.getNoteType().getValue().compareTo(o1.getNoteType().getValue()));
    }

    public void withdraw(Float cash) {
        checkAvailableCash(cash);
        Map<CashCartridge, Integer> withdrawalMap = getWithdrawalMap(cash);
        checkIfThereEnoughNotesInCartridges(withdrawalMap, cash);

        for (Map.Entry<CashCartridge, Integer> withdrawal : withdrawalMap.entrySet()) {
            removeNote(withdrawal.getKey().getNoteType(), withdrawal.getValue());
        }
    }

    public void addCartridge(CashCartridge cartridge) {
        CashCartridge newCartridge = new CashCartridge(cartridge.getNoteType(), cartridge.getNoteCount());
        this.vault.add(newCartridge);
    }

    public Float getVaultRemainder() {
        Float sum = 0F;
        for (CashCartridge cashCartridge : this.vault) {
            sum += cashCartridge.getNoteSum();
        }
        return sum;
    }

    public void checkAvailableCash(float cash) throws AtmException {
        Float vaultRemainder = getVaultRemainder();
        if (cash > vaultRemainder) {
            throw new AtmException("В банкомате недостаточно средств: запрошено " + cash,
                    ErrorCode.WITHDRAWAL_EXCEEDED_VAULT_REMAINDER);
        }
    }

    public void addNote(Banknotes note, Integer count) throws AtmException {
        CashCartridge cartridgeToAddTo = null;
        for (CashCartridge cartridge : this.vault) {
            if (note.equals(cartridge.getNoteType())) {
                cartridgeToAddTo = cartridge;
                break;
            }
        }
        if (cartridgeToAddTo == null) {
            throw new AtmException("Банкомат не принимает " + note.getName(),
                    ErrorCode.NO_SUCH_CARTRIDGE_FOR_NOTE);
        }
        else {
            cartridgeToAddTo.addNotes(count);
        }
    }

    private void removeNote(Banknotes note, Integer count) {
        CashCartridge cartridgeToRemoveFrom = null;
        for (CashCartridge cartridge : this.vault) {
            if (note.equals(cartridge.getNoteType())) {
                cartridgeToRemoveFrom = cartridge;
                break;
            }
        }
        if (cartridgeToRemoveFrom != null) {
            cartridgeToRemoveFrom.removeNotes(count);
        }
    }

    private Map<CashCartridge, Integer> getWithdrawalMap(Float cash) {
        Map<CashCartridge, Integer> withdrawalMap = new HashMap<>();
        Float cashRemainder = cash;

        for (CashCartridge cartridge : vault) {

            Float floatCount = cashRemainder / cartridge.getNoteType().getValue();
            Integer noteCount = floatCount.intValue();
            cashRemainder -= noteCount * cartridge.getNoteType().getValue();

            if (cartridge.getNoteCount() >= noteCount) { // только если есть такое количество банкнот в ячейке
                withdrawalMap.put(cartridge, noteCount);
            }
        }
        return withdrawalMap;
    }

    private void checkIfThereEnoughNotesInCartridges(Map<CashCartridge, Integer> withdrawalMap, Float cash) {
        Float withdrawalCash = 0F;
        for (Map.Entry<CashCartridge, Integer> withdrawal : withdrawalMap.entrySet()) {
            withdrawalCash += withdrawal.getKey().getNoteType().getValue() * withdrawal.getValue();
        }
        if (!withdrawalCash.equals(cash)) {
            throw new AtmException("В ячейках не хватает банкнот для выдачи " + cash,
                    ErrorCode.NOT_ENOUGH_NOTES_IN_CARTRIDGE);
        }
    }
}
