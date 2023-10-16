import java.time.LocalDate;

public class Membership {

    static final int THRESHOLD_FOR_SILVER_MEMBERSHIP = 1000;
    static final int THRESHOLD_FOR_GOLD_MEMBERSHIP = 5000;

    private final Customer customer;
    private final LocalDate startDate;
    private MembershipType membershipType;
    private int memberPoints;

    // TODO: ADD FUNCTIONALITY FOR BONUS CHECKS.
    public Membership(Customer customer, LocalDate startDate, int memberPoints) {
        this.customer = customer;
        this.startDate = startDate;
        this.memberPoints = memberPoints;
        determineMembershipType(memberPoints);
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public int getMemberPoints() {
        return memberPoints;
    }

    private void determineMembershipType(int memberPoints) {
        if (memberPoints > THRESHOLD_FOR_GOLD_MEMBERSHIP) {
            membershipType = MembershipType.GOLD;
        } else if (memberPoints > THRESHOLD_FOR_SILVER_MEMBERSHIP) {
            membershipType = MembershipType.SILVER;
        } else {
            membershipType = MembershipType.BRONZE;
        }
    }
}
