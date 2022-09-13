## Optional4J

Welcome to __Optional4J__, a Java tool that automates null-safety of method invocations using code generation and
transformation via declarative annotations and annotation processors. To promote simplicity, optional4j is predominantly
driven by a single annotation i.e., `@Optional4J`. However, it further provides other annotations to allow developers to
customize transformed code.

### How it works:

Optional4J provides two main mechanisms to mitigate the tediousness and forgetfulness of null checks of at Java methods,
namely: __method optionalization__ and __null-safe method invocation__. The former transforms the source code of method
declarations to generate null-safe ones. The transformed methods later force developers to perform the necessary null
checks at build time. On the other hand, The __null-safe method invocation__ mechanism allows developers to invoke
null-unsafe methods in a null-safe manner, especially if they have no access to the source code of the respective
methods they are calling. Both techniques employ code generation and transformation via a single `@Optional4J`
annotation and annotation processors build using Java Spoon.

### Dependencies:
For maven-based projects, add the following dependency:

```xml

<dependency>
    <groupId>com.petra.optional4j</groupId>
    <artifactId>jsr-305</artifactId>
    <version>${project.version}</version>
</dependency>

```
Then add the following maven plugin:
```xml
<plugin>
    <groupId>fr.inria.gforge.spoon</groupId>
    <artifactId>spoon-maven-plugin</artifactId>
    <version>${java.spoon.version}</version>
    <dependencies>
        <dependency>
            <groupId>com.petra.optional4j</groupId>
            <artifactId>optional4j-codegen</artifactId>
            <version>${optional4j.version}</version>
        </dependency>
    </dependencies>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <outputType>classes</outputType>
        <withImports>true</withImports>
        <noClasspath>true</noClasspath>
        <processors>
            <processor>optional4j.codegen.processor.Optional4JAnnotationProcessor</processor>
            <processor>optional4j.codegen.processor.CollaboratorAnnotationProcessor</processor>
            <processor>optional4j.codegen.processor.NullableAnnotationProcessor</processor>
            <processor>optional4j.codegen.processor.NullAssertAnnotationProcessor</processor>
            <processor>optional4j.codegen.processor.NullSafeAnnotationProcessor</processor>
        </processors>
    </configuration>
</plugin>

```

### Usage:

Here we provide sample examples on how to use Optional4J at either method declarations or invocations:

#### 1- Method Optionalization

In order to make a method null-safe for callers, simply annotate its declaration with `@Optional4J` as shown below:

```java
public class CustomerService {

    @Optional4J // annotation provided by developer
    public Address getAddress(String id) {
        // logic to retrieve address
        return address;
    }
}
```

At build time, the logic above will be automatically transformed to become as shown below.

```java

@Generated
public class CustomerService {

    @NonNull
    public Optional<Address> getAddress(String id) {
        // logic to retrieve address
        return Optional.ofNullable(address);
    }
}
```

Note that the return type becomes `Optional<Address>` which is created using the static method `Optional.ofNullable`.
Also, note that the syntax is exactly the same as that of the JDK Optional wrapper class. Finally, the generated method
is automatically marked as `@NonNull`. That is because the newly returned `Optional<Address>` is guaranteed not to be
null. Such auto-generated annotation can provide insight to other static analysis tools.

In order to benefit from the performance improvements of Optional4J. If the developer has access to the source code of
the `Address` class, they should also annotate it with `@Optional4J` as shown below:

```java

@Optional4J // annotation provided by developer
public class Address {

    // attributes, getters, setters...etc.
}
```

The newly generated `Address` class will be transformed to implement the `Present` interface which, in turn, extends
the `Optional` interface. Here is how Optional4J transforms the `Address` class at build time.

```java

@Generated
public class Address implements Present<Address> {

    // attributes, getters, setters...etc.
}
```

Such transformation, while simple, is powerful because it allows Optional4J to avoid creating a runtime wrapper instance
and instead perform a mere null check whenever the `Optional.ofNullable` static factory method is invoked on an address
instance.

#### 2- Null-safe Method Invocation
