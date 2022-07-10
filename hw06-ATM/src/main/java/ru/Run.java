package ru;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Run {
    public static void main(String[] args) {
        System.out.println("Начнем работу!");
        List<Denomination> list = new ArrayList<Denomination>(Arrays.asList(Denomination.ONE_HUNDRED, Denomination.TWO_HUNDRED, Denomination.FIVE_HUNDRED, Denomination.ONE_THOUSAND, Denomination.TWO_THOUSAND));
        Atm atm = new AtmImpl(list);
        atm.putBanknotes(600);
        System.out.println("Закончили работу!");
        atm.getBalanceAtm();

        atm.putBanknotes(800);
        atm.getBalanceAtm();

        atm.putBanknotes(1000);
        atm.getBalanceAtm();

        atm.getSum(500);
        atm.getBalanceAtm();

    }
}
