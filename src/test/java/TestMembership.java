import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestMembership {

    /**
     * Over 18 social security number: 2002-01-02-1234
     * Under 18 social security number: 2005-11-25-1234
     */
    private static final String SOCIAL_SECURITY_NUMBER_OVER_18 = "0201021234";
    private static final String SOCIAL_SECURITY_NUMBER_UNDER_18 = "0511251234";

    @Test
    void throwsException_when_customerIsUnder18() {
        Customer customer = createCustomerUnder18();
        assertThrows(IllegalStateException.class, () -> customer.joinMembership(0, false));
    }

    @Test
    void hasNoDiscount_when_customerIsNotEmployed_andHasNoPoints() {
        Customer customer = createCustomerOver18();
        customer.joinMembership(0, false);
        int discountRate = customer.getMembership().getDiscountRate();
        assertEquals(discountRate, 0);
    }

    @Test
    void has15PercentDiscount_when_customerIsEmployed() {
        Customer customer = createCustomerOver18();
        customer.joinMembership(0, true);
        int discountRate = customer.getMembership().getDiscountRate();
        assertEquals(discountRate, 15);
    }

    @Test
    void has5PercentDiscount_when_customerIsNotEmployed_butHas25kPoints() {
        Customer customer = createCustomerOver18();
        customer.joinMembership(25_000_00, false);
        int discountRate = customer.getMembership().getDiscountRate();
        assertEquals(discountRate, 5);
    }

    @Test
    void has10PercentDiscount_when_customerIsNotEmployed_butHas100kPoints() {
        Customer customer = createCustomerOver18();
        customer.joinMembership(100_000_00, false);
        int discountRate = customer.getMembership().getDiscountRate();
        assertEquals(discountRate, 10);
    }

    @Test
    void updatesMembershipType_when_adjustingMemberPoints() {
        Customer customer = createCustomerOver18();
        customer.joinMembership(10_000_00, false);

        // Contains an additional assert to ensure that the membership type before the adjustment is BRONZE.
        assertEquals(customer.getMembership().getMembershipType(), MembershipType.BRONZE);

        customer.getMembership().adjustMemberPoints(15_000_00);
        assertEquals(customer.getMembership().getMembershipType(), MembershipType.SILVER);
    }

    @Test
    void updatesMembershipType_when_adjustingEmploymentStatus() {
        Customer customer = createCustomerOver18();
        customer.joinMembership(0, true);

        // Contains an additional assert to ensure that the membership type before the adjustment is EMPLOYEE.
        assertEquals(customer.getMembership().getMembershipType(), MembershipType.EMPLOYEE);

        customer.getMembership().setEmploymentStatus(false);
        assertEquals(customer.getMembership().getMembershipType(), MembershipType.BRONZE);
    }

    @Test
    public void isALegacyMember_when_beenAMemberForTenYears(){
        LocalDate currentDate = LocalDate.of(2023, 1, 1);
        LocalDate joinDate = LocalDate.of(2010, 1, 1);
        try (MockedStatic<LocalDate> localDateMock = Mockito.mockStatic(LocalDate.class)) {
            localDateMock.when(LocalDate::now).thenReturn(currentDate);
            Customer customer = createCustomerOver18();
            customer.joinMembership(0, false, joinDate);
            assertTrue(customer.getMembership().isALegacyMember());
        }
    }

    @Test
    public void isNotALegacyMember_when_beenAMemberForLessThanTenYears() {
        LocalDate currentDate = LocalDate.of(2023, 1, 1);
        LocalDate joinDate = LocalDate.of(2022, 1, 1);
        try (MockedStatic<LocalDate> localDateMock = Mockito.mockStatic(LocalDate.class)) {
            localDateMock.when(LocalDate::now).thenReturn(currentDate);
            Customer customer = createCustomerOver18();
            customer.joinMembership(0, false, joinDate);
            assertFalse(customer.getMembership().isALegacyMember());
        }
    }

    @Test
    public void throwsException_when_tryingToAdjustMemberPoints_withNegativeValue() {
        Customer customer = createCustomerOver18();
        customer.joinMembership(0, false);
        assertThrows(IllegalArgumentException.class, () -> customer.getMembership().adjustMemberPoints(-5));
    }

    @Test
    public void printsMembershipInfoCorrectly_when_toStringCalled() {
        Customer customer = createCustomerOver18();
        LocalDate joinDate = LocalDate.of(2023, 7, 30);
        customer.joinMembership(0, false, joinDate);
        String expectedString = "CustomerName has been a member since 2023-07-30. Member status: Bronze.";
        String actualString = customer.getMembership().toString();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void displaysTheUpdatedMemberPoints_when_memberPointsAreUpdatedAndFetched() {
        long initialPoints = 100;
        long pointsToAdjustBy = 200;

        Customer customer = createCustomerOver18();
        customer.joinMembership(initialPoints, false);
        customer.getMembership().adjustMemberPoints(pointsToAdjustBy);

        long expectedPoints = 300;
        long actualPoints = customer.getMembership().getMemberPoints();

        assertEquals(expectedPoints, actualPoints);
    }

    @Test
    public void setsStartDateCorrectly_when_joiningMembership() {
        Customer customer = createCustomerOver18();
        LocalDate joinDate = LocalDate.of(2023, 7, 30);
        customer.joinMembership(0, false, joinDate);

        LocalDate actualDate = customer.getMembership().getStartDate();
        assertEquals(joinDate, actualDate);
    }

    private Customer createCustomerOver18() {
        return new Customer.CustomerBuilder("CustomerName", SOCIAL_SECURITY_NUMBER_OVER_18).build();
    }

    private Customer createCustomerUnder18() {
        return new Customer.CustomerBuilder("CustomerName", SOCIAL_SECURITY_NUMBER_UNDER_18).build();
    }

}
