import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestInventoryBalance {

    static final int VALID_AMOUNT_TO_INCREASE_BY = 5;
    static final int VALID_AMOUNT_TO_DECREASE_BY = -10;

    @Test
    void increasesCountOfItem_when_tryingToIncreaseCountByValidAmount() {
        InventoryBalance ib = createDefaultInventoryBalanceForTesting();
        Product itemInInventory = createDefaultItemForTesting();

        int previousCountOfItem = ib.getCount(itemInInventory);

        ib.updateCount(itemInInventory, VALID_AMOUNT_TO_INCREASE_BY);
        int currentCountOfItem = ib.getCount(itemInInventory);

        assertEquals(currentCountOfItem, previousCountOfItem + VALID_AMOUNT_TO_INCREASE_BY);
    }

    @Test
    void decreaseCountOfItem_when_tryingToDecreaseCountByValidAmount() {
        InventoryBalance ib = createDefaultInventoryBalanceForTesting();
        Product itemInInventory = createDefaultItemForTesting();

        int previousCountOfItem = ib.getCount(itemInInventory);

        ib.updateCount(itemInInventory, VALID_AMOUNT_TO_DECREASE_BY);
        int currentCountOfItem = ib.getCount(itemInInventory);

        assertEquals(currentCountOfItem, previousCountOfItem + VALID_AMOUNT_TO_DECREASE_BY);
    }

    @Test
    void throwsException_when_tryingToIncreaseCountOfItemNotInInventory() {
        InventoryBalance ib = createDefaultInventoryBalanceForTesting();
        Product itemNotInInventory = createDefaultItemForTestingNotPresentInInventory();

        assertThrows(IllegalArgumentException.class, () -> {
            ib.updateCount(itemNotInInventory, VALID_AMOUNT_TO_INCREASE_BY);
        });

    }

    @Test
    void throwsException_when_tryingToDecreaseCountOfItemNotInInventory() {
        InventoryBalance ib = createDefaultInventoryBalanceForTesting();
        Product itemNotInInventory = createDefaultItemForTestingNotPresentInInventory();

        assertThrows(IllegalArgumentException.class, () -> {
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
}
