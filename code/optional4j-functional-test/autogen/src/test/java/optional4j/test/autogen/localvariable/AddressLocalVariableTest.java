package optional4j.test.autogen.localvariable;

import static org.assertj.core.api.Assertions.assertThat;

import optional4j.test.autogen.localvariable.AddressLocalVariable.Address;
import optional4j.test.autogen.localvariable.AddressLocalVariable.Customer;
import org.junit.Test;

public class AddressLocalVariableTest {

    // get null address using new customer instance
    @Test
    public void getNullAddressUsingNewInstance() {

        Customer customer = new Customer();

        assertThat(customer.getNullAddressUsingNewInstance()).isNull();
    }

    // get null address using customer's constructor
    @Test
    public void getNullAddressUsingConstructor() {

        Customer customer = new Customer();

        assertThat(customer.getNullAddressUsingConstructor()).isNull();
    }

    // get non-null address using customer's constructor
    @Test
    public void getNonNullAddressUsingConstructor() {

        Customer customer = new Customer();

        assertThat(customer.getNonNullAddressUsingConstructor()).isNotNull();
    }

    // get null address using customer's static method
    @Test
    public void getNullAddressUsingStaticMethod() {

        Customer customer = new Customer();

        assertThat(customer.getNullAddressUsingStaticMethod()).isNull();
    }

    // get non-null address using customer's static method
    @Test
    public void getNonNullAddressUsingStaticMethod() {

        Customer customer = new Customer();

        assertThat(customer.getNonNullAddressUsingStaticMethod()).isNotNull();
    }

    // get null customer getter
    @Test
    public void getNullCustomerUsingGetter() {

        Customer customer = new Customer();

        Address address = customer.getNullCustomerUsingGetter();

        assertThat(address).isNull();
    }

    // get null address using customer's getter
    @Test
    public void getNullAddressUsingGetter() {

        Customer customer = new Customer();

        assertThat(customer.getNullAddressUsingGetter()).isNull();
    }

    // get non-null address using customer's getter
    @Test
    public void getNonNullAddressUsingGetter() {

        Customer customer = new Customer();

        assertThat(customer.getNonNullAddressUsingGetter()).isNotNull();
    }

    // assert null address using customer's new instance
    @Test
    public void assertNullAddressUsingNewInstance() {

        Customer customer = new Customer();

        Address address = customer.assertNullAddressUsingNewInstance();

        assertThat(address).isNull();
    }

    // assert null address using customer's constructor
    @Test
    public void assertNullAddressUsingConstructor() {

        Customer customer = new Customer();

        Address address = customer.assertNullAddressUsingConstructor();

        assertThat(address).isNull();
    }

    // assert non-null address using customer's constructor
    @Test
    public void assertNonNullAddressUsingConstructor() {

        Customer customer = new Customer();

        Address address = customer.assertNonNullAddressUsingConstructor();

        assertThat(address).isNotNull();
    }

    // assert null customer getter
    @Test(expected = NullPointerException.class)
    public void assertNullCustomerUsingGetter() {

        Customer customer = new Customer();

        customer.assertNullCustomerUsingGetter();
    }

    // assert null address using customer's getter
    @Test
    public void assertNullAddressMethodUsingGetter() {

        Customer customer = new Customer();

        Address address = customer.assertNullAddressMethodUsingGetter();

        assertThat(address).isNull();
    }

    // assert non-null address using customer's getter
    @Test
    public void assertNonNullAddressMethodUsingGetter() {

        Customer customer = new Customer();

        Address address = customer.assertNonNullAddressMethodUsingGetter();

        assertThat(address).isNotNull();
    }

    // assert null customer's getter
    @Test(expected = NullPointerException.class)
    public void assertNullCustomerUsingStaticMethod() {

        Customer customer = new Customer();

        customer.assertNullCustomerUsingStaticMethod();
    }

    // assert null address using customer's getter
    @Test
    public void assertNullAddressUsingStaticMethod() {

        Customer customer = new Customer();

        Address address = customer.assertNullAddressUsingStaticMethod();

        assertThat(address).isNull();
    }

    // assert non-null address using customer's getter
    @Test
    public void assertNonNullAddressUsingStaticMethod() {

        Customer customer = new Customer();

        Address address = customer.assertNonNullAddressUsingStaticMethod();

        assertThat(address).isNotNull();
    }
}
