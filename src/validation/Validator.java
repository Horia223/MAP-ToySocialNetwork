package validation;

import java.util.Objects;
@FunctionalInterface
public interface Validator<T> {
    void validate(T entity);



    default Validator<T> andThen(Validator<T> other) {
        Objects.requireNonNull(other, "Other validator cannot be null");

        return entity -> {
            this.validate(entity);
            other.validate(entity);
        };
    }
}
