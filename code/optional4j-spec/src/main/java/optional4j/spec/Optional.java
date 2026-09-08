package optional4j.spec;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.NonNull;
import javax.annotation.Nullable;

public interface Optional<T> {

    @NonNull
    static <T> Optional<T> ofNullable(@Nullable T value) {
        if (value == null) {
            return Absent.nothing();
        }
        if (value instanceof Optional) {
            return ofNullable((Optional<T>) value);
        }
        return Present.of(value);
    }

    @NonNull
    static <T> Optional<T> ofNullable(@Nullable Optional<T> value) {
        return value != null ? value : empty();
    }

    @NonNull
    static <T> Optional<T> of(@NonNull T value) {
        return Present.of(value);
    }

    @NonNull
    static <T> Optional<T> empty() {
        return Absent.nothing();
    }

    @NonNull
    java.util.Optional<T> toJavaUtil();

    static <T> Optional<T> fromJavaUtil(java.util.Optional<T> optionalT) {
        if (optionalT.isPresent()) {
            T t = optionalT.get();
            if (t instanceof Present) {
                return (Present<T>) t;
            }
            return Optional.of(t);
        } else {
            return empty();
        }
    }

    boolean isEmpty();

    boolean isPresent();

    @Nullable
    <R> R ifPresentOrElseGet(@NonNull Function<? super T, R> ifPresent, Supplier<R> orElse);

    @Nullable
    <R> R ifPresentOrElse(@NonNull Function<? super T, R> ifPresent, R orElse);

    void ifPresent(@NonNull Consumer<T> ifPresent);

    @Nullable
    T orElseGet(@NonNull Supplier<T> orElse);

    @Nullable
    T orElse(@Nullable T orElse);

    @Nullable
    <R> R ifNullOrElse(Supplier<R> ifNull, @NonNull Supplier<R> orElse);

    void ifNull(@NonNull Runnable ifNull);

    @Nullable
    T orNull();

    @NonNull
    <R> Optional<R> flatMap(@NonNull Function<? super T, Optional<R>> mapper);

    <R extends NullableObject> R map(
            @NonNull Function<? super T, R> mapper, @NonNull Supplier<R> ifEmpty);

    @NonNull
    <U> Optional<U> map(@NonNull Function<? super T, ? extends U> mapper);

    @NonNull
    Optional<T> or(@NonNull Supplier<? extends Optional<? extends T>> supplier);

    @NonNull
    T orElseThrow();

    @NonNull
    <X extends Throwable> T orElseThrow(@NonNull Supplier<? extends X> exceptionSupplier) throws X;

    boolean isNull();

    @NonNull
    T get();
}
