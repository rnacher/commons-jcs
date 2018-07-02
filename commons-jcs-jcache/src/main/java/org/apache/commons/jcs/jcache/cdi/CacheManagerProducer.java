package org.apache.commons.jcs.jcache.cdi;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class CacheManagerProducer {

	@ApplicationScoped
	@Produces
	public CacheManager produces() {
		return Caching.getCachingProvider().getCacheManager();
	}

}
