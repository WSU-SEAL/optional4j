package optional4j.test.autogen;

import static org.assertj.core.api.Assertions.assertThat;

import optional4j.spec.Optional;
import optional4j.test.autogen.OptionalReturn.Address;
import optional4j.test.autogen.OptionalReturn.Country;
import optional4j.test.autogen.OptionalReturn.Customer;
import optional4j.test.autogen.OptionalReturn.IsoCode;
import optional4j.test.autogen.OptionalReturn.Order;
import optional4j.test.autogen.OptionalReturn.Street;
import org.junit.Test;

public class OptionalReturnTest {

    @Test
    public void getOrder() {

        // given
        OptionalReturn myOptionalReturn = new OptionalReturn();

        // when
        Optional<Order> order = myOptionalReturn.getOrder();

        // then
        assertThat(Order.class.isAssignableFrom(order.getClass())).isFalse();
        assertThat(Optional.class.isAssignableFrom(order.getClass())).isTrue();
    }

    @Test
    public void getCustomer() {

        // given
        Order order = new Order();

        // when
        Optional<Customer> customer = order.getCustomer();

        // then
        assertThat(Customer.class.isAssignableFrom(customer.getClass())).isFalse();
        assertThat(Optional.class.isAssignableFrom(customer.getClass())).isTrue();
    }

    @Test
    public void isoCodeIsOptional() {

        // given
        Country country = new Country();

        // when
        Optional<IsoCode> isoCode = country.getIsoCode();

        // then
        assertThat(IsoCode.class.isAssignableFrom(isoCode.getClass())).isFalse();
        assertThat(Optional.class.isAssignableFrom(isoCode.getClass())).isTrue();
    }

    @Test
    public void countryIsOptional() {

        // given
        Address address = new Address();

        // when
        Optional<Country> country = address.getCountry();

        // then
        assertThat(Country.class.isAssignableFrom(country.getClass())).isFalse();
        assertThat(Optional.class.isAssignableFrom(country.getClass())).isTrue();
    }

    @Test
    public void zipcodeIsAnInteger() {

        // given
        Address address = new Address();

        // when
        Optional<Integer> zipcode = address.getZipcode();

        // then
        assertThat(Integer.class.isAssignableFrom(zipcode.getClass())).isFalse();
        assertThat(Optional.class.isAssignableFrom(zipcode.getClass())).isTrue();
    }

    @Test
    public void streetIsNullObject() {

        // given
        Address address = new Address();

        // when
        Optional<Street> street = address.getStreet();

        // then
        assertThat(Street.class.isAssignableFrom(street.getClass())).isFalse();
        assertThat(Optional.class.isAssignableFrom(street.getClass())).isTrue();
    }

    @Test
    public void addressIsOptional() {

        // given
        Customer customer = new Customer();

        // when
        Optional<Address> address = customer.getAddress();

        // then
        assertThat(Address.class.isAssignableFrom(address.getClass())).isFalse();
        assertThat(Optional.class.isAssignableFrom(address.getClass())).isTrue();
    }

    @Test
    public void name() {

        // given
        Customer customer = new Customer();

        // when
        Optional<Address> address = customer.getAddress();

        // then
        assertThat(Address.class.isAssignableFrom(address.getClass())).isFalse();
        assertThat(Optional.class.isAssignableFrom(address.getClass())).isTrue();
    }
}
