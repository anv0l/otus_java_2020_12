package ru.otus.atm.notes;

public class OneHundredDollars implements BaseNote{
    private final String name = "one-hundred-dollar bill";
    private final Float value = 100F;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Float getValue() {
        return this.value;
    }
}
