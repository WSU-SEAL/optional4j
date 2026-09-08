package optional4j.test.model;

import java.util.Optional;
import optional4j.spec.Present;

public class Order implements Present<Order> {

    private Customer customer;

    public Order() {}

    public Order(Customer customer) {
        this.customer = customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public optional4j.spec.Optional<Customer> getCustomerOptional4J() {
        return customer == null ? optional4j.spec.Optional.empty() : customer;
    }

    public Optional<Customer> getCustomerOptional() {
        return customer == null ? Optional.empty() : Optional.of(customer);
    }

    public Customer getCustomer() {
        return this.customer;
    }
}
