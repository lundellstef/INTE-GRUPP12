import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CashRegister {

    Money amountOfMoneyInStore;
    int[] denominations = new int[]{1000_00, 500_00, 200_00, 100_00, 50_00, 20_00, 10_00, 5_00, 2_00, 1_00, 1};

    public CashRegister(){
        readFromDatabaseFile("src/main/resources/cashRegisterMoney.txt");
    }

    public CashRegister(String cashRegisterMoneyDatabaseFileName){
        readFromDatabaseFile(cashRegisterMoneyDatabaseFileName);
    }

    /**
     * amount of money in store returned in minor unit
     * @return amount of money in store
     */
    public long getAmountOfMoneyInStore(){
        return amountOfMoneyInStore.getAmountInMinorUnit();
    }

    /**
     * A method that handles payment by card. Calls on helper method to write the new amount
     * to the database file.
     * @param amountInMinorUnit cost of the purchase
     * @param fileName database file containing the store's money that should be updated.
     */
    public void payByCard(long amountInMinorUnit, String fileName){
        writeToDatabaseFile(amountInMinorUnit, fileName);
        amountOfMoneyInStore.add(amountInMinorUnit);
    }

    /**
     * A method that handles payment by cash. If the amount of cash is less than cost of purchase, an exception is thrown.
     * Calls on helper method to write the new amount to the database file.
     * @param payment the cash the customer pays with
     * @param actualCostInMinorUnit the cost of the purchase
     * @param filename database file containing the store's money that should be updated.
     * @return a map containing the change where the key is a CashMoney object in a specific denomination
     * and the corresponding value represents how many bills there is of that specific denomination.
     * If there is no change an empty map is returned.     I
     */
    public HashMap<CashMoney, Integer> payByCash(HashMap<CashMoney, Integer> payment, long actualCostInMinorUnit, String filename){
        long amountInCash = 0;
        HashMap<CashMoney, Integer> returnWallet = new HashMap<>();
        for(CashMoney cash: payment.keySet()){
            for(int i = 0; i < payment.get(cash); i++){
                amountInCash += cash.getCashMoneyDenomination();
            }
        }
        if(amountInCash < actualCostInMinorUnit){
            throw new IllegalArgumentException("You have not paid enough money");
        }
        if(amountInCash > actualCostInMinorUnit){
            long change = amountInCash - actualCostInMinorUnit;
            returnWallet = calculateChange(change);
        }
        writeToDatabaseFile(actualCostInMinorUnit, filename);
        amountOfMoneyInStore.add(actualCostInMinorUnit);
        return returnWallet;
    }

    /**
     * Method that calculates the change with regard to the available denominations in SEK.
     * @param amount the amount of change to be given to the customer
     * @return a map containing the change where the key is a CashMoney object in a specific denomination
     * and the corresponding value represents how many bills there is of that specific denomination.
     */
    private HashMap<CashMoney, Integer> calculateChange(long amount){
        HashMap<CashMoney, Integer> returnWallet = new HashMap<>();
        for(int denomination : denominations){
            int currentDenomination = (int) (amount/denomination);

            if((currentDenomination > 0)){
                returnWallet.put(new CashMoney(denomination), currentDenomination);
                amount %= denomination;
            }
        }
        return returnWallet;
    }

    /**
     * Method that writes the new amount of money to the database file containing the store's total
     * amount of money. Throws exception if amount of money is less than zero.
     * @param amountInMinorUnit the amount to be added to the database file
     * @param fileName the database file to be changed
     */
    private void writeToDatabaseFile(long amountInMinorUnit, String fileName){
        try{
            if(amountInMinorUnit < 0){
                throw new IllegalArgumentException("You cannot have negative money");
            }
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            long prePurchaseAmountOfMoney = getAmountOfMoneyInStore();
            long amountOfMoneyAfterPurchase = prePurchaseAmountOfMoney + amountInMinorUnit;
            String amountToDatabase = String.valueOf(amountOfMoneyAfterPurchase);
            writer.write(amountToDatabase);
            writer.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Method that reads the amount of money currently in the database file containing the store's total
     * amount of money.
     * @param fileName the database file to be read from.
     */
    private void readFromDatabaseFile(String fileName){
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);
            String readLine = reader.readLine();

            if(readLine == null){
                amountOfMoneyInStore = new Money(0);
            } else if(Long.parseLong(readLine) < 0) {
                throw new IllegalArgumentException("Money cannot be negative");
            } else{
                amountOfMoneyInStore = new Money(Long.parseLong(readLine));
            }
            reader.close();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }



}

































