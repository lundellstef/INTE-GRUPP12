import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestProduct {

    static final String DEFAULT_BRAND_NAME = "Brand Name";
    static final String DEFAULT_PRODUCT_NAME = "Product Name";
    static final int DEFAULT_PRICE = 2000_00;
    static final int EXPECTED_PRICE_WITH_VAT12 = 2240_00;
    static final int EXPECTED_PRICE_WITH_VAT25 = 2500_00;
    static final int DEFAULT_DISCOUNT = 10;
    static final int EXPECTED_PRICE_WITH_VAT12_AND_DEFAULT_DISCOUNT = 2016_00;
    static final int DEFAULT_AMOUNT = 10;

    @Test
    void throwsException_when_creatingProduct_withOutPrice() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                        .setAmount(DEFAULT_AMOUNT)
                        .setVatRate(VAT.FOOD)
                        .setDiscount(DEFAULT_DISCOUNT)
                        .build());
    }

    @Test
    void throwsException_when_creatingProduct_withInvalidPrice() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                        .setPrice(-100)
                        .setAmount(DEFAULT_AMOUNT)
                        .setVatRate(VAT.FOOD)
                        .setDiscount(DEFAULT_DISCOUNT)
                        .build());
    }

    @Test
    void throwsException_when_creatingProduct_withInvalidDiscount() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                        .setPrice(DEFAULT_PRICE)
                        .setAmount(DEFAULT_AMOUNT)
                        .setVatRate(VAT.FOOD)
                        .setDiscount(-10)
                        .build());
    }

    @Test
    void throwsException_when_settingDiscountOfCreatedProduct_toInvalidAmount() {
        Product product = createProductWithDefaultValues(VAT.STANDARD);
        assertThrows(IllegalArgumentException.class, () -> product.setDiscount(-10));
    }

    @Test
    void throwsException_when_settingAmountOfCreatedProduct_toInvalidAmount() {
        Product product = createProductWithDefaultValues(VAT.STANDARD);
        assertThrows(IllegalArgumentException.class, () -> product.setAmount(-100));
    }

    @Test
    void throwsException_when_creatingProduct_withoutAmount() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                        .setPrice(DEFAULT_PRICE)
                        .setVatRate(VAT.FOOD)
                        .setDiscount(DEFAULT_DISCOUNT)
                        .build());
    }

    @Test
    void throwsException_when_creatingProduct_withoutVatRate() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                        .setPrice(DEFAULT_PRICE)
                        .setAmount(DEFAULT_AMOUNT)
                        .setDiscount(DEFAULT_DISCOUNT)
                        .build());
    }

    @Test
    void returnsCorrectPrice_withVat12_when_productIsCreated() {
        Product product = createProductWithDefaultValues(VAT.FOOD);
        assertEquals(product.getPriceWithVat(), EXPECTED_PRICE_WITH_VAT12);
    }

    @Test
    void returnsCorrectPrice_withVat25_when_productIsCreated() {
        Product product = createProductWithDefaultValues(VAT.STANDARD);
        assertEquals(product.getPriceWithVat(), EXPECTED_PRICE_WITH_VAT25);
    }

    @Test
    void returnsCorrectDiscountedPrice_withVat12_when_productIsCreated() {
        Product product = createProductWithDefaultValues(VAT.FOOD);
        int actualPriceWithVatAndDiscount = product.getPriceWithVatAndDiscount();

        assertEquals(actualPriceWithVatAndDiscount, EXPECTED_PRICE_WITH_VAT12_AND_DEFAULT_DISCOUNT);
    }

    @Test
    void setsDiscountToDefaultValueZero_when_creatingProduct_withoutDiscount() {
        Product product = new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                .setPrice(DEFAULT_PRICE)
                .setAmount(DEFAULT_AMOUNT)
                .setVatRate(VAT.FOOD)
                .build();
        assertEquals(product.getDiscount(), 0);
        assertFalse(product.hasDiscount());
    }

    @Test
    void removesProductFromInventory_when_removingByName() {
        InventoryBalance inventoryBalance = new InventoryBalance();
        Product product = new Product.ProductBuilder("Explicit Brand", "Explicit Name")
                .setPrice(DEFAULT_PRICE)
                .setAmount(DEFAULT_AMOUNT)
                .setVatRate(VAT.FOOD)
                .build();

        inventoryBalance.addProduct(product);
        /*
         * Another assert is used here to ensure that the inventory actually contained the product that is about
         * to be removed.
         */
        assertTrue(inventoryBalance.contains(product));

        inventoryBalance.deleteProduct("Explicit Brand", "Explicit Name");
        assertFalse(inventoryBalance.contains(product));
    }

    @Test
    void throwsException_when_tryingToSetAnExpirationDate_onAProduct_thatAlreadyHasOne() {
        Product product = createProductWithDefaultValues(VAT.FOOD);
        LocalDate expirationDate = LocalDate.of(2023, 1, 2);
        product.setExpirationDate(expirationDate);
        assertThrows(IllegalArgumentException.class, () -> product.setExpirationDate(expirationDate));
    }

    @Test
    void setsDiscountToNewValue_when_changingDiscountAfterCreation() {
        Product product = createProductWithDefaultValues(VAT.FOOD);
        int expectedDiscount = 15;
        product.setDiscount(expectedDiscount);

        int actualDiscount = product.getDiscount();
        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    void hasNoDiscount_when_updatingDiscountAfterCreation() {
        Product product = new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                .setPrice(DEFAULT_PRICE)
                .setAmount(DEFAULT_AMOUNT)
                .setVatRate(VAT.STANDARD)
                .setDiscount(10)
                .build();
        // Additional assert to ensure that the product actually has a discount before updating discount value.
        assertTrue(product.hasDiscount());

        product.setDiscount(0);
        assertFalse(product.hasDiscount());
    }

    @Test
    void setsNameAndBrand_when_creatingProduct() {
        Product product = new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                .setPrice(DEFAULT_PRICE)
                .setAmount(DEFAULT_AMOUNT)
                .setVatRate(VAT.STANDARD)
                .setDiscount(DEFAULT_DISCOUNT)
                .build();
        boolean correctBrand = product.getBrandName().equals(DEFAULT_BRAND_NAME);
        boolean correctName = product.getProductName().equals(DEFAULT_PRODUCT_NAME);
        assertTrue(correctBrand && correctName);
    }

    /**
     * Support method used to create a product with default values.
     *
     * @param vatRate is the chosen VAT for the product to be created.
     * @return a Product with default values and the chosen VAT rate.
     */
    private Product createProductWithDefaultValues(VAT vatRate) {
        return new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                .setPrice(DEFAULT_PRICE)
                .setAmount(DEFAULT_AMOUNT)
                .setVatRate(vatRate)
                .setDiscount(DEFAULT_DISCOUNT)
                .build();
    }
}
