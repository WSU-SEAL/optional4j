package optional4j.test.model;

import java.util.Optional;
import optional4j.spec.Present;

public class Customer implements Present<Customer> {

    private Address address;

    public Customer() {}

    public Customer(Address address) {
        this.address = address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public optional4j.spec.Optional<Address> getAddressOptional4J() {
        return address == null ? optional4j.spec.Optional.empty() : address;
    }

    public Optional<Address> getAddressOptional() {
        return address == null ? Optional.empty() : Optional.of(address);
    }

    public Address getAddress() {
        return this.address;
    }
}
