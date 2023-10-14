import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCashRegister {

    long VALID_AMOUNT_READ_FROM_DATABASE_FILE = 200_000;

    @Test
    public void cashRegisterAmountOfMoneyCorrectlyReadFromDatabaseFile(){
        CashRegister cashRegister = new CashRegister();
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(VALID_AMOUNT_READ_FROM_DATABASE_FILE, amountOfMoneyInStore);
    }

}
