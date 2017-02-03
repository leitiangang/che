package org.eclipse.che.api.system.shared.event.service;

import org.eclipse.che.api.system.shared.event.EventType;
import org.eclipse.che.commons.annotation.Nullable;

import java.util.Objects;

/**
 * Published when system service item(like workspace) is stopped.
 *
 * @author Yevhenii Voevodin
 * @see EventType#SERVICE_ITEM_STOPPED
 */
public class ServiceItemStoppedEvent extends SystemServiceEvent {

    private final String item;

    private Integer total;
    private Integer current;

    public ServiceItemStoppedEvent(String serviceName, String item) {
        super(serviceName);
        this.item = Objects.requireNonNull(item, "Item required");
    }

    public ServiceItemStoppedEvent(String serviceName,
                                   String item,
                                   @Nullable Integer current,
                                   @Nullable Integer total) {
        this(serviceName, item);
        this.current = current;
        this.total = total;
    }

    @Override
    public EventType getType() {
        return EventType.SERVICE_ITEM_STOPPED;
    }

    public String getItem() {
        return item;
    }

    @Nullable
    public Integer getTotal() {
        return total;
    }

    @Nullable
    public Integer getCurrent() {
        return current;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ServiceItemStoppedEvent)) {
            return false;
        }
        final ServiceItemStoppedEvent that = (ServiceItemStoppedEvent)obj;
        return super.equals(that)
               && item.equals(that.item)
               && Objects.equals(total, that.total)
               && Objects.equals(current, that.current);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + super.hashCode();
        hash = 31 * hash + item.hashCode();
        hash = 31 * hash + Objects.hashCode(total);
        hash = 31 * hash + Objects.hashCode(current);
        return hash;
    }

    @Override
    public String toString() {
        return "ServiceItemStoppedEvent{" +
               "item='" + item + '\'' +
               ", total=" + total +
               ", current=" + current +
               "} " + super.toString();
    }
}
