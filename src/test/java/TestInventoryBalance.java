import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestInventoryBalance {

    static final int VALID_AMOUNT_TO_INCREASE_BY = 5;
    static final int VALID_AMOUNT_TO_DECREASE_BY = -10;
    static final String FILE_PATH = "src/main/resources/products_testing_data.csv";

    @Test
    void increasesCountOfProduct_when_tryingToIncreaseCountByValidAmount() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemInInventory = InventoryLoader.createSingleProductFromTextFile(FILE_PATH);

        int previousCountOfItem = ib.getCount(itemInInventory);

        ib.adjustAmount(itemInInventory, VALID_AMOUNT_TO_INCREASE_BY);
        int currentCountOfItem = ib.getCount(itemInInventory);

        assertEquals(currentCountOfItem, previousCountOfItem + VALID_AMOUNT_TO_INCREASE_BY);
    }

    @Test
    void decreaseCountOfProduct_when_tryingToDecreaseCountByValidAmount() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemInInventory = InventoryLoader.createSingleProductFromTextFile(FILE_PATH);

        int previousCountOfItem = ib.getCount(itemInInventory);

        ib.adjustAmount(itemInInventory, VALID_AMOUNT_TO_DECREASE_BY);
        int currentCountOfItem = ib.getCount(itemInInventory);

        assertEquals(currentCountOfItem, previousCountOfItem + VALID_AMOUNT_TO_DECREASE_BY);
    }

    @Test
    void throwsException_when_tryingToIncreaseCountOfProductNotInInventory() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemNotInInventory = createProductNotPresentInInventory();

        assertThrows(NoSuchElementException.class, () -> {
            ib.adjustAmount(itemNotInInventory, VALID_AMOUNT_TO_INCREASE_BY);
        });

    }

    @Test
    void throwsException_when_tryingToDecreaseCountOfProductNotInInventory() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product productNotInInventory = createProductNotPresentInInventory();

        assertThrows(NoSuchElementException.class, () -> {
            ib.adjustAmount(productNotInInventory, VALID_AMOUNT_TO_DECREASE_BY);
        });

    }

    @Test
    void throwsException_when_tryingToRemoveProductNotInInventory() {
        InventoryBalance ib = new InventoryBalance();
        Product productNotInInventory = createProductNotPresentInInventory();

        assertThrows(NoSuchElementException.class, () -> {
            ib.removeProduct(productNotInInventory);
        });
    }

    @Test
    void increasesCountInInventory_when_itemIsIncremented() {
        InventoryBalance ib = new InventoryBalance();
        Product product = createProductNotPresentInInventory();
        ib.addProduct(product);

        int originalAmount = ib.getCount(product);
        ib.increment(product);
        int currentAmount = ib.getCount(product);

        assertEquals(currentAmount, originalAmount + 1);
    }

    @Test
    void decreasesCountInInventory_when_itemIsDecremented() {
        InventoryBalance ib = new InventoryBalance();
        Product product = createProductNotPresentInInventory();
        ib.addProduct(product);
        
        int originalAmount = ib.getCount(product);
        ib.decrement(product);
        int currentAmount = ib.getCount(product);

        assertEquals(currentAmount, originalAmount - 1);
    }

    @Test
    void displaysCountOfEveryItem_when_totalInventoryIsPrinted() {

    }

    /**
     * Support method used to create a Product with junk values.
     * @return a "junk" Product not present in inventory.
     */
    private Product createProductNotPresentInInventory() {
        return new Product.ProductBuilder("AAA", "BBB")
                .setPrice(100_00)
                .setAmount(10)
                .setVatRate(VAT.STANDARD)
                .setDiscount(0)
                .build();
    }
}
