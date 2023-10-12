
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TestMoney {

    static final String DEFAULT_CURRENCY = "SEK";
    static final long VALID_AMOUNT = 10_000;

    static final long DEFAULT_ADD_AND_SUBTRACT_AMOUNT = 1_000;

    static final long DEFAULT_AMOUNT_AFTER_SUBTRACT = 9_000;

    static final long DEFAULT_AMOUNT_AFTER_ADD = 11_000;
    static final long INVALID_AMOUNT = -10_000;

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
        assertEquals(VALID_AMOUNT, amount);
    }

    @Test
    public void getCurrencyReturnsCorrectCurrency(){
        Money money = new Money(VALID_AMOUNT);
        String currency = money.getCurrency();
        assertEquals(DEFAULT_CURRENCY, currency);
    }

    @Test
    public void subtractMoneyGivesCorrectNewAmount(){
        Money money = new Money(VALID_AMOUNT);
        money.subtract(DEFAULT_ADD_AND_SUBTRACT_AMOUNT);
        long amount = money.getAmountInMinorUnit();
        assertEquals(DEFAULT_AMOUNT_AFTER_SUBTRACT, amount);
    }

    @Test
    public void throwsExceptionWhenAmountToSubtractIsBiggerThanTotalAmount(){
        Money money = new Money(VALID_AMOUNT);
        assertThrows(IllegalArgumentException.class, () -> {
            money.subtract(VALID_AMOUNT + 1);
        });
    }

    @Test
    public void throwExceptionWhenAmountToBeSubtractedIsNegative(){
        Money money = new Money(VALID_AMOUNT);
        assertThrows(IllegalArgumentException.class, () -> {
            money.subtract(INVALID_AMOUNT);
        });
    }

    @Test
    public void addMoneyGivesCorrectNewAmount(){
        Money money = new Money(VALID_AMOUNT);
        money.add(DEFAULT_ADD_AND_SUBTRACT_AMOUNT);
        long amount = money.getAmountInMinorUnit();
        assertEquals(DEFAULT_AMOUNT_AFTER_ADD, amount);
    }

    @Test
    public void throwExceptionWhenAmountToBeAddedIsNegative(){
        Money money = new Money(VALID_AMOUNT);
        assertThrows(IllegalArgumentException.class, () -> {
            money.add(INVALID_AMOUNT);
        });
    }

    @Test
    public void toStringReturnsCorrectlyFormattedString(){
        Money money = new Money(VALID_AMOUNT);
        String printedMoney = money.toString();
        assertEquals("100kr", printedMoney);
    }

}
