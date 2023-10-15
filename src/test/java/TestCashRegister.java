import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCashRegister {

    long VALID_AMOUNT_READ_FROM_DATABASE_FILE = 200_000;
    long VALID_CARD_PAYMENT_AMOUNT = 25_000;


    @Test
    public void cashRegisterAmountOfMoneyCorrectlyReadFromDatabaseFile(){
        CashRegister cashRegister = new CashRegister("src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(VALID_AMOUNT_READ_FROM_DATABASE_FILE, amountOfMoneyInStore);
    }

    @Test
    public void cashRegisterAmountOfMoneyCorrectlyReadFromDatabaseFileWhenAmountIsZero(){
        CashRegister cashRegister = new CashRegister("src/main/java/CashRegisterMoneyTestFiles/amountOfMoneyIsZero.txt");
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(0, amountOfMoneyInStore);
    }

    @Test
    public void throwsExceptionIfDatabaseFileHasNegativeAmountOfMoney() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CashRegister("src/main/java/CashRegisterMoneyTestFiles/negativeAmountOfMoney.txt");
        });
    }

    @Test
    public void ifDatabaseFileIsEmptyAmountOfMoneyInStoreShouldHaveZeroInAmount() {
        CashRegister cashRegister = new CashRegister("src/main/java/CashRegisterMoneyTestFiles/emptyFile.txt");
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(0, amountOfMoneyInStore);
    }

    @Test
    public void throwsExceptionIfNoFileIsFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CashRegister("src/main/java/CashRegisterMoneyTestFiles/fileThatDoesNotExist.txt");
        });
    }

    @Test
    public void throwsExceptionIfDatabaseFileDoesNotContainNumbers() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CashRegister("src/main/java/CashRegisterMoneyTestFiles/nonNumericCharacters.txt");
        });
    }

    @Test
    public void ifDatabaseFileHasLargeAmountOfMoneyCorrectAmountShouldStillBeMAde() {
        CashRegister cashRegister = new CashRegister("src/main/java/CashRegisterMoneyTestFiles/largeAmountOfMoney.txt");
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(999999999999999L, amountOfMoneyInStore);
    }

    @Test
    public void payByCardShouldIncrementValueInAmountOfMoneyInStoreByCorrectAmount(){
        CashRegister cashRegister = new CashRegister("src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
        long prePurchaseAmountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        long expectedAmount = prePurchaseAmountOfMoneyInStore + VALID_CARD_PAYMENT_AMOUNT;
        cashRegister.payByCard(VALID_CARD_PAYMENT_AMOUNT);
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(expectedAmount, amountOfMoneyInStore);
    }

}
