package optional4j.test.autogen.localvariable;

import optional4j.annotation.NullAssert;
import optional4j.annotation.NullSafe;
import optional4j.annotation.Optional4J;

public class CountryLocalVariable {

    public static class Customer {

        private Address address;

        public Customer() {}

        public Customer(Address address) {
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }

        public Year getYear() {
            @Optional4J
            Year year =
                    new Customer().getAddress().getCountry().getIsoCode().getAlphaCode2().getYear();
            return year;
        }

        public Year getYearWithNullSafeAnnotationOnly() {
            @NullSafe
            Year year =
                    new Customer().getAddress().getCountry().getIsoCode().getAlphaCode2().getYear();
            return year;
        }

        public Integer getYearValue() {
            @Optional4J
            @NullSafe("11")
            Integer value =
                    new Customer()
                            .getAddress()
                            .getCountry()
                            .getIsoCode()
                            .getAlphaCode2()
                            .getYear()
                            .getCode();
            return value;
        }

        public Integer getYearValueWithNullSafeAnnotationOnly() {

            @NullSafe("11")
            Integer value =
                    new Customer()
                            .getAddress()
                            .getCountry()
                            .getIsoCode()
                            .getAlphaCode2()
                            .getYear()
                            .getCode();
            return value;
        }

        public Year assertYear() {
            @Optional4J
            @NullAssert
            Year year =
                    new Customer().getAddress().getCountry().getIsoCode().getAlphaCode2().getYear();
            return year;
        }

        public Year assertYearWithNullAssertAnnotationOnly() {

            @NullAssert
            Year year =
                    new Customer().getAddress().getCountry().getIsoCode().getAlphaCode2().getYear();
            return year;
        }
    }

    public static class Address {

        private Country country;

        public Address() {}

        public Address(Country country) {
            this.country = country;
        }

        public Integer getZipcode() {
            return null;
        }

        public Country getCountry() {
            return country;
        }

        public Street getStreet() {
            return null;
        }
    }

    public static class Street {

        public String getAddressLine() {
            return null;
        }
    }

    public static class Country {

        public IsoCode getIsoCode() {
            return null;
        }
    }

    public static class IsoCode {

        public AlphaCode2 getAlphaCode2() {
            return null;
        }
    }

    public static class AlphaCode2 {

        public Year getYear() {
            return null;
        }
    }

    public static class Year {

        public Integer getCode() {
            return null;
        }
    }
}
