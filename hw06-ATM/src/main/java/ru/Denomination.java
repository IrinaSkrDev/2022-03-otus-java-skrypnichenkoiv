package ru;

public enum Denomination {


    FIVE_THOUSAND(5000),
    TWO_THOUSAND(2000),
    ONE_THOUSAND(1000),
    FIVE_HUNDRED(500),
    TWO_HUNDRED(200),
    ONE_HUNDRED(100);
    private final Integer denominationSum;

    Denomination(Integer denominationSum) {
        this.denominationSum = denominationSum;
    }

    public Integer getDenominationSum() {
        return this.denominationSum;
    }
}
