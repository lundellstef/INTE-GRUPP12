public class TestProduct {

    static final String DEFAULT_PRODUCT_ID = "Banana";
    static final int DEFAULT_PRODUCT_PRICE = 2000;
    static final int DEFAULT_PRODUCT_DISCOUNT = 10;
    static final int NO_DISCOUNT = 0;

    private ProductItem createDefaultProductItem() {
        return new ProductItem(DEFAULT_PRODUCT_PRICE, DEFAULT_PRODUCT_ID, NO_DISCOUNT, VAT.FOOD);
    }

}
