package com.jacksanger.mhassistant.security.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;

/*
 * Class used for validating two fields as the same in support of the FieldMatch validation
 */

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
   private String firstFieldName;
   private String secondFieldName;

   //Initializes the class
   @Override
   public void initialize(final FieldMatch constraintAnnotation) {
       firstFieldName = constraintAnnotation.first();
       secondFieldName = constraintAnnotation.second();
   }

   //Compares the two fields in the annotation and returns if they are equal as a boolean
   @Override
   public boolean isValid(final Object value, final ConstraintValidatorContext context) {
       try {
           final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
           final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
           return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
       } catch (final Exception ignore) {}
       return true;
   }
}