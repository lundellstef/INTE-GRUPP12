import java.util.Optional;

/**
 * Entering a name is mandatory when creating a Customer, everything else is optional.
 * Name and social security number cannot be changed, everything else can.
 */
public class Customer {

    private final String name;
    private final String sSNumber;
    private String phoneNumber;
    private String emailAddress;
    private String address;

    private Customer(CustomerBuilder builder) {
        this.name = builder.name;
        this.phoneNumber = builder.phoneNumber;
        this.emailAddress = builder.emailAddress;
        this.address = builder.address;
        this.sSNumber = builder.sSNumber;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Optional<String> getEmailAddress() {
        return Optional.ofNullable(emailAddress);
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Optional<String> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Optional<String> getSSNumber() {
        return Optional.ofNullable(sSNumber);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Name = ").append(name);
        if (getAddress().isPresent()) {
            sb.append(", Address = ").append(address);
        }
        if (getEmailAddress().isPresent()) {
            sb.append(", EmailAddress = ").append(emailAddress);
        }
        if (getPhoneNumber().isPresent()) {
            sb.append(", PhoneNumber = ").append(phoneNumber);
        }
        if (getSSNumber().isPresent()) {
            sb.append(", SSNumber = ").append(sSNumber);
        }
        sb.append("]");
        return sb.toString();
    }

    public static class CustomerBuilder {

        private final String name;
        private String phoneNumber;
        private String emailAddress;
        private String address;
        private String sSNumber;

        public CustomerBuilder(String name) {
            this.name = name;
        }

        public CustomerBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CustomerBuilder setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public CustomerBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public CustomerBuilder setSSNumber(String sSNumber) {
            this.sSNumber = sSNumber;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer(this);
            validateCustomer(customer);
            return customer;
        }

        /**
         * Support method used to validate the inputs.
         *
         * @param customer is the new Customer to validate.
         */
        private void validateCustomer(Customer customer) {

        }

    }
}
