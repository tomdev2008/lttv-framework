package com.letv.mobile.core.rpc.rest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.letv.javax.ws.rs.CookieParam;
import com.letv.javax.ws.rs.HeaderParam;
import com.letv.javax.ws.rs.MatrixParam;
import com.letv.javax.ws.rs.PathParam;
import com.letv.javax.ws.rs.QueryParam;
import com.letv.javax.ws.rs.core.Context;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
@SuppressWarnings("unchecked")
public final class FindAnnotation
{

   /**
    *
    */
   private static final Class<? extends Annotation>[] JAXRS_ANNOTATIONS =
           (Class<? extends Annotation>[]) new Class[]{
                   QueryParam.class,
                   HeaderParam.class,
                   CookieParam.class,
                   PathParam.class,
                   MatrixParam.class,
                   Context.class
           };

   private static final Class[] findJaxRSAnnotations_TYPE = new Class[]{};


   private FindAnnotation()
   {
   }

   /**
    * FIXME Comment this
    *
    * @param <T>
    * @param searchList
    * @param annotation
    * @return
    */
   public static <T> T findAnnotation(Annotation[] searchList, Class<T> annotation)
   {
      if (searchList == null) return null;
      for (Annotation ann : searchList)
      {
         if (ann.annotationType().equals(annotation))
         {
            return (T) ann;
         }
      }
      return null;
   }


   public static Class<? extends Annotation>[] findJaxRSAnnotations(Annotation[] searchList)
   {

      LinkedList<Class<? extends Annotation>> result = new LinkedList<Class<? extends Annotation>>();

      for (Class<? extends Annotation> clazz : JAXRS_ANNOTATIONS)
      {

         if (findAnnotation(searchList, clazz) != null)
            result.add(clazz);

      }

      return result.toArray(findJaxRSAnnotations_TYPE);

   }

   /**
    * Returns an array of annotations the specified method of
    * a resource class.
    *
    * @param method
    * @return
    */
   public static Annotation[] getResourcesAnnotations(Method method)
   {
      Map<Class<?>, Annotation> annotations = new HashMap<Class<?>, Annotation>();
      for (Annotation annotation : method.getDeclaringClass().getAnnotations())
      {
         annotations.put(annotation.getClass(), annotation);
      }
      for (Annotation annotation : method.getAnnotations())
      {
         annotations.put(annotation.getClass(), annotation);
      }
      return annotations.values().toArray(new Annotation[annotations.size()]);
   }

   /**
    * Look for an annotation in a list of annotations.  If not there, see if it is on the type provided
    *
    * @param type
    * @param annotations
    * @param annnotation
    * @return
    */
   public static <T extends Annotation> T findAnnotation(Class<?> type, Annotation[] annotations, Class<T> annotation)
   {
      T config = FindAnnotation.findAnnotation(annotations, annotation);
      if (config == null)
      {
         config = type.getAnnotation(annotation);
      }
      return config;
   }


}
