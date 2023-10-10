public class ProductItem extends Product{

    public ProductItem(int price, String productID, int discount, VAT vatRate) {
        setPrice(price);
        setProductID(productID);
        setDiscount(discount);
        setVat(vatRate);
        setHasDiscount(discount > 0);
    }
}
