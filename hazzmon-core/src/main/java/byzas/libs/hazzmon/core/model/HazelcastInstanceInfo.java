package byzas.libs.hazzmon.core.model;

import com.hazelcast.config.AwsConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.spi.discovery.DiscoveryStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private boolean discoveryMulticast;
    private boolean discoveryTcpIp;
    private boolean discoveryAws;
    private MulticastConfig multicastConfig;
    private TcpIpConfig tcpIpConfig;
    private AwsConfig awsConfig;
}
