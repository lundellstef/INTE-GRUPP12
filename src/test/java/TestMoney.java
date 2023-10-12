
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TestMoney {

    static final String DEFAULT_CURRENCY = "SEK";
    static final int VALID_AMOUNT = 100;
    static final int INVALID_AMOUNT = -100;

    @Test
    public void throwsExceptionWhenAmountIsLessThanZero(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Money(INVALID_AMOUNT);
        });
    }

    @Test
    public void getAmountReturnsCorrectAmount(){
        Money money = new Money(VALID_AMOUNT);
        long amount = money.getAmountInMinorUnit();
        assertEquals(100, amount);
    }

    @Test
    public void getCurrencyReturnsCorrectCurrency(){
        Money money = new Money(VALID_AMOUNT);
        String currency = money.getCurrency();
        assertEquals(DEFAULT_CURRENCY, currency);
    }



}
