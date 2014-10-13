package com.letv.mobile.core.rpc.rest;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.letv.javax.ws.rs.RuntimeType;
import com.letv.javax.ws.rs.client.ClientRequestFilter;
import com.letv.javax.ws.rs.client.ClientResponseFilter;
import com.letv.javax.ws.rs.container.DynamicFeature;
import com.letv.javax.ws.rs.core.Configurable;
import com.letv.javax.ws.rs.core.Configuration;
import com.letv.javax.ws.rs.core.Feature;
import com.letv.javax.ws.rs.core.MediaType;
import com.letv.javax.ws.rs.ext.ContextResolver;
import com.letv.javax.ws.rs.ext.ExceptionMapper;
import com.letv.javax.ws.rs.ext.MessageBodyReader;
import com.letv.javax.ws.rs.ext.MessageBodyWriter;
import com.letv.javax.ws.rs.ext.Providers;
import com.letv.javax.ws.rs.ext.ReaderInterceptor;
import com.letv.javax.ws.rs.ext.RuntimeDelegate;
import com.letv.javax.ws.rs.ext.WriterInterceptor;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
public class ClientConfiguration implements Configuration, Configurable<ClientConfiguration>, Providers, HeaderValueProcessor
{
   protected ResteasyProviderFactory providerFactory;

   public ClientConfiguration(ResteasyProviderFactory factory)
   {
//      if (factory instanceof ThreadLocalResteasyProviderFactory)
//      {
//         factory = ((ThreadLocalResteasyProviderFactory)factory).getDelegate();
//      }
      this.providerFactory = new ResteasyProviderFactory(factory);
   }

   public ClientConfiguration(ClientConfiguration parent)
   {
      this(parent.getProviderFactory());
      setProperties(parent.getProperties());
   }

   public void setProperties(Map<String, Object> newProps)
   {
      providerFactory.setProperties(newProps);
   }

   protected ResteasyProviderFactory getProviderFactory()
   {
      return providerFactory;
   }

   public Map<String, Object> getMutableProperties()
   {
      return providerFactory.getMutableProperties();
   }

   /**
    * Convert an object to a header string.  First try StringConverter, then HeaderDelegate, then object.toString()
    *
    * @param object
    * @return
    */
   public String toHeaderString(Object object)
   {
      if (object instanceof String) return (String)object;
      // Javadoc and TCK requires that you only get from RuntimeDelegate.getInstance().createHeaderDelegate()
      RuntimeDelegate.HeaderDelegate delegate = RuntimeDelegate.getInstance().createHeaderDelegate(object.getClass());
      if (delegate != null)
         return delegate.toString(object);
      else
         return object.toString();
   }

   public <T> MessageBodyWriter<T> getMessageBodyWriter(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return providerFactory.getMessageBodyWriter(type, genericType, annotations, mediaType);
   }

   public <T> MessageBodyReader<T> getMessageBodyReader(Class<T> type, Type genericType, Annotation[] annotations, MediaType mediaType)
   {
      return providerFactory.getMessageBodyReader(type, genericType, annotations, mediaType);
   }

   public WriterInterceptor[] getWriterInterceptors(Class declaring, AccessibleObject target)
   {
      return providerFactory.getClientWriterInterceptorRegistry().postMatch(declaring, target);
   }

   public ReaderInterceptor[] getReaderInterceptors(Class declaring, AccessibleObject target)
   {
      return providerFactory.getClientReaderInterceptorRegistry().postMatch(declaring, target);
   }

   public ClientRequestFilter[] getRequestFilters(Class declaring, AccessibleObject target)
   {
      return providerFactory.getClientRequestFilters().postMatch(declaring, target);
   }

   public ClientResponseFilter[] getResponseFilters(Class declaring, AccessibleObject target)
   {
      return providerFactory.getClientResponseFilters().postMatch(declaring, target);
   }

   public Set<DynamicFeature> getDynamicFeatures()
   {
      return providerFactory.getClientDynamicFeatures();
   }

   public String toString(Object object)
   {
      return providerFactory.toString(object, object.getClass(), null, null);
   }




   // interface implementation

   // Providers

   @Override
   public <T extends Throwable> ExceptionMapper<T> getExceptionMapper(Class<T> type)
   {
      return providerFactory.getExceptionMapper(type);
   }

   @Override
   public <T> ContextResolver<T> getContextResolver(Class<T> contextType, MediaType mediaType)
   {
      return providerFactory.getContextResolver(contextType, mediaType);
   }

   // Configuration

   @Override
   public Map<String, Object> getProperties()
   {
      return providerFactory.getProperties();
   }

   @Override
   public Object getProperty(String name)
   {
      return providerFactory.getProperty(name);
   }

   @Override
   public Set<Class<?>> getClasses()
   {
      return providerFactory.getProviderClasses();
   }

   @Override
   public Set<Object> getInstances()
   {
      return providerFactory.getProviderInstances();
   }

   @Override
   public ClientConfiguration register(Class<?> providerClass)
   {
      providerFactory.register(providerClass);
      return this;
   }

   @Override
   public ClientConfiguration register(Object provider)
   {
      providerFactory.register(provider);
      return this;
   }

   @Override
   public ClientConfiguration register(Class<?> providerClass, int priority)
   {
      providerFactory.register(providerClass, priority);
      return this;
   }

   @Override
   public ClientConfiguration register(Object provider, int Priority)
   {
      providerFactory.register(provider, Priority);
      return this;
   }

   @Override
   public ClientConfiguration property(String name, Object value)
   {
      providerFactory.property(name, value);
      return this;
   }

   @Override
   public Configuration getConfiguration()
   {
      return this;
   }

   @Override
   public ClientConfiguration register(Class<?> componentClass, Class<?>... contracts)
   {
      providerFactory.register(componentClass, contracts);
      return this;
   }

   @Override
   public ClientConfiguration register(Class<?> componentClass, Map<Class<?>, Integer> contracts)
   {
      providerFactory.register(componentClass, contracts);
      return this;
   }

   @Override
   public ClientConfiguration register(Object component, Class<?>... contracts)
   {
      providerFactory.register(component, contracts);
      return this;
   }

   @Override
   public ClientConfiguration register(Object component, Map<Class<?>, Integer> contracts)
   {
      providerFactory.register(component, contracts);
      return this;
   }

   @Override
   public RuntimeType getRuntimeType()
   {
      return RuntimeType.CLIENT;
   }

   @Override
   public Collection<String> getPropertyNames()
   {
      return providerFactory.getProperties().keySet();
   }

   @Override
   public boolean isEnabled(Feature feature)
   {
      return providerFactory.isEnabled(feature);
   }

   @Override
   public boolean isEnabled(Class<? extends Feature> featureClass)
   {
      return providerFactory.isEnabled(featureClass);
   }

   @Override
   public boolean isRegistered(Object component)
   {
      return providerFactory.isRegistered(component);
   }

   @Override
   public boolean isRegistered(Class<?> componentClass)
   {
      return providerFactory.isRegistered(componentClass);
   }

   @Override
   public Map<Class<?>, Integer> getContracts(Class<?> componentClass)
   {
      return providerFactory.getContracts(componentClass);
   }
}
