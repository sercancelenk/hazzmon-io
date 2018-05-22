package byzas.libs.hazzmon.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 17.05.2018
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapInfo {
    private String mapName;
    private MapType mapType;
    private int timeToLive;
    private TimeUnit timeToLiveType;
    private List<Object> keys;
}
