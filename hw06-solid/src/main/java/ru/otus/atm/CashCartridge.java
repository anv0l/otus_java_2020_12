package ru.otus.atm;

import ru.otus.atm.notes.BaseNote;

public class CashCartridge {
    private final BaseNote noteType;
    private Integer noteCount;

    private void checkNoteCount(Integer count) {
        if (count < 0) {
            throw new IllegalArgumentException("Количество банкнот не должно быть отрицательным");
        }
    }

    public CashCartridge(BaseNote baseNote, Integer count) {
        checkNoteCount(count);
        this.noteType = baseNote;
        this.noteCount = count;
    }

    public BaseNote getNoteType() {
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
        this.noteCount -= count;
    }
}
