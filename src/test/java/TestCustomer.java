import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomer {

    @Test
    void basicSetupTest() {
        Customer customer = new Customer.CustomerBuilder("Namn")
                .setPhoneNumber("1231")
                .setAddress("Kedjebacken 2A")
                .setEmailAddress("asd@hotmail.com")
                .setSSNumber("12939141")
                .build();
        System.out.println(customer);
    }

}
