/*
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCashRegister {

    String DEFAULT_PRODUCT_ID = "123";
    long DEFAULT_TOTAL_AMOUNT_ABOVE_ZERO = 10_000_000L;
    int DEFAULT_PRODUCT_ITEM_PRICE = 2000;
    int DEFAULT_DISCOUNT = 10;

    int DEFAULT_TOTAL_PRICE = 2250;

    VAT DEFAULT_VAT = VAT.STANDARD;

    @Test
    void totalAmountIsCorrectAfterFirstPurchaseHasBeenMade(){
        CashRegister cashRegister = new CashRegister();
        Purchase purchase = new Purchase();
        ProductItem productItem = new ProductItem(DEFAULT_PRODUCT_ITEM_PRICE, DEFAULT_PRODUCT_ID, DEFAULT_DISCOUNT, DEFAULT_VAT);
        purchase.scanItem(productItem);
        assertEquals(DEFAULT_TOTAL_PRICE, cashRegister.getTotalAmount());
    }

    @Test
    void totalAmountIsCorrectAfterPurchaseHasBeenMadeAndTotalAmountWasNotZero(){
        CashRegister cashRegister = new CashRegister();
        cashRegister.setTotalAmount(DEFAULT_TOTAL_AMOUNT_ABOVE_ZERO);
        Purchase purchase = new Purchase();
        ProductItem productItem = new ProductItem(DEFAULT_PRODUCT_ITEM_PRICE, DEFAULT_PRODUCT_ID, DEFAULT_DISCOUNT, DEFAULT_VAT);
        purchase.scanItem(productItem);
        assertEquals(DEFAULT_TOTAL_AMOUNT_ABOVE_ZERO + DEFAULT_TOTAL_PRICE, cashRegister.getTotalAmount());
    }



    @Test
    void throwsExceptionWhenEnoughMoneyForChangeDoesNotExist(){

        List<Money> customerPayment = new ArrayList<>();
        customerPayment.add(new Money(SEK, 1_000));
        customerPayment.add(new Money(SEK, 1_000));
        customerPayment.add(new Money(SEK, 500));

        List<Money> moneyInRegister = new ArrayList<>();
        moneyInRegister.add(new Money(SEK, 1_000));
        moneyInRegister.add(new Money(SEK, 1_000));
        moneyInRegister.add(new Money(SEK, 1_000));

        CashRegister cashRegister = new CashRegister();
        cashRegister.setMoneyInRegister(moneyInRegister);

        assertThrows(IllegalArgumentException.class, () -> {
            cashRegister.handlePayment(customerPayment, DEFAULT_TOTAL_PRICE);
        });
    }

    @Test
    void throwsExceptionWhenThereIsNoMoneyInRegister(){

        List<Money> customerPayment = new ArrayList<>();
        customerPayment.add(new Money(SEK, 1_000));
        customerPayment.add(new Money(SEK, 1_000));
        customerPayment.add(new Money(SEK, 500));

        List<Money> moneyInRegister = new ArrayList<>();

        CashRegister cashRegister = new CashRegister();
        cashRegister.setMoneyInRegister(moneyInRegister);

        assertThrows(IllegalArgumentException.class, () -> {
            cashRegister.handlePayment(customerPayment, DEFAULT_TOTAL_PRICE);
        });
    }

    @Test
    void returnsLeastAmountOfMoneyObjectsForChange(){
        List<Money> customerPayment = new ArrayList<>();
        customerPayment.add(new Money(SEK, 1_000));
        customerPayment.add(new Money(SEK, 1_000));
        customerPayment.add(new Money(SEK, 500));

        List<Money> moneyInRegister = new ArrayList<>();
        moneyInRegister.add(new Money(SEK, 1_000));
        moneyInRegister.add(new Money(SEK, 1_000));
        moneyInRegister.add(new Money(SEK, 1_000));
        moneyInRegister.add(new Money(SEK, 200));
        moneyInRegister.add(new Money(SEK, 1));
        moneyInRegister.add(new Money(SEK, 1));

        CashRegister cashRegister = new CashRegister();
        cashRegister.setMoneyInRegister(moneyInRegister);
    }

}*/
