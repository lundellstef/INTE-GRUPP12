import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Entering a name and social security number is mandatory when creating a Customer, everything else is optional.
 * Because of this: call the boolean methods (i.e. "hasAPhoneNumber()") before using the values.
 * phoneNumber, emailAddress, address, membership.
 * Creation of Customer is handled with builder pattern - see bottom of class.
 */
public class Customer {

    private final String name;
    private final String sSNumber;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private Membership membership;

    private Customer(CustomerBuilder builder) {
        this.name = builder.name;
        this.sSNumber = builder.sSNumber;
        this.phoneNumber = builder.phoneNumber;
        this.emailAddress = builder.emailAddress;
        this.address = builder.address;
    }

    public boolean hasAPhoneNumber() {
        return phoneNumber != null;
    }

    public boolean hasAnEmailAddress() {
        return emailAddress != null;
    }

    public boolean hasAnAddress() {
        return address != null;
    }

    public boolean isAMember() {
        return membership != null;
    }

    public Membership getMembership() {
        return membership;
    }

    public void joinMembership(LocalDate joinDate, long initialPoints) {
        if (isAMember()) {
            throw new IllegalArgumentException("Already a member.");
        }
        membership = new Membership(this, joinDate, initialPoints);
    }

    public void leaveMembership() {
        if (!(isAMember())) {
            throw new IllegalArgumentException("Not a member.");
        }
        membership = null;
    }

    public String getName() {
        return name;
    }

    public String getSSNumber() {
        return sSNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Customer o)) {
            return false;
        }
        return o.sSNumber.equals(sSNumber) && o.name.equals(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sSNumber, name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Name = ").append(name).append(", SSNumber = ").append(sSNumber);
        if (hasAnAddress()) {
            sb.append(", Address = ").append(address);
        }
        if (hasAnEmailAddress()) {
            sb.append(", EmailAddress = ").append(emailAddress);
        }
        if (hasAPhoneNumber()) {
            sb.append(", PhoneNumber = ").append(phoneNumber);
        }
        sb.append("]");
        return sb.toString();
    }

    public static class CustomerBuilder {

        private final String name;
        private final String sSNumber;
        private String phoneNumber;
        private String emailAddress;
        private String address;

        public CustomerBuilder(String name, String sSNumber) {
            this.name = name;
            this.sSNumber = sSNumber;
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

        public Customer build() {
            validateCustomer();
            return new Customer(this);
        }

        /**
         * Method used to validate the inputs.
         * Validation of each individual variable is split into own method.
         */
        private void validateCustomer() {
            validateName();
            validateSSNumber();
            validatePhoneNumber();
            validateEmailAddress();
            validateAddress();

        }

        /**
         * Support method used to validate the customers entered name.
         * Does not need a null check since name is mandatory.
         */
        private void validateName() {
            // TODO: Add more validation checks for name.
            if (name.length() < 3) {
                throwIllegalArgument(name, "Too few characters.");
            }
            if (name.length() > 150) {
                throwIllegalArgument(name, "Too many characters.");
            }
            Pattern regex = Pattern.compile("[0-9]");
            if (regex.matcher(name).find()) {
                throwIllegalArgument(name, "Cannot have digits in the name.");
            }
            regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
            if (regex.matcher(name).find()) {
                throwIllegalArgument(name, "Cannot contain special characters.");
            }
        }

        /**
         * Support method used to validate the customers entered social security number.
         * Does not need a null check since social security number is mandatory.
         */
        private void validateSSNumber() {
            // TODO: Add more validation checks for social security number.
            if (sSNumber.length() != 10) {
                throwIllegalArgument(sSNumber, "Social security number must be exactly 10 digits.");
            }
            Pattern regex = Pattern.compile("[a-zA-Z]");
            if (regex.matcher(sSNumber).find()) {
                throwIllegalArgument(sSNumber, "Social security number cannot contain any characters.");
            }
        }

        /**
         * Support method used to validate the customers entered phone number.
         * Checks for null since phone number can be omitted.
         */
        private void validatePhoneNumber() {
            if (phoneNumber == null) {
                return;
            }
            // TODO: Add more validation checks for phone number.
            if (phoneNumber.length() > 10) {
                throwIllegalArgument(phoneNumber, "Too many numbers.");
            }
        }

        /**
         * Support method used to validate the customers entered email address.
         * Checks for null since email address can be omitted.
         */
        private void validateEmailAddress() {
            if (emailAddress == null) {
                return;
            }
            // TODO: Add more validation checks for email address.
            if (emailAddress.length() > 0) {
            }
        }

        /**
         * Support method used to validate the customers entered address.
         * Checks for null since address can be omitted.
         */
        private void validateAddress() {
            if (address == null) {
                return;
            }
            // TODO: Add more validation checks for address.
            if (address.length() > 0) {
            }

        }

        /**
         * Support method used to throw IllegalArgumentException and print appropriate message.
         *
         * @param value   is the variable that caused the exception to be thrown.
         * @param message is the message to be printed together with the exception.
         */
        private void throwIllegalArgument(String value, String message) {
            throw new IllegalArgumentException(String.format("%s is invalid. %s", value, message));
        }

    }
}
