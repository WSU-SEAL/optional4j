package optional4j.test.model;

import java.util.Optional;
import optional4j.spec.Present;

public class AlphaCode2 implements Present<AlphaCode2> {

    private Year year;

    public AlphaCode2() {}

    public AlphaCode2(Year year) {
        this.year = year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public optional4j.spec.Optional<Year> getYearOptional4J() {
        return year == null ? optional4j.spec.Optional.empty() : year;
    }

    public Optional<Year> getYearOptional() {
        return year == null ? java.util.Optional.empty() : java.util.Optional.of(year);
    }

    public Year getYear() {
        return this.year;
    }
}
