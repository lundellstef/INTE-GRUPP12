import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestInventoryBalance {

    static final String DEFAULT_PRODUCT_NAME = "Product Name";
    static final String TEST_DATA_FILE_PATH = "src/test/resources/product_test_data.csv";
    static final String FIRST_PRODUCT_LOW_IN_STOCK = "Polarbröd Vetekaka";
    static final String SECOND_PRODUCT_LOW_IN_STOCK = "Kavli Mjukost";

    @Test
    void removesProduct_when_removingProductInInventory() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);
        inventoryBalance.addProduct(product);

        // Additional assert added to ensure that the product has been properly added before it is removed.
        assertTrue(inventoryBalance.contains(product));

        inventoryBalance.deleteProduct(product);
        assertFalse(inventoryBalance.contains(product));
    }

    @Test
    void throwsException_when_tryingToRemove_ProductNotInInventory() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = createDefaultProduct(DEFAULT_PRODUCT_NAME);

        assertThrows(NoSuchElementException.class, () -> inventoryBalance.deleteProduct(product));
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
    void findsEveryProductLowInStock_when_displayingProductsLowInStock() {
        InventoryBalance inventoryBalance = InventoryLoader.createInventoryBalanceFromTextFile(TEST_DATA_FILE_PATH);
        ArrayList<String> lowInStock = (ArrayList<String>) inventoryBalance.getProductsLowInStock();

        boolean containsFirstItem = lowInStock.contains(FIRST_PRODUCT_LOW_IN_STOCK);
        boolean containsSecondItem = lowInStock.contains(SECOND_PRODUCT_LOW_IN_STOCK);

        assertTrue(containsFirstItem && containsSecondItem);
    }

    @Test
    void showsEveryProductAboutToExpire_when_findingEveryProductWithShortExpirationDate() {
        InventoryBalance inventoryBalance = InventoryLoader.createInventoryBalanceFromTextFile(TEST_DATA_FILE_PATH);

        Product firstProduct = new Product.ProductBuilder("Arla", "Västerbottenost")
                .setAmount(5)
                .setPrice(80_00)
                .setVatRate(VAT.FOOD)
                .build();
        Product secondProduct = new Product.ProductBuilder("Heinz", "Ketchup")
                .setAmount(3)
                .setPrice(30_00)
                .setVatRate(VAT.FOOD)
                .build();

        LocalDate expirationDate = LocalDate.of(2023, 12, 24);
        firstProduct.setExpirationDate(expirationDate);
        secondProduct.setExpirationDate(expirationDate);

        inventoryBalance.addProduct(firstProduct);
        inventoryBalance.addProduct(secondProduct);

        LocalDate mockedCurrentDate = LocalDate.of(2023, 12, 20);
        try (MockedStatic<LocalDate> localDateMock = Mockito.mockStatic(LocalDate.class)) {
            localDateMock.when(LocalDate::now).thenReturn(mockedCurrentDate);

            ArrayList<Product> productsAboutToExpire = inventoryBalance.getProductsAboutToExpire();
            assertTrue(productsAboutToExpire.contains(firstProduct) && productsAboutToExpire.contains(secondProduct));
        }
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
