package optional4j.test.autogen;

import optional4j.annotation.Optional4J;

public class OptionalReturn {

    public abstract static class CustomerProvider {

        @Optional4J
        public abstract Customer getCustomer();
    }

    public static interface AddressProvider {

        @Optional4J
        Address getAddress();
    }

    public static interface CountryProvider {

        @Optional4J
        Country getCountry();
    }

    @Optional4J
    public static class Order extends CustomerProvider {

        @Optional4J
        public Customer getCustomer() {
            return null;
        }
    }

    @Optional4J
    public static class Customer implements AddressProvider {

        @Optional4J
        public Address getAddress() {
            return null;
        }
    }

    @Optional4J
    public static class Address implements CountryProvider {

        @Optional4J
        public Integer getZipcode() {
            return null;
        }

        @Optional4J
        public Country getCountry() {
            return null;
        }

        @Optional4J
        public Street getStreet() {
            return null;
        }
    }

    @Optional4J
    public static class Street {

        @Optional4J
        public String getAddressLine() {
            return null;
        }
    }

    @Optional4J
    public static class Country {

        @Optional4J
        public IsoCode getIsoCode() {
            return null;
        }
    }

    @Optional4J
    public static class IsoCode {

        @Optional4J
        public AlphaCode2 getAlphaCode2() {
            return null;
        }
    }

    @Optional4J
    public static class AlphaCode2 {

        @Optional4J
        public Year getYear() {
            return null;
        }
    }

    @Optional4J
    public static class Year {

        @Optional4J
        public Integer getCode() {
            return null;
        }
    }

    @Optional4J
    public Order getOrder() {
        return null;
    }
}
