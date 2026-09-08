package optional4j.codegen.processor;

import optional4j.annotation.Collaborator;
import optional4j.codegen.builder.NullObjectBuilder;
import optional4j.codegen.builder.OptionalTypeBuilder;
import optional4j.codegen.visitor.collaborator.CollaboratorVisitor;
import spoon.reflect.declaration.CtElement;

public class CollaboratorAnnotationProcessor
        extends BaseAnnotationProcessor<Collaborator, CtElement> {

    @Override
    public void process(Collaborator nullObjectType, CtElement ctElement) {

        getEnvironment().setAutoImports(true);

        ctElement.accept(
                new CollaboratorVisitor(
                        this.getClass(),
                        getEnvironment(),
                        new NullObjectBuilder(getFactory()),
                        new OptionalTypeBuilder(getFactory()),
                        getProperties()));
    }
}
