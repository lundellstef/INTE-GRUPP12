public class Product {

    private final String productName;
    private final String brandName;
    private int priceInMinorUnits;
    private int discount;
    private boolean hasDiscount;
    private VAT vatRate;

    private Product(ProductBuilder builder) {
        this.productName = builder.productName;
        this.brandName = builder.brandName;
        this.priceInMinorUnits = builder.priceInMinorUnits;
        this.discount = builder.discount;
        this.vatRate = builder.vatRate;
    }

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

    /**
     * Returns the price with VAT and discount applied.
     * If a banana costs 2240 with VAT, this would return 2016.
     * (2240 * 0.9 = 2016).
     */
    public int getPriceWithVatAndDiscount() {
        int fraction = 100 - getDiscount();
        int priceWithDiscount = getPriceWithVat() * fraction;
        return priceWithDiscount / 100;
    }

    /**
     * Returns the amount discounted from the price with VAT.
     * If a banana costs 2240 with VAT, and 10% discount is applied, this returns 224.
     * (2240 * 0.9 = 2016. 2240 - 2016 = 224.)
     */
    public int getDiscountAmount() {
        return getPriceWithVat() - getPriceWithVatAndDiscount();
    }

    public void setPrice(int priceInMinorUnits) {
        this.priceInMinorUnits = priceInMinorUnits;
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

    @Override
    public String toString() {
        return String.format("%s", productName);
    }

    public static class ProductBuilder {
        private final String productName;
        private final String brandName;
        private int priceInMinorUnits;
        private int discount;
        private boolean hasDiscount;
        private VAT vatRate;

        public ProductBuilder(String productName, String brandName) {
            this.productName = productName;
            this.brandName = brandName;
        }

        public ProductBuilder setPrice(int priceInMinorUnits) {
            this.priceInMinorUnits = priceInMinorUnits;
            return this;
        }

        public ProductBuilder setDiscount(int discount) {
            this.discount = discount;
            this.hasDiscount = discount > 0;
            return this;
        }

        public ProductBuilder setVatRate(VAT vatRate) {
            this.vatRate = vatRate;
            return this;
        }

        public Product build() {
            Product product = new Product(this);
            validateProduct(product);
            return product;
        }

        private void validateProduct(Product product) {

        }
    }
}
