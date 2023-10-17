import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomer {

    static final String CUSTOMER_INFORMATION = "[Name = Peter, SSNumber = 9804195555, Address = Vaskvägen 89, EmailAddress = peter@hotmail.com, PhoneNumber = 0708766789]";

    static final String VALID_NAME = "Peter";
    static final String VALID_SS_NUMBER = "9804195555";
    static final String VALID_ADDRESS = "Vaskvägen 89";
    static final String VALID_EMAIL_ADDRESS = "peter@hotmail.com";
    static final String VALID_PHONE_NUMBER = "0708766789";

    @Test
    public void createCustomerWithAllValuesEnteredStoresCorrectInformation(){
        Customer customer = setUpTestCustomerWithAllValuesEntered();
        assertEquals(CUSTOMER_INFORMATION, customer.toString());
    }

    @Test
    public void throwsExceptionWhenNoSSNumberIsEntered(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, "").build();
        });
    }

    @Test
    public void throwsExceptionWhenNoNameIsEntered(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder("", VALID_SS_NUMBER).build();
        });
    }

    @Test
    public void createCustomerWithOnlyNameAndSSNumberWorks(){
        Customer customer = new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER).build();
        String expectedCustomerString = "[Name = Peter, SSNumber = 9804195555]";
        assertEquals(expectedCustomerString, customer.toString());
    }

    @Test
    public void nameContainingNumbersThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder("Peter1", VALID_SS_NUMBER)
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void nameContainingSpecialCharactersThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder("Peter!", VALID_SS_NUMBER)
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void nameContainingDashCanStillCreateCustomer(){

            new Customer.CustomerBuilder("Peter-Peter",VALID_SS_NUMBER)
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
    }

    @Test
    public void lengthOfSSNumberLessThanTenThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, "123")
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void lengthOfSSNumberLongerThanTenThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, "12345678910")
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void sSNumberContainingLettersThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, "123456789L")
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void sSNumberContainingSpecialCharactersThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, "123456789!")
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void lengthOfPhoneNumberLessThanEightDigitsThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                    .setPhoneNumber("123")
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void lengthOfPhoneNumberMoreThanTenDigitsThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                    .setPhoneNumber("12345678910")
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void phoneNumberContainingLettersThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                    .setPhoneNumber("1234567A")
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void phoneNumberContainingSpecialCharactersThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                    .setPhoneNumber("1234567!")
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void addressContainingSpecialCharactersThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress("Vaskvägen 89!")
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void addressWithOutStreetNumberThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress("Vaskvägen")
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void addressWithOutStreetNameThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress("89")
                    .setEmailAddress(VALID_EMAIL_ADDRESS)
                    .build();
        });
    }

    @Test
    public void emailAddressThatBeginsWithAtThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress("@peter.hotmail.com")
                    .build();
        });
    }

    @Test
    public void emailAddressThatEndsWithAtThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress("peter.hotmail.com@")
                    .build();
        });
    }

    @Test
    public void emailAddressThatEndsWithNumbersThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                    .setPhoneNumber(VALID_PHONE_NUMBER)
                    .setAddress(VALID_ADDRESS)
                    .setEmailAddress("peter@hotmail.111")
                    .build();
        });
    }

    @Test
    public void joinMemberShipWhenNotAlreadyMemberCorrectlyJoinsMembership(){
        Customer customer = setUpTestCustomerWithAllValuesEntered();
        customer.joinMembership(0);
        String memberShipDescription = "Peter has been a member since 2023-10-17. Member status: Bronze.";
        assertEquals(memberShipDescription, customer.getMembership().toString());
    }

    @Test
    public void joinMembershipWhenAlreadyMemberThrowsException(){
        Customer customer = setUpTestCustomerWithAllValuesEntered();
        customer.joinMembership(0);
        assertThrows(IllegalArgumentException.class, () -> {
            customer.joinMembership(0);
        });
    }

    @Test
    public void leaveMemberShipWhenNotMemberThrowsException() {
        Customer customer = setUpTestCustomerWithAllValuesEntered();
        assertThrows(IllegalArgumentException.class, customer::leaveMembership);
    }

    @Test
    public void leaveMemberShipWhenMemberRemovesMemberShip() {
        Customer customer = setUpTestCustomerWithAllValuesEntered();
        customer.joinMembership(0);
        customer.leaveMembership();
        assertNull(customer.getMembership());
    }

    @Test
    public void getNameReturnsCorrectName(){
        Customer customer = setUpTestCustomerWithAllValuesEntered();
        assertEquals(VALID_NAME, customer.getName());
    }

    @Test
    public void getSSNumberReturnsCorrectNumber(){
        Customer customer = setUpTestCustomerWithAllValuesEntered();
        assertEquals(VALID_SS_NUMBER, customer.getSSNumber());
    }

    @Test
    public void getPhoneNumberReturnsCorrectNumber(){
        Customer customer = setUpTestCustomerWithAllValuesEntered();
        assertEquals(VALID_PHONE_NUMBER, customer.getPhoneNumber());
    }

    @Test
    public void getEmailAddressReturnsCorrectNumber(){
        Customer customer = setUpTestCustomerWithAllValuesEntered();
        assertEquals(VALID_EMAIL_ADDRESS, customer.getEmailAddress());
    }



    private Customer setUpTestCustomerWithAllValuesEntered() {
        return new Customer.CustomerBuilder(VALID_NAME, VALID_SS_NUMBER)
                .setPhoneNumber(VALID_PHONE_NUMBER)
                .setAddress(VALID_ADDRESS)
                .setEmailAddress(VALID_EMAIL_ADDRESS)
                .build();

    }



}
