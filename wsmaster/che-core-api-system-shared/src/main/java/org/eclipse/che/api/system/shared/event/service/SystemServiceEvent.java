package org.eclipse.che.api.system.shared.event.service;

import org.eclipse.che.api.system.shared.event.SystemEvent;

import java.util.Objects;

/**
 * @author Yevhenii Voevodin
 */
public abstract class SystemServiceEvent implements SystemEvent {

    protected final String serviceName;

    protected SystemServiceEvent(String serviceName) {
        this.serviceName = Objects.requireNonNull(serviceName, "Service name required");
    }

    public String getServiceName() {
        return serviceName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SystemServiceEvent)) {
            return false;
        }
        final SystemServiceEvent that = (SystemServiceEvent)obj;
        return Objects.equals(getType(), that.getType())
               && Objects.equals(serviceName, that.serviceName);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + Objects.hashCode(getType());
        hash = 31 * hash + Objects.hashCode(serviceName);
        return hash;
    }

    @Override
    public String toString() {
        return getClass() + "{evenType='" + getType() + "', serviceName='" + serviceName + "'}";
    }
}
