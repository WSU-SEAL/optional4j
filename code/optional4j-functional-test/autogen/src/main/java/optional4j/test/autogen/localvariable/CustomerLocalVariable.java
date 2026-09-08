package optional4j.test.autogen.localvariable;

import optional4j.annotation.NullAssert;
import optional4j.annotation.NullSafe;
import optional4j.annotation.Optional4J;

public class CustomerLocalVariable {

    public static class Customer {

        public String getCustomerId() {
            @NullSafe("\"5678\"")
            String id = calculateCustomerId();

            return id;
        }

        private String calculateCustomerId() {
            return null;
        }

        // ***************** BEGIN GET METHODS ****************** //
        public Integer nullSafeTest() {
            //
            // Integer value =
            // new Customer()
            // .getAddress()
            // .getCountry()
            // .getIsoCode()
            // .getCode()
            // .getYear()
            // .getValue()
            // .orElse(0);
            //
            // return value;
            return 0;
        }

        public void nullAssertTest() {
            // @NullAssert
            // Integer value =
            // new Customer()
            // .getAddress()
            // .getCountry()
            // .getIsoCode()
            // .getCode()
            // .getYear()
            // .getValue()
            // .orElse(0);
        }

        public Customer getNonNullCustomerUsingGetter() {
            @Optional4J Customer customer = doGetCustomer();
            return customer;
        }

        public Customer getNullCustomerUsingGetter() {
            @Optional4J Customer customer = doGetNullCustomer();
            return customer;
        }

        public Customer getNonNullCustomerUsingStaticMethod() {
            @Optional4J Customer customer = doGetCustomerStatically();
            return customer;
        }

        public Customer getNullCustomerUsingStaticMethod() {
            @Optional4J Customer customer = doGetNullCustomerStatically();
            return customer;
        }
        // ***************** END GET METHODS ****************** //

        // ***************** BEGIN ASSERT METHODS ****************** //
        public Customer assertNonNullCustomerUsingGetter() {
            @Optional4J
            @NullAssert
            Customer customer = doGetCustomer();

            return customer;
        }

        public Customer assertNullCustomerUsingGetter() {
            @Optional4J
            @NullAssert
            Customer customer = doGetNullCustomer();

            return customer;
        }

        public Customer assertNullCustomerUsingStaticMethod() {
            @Optional4J
            @NullAssert
            Customer customer = doGetNullCustomerStatically();
            return customer;
        }

        public Customer assertNonNullCustomerUsingStaticMethod() {
            @Optional4J
            @NullAssert
            Customer customer = doGetCustomerStatically();
            return customer;
        }
        // ***************** END ASSERT METHODS ****************** //

        private Customer doGetCustomer() {
            return new Customer();
        }

        private Customer doGetNullCustomer() {
            return null;
        }

        private static Customer doGetCustomerStatically() {
            return new Customer();
        }

        private static Customer doGetNullCustomerStatically() {
            return null;
        }
    }
}
