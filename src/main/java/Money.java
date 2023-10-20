import java.util.Objects;

public class Money {

    long amountInMinorUnit;

    final String currency = "SEK";
    Money(long amountInMinorUnit){
        if(amountInMinorUnit < 0) {
            throw new IllegalArgumentException();
        }
        this.amountInMinorUnit = amountInMinorUnit;
    }

    public long getAmountInMinorUnit(){
        return amountInMinorUnit;
    }

    public String getCurrency(){
        return currency;
    }

    /**
     * Subtracts the amount of money stored in the money object by a specific amount
     * @param amountInMinorUnit the amount to be subtracted
     */
    public void subtract(long amountInMinorUnit){
        if((amountInMinorUnit > this.amountInMinorUnit)|| (amountInMinorUnit < 0)){
            throw new IllegalArgumentException();
        }
        this.amountInMinorUnit -= amountInMinorUnit;
    }

    /**
     * Adds the amount of money stored in the money object by a specific amount
     * @param amountInMinorUnit the amount to be added
     */
    public void add(long amountInMinorUnit){
        if(amountInMinorUnit < 0){
            throw new IllegalArgumentException();
        }
        this.amountInMinorUnit += amountInMinorUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amountInMinorUnit == money.amountInMinorUnit && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountInMinorUnit, currency);
    }

    @Override
    public String toString(){
        return (amountInMinorUnit/100) + "kr";
    }


}