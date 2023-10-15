import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestCashRegister {

    static final long VALID_AMOUNT_READ_FROM_DATABASE_FILE = 200_000;
    static final long VALID_CARD_PAYMENT_AMOUNT = 25_000;

    static final long INVALID_CARD_PAYMENT_AMOUNT = -25_000;


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
        cashRegister.payByCard(VALID_CARD_PAYMENT_AMOUNT, "src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(expectedAmount, amountOfMoneyInStore);
        rollBackTestDatabaseUpdate("200000", "src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
    }

    @Test
    public void multiplePaymentsShouldIncrementValueInAmountOfMoneyInStoreByCorrectAmount(){
        CashRegister cashRegister = new CashRegister("src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
        long prePurchaseAmountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        long expectedAmount = prePurchaseAmountOfMoneyInStore + (VALID_CARD_PAYMENT_AMOUNT * 4);
        for(int i = 0; i < 4; i++){
            cashRegister.payByCard(VALID_CARD_PAYMENT_AMOUNT,"src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
        }
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(expectedAmount, amountOfMoneyInStore);
        rollBackTestDatabaseUpdate("200000", "src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
    }

    @Test
    public void payByCardShouldUpdateDatabaseFileWithCorrectNewAmount(){
        long[] expectedAmountAndActualAmount = setUpDoublePurchaseToReadFromDatabase(VALID_CARD_PAYMENT_AMOUNT, "src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
        long expectedAmount = expectedAmountAndActualAmount[0];
        long amountOfMoneyInStoreAfterPurchase = expectedAmountAndActualAmount[1];
        assertEquals(expectedAmount, amountOfMoneyInStoreAfterPurchase);
        rollBackTestDatabaseUpdate("200000", "src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
    }

    @Test
    public void payByCardShouldThrowExceptionIfTryingToPayWithNegativeAmount(){
        CashRegister cashRegister = new CashRegister("src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
        assertThrows(IllegalArgumentException.class, () -> {
            cashRegister.payByCard(INVALID_CARD_PAYMENT_AMOUNT,"src/main/java/CashRegisterMoneyTestFiles/validAmountOfMoney.txt");
        });
    }

    @Test
    public void payByCardShouldUpdateToCorrectAmountWhenDatabaseFileIsEmpty(){
        long[] expectedAmountAndActualAmount = setUpDoublePurchaseToReadFromDatabase(VALID_CARD_PAYMENT_AMOUNT, "src/main/java/CashRegisterMoneyTestFiles/emptyFile.txt");
        long expectedAmount = expectedAmountAndActualAmount[0];
        long amountOfMoneyInStoreAfterPurchase = expectedAmountAndActualAmount[1];
        assertEquals(expectedAmount, amountOfMoneyInStoreAfterPurchase);
        rollBackTestDatabaseUpdate("", "src/main/java/CashRegisterMoneyTestFiles/emptyFile.txt");
    }


    private void rollBackTestDatabaseUpdate(String amountInFile, String fileName){
        try{
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(amountInFile);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long[] setUpDoublePurchaseToReadFromDatabase(long amount, String filename){
        CashRegister cashRegister = new CashRegister(filename);
        long prePurchaseAmountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        cashRegister.payByCard(amount,filename);
        long expectedAmount = prePurchaseAmountOfMoneyInStore + amount;
        CashRegister cashRegisterAfterPurchase = new CashRegister(filename);
        long amountOfMoneyInStore = cashRegisterAfterPurchase.getAmountOfMoneyInStore();
        return new long[]{expectedAmount, amountOfMoneyInStore};
    }

}
