package optional4j.test.model;

import java.util.Optional;
import optional4j.spec.Present;

public class Country implements Present<Country> {

    private IsoCode isoCode;

    public Country() {}

    public Country(IsoCode isoCode) {
        this.isoCode = isoCode;
    }

    public void setIsoCode(IsoCode isoCode) {
        this.isoCode = isoCode;
    }

    public optional4j.spec.Optional<IsoCode> getIsoCodeOptional4J() {
        return isoCode == null ? optional4j.spec.Optional.empty() : isoCode;
    }

    public Optional<IsoCode> getIsoCodeOptional() {
        return isoCode == null ? java.util.Optional.empty() : java.util.Optional.of(isoCode);
    }

    public IsoCode getIsoCode() {
        return this.isoCode;
    }
}
