package byzas.libs.hazzmon.core.service.impl;

import byzas.libs.hazzmon.core.util.SPAppContext;
import com.hazelcast.spring.cache.HazelcastCacheManager;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 17.05.2018
 */


public abstract class BaseService {
    public Object findHZInstance(String beanName) {
        Object beanObject = SPAppContext.getApplicationContext().getBean(beanName);
        if (beanObject instanceof HazelcastCacheManager) {
            // Example: hazelcastGenericCache
            HazelcastCacheManager manager = (com.hazelcast.spring.cache.HazelcastCacheManager) beanObject;
            beanObject = manager.getHazelcastInstance();
        }
        return beanObject;
    }
}
