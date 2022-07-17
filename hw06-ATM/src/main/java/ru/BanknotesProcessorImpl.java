package ru;

import java.util.Set;

public class BanknotesProcessorImpl implements BanknotesProcessor<BanknoteCell, Integer> {
    @Override //action
    public void addBaknotes(BanknoteCell banknoteCell, Integer count) throws RuntimeException {
        if (count >= 0) {
            banknoteCell.setCountOfBanknote(banknoteCell.getCountOfBanknote() + count);
        } else {
            int diff = banknoteCell.getCountOfBanknote() + count;
            if (diff < 0) {
                throw new RuntimeException("не достаточно средств в ячейке");
            }
            banknoteCell.setCountOfBanknote(banknoteCell.getCountOfBanknote() - count);

        }
    }


}
