package byzas.libs.hazzmon.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 28.05.2018
 */

@Getter @Setter @NoArgsConstructor
public class MemberInfo implements Serializable {
    private String inetAddress;
    private String inetSocketAddress;
    private String socketAddress;
    private String uuid;
    private String version;
    private String instanceName;
}
