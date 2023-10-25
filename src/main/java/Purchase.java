import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Purchase {

   private final Map<Product, Integer> items;
    private int totalPriceExVAT;
    private int totalVAT;
    private int totalDiscount;

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
    public void scanItem(Product item){
        if(item.getAmount() == 0){
            throw new IllegalArgumentException("There are no more of this product in store");
        }
        if(items.containsKey(item)){
            int currentAmount = items.get(item);
            items.put(item, currentAmount + 1);
            item.decrement();
        }
        else{
            items.put(item, 1);
            item.decrement();
        }
        if(item.hasDiscount()){
            incrementTotalDiscount(item);
        }
        incrementTotalPriceExVat(item);
        incrementTotalVat(item);
    }

    /**
     * increments the total discount
     * @param item the item that is added to the purchase
     */
   private void incrementTotalDiscount(Product item){
        totalDiscount += item.getDiscountAmount();
    }


    /**
     * increments the total amount of money that is added to the purchase due to the product's individual VAT
     * @param item the item to be added to the purchase
     */
    private void incrementTotalVat(Product item){
        totalVAT += item.getVatAmountOfPrice();
    }

    /**
     *increments totalPrice price based on price of item to be purchased.
     * @param item the item to decide price for.
     */
    private void incrementTotalPriceExVat(Product item){
        totalPriceExVAT += item.getPriceInMinorUnits();
    }

    /**
     * Removes an item from the list. Only one item can be removed at a time and if the item to be removed is not
     * in the list of items, an exception is thrown.
     * @param item the item to be removed
     */
    public void removeScannedItem(Product item){
        if(!items.containsKey(item)){
            throw new NoSuchElementException("The item you are trying to remove has not been purchased");
        }
        if(items.get(item) > 1){
            int currentAmount = items.get(item);
            items.put(item, currentAmount - 1);
            item.increment();
        } else {
            items.remove(item);
            item.increment();
        }
        if(item.hasDiscount()){
            decrementTotalDiscount(item);
        }
        decrementPriceExVat(item);
        decrementTotalVat(item);

    }

    /**
     * decrements the total discount
     * @param item the item that is removed
     */
    private void decrementTotalDiscount(Product item){
        totalDiscount -= item.getDiscountAmount();
    }

    /**
     * decrements the total amount of money that is removed to the purchase due to the product's individual VAT
     * @param item the item to be added to the purchase
     */
    private void decrementTotalVat(Product item){
        totalVAT -= item.getVatAmountOfPrice();
    }

    /**
     *decrements totalPrice price based on price of item to be purchased.
     * @param item the item to decide price for.
     */
    private void decrementPriceExVat(Product item){
        totalPriceExVAT -= item.getPriceInMinorUnits();
    }

    public int getTotalPriceExVat(){
        return totalPriceExVAT;
    }

    public int getTotalPrice(){
        return (totalPriceExVAT + totalVAT) - totalDiscount;
    }

    public Map<Product, Integer> getPurchasedItems(){
        return items;
    }


    public int getTotalVAT(){
        return totalVAT;
    }

    public int getTotalDiscount(){
        return totalDiscount;
    }

    public void cancelPurchase(){
        for(Product product : items.keySet()){
            int amountOfProductPurchased = items.get(product);
            int currentAmount = product.getAmount();
            product.setAmount(currentAmount + amountOfProductPurchased);
        }
        items.clear();
        totalDiscount = 0;
        totalVAT = 0;
        totalPriceExVAT = 0;
    }
}
