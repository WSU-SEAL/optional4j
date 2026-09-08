package optional4j.codegen.visitor.optional4j;

import static java.util.stream.Collectors.joining;
import static optional4j.codegen.CodegenUtil.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import optional4j.annotation.Config;
import optional4j.codegen.CodegenProperties;
import optional4j.codegen.CodegenUtil;
import optional4j.codegen.builder.OptionalTypeBuilder;
import optional4j.spec.Optional;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.reference.CtTypeReference;

@RequiredArgsConstructor
public class OptionalMethodWrapper {

    private final OptionalTypeBuilder optionalTypeBuilder;

    private final CodegenProperties codegenProperties;

    public <T> CtMethod<T> wrapMethod(CtMethod<T> ctMethod) {

        CtMethod<T> wrapperMethod = doCreateOptionalWrapperMethod(ctMethod);

        privatize(ctMethod);

        wrapperMethod.setBody(ofNullableMethodInvocation(ctMethod));

        // @NonNull Optional<Address> getAddress(){}
        addNullableAnnotation(ctMethod, optionalTypeBuilder.getFactory());
        addNonNullAnnotation(wrapperMethod, optionalTypeBuilder.getFactory());
        removeAnnotation(wrapperMethod, optionalTypeBuilder.getFactory(), Config.class);
        removeAnnotation(ctMethod, optionalTypeBuilder.getFactory(), Config.class);

        return wrapperMethod;
    }

    private <T> CtMethod<T> doCreateOptionalWrapperMethod(CtMethod<T> ctMethod) {

        // Address getAddress(){}
        CtMethod<T> newMethod = ctMethod.clone();

        // Optional<Address> getAddress(){}
        changeMethodReturnTypeToOptional4J(newMethod);

        return newMethod;
    }

    private <T> void privatize(CtMethod<T> ctMethod) {

        // do_getAddress(){}
        ctMethod.setSimpleName("do_" + ctMethod.getSimpleName());

        if (ctMethod.getDeclaringType().isClass()) {
            // private do_getAddress(){}
            makeMethodPrivate(ctMethod);
        }
    }

    private <T> CtStatement ofNullableMethodInvocation(CtMethod<T> ctMethod) {

        if (isOptimisticMode(ctMethod, codegenProperties)) {
            return ifNullStatement(ctMethod);
        }

        return ofNullableMethodInvocationExpression(ctMethod);
    }

    private <T> CtReturn<?> ofNullableMethodInvocationExpression(CtMethod<T> ctMethod) {
        return createReturn(
                "optional4j.spec.Optional.ofNullable(" + delegateMethodName(ctMethod) + ")");
    }

    private CtBlock<?> ifNullStatement(CtMethod<?> ctMethod) {

        String localVariable = "toReturn$$";

        String declareLocalVariableStatement =
                ctMethod.getType().getSimpleName()
                        + " "
                        + localVariable
                        + " = "
                        + delegateMethodName(ctMethod);

        return optionalTypeBuilder
                .createBlock()
                .addStatement(
                        optionalTypeBuilder.createCodeSnippetStatement(
                                declareLocalVariableStatement))
                .addStatement(
                        createReturn(
                                localVariable
                                        + " != null? "
                                        + localVariable
                                        + ": Optional.empty()"));
    }

    private <T> CtReturn<?> createReturn(String toReturn) {
        return optionalTypeBuilder
                .getFactory()
                .createReturn()
                .setReturnedExpression(
                        optionalTypeBuilder.getFactory().createCodeSnippetExpression(toReturn));
    }

    private String delegateMethodName(CtMethod<?> ctMethod) {
        return ctMethod.getSimpleName() + "(" + joinParams(ctMethod.getParameters()) + ")";
    }

    private String joinParams(List<CtParameter<?>> parameters) {
        return parameters.stream().map(CtNamedElement::getSimpleName).collect(joining(","));
    }

    private <T> void makeMethodPrivate(CtMethod<T> ctMethod) {
        //        Set<ModifierKind> modifiers = ctMethod.getModifiers();
        //        modifiers.removeAll(Set.of(PRIVATE, PROTECTED, PUBLIC));
        //        modifiers.add(PRIVATE);
        //        ctMethod.setModifiers(modifiers);
    }

    /**
     * Changes the method return type to Optional<original-return-type>
     *
     * @param ctMethod
     */
    public void changeMethodReturnTypeToOptional4J(CtMethod ctMethod) {
        ctMethod.setType(optionalOf(ctMethod));
    }

    private CtTypeReference<Optional<?>> optionalOf(CtMethod<?> ctMethod) {
        return optionalTypeBuilder.createOptionalOf(CodegenUtil.getReturnTypeRef(ctMethod));
    }
}
