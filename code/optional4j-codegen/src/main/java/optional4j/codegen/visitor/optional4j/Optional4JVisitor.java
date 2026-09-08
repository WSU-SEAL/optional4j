package optional4j.codegen.visitor.optional4j;

import static optional4j.codegen.CodegenUtil.addGeneratedAnnotation;
import static optional4j.codegen.CodegenUtil.addNonNullAnnotation;
import static optional4j.codegen.CodegenUtil.getNullableMethods;
import static optional4j.codegen.CodegenUtil.getNullness;
import static optional4j.codegen.CodegenUtil.hasNonNullAnnotation;
import static optional4j.codegen.CodegenUtil.isOptimisticMode;
import static optional4j.codegen.CodegenUtil.isOptionalReturn;
import static optional4j.codegen.CodegenUtil.isValueType;
import static optional4j.codegen.CodegenUtil.isVoidReturn;
import static optional4j.codegen.CodegenUtil.printProcessing;
import static optional4j.codegen.CodegenUtil.removeAnnotation;
import static optional4j.codegen.CodegenUtil.returnsNullObjectType;
import static optional4j.support.NullabilityValue.NULLABLE;

import java.util.Random;
import java.util.Set;
import javax.annotation.NonNull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import optional4j.annotation.Collaborator;
import optional4j.annotation.Config;
import optional4j.annotation.NullAssert;
import optional4j.annotation.NullSafe;
import optional4j.annotation.Optional4J;
import optional4j.codegen.CodegenProperties;
import optional4j.codegen.builder.NullObjectBuilder;
import optional4j.codegen.builder.OptionalTypeBuilder;
import optional4j.codegen.visitor.collaborator.CollaboratorVisitor;
import optional4j.spec.Optional;
import spoon.compiler.Environment;
import spoon.processing.AnnotationProcessor;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.CtAbstractVisitor;

@RequiredArgsConstructor
public class Optional4JVisitor extends CtAbstractVisitor {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private final Class<? extends AnnotationProcessor<?, ?>> processorClass;

    private final Environment environment;

    private final OptionalTypeBuilder optionalTypeBuilder;

    private final CodegenProperties codegenProperties;

    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {

        if (localVariable.hasAnnotation(NullAssert.class)) {
            return;
        }

        printProcessing(environment, "Processing var: " + localVariable.getSimpleName());

        CtExpression<T> assignment = localVariable.getAssignment();

        if (assignment instanceof CtInvocation) {

            CtInvocation<T> invocation = (CtInvocation<T>) assignment;

            String previousVar = doVisitCtInvocation(localVariable, invocation);

            if (invocation.getTarget() != null && invocation.getTarget() instanceof CtThisAccess) {
                CtConditional conditional =
                        createNullConditional(previousVar, getNullSafeValue(localVariable));
                localVariable.setAssignment(conditional);
            } else if (!"".equalsIgnoreCase(previousVar)) {
                CtLocalVariable var = createLocalVar(previousVar, invocation);
                CtConditional<T> conditional =
                        createNullConditional(
                                invocation, previousVar, getNullSafeValue(localVariable));
                var.setSimpleName(localVariable.getSimpleName());
                var.setAssignment(conditional);
                localVariable.replace(var);
            }
            removeAnnotation(localVariable, optionalTypeBuilder.getFactory(), NullSafe.class);
            removeAnnotation(localVariable, optionalTypeBuilder.getFactory(), Optional4J.class);
        }
    }

    private <T> String getNullSafeValue(CtLocalVariable<T> localVariable) {
        if (!localVariable.hasAnnotation(NullSafe.class)) {
            return "null";
        }
        return localVariable.getAnnotation(NullSafe.class).value();
    }

