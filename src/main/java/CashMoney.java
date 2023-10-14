public class CashMoney {

    int amountInMinorUnit;
    public CashMoney(int denomination){
        switch (denomination) {
            case 1 -> amountInMinorUnit = 1;
            case 100 -> amountInMinorUnit = 100;
            case 200 -> amountInMinorUnit = 200;
            case 500 -> amountInMinorUnit = 500;
            case 1_000 -> amountInMinorUnit = 1_000;
            case 2_000 -> amountInMinorUnit = 2_000;
            case 5_000 -> amountInMinorUnit = 5_000;
            case 10_000 -> amountInMinorUnit = 10_000;
            case 20_000 -> amountInMinorUnit = 20_000;
            case 50_000 -> amountInMinorUnit = 50_000;
            case 100_000 -> amountInMinorUnit = 100_000;
            default -> throw new IllegalArgumentException();
        }
    }

    public int getCashMoneyDenomination(){
        return amountInMinorUnit;
    }
}
