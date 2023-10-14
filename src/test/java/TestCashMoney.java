import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCashMoney {

    static final int INVALID_DENOMINATION = 7;
    static final int VALID_DENOMINATION = 1_000;

    @Test
    public void throwsExceptionWhenCreatingMoneyWithInvalidDenomination(){
        assertThrows(IllegalArgumentException.class, () -> {
            new CashMoney(INVALID_DENOMINATION);
        });
    }

    @Test
    public void getCashMoneyReturnsCorrectDenomination(){
        CashMoney cashMoney = new CashMoney(VALID_DENOMINATION);
        assertEquals(VALID_DENOMINATION, cashMoney.getCashMoneyDenomination());
    }

    @Test
    public void toStringReturnsCorrectlyFormattedString(){
        CashMoney cashMoney = new CashMoney(VALID_DENOMINATION);
        String printedMoney = cashMoney.toString();
        assertEquals("100kr", printedMoney);
    }
}
