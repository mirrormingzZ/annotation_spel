package io.github.mirrormingzz.annotation_spel.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;


/**
 * @author Mireal
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
public @interface MyAnnotation {
    String value() default "";
}

