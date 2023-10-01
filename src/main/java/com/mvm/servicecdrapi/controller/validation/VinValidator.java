package com.mvm.servicecdrapi.controller.validation;

import com.mvm.servicecdrapi.exception.InvalidVinException;
import de.kyrychenko.utils.vin.VinValidatorUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class VinValidator implements ConstraintValidator<ValidVIN, String> {

    @Override
    public void initialize(ValidVIN constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
            if (!VinValidatorUtils.isValidVin(value)) {
                throw new InvalidVinException("Invalid VehicleId");
            }
            return true;
    }
}
