package com.letv.mobile.core.rpc.rest;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.letv.javax.ws.rs.MatrixParam;
import com.letv.javax.ws.rs.core.PathSegment;
import com.letv.mobile.core.rpc.rest.spi.HttpRequest;
import com.letv.mobile.core.rpc.rest.spi.HttpResponse;
import com.letv.mobile.core.rpc.rest.spi.ValueInjector;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
public class MatrixParamInjector extends StringParameterInjector implements ValueInjector
{
   private boolean encode;
   
   public MatrixParamInjector(Class type, Type genericType, AccessibleObject target, String paramName, String defaultValue, boolean encode, Annotation[] annotations, ResteasyProviderFactory factory)
   {
      super(type, genericType, paramName, MatrixParam.class, defaultValue, target, annotations, factory);
      this.encode = encode;
   }

   public Object inject(HttpRequest request, HttpResponse response)
   {
      ArrayList<String> values = new ArrayList<String>();
      if (encode)
      {
         for (PathSegment segment : request.getUri().getPathSegments(false))
         {
            List<String> list = segment.getMatrixParameters().get(paramName);
            if (list != null) values.addAll(list);
         }
      }
      else
      {
         for (PathSegment segment : request.getUri().getPathSegments())
         {
            List<String> list = segment.getMatrixParameters().get(paramName);
            if (list != null) values.addAll(list);
         }
      }
      if (values.size() == 0) return extractValues(null);
      else return extractValues(values);
   }

   public Object inject()
   {
      throw new RuntimeException("It is illegal to inject a @MatrixParam into a singleton");
   }
}