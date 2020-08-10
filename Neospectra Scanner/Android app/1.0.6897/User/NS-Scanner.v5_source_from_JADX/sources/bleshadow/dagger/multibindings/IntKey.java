package bleshadow.dagger.multibindings;

import bleshadow.dagger.MapKey;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@MapKey
@Retention(RetentionPolicy.RUNTIME)
public @interface IntKey {
    int value();
}
