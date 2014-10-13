package com.letv.mobile.core.rpc.rest;
import java.io.InputStream;
import java.net.URI;
import java.util.Enumeration;

import com.letv.javax.ws.rs.core.HttpHeaders;
import com.letv.javax.ws.rs.core.MultivaluedMap;
import com.letv.mobile.core.rpc.rest.spi.HttpRequest;
import com.letv.mobile.core.rpc.rest.spi.ResteasyUriInfo;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
public class DelegatingHttpRequest implements HttpRequest
{
   private HttpRequest delegate;

   public DelegatingHttpRequest(HttpRequest delegate)
   {
      this.delegate = delegate;
   }

   @Override
   public void setHttpMethod(String method)
   {
      delegate.setHttpMethod(method);
   }

   @Override
   public ResteasyUriInfo getUri()
   {
      return delegate.getUri();
   }

   @Override
   public void setRequestUri(URI requestUri) throws IllegalStateException
   {
      delegate.setRequestUri(requestUri);
   }

   @Override
   public void setRequestUri(URI baseUri, URI requestUri) throws IllegalStateException
   {
      delegate.setRequestUri(baseUri, requestUri);
   }

   @Override
   public HttpHeaders getHttpHeaders()
   {
      return delegate.getHttpHeaders();
   }

   @Override
   public InputStream getInputStream()
   {
      return delegate.getInputStream();
   }

   @Override
   public void setInputStream(InputStream stream)
   {
      delegate.setInputStream(stream);
   }

   @Override
   public String getHttpMethod()
   {
      return delegate.getHttpMethod();
   }

   @Override
   public MultivaluedMap<String, String> getFormParameters()
   {
      return delegate.getFormParameters();
   }

   @Override
   public MultivaluedMap<String, String> getDecodedFormParameters()
   {
      return delegate.getDecodedFormParameters();
   }

   @Override
   public Object getAttribute(String attribute)
   {
      return delegate.getAttribute(attribute);
   }

   @Override
   public void setAttribute(String name, Object value)
   {
      delegate.setAttribute(name, value);
   }

   @Override
   public void removeAttribute(String name)
   {
      delegate.removeAttribute(name);
   }

   @Override
   public Enumeration<String> getAttributeNames()
   {
      return delegate.getAttributeNames();
   }

   @Override
   public boolean isInitial()
   {
      return delegate.isInitial();
   }


//   @Override
//   public ResteasyAsynchronousContext getAsyncContext()
//   {
//      return delegate.getAsyncContext();
//   }
}
