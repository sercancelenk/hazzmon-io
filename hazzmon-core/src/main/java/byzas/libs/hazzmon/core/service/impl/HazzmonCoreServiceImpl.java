package byzas.libs.hazzmon.core.service.impl;

import byzas.libs.hazzmon.core.model.*;
import byzas.libs.hazzmon.core.service.HazzmonCoreService;
import byzas.libs.hazzmon.core.util.SPAppContext;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.ReplicatedMapConfig;
import com.hazelcast.core.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 17.05.2018
 */

@Service
class HazzmonCoreServiceImpl extends BaseService implements HazzmonCoreService {

    public static final Logger logger = LoggerFactory.getLogger(HazzmonCoreService.class);

    @Override
    public List<ClusterMemberInfo> getInstanceMembers(String instanceBeanName) {
        List<ClusterMemberInfo> instanceMembersMap = new ArrayList<>();
        Set<Member> members = new HashSet<>();
        if ("all".equals(instanceBeanName)) {
            Set<HazelcastInstance> allInstances = Hazelcast.getAllHazelcastInstances();
            for (HazelcastInstance instance : allInstances) {
                populateClusterMembers(instanceMembersMap, instance);
            }
        } else if (StringUtils.isNoneBlank(instanceBeanName)) {
            Object beanObject = findHZInstance(instanceBeanName);
            if (beanObject instanceof HazelcastInstance) {
                HazelcastInstance instance = (HazelcastInstance) beanObject;
                populateClusterMembers(instanceMembersMap, instance);
            }
        }

        return instanceMembersMap;
    }


    private void populateClusterMembers(List<ClusterMemberInfo> instanceMembersMap, HazelcastInstance instance) {
        instance.getCluster().getMembers().stream().map(m -> {
            ClusterMemberInfo memberInfo = new ClusterMemberInfo();
            memberInfo.setInstanceName(instance.getName());
            memberInfo.setMember(m);
            return memberInfo;
        }).collect(Collectors.toCollection(() -> instanceMembersMap));
    }

