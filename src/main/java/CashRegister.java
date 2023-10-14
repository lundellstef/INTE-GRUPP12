import java.io.BufferedReader;
import java.io.FileReader;
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
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public long getAmountOfMoneyInStore(){
        return amountOfMoneyInStore.getAmountInMinorUnit();
    }

}

































