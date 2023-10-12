public class Money {

    long amountInMinorUnit;
    String currency = "SEK";
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

    public void subtract(long amountInMinorUnit){
        if(amountInMinorUnit > this.amountInMinorUnit){
            throw new IllegalArgumentException();
        }
        this.amountInMinorUnit -= amountInMinorUnit;
    }

    public void add(long amountInMinorUnit){
        this.amountInMinorUnit += amountInMinorUnit;
    }


}