package org.apache.commons.jcs.jcache.cdi;

import javax.cache.configuration.Factory;
import javax.cache.expiry.EternalExpiryPolicy;
import javax.cache.expiry.ExpiryPolicy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class TestExprirationPolicyFactoryProducer {

	@ApplicationScoped
	@Produces
	public ExpiryPolicyFactoryResolver produces() {
		return new ExpiryPolicyFactoryResolver() {
			@Override
			public Factory<? extends ExpiryPolicy> get(String key) {
				return EternalExpiryPolicy.factoryOf();
			}
		};
	}

}
