import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class representing the inventory balance for the store.
 * Products are stored in a Map: the products' hashcode is the key, and the Product is the value. (For faster search.)
 *
 * To create an inventory balance preloaded with values, see the InventoryLoader class.
 */
public class InventoryBalance {

    private final Map<Integer, Product> inventory;

    public InventoryBalance() {
        inventory = new HashMap<>();
    }

    /**
     * Adds a product to the inventory.
     * Adding a product that's already present in the inventory should NOT increase the amount of that product.
     * If you want to do that, you should call adjustValue(product) instead.
     *
     * @param product is the Product to be added.
     * @throws IllegalArgumentException if trying to add an item already present in the inventory.
     */
    public void addProduct(Product product) throws IllegalArgumentException {
        if (contains(product)) {
            throw new IllegalArgumentException("Product already present in inventory.");
        }
        inventory.put(product.hashCode(), product);
    }

    /**
     * Removes a product from the inventory.
     * Removing a product that's not present in the inventory is NOT possible.
     *
     * @param product is the Product to be removed.
     * @return the removed Product (mimicking the behaviour from map.remove()).
     * @throws NoSuchElementException if trying to remove a Product not present in the inventory.
     */
    public Product removeProduct(Product product) throws NoSuchElementException {
        Product removedProduct = inventory.remove(product.hashCode());
        if (removedProduct == null) {
            throw new NoSuchElementException("Product not in inventory.");
        } else {
            return removedProduct;
        }
    }

    /**
     * Returns the current amount of a chosen Product in inventory.
     * Uses the get() support method, and as such, exceptions are handled there.
     *
     * @param product is the Product to get the count of.
     * @return the current amount of that Product in inventory.
     */
    public int getAmount(Product product) {
        return get(product).getAmount();
    }

    /**
     * Adjusts the amount of a Product in inventory.
     * Both positive and negative amount values are valid.
     *
     * @param product is the Product which amount is to be adjusted.
     * @param amount  is the value to adjust the amount by.
     */
    public void adjustAmount(Product product, int amount) {
        int previousAmount = getAmount(product);
        get(product).setAmount(previousAmount + amount);
    }

    public boolean contains(Product product) {
        return inventory.containsKey(product.hashCode());
    }

    public void increment(Product product) {
        adjustAmount(product, 1);
    }

    public void decrement(Product product) {
        adjustAmount(product, -1);
    }

    /**
     * Support method used to increase readability and handle exception when a Product is not in inventory.
     *
     * @param product is the product to be fetched from inventory.
     * @return the Product from inventory.
     * @throws NoSuchElementException when the searched Product is not found in inventory.
     */
    private Product get(Product product) throws NoSuchElementException {
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
