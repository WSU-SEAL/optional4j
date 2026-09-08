package optional4j.codegen.processor;

import optional4j.annotation.NullSafe;
import optional4j.codegen.builder.OptionalTypeBuilder;
import optional4j.codegen.visitor.optional4j.Optional4JVisitor;
import spoon.reflect.declaration.CtElement;

public class NullSafeAnnotationProcessor extends BaseAnnotationProcessor<NullSafe, CtElement> {

    @Override
    public void process(NullSafe valueType, CtElement ctElement) {

        getEnvironment().setAutoImports(true);

        ctElement.accept(
                new Optional4JVisitor(
                        this.getClass(),
                        getEnvironment(),
                        new OptionalTypeBuilder(getFactory()),
                        getProperties()));
    }
}
