import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Membership points are directly correlated with swedish öre.
 * This means that a purchase worth 400_00 öre (400kr) equals 400_00 member points.
 */
public class Membership {

    public static final int THRESHOLD_FOR_SILVER_MEMBERSHIP = 25_000_00;
    public static final int THRESHOLD_FOR_GOLD_MEMBERSHIP = 100_000_00;

    private final Customer customer;
    private final LocalDate startDate;
    private MembershipType membershipType;
    private long memberPoints;
    private boolean employee;

    public Membership(Customer customer, LocalDate startDate, long initialPoints, boolean employee) {
        try{
            validateCustomerAge(customer);
        } catch(ParseException exception){
            exception.printStackTrace();
        }
        this.customer = customer;
        this.startDate = startDate;
        this.memberPoints = initialPoints;
        this.employee = employee;
        determineMembershipType();
    }

    /**
     * Adjusts member points and checks to see if membership type should be upgraded.
     *
     * @param memberPoints the number to increase current member points with.
     */
    public void adjustMemberPoints(long memberPoints) {
        if (memberPoints < 0) {
            throw new IllegalArgumentException(String.format("%d is invalid.", memberPoints));
        }
        this.memberPoints += memberPoints;
        determineMembershipType();
    }

    /**
     * Sets the members' employment status.
     *
     * @param isEmployed holds the value of whether the customer is employed or not.
     */
    public void setEmploymentStatus(boolean isEmployed) {
        employee = isEmployed;
        determineMembershipType();
    }

    public boolean isAnEmployee() {
        return employee;
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

    public int getDiscountRate() {
        return membershipType.getDiscountPercentage();
    }

    public long getMemberPoints() {
        return memberPoints;
    }

    /**
     * If the customer is an employee, member points are disregarded.
     * I.e., it does not matter if the customer has millions of points -
     * the EMPLOYEE type still gives the highest possible discount rate.
     */
    private void determineMembershipType() {
        if (isAnEmployee()) {
            membershipType = MembershipType.EMPLOYEE;
        } else if (memberPoints >= THRESHOLD_FOR_GOLD_MEMBERSHIP) {
            membershipType = MembershipType.GOLD;
        } else if (memberPoints >= THRESHOLD_FOR_SILVER_MEMBERSHIP) {
            membershipType = MembershipType.SILVER;
        } else {
            membershipType = MembershipType.BRONZE;
        }
    }

    /**
     * Validates that the customer is above 18 years old.
     * Assumes that the customers' social security number has been entered properly.
     * Compares using milliseconds, the formula for 18 years in milliseconds is:
     * EIGHTEEN_YEARS_IN_MS = 18 years * 365,25 days * 24 hours * 60 minutes * 60 seconds * 1000 milliseconds.
     *
     * @param customer is the customer to verify the age of.
     */
    private void validateCustomerAge(Customer customer) throws ParseException{
        final long EIGHTEEN_YEARS_IN_MS = 568_036_800_000L;
        String firstSixDigits = customer.getSSNumber().substring(0, 6);
        boolean customerIsOver18 = false;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
            Date birthDate = dateFormat.parse(firstSixDigits);
            Date currentDate = new Date();
            if (currentDate.getTime() - birthDate.getTime() >= EIGHTEEN_YEARS_IN_MS) {
                customerIsOver18 = true;
            }
        } catch (ParseException exception) {
            throw new ParseException(exception.getMessage(), exception.getErrorOffset());
        }

        if (!customerIsOver18) {
            throw new IllegalStateException("The customer is not 18 years old.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s has been a member since %s. Member status: %s.", customer.getName(), startDate.toString(), membershipType);
    }
}
