import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomer {

    @Test
    void inProgressTest() {
        Customer customer = new Customer.CustomerBuilder("Namn", "9507301122")
                .setPhoneNumber("1231")
                .setAddress("Kedjebacken 2A")
                .setEmailAddress("asd@hotmail.com")
                .build();
        System.out.println(customer);
    }

}
