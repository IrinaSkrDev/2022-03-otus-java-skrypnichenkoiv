package ru;

import java.util.HashSet;

public interface Atm {
    public HashSet<BanknoteCell> putBanknotes(Integer sum);

    public void getSum(Integer sum);

    public Integer getBalanceAtm();
}
