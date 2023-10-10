public abstract class Product {

    private int priceInMinorUnits;
    private String productID;
    private int discount;
    private boolean hasDiscount;
    private VAT vatRate;

    /**
     * Returns the total price of the product including VAT.
     * If a banana costs 2000 without VAT (20 SEK), this would return 2240.
     **/
    public int getPriceWithVat() {
        return (getPrice() * (getVat().value + 100) / 100);
    }

    /**
     * Returns the amount of VAT from the total price.
     * If a banana costs 2240 with VAT, this would return 240.
     */
    public int getVatAmount() {
        return getPriceWithVat() - getPrice();
    }

    /**
     * Price is entered in minor units, WITHOUT VAT.
     * If a banana costs 20 SEK, the price entered would be 2000. (2000 öre.)
     **/
    public int getPrice() {
        return priceInMinorUnits;
    }

    public void setPrice(int priceInMinorUnits) {
        this.priceInMinorUnits = priceInMinorUnits;
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
