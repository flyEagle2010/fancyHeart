/**
 * 
 */
package com.doteyplay.game;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该声明主要是将配置信息的常量打印出来
 * 
 * 
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldAnnotation {
	String name();
}
