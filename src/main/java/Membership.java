import java.time.LocalDate;

public class Membership {

    public static final int THRESHOLD_FOR_SILVER_MEMBERSHIP = 1000_00;
    public static final int THRESHOLD_FOR_GOLD_MEMBERSHIP = 5000_00;

    private final Customer customer;
    private final LocalDate startDate;
    private MembershipType membershipType;
    private long memberPoints;

    public Membership(Customer customer, LocalDate startDate, long memberPoints) {
        this.customer = customer;
        this.startDate = startDate;
        this.memberPoints = memberPoints;
        determineMembershipType(memberPoints);
    }

    /**
     * Updates member points and checks to see if membership type should be upgraded.
     *
     * @param memberPoints the number to increase current member points with.
     */
    public void updateMemberPoints(long memberPoints) {
        if (memberPoints < 0) {
            throw new IllegalArgumentException(String.format("%d is invalid.", memberPoints));
        }
        this.memberPoints += memberPoints;
        determineMembershipType(this.memberPoints);
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

    public long getMemberPoints() {
        return memberPoints;
    }

    private void determineMembershipType(long memberPoints) {
        if (memberPoints > THRESHOLD_FOR_GOLD_MEMBERSHIP) {
            membershipType = MembershipType.GOLD;
        } else if (memberPoints > THRESHOLD_FOR_SILVER_MEMBERSHIP) {
            membershipType = MembershipType.SILVER;
        } else {
            membershipType = MembershipType.BRONZE;
        }
    }

    @Override
    public String toString() {
        return String.format("%s has been a member since %s. Member status: %s.", customer.getName(), startDate.toString(), membershipType);
    }
}
