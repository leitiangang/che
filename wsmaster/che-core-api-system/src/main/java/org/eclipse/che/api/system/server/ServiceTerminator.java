package org.eclipse.che.api.system.server;

import org.eclipse.che.api.core.model.workspace.WorkspaceStatus;
import org.eclipse.che.api.core.notification.EventService;
import org.eclipse.che.api.core.notification.EventSubscriber;
import org.eclipse.che.api.system.shared.event.service.ServiceItemStoppedEvent;
import org.eclipse.che.api.system.shared.event.service.StoppingSystemServiceEvent;
import org.eclipse.che.api.system.shared.event.service.SystemServiceStoppedEvent;
import org.eclipse.che.api.workspace.server.WorkspaceManager;
import org.eclipse.che.api.workspace.shared.dto.event.WorkspaceStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Terminates system services.
 *
 * @author Yevhenii Voevodin
 */
public class ServiceTerminator {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceTerminator.class);

    @Inject
    private EventService eventService;

    @Inject
    private WorkspaceManager workspaceManager;

    /**
     * Terminates system services.
     *
     * @throws InterruptedException
     *         when termination is interrupted
     */
    public void terminateAll() throws InterruptedException {
        List<ServiceTermination> terminations = new ArrayList<>();
        terminations.add(new WorkspaceServiceTermination());

        for (ServiceTermination termination : terminations) {
            LOG.info("Shutting down '{}' service", termination.getServiceName());
            eventService.publish(new StoppingSystemServiceEvent(termination.getServiceName()));
            try {
                termination.terminate();
            } catch (InterruptedException x) {
                LOG.error("Interrupted while waiting for '{}' service to shutdown", termination.getServiceName());
                throw x;
            }
            eventService.publish(new SystemServiceStoppedEvent(termination.getServiceName()));
        }
    }

    /** Implements termination flow for workspace service. */
    private class WorkspaceServiceTermination implements ServiceTermination {

        @Override
        public void terminate() throws InterruptedException {
            int totalRunning = workspaceManager.getRunningWorkspacesIds().size();
            EventSubscriber propagator = new WorkspaceStoppedEventsPropagator(getServiceName(), totalRunning);
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
    }

    /**
     * Propagates workspace stopped events as {@link SystemServiceStoppedEvent} events.
     */
    private class WorkspaceStoppedEventsPropagator implements EventSubscriber<WorkspaceStatusEvent> {

        private final int           totalRunning;
        private final String        serviceName;
        private final AtomicInteger currentlyStopped;

        private WorkspaceStoppedEventsPropagator(String serviceName, int totalRunning) {
            this.totalRunning = totalRunning;
            this.currentlyStopped = new AtomicInteger(0);
            this.serviceName = serviceName;
        }

        @Override
        public void onEvent(WorkspaceStatusEvent event) {
            if (event.getStatus() == WorkspaceStatus.STOPPED) {
                eventService.publish(new ServiceItemStoppedEvent(serviceName,
                                                                 event.getWorkspaceId(),
                                                                 currentlyStopped.incrementAndGet(),
                                                                 totalRunning));
            }
        }
    }
}
