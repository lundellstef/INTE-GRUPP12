import java.time.LocalDate;
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
    private static final int PRODUCT_LOW_IN_STOCK = 5;
    private static final int DAYS_BEFORE_SHORT_DATE_WARNING = 5;

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
    public void deleteProduct(Product product) throws NoSuchElementException {
        Product removedProduct = inventory.remove(product.hashCode());
        if (removedProduct == null) {
            throw new NoSuchElementException("Product not in inventory.");
        }
    }

    /**
     * Overloaded remove method.
     * A temporary product is created with the same name and brand, but with junk values.
     * That product is then sent to deleteProduct(product).
     */
    public void deleteProduct(String brandName, String productName) {
        Product tempProduct = new Product.ProductBuilder(brandName, productName)
                .setPrice(1)
                .setAmount(1)
                .setVatRate(VAT.STANDARD)
                .build();
        deleteProduct(tempProduct);
    }

    /**
     * Checks the inventory for products that are low in stock.
     *
     * @return a list of products that are currently low in stock.
     */
    public List<Product> getProductsLowInStock() {
        ArrayList<Product> listOfProductsLowInStock = new ArrayList<>();

        for (Product product : inventory.values()) {
            if (product.getAmount() < PRODUCT_LOW_IN_STOCK) {
                listOfProductsLowInStock.add(product);
            }
        }
        return listOfProductsLowInStock;
    }

    /**
     * Checks the inventory for products that are about to reach their expiration date.
     * Performs check based on constant DAYS_BEFORE_SHORT_DATE_WARNING.
     *
     * @return the list of products that are about to expire.
     */
    public List<Product> getProductsAboutToExpire() {
        ArrayList<Product> productsAboutToExpire = new ArrayList<>();
        LocalDate expirationDate;
        int daysDifference;
        for (Product product : inventory.values()) {
            if (product.hasAnExpirationDate()) {
                expirationDate = product.getExpirationDate();
                daysDifference = expirationDate.getDayOfYear() - LocalDate.now().getDayOfYear();
                if (daysDifference <= DAYS_BEFORE_SHORT_DATE_WARNING) {
                    productsAboutToExpire.add(product);
                }
            }
        }
        return productsAboutToExpire;
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
     * Creates a product with junk values in order to pass to other get method.
     */
    public Product get(String brandName, String productName) {
        Product product = new Product.ProductBuilder(brandName, productName)
                .setAmount(1)
                .setPrice(1)
                .setVatRate(VAT.STANDARD)
                .build();
        return get(product);
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