    public <T> String doVisitCtInvocation(
            CtLocalVariable<T> localVariable, CtInvocation<T> invocation) {

        printProcessing(environment, "Processing var invocation: " + invocation.toString());

        CtExpression<?> target = invocation.getTarget();

        printProcessing(environment, "Processing target: " + target.getClass());

        if (target instanceof CtInvocation) {

            CtInvocation<T> targetInvocation = (CtInvocation<T>) target;
            String previousVar = doVisitCtInvocation(localVariable, targetInvocation);

            CtLocalVariable var = createLocalVar(previousVar, target);

            if (!"".equalsIgnoreCase(previousVar)) {
                CtConditional<T> conditional =
                        createNullConditional(targetInvocation, previousVar, null);
                var.setAssignment(conditional);
            }
            localVariable.insertBefore(var);
            return var.getSimpleName();
        } else if (target instanceof CtThisAccess) {
            CtLocalVariable var = createLocalVar("", invocation);
            var.setAssignment(invocation);
            localVariable.insertBefore(var);
            return var.getSimpleName();
        } else if (target instanceof CtTypeAccess) {
            return "";
        } else if (target != null) {

            // new Customer().getAddressPlain()
            // target // new Customer()
            CtLocalVariable<?> var = createLocalVar("", target);
            localVariable.insertBefore(var);
            return var.getSimpleName();
        } else {
            printProcessing(environment, "target is null for: " + invocation);
            return "";
        }
    }

    private <T> CtConditional<T> createNullConditional(
            CtInvocation<T> targetInvocation, String previousVar, String nullSafeValue) {
        CtConditional<T> conditional = optionalTypeBuilder.createConditional();
        conditional.setCondition(
                optionalTypeBuilder.createCodeSnippetExpression(previousVar + " == null"));

        if (nullSafeValue == null || nullSafeValue.isEmpty()) {
            conditional.setThenExpression(optionalTypeBuilder.createCodeSnippetExpression("null"));
        } else {
            conditional.setThenExpression(
                    optionalTypeBuilder.createCodeSnippetExpression(nullSafeValue));
        }

        CtInvocation<T> targetInvocationClone = targetInvocation.clone();
        targetInvocationClone.setTarget(
                optionalTypeBuilder.createCodeSnippetExpression(previousVar));
        conditional.setElseExpression(targetInvocationClone);
        return conditional;
    }

    private <T> CtConditional<T> createNullConditional(String previousVar, String nullSafeValue) {
        CtConditional<T> conditional = optionalTypeBuilder.createConditional();
        conditional.setCondition(
                optionalTypeBuilder.createCodeSnippetExpression(previousVar + " == null"));

        if (nullSafeValue == null || nullSafeValue.isEmpty()) {
            conditional.setThenExpression(optionalTypeBuilder.createCodeSnippetExpression("null"));
        } else {
            conditional.setThenExpression(
                    optionalTypeBuilder.createCodeSnippetExpression(nullSafeValue));
        }

        conditional.setElseExpression(optionalTypeBuilder.createCodeSnippetExpression(previousVar));
        return conditional;
    }

    private <T, V> CtLocalVariable<V> createLocalVar(String previousVar, CtExpression<V> target) {

        CtLocalVariable<V> localVariable = optionalTypeBuilder.createLocalVariable();

        String varName;
        if (!previousVar.isEmpty() && !previousVar.equals("_")) {
            varName = previousVar.concat("_").concat(targetToVarName(target));
        } else {
            varName = targetToVarName(target);
        }

        localVariable.setSimpleName(varName);
        localVariable.setType(target.getType());
        localVariable.setAssignment(target);
        return localVariable;
    }

    private <V> String targetToVarName(CtExpression<V> target) {
        return target.toString()
                .substring(target.toString().lastIndexOf(".") + 1)
                .replace(" ", "")
                .replace("(", "")
                .replace(")", "");
    }

    @Override
    public <T> void visitCtInterface(CtInterface<T> tCtInterface) {

        if (tCtInterface.hasAnnotation(Collaborator.class)) {
            return;
        }

        Optional4J valueType = tCtInterface.getAnnotation((Optional4J.class));
        if (valueType == null) {
            return;
        }

        printProcessing(environment, tCtInterface);

        implementSomething(tCtInterface);
        // visitJsr305Methods(tCtInterface);
        addGeneratedAnnotations(tCtInterface);
    }

