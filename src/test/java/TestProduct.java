import org.junit.jupiter.api.Test;

public class TestProduct {

    @Test
    void setsUpProduct_when_allMethodsCalled() {
        Product banana = new Product.ProductBuilder("Chiquita", "Banan")
                .setPrice(1000)
                .setAmount(100)
                .setVatRate(VAT.FOOD)
                .build();
        System.out.println(banana);
    }
}
