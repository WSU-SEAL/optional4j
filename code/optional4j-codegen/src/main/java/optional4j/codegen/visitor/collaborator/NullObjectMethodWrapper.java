package optional4j.codegen.visitor.collaborator;

import static java.util.stream.Collectors.joining;
import static optional4j.codegen.CodegenUtil.addNonNullAnnotation;
import static optional4j.codegen.CodegenUtil.getReturnType;
import static optional4j.codegen.CodegenUtil.isOptimisticMode;
import static optional4j.codegen.CodegenUtil.removeAnnotation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import optional4j.annotation.Config;
import optional4j.codegen.CodegenProperties;
import optional4j.codegen.builder.NullObjectBuilder;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.reference.CtTypeReference;

@RequiredArgsConstructor
public class NullObjectMethodWrapper {

    private final NullObjectBuilder nullObjectBuilder;

    private final CodegenProperties codegenProperties;

    public <T> CtMethod<T> wrapMethod(CtMethod<T> ctMethod) {

        CtMethod<T> wrapperMethod = doCreateNullObjectWrapperMethod(ctMethod);

        privatize(ctMethod);

        wrapperMethod.setBody(ofNullableMethodInvocation(wrapperMethod, ctMethod));

        // @NonNull Optional<Address> getAddress(){}
        addNonNullAnnotation(wrapperMethod, nullObjectBuilder.getFactory());
        removeAnnotation(wrapperMethod, nullObjectBuilder.getFactory(), Config.class);
        removeAnnotation(ctMethod, nullObjectBuilder.getFactory(), Config.class);

        return wrapperMethod;
    }

    private <T> CtMethod<T> doCreateNullObjectWrapperMethod(CtMethod<T> existingMethod) {

        // Address etAddress(){}
        CtMethod<T> newMethod = existingMethod.clone();

        // AbstractAddress
        CtTypeReference<?> newReturnType = getNullObjectTypeReference(existingMethod);

        // AbstractAddress getAddress(){}
        changeMethodReturnTypeToNullObject(newMethod, newReturnType);

        return newMethod;
    }

    private <T> CtTypeReference<?> getNullObjectTypeReference(CtMethod<T> ctMethod) {
        return nullObjectBuilder.getNullObjectTypeReference(getReturnType(ctMethod));
    }

    /**
     * Changes the method return type to FooNullObject
     *
     * @param ctMethod
     * @param newReturnType
     */
    private void changeMethodReturnTypeToNullObject(
            CtMethod ctMethod, CtTypeReference<?> newReturnType) {
        ctMethod.setType(newReturnType);
    }

    /**
     * Changes the method return type to FooNullObject
     *
     * @param ctMethod
     */
    public void changeMethodReturnTypeToNullObject(CtMethod ctMethod) {
        changeMethodReturnTypeToNullObject(ctMethod, getNullObjectTypeReference(ctMethod));
    }

    private <T> void privatize(CtMethod<T> ctMethod) {

        // do_getAddress
        ctMethod.setSimpleName("do_" + ctMethod.getSimpleName());

        if (ctMethod.getDeclaringType().isClass()) {
            // private do_getAddress(){}
            makeMethodPrivate(ctMethod);
        }
    }

    private <T> CtStatement ofNullableMethodInvocation(CtMethod<?> wrapper, CtMethod<T> ctMethod) {

        if (isOptimisticMode(ctMethod, codegenProperties)) {
            return ifNullStatement(wrapper.getType().getSimpleName(), ctMethod);
        }

        return ofNullObjectStatement(wrapper.getType().getSimpleName(), ctMethod);
    }

    private CtReturn<?> ofNullObjectStatement(String nullObjectTypeName, CtMethod<?> ctMethod) {
        return createReturn(
                nullObjectTypeName + "." + "ofNullable" + "(" + delegateMethodName(ctMethod) + ")");
    }

    private CtBlock<?> ifNullStatement(String nullObjectTypeName, CtMethod<?> ctMethod) {

        String localVariable = "toReturn$$";

        String declareLocalVariableStatement =
                ctMethod.getType().getSimpleName()
                        + " "
                        + localVariable
                        + " = "
                        + delegateMethodName(ctMethod);

        return nullObjectBuilder
                .createBlock()
                .addStatement(
                        nullObjectBuilder.createCodeSnippetStatement(declareLocalVariableStatement))
                .addStatement(
                        createReturn(
                                localVariable
                                        + " != null? "
                                        + localVariable
                                        + ": "
                                        + nullObjectTypeName
                                        + "."
                                        + "nullInstance"
                                        + "()"));
    }

    private CtReturn<?> createReturn(String toReturn) {
        return nullObjectBuilder
                .getFactory()
                .createReturn()
                .setReturnedExpression(
                        nullObjectBuilder.getFactory().createCodeSnippetExpression(toReturn));
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
}
