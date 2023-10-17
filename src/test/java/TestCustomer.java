import org.junit.jupiter.api.Test;

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
            new Customer.CustomerBuilder("Peter1", "9804195555")
                    .setPhoneNumber("0708766789")
                    .setAddress("Vaskvägen 89")
                    .setEmailAddress("peter@hotmail.com")
                    .build();
        });
    }

    @Test
    public void nameContainingSpecialCharactersThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer.CustomerBuilder("Peter!", "9804195555")
                    .setPhoneNumber("0708766789")
                    .setAddress("Vaskvägen 89")
                    .setEmailAddress("peter@hotmail.com")
                    .build();
        });
    }

    @Test
    public void nameContainingDashCanStillCreateCustomer(){

            new Customer.CustomerBuilder("Peter-Peter", "9804195555")
                    .setPhoneNumber("0708766789")
                    .setAddress("Vaskvägen 89")
                    .setEmailAddress("peter@hotmail.com")
                    .build();
    }


    private Customer setUpTestCustomerWithAllValuesEntered() {
        return new Customer.CustomerBuilder("Peter", "9804195555")
                .setPhoneNumber("0708766789")
                .setAddress("Vaskvägen 89")
                .setEmailAddress("peter@hotmail.com")
                .build();

    }

}
