
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TestMoney {

    static final String VALID_CURRENCY = "SEK";
    static final String INVALID_CURRENCY = "USD";
    static final int VALID_AMOUNT = 100;
    static final int INVALID_AMOUNT = -100;

    @Test
    public void throwsExceptionWhenAmountIsLessThanZero(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Money(VALID_CURRENCY, INVALID_AMOUNT);
        });
    }

    @Test
    public void throwsExceptionWhenCurrencyIsSomethingOtherThanSEK(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Money(INVALID_CURRENCY, VALID_AMOUNT);
        });
    }

    @Test
    public void getCurrencyReturnsCorrectCurrency(){
        Money money = new Money(VALID_CURRENCY, VALID_AMOUNT);
        assertEquals("SEK", money.getCurrency());
    }

}
