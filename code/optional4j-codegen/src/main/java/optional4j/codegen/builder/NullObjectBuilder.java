package optional4j.codegen.builder;

import static java.util.stream.Collectors.toSet;
import static spoon.reflect.declaration.ModifierKind.FINAL;
import static spoon.reflect.declaration.ModifierKind.PRIVATE;
import static spoon.reflect.declaration.ModifierKind.PROTECTED;
import static spoon.reflect.declaration.ModifierKind.PUBLIC;
import static spoon.reflect.declaration.ModifierKind.STATIC;

import java.util.Set;
import javax.annotation.NonNull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import optional4j.spec.NullableObject;
import optional4j.spec.Optional;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;

@RequiredArgsConstructor
public class NullObjectBuilder {

    @Getter @Delegate private final Factory factory;

    // interface FooNullObject extends Optional<Foo> {
    // ... Foo method declarations
    // }
    public CtType<?> createNullObjectType(CtType<?> ctClass) {

        CtInterface<?> nullObjectType = declareNullObjectType(ctClass);
        nullObjectType.addSuperInterface(factory.createCtTypeReference(NullableObject.class));
        nullObjectType.setModifiers(
                ctClass.getModifiers().stream()
                        .filter(modifier -> Set.of(PUBLIC, PRIVATE, PROTECTED).contains(modifier))
                        .collect(toSet()));
        implementOptional(nullObjectType, ctClass);

        return nullObjectType;
    }

    // Reference to FooNullObject
    public CtTypeReference<?> getNullObjectTypeReference(CtType<?> ctType) {
        return getFactory().createReference(getNullObjectTypeQualifiedName(ctType));
    }

    // Reference to FooNull
    public CtTypeReference<?> getNullClassReference(CtClass<?> ctClass) {
        return getNullClassReference(getNullClassQualifiedName(ctClass));
    }

    private CtTypeReference<?> getNullClassReference(String qualifiedName) {
        return getFactory().createReference(qualifiedName);
    }

    // class NullFoo implements FooNullObject{
    // }
    public CtClass<?> createNullClass(CtClass<?> ctClass, CtType<?> nullObjectType) {
        CtClass nullClass = declareNullClass(ctClass);
        nullClass.addField(
                getFactory()
                        .createCtField(
                                "NULL",
                                nullClass.getReference(),
                                "new " + nullClass.getSimpleName() + "()",
                                STATIC,
                                FINAL));
        implementsNullObjectInterface(nullClass, nullObjectType);
        return nullClass;
    }

    /**
     * @param nullObjectType Generates method ==> @NonNull FooNullObject ofNullObject(@Nullable
     *     FooNullObject optional4j.nullObject) { return optional4j.nullObject != null?
     *     optional4j.nullObject: FooNull.nullInstance(); }
     */
    public void addOfNullObjectFactoryMethod(CtType<?> nullObjectType) {

        CtMethod ofNullObjectMethod = createMethod();
        ofNullObjectMethod.setType(nullObjectType.getReference());

        ofNullObjectMethod.setSimpleName("ofNullable");
        ofNullObjectMethod.setModifiers(Set.of(PUBLIC, STATIC));
        ofNullObjectMethod.addAnnotation(createAnnotation(createCtTypeReference(NonNull.class)));

        CtParameter nullObject = createParameter();
        nullObject.addAnnotation(createAnnotation(createCtTypeReference(Nullable.class)));
        nullObject.setType(nullObjectType.getReference());

        String nullObjectParamName = "nullable";
        nullObject.setSimpleName(nullObjectParamName);

        ofNullObjectMethod.addParameter(nullObject);

        ofNullObjectMethod.setBody(
                createReturn()
                        .setReturnedExpression(
                                createCodeSnippetExpression(
                                        nullObjectParamName
                                                + " != null? "
                                                + nullObjectParamName
                                                + ": "
                                                + "nullInstance"
                                                + "()")));

        nullObjectType.addMethod(ofNullObjectMethod);
    }

    /**
     * @param nullObjectType
     * @param ctClass Generates method ==> @NonNull public static FooNullObject nullInstance() {
     *     return FooNull.NULL; }
     */
    public void addNullInstanceFactoryMethod(CtType<?> nullObjectType, CtClass<?> ctClass) {

        CtMethod nullInstanceMethod = createMethod();
        nullInstanceMethod.setType(nullObjectType.getReference());

        nullInstanceMethod.setSimpleName("nullInstance");
        nullInstanceMethod.setModifiers(Set.of(PUBLIC, STATIC));
        nullInstanceMethod.addAnnotation(createAnnotation(createCtTypeReference(NonNull.class)));

        nullInstanceMethod.setBody(
                createReturn().setReturnedExpression(nullStaticInstanceAccess(ctClass)));

        nullObjectType.addMethod(nullInstanceMethod);
    }

    private CtFieldRead nullStaticInstanceAccess(CtClass<?> ctClass) {
        return createFieldRead()
                .setVariable(
                        createFieldReference()
                                .setDeclaringType(getNullClassReference(ctClass))
                                .setStatic(true)
                                .setSimpleName("NULL"));
    }

    /**
     * interface FooNullObject {}
     *
     * @param ctType
     * @return
     */
    private CtInterface<?> declareNullObjectType(CtType<?> ctType) {
        return getFactory().createInterface(getNullObjectTypeQualifiedName(ctType));
    }

    /**
     * class FooNull {}
     *
     * @param ctClass
     * @return
     */
    private CtClass<?> declareNullClass(CtClass<?> ctClass) {
        return getFactory().createClass(getNullClassQualifiedName(ctClass)).addModifier(PUBLIC);
    }

    /**
     * FooNull
     *
     * @param ctClass
     * @return
     */
    private String getNullClassQualifiedName(CtClass<?> ctClass) {
        return ctClass.getPackage().getQualifiedName() + "." + "Null" + ctClass.getSimpleName();
    }

    /**
     * FooNullObject
     *
     * @param ctType
     * @return
     */
    private String getNullObjectTypeQualifiedName(CtType<?> ctType) {
        return ctType.getPackage().getQualifiedName() + "." + "Abstract" + ctType.getSimpleName();
    }

    private void implementsNullObjectInterface(CtClass<?> ctClass, CtType<?> nullObjectType) {
        ctClass.addSuperInterface(nullObjectType.getReference());
    }

    private void implementOptional(CtType<?> ctType, CtType<?> argumentCtType) {
        CtTypeReference<Optional<?>> optional = getFactory().createCtTypeReference(Optional.class);
        optional.addActualTypeArgument(argumentCtType.getReference());
        ctType.addSuperInterface(optional);
    }
}
