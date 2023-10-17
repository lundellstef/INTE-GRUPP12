public enum MembershipType {
    EMPLOYEE(15), GOLD(10), SILVER(5), BRONZE(0);

    private final int discountPercentage;

    MembershipType(int value) {
        discountPercentage = value;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    @Override
    public String toString() {
        return switch (this) {
            case EMPLOYEE -> "Employee";
            case GOLD -> "Gold";
            case SILVER -> "Silver";
            default -> "Bronze";
        };
    }
}
