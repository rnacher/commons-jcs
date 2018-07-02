package org.apache.commons.jcs.jcache.cdi;

import javax.cache.configuration.Factory;
import javax.cache.expiry.ExpiryPolicy;

public interface ExpiryPolicyFactoryResolver {

  public Factory<? extends ExpiryPolicy> get(String cacheName);

}
