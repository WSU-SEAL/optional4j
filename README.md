# Optional4J

This is a documentation of the Optional4J Java tool.

### Usage:
#### 1- Method Optionalization
In order to optionalize a method (i.e., automatically transform it to return an `Optional` type), simply annotate it
with `@Optional4J` as shown below:

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
