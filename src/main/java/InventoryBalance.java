import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InventoryBalance {

    private final Map<Integer, Product> inventory;

    public InventoryBalance() {
        inventory = new HashMap<>();
    }

    public InventoryBalance(File file) {
        this();
        readFromFile(file);
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

    /**
     * IMPORTANT: Call br.readLine() once before reading values from csv-file.
     * @param file is the file in /resources to read values from.
     */
    private void readFromFile(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine = br.readLine();
            String[] values;
            while ((currentLine = br.readLine()) != null) {
                values = currentLine.split(",");
                Product product = createProductFromString(values);
                System.out.println(product);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     *
     * @param values is the String array containing all the values needed for the product.
     * @return a new Product created from the values.
     */
    private Product createProductFromString(String[] values) {
        String brandName = values[0];
        String productName = values[1];
        int priceInMinorUnits = Integer.parseInt(values[2]);
        VAT vatRate = readVatFromString(values[3]);
        int amount = Integer.parseInt(values[4]);
        int discount = Integer.parseInt(values[5]);

        return new Product.ProductBuilder(brandName,productName)
                .setPrice(priceInMinorUnits)
                .setVatRate(vatRate)
                .setAmount(amount)
                .setDiscount(discount)
                .build();
    }

    private VAT readVatFromString(String value) {
        return switch (value) {
            case "VAT.STANDARD" -> VAT.STANDARD;
            case "VAT.FOOD" -> VAT.FOOD;
            case "VAT.REDUCED" -> VAT.REDUCED;
            default -> VAT.NO_TAX;
        };
    }
}
