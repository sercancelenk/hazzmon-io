package byzas.libs.hazzmon.core.model;

import com.hazelcast.cluster.ClusterState;
import com.hazelcast.core.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 19.05.2018
 */

@Getter
@Setter
@NoArgsConstructor
public class ClusterInfo {
    private ClusterState clusterState;
    private Version version;
    private Member localMember;
    private long clusterTime;
}
