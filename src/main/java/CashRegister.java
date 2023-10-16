import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CashRegister {

    Money amountOfMoneyInStore;
    int[] denominations = new int[]{100_000, 50_000, 20_000, 10_000, 5_000, 2_000, 1_000, 500, 200, 100, 1};

    public CashRegister(){
        String moneyDatabase = "src/main/java/cashRegisterMoney.txt";
        try {
            FileReader fileReader = new FileReader(moneyDatabase);
            BufferedReader reader = new BufferedReader(fileReader);
            String readLine = reader.readLine();
            if(readLine == null){
                amountOfMoneyInStore = new Money(0);
            } else {
                amountOfMoneyInStore = new Money(Long.parseLong(readLine));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CashRegister(String cashRegisterMoneyDatabaseFileName){
        try {
            FileReader fileReader = new FileReader(cashRegisterMoneyDatabaseFileName);
            BufferedReader reader = new BufferedReader(fileReader);
            String readLine = reader.readLine();

            if(readLine == null){
                amountOfMoneyInStore = new Money(0);
            } else if(Long.parseLong(readLine) < 0) {
                throw new IllegalArgumentException();
            } else{
                amountOfMoneyInStore = new Money(Long.parseLong(readLine));
            }
            reader.close();
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public long getAmountOfMoneyInStore(){
        return amountOfMoneyInStore.getAmountInMinorUnit();
    }

    public void payByCard(long amountInMinorUnit, String fileName){
        writeToDatabaseFile(amountInMinorUnit, fileName);
        amountOfMoneyInStore.add(amountInMinorUnit);
    }

    public HashMap<CashMoney, Integer> payByCash(HashMap<CashMoney, Integer> payment, long actualCostInMinorUnit, String filename){
        long amountInCash = 0;
        HashMap<CashMoney, Integer> returnWallet = new HashMap<>();
        for(CashMoney cash: payment.keySet()){
            for(int i = 0; i < payment.get(cash); i++){
                amountInCash += cash.getCashMoneyDenomination();
            }
        }
        if(amountInCash < actualCostInMinorUnit){
            throw new IllegalArgumentException();
        }
        if(amountInCash > actualCostInMinorUnit){
            long change = amountInCash - actualCostInMinorUnit;
            returnWallet = calculateChange(change);
        }
        writeToDatabaseFile(actualCostInMinorUnit, filename);
        amountOfMoneyInStore.add(actualCostInMinorUnit);
        return returnWallet;
    }

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

    private void writeToDatabaseFile(long amountInMinorUnit, String fileName){
        try{
            if(amountInMinorUnit < 0){
                throw new IllegalArgumentException();
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



}

































