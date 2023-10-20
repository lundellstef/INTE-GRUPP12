import java.util.Date;
import java.util.Map;

public class Receipt {

    final Map<Product, Integer> items;
    final int totalPriceExVat;
    final Date date;
    final int totalVat;
    final int totalDiscount;
    final int totalPrice;

    Receipt(Purchase purchase){
        items = purchase.getPurchasedItems();
        totalPriceExVat = purchase.getTotalPriceExVat();
        totalVat = purchase.getTotalVAT();
        totalDiscount = purchase.getTotalDiscount();
        totalPrice = purchase.getTotalPrice();
        date = new Date();
    }



    /**
     * Method prints the receipt.
     * @return the receipt as a String
     */
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Du har köpt varorna:");
        for(Product item : items.keySet()){
            stringBuilder.append(System.getProperty("line.separator")).append(item.toString()).append(", antal: ").append(items.get(item));
        }
        stringBuilder.append(System.getProperty("line.separator")).append("Pris ex moms: ").append(totalPriceExVat/100).append("kr");
        stringBuilder.append(System.getProperty("line.separator")).append("Moms: ").append(totalVat/100).append("kr");
        stringBuilder.append(System.getProperty("line.separator")).append("Rabatt: ").append(totalDiscount/100).append("kr");
        stringBuilder.append(System.getProperty("line.separator")).append("Totalpris: ").append(totalPrice/100).append("kr");

        return stringBuilder.toString();
    }
}
