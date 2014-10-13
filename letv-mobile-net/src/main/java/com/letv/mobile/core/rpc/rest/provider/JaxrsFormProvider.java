package com.letv.mobile.core.rpc.rest.provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import com.letv.javax.ws.rs.Consumes;
import com.letv.javax.ws.rs.Produces;
import com.letv.javax.ws.rs.WebApplicationException;
import com.letv.javax.ws.rs.core.Form;
import com.letv.javax.ws.rs.core.MediaType;
import com.letv.javax.ws.rs.core.MultivaluedMap;
import com.letv.javax.ws.rs.ext.MessageBodyReader;
import com.letv.javax.ws.rs.ext.MessageBodyWriter;
import com.letv.javax.ws.rs.ext.Provider;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
@Provider
@Produces("application/x-www-form-urlencoded")
@Consumes("application/x-www-form-urlencoded")
public class JaxrsFormProvider implements MessageBodyReader<Form>, MessageBodyWriter<Form>
{
   @Override
   public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return Form.class.isAssignableFrom(type);
   }

   @Override
   public Form readFrom(Class<Form> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException
   {
      MultivaluedMap<String, String> map = new FormUrlEncodedProvider().readFrom(null, null, annotations, mediaType, httpHeaders, entityStream);
      return new Form(map);
   }

   @Override
   public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return Form.class.isAssignableFrom(type);
   }

   @Override
   public long getSize(Form form, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return -1;
   }

   @Override
   public void writeTo(Form form, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
   {
      new FormUrlEncodedProvider().writeTo(form.asMap(), null, null, annotations, mediaType, httpHeaders, entityStream);
   }
}
