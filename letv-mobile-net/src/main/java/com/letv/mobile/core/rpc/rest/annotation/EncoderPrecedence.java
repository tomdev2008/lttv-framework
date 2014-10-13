package com.letv.mobile.core.rpc.rest.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This interceptor is an Content-Encoding encoder.  It is used with MessageBodyWriter interceptors.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Precedence("ENCODER")
public @interface EncoderPrecedence
{
   public static final String PRECEDENCE_STRING = "ENCODER";
}
