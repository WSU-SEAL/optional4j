package optional4j.performance;

import gopt.Goptional;
import optional4j.spec.Optional;
import optional4j.test.model.Address;
import optional4j.test.model.AlphaCode2;
import optional4j.test.model.Country;
import optional4j.test.model.Customer;
import optional4j.test.model.IsoCode;
import optional4j.test.model.Order;
import optional4j.test.model.Year;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class OptionalDepthProfiler {

    static final Year YEAR_NOT_FOUND = new Year(0);

    private Order order;

    @Param({"0", "1", "2", "3", "4", "5", "6"})
    private String depth;

    @Setup
    public void setUp() {

        switch (depth) {
            case "0":
                order = null;
                break;
            case "1":
                order = new Order(null);
                break;
            case "2":
                order = new Order(new Customer(null));
                break;
            case "3":
                order = new Order(new Customer(new Address(null)));
                break;
            case "4":
                order = new Order(new Customer(new Address(new Country(null))));
                break;
            case "5":
                order = new Order(new Customer(new Address(new Country(new IsoCode(null)))));
                break;
            case "6":
                order =
                        new Order(
                                new Customer(
                                        new Address(
                                                new Country(new IsoCode(new AlphaCode2(null))))));
                break;
            case "7":
                order =
                        new Order(
                                new Customer(
                                        new Address(
                                                new Country(
                                                        new IsoCode(
                                                                new AlphaCode2(new Year(null)))))));
                break;
            default:
                throw new IllegalStateException("Unsupported filling.");
        }

        new Order(new Customer(new Address(new Country(new IsoCode(new AlphaCode2(new Year(5)))))));

        for (int i = 0; i < 1000; i++) {
            java.util.Optional.ofNullable(null);
            optional4j.spec.Optional.ofNullable(null);
            gopt.Goptional.fromNullable(null);
        }
    }

    @Benchmark
    public void base(Blackhole blackhole) {
        blackhole.consume(getYearUsingConditionalIfElse(order));
    }

    @Benchmark
    public void optional4j(Blackhole blackhole) {

        blackhole.consume(
                Optional.ofNullable(order)
                        .flatMap(Order::getCustomerOptional4J)
                        .flatMap(Customer::getAddressOptional4J)
                        .flatMap(Address::getCountryOptional4J)
                        .flatMap(Country::getIsoCodeOptional4J)
                        .flatMap(IsoCode::getAlphaCode2Optional4J)
                        .flatMap(AlphaCode2::getYearOptional4J)
                        .orElse(YEAR_NOT_FOUND));
    }

    @Benchmark
    public void optional(Blackhole blackhole) {

        blackhole.consume(
                java.util.Optional.ofNullable(order)
                        .flatMap(Order::getCustomerOptional)
                        .flatMap(Customer::getAddressOptional)
                        .flatMap(Address::getCountryOptional)
                        .flatMap(Country::getIsoCodeOptional)
                        .flatMap(IsoCode::getAlphaCode2Optional)
                        .flatMap(AlphaCode2::getYearOptional)
                        .orElse(YEAR_NOT_FOUND));
    }

    @Benchmark
    public void guava(Blackhole blackhole) {

        blackhole.consume(
                Goptional.fromNullable(order)
                        .transform(Order::getCustomer)
                        .transform(Customer::getAddress)
                        .transform(Address::getCountry)
                        .transform(Country::getIsoCode)
                        .transform(IsoCode::getAlphaCode2)
                        .transform(AlphaCode2::getYear)
                        .or(YEAR_NOT_FOUND));
    }

    public static Object getYearUsingConditionalIfElse(Order order) {

        if (order == null) {
            return YEAR_NOT_FOUND;
        }

        Customer customer = order.getCustomer();
        if (customer == null) {
            return YEAR_NOT_FOUND;
        }

        Address address = customer.getAddress();
        if (address == null) {
            return YEAR_NOT_FOUND;
        }

        Country country = address.getCountry();
        if (country == null) {
            return YEAR_NOT_FOUND;
        }

        IsoCode isoCode = country.getIsoCode();
        if (isoCode == null) {
            return YEAR_NOT_FOUND;
        }

        AlphaCode2 code = isoCode.getAlphaCode2();
        if (code == null) {
            return YEAR_NOT_FOUND;
        }

        Year year = code.getYear();
        if (year == null) {
            return YEAR_NOT_FOUND;
        }

        return year;
    }
}
