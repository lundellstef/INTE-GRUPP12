import java.util.Objects;

public class CashMoney {

    private int amountInMinorUnit;
    public CashMoney(int denomination){
        switch (denomination) {
            case 1 -> amountInMinorUnit = 1;
            case 100 -> amountInMinorUnit = 1_00;
            case 200 -> amountInMinorUnit = 2_00;
            case 500 -> amountInMinorUnit = 5_00;
            case 1_000 -> amountInMinorUnit = 10_00;
            case 2_000 -> amountInMinorUnit = 20_00;
            case 5_000 -> amountInMinorUnit = 50_00;
            case 10_000 -> amountInMinorUnit = 100_00;
            case 20_000 -> amountInMinorUnit = 200_00;
            case 50_000 -> amountInMinorUnit = 500_00;
            case 100_000 -> amountInMinorUnit = 1000_00;
            default -> throw new IllegalArgumentException();
        }
    }

    public int getCashMoneyDenomination(){
        return amountInMinorUnit;
    }


    @Override
    public String toString(){
        return (amountInMinorUnit/100) + "kr";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashMoney cashMoney = (CashMoney) o;
        return amountInMinorUnit == cashMoney.amountInMinorUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountInMinorUnit);
    }
}
