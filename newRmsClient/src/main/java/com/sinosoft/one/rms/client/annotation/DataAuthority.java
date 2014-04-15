package com.sinosoft.one.rms.client.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * data authority
 * User: ChengQi
 * Date: 3/21/12
 * Time: 4:35 PM
 */
@Target(value={ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface DataAuthority {
    String value() default ""; 
    String tabAlias() default "";
}
