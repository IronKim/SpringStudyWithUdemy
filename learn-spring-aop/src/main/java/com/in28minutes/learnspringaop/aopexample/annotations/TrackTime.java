package com.in28minutes.learnspringaop.aopexample.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 메소드를 대상으로 
@Target(ElementType.METHOD)
// 런타임에 사용
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackTime {

}
