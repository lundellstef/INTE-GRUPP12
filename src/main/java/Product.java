public abstract class Product {

    private int price;
    private String productID;
    private int discount;
    private boolean hasDiscount;
    private VAT vatRate;

    public int getPriceWithVat() {
        return (getPrice() * vatRate.value) / 100;
    }

    /**
     * Price is entered in minor units, WITHOUT tax.
     * If a banana costs 20 SEK, the price entered would be 2000. (2000 öre.)
     **/
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

    public VAT getVat() {
        return vatRate;
    }

    public void setVat(VAT vatRate) {
        this.vatRate = vatRate;
    }
}
