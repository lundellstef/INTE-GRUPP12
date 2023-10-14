import java.util.Objects;

/**
 * Product is treated as immutable as possible.
 * After a product has been created, you should only be able to change the amount and the discount.
 * Creation of Product objects is managed with builder pattern - see bottom of class.
 */
public class Product {

    private final String brandName;
    private final String productName;
    private final int priceInMinorUnits;
    private final VAT vatRate;

    private int amountInInventory;
    private int discount;
    private boolean hasDiscount;

    private Product(ProductBuilder builder) {
        this.brandName = builder.brandName;
        this.productName = builder.productName;
        this.priceInMinorUnits = builder.priceInMinorUnits;
        this.vatRate = builder.vatRate;
        this.amountInInventory = builder.amount;
        this.discount = builder.discount;
        this.hasDiscount = builder.hasDiscount;
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
    public int getVatAmountOfPrice() {
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
        this.hasDiscount = discount > 0;
    }

    public boolean hasDiscount() {
        return hasDiscount;
    }

    public VAT getVat() {
        return vatRate;
    }

    public int getAmount() {
        return amountInInventory;
    }

    /**
     * Checking for Integer.MAX_VALUE is redundant since overflow will turn number negative.
     */
    public void setAmount(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException(String.format("%d is not a valid amount.", amount));
        }
        this.amountInInventory = amount;
    }

    public void increment() {
        setAmount(amountInInventory + 1);
    }

    public void decrement() {
        setAmount(amountInInventory - 1);
    }

    public String getProductName() {
        return productName;
    }

    public String getBrandName() {
        return brandName;
    }

    @Override
    public String toString() {
        return String.format("%s %s", brandName, productName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Product o)) {
            return false;
        }
        return o.brandName.equals(brandName) && o.productName.equals(productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandName, productName);
    }

    /**
     * Builder pattern used to create Product objects.
     * All fields are mandatory except discount and hasDiscount.
     * If the discount is not explicitly set by the user, the discount defaults to 0 and hasDiscount to false.
     * (int values declared but not initialized default to 0, boolean values to false.)
     * <p>
     * brandName and productName are entered directly in the constructor, all remaining fields are set by methods.
     * For example:
     * Product milk = new Product.ProductBuilder("Arla", "Mellanmjölk")
     * .setPrice(2000)
     * .setAmount(5)
     * .setVatRate(VAT.FOOD)
     * .build();
     */
    public static class ProductBuilder {

        private final String brandName;
        private final String productName;

        private int priceInMinorUnits;
        private VAT vatRate;
        private int amount;
        private int discount;
        private boolean hasDiscount;

        public ProductBuilder(String brandName, String productName) {
            this.brandName = brandName;
            this.productName = productName;
        }

        public ProductBuilder setPrice(int priceInMinorUnits) {
            this.priceInMinorUnits = priceInMinorUnits;
            return this;
        }

        public ProductBuilder setVatRate(VAT vatRate) {
            this.vatRate = vatRate;
            return this;
        }

        public ProductBuilder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public ProductBuilder setDiscount(int discount) {
            this.discount = discount;
            hasDiscount = discount > 0;
            return this;
        }

        /**
         * Finalizes the build of the Product object.
         * Calls validateProduct() to ensure that the object is built properly, with all mandatory fields.
         */
        public Product build() {
            Product product = new Product(this);
            validateProduct(product);
            return product;
        }

        private void validateProduct(Product product) throws IllegalArgumentException {
            if (priceInMinorUnits <= 0) {
                exceptionMessage(product, "Price");
            }

            if (amount <= 0) {
                exceptionMessage(product, "Amount");
            }

            if (vatRate == null) {
                exceptionMessage(product, "VAT");
            }

            if (discount < 0) {
                exceptionMessage(product, "Discount");
            }
        }

        /**
         * Support method used to throw exceptions when validating product.
         * Discount cannot be "missing", therefore it has its own message.
         */
        private void exceptionMessage(Product product, String message) {
            if (message.equals("Discount")) {
                throw new IllegalArgumentException(String.format(message + " invalid in %s", product.productName));
            } else {
                throw new IllegalArgumentException(String.format(message + " missing or invalid in %s", product.productName));
            }
        }
    }
}
