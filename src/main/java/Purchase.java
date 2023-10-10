import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Purchase {

    Map<ProductItem, Integer> items;
    int totalPrice;

    public Purchase(){
        items = new HashMap<>();
        totalPrice = 0;
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
        incrementTotalPrice(item);
    }

    /**
     *increments totalPrice price based on price of item to be purchased.
     * @param item the item to decide price for.
     */
    private void incrementTotalPrice(ProductItem item){
        if(item.hasDiscount()) {
            totalPrice += (item.getPrice() * ((100 - item.getDiscount())/100.0));
        } else{
            totalPrice += item.getPrice();
        }
    }

    /**
     * Removes an item from the list. Only one item can be removed at a time and it the item to be removed is not
     * in the list of items, an exception is thrown.
     * TANKE: SKA VI GÖRA SÅ MAN FÅR VÄLJA FRÅN LISTAN AV ITEMS SÅ DET BLIR OMÖJLIGT ATT TA BORT NÅGOT SOM INTE FINNS?
     * @param item
     * @return
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
        decrementPrice(item);
        return item;
    }

    /**
     *decrements totalPrice price based on price of item to be purchased.
     * @param item the item to decide price for.
     */
    private void decrementPrice(ProductItem item){
        if(item.hasDiscount()) {
            totalPrice -= (item.getPrice() * ((100 - item.getDiscount())/100.0));
        } else{
            totalPrice -= item.getPrice();
        }
    }

    public int getTotalPrice(){
        return totalPrice;
    }

    public Map<ProductItem, Integer> getPurchasedItems(){
        return items;
    }
}
