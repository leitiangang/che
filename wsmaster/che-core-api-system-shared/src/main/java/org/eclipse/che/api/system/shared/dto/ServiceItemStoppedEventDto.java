package org.eclipse.che.api.system.shared.dto;

import org.eclipse.che.api.system.shared.event.EventType;
import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.dto.shared.DTO;

/**
 * See {@link EventType#SERVICE_ITEM_STOPPED} for details.
 *
 * @author Yevhenii Voevodin
 */
@DTO
public interface ServiceItemStoppedEventDto extends SystemServiceEventDto {

    /**
     * Returns an item for which this event is published(like workspace id).
     */
    String getItem();

    void setItem(String item);

    ServiceItemStoppedEventDto withItem(String item);

    /**
     * Returns the index of currently stopped item or an amount of
     * items currently running, it's either present with {@link #getTotal()}
     * or missing at all(null is returned).
     */
    @Nullable
    Integer getCurrent();

    void setCurrent(Integer current);

    ServiceItemStoppedEventDto withCurrent(Integer current);

    /**
     * Returns total count of items which had not been stopped before
     * service shutdown was called. It's either present with {@link #getCurrent()}
     * or missing at all(null is returned).
     */
    @Nullable
    Integer getTotal();

    void setTotal(Integer total);

    ServiceItemStoppedEventDto withTotal(Integer total);
}
