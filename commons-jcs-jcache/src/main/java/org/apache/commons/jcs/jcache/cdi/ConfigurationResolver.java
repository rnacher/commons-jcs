package org.apache.commons.jcs.jcache.cdi;

import javax.cache.configuration.Configuration;

public interface ConfigurationResolver
{
    Configuration<Object,Object> get(String cacheName);
}
