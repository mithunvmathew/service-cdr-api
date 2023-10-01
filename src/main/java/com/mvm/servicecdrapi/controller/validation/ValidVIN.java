package com.mvm.servicecdrapi.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = VinValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVIN {
    String message() default "Invalid VIN number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
