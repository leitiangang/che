package org.eclipse.che.api.system.server;

import org.eclipse.che.api.core.model.workspace.WorkspaceStatus;
import org.eclipse.che.api.core.notification.EventService;
import org.eclipse.che.api.core.notification.EventSubscriber;
import org.eclipse.che.api.system.shared.event.service.ServiceItemStoppedEvent;
import org.eclipse.che.api.system.shared.event.service.SystemServiceStoppedEvent;
import org.eclipse.che.api.workspace.server.WorkspaceManager;
import org.eclipse.che.api.workspace.shared.dto.event.WorkspaceStatusEvent;

import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Terminates workspace service.
 *
 * @author Yevhenii Voevodin
 */
class WorkspaceServiceTermination implements ServiceTermination {

    @Inject
    private WorkspaceManager workspaceManager;

    @Inject
    private EventService eventService;

    @Override
    public void terminate() throws InterruptedException {
        EventSubscriber propagator = new WorkspaceStoppedEventsPropagator();
        eventService.subscribe(propagator);
        try {
            workspaceManager.shutdown();
        } finally {
            eventService.unsubscribe(propagator);
        }
    }

    @Override
    public String getServiceName() {
        return "workspace";
    }

    /**
     * Propagates workspace stopped events as {@link SystemServiceStoppedEvent} events.
     */
    private class WorkspaceStoppedEventsPropagator implements EventSubscriber<WorkspaceStatusEvent> {

        private final int           totalRunning;
        private final AtomicInteger currentlyStopped;

        private WorkspaceStoppedEventsPropagator() {
            this.totalRunning = workspaceManager.getRunningWorkspacesIds().size();
            this.currentlyStopped = new AtomicInteger(0);
        }

        @Override
        public void onEvent(WorkspaceStatusEvent event) {
            if (event.getStatus() == WorkspaceStatus.STOPPED) {
                eventService.publish(new ServiceItemStoppedEvent(getServiceName(),
                                                                 event.getWorkspaceId(),
                                                                 currentlyStopped.incrementAndGet(),
                                                                 totalRunning));
            }
        }
    }
}
