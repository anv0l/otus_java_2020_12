package ru.otus.atm;

import ru.otus.atm.exceptions.AtmException;
import ru.otus.atm.exceptions.ErrorCode;
import ru.otus.atm.notes.Banknotes;

public class CashCartridge {
    private final Banknotes noteType;
    private Integer noteCount;

    public CashCartridge(Banknotes baseNote, Integer count) {
        checkNoteCount(count);
        this.noteType = baseNote;
        this.noteCount = count;
    }

    public Banknotes getNoteType() {
        return this.noteType;
    }

    public void addNotes(Integer count) {
        checkNoteCount(count);
        this.noteCount += count;
    }

    public Integer getNoteCount() {
        return this.noteCount;
    }

    public Float getNoteSum() {
        return this.noteCount * noteType.getValue();
    }

    public void removeNotes(Integer count) {
        if (count <= this.noteCount) {
            this.noteCount -= count;
        }
        else {
            throw new AtmException("Нельзя снять больше банкнот, чем находится в ячейке!", ErrorCode.NOT_ENOUGH_NOTES_IN_CARTRIDGE);
        }
    }

    private void checkNoteCount(Integer count) {
        if (count < 0) {
            throw new IllegalArgumentException("Количество банкнот не должно быть отрицательным");
        }
    }
}
