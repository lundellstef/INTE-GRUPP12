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
        ArrayList<Product> lowInStock = (ArrayList<Product>) inventoryBalance.getProductsLowInStock();

        /* Products are compared only according to their brandName and productName.
         * Therefore, two products are created with junk values BUT with correct brand names and product names.
         * Meaning: The two products that are expected to be low in stock are Polarbröd Vetekaka and Kavli Mjukost.
         */
        Product firstExpectedProduct = new Product.ProductBuilder("Polarbröd", "Vetekaka")
                .setAmount(1)
                .setPrice(1)
                .setVatRate(VAT.FOOD)
                .build();
        Product secondExpectedProduct = new Product.ProductBuilder("Kavli", "Mjukost")
                .setAmount(1)
                .setPrice(1)
                .setVatRate(VAT.FOOD)
                .build();

        boolean containsFirstItem = lowInStock.contains(firstExpectedProduct);
        boolean containsSecondItem = lowInStock.contains(secondExpectedProduct);

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

            ArrayList<Product> productsAboutToExpire = (ArrayList<Product>) inventoryBalance.getProductsAboutToExpire();
            assertTrue(productsAboutToExpire.contains(firstProduct) && productsAboutToExpire.contains(secondProduct));
        }
    }

    @Test
    void findsProductInInventory_when_searchingByName() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product addedProduct = InventoryLoader.createSingleProductFromTextFile(TEST_DATA_FILE_PATH);
        inventoryBalance.addProduct(addedProduct);
        Product foundProduct = inventoryBalance.get("Arla", "Mellanmjölk");
        assertEquals(addedProduct, foundProduct);
    }

    @Test
    void throwsException_when_tryingToFindAProductNotInInventory() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product addedProduct = InventoryLoader.createSingleProductFromTextFile(TEST_DATA_FILE_PATH);
        Product productNotAdded = createDefaultProduct(DEFAULT_PRODUCT_NAME);

        inventoryBalance.addProduct(addedProduct);
        assertThrows(NoSuchElementException.class, () -> inventoryBalance.get(productNotAdded));
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
