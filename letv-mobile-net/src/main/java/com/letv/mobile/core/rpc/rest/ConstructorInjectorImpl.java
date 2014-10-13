package com.letv.mobile.core.rpc.rest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import com.letv.javax.ws.rs.InternalServerErrorException;
import com.letv.javax.ws.rs.WebApplicationException;
import com.letv.mobile.core.rpc.rest.spi.ApplicationException;
import com.letv.mobile.core.rpc.rest.spi.ConstructorInjector;
import com.letv.mobile.core.rpc.rest.spi.Failure;
import com.letv.mobile.core.rpc.rest.spi.HttpRequest;
import com.letv.mobile.core.rpc.rest.spi.HttpResponse;
import com.letv.mobile.core.rpc.rest.spi.ValueInjector;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
public class ConstructorInjectorImpl implements ConstructorInjector
{
   protected Constructor constructor;
   protected ValueInjector[] params;

//   public ConstructorInjectorImpl(ResourceConstructor constructor, ResteasyProviderFactory factory)
//   {
//      this.constructor = constructor.getConstructor();
//      params = new ValueInjector[constructor.getParams().length];
//      int i = 0;
//      for (ConstructorParameter parameter : constructor.getParams())
//      {
//         params[i++] = factory.getInjectorFactory().createParameterExtractor(parameter, factory);
//      }
//
//   }


   public ConstructorInjectorImpl(Constructor constructor, ResteasyProviderFactory factory)
   {
      this.constructor = constructor;
      params = new ValueInjector[constructor.getParameterTypes().length];
      for (int i = 0; i < constructor.getParameterTypes().length; i++)
      {
         Class type = constructor.getParameterTypes()[i];
         Type genericType = constructor.getGenericParameterTypes()[i];
         Annotation[] annotations = constructor.getParameterAnnotations()[i];
         params[i] = factory.getInjectorFactory().createParameterExtractor(constructor.getDeclaringClass(), constructor, type, genericType, annotations, factory);
      }
   }

   public Object[] injectableArguments(HttpRequest input, HttpResponse response)
   {
      Object[] args = null;
      if (params != null && params.length > 0)
      {
         args = new Object[params.length];
         int i = 0;
         for (ValueInjector extractor : params)
         {
            args[i++] = extractor.inject(input, response);
         }
      }
      return args;
   }

   public Object[] injectableArguments()
   {
      Object[] args = null;
      if (params != null && params.length > 0)
      {
         args = new Object[params.length];
         int i = 0;
         for (ValueInjector extractor : params)
         {
            args[i++] = extractor.inject();
         }
      }
      return args;
   }

   public Object construct(HttpRequest request, HttpResponse httpResponse) throws Failure, ApplicationException, WebApplicationException
   {
      Object[] args = null;
      try
      {
         args = injectableArguments(request, httpResponse);
      }
      catch (Exception e)
      {
         throw new InternalServerErrorException("Failed processing arguments of " + constructor.toString(), e);
      }
      try
      {
         return constructor.newInstance(args);
      }
      catch (InstantiationException e)
      {
         throw new InternalServerErrorException("Failed to construct " + constructor.toString(), e);
      }
      catch (IllegalAccessException e)
      {
         throw new InternalServerErrorException("Failed to construct " + constructor.toString(), e);
      }
      catch (InvocationTargetException e)
      {
         Throwable cause = e.getCause();
         if (cause instanceof WebApplicationException)
         {
            throw (WebApplicationException) cause;
         }
         throw new ApplicationException("Failed to construct " + constructor.toString(), e.getCause());
      }
      catch (IllegalArgumentException e)
      {
         String msg = "Bad arguments passed to " + constructor.toString() + "  (";
         boolean first = false;
         for (Object arg : args)
         {
            if (!first)
            {
               first = true;
            }
            else
            {
               msg += ",";
            }
            if (arg == null)
            {
               msg += " null";
               continue;
            }
            msg += " " + arg;
         }
         throw new InternalServerErrorException(msg, e);
      }
   }

   public Object construct()
   {
      Object[] args = null;
      args = injectableArguments();
      try
      {
         return constructor.newInstance(args);
      }
      catch (InstantiationException e)
      {
         throw new RuntimeException("Failed to construct " + constructor.toString(), e);
      }
      catch (IllegalAccessException e)
      {
         throw new RuntimeException("Failed to construct " + constructor.toString(), e);
      }
      catch (InvocationTargetException e)
      {
         throw new RuntimeException("Failed to construct " + constructor.toString(), e.getCause());
      }
      catch (IllegalArgumentException e)
      {
         String msg = "Bad arguments passed to " + constructor.toString() + "  (";
         boolean first = false;
         for (Object arg : args)
         {
            if (!first)
            {
               first = true;
            }
            else
            {
               msg += ",";
            }
            if (arg == null)
            {
               msg += " null";
               continue;
            }
            msg += " " + arg;
         }
         throw new RuntimeException(msg, e);
      }
   }
}