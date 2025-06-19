package com.wallpaper.wallpaper_api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PathOrUrlValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathOrUrl {
    String message() default "Either url or path must be provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
