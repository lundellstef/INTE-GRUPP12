public class ProductItem extends Product{

    public ProductItem(int price, String productID, int discount, VAT tollRate) {
        setPrice(price);
        setProductID(productID);
        setDiscountPercentage(discount);
        setTollRate(tollRate);
        setHasDiscount(discount > 0);
    }
}
