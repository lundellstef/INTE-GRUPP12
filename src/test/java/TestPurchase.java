import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestPurchase {


    //First test from state chart diagram
    @Test
    public void scanningAndRemovingProductsResultsInPurchaseWithCorrectProducts(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(1));
        purchase.scanItem(products.get(2));
        purchase.removeScannedItem(products.get(2));
        purchase.scanItem(products.get(4));
        HashMap<Product, Integer> expectedPurchase = new HashMap<>();
        expectedPurchase.put(products.get(1), 1);
        expectedPurchase.put(products.get(4), 1);
        assertEquals(expectedPurchase, purchase.getPurchasedItems());
    }

    //Second test from state chart diagram
    @Test
    public void scanningProductsAndCancelingPurchaseResultsEmptyPurchase(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(1));
        purchase.cancelPurchase();
        assertEquals(new HashMap<Product, Integer>(), purchase.getPurchasedItems());
    }

    //Third test from state chart diagram
    @Test
    public void scanningAndRemovingProductsAndCancelingPurchaseResultsEmptyPurchase(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(1));
        purchase.removeScannedItem(products.get(1));
        purchase.cancelPurchase();
        assertEquals(new HashMap<Product, Integer>(), purchase.getPurchasedItems());
    }

    //Forth test from state chart diagram
    @Test
    public void scanningAndRemovingProductsInTheEndResultsInPurchaseWithCorrectProducts(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(1));
        purchase.scanItem(products.get(2));
        purchase.removeScannedItem(products.get(2));
        HashMap<Product, Integer> expectedPurchase = new HashMap<>();
        expectedPurchase.put(products.get(1), 1);
        assertEquals(expectedPurchase, purchase.getPurchasedItems());
    }

    //Fifth test from state chart diagram
    @Test
    public void cancelingFromStartPurchaseResultsEmptyPurchase(){
        Purchase purchase = new Purchase();
        purchase.cancelPurchase();
        assertEquals(new HashMap<Product, Integer>(), purchase.getPurchasedItems());
    }

    //Sixth test from state chart diagram
    @Test
    public void removingMultipleProductsResultsInCorrectProductsInPurchase(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(1));
        purchase.scanItem(products.get(2));
        purchase.scanItem(products.get(3));
        purchase.removeScannedItem(products.get(2));
        purchase.removeScannedItem(products.get(3));
        HashMap<Product, Integer> expectedPurchase = new HashMap<>();
        expectedPurchase.put(products.get(1), 1);
        assertEquals(expectedPurchase, purchase.getPurchasedItems());
    }

    @Test
    public void scanningProductsAndCancelingPurchaseDecreasesTotalDiscountToZero(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(4));
        purchase.cancelPurchase();
        assertEquals(0, purchase.getTotalDiscount());
    }

    @Test
    public void scanningProductsAndCancelingPurchaseDecreasesTotalPriceExVatToZero(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(4));
        purchase.cancelPurchase();
        assertEquals(0, purchase.getTotalPriceExVat());
    }

    @Test
    public void scanningProductsAndCancelingPurchaseDecreasesTotalPriceToZero(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(4));
        purchase.cancelPurchase();
        assertEquals(0, purchase.getTotalPrice());
    }

    @Test
    public void scanningProductsAndCancelingPurchaseDecreasesTotalVatToZero(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(4));
        purchase.cancelPurchase();
        assertEquals(0, purchase.getTotalVAT());
    }

    @Test
    public void scanningProductsCorrectlyChangesAmountInProduct(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        int amountInProductBeforePurchase = products.get((1)).getAmount();
        purchase.scanItem(products.get(1));
        purchase.scanItem(products.get(2));
        purchase.scanItem(products.get(1));
        int expectedAmountInProductAfterPurchase = amountInProductBeforePurchase - 2;
        assertEquals(expectedAmountInProductAfterPurchase, products.get(1).getAmount());
    }

    @Test
    public void scanningAndRemovingProductsCorrectlyChangesAmountInProduct(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        int amountInProductBeforePurchase = products.get((1)).getAmount();
        purchase.scanItem(products.get(1));
        purchase.scanItem(products.get(2));
        purchase.scanItem(products.get(1));
        purchase.removeScannedItem(products.get(1));
        int expectedAmountInProductAfterPurchase = amountInProductBeforePurchase - 1;
        assertEquals(expectedAmountInProductAfterPurchase, products.get(1).getAmount());
    }

    @Test
    public void scanningProductsUntilThereIsNonOfThatProductLeftThrowsException(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(5));
        assertThrows(IllegalArgumentException.class, () -> purchase.scanItem(products.get(5)));
    }

    @Test
    public void scanningProductsCorrectlyIncrementsTotalDiscount(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(0));
        purchase.scanItem(products.get(2));
        purchase.scanItem((products.get(4)));
        int expectedTotalDiscount = products.get(0).getDiscountAmount();
        expectedTotalDiscount += products.get(2).getDiscountAmount();
        expectedTotalDiscount += products.get(4).getDiscountAmount();
        assertEquals(expectedTotalDiscount, purchase.getTotalDiscount());
    }

    @Test
    public void scanningAndRemovingProductsCorrectlyIncrementsTotalDiscount(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(0));
        purchase.scanItem(products.get(2));
        purchase.scanItem((products.get(4)));
        purchase.removeScannedItem(products.get(4));
        int expectedTotalDiscount = products.get(0).getDiscountAmount();
        expectedTotalDiscount += products.get(2).getDiscountAmount();
        assertEquals(expectedTotalDiscount, purchase.getTotalDiscount());
    }

    @Test
    public void scanningProductsCorrectlyIncrementsTotalPriceExVat(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(0));
        purchase.scanItem(products.get(2));
        purchase.scanItem((products.get(4)));
        int expectedTotalPriceExVat = products.get(0).getPrice();
        expectedTotalPriceExVat += products.get(2).getPrice();
        expectedTotalPriceExVat += products.get(4).getPrice();
        assertEquals(expectedTotalPriceExVat, purchase.getTotalPriceExVat());
    }

    @Test
    public void scanningAndRemovingProductsCorrectlyIncrementsTotalPriceExVat(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(0));
        purchase.scanItem(products.get(2));
        purchase.scanItem((products.get(4)));
        purchase.removeScannedItem(products.get(4));
        int expectedTotalPriceExVat = products.get(0).getPrice();
        expectedTotalPriceExVat += products.get(2).getPrice();
        assertEquals(expectedTotalPriceExVat, purchase.getTotalPriceExVat());
    }

    @Test
    public void scanningProductsCorrectlyIncrementsTotalVAT(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(0));
        purchase.scanItem(products.get(2));
        purchase.scanItem((products.get(4)));
        int expectedTotalVat = products.get(0).getVatAmountOfPrice();
        expectedTotalVat += products.get(2).getVatAmountOfPrice();
        expectedTotalVat += products.get(4).getVatAmountOfPrice();
        assertEquals(expectedTotalVat, purchase.getTotalVAT());
    }

    @Test
    public void scanningAndRemovingProductsCorrectlyIncrementsTotalVAT(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(0));
        purchase.scanItem(products.get(2));
        purchase.scanItem((products.get(4)));
        purchase.removeScannedItem(products.get(4));
        int expectedTotalVat = products.get(0).getVatAmountOfPrice();
        expectedTotalVat += products.get(2).getVatAmountOfPrice();
        assertEquals(expectedTotalVat, purchase.getTotalVAT());
    }

    @Test
    public void getTotalPriceCalculatesCorrectPriceAfterScanningProducts(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(0));
        purchase.scanItem(products.get(2));
        purchase.scanItem((products.get(4)));
        int expectedTotalPrice = products.get(0).getPriceWithVatAndDiscount();
        expectedTotalPrice += products.get(2).getPriceWithVatAndDiscount();
        expectedTotalPrice += products.get(4).getPriceWithVatAndDiscount();
        assertEquals(expectedTotalPrice, purchase.getTotalPrice());
    }

    @Test
    public void tryingToRemoveProductsThatDoNotExistThrowsException(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(0));
        purchase.scanItem(products.get(2));
        purchase.scanItem((products.get(4)));
        assertThrows(NoSuchElementException.class, () -> purchase.removeScannedItem(products.get(3)));
    }

    private ArrayList<Product> setUpProducts(){
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product.ProductBuilder("Arla", "Mellanmjölk").setAmount(10).setDiscount(0).setPrice(2000).setVatRate(VAT.FOOD).build());
        productList.add(new Product.ProductBuilder("Axa", "Fruktmusli").setAmount(10).setDiscount(0).setPrice(3600).setVatRate(VAT.FOOD).build());
        productList.add(new Product.ProductBuilder("Lambi", "8P toalettpapper").setAmount(10).setDiscount(10).setPrice(6500).setVatRate(VAT.STANDARD).build());
        productList.add(new Product.ProductBuilder("Sempers", "Barnmat lasagne 8-6 mån").setAmount(10).setDiscount(5).setPrice(1500).setVatRate(VAT.FOOD).build());
        productList.add(new Product.ProductBuilder("Arla", "Hushållsost").setAmount(10).setDiscount(5).setPrice(12600).setVatRate(VAT.FOOD).build());
        productList.add(new Product.ProductBuilder("Zeta", "Buffalomozzarella").setAmount(1).setDiscount(0).setPrice(3400).setVatRate(VAT.FOOD).build());

        return productList;
    }
}
