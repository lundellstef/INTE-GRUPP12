import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestProduct {

    static final String DEFAULT_BRAND_NAME = "Brand Name";
    static final String DEFAULT_PRODUCT_NAME = "Product Name";
    static final int DEFAULT_PRICE = 2000_00;
    static final int EXPECTED_PRICE_WITH_VAT12 = 2240_00;
    static final int EXPECTED_PRICE_WITH_VAT25 = 2500_00;
    static final int DEFAULT_AMOUNT = 10;
    static final int DEFAULT_DISCOUNT = 10;

    @Test
    void throwsException_when_creatingProduct_withOutPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                        .setAmount(DEFAULT_AMOUNT)
                        .setVatRate(VAT.FOOD)
                        .setDiscount(DEFAULT_DISCOUNT)
                        .build());
        System.out.println(exception.getMessage());
    }

    @Test
    void throwsException_when_creatingProduct_withInvalidPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                        .setPrice(-100)
                        .setAmount(DEFAULT_AMOUNT)
                        .setVatRate(VAT.FOOD)
                        .setDiscount(DEFAULT_DISCOUNT)
                        .build());
        System.out.println(exception.getMessage());
    }

    @Test
    void throwsException_when_settingDiscountOfCreatedProduct_toInvalidAmount() {
        Product product = createProductWithDefaultValues(VAT.STANDARD);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> product.setDiscount(-10));
        System.out.println(exception.getMessage());
    }

    @Test
    void throwsException_when_settingAmountOfCreatedProduct_toInvalidAmount() {
        Product product = createProductWithDefaultValues(VAT.STANDARD);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> product.setAmount(-100));
        System.out.println(exception.getMessage());
    }

    @Test
    void throwsException_when_creatingProduct_withoutAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                        .setPrice(DEFAULT_PRICE)
                        .setVatRate(VAT.FOOD)
                        .setDiscount(DEFAULT_DISCOUNT)
                        .build());
        System.out.println(exception.getMessage());
    }

    @Test
    void throwsException_when_creatingProduct_withoutVatRate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                        .setPrice(DEFAULT_PRICE)
                        .setAmount(DEFAULT_AMOUNT)
                        .setDiscount(DEFAULT_DISCOUNT)
                        .build());
        System.out.println(exception.getMessage());
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
    void setsDiscountToDefaultValueZero_when_creatingProduct_withoutDiscount() {
        Product product = new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_PRODUCT_NAME)
                .setPrice(DEFAULT_PRICE)
                .setAmount(DEFAULT_AMOUNT)
                .setVatRate(VAT.FOOD)
                .build();
        assertEquals(product.getDiscount(), 0);
        assertFalse(product.hasDiscount());
    }

    private Product createProductWithDefaultValues(VAT vatRate) {
        return new Product.ProductBuilder(DEFAULT_BRAND_NAME, DEFAULT_BRAND_NAME)
                .setPrice(DEFAULT_PRICE)
                .setAmount(DEFAULT_AMOUNT)
                .setVatRate(vatRate)
                .setDiscount(DEFAULT_DISCOUNT)
                .build();
    }
}
