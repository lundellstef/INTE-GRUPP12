import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestProduct {

    static final String DEFAULT_PRODUCT_ID = "Banana";
    static final int DEFAULT_PRODUCT_PRICE = 2000;
    static final int DEFAULT_PRODUCT_PRICE_WITH_VAT12 = 2240;
    static final int DEFAULT_VAT_AMOUNT_WITH_VAT12 = 240;
    static final int DEFAULT_PRODUCT_DISCOUNT = 10;
    static final int NO_DISCOUNT = 0;

    @Test
    void should_returnCorrectPriceWithVat_when_productIsCreated() {
        ProductItem item = createDefaultProductItem();
        assertEquals(item.getPriceWithVat(), DEFAULT_PRODUCT_PRICE_WITH_VAT12);
    }

    @Test
    void should_returnCorrectVatAmount_when_productIsCreated() {
        ProductItem item = createDefaultProductItem();
        assertEquals(item.getVatAmount(), DEFAULT_VAT_AMOUNT_WITH_VAT12);
    }

    private ProductItem createDefaultProductItem() {
        return new ProductItem(DEFAULT_PRODUCT_PRICE, DEFAULT_PRODUCT_ID, NO_DISCOUNT, VAT.FOOD);
    }

}
