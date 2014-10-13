package com.letv.mobile.core.rpc.util;

import java.util.regex.Pattern;

import com.letv.javax.ws.rs.core.UriInfo;
import com.letv.mobile.core.rpc.rest.ResteasyProviderFactory;

/**
 * Utility to replace predefined expressions within a string with  values from the HTTP request;
 * <p/>
 * ${basepath} - UriInfo.getBaseUri().getRawPath()
 * ${absolutepath} - UriInfo.getAbsolutePath().getRawPath()
 * ${absoluteuri} - UriInfo.getAbsolutePath().toString()
 * ${baseuri} - UriInfo.getBaseUri().toString()
 * ${contextpath} - HttpServletRequest.getContextPath()
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
public class StringContextReplacement
{
   private static final Pattern basepath = Pattern.compile("\\$\\{basepath\\}");
   private static final Pattern absolutepath = Pattern.compile("\\$\\{absolutepath\\}");
   private static final Pattern absoluteUri = Pattern.compile("\\$\\{absoluteuri\\}");
   private static final Pattern baseUri = Pattern.compile("\\$\\{baseuri\\}");
   private static final Pattern contextPath = Pattern.compile("\\$\\{contextpath\\}");

   /**
    * Utility to replace predefined expressions within a string with values from the HTTP request;
    * <p/>
    * ${basepath} - UriInfo.getBaseUri().getRawPath()
    * ${absolutepath} - UriInfo.getAbsolutePath().getRawPath()
    * ${absoluteuri} - UriInfo.getAbsolutePath().toString()
    * ${baseuri} - UriInfo.getBaseUri().toString()
    * ${contextpath} - HttpServletRequest.getContextPath()
    *
    * @param original
    * @return
    */
   public static String replace(String original)
   {
      UriInfo uriInfo = ResteasyProviderFactory.getContextData(UriInfo.class);
      if (uriInfo != null)
      {
         String base = uriInfo.getBaseUri().getRawPath();
         String abs = uriInfo.getAbsolutePath().getRawPath();
         String absU = uriInfo.getAbsolutePath().toString();
         String baseU = uriInfo.getBaseUri().toString();

         original = basepath.matcher(original).replaceAll(base);
         original = absolutepath.matcher(original).replaceAll(abs);
         original = absoluteUri.matcher(original).replaceAll(absU);
         original = baseUri.matcher(original).replaceAll(baseU);

      }
//      HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
//      if (request != null)
//      {
//         original = contextPath.matcher(original).replaceAll(request.getContextPath());
//
//      }
      return original;
   }

}
