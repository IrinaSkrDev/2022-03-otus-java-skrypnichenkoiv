package ru;

import java.util.Set;

public interface Atm {
    public Set<BanknoteCell> putBanknotes(Integer sum);

    public void getSum(Integer sum);

    public Integer getBalanceAtm();
}
