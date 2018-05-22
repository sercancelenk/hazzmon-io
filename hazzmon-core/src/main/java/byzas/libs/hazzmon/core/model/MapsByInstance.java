package byzas.libs.hazzmon.core.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 17.05.2018
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapsByInstance {
    private String instanceName;
    @Getter(AccessLevel.NONE)
    private List<MapInfo> maps;

    public List<MapInfo> getMaps() {
        if (Objects.isNull(maps)) maps = new ArrayList<>();
        return maps;
    }
}
