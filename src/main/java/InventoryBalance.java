import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class representing the inventory balance for the store.
 * Products are stored in a Map: the products' hashcode is the key, and the Product is the value. (For faster search.)
 */
public class InventoryBalance {

    private final Map<Integer, Product> inventory;

    public InventoryBalance() {
        inventory = new HashMap<>();
    }

    public void addProduct(Product product) {
        inventory.put(product.hashCode(), product);
    }

    public int getCount(Product product) {
        return get(product).getAmount();
    }

    // Change to "addToCount"
    public void updateCount(Product product, int amount) {
        int previousAmount = getCount(product);
        get(product).setAmount(previousAmount + amount);
    }

    /**
     * Support method used to increase readability and handle exception when a Product is not in inventory.
     * @param product is the product to be fetched from inventory.
     * @return the Product from inventory.
     * @throws NoSuchElementException when the searched Product is not found in inventory.
     */
    private Product get(Product product) throws NoSuchElementException{
        if (inventory.get(product.hashCode()) == null) {
            throw new NoSuchElementException("Product not in inventory.");
        }
        return inventory.get(product.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product p : inventory.values()) {
            sb.append(p);
            sb.append(" : ").append(p.getAmount()).append("st");
            sb.append("\n");
        }
        return sb.toString();
    }
}
