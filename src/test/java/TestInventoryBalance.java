import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestInventoryBalance {

    static final int VALID_AMOUNT_TO_INCREASE_BY = 5;
    static final int VALID_AMOUNT_TO_DECREASE_BY = -10;
    static final String FILE_PATH = "src/main/resources/products_testing_data.csv";

    @Test
    void increasesCountOfItem_when_tryingToIncreaseCountByValidAmount() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemInInventory = InventoryLoader.createSingleProductFromTextFile(FILE_PATH);

        int previousCountOfItem = ib.getCount(itemInInventory);

        ib.updateCount(itemInInventory, VALID_AMOUNT_TO_INCREASE_BY);
        int currentCountOfItem = ib.getCount(itemInInventory);

        assertEquals(currentCountOfItem, previousCountOfItem + VALID_AMOUNT_TO_INCREASE_BY);
    }

    @Test
    void decreaseCountOfItem_when_tryingToDecreaseCountByValidAmount() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemInInventory = InventoryLoader.createSingleProductFromTextFile(FILE_PATH);

        int previousCountOfItem = ib.getCount(itemInInventory);

        ib.updateCount(itemInInventory, VALID_AMOUNT_TO_DECREASE_BY);
        int currentCountOfItem = ib.getCount(itemInInventory);

        assertEquals(currentCountOfItem, previousCountOfItem + VALID_AMOUNT_TO_DECREASE_BY);
    }

    @Test
    void throwsException_when_tryingToIncreaseCountOfItemNotInInventory() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemNotInInventory = createJunkProductNotPresentInInventory();

        assertThrows(NoSuchElementException.class, () -> {
            ib.updateCount(itemNotInInventory, VALID_AMOUNT_TO_INCREASE_BY);
        });

    }

    @Test
    void throwsException_when_tryingToDecreaseCountOfItemNotInInventory() {
        InventoryBalance ib = InventoryLoader.createInventoryBalanceFromTextFile(FILE_PATH);
        Product itemNotInInventory = createJunkProductNotPresentInInventory();

        assertThrows(NoSuchElementException.class, () -> {
            ib.updateCount(itemNotInInventory, VALID_AMOUNT_TO_DECREASE_BY);
        });

    }

    @Test
    void increasesCountInInventory_when_itemIsIncremented() {

    }

    @Test
    void decreasesCountInInventory_when_itemIsDecremented() {

    }

    @Test
    void displaysCountOfEveryItem_when_totalInventoryIsPrinted() {

    }

    /**
     * Support method used to create a Product with junk values.
     * @return a "junk" Product not present in inventory.
     */
    private Product createJunkProductNotPresentInInventory() {
        return new Product.ProductBuilder("AAA", "BBB")
                .setPrice(10_00)
                .setAmount(1)
                .setVatRate(VAT.STANDARD)
                .setDiscount(0)
                .build();
    }
}
