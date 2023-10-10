import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestProduct {
    
    static private final String DEFAULT_PRODUCT_NAME = "Banan";
    static private final int DEFAULT_PRODUCT_PRICE = 200;
    static private final int NO_DISCOUNT = 0;
    static private final int DEFAULT_DISCOUNT = 10;

    @Test
    void should_createProduct_when_inputIsValid() {
        assertDoesNotThrow(() -> {
            ProductItem banan = new ProductItem(DEFAULT_PRODUCT_PRICE, DEFAULT_PRODUCT_NAME, NO_DISCOUNT, VAT.STANDARD);
        });
    }
}
