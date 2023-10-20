import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestCashRegister {

    //TESTAR INTE ATT DATABASFILEN UPPDATERAS KORREKT NÄR MAN PAYBYCASH, DET ÄR JU SAMMA GREJ SOM FÖR PAYBYCARD - TITTA PÅ DETTA.
    static final long VALID_AMOUNT_READ_FROM_DATABASE_FILE = 2000_00;
    static final long VALID_PAYMENT_AMOUNT = 250_00;

    static final String VALID_DATABASE_FILE = "src/test/resources/CashRegisterMoneyTestFiles/validAmountOfMoney.txt";
    static final String VALID_DATABASE_FILE_AMOUNT_ZERO = "src/test/resources/CashRegisterMoneyTestFiles/amountOfMoneyIsZero.txt";

    static final String INVALID_DATABASE_FILE_NEGATIVE_AMOUNT = "src/test/resources/CashRegisterMoneyTestFiles/negativeAmountOfMoney.txt";

    static final String VALID_EMPTY_DATABASE_FILE = "src/test/resources/CashRegisterMoneyTestFiles/emptyFile.txt";

    static final String INVALID_NON_EXISTING_DATABASE_FILE = "src/main/java/CashRegisterMoneyTestFiles/fileThatDoesNotExist.txt";

    static final String INVALID_NON_NUMERIC_DATABASE_FILE = "src/test/resources/CashRegisterMoneyTestFiles/nonNumericCharacters.txt";

    static final String VALID_DATABASE_FILE_LARGE_AMOUNT_OF_MONEY = "src/test/resources/CashRegisterMoneyTestFiles/largeAmountOfMoney.txt";

    static final long INVALID_PAYMENT_AMOUNT = -250_00;


    @Test
    public void cashRegisterAmountOfMoneyCorrectlyReadFromDatabaseFile(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE);
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(VALID_AMOUNT_READ_FROM_DATABASE_FILE, amountOfMoneyInStore);
    }

    @Test
    public void cashRegisterAmountOfMoneyCorrectlyReadFromDatabaseFileWhenAmountIsZero(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE_AMOUNT_ZERO);
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(0, amountOfMoneyInStore);
    }

    @Test
    public void throwsExceptionIfDatabaseFileHasNegativeAmountOfMoney() {
        assertThrows(IllegalArgumentException.class, () -> new CashRegister(INVALID_DATABASE_FILE_NEGATIVE_AMOUNT));
    }

    @Test
    public void ifDatabaseFileIsEmptyAmountOfMoneyInStoreShouldHaveZeroInAmount() {
        CashRegister cashRegister = new CashRegister(VALID_EMPTY_DATABASE_FILE);
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(0, amountOfMoneyInStore);
    }

    @Test
    public void throwsExceptionIfNoFileIsFound() {
        assertThrows(IllegalArgumentException.class, () -> new CashRegister(INVALID_NON_EXISTING_DATABASE_FILE));
    }

    @Test
    public void throwsExceptionIfDatabaseFileDoesNotContainNumbers() {
        assertThrows(IllegalArgumentException.class, () -> new CashRegister(INVALID_NON_NUMERIC_DATABASE_FILE));
    }

    @Test
    public void ifDatabaseFileHasLargeAmountOfMoneyCorrectAmountShouldStillBeMAde() {
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE_LARGE_AMOUNT_OF_MONEY);
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(999999999999999L, amountOfMoneyInStore);
    }

    @Test
    public void payByCardShouldIncrementValueInAmountOfMoneyInStoreByCorrectAmount(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE);
        long amount = calculateExpectedAmountOfMoneyAfterPurchase(cashRegister);
        cashRegister.payByCard(VALID_PAYMENT_AMOUNT, VALID_DATABASE_FILE);
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(amount, amountOfMoneyInStore);
        rollBackTestDatabaseUpdate("200000", VALID_DATABASE_FILE);
    }

    @Test
    public void multiplePaymentsShouldIncrementValueInAmountOfMoneyInStoreByCorrectAmount(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE);
        long prePurchaseAmountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        long expectedAmount = prePurchaseAmountOfMoneyInStore + (VALID_PAYMENT_AMOUNT * 4);
        for(int i = 0; i < 4; i++){
            cashRegister.payByCard(VALID_PAYMENT_AMOUNT,VALID_DATABASE_FILE);
        }
        long amountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        assertEquals(expectedAmount, amountOfMoneyInStore);
        rollBackTestDatabaseUpdate("200000", VALID_DATABASE_FILE);
    }

    @Test
    public void payByCardShouldUpdateDatabaseFileWithCorrectNewAmount(){
        long[] expectedAmountAndActualAmount = setUpDoublePurchaseToReadFromDatabase(VALID_DATABASE_FILE);
        long expectedAmount = expectedAmountAndActualAmount[0];
        long amountOfMoneyInStoreAfterPurchase = expectedAmountAndActualAmount[1];
        assertEquals(expectedAmount, amountOfMoneyInStoreAfterPurchase);
        rollBackTestDatabaseUpdate("200000", VALID_DATABASE_FILE);
    }

    @Test
    public void payByCardShouldThrowExceptionIfTryingToPayWithNegativeAmount(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE);
        assertThrows(IllegalArgumentException.class, () -> cashRegister.payByCard(INVALID_PAYMENT_AMOUNT,VALID_DATABASE_FILE));
    }

    @Test
    public void payByCardShouldUpdateToCorrectAmountWhenDatabaseFileIsEmpty(){
        long[] expectedAmountAndActualAmount = setUpDoublePurchaseToReadFromDatabase(VALID_EMPTY_DATABASE_FILE);
        long expectedAmount = expectedAmountAndActualAmount[0];
        long amountOfMoneyInStoreAfterPurchase = expectedAmountAndActualAmount[1];
        assertEquals(expectedAmount, amountOfMoneyInStoreAfterPurchase);
        rollBackTestDatabaseUpdate("", VALID_EMPTY_DATABASE_FILE);
    }

    @Test
    public void payByCashUpdatesAmountOfMoneyInStoreCorrectly(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE);
        long amount = calculateExpectedAmountOfMoneyAfterPurchase(cashRegister);
        HashMap<CashMoney, Integer> cashMoneyPayment = new HashMap<>();
        addExactPaymentAmountToWallet(cashMoneyPayment);
        cashRegister.payByCash(cashMoneyPayment, VALID_PAYMENT_AMOUNT, VALID_DATABASE_FILE);
        assertEquals(amount, cashRegister.getAmountOfMoneyInStore());
        rollBackTestDatabaseUpdate("200000", VALID_DATABASE_FILE);
    }

    @Test
    public void payByCashWithAmountBiggerThanPaymentAmountIncrementsAmountOfMoneyInStoreWithPaymentAmount(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE);
        long amount = calculateExpectedAmountOfMoneyAfterPurchase(cashRegister);
        HashMap<CashMoney, Integer> cashMoneyPayment = new HashMap<>();
        addMoreThanPaymentAmountToWallet(cashMoneyPayment);
        cashRegister.payByCash(cashMoneyPayment, VALID_PAYMENT_AMOUNT, VALID_DATABASE_FILE);
        assertEquals(amount, cashRegister.getAmountOfMoneyInStore());
        rollBackTestDatabaseUpdate("200000", VALID_DATABASE_FILE);
    }

    @Test
    public void payByCashWithAmountLessThanPaymentAmountThrowsException(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE);
        HashMap<CashMoney, Integer> cashMoneyPayment = new HashMap<>();
        addLessThanPaymentAmountToWallet(cashMoneyPayment);
        assertThrows(IllegalArgumentException.class, () -> cashRegister.payByCash(cashMoneyPayment, VALID_PAYMENT_AMOUNT, VALID_DATABASE_FILE));
    }

    @Test
    public void payByCashShouldReturnCorrectAmountOfMoney(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE);
        long expectedChange = 2000;
        HashMap<CashMoney, Integer> cashMoneyPayment = new HashMap<>();
        addMoreThanPaymentAmountToWallet(cashMoneyPayment);
        HashMap<CashMoney, Integer> returnedChange = cashRegister.payByCash(cashMoneyPayment, VALID_PAYMENT_AMOUNT, VALID_DATABASE_FILE);
        long actualChange = getAmountOfMoneyInCash(returnedChange);
        assertEquals(expectedChange, actualChange);
        rollBackTestDatabaseUpdate("200000", VALID_DATABASE_FILE);
    }

    @Test
    public void payByCashShouldReturnAmountOfMoneyInLeastAmountOfCash(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE);
        HashMap<CashMoney, Integer> expectedReturnWallet = new HashMap<>();
        expectedReturnWallet.put(new CashMoney(2000), 1);
        HashMap<CashMoney, Integer> cashMoneyPayment = new HashMap<>();
        addMoreThanPaymentAmountToWallet(cashMoneyPayment);
        HashMap<CashMoney, Integer> returnedChange = cashRegister.payByCash(cashMoneyPayment, VALID_PAYMENT_AMOUNT, VALID_DATABASE_FILE);
        assertEquals(expectedReturnWallet, returnedChange);
        rollBackTestDatabaseUpdate("200000", VALID_DATABASE_FILE);
    }

    @Test
    public void payByCashShouldReturnAmountOfMoneyInLeastAmountOfCashWhenPaymentIsLarge(){
        CashRegister cashRegister = new CashRegister(VALID_DATABASE_FILE);
        HashMap<CashMoney, Integer> paymentWallet = createPaymentWalletForLargeAmountOfMoney();
        HashMap<CashMoney, Integer> returnedChange = cashRegister.payByCash(paymentWallet, VALID_PAYMENT_AMOUNT, VALID_DATABASE_FILE);
        HashMap<CashMoney, Integer> expectedReturnWallet = createExpectedReturnWalletForLargeAmountOfMoney();
        assertEquals(expectedReturnWallet, returnedChange);
        rollBackTestDatabaseUpdate("200000", VALID_DATABASE_FILE);
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

    private long[] setUpDoublePurchaseToReadFromDatabase(String filename){
        CashRegister cashRegister = new CashRegister(filename);
        long prePurchaseAmountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        long expectedAmount = prePurchaseAmountOfMoneyInStore + VALID_PAYMENT_AMOUNT;
        cashRegister.payByCard(VALID_PAYMENT_AMOUNT,filename);
        CashRegister cashRegisterAfterPurchase = new CashRegister(filename);
        long amountOfMoneyInStore = cashRegisterAfterPurchase.getAmountOfMoneyInStore();
        return new long[]{expectedAmount, amountOfMoneyInStore};
    }

    private void addExactPaymentAmountToWallet(HashMap<CashMoney, Integer> wallet){
        wallet.put(new CashMoney(5_000), 1);
        wallet.put(new CashMoney(10_000), 2);
    }

    private void addMoreThanPaymentAmountToWallet(HashMap<CashMoney, Integer> wallet){
        wallet.put(new CashMoney(5_000), 1);
        wallet.put(new CashMoney(10_000), 2);
        wallet.put(new CashMoney(2_000), 1);
    }

    private void addLessThanPaymentAmountToWallet(HashMap<CashMoney, Integer> wallet){
        wallet.put(new CashMoney(5_000), 1);
        wallet.put(new CashMoney(10_000), 1);

    }

    private long getAmountOfMoneyInCash(HashMap<CashMoney, Integer> wallet){
        long amountInCash = 0;
        for(CashMoney cash: wallet.keySet()){
            for(int i = 0; i < wallet.get(cash); i++){
                amountInCash += cash.getCashMoneyDenomination();
            }
        }
        return amountInCash;
    }

    private HashMap<CashMoney, Integer> createPaymentWalletForLargeAmountOfMoney(){
        HashMap<CashMoney, Integer> wallet = new HashMap<>();
        wallet.put(new CashMoney(100_000), 52);
        wallet.put(new CashMoney(50_000), 1);
        wallet.put(new CashMoney(2_000), 1);
        wallet.put(new CashMoney(1_000), 1);
        return wallet;
    }

    private HashMap<CashMoney, Integer> createExpectedReturnWalletForLargeAmountOfMoney(){
        HashMap<CashMoney, Integer> wallet = new HashMap<>();
        wallet.put(new CashMoney(100_000), 52);
        wallet.put(new CashMoney(20_000), 1);
        wallet.put(new CashMoney(5_000), 1);
        wallet.put(new CashMoney(2_000), 1);
        wallet.put(new CashMoney(1_000), 1);
        return wallet;
    }

    private long calculateExpectedAmountOfMoneyAfterPurchase(CashRegister cashRegister){
        long prePurchaseAmountOfMoneyInStore = cashRegister.getAmountOfMoneyInStore();
        return prePurchaseAmountOfMoneyInStore + VALID_PAYMENT_AMOUNT;
    }
}
