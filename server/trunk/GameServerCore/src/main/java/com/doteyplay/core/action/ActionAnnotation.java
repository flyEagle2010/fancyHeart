package com.doteyplay.core.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.doteyplay.net.message.AbstractMessage;

/**
 * action×¢½âÀà
 * @author
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ActionAnnotation {
	Class<? extends AbstractMessage> message();
}
