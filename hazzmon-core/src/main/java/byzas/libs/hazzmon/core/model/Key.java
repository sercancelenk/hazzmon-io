package byzas.libs.hazzmon.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 18.05.2018
 */


@Getter
@Setter
@NoArgsConstructor
public class Key implements Serializable {
    private String beanName;
    private String instanceName;
    private String mapName;
    private MapType mapType;
    private int timeToLive;
    private TimeUnit timeToLiveType;
    @JsonIgnore
    private Object value;
    private Object key;
    private boolean status;
    private Version version;
}
