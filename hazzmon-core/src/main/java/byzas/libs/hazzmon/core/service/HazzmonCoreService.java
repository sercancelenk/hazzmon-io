package byzas.libs.hazzmon.core.service;

import com.hazelcast.core.HazelcastInstance;
import byzas.libs.hazzmon.core.model.ClusterMemberInfo;
import byzas.libs.hazzmon.core.model.HazelcastInstanceInfo;
import byzas.libs.hazzmon.core.model.Key;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 17.05.2018
 */


public interface HazzmonCoreService {

    List<ClusterMemberInfo> getInstanceMembers(String instanceBeanName);

    Map<String, Integer> getCounts(String instanceBeanName, String key);

    List<HazelcastInstanceInfo> getInstanceNames();

    List<Key> getKeysBy(Optional<String> beanName);

    List<Key> getKeysBy(HazelcastInstance instance, String beanName);

    Key getDetailOfKey(String keyName);
}
