package optional4j.test.autogen.localvariable;

import optional4j.annotation.NullAssert;
import optional4j.annotation.NullSafe;
import optional4j.annotation.Optional4J;

public class StreetLocalVariable {

    public static class Customer {

        private Address address;

        public Customer() {}

        public Customer(Address address) {
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }

        public String getStreet() {
            @Optional4J Street street = new Customer().getAddress().getStreet().getAddressLine();
            return street;
        }

        public String getStreetWithNullSafeAnnotationOnly() {
            @NullSafe Street street = new Customer().getAddress().getStreet().getAddressLine();
            return street;
        }

        public String getStreetValue() {
            @Optional4J
            @NullSafe("\"barclay way\"")
            String value = new Customer().getAddress().getStreet().getAddressLine();
            return value;
        }

        public String getStreetValueWithNullSafeAnnotationOnly() {

            @NullSafe("\"barclay way\"")
            String value = new Customer().getAddress().getStreet().getAddressLine();
            return value;
        }

        public String assertStreet() {
            @Optional4J
            @NullAssert
            Street street = new Customer().getAddress().getStreet().getAddressLine();
            return street;
        }

        public String assertStreetWithNullAssertAnnotationOnly() {

            @NullAssert Street street = new Customer().getAddress().getStreet().getAddressLine();
            return street;
        }
    }

    public static class Address {

        public Street getStreet() {
            return null;
        }
    }

    public static class Street {

        public String getAddressLine() {
            return null;
        }
    }
}
