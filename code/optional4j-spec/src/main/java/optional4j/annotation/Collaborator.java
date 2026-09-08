package optional4j.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({TYPE, PARAMETER})
@Retention(RUNTIME)
public @interface Collaborator {

    Variation value() default Variation.COMMON_ANCESTOR;

    enum Variation {
        COMMON_ANCESTOR,
        COMMON_INTERFACE,
        SIMPLE
    }
}
