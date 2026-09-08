package optional4j.codegen.visitor.nullable;

import static optional4j.codegen.CodegenUtil.hasNonNullAnnotation;
import static optional4j.codegen.CodegenUtil.isValueType;
import static optional4j.codegen.CodegenUtil.printProcessing;
import static optional4j.codegen.CodegenUtil.returnsNullObjectType;
import static optional4j.support.ModeValue.PESSIMISTIC;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import optional4j.codegen.CodegenProperties;
import optional4j.codegen.builder.NullObjectBuilder;
import optional4j.codegen.builder.OptionalTypeBuilder;
import optional4j.codegen.visitor.collaborator.CollaboratorVisitor;
import optional4j.codegen.visitor.optional4j.Optional4JVisitor;
import spoon.compiler.Environment;
import spoon.processing.AnnotationProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.CtAbstractVisitor;

@RequiredArgsConstructor
public class NullableVisitor extends CtAbstractVisitor {

    private final Class<? extends AnnotationProcessor<?, ?>> processorClass;

    private final Environment environment;

    private final NullObjectBuilder nullObjectBuilder;

    private final OptionalTypeBuilder optionalTypeBuilder;

    private final CodegenProperties codegenProperties;

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        visitMethods(ctClass.getMethods());
    }

    private void visitMethods(Set<CtMethod<?>> methods) {
        methods.forEach(this::visitCtMethod);
    }

    @Override
    public <T> void visitCtMethod(CtMethod<T> ctMethod) {

        if (!codegenProperties.isNullityEnabled()) {
            return;
        }

        if (hasNonNullAnnotation(ctMethod)) {
            return;
        }

        printProcessing(environment, ctMethod);

        if (returnsNullObjectType(ctMethod)) {
            ctMethod.accept(
                    new CollaboratorVisitor(
                            processorClass,
                            environment,
                            nullObjectBuilder,
                            optionalTypeBuilder,
                            codegenProperties));
            return;
        }

        if (isValueType(ctMethod, optionalTypeBuilder.getFactory())
                || PESSIMISTIC.equals(codegenProperties.getMode())) {
            ctMethod.accept(
                    new Optional4JVisitor(
                            processorClass, environment, optionalTypeBuilder, codegenProperties));
            return;
        }

        // todo: what to do with nullable?
    }
}
