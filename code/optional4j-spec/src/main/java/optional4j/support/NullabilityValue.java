package optional4j.support;

public enum NullabilityValue {

    // By default, all references are assumed NonNull unless annotated with @Nullable
    NON_NULL,

    // By default, all references are assumed Nullable unless annotated with @NonNull
    NULLABLE
}
