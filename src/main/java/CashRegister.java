import java.io.*;
import java.util.ArrayList;

public class CashRegister {

    Money amountOfMoneyInStore;

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
            amountOfMoneyInStore.add(amountInMinorUnit);
            writer.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }


}

































