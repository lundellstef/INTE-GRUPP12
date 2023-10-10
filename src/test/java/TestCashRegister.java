
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestCashRegister {

    String DEFAULT_PRODUCT_ID = "123";
    long DEFAULT_TOTAL_AMOUNT_ABOVE_ZERO = 10_000_000L;
    int DEFAULT_PRODUCT_ITEM_PRICE = 2000;
    int DEFAULT_DISCOUNT = 10;

    int DEFAULT_PRICE_INCREASE = 2250;

    VAT DEFAULT_VAT = VAT.STANDARD;

    @Test
    void totalAmountIsCorrectAfterFirstPurchaseHasBeenMade(){
        CashRegister cashRegister = new CashRegister();
        Purchase purchase = new Purchase();
        ProductItem productItem = new ProductItem(DEFAULT_PRODUCT_ITEM_PRICE, DEFAULT_PRODUCT_ID, DEFAULT_DISCOUNT, DEFAULT_VAT);
        purchase.scanItem(productItem);
        assertEquals(DEFAULT_PRICE_INCREASE, cashRegister.getTotalAmount());
    }

    @Test
    void totalAmountIsCorrectAfterPurchaseHasBeenMadeAndTotalAmountIsNotZero(){
        CashRegister cashRegister = new CashRegister();
        cashRegister.setTotalAmount(DEFAULT_TOTAL_AMOUNT_ABOVE_ZERO);
        Purchase purchase = new Purchase();
        ProductItem productItem = new ProductItem(DEFAULT_PRODUCT_ITEM_PRICE, DEFAULT_PRODUCT_ID, DEFAULT_DISCOUNT, DEFAULT_VAT);
        purchase.scanItem(productItem);
        assertEquals(DEFAULT_TOTAL_AMOUNT_ABOVE_ZERO + DEFAULT_PRICE_INCREASE, cashRegister.getTotalAmount());
    }
}
