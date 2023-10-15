import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestInventoryBalance {

    static final int VALID_AMOUNT_TO_INCREASE_BY = 5;
    static final int VALID_AMOUNT_TO_DECREASE_BY = -10;
    static final String TEST_DATA_FILE_PATH = "src/test/resources/product_test_data.csv";

    static final String DEFAULT_PRODUCT_NAME = "Product Name";

    @Test
    void increasesCountOfProduct_when_adjustingValue_WithValidPositiveAmount() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        inventoryBalance.addProduct(product);

        int previousCountOfItem = inventoryBalance.getAmount(product);

        inventoryBalance.adjustAmount(product, VALID_AMOUNT_TO_INCREASE_BY);
        int currentCountOfItem = inventoryBalance.getAmount(product);

        assertEquals(currentCountOfItem, previousCountOfItem + VALID_AMOUNT_TO_INCREASE_BY);
    }

    @Test
    void decreaseCountOfProduct_when_adjustingValue_WithValidNegativeAmount() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        inventoryBalance.addProduct(product);

        int previousCountOfItem = inventoryBalance.getAmount(product);

        inventoryBalance.adjustAmount(product, VALID_AMOUNT_TO_DECREASE_BY);
        int currentCountOfItem = inventoryBalance.getAmount(product);

        assertEquals(currentCountOfItem, previousCountOfItem + VALID_AMOUNT_TO_DECREASE_BY);
    }

    @Test
    void throwsException_when_adjustingValue_WithInvalidAmount() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        inventoryBalance.addProduct(product);

        inventoryBalance.adjustAmount(product, VALID_AMOUNT_TO_INCREASE_BY);

        assertThrows(IllegalArgumentException.class, () -> inventoryBalance.adjustAmount(product, Integer.MAX_VALUE));
    }

    @Test
    void throwsException_when_tryingToAdjustValueOf_ProductNotInInventory() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);

        assertThrows(NoSuchElementException.class, () -> inventoryBalance.adjustAmount(product, VALID_AMOUNT_TO_INCREASE_BY));
    }

    @Test
    void removesProduct_when_removingProductInInventory() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        inventoryBalance.addProduct(product);

        // Additional assert added to ensure that the product has been properly added before it is removed.
        assertTrue(inventoryBalance.contains(product));

        inventoryBalance.removeProduct(product);
        assertFalse(inventoryBalance.contains(product));
    }

    @Test
    void throwsException_when_tryingToRemove_ProductNotInInventory() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);

        assertThrows(NoSuchElementException.class, () -> inventoryBalance.removeProduct(product));
    }

    @Test
    void throwsException_when_tryingToAddProductAlreadyInInventory() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        inventoryBalance.addProduct(product);

        // Additional assert added to ensure that the product has been properly added before it is removed.
        assertTrue(inventoryBalance.contains(product));

        assertThrows(IllegalArgumentException.class, () -> inventoryBalance.addProduct(product));
    }

    @Test
    void increasesCountInInventory_when_itemIsIncremented() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        inventoryBalance.addProduct(product);

        int originalAmount = inventoryBalance.getAmount(product);

        inventoryBalance.increment(product);

        int currentAmount = inventoryBalance.getAmount(product);

        assertEquals(currentAmount, originalAmount + 1);
    }

    @Test
    void decreasesCountInInventory_when_itemIsDecremented() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        inventoryBalance.addProduct(product);

        int originalAmount = inventoryBalance.getAmount(product);

        inventoryBalance.decrement(product);

        int currentAmount = inventoryBalance.getAmount(product);

        assertEquals(currentAmount, originalAmount - 1);
    }

    @Test
    void displaysEveryProduct_when_inventoryIsPrinted() {
        InventoryBalance inventoryBalance = new InventoryBalance();

        Product product1 = createDefaultProduct("AAA");
        Product product2 = createDefaultProduct("BBB");
        Product product3 = createDefaultProduct("CCC");

        inventoryBalance.addProduct(product1);
        inventoryBalance.addProduct(product2);
        inventoryBalance.addProduct(product3);

        String inventoryContents = inventoryBalance.toString();

        boolean containsFirstProduct = inventoryContents.contains(product1.toString());
        boolean containsSecondProduct = inventoryContents.contains(product2.toString());
        boolean containsThirdProduct = inventoryContents.contains(product3.toString());

        assertTrue(containsFirstProduct && containsSecondProduct && containsThirdProduct);
    }

    @Test
    void showsListOfProductsLowInStock_when_inventoryIsSearched() {
        InventoryBalance inventoryBalance = InventoryLoader.createInventoryBalanceFromTextFile(TEST_DATA_FILE_PATH);
        ArrayList<String> lowInStock = (ArrayList<String>) inventoryBalance.getProductsLowInStock();

        assertTrue(lowInStock.contains("Polarbröd Vetekaka") && lowInStock.contains("Kavli Mjukost"));
    }

    /**
     * Support method used to create a Product with typical values.
     *
     * @return a "junk" Product not present in inventory.
     */
    private Product createDefaultProduct(String productName) {
        return new Product.ProductBuilder("Brand Name", productName)
                .setPrice(2000_00)
                .setAmount(10)
                .setVatRate(VAT.STANDARD)
                .setDiscount(0)
                .build();
    }
}
