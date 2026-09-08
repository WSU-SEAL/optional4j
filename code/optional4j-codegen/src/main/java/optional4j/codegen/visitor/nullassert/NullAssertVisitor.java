package optional4j.codegen.visitor.nullassert;

import static optional4j.codegen.CodegenUtil.printProcessing;
import static optional4j.codegen.CodegenUtil.removeAnnotation;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import optional4j.annotation.NullAssert;
import optional4j.annotation.Optional4J;
import optional4j.codegen.CodegenProperties;
import optional4j.codegen.builder.NullObjectBuilder;
import optional4j.codegen.builder.OptionalTypeBuilder;
import spoon.compiler.Environment;
import spoon.processing.AnnotationProcessor;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtThisAccess;
import spoon.reflect.code.CtTypeAccess;
import spoon.reflect.visitor.CtAbstractVisitor;

@RequiredArgsConstructor
public class NullAssertVisitor extends CtAbstractVisitor {

    public static final Random RANDOM = new Random(System.currentTimeMillis());

    private final Class<? extends AnnotationProcessor<?, ?>> processorClass;

    private final Environment environment;

    private final NullObjectBuilder nullObjectBuilder;

    private final OptionalTypeBuilder optionalTypeBuilder;

    private final CodegenProperties codegenProperties;

    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> localVariable) {

        printProcessing(environment, "Processing var: " + localVariable.getSimpleName());

        CtExpression<T> assignment = localVariable.getAssignment();

        if (assignment instanceof CtInvocation) {

            CtInvocation<T> invocation = (CtInvocation<T>) assignment;

            CtLocalVariable previousVar = doVisitCtInvocation(localVariable, invocation);

            if (previousVar != null) {
                CtLocalVariable var = createLocalVar(previousVar.getSimpleName(), invocation);

                CtExpression nullAssertedInvocation =
                        createNullAssertedInvocation(invocation, previousVar);
                var.setSimpleName(localVariable.getSimpleName());
                var.setAssignment(nullAssertedInvocation);
                localVariable.replace(var);
            }
            removeAnnotation(localVariable, optionalTypeBuilder.getFactory(), Optional4J.class);
            removeAnnotation(localVariable, optionalTypeBuilder.getFactory(), NullAssert.class);
        }
    }

    public <T> CtLocalVariable doVisitCtInvocation(
            CtLocalVariable<T> localVariable, CtInvocation<T> invocation) {

        printProcessing(environment, "Processing var invocation: " + invocation.toString());

        CtExpression<?> target = invocation.getTarget();

        //        if (target instanceof CtTypeAccess || target instanceof CtThisAccess) {
        //            return "";
        //        }

        if (target instanceof CtInvocation) {

            CtInvocation<T> targetInvocation = (CtInvocation<T>) target;
            CtLocalVariable<T> previousVar = doVisitCtInvocation(localVariable, targetInvocation);
            CtLocalVariable<?> var;
            if (previousVar != null) {
                var = createLocalVar(previousVar.getSimpleName(), target);
            } else {
                var = createLocalVar("", target);
            }
            if (previousVar != null) {
                CtExpression nullAssertedInvocation =
                        createNullAssertedInvocation(targetInvocation, previousVar);
                var.setAssignment(nullAssertedInvocation);
            }

            localVariable.insertBefore(var);
            localVariable.insertBefore(nullAssert(var));

            return var;
        } else if (target instanceof CtThisAccess) {
            return null;
        } else if (target instanceof CtTypeAccess) {
            return null;
        } else if (target != null) {

            // new Customer().getAddressPlain()
            // target // new Customer()
            CtLocalVariable<?> var = createLocalVar("_", target);
            localVariable.insertBefore(var);
            localVariable.insertBefore(nullAssert(var));
            return var;
        } else {
            return null;
        }
    }

    private <T> CtStatement nullAssert(CtLocalVariable<?> var) {
        String varName = var.getSimpleName();
        CtIf anIf = optionalTypeBuilder.createIf();
        anIf.setCondition(
                optionalTypeBuilder.createCodeSnippetExpression(varName.concat(" == null")));
        anIf.setThenStatement(
                optionalTypeBuilder.createCodeSnippetStatement(
                        "throw new NullPointerException(\"" + varName + " is null\")"));
        return anIf;
    }

    private <T> CtConditional<T> createNullConditional(
            CtInvocation<T> targetInvocation, String previousVar) {
        CtConditional<T> conditional = optionalTypeBuilder.createConditional();
        conditional.setCondition(
                optionalTypeBuilder.createCodeSnippetExpression(previousVar + " == null"));
        conditional.setThenExpression(optionalTypeBuilder.createCodeSnippetExpression("null"));

        CtInvocation<T> targetInvocationClone = targetInvocation.clone();
        targetInvocationClone.setTarget(
                optionalTypeBuilder.createCodeSnippetExpression(previousVar));
        conditional.setElseExpression(targetInvocationClone);
        return conditional;
    }

    private <T> CtExpression<T> createNullAssertedInvocation(
            CtInvocation<T> targetInvocation, CtLocalVariable<?> previousVar) {
        CtInvocation<T> targetInvocationClone = targetInvocation.clone();
        targetInvocationClone.setTarget(
                optionalTypeBuilder.createCodeSnippetExpression(previousVar.getSimpleName()));
        return targetInvocationClone;
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
}
