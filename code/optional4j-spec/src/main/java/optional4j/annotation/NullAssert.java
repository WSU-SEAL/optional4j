package optional4j.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Target;

@Target(LOCAL_VARIABLE)
public @interface NullAssert {

    Class<? extends Throwable> value() default NullPointerException.class;

    String message() default "";
}
