/**
 * The typical tax rates are 25%, 12% and 6%.
 * 25% is used for alcohol above 3.5 ABV, products, etc.
 * 12% is used for provisions, food, etc.
 * 6% is used for books, newspapers, etc.
 * <p>
 * VAT has no explicit test class - will be tested indirectly through other classes.
 **/
public enum VAT {

    STANDARD(25), FOOD(12), REDUCED(6), NO_TAX(0);

    public final int value;

    VAT(int value) {
        this.value = value;
    }

}
