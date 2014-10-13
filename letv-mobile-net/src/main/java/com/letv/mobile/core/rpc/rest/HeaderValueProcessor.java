package com.letv.mobile.core.rpc.rest;
/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision$
 */
public interface HeaderValueProcessor
{
   /**
    * Convert an object to a header string.  First try StringConverter, then HeaderDelegate, then object.toString()
    *
    * @param object
    * @return
    */
   String toHeaderString(Object object);
}
