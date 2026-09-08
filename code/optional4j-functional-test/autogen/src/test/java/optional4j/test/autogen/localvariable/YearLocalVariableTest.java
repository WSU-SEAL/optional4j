package optional4j.test.autogen.localvariable;

import static org.assertj.core.api.Assertions.assertThat;

import optional4j.test.autogen.localvariable.CountryLocalVariable.Customer;
import optional4j.test.autogen.localvariable.CountryLocalVariable.Year;
import org.junit.Test;

public class YearLocalVariableTest {

    // get null year
    @Test
    public void getYear() {

        Customer customer = new Customer();

        Year year = customer.getYear();

        assertThat(year).isNull();
    }

    // get null year
    @Test
    public void getYearWithNullSafeAnnotationOnly() {

        Customer customer = new Customer();

        Year year = customer.getYearWithNullSafeAnnotationOnly();

        assertThat(year).isNull();
    }

    // get null year
    @Test
    public void getYearValue() {

        Customer customer = new Customer();

        Integer year = customer.getYearValue();

        assertThat(year).isEqualTo(11);
    }

    // get null year
    @Test
    public void getYearValueWithNullSafeAnnotationOnly() {

        Customer customer = new Customer();

        Integer year = customer.getYearValueWithNullSafeAnnotationOnly();

        assertThat(year).isEqualTo(11);
    }

    // assert null year
    @Test(expected = NullPointerException.class)
    public void assertYear() {

        Customer customer = new Customer();

        Year year = customer.assertYear();
    }

    // assert null year
    @Test(expected = NullPointerException.class)
    public void assertYearWithNullAssertAnnotationOnly() {

        Customer customer = new Customer();

        Year year = customer.assertYearWithNullAssertAnnotationOnly();
    }
}
