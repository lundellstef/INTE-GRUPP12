public class Money {

    long amountInMinorUnit;
    String currency = "SEK";
    Money(int amountInMinorUnit){
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


}