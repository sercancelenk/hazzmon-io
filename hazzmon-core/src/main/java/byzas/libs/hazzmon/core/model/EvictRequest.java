package byzas.libs.hazzmon.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Created By Sercan CELENK (TCSECELENK) at 27.05.2018
 */

@Getter @Setter @NoArgsConstructor
public class EvictRequest implements Serializable {
    private String bean;
    private List<String> keys;
}
