package optional4j.test.model;

import optional4j.spec.Optional;
import optional4j.spec.Present;

public class Address implements Present<Address> {

    private Country country;

    public Address() {}

    public Address(Country country) {
        this.country = country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Optional<Country> getCountryOptional4J() {
        return country == null ? optional4j.spec.Optional.empty() : country;
    }

    public java.util.Optional<Country> getCountryOptional() {
        return country == null ? java.util.Optional.empty() : java.util.Optional.of(country);
    }

    public Country getCountry() {
        return this.country;
    }
}
