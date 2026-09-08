package optional4j.codegen.processor;

import optional4j.annotation.NullAssert;
import optional4j.codegen.builder.NullObjectBuilder;
import optional4j.codegen.builder.OptionalTypeBuilder;
import optional4j.codegen.visitor.nullassert.NullAssertVisitor;
import spoon.reflect.declaration.CtElement;

public class NullAssertAnnotationProcessor extends BaseAnnotationProcessor<NullAssert, CtElement> {

    @Override
    public void process(NullAssert nullSafe, CtElement ctElement) {

        getEnvironment().setAutoImports(true);

        ctElement.accept(
                new NullAssertVisitor(
                        this.getClass(),
                        getEnvironment(),
                        new NullObjectBuilder(getFactory()),
                        new OptionalTypeBuilder(getFactory()),
                        getProperties()));
    }
}
