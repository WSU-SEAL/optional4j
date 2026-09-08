package optional4j.test.autogen.localvariable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import optional4j.test.autogen.localvariable.CustomerLocalVariable.Customer;
import org.junit.Ignore;
import org.junit.Test;

public class CustomerLocalVariableTest {

    @Test
    public void getCustomerId() {

        String id = new Customer().getCustomerId();

        assertThat(id).isEqualTo("5678");
    }

    @Test
    @Ignore
    public void nullSafeTest() {

        Customer customer = new Customer();

        assertThat(customer.nullSafeTest()).isNull();
    }

    // get non-null using getter
    @Test
    public void getNonNullCustomerUsingGetter() {

        Customer customer = new Customer();

        assertThat(customer.getNonNullCustomerUsingGetter()).isNotNull();
    }

    // get null using getter
    @Test
    public void getNullCustomerUsingGetter() {

        Customer customer = new Customer();

        assertThat(customer.getNullCustomerUsingGetter()).isNull();
    }

    // get non-null using static-method
    @Test
    public void getNonNullCustomerUsingStaticMethod() {

        Customer customer = new Customer();

        assertThat(customer.getNonNullCustomerUsingStaticMethod()).isNotNull();
    }

    // get null using static-method
    @Test
    public void getNullCustomerUsingStaticMethod() {

        Customer customer = new Customer();

        assertThat(customer.getNullCustomerUsingStaticMethod()).isNull();
    }

    @Test
    @Ignore
    public void nullAssertTest() {

        Customer customer = new Customer();

        assertThatExceptionOfType(NullPointerException.class).isThrownBy(customer::nullAssertTest);
    }

    // assert non-null using getter
    @Test
    public void assertNonNullCustomerUsingGetter() {

        Customer customer = new Customer();

        customer.assertNonNullCustomerUsingGetter();
    }

    // assert null using getter
    @Test
    public void assertNullCustomerUsingGetter() {

        Customer customer = new Customer();

        customer.assertNullCustomerUsingGetter();
    }

    // assert non-null using static-method
    @Test
    public void assertNonNullCustomerUsingStaticMethod() {

        Customer customer = new Customer();

        customer.assertNonNullCustomerUsingStaticMethod();
    }

    // assert null using static-method
    @Test
    public void assertNullCustomerUsingStaticMethod() {

        Customer customer = new Customer();

        customer.assertNullCustomerUsingStaticMethod();
    }
}
