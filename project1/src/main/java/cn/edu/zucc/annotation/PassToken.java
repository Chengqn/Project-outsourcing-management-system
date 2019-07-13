package cn.edu.zucc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
public @interface PassToken {
    boolean required() default  true;
}
