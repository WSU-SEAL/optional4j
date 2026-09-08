package optional4j.test.model;

import java.util.Optional;
import optional4j.spec.Present;

public class IsoCode implements Present<IsoCode> {

    private AlphaCode2 alphaCode2;

    public IsoCode() {}

    public IsoCode(AlphaCode2 alphaCode2) {
        this.alphaCode2 = alphaCode2;
    }

    public void setAlphaCode2(AlphaCode2 alphaCode2) {
        this.alphaCode2 = alphaCode2;
    }

    public optional4j.spec.Optional<AlphaCode2> getAlphaCode2Optional4J() {
        return alphaCode2 == null ? optional4j.spec.Optional.empty() : alphaCode2;
    }

    public Optional<AlphaCode2> getAlphaCode2Optional() {
        return alphaCode2 == null ? java.util.Optional.empty() : java.util.Optional.of(alphaCode2);
    }

    public AlphaCode2 getAlphaCode2() {
        return this.alphaCode2;
    }
}
