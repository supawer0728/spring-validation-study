package org.parfait.study.springvalidation.vaidation.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.parfait.study.springvalidation.vaidation.PhoneNumber;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final Pattern pattern = Pattern.compile("^01\\d-\\d{3,4}-\\d{4}");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        return pattern.matcher(value).matches();
    }
}
