package byzas.libs.hazzmon.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 24.05.2018
 */

@Getter
@Setter
@NoArgsConstructor
public class Version implements Serializable {
    private byte major;
    private byte minor;
    private int factoryId;
    private int id;
}
