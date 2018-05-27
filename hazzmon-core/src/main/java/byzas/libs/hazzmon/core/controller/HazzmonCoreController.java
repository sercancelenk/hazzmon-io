package byzas.libs.hazzmon.core.controller;

import byzas.libs.hazzmon.core.model.EvictRequest;
import byzas.libs.hazzmon.core.service.HazzmonCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<?> getDetailOfKey(@PathVariable String keyName) {
        return ResponseEntity.ok(hazelcastCacheInfoService.getDetailOfKey(keyName));
    }

    @GetMapping(value = "/value/key/{keyName}")
    public ResponseEntity<?> getValueOfKey(@PathVariable String keyName) {
        Map<String, Object> result = new HashMap<>();
        result.put("value", hazelcastCacheInfoService.getValueOfKey(keyName));
        return ResponseEntity.ok(result);
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

    @PostMapping(value = "/evict")
    public ResponseEntity<?> evict(@RequestBody EvictRequest request) {
        return ResponseEntity.ok(hazelcastCacheInfoService.evictKeyByInstance(request));
    }

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