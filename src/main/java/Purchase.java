import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Purchase {

    Map<ProductItem, Integer> items;
    int totalPriceExVAT;
    int totalVAT;
    int totalDiscount;

    public Purchase(){
        items = new HashMap<>();
        totalPriceExVAT = 0;
        totalVAT = 0;
        totalDiscount = 0;
    }

    /**
     * When an item is scanned it is added to the list of items. If the item already is in list of items
     * the amount is incremented by 1. decidePrice-method determines price of item
     * @param item the item to be added to the list of items to be purchased.
     */
    public void scanItem(ProductItem item){
        if(items.containsKey(item)){
            int currentAmount = items.get(item);
            items.put(item, currentAmount + 1);
        } else{
            items.put(item, 1);
        }
        if(item.hasDiscount()){
            incrementTotalDiscount(item);
        }
        incrementTotalPriceExVat(item);
        incrementTotalVat(item);
        incrementTotalDiscount(item);
    }

    /**
     * increments the total discount
     * @param item the item that is added to the purchase
     */
    private void incrementTotalDiscount(ProductItem item){
        int discountPercentage = item.getDiscount();
        int vat = item.getVat().value;
        int priceIncVat = item.getPrice() * ((100 + vat)/100);
        totalDiscount += priceIncVat * (100 - discountPercentage);
    }

    /**
     * increments the total amount of money that is added to the purchase due to the product's individual VAT
     * @param item the item to be added to the purchase
     */
    private void incrementTotalVat(ProductItem item){
        int vatPercentage = item.getVat().value;
        totalVAT += (item.getPrice() * ((100 + vatPercentage)/100)) - item.getPrice();
    }

    /**
     *increments totalPrice price based on price of item to be purchased.
     * @param item the item to decide price for.
     */
    private void incrementTotalPriceExVat(ProductItem item){
        if(item.hasDiscount()) {
            totalPriceExVAT += (item.getPrice() * ((100 - item.getDiscount())/100.0));
        } else{
            totalPriceExVAT += item.getPrice();
        }
    }

    /**
     * Removes an item from the list. Only one item can be removed at a time and it the item to be removed is not
     * in the list of items, an exception is thrown.
     * TANKE: SKA VI GÖRA SÅ MAN FÅR VÄLJA FRÅN LISTAN AV ITEMS SÅ DET BLIR OMÖJLIGT ATT TA BORT NÅGOT SOM INTE FINNS?
     * @param item the item to be removed
     * @return the item if it has been successfully removed
     */
    public ProductItem removeScannedItem(ProductItem item){
        if(!items.containsKey(item)){
            throw new NoSuchElementException("The item you are trying to remove has not been purchased");
        }
        if(items.get(item) > 1){
            int currentAmount = items.get(item);
            items.put(item, currentAmount - 1);
        } else {
            items.remove(item);
        }
        decrementPriceExVat(item);
        decrementTotalVat(item);
        decrementTotalDiscount(item);
        return item;
    }

    /**
     * decrements the total discount
     * @param item the item that is removed
     */

    private void decrementTotalDiscount(ProductItem item){
        int discountPercentage = item.getDiscount();
        int vat = item.getVat().value;
        int priceIncVat = item.getPrice() * ((100 + vat)/100);
        totalDiscount -= priceIncVat * (100 - discountPercentage);
    }

    /**
     * decrements the total amount of money that is removed to the purchase due to the product's individual VAT
     * @param item the item to be added to the purchase
     */
    private void decrementTotalVat(ProductItem item){
        int vatPercentage = item.getVat().value;
        totalVAT -= (item.getPrice() * ((100 + vatPercentage)/100)) - item.getPrice();
    }

    /**
     *decrements totalPrice price based on price of item to be purchased.
     * @param item the item to decide price for.
     */
    private void decrementPriceExVat(ProductItem item){
        if(item.hasDiscount()) {
            totalPriceExVAT -= (item.getPrice() * ((100 - item.getDiscount())/100.0));
        } else{
            totalPriceExVAT -= item.getPrice();
        }
    }

    public int getTotalPriceExVat(){
        return totalPriceExVAT;
    }

    public Map<ProductItem, Integer> getPurchasedItems(){
        return items;
    }


    public int getTotalVAT(){
        return totalVAT;
    }

    public int getTotalDiscount(){
        return totalDiscount;
    }
}
