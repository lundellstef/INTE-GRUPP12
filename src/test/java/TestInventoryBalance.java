import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestInventoryBalance {

    static final int VALID_AMOUNT_TO_INCREASE_BY = 5;
    static final int VALID_AMOUNT_TO_DECREASE_BY = -10;
    static final String FILE_PATH = "src/main/resources/products_testing_data.csv";

    static final String DEFAULT_PRODUCT_NAME = "Product Name";

    @Test
    void increasesCountOfProduct_when_adjustingValue_WithValidPositiveAmount() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemInInventory = InventoryLoader.createSingleProductFromTextFile(FILE_PATH);

        int previousCountOfItem = ib.getAmount(itemInInventory);

        ib.adjustAmount(itemInInventory, VALID_AMOUNT_TO_INCREASE_BY);
        int currentCountOfItem = ib.getAmount(itemInInventory);

        assertEquals(currentCountOfItem, previousCountOfItem + VALID_AMOUNT_TO_INCREASE_BY);
    }

    @Test
    void decreaseCountOfProduct_when_adjustingValue_WithValidNegativeAmount() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemInInventory = InventoryLoader.createSingleProductFromTextFile(FILE_PATH);

        int previousCountOfItem = ib.getAmount(itemInInventory);

        ib.adjustAmount(itemInInventory, VALID_AMOUNT_TO_DECREASE_BY);
        int currentCountOfItem = ib.getAmount(itemInInventory);

        assertEquals(currentCountOfItem, previousCountOfItem + VALID_AMOUNT_TO_DECREASE_BY);
    }

    @Test
    void throwsException_when_adjustingValue_WithInvalidAmount() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemInInventory = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        ib.addProduct(itemInInventory);
        System.out.println(ib.getAmount(itemInInventory));
        System.out.println(itemInInventory.getAmount());

        itemInInventory.setAmount(5);
        System.out.println();
        System.out.println(ib.contains(itemInInventory));

        System.out.println(ib.getAmount(itemInInventory));
        System.out.println(itemInInventory.getAmount());
    }

    @Test
    void throwsException_when_tryingToIncreaseCountOfProductNotInInventory() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemNotInInventory = createDefaultProduct(DEFAULT_PRODUCT_NAME);

        assertThrows(NoSuchElementException.class, () -> ib.adjustAmount(itemNotInInventory, VALID_AMOUNT_TO_INCREASE_BY));

    }

    @Test
    void throwsException_when_tryingToDecreaseCountOfProductNotInInventory() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product productNotInInventory = createDefaultProduct(DEFAULT_PRODUCT_NAME);

        assertThrows(NoSuchElementException.class, () -> ib.adjustAmount(productNotInInventory, VALID_AMOUNT_TO_DECREASE_BY));

    }

    @Test
    void removesProduct_when_removingProductInInventory() {
        InventoryBalance ib = new InventoryBalance();
        Product addedProduct = createDefaultProduct(DEFAULT_PRODUCT_NAME);

        ib.addProduct(addedProduct);
        // Additional assert added to ensure that the product has been properly added before it is removed.
        assertTrue(ib.contains(addedProduct));

        ib.removeProduct(addedProduct);
        assertFalse(ib.contains(addedProduct));
    }

    @Test
    void throwsException_when_tryingToRemoveProductNotInInventory() {
        InventoryBalance ib = new InventoryBalance();
        Product productNotInInventory = createDefaultProduct(DEFAULT_PRODUCT_NAME);

        assertThrows(NoSuchElementException.class, () -> ib.removeProduct(productNotInInventory));
    }

    @Test
    void throwsException_when_tryingToAddProductAlreadyInInventory() {
        InventoryBalance ib = new InventoryBalance();
        Product productToAdd = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        ib.addProduct(productToAdd);

        assertThrows(IllegalArgumentException.class, () -> ib.addProduct(productToAdd));
    }

    @Test
    void increasesCountInInventory_when_itemIsIncremented() {
        InventoryBalance ib = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        ib.addProduct(product);

        int originalAmount = ib.getAmount(product);
        ib.increment(product);
        int currentAmount = ib.getAmount(product);

        assertEquals(currentAmount, originalAmount + 1);
    }

    @Test
    void decreasesCountInInventory_when_itemIsDecremented() {
        InventoryBalance ib = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        ib.addProduct(product);

        int originalAmount = ib.getAmount(product);
        ib.decrement(product);
        int currentAmount = ib.getAmount(product);

        assertEquals(currentAmount, originalAmount - 1);
    }

    @Test
    void displaysEveryProduct_when_totalInventoryIsPrinted() {
        InventoryBalance ib = new InventoryBalance();

        Product product1 = createDefaultProduct("AAA");
        Product product2 = createDefaultProduct("BBB");
        Product product3 = createDefaultProduct("CCC");

        ib.addProduct(product1);
        ib.addProduct(product2);
        ib.addProduct(product3);

        String inventoryContents = ib.toString();

        boolean containsFirstProduct = inventoryContents.contains(product1.toString());
        boolean containsSecondProduct = inventoryContents.contains(product2.toString());
        boolean containsThirdProduct = inventoryContents.contains(product3.toString());

        boolean containsAll = containsFirstProduct && containsSecondProduct && containsThirdProduct;

        assertTrue(containsAll);
    }

    /**
     * Support method used to create a Product with junk values.
     *
     * @return a "junk" Product not present in inventory.
     */
    private Product createDefaultProduct(String productName) {
        return new Product.ProductBuilder("Brand Name", productName)
                .setPrice(100_00)
                .setAmount(10)
                .setVatRate(VAT.STANDARD)
                .setDiscount(0)
                .build();
    }
}
