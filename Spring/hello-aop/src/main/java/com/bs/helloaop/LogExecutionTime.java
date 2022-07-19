package com.bs.helloaop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD) //어노테이션을 메소드에 적용
@Retention(RetentionPolicy.RUNTIME) // 해당 어노테이션 언제까지 유지
public @interface LogExecutionTime {
}
