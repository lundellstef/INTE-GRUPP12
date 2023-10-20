import java.util.*;

/**
 * Class representing the inventory balance for the store.
 * Products are stored in a Map: the products' hashcode is the key, and the Product is the value. (For faster search.)
 * <p>
 * The inventory holds direct references to the actual Products.
 * This means that, if the amount of a Product is changed elsewhere (without interacting with the inventory)
 * the inventory will still reflect that change.
 * <p>
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
     * @throws NoSuchElementException if trying to remove a Product not present in the inventory.
     */
    public void removeProduct(Product product) throws NoSuchElementException {
        Product removedProduct = inventory.remove(product.hashCode());
        if (removedProduct == null) {
            throw new NoSuchElementException("Product not in inventory.");
        } else {
        }
    }

    /**
     * Overloaded remove method.
     */
    public Product removeProduct(String brandName, String productName) {
        Product tempProduct = new Product.ProductBuilder(brandName, productName).build();
        Product removedProduct = inventory.remove(tempProduct.hashCode());
        if (removedProduct == null) {
            throw new NoSuchElementException("Product not in inventory.");
        } else {
            return removedProduct;
        }
    }

    /**
     * Checks the inventory for products that are low in stock.
     *
     * @return a list of products that are currently low in stock.
     */
    public List<String> getProductsLowInStock() {
        ArrayList<String> listOfProductsLowInStock = new ArrayList<>();
        for (Product product : inventory.values()) {
            if (product.getAmount() < 5) {
                listOfProductsLowInStock.add(product.getBrandName() + " " + product.getProductName());
            }
        }
        return listOfProductsLowInStock;
    }

    public boolean contains(Product product) {
        return inventory.containsKey(product.hashCode());
    }

    /**
     * Support method used to increase readability and handle exception when a Product is not in inventory.
     *
     * @param product is the product to be fetched from inventory.
     * @return the Product from inventory.
     * @throws NoSuchElementException when the searched Product is not found in inventory.
     */
    public Product get(Product product) throws NoSuchElementException {
        if (inventory.get(product.hashCode()) == null) {
            throw new NoSuchElementException("Product not in inventory.");
        }
        return inventory.get(product.hashCode());
    }

    /**
     * Overloaded get method.
     */
    public Product get(String brandName, String productName) {
        Product product = new Product.ProductBuilder(brandName, productName).build();
        if (inventory.containsKey(product.hashCode())) {
            return inventory.get(product.hashCode());
        } else {
            throw new NoSuchElementException("Product not in inventory.");
        }
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
