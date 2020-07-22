package io.github.mirrormingzz.annotation_spel.annotation;

import java.lang.annotation.*;

/**
 * @author Mireal
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAnnotation {
    String value();
}
