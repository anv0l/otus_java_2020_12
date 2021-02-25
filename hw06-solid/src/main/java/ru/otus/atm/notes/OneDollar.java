package ru.otus.atm.notes;

public class OneDollar implements BaseNote{
    private final String name = "one-dollar bill";
    private final Float value = 1F;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Float getValue() {
        return this.value;
    }
}
