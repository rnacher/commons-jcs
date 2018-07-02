/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.jcs.jcache.cdi;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.annotation.CacheMethodDetails;
import javax.cache.annotation.CacheResolver;
import javax.cache.annotation.CacheResolverFactory;
import javax.cache.annotation.CacheResult;
import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.spi.CachingProvider;
import javax.inject.Inject;

import java.lang.annotation.Annotation;

public class CacheResolverFactoryImpl implements CacheResolverFactory
{
	private final CachingProvider provider;
    private final CacheManager cacheManager;
    private final ExpiryPolicyFactoryResolver expirationFactory;

    @Inject
    public CacheResolverFactoryImpl(CachingProvider provider,CacheManager cacheManager,
    	      ExpiryPolicyFactoryResolver expirationFactory) {
    	this.provider = provider;
    	this.cacheManager = cacheManager;
    	this.expirationFactory = expirationFactory;
    }

    @Override
    public CacheResolver getCacheResolver(CacheMethodDetails<? extends Annotation> cacheMethodDetails)
    {
        return findCacheResolver(cacheMethodDetails.getCacheName());
    }

    @Override
    public CacheResolver getExceptionCacheResolver(final CacheMethodDetails<CacheResult> cacheMethodDetails)
    {
        final String exceptionCacheName = cacheMethodDetails.getCacheAnnotation().exceptionCacheName();
        if (exceptionCacheName == null || exceptionCacheName.isEmpty())
        {
            throw new IllegalArgumentException("CacheResult.exceptionCacheName() not specified");
        }
        return findCacheResolver(exceptionCacheName);
    }

    private CacheResolver findCacheResolver(String exceptionCacheName)
    {
        Cache<?, ?> cache = cacheManager.getCache(exceptionCacheName);
        if (cache == null)
        {
            cache = createCache(exceptionCacheName);
        }
        return new CacheResolverImpl(cache);
    }

    private Cache<?, ?> createCache(final String exceptionCacheName)
    {
    	Factory<? extends ExpiryPolicy> factory = expirationFactory.get(exceptionCacheName);

	    cacheManager.createCache(exceptionCacheName, new MutableConfiguration<Object, Object>()
	        .setStoreByValue(false).setExpiryPolicyFactory(factory));
	    return cacheManager.getCache(exceptionCacheName);
    }

    public void release()
    {
        cacheManager.close();
        provider.close();
    }
}
