package optional4j.test.autogen;

import javax.annotation.Nullable;
import optional4j.annotation.Optional4J;

public class NullableReturn {

    public abstract static class CustomerProvider {

        @Nullable
        public abstract Customer getCustomer();
    }

    public static interface AddressProvider {

        @Nullable
        Address getAddress();
    }

    public static interface CountryProvider {

        @Nullable
        Country getCountry();
    }

    @Optional4J
    public static class Order extends CustomerProvider {

        @Nullable
        public Customer getCustomer() {
            return null;
        }
    }

    @Optional4J
    public static class Customer implements AddressProvider {

        @Nullable
        public Address getAddress() {
            return null;
        }
    }

    @Optional4J
    public static class Address implements CountryProvider {

        @Nullable
        public Integer getZipcode() {
            return null;
        }

        @Nullable
        public Country getCountry() {
            return null;
        }

        @Nullable
        public Street getStreet() {
            return null;
        }
    }

    @Optional4J
    public static class Street {

        @Nullable
        public String getAddressLine() {
            return null;
        }
    }

    @Optional4J
    public static class Country {

        @Nullable
        public IsoCode getIsoCode() {
            return null;
        }
    }

    @Optional4J
    public static class IsoCode {

        @Nullable
        public AlphaCode2 getAlphaCode2() {
            return null;
        }
    }

    @Optional4J
    public static class AlphaCode2 {

        @Nullable
        public Year getYear() {
            return null;
        }
    }

    @Optional4J
    public static class Year {

        @Nullable
        public Integer getCode() {
            return null;
        }
    }

    @Nullable
    public Order getOrder() {
        return null;
    }
}
