package optional4j.test.autogen;

import javax.annotation.Nullable;
import optional4j.annotation.Collaborator;
import optional4j.annotation.Optional4J;

public class CollaboratorClass {

    Order order;

    @Collaborator
    public static class Order {

        Customer customer;

        @Nullable
        public Customer getCustomer() {
            return customer;
        }

        //        @Collaborator
        public Customer getCollaboratorCustomer() {
            return null;
        }

        public String getIdText() {
            return "123";
        }

        public Integer getId() {
            return 123;
        }

        public Boolean isDelivery() {
            return true;
        }

        public Byte getByte() {
            return 11;
        }
    }

    @Optional4J
    public static class Customer {

        @Nullable
        public Address getAddress() {
            return null;
        }
    }

    @Optional4J
    public static class Address {

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
        return order;
    }
}
