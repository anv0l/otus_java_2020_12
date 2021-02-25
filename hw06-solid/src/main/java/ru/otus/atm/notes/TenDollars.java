package ru.otus.atm.notes;

public class TenDollars implements BaseNote {
    private final String name = "ten-dollar bill";
    private final Float value = 10F;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Float getValue() {
        return this.value;
    }
}
