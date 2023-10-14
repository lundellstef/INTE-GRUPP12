import java.util.HashMap;
import java.util.Map;

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

    // Support-method for readability.
    private Product get(Product product) {
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
