package org.eclipse.che.api.system.shared.dto;

import org.eclipse.che.dto.shared.DTO;

/**
 * Base dto for system service events.
 *
 * @author Yevhenii Voevodin
 */
@DTO
public interface SystemServiceEventDto extends SystemEventDto {

    /**
     * Returns the name of the service related to the event.
     */
    String getService();

    void setService(String service);

    SystemServiceEventDto withService(String service);
}
