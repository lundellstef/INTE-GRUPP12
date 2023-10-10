// The toll rates are: 25%, 12%, 6%, 0%.
public enum VAT {
    STANDARD(25), REDUCED(12), LOW(6), TOLL_FREE(0);

    public final int value;

    VAT(int value) {
        this.value = value;
    }
}
