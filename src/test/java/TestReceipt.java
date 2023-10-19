import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestReceipt {

    @Test
    public void testToString(){
        Receipt receipt = new Receipt(setUpPurchase());
        String expectedString = """
                Du har köpt varorna:
                Arla Mellanmjölk, antal: 1
                Axa Fruktmusli, antal: 1
                Lambi 8P toalettpapper, antal: 1
                Pris ex moms: 121kr
                Moms: 22kr
                Rabatt: 8kr
                Totalpris: 135kr""";
        assertEquals(expectedString, receipt.toString());
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

    private Purchase setUpPurchase(){
        Purchase purchase = new Purchase();
        ArrayList<Product> products = setUpProducts();
        purchase.scanItem(products.get(0));
        purchase.scanItem(products.get(1));
        purchase.scanItem(products.get(2));
        return purchase;
    }

}
