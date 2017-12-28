package com.incture.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A Custom Annotation to inject additional information into a TestNG Test
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestInfo {
    
    /**
     * Service.
     *
     * @return the string
     */
	String TestCaseId();
	
    String TestCaseName();
        
}
