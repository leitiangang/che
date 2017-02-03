package org.eclipse.che.api.system.shared.event.service;

import org.eclipse.che.api.system.shared.event.EventType;

/**
 * @author Yevhenii Voevodin
 */
public class SystemServiceStoppedEvent extends SystemServiceEvent {

    public SystemServiceStoppedEvent(String serviceName) {
        super(serviceName);
    }

    @Override
    public EventType getType() {
        return EventType.SERVICE_STOPPED;
    }
}
