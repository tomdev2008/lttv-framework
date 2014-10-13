/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.letv.javax.ws.rs.ext;


/**
 * Interface for message body writer interceptors that wrap around calls
 * to {@link com.letv.javax.ws.rs.ext.MessageBodyWriter#writeTo}.
 *
 * <p>
 * Providers implementing {@code WriterInterceptor} contract must be either programmatically
 * registered in a JAX-RS runtime or must be annotated with
 * {@link com.letv.javax.ws.rs.ext.Provider &#64;Provider} annotation to be automatically discovered
 * by the JAX-RS runtime during a provider scanning phase.
 * Message body interceptor instances may also be discovered and
 * bound {@link com.letv.javax.ws.rs.container.DynamicFeature dynamically} to particular resource methods.
 * </p>
 *
 * @author Santiago Pericas-Geertsen
 * @author Bill Burke
 * @author Marek Potociar
 * @see MessageBodyWriter
 * @since 2.0
 */
public interface WriterInterceptor {

    /**
     * Interceptor method wrapping calls to {@link MessageBodyWriter#writeTo} method.
     * The parameters of the wrapped method called are available from {@code context}.
     * Implementations of this method SHOULD explicitly call
     * {@link WriterInterceptorContext#proceed} to invoke the next interceptor in the chain,
     * and ultimately the wrapped {@code MessageBodyWriter.writeTo} method.
     *
     * @param context invocation context.
     * @throws java.io.IOException if an IO error arises or is thrown by the wrapped
     *                             {@code MessageBodyWriter.writeTo} method.
     * @throws com.letv.javax.ws.rs.WebApplicationException
     *                             thrown by the wrapped {@code MessageBodyWriter.writeTo} method.
     */
    void aroundWriteTo(WriterInterceptorContext context)
            throws java.io.IOException, com.letv.javax.ws.rs.WebApplicationException;
}
