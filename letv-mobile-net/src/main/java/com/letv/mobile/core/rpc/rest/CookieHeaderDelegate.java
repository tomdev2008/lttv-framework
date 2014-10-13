package com.letv.mobile.core.rpc.rest;
import com.letv.javax.ws.rs.core.Cookie;
import com.letv.javax.ws.rs.ext.RuntimeDelegate;
/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
public class CookieHeaderDelegate implements RuntimeDelegate.HeaderDelegate
{


   public Object fromString(String value) throws IllegalArgumentException
   {
      if (value == null) throw new IllegalArgumentException("Cookie header value was null");
      try
      {
         int version = 0;
         String domain = null;
         String path = null;
         String cookieName = null;
         String cookieValue = null;

         String parts[] = value.split("[;,]");

         for (String part : parts)
         {
            String nv[] = part.split("=", 2);
            String name = nv.length > 0 ? nv[0].trim() : "";
            String value1 = nv.length > 1 ? nv[1].trim() : "";
            if (value1.startsWith("\"") && value1.endsWith("\"") && value1.length() > 1)
               value1 = value1.substring(1, value1.length() - 1);
            if (!name.startsWith("$"))
            {
               cookieName = name;
               cookieValue = value1;
            }
            else if (name.equalsIgnoreCase("$Version"))
            {
               version = Integer.parseInt(value1);
            }
            else if (name.equalsIgnoreCase("$Path"))
            {
               path = value1;
            }
            else if (name.equalsIgnoreCase("$Domain"))
            {
               domain = value1;
            }
         }
         return new Cookie(cookieName, cookieValue, path, domain, version);
      }
      catch (Exception ex)
      {
         throw new IllegalArgumentException("Failed to parse cookie string '" + value + "'", ex);
      }
   }

   public String toString(Object value)
   {
      Cookie cookie = (Cookie) value;
      StringBuffer buf = new StringBuffer();
      ServerCookie.appendCookieValue(buf, 0, cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getDomain(), null, -1, false);
      return buf.toString();
   }
}