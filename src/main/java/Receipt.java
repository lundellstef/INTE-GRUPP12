import java.time.LocalDate;
import java.util.Map;

public class Receipt {

    final Map<Product, Integer> items;
    final int totalPriceExVat;
    final LocalDate date;
    final int totalVat;
    final int totalDiscount;
    final int totalPrice;

    Receipt(Purchase purchase){
        items = purchase.getPurchasedItems();
        totalPriceExVat = purchase.getTotalPriceExVat();
        totalVat = purchase.getTotalVAT();
        totalDiscount = purchase.getTotalDiscount();
        totalPrice = purchase.getTotalPrice();
        date = LocalDate.now();
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
            stringBuilder.append("\n").append(item.toString()).append(", antal: ").append(items.get(item));
        }
        stringBuilder.append("\n").append("Pris ex moms: ").append(totalPriceExVat/100).append("kr");
        stringBuilder.append("\n").append("Moms: ").append(totalVat/100).append("kr");
        stringBuilder.append("\n").append("Rabatt: ").append(totalDiscount/100).append("kr");
        stringBuilder.append("\n").append("Totalpris: ").append(totalPrice/100).append("kr");
        stringBuilder.append("\n").append("Datum: ").append(date);

        return stringBuilder.toString();
    }
}
