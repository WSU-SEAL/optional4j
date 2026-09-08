package optional4j.test.autogen.localvariable;

import optional4j.annotation.NullAssert;
import optional4j.annotation.Optional4J;

public class AddressLocalVariable {

    public static class Customer {

        private Address address;

        public Customer() {}

        public Customer(Address address) {
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }

        // ***************** BEGIN GET METHODS ****************** //
        public Address getNullAddressUsingNewInstance() {
            @Optional4J Address address = new Customer().getAddress();
            return address;
        }

        public Address getNullAddressUsingGetter() {
            @Optional4J Address address = doGetCustomer(null).getAddress();
            return address;
        }

        public Address getNonNullAddressUsingGetter() {
            @Optional4J Address address = doGetCustomer(new Address()).getAddress();
            return address;
        }

        public Address getNullAddressUsingStaticMethod() {
            @Optional4J Address address = doGetCustomerStatically(null).getAddress();
            return address;
        }

        public Address getNullCustomerUsingStaticMethod() {
            @Optional4J Address address = doGetNullCustomerStatically().getAddress();
            return address;
        }

        public Address getNullCustomerUsingGetter() {
            @Optional4J Address address = doGetNullCustomer().getAddress();
            return address;
        }

        public Address getNonNullAddressUsingStaticMethod() {
            @Optional4J Address address = doGetCustomerStatically(new Address()).getAddress();
            return address;
        }

        public Address getNonNullAddressUsingConstructor() {
            @Optional4J Address address = new Customer(new Address()).getAddress();
            return address;
        }

        public Address getNullAddressUsingConstructor() {
            @Optional4J Address address = new Customer(null).getAddress();
            return address;
        }
        // ***************** END GET METHODS ****************** //

        // ***************** BEGIN ASSERT METHODS ****************** //
        public Address assertNullAddressUsingNewInstance() {
            @Optional4J
            @NullAssert
            Address address = new Customer().getAddress();

            return address;
        }

        public Address assertNullAddressMethodUsingGetter() {
            @Optional4J
            @NullAssert
            Address address = doGetCustomer(null).getAddress();

            return address;
        }

        public Address assertNullCustomerUsingStaticMethod() {
            @Optional4J
            @NullAssert
            Address address = doGetNullCustomerStatically().getAddress();
            return address;
        }

        public Address assertNullCustomerUsingGetter() {
            @Optional4J
            @NullAssert
            Address address = doGetNullCustomer().getAddress();
            return address;
        }

        public Address assertNonNullAddressMethodUsingGetter() {
            @Optional4J
            @NullAssert
            Address address = doGetCustomer(new Address()).getAddress();

            return address;
        }

        public Address assertNullAddressUsingStaticMethod() {
            @Optional4J
            @NullAssert
            Address address = doGetCustomerStatically(null).getAddress();
            return address;
        }

        public Address assertNonNullAddressUsingStaticMethod() {
            @Optional4J
            @NullAssert
            Address address = doGetCustomerStatically(new Address()).getAddress();

            return address;
        }

        public Address assertNonNullAddressUsingConstructor() {
            @Optional4J
            @NullAssert
            Address address = new Customer(new Address()).getAddress();
            return address;
        }

        public Address assertNullAddressUsingConstructor() {
            @Optional4J
            @NullAssert
            Address address = new Customer(null).getAddress();
            return address;
        }
        // ***************** END ASSERT METHODS ****************** //

        private Customer doGetCustomer(Address address) {
            return new Customer(address);
        }

        private Customer doGetNullCustomer() {
            return null;
        }

        private static Customer doGetCustomerStatically(Address address) {
            return new Customer(address);
        }

        private static Customer doGetNullCustomerStatically() {
            return null;
        }
    }

    public static class Address {

        public Address() {}
    }
}
