import java.util.Date;
import java.util.Map;

public class Receipt {

    Map<Product, Integer> items;
    int totalPriceExVat;
    Date date;
    int totalVat;
    int totalDiscount;
    int totalPrice;

    Receipt(Purchase purchase){
        items = purchase.getPurchasedItems();
        totalPriceExVat = purchase.getTotalPriceExVat();
        totalVat = purchase.getTotalVAT();
        totalDiscount = purchase.getTotalDiscount();
        totalPrice = purchase.getTotalPrice();
        date = new Date();
    }



    /**
     * prints the receipt, right now as a long string, this method needs refactoring
     * @return the receipt as a String
     */
    public String printReceipt(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Du har köpt varorna:");
        for(Product item : items.keySet()){
            stringBuilder.append(item.toString()).append(" antal: ").append(items.get(item));
        }
        stringBuilder.append("Pris ex moms: ").append(totalPriceExVat);
        stringBuilder.append("Moms: ").append(totalVat);
        stringBuilder.append("Rabatt: ").append("-").append(totalDiscount);
        stringBuilder.append("Totalpris: ").append(totalPrice);

        return stringBuilder.toString();
    }
}
