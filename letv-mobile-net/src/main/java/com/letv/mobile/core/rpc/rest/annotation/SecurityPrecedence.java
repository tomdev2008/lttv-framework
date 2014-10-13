package com.letv.mobile.core.rpc.rest.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Should be placed on a PreProcessInterceptor.
 * This annotation specifies ordering of interceptors.
 * Security-based interceptors should always come first.  They may look at headers, but they don't read the input
 * stream.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Precedence("SECURITY")
public @interface SecurityPrecedence
{
   public static final String PRECEDENCE_STRING = "SECURITY";
}