    @Override
    public Map<String, Integer> getCounts(String instanceBeanName, String key) {
        int cntInDistributed = 0;
        int cntInReplicated = 0;
        Object beanObject = findHZInstance(instanceBeanName);
        if (beanObject instanceof HazelcastInstance) {
            HazelcastInstance instance = (HazelcastInstance) beanObject;
            final String keyWithoutAsterisk = StringUtils.remove(StringUtils.remove(key, "%"), "*");
            Set<String> configKeyset = instance.getConfig().getMapConfigs().keySet();
            if (key.startsWith("*") || key.startsWith("%")) {
                for (Iterator iterator = configKeyset.iterator(); iterator.hasNext(); ) {
                    String configName = (String) iterator.next();

                    List<Object> keys = (List<Object>) instance.getReplicatedMap(configName).keySet().stream().filter(s -> s.toString().contains(keyWithoutAsterisk)).collect(Collectors.toList());
                    cntInReplicated += keys.size();
                    keys = (List<Object>) instance.getMap(configName).keySet().stream().filter(s -> s.toString().contains(keyWithoutAsterisk)).collect(Collectors.toList());
                    cntInDistributed += keys.size();
                }
            } else if (key.endsWith("*") || key.endsWith("%")) {
                for (Iterator iterator = configKeyset.iterator(); iterator.hasNext(); ) {
                    String configName = (String) iterator.next();

                    List<Object> keys = (List<Object>) instance.getReplicatedMap(configName).keySet().stream().filter(s -> s.toString().startsWith(keyWithoutAsterisk)).collect(Collectors.toList());
                    cntInReplicated += keys.size();
                    keys = (List<Object>) instance.getMap(configName).keySet().stream().filter(s -> s.toString().startsWith(keyWithoutAsterisk)).collect(Collectors.toList());
                    cntInDistributed += keys.size();
                }
            } else {
                for (Iterator iterator = configKeyset.iterator(); iterator.hasNext(); ) {
                    String configName = (String) iterator.next();

                    List<Object> keys = (List<Object>) instance.getReplicatedMap(configName).keySet().stream().filter(s -> s.toString().equals(keyWithoutAsterisk)).collect(Collectors.toList());
                    cntInReplicated += keys.size();
                    keys = (List<Object>) instance.getMap(configName).keySet().stream().filter(s -> s.toString().equals(keyWithoutAsterisk)).collect(Collectors.toList());
                    cntInDistributed += keys.size();
                }
            }
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("Count in ReplicatedMap", cntInReplicated);
        result.put("Count in DistributedMap", cntInDistributed);
        return result;
    }

    @Override
    public List<HazelcastInstanceInfo> getInstanceNames() {
        List<HazelcastInstanceInfo> instances = new ArrayList<>();
        String[] beanNames = SPAppContext.getApplicationContext().getBeanNamesForType(HazelcastInstance.class);
        if (Objects.nonNull(beanNames)) {
            for (int i = 0; i < beanNames.length; i++) {
                String beanName = beanNames[i];
                HazelcastInstance instance = (HazelcastInstance) SPAppContext.getApplicationContext().getBean(beanName);
                HazelcastInstanceInfo instanceInfo = new HazelcastInstanceInfo();
                instanceInfo.setBeanName(beanName);
                instanceInfo.setInstanceName(instance.getName());
                Map<String, MapConfig> mapConfigs = instance.getConfig().getMapConfigs();
                Map<String, ReplicatedMapConfig> replicatedMapConfigs = instance.getConfig().getReplicatedMapConfigs();

                if (Objects.nonNull(mapConfigs)) {
                    instanceInfo.setTotalDistributedMapCount(mapConfigs.size());
                }

                int totalKeyCountInDistributedMaps = 0;
                for (Map.Entry<String, MapConfig> entry : mapConfigs.entrySet()) {
                    MapConfig distributedMapConfig = entry.getValue();
                    Set<Object> mapKeyset = instance.getMap(distributedMapConfig.getName()).keySet();
                    if (CollectionUtils.isNotEmpty(mapKeyset)) totalKeyCountInDistributedMaps += mapKeyset.size();
                }
                instanceInfo.setTotalKeyCountInDistributedMaps(totalKeyCountInDistributedMaps);

                if (Objects.nonNull(replicatedMapConfigs)) {
                    instanceInfo.setTotalReplicatedMapCount(replicatedMapConfigs.size());
                }

                int totalKeyCountInReplicatedMaps = 0;
                for (Map.Entry<String, ReplicatedMapConfig> entry : replicatedMapConfigs.entrySet()) {
                    ReplicatedMapConfig replicatedMapConfig = entry.getValue();
                    Set<Object> mapKeyset = instance.getReplicatedMap(replicatedMapConfig.getName()).keySet();
                    if (CollectionUtils.isNotEmpty(mapKeyset)) totalKeyCountInReplicatedMaps += mapKeyset.size();
                }
                instanceInfo.setTotalKeyCountInReplicatedMaps(totalKeyCountInReplicatedMaps);

                instances.add(instanceInfo);
            }
        }
        return instances;
    }

    @Override
    public List<Key> getKeysBy(Optional<String> beanName) {
        List<Key> cacheInfos = new ArrayList<>();

        if (!beanName.isPresent() || (beanName.isPresent() && "all".equals(beanName.get()))) {
            Set<HazelcastInstance> instances = Hazelcast.getAllHazelcastInstances();
            if (CollectionUtils.isNotEmpty(instances)) {
                for (HazelcastInstance instance : instances) {
                    String[] hzBeanNames = SPAppContext.getApplicationContext().getBeanNamesForType(HazelcastInstance.class);
                    for (String bean : hzBeanNames) {
                        cacheInfos.addAll(this.getKeysBy(instance, bean));
                    }
                }
            }
        } else {
            Object beanObject = findHZInstance(beanName.get());
            if (beanObject instanceof HazelcastInstance) {
                // Example: hzInstanceMicroservice
                HazelcastInstance instance = (HazelcastInstance) beanObject;
                cacheInfos.addAll(this.getKeysBy(instance, beanName.get()));
            }
        }

        return cacheInfos;
    }

    @Override
    public List<Key> getKeysBy(HazelcastInstance instance, String beanName) {
        if (Objects.isNull(instance)) return null;

        List<Key> keys = new ArrayList<>();

        Map<String, MapConfig> distrubutedMapConfigs = instance.getConfig().getMapConfigs();
        Map<String, ReplicatedMapConfig> replicatedMapConfigs = instance.getConfig().getReplicatedMapConfigs();

        for (Map.Entry<String, MapConfig> entry : distrubutedMapConfigs.entrySet()) {

            MapConfig distributedMapConfig = entry.getValue();
            MapInfo mapInfo = new MapInfo();
            mapInfo.setMapType(MapType.DISTRIBUTED);
            mapInfo.setTimeToLive(distributedMapConfig.getTimeToLiveSeconds());
            mapInfo.setTimeToLiveType(TimeUnit.SECONDS);
            mapInfo.setMapName(distributedMapConfig.getName());

            Set<Object> mapKeyset = instance.getMap(entry.getValue().getName()).keySet();
            populateKeys(instance, keys, mapInfo, mapKeyset, beanName);


        }

        for (Map.Entry<String, ReplicatedMapConfig> entry : replicatedMapConfigs.entrySet()) {
            ReplicatedMapConfig replicatedMapConfig = entry.getValue();
            MapInfo mapInfo = new MapInfo();
            mapInfo.setMapType(MapType.REPLICATED);
            mapInfo.setTimeToLive(0);
            mapInfo.setTimeToLiveType(TimeUnit.SECONDS);
            mapInfo.setMapName(replicatedMapConfig.getName());

            ReplicatedMap replicatedMap = instance.getReplicatedMap(entry.getValue().getName());
            Set<Object> mapKeyset = replicatedMap.keySet();

            populateKeys(instance, keys, mapInfo, mapKeyset, beanName);
        }

        return keys;
    }


    @Override
    public Object getValueOfKey(String keyName) {
        Key key = this.getDetailOfKey(keyName);
        return Objects.nonNull(key) ? key.getValue() : StringUtils.EMPTY;
    }

    @Override
    public Key getDetailOfKey(String keyName) {
        Object value = null;
        Key key = new Key();

        String[] hzBeanNames = SPAppContext.getApplicationContext().getBeanDefinitionNames();
        for (int j = 0; j < hzBeanNames.length; j++) {
            String hzBeanName = hzBeanNames[j];
            Object object = SPAppContext.getApplicationContext().getBean(hzBeanName);

            if (object instanceof HazelcastInstance) {
                HazelcastInstance instance = (HazelcastInstance) object;


                for (Map.Entry<String, MapConfig> entry : instance.getConfig().getMapConfigs().entrySet()) {
                    MapConfig mapConfig = entry.getValue();
                    IMap map = instance.getMap(mapConfig.getName());
                    key.setTimeToLiveType(TimeUnit.SECONDS);
                    key.setTimeToLive(mapConfig.getTimeToLiveSeconds());
                    key.setMapType(MapType.DISTRIBUTED);
                    key.setMapName(mapConfig.getName());
                    value = map.get(keyName);
                    if (Objects.nonNull(value)) break;
                }

                if (Objects.isNull(value)) {
                    for (Map.Entry<String, ReplicatedMapConfig> entry : instance.getConfig().getReplicatedMapConfigs().entrySet()) {
                        ReplicatedMapConfig replicatedMapConfig = entry.getValue();
                        ReplicatedMap map = instance.getReplicatedMap(replicatedMapConfig.getName());
                        key.setTimeToLiveType(TimeUnit.SECONDS);
                        key.setTimeToLive(0);
                        key.setMapType(MapType.REPLICATED);
                        key.setMapName(replicatedMapConfig.getName());
                        value = map.get(keyName);
                        if (Objects.nonNull(value)) break;
                    }

                }

                if (Objects.isNull(value)) {
                    key = new Key();
                    continue;
                }

                key.setBeanName(hzBeanName);
                key.setInstanceName(instance.getName());
                key.setValue(value);
                key.setStatus(true);
                key.setKey(keyName);
                break;
            }
        }

        return key;
    }

    @Override
    public List<String> evictKeyByInstance(EvictRequest request) {
        List<String> keysToRemove = new ArrayList<>();


        if("all".equals(request.getBean())){
            Set<HazelcastInstance> instances =  Hazelcast.getAllHazelcastInstances();
            for(HazelcastInstance instance : instances){
                if(CollectionUtils.isNotEmpty(request.getKeys())){
                    for (String key : request.getKeys()){
                        keysToRemove = prepareKeysForEviction(key, instance);
                        Set<String> configKeyset = instance.getConfig().getMapConfigs().keySet();
                        Set<String> replicatedConfigKeyset = instance.getConfig().getReplicatedMapConfigs().keySet();
                        removeKeys(keysToRemove, instance, configKeyset);
                        removeKeys(keysToRemove, instance, replicatedConfigKeyset);
                    }
                }
            }
        }else{
            Object beanObject = findHZInstance(request.getBean());
            if (beanObject instanceof HazelcastInstance) {
                if(CollectionUtils.isNotEmpty(request.getKeys())){
                    for(String key : request.getKeys()){
                        HazelcastInstance instance = (HazelcastInstance) beanObject;
                        keysToRemove = prepareKeysForEviction(key, instance);
                        Set<String> configKeyset = instance.getConfig().getMapConfigs().keySet();
                        Set<String> replicatedConfigKeyset = instance.getConfig().getReplicatedMapConfigs().keySet();
                        removeKeys(keysToRemove, instance, configKeyset);
                        removeKeys(keysToRemove, instance, replicatedConfigKeyset);
                    }
                }
            }
        }

        return keysToRemove;
    }

    private List<String> prepareKeysForEviction(String key, HazelcastInstance instance) {
        List<String> keysToRemove;
        final String keyWithoutAsterisk = StringUtils.remove(StringUtils.remove(key, "%"), "*");
        Set<String> configKeyset = instance.getConfig().getMapConfigs().keySet();
        Set<String> replicatedConfigKeyset = instance.getConfig().getReplicatedMapConfigs().keySet();
        keysToRemove = new ArrayList<String>();
        if (key.startsWith("*") || key.startsWith("%")) {
            for (Iterator iterator = configKeyset.iterator(); iterator.hasNext(); ) {
                String configName = (String) iterator.next();
                keysToRemove.addAll(instance.getMap(configName).keySet().stream().filter(s -> s.toString().contains(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList()));
            }
            for (Iterator iterator = replicatedConfigKeyset.iterator(); iterator.hasNext(); ) {
                String configName = (String) iterator.next();
                keysToRemove.addAll(instance.getReplicatedMap(configName).keySet().stream().filter(s -> s.toString().contains(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList()));
            }
        } else if (key.endsWith("*") || key.endsWith("%")) {
            for (Iterator iterator = configKeyset.iterator(); iterator.hasNext(); ) {
                String configName = (String) iterator.next();

                keysToRemove.addAll(instance.getReplicatedMap(configName).keySet().stream().filter(s -> s.toString().startsWith(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList()));
                keysToRemove.addAll(instance.getMap(configName).keySet().stream().filter(s -> s.toString().startsWith(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList()));
            }
            for (Iterator iterator = replicatedConfigKeyset.iterator(); iterator.hasNext(); ) {
                String configName = (String) iterator.next();
                keysToRemove.addAll(instance.getReplicatedMap(configName).keySet().stream().filter(s -> s.toString().startsWith(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList()));
            }
        } else {
            for (Iterator iterator = configKeyset.iterator(); iterator.hasNext(); ) { String configName = (String) iterator.next();

                keysToRemove.addAll(instance.getMap(configName).keySet().stream().filter(s -> s.toString().equals(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList()));
            }

            for (Iterator iterator = replicatedConfigKeyset.iterator(); iterator.hasNext(); ) {
                String configName = (String) iterator.next();

                keysToRemove.addAll(instance.getReplicatedMap(configName).keySet().stream().filter(s -> s.toString().equals(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList()));
            }
        }
        return keysToRemove;
    }

    private void removeKeys(List<String> keysToRemove, HazelcastInstance instance, Set<String> configKeyset) {
        for (Iterator iterator = configKeyset.iterator(); iterator.hasNext(); ) {
            String configName = (String) iterator.next();
            ReplicatedMap<Object, Object> rMap = instance.getReplicatedMap(configName);
            IMap<Object, Object> iMap = instance.getMap(configName);
            for (String kk : keysToRemove) {
                rMap.remove(kk);
                iMap.remove(kk);
            }
        }
    }

    private void populateKeys(HazelcastInstance instance, List<Key> keys, MapInfo mapInfo, Set<Object> mapKeyset, String beanName) {
        mapKeyset.stream().limit(1500).map(k -> {
            Key key = new Key();
            key.setInstanceName(instance.getName());
            key.setKey(k);
            key.setMapName(mapInfo.getMapName());
            key.setMapType(mapInfo.getMapType());
            key.setTimeToLive(mapInfo.getTimeToLive());
            key.setTimeToLiveType(mapInfo.getTimeToLiveType());
            key.setBeanName(beanName);
            return key;

        }).collect(Collectors.toCollection(() -> keys));
    }


}