    private <T> void visitJsr305Methods(CtType<T> ctType) {

        if (!codegenProperties.isNullityEnabled()) {
            return;
        }

        if (NULLABLE == getNullness(ctType, codegenProperties)) {
            visitCtMethods(ctType.getMethods());
            return;
        }

        visitCtMethods(getNullableMethods(ctType));
    }

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {

        if (ctClass.hasAnnotation(Collaborator.class)) {
            return;
        }

        Optional4J valueType = ctClass.getAnnotation((Optional4J.class));
        if (valueType == null) {
            return;
        }

        printProcessing(environment, ctClass);

        if (codegenProperties.isEnhancedSyntax()) {
            implementEnhancedOptionalType(ctClass);
        }
        implementSomething(ctClass);
        // visitJsr305Methods(ctClass);
        addGeneratedAnnotations(ctClass);
    }

    public void visitCtMethods(Set<CtMethod<?>> methods) {
        methods.forEach(this::visitCtMethod);
    }

    @Override
    public <T> void visitCtMethod(CtMethod<T> ctMethod) {

        printProcessing(environment, ctMethod);

        if (isVoidReturn(ctMethod, optionalTypeBuilder.getFactory())) {
            return;
        }

        if (returnsNullObjectType(ctMethod)) {
            ctMethod.accept(
                    new CollaboratorVisitor(
                            processorClass,
                            environment,
                            new NullObjectBuilder(optionalTypeBuilder.getFactory()),
                            optionalTypeBuilder,
                            codegenProperties));
            return;
        }

        if (ctMethod.getType().getQualifiedName().startsWith(Optional.class.getName())) {
            removeAnnotation(ctMethod, optionalTypeBuilder.getFactory(), Optional4J.class);
            removeAnnotation(ctMethod, optionalTypeBuilder.getFactory(), Nullable.class);
            return;
        }

        if (!isOptionalReturn(ctMethod)) { // @Optional4J

            if (!codegenProperties.isNullityEnabled()) {
                return;
            }

            if (hasNonNullAnnotation(ctMethod)) { // @NonNull
                return;
            }
        }

        if (!isValueType(ctMethod, optionalTypeBuilder.getFactory())) {
            if (isOptimisticMode(ctMethod, codegenProperties)) {
                return;
            }
        }

        removeAnnotation(ctMethod, optionalTypeBuilder.getFactory(), Optional4J.class);
        removeAnnotation(ctMethod, optionalTypeBuilder.getFactory(), Nullable.class);

        OptionalMethodWrapper wrapper =
                new OptionalMethodWrapper(optionalTypeBuilder, codegenProperties);
        if (ctMethod.getBody() == null) {
            wrapper.changeMethodReturnTypeToOptional4J(ctMethod);
            addNonNullAnnotation(ctMethod, optionalTypeBuilder.getFactory());
            return;
        }
        ctMethod.getDeclaringType().addMethod(wrapper.wrapMethod(ctMethod));
    }

    private <T> void addGeneratedAnnotations(CtType<T> tCtType) {
        addGeneratedAnnotation(tCtType, optionalTypeBuilder.getFactory(), processorClass);
        removeAnnotation(tCtType, optionalTypeBuilder.getFactory(), Nullable.class);
        removeAnnotation(tCtType, optionalTypeBuilder.getFactory(), NonNull.class);
        removeAnnotation(tCtType, optionalTypeBuilder.getFactory(), Config.class);
        removeAnnotation(tCtType, optionalTypeBuilder.getFactory(), Optional4J.class);
    }

    private <T> void implementEnhancedOptionalType(CtClass<T> ctClass) {
        CtType<?> nType = optionalTypeBuilder.createEnhancedOptionalType(ctClass);
        ctClass.addSuperInterface(nType.getReference());
    }

    private void implementSomething(CtType<?> ctType) {
        optionalTypeBuilder.implementPresent(ctType);
    }
}
