package optional4j.test.autogen.localvariable;

import static org.assertj.core.api.Assertions.assertThat;

import optional4j.test.autogen.localvariable.StreetLocalVariable.Customer;
import org.junit.Test;

public class StreetLocalVariableTest {

    // get null street
    @Test
    public void getStreet() {

        Customer customer = new Customer();

        String street = customer.getStreet();

        assertThat(street).isNull();
    }

    // get null street
    @Test
    public void getStreetWithNullSafeAnnotationOnly() {

        Customer customer = new Customer();

        String street = customer.getStreetWithNullSafeAnnotationOnly();

        assertThat(street).isNull();
    }

    // get null street
    @Test
    public void getStreetValue() {

        Customer customer = new Customer();

        String street = customer.getStreetValue();

        assertThat(street).isEqualTo("barclay way");
    }

    // get null street
    @Test
    public void getStreetValueWithNullSafeAnnotationOnly() {

        Customer customer = new Customer();

        String street = customer.getStreetValueWithNullSafeAnnotationOnly();

        assertThat(street).isEqualTo("barclay way");
    }

    // assert null street
    @Test(expected = NullPointerException.class)
    public void assertStreet() {

        Customer customer = new Customer();

        String street = customer.assertStreet();
    }

    // assert null street
    @Test(expected = NullPointerException.class)
    public void assertStreetWithNullAssertAnnotationOnly() {

        Customer customer = new Customer();

        String street = customer.assertStreetWithNullAssertAnnotationOnly();
    }
}
