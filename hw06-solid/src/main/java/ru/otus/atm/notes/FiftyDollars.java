package ru.otus.atm.notes;

public class FiftyDollars implements BaseNote {
    private final String name = "fifty-dollar bill";
    private final Float value = 50F;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Float getValue() {
        return this.value;
    }
}
