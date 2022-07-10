package ru;

import java.util.*;

public class AtmImpl implements Atm {
    private HashSet<BanknoteCell> banknotes = new HashSet<BanknoteCell>();// переделать на TreeSet

    public AtmImpl(List<Denomination> listCells) {
        listCells.forEach(denomination -> this.banknotes.add(new BanknoteCell(denomination, 0)));
    }

    @Override
    public HashSet<BanknoteCell> putBanknotes(Integer sum) throws RuntimeException {
        System.out.println("Кладем в банкомат " + sum);
        HashMap<Denomination, Integer> summByCells = new HashMap<>();
        summByCells = divingSum(sum, Denomination.FIVE_THOUSAND);
        if (summByCells == null) {
            System.out.println(summByCells.toString());
            summByCells = null;
            throw new RuntimeException("Невозможно разложить данные банкноты!");
        }

        summByCells.forEach((d, s) -> bancknotesProcessing(banknotes, new BanknotesProcessorImpl(), d, s, 1));
        return banknotes;
    }


    @Override
    public void getSum(Integer sum) throws RuntimeException {
        System.out.println("Берем из банкомата " + sum);
        HashMap<Denomination, Integer> summByCells = new HashMap<>();
        summByCells = divingSum(sum, Denomination.FIVE_THOUSAND);
        if (summByCells == null) {
            System.out.println(summByCells.toString());
            summByCells = null;
            throw new RuntimeException("Невозможно выдать необходимую сумму!");
        }
        try {
            summByCells.forEach((d, s) -> bancknotesProcessing(banknotes, new BanknotesProcessorImpl(), d, s, -1));
        } catch (RuntimeException e) {
            Denomination maxVal = Collections.max(summByCells.keySet());
            ;
            if (summByCells == null || summByCells.isEmpty()) {
                System.out.println(summByCells.toString());
                summByCells = null;
                throw new RuntimeException("Невозможно выдать необходимую сумму!");
            }
            summByCells.forEach((d, s) -> bancknotesProcessing(banknotes, new BanknotesProcessorImpl(), d, s, -1));
        }
    }

    @Override
    public Integer getBalanceAtm() {
        Integer[] balanceAtm = new Integer[1];
        balanceAtm[0] = 0;
        banknotes.forEach(b -> {
            System.out.println(b.getDenomination() + "  " + b.getCountOfBanknote());
            balanceAtm[0] = balanceAtm[0] + b.getCountOfBanknote() * b.getDenomination().getDenominationSum();
        })
        ;
        return balanceAtm[0];
    }

    private void bancknotesProcessing(Collection<BanknoteCell> listOfCells, BanknotesProcessor<BanknoteCell, Integer> processBanknotes, Denomination denomination, Integer count, Integer operation) {
        BanknoteCell banknotesCellModify = listOfCells.stream().filter(bank -> bank.getDenomination().equals(denomination)).findAny().get();
        processBanknotes.addBaknotes(banknotesCellModify, operation * count);
    }

    public HashMap<Denomination, Integer> divingSum(Integer sum, Denomination sumFrom) {
        HashMap<Denomination, Integer> summByCells = new HashMap<>();

        for (Denomination d : Denomination.values()) {
            if ((d.getDenominationSum() < sumFrom.getDenominationSum()) || d.getDenominationSum() == Denomination.FIVE_THOUSAND.getDenominationSum()) {
                Integer remaindiv = sum % d.getDenominationSum();
                Integer devVal = sum / d.getDenominationSum();
                if (devVal > 0) {
                    summByCells.put(d, devVal);
                    sum = sum - (d.getDenominationSum() * devVal);
                }
                System.out.println("===========");
                System.out.println("sum " + sum);
                System.out.println(d.getDenominationSum());
                System.out.println("devVal " + devVal);
                System.out.println("remaindiv " + remaindiv);
            }

        }
        if (sum != 0) {
            return null;
        }
        return summByCells;
    }
}
