package byzas.libs.hazzmon.core.controller;

import byzas.libs.hazzmon.core.service.HazzmonCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 16.05.2018
 */


@RestController
@RequestMapping(value = "hazzmon-api")
public class HazzmonCoreController {

    @Autowired
    private HazzmonCoreService hazelcastCacheInfoService;

    @GetMapping(value = {"/list/keys", "/list/keys/of/{instanceBeanName}"})
    public ResponseEntity<?> getKeys(@PathVariable(required = false, value = "instanceBeanName") Optional<String> beanName) {
        return ResponseEntity.ok(hazelcastCacheInfoService.getKeysBy(beanName));
    }

    @GetMapping(value = "/detail/key/{keyName}")
    public ResponseEntity<?> getDetailOfKey(@PathVariable String keyName){
        return ResponseEntity.ok(hazelcastCacheInfoService.getDetailOfKey(keyName));
    }

    @GetMapping("list/names/of/instances")
    public ResponseEntity<?> getInstanceNames() {
        return ResponseEntity.ok(hazelcastCacheInfoService.getInstanceNames());
    }


    @GetMapping(value = "/counts/of/{key}/by/{instanceBeanName}")
    public ResponseEntity<?> getCounts(@PathVariable(value = "instanceBeanName") String beanName, @PathVariable(value = "key") String key) {
        return ResponseEntity.ok(hazelcastCacheInfoService.getCounts(beanName, key));
    }

    @GetMapping(value = "/list/names/of/members/by/instance/{instanceBeanName}")
    public ResponseEntity<?> getInstanceMembers(@PathVariable("instanceBeanName") String instanceBeanName) {
        return ResponseEntity.ok(hazelcastCacheInfoService.getInstanceMembers(instanceBeanName));
    }

//    @GetMapping(value = "/evict/{bean}/{key}")
//    @FizyIgnoreSecurity
//    public ResponseEntity<?> evict(@PathVariable(required = true) String bean, @PathVariable(required = true) String key) {
//        Object beanObject = findHZInstance(bean);
//        int cnt = 0;
//        if (beanObject instanceof HazelcastCacheStore) {
//            HazelcastCacheStore store = (HazelcastCacheStore)beanObject;
//            final String keyWithoutAsterisk = StringUtils.remove(StringUtils.remove(key, "%"), "*");
//            List<String> keysToRemove = null;
//            if (key.startsWith("*") || key.startsWith("%")) {
//                keysToRemove = (List<String>) store.getReplicatedMap().keySet().stream().filter(s -> s.toString().contains(keyWithoutAsterisk)).collect(Collectors.toList());
//            } else if (key.endsWith("*") || key.endsWith("%")) {
//                keysToRemove = (List<String>) store.getReplicatedMap().keySet().stream().filter(s -> s.toString().startsWith(keyWithoutAsterisk)).collect(Collectors.toList());
//            } else {
//                keysToRemove = (List<String>) store.getReplicatedMap().keySet().stream().filter(s -> s.toString().equals(keyWithoutAsterisk)).collect(Collectors.toList());
//            }
//            cnt = keysToRemove.size();
//            keysToRemove.forEach(k -> store.evict(k));
//        } else if (beanObject instanceof HazelcastInstance) {
//            HazelcastInstance instance = (HazelcastInstance)beanObject;
//            final String keyWithoutAsterisk = StringUtils.remove(StringUtils.remove(key, "%"), "*");
//            Set<String> configKeyset = instance.getConfig().getMapConfigs().keySet();
//            List<String> keysToRemove = new ArrayList<String>();
//            if (key.startsWith("*") || key.startsWith("%")) {
//                for (Iterator iterator = configKeyset.iterator(); iterator.hasNext();) {
//                    String configName = (String) iterator.next();
//
//                    keysToRemove.addAll(instance.getReplicatedMap(configName).keySet().stream().filter(s -> s.toString().contains(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList())) ;
//                    keysToRemove.addAll(instance.getMap(configName).keySet().stream().filter(s -> s.toString().contains(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList())) ;
//                }
//            } else if (key.endsWith("*") || key.endsWith("%")) {
//                for (Iterator iterator = configKeyset.iterator(); iterator.hasNext();) {
//                    String configName = (String) iterator.next();
//
//                    keysToRemove.addAll(instance.getReplicatedMap(configName).keySet().stream().filter(s -> s.toString().startsWith(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList())) ;
//                    keysToRemove.addAll(instance.getMap(configName).keySet().stream().filter(s -> s.toString().startsWith(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList())) ;
//                }
//            } else {
//                for (Iterator iterator = configKeyset.iterator(); iterator.hasNext();) {
//                    String configName = (String) iterator.next();
//
//                    keysToRemove.addAll(instance.getReplicatedMap(configName).keySet().stream().filter(s -> s.toString().equals(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList())) ;
//                    keysToRemove.addAll(instance.getMap(configName).keySet().stream().filter(s -> s.toString().equals(keyWithoutAsterisk)).map(s -> s.toString()).collect(Collectors.toList())) ;
//                }
//            }
//
//            cnt = keysToRemove.size();
//            for (Iterator iterator = configKeyset.iterator(); iterator.hasNext();) {
//                String configName = (String) iterator.next();
//                ReplicatedMap<Object, Object> rMap = instance.getReplicatedMap(configName);
//                IMap<Object, Object> iMap = instance.getMap(configName);
//                for (String kk : keysToRemove) {
//                    rMap.remove(kk);
//                    iMap.remove(kk);
//                }
//            }
//        }
//
//        return ResponseEntity.ok("Evicted items count: " + cnt);
//    }


//    @ApiOperation(value = "Clear")
//    @GetMapping(value = {"/clear/all", "/clear/{bean}"})
//    public ResponseEntity<?> clear(@PathVariable(required = false) String bean) {
//        if (bean == null) {
//            Hazelcast.getAllHazelcastInstances().stream()
//                    .map(HazelcastInstance::getDistributedObjects)
//                    .forEach(data -> data.forEach(DistributedObject::destroy));
//        } else {
//            Object beanObject = SpringApplicationContext.getApplicationContext().getBean(bean);
//            if (beanObject instanceof HazelcastCacheStore) {
//                // Example: hzOneHourTTLBroadcast, hzOneHourTTLMaxSizedArtist
//                HazelcastCacheStore store = (HazelcastCacheStore)beanObject;
//                store.getReplicatedMap().clear();
//                store.clear();
//                beanObject = store.getHzInstance();
//            } else if (beanObject instanceof HazelcastCacheManager) {
//                // Example: hazelcastGenericCache
//                HazelcastCacheManager manager = (com.hazelcast.spring.cache.HazelcastCacheManager)beanObject;
//                manager.getCacheNames().forEach(name -> { manager.getCache(name).clear(); } );
//            } else if (beanObject instanceof HazelcastInstance) {
//                // Example: hzInstanceLocal, hzInstanceMicroservice, hzInstanceBroadcast
//                HazelcastInstance instance = (HazelcastInstance)beanObject;
//                instance.getDistributedObjects().forEach(o -> o.destroy());
//            }
//        }
//
//        return ResponseEntity.ok("OK");
//    }


}