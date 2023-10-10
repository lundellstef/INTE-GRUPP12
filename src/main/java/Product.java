public abstract class Product {

    private int price;
    private String productID;
    private int discount;
    private boolean hasDiscount;
    private VAT tollRate;

    // Price is in minor units. If (price = 100) that equals 1KR SEK, 500 is 5KR SEK, etc.
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean hasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public VAT getTollRate() {
        return tollRate;
    }

    public void setTollRate(VAT tollRate) {
        this.tollRate = tollRate;
    }
}
