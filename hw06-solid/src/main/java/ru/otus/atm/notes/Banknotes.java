package ru.otus.atm.notes;

public enum Banknotes {
    ONE_DOLLAR(1F, "One-dollar bill"),
    TEN_DOLLARS(10F, "Ten-dollar bill"),
    FIFTY_DOLLARS(50F, "Fifty-dollar bill"),
    ONE_HUNDRED_DOLLARS(100F, "One-hundred-dollar bill");

    private final Float value;
    private final String name;

    Banknotes(float value, String name) {
        this.value = value;
        this.name = name;
    }

    public Float getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
