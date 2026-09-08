package optional4j.test.model;

import optional4j.spec.Present;

public class Year implements Present<Year> {

    private Integer value;

    public Year() {}

    public Year(Integer value) {
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
