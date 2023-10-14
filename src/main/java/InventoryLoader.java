import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Support class used to create InventoryBalance and Product objects from text files.
 */
public class InventoryLoader {

    /**
     * Returns an InventoryBalance loaded with all the data from [filePath].
     * IMPORTANT: Call br.readLine() once before reading values from csv-file.
     * @param filePath is the path to the file to read values from.
     */
    public static InventoryBalance createInventoryBalanceFromTextFile(String filePath) {
        InventoryBalance inventoryBalance = new InventoryBalance();
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine = br.readLine();
            String[] values;
            while ((currentLine = br.readLine()) != null) {
                values = currentLine.split(",");
                Product product = createProductFromString(values);
                inventoryBalance.addProduct(product);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return inventoryBalance;
    }

    /**
     * @param values is the String array containing all the values needed for the product.
     * @return a new Product created from the values.
     */
    private static Product createProductFromString(String[] values) {
        String brandName = values[0];
        String productName = values[1];
        int priceInMinorUnits = Integer.parseInt(values[2]);
        VAT vatRate = readVatFromString(values[3]);
        int amount = Integer.parseInt(values[4]);
        int discount = Integer.parseInt(values[5]);

        return new Product.ProductBuilder(brandName, productName)
                .setPrice(priceInMinorUnits)
                .setVatRate(vatRate)
                .setAmount(amount)
                .setDiscount(discount)
                .build();
    }

    private static VAT readVatFromString(String value) {
        return switch (value) {
            case "VAT.STANDARD" -> VAT.STANDARD;
            case "VAT.FOOD" -> VAT.FOOD;
            case "VAT.REDUCED" -> VAT.REDUCED;
            default -> VAT.NO_TAX;
        };
    }

    /**
     * Returns a Product from the list of products in the csv-file.
     * The Product returned is the first valid line from the csv-file.
     * IMPORTANT: Call br.readLine() once before reading values from csv-file.
     * @param filePath is the path to the file to read values from.
     */
    public static Product createSingleProductFromTextFile(String filePath) {
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currentLine = br.readLine();
            currentLine = br.readLine();
            String[] values = currentLine.split(",");
            return createProductFromString(values);

        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }
}
