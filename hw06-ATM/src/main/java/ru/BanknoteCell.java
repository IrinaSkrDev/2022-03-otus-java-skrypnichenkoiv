package ru;

public class BanknoteCell {
    private Denomination denomination;
    private Integer countOfBanknote;

    public BanknoteCell(Denomination denomination, Integer count) {
        this.denomination = denomination;
        this.countOfBanknote = count;
    }
    public Denomination getDenomination() {
        return  this.denomination;
    }
    public Integer getCountOfBanknote(){
        return  this.countOfBanknote;
    }
    public void setCountOfBanknote(Integer countOfBanknote){
        this.countOfBanknote = countOfBanknote;

    }
}
