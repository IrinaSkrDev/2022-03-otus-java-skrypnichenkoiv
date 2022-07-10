package ru;

import java.util.Set;
@FunctionalInterface
public interface BanknotesProcessor<T,F>{
    public void addBaknotes(T banknoteCell,F count);
    };


