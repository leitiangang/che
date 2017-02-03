package org.eclipse.che.api.system.shared.event.service;

import org.eclipse.che.api.system.shared.event.EventType;

/**
 * @author Yevhenii Voevodin
 */
public class StoppingSystemServiceEvent extends SystemServiceEvent {

    public StoppingSystemServiceEvent(String serviceName) {
        super(serviceName);
    }

    @Override
    public EventType getType() {
        return EventType.STOPPING_SERVICE;
    }
}
