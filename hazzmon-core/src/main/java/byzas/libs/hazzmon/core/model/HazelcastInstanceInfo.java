package byzas.libs.hazzmon.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 19.05.2018
 */

@Getter
@Setter
@NoArgsConstructor
public class HazelcastInstanceInfo {
    private String instanceName;
    private String beanName;
    private int totalDistributedMapCount;
    private int totalReplicatedMapCount;
    private int totalKeyCountInDistributedMaps;
    private int totalKeyCountInReplicatedMaps;
}
