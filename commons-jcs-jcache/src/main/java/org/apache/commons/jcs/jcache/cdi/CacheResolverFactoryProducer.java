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

import javax.cache.CacheManager;
import javax.cache.annotation.CacheResolverFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class CacheResolverFactoryProducer {

	private CacheManager cacheManager;
	private ExpiryPolicyFactoryResolver expiryPolicyFactoryResolver;

//	@Inject
//	public CacheResolverFactoryProducer(CacheManager cacheManager) {
//		this.cacheManager = cacheManager;
//	}

	@ApplicationScoped
	@Produces
	public CacheResolverFactory produce() {
		return new CacheResolverFactoryImpl(cacheManager, expiryPolicyFactoryResolver);
	}

}
