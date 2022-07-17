package ru;

import java.util.Set;
@FunctionalInterface
public interface BanknotesProcessor<BanknoteCell, Integer>{
    public void addBaknotes(BanknoteCell banknoteCell,Integer count);
    };


