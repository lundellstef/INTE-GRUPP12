public class Money {


    public Money(String currency, int amount){
        if(amount < 0 || !currency.equals("SEK")) {
            throw new IllegalArgumentException();
        }
    }

}