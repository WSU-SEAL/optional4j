package optional4j.codegen.processor;

import optional4j.annotation.Optional4J;
import optional4j.codegen.builder.OptionalTypeBuilder;
import optional4j.codegen.visitor.optional4j.Optional4JVisitor;
import spoon.reflect.declaration.CtElement;

public class Optional4JAnnotationProcessor extends BaseAnnotationProcessor<Optional4J, CtElement> {

    @Override
    public void process(Optional4J valueType, CtElement ctElement) {

        getEnvironment().setAutoImports(true);

        ctElement.accept(
                new Optional4JVisitor(
                        this.getClass(),
                        getEnvironment(),
                        new OptionalTypeBuilder(getFactory()),
                        getProperties()));
    }
}
