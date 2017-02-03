package org.eclipse.che.api.system.server;

import org.eclipse.che.api.system.shared.event.service.ServiceItemStoppedEvent;
import org.eclipse.che.api.system.shared.event.service.StoppingSystemServiceEvent;
import org.eclipse.che.api.system.shared.event.service.SystemServiceStoppedEvent;

/**
 * Defines a termination process for a certain service.
 *
 * @author Yevhenii Voevodin
 */
public interface ServiceTermination {

    /**
     * Terminates a certain service.
     * It's expected that termination is synchronous.
     *
     * @throws InterruptedException
     *         as termination is synchronous some of the implementations
     *         may need to wait for asynchronous jobs to finish their execution,
     *         so if termination is interrupted and implementation supports termination
     *         it should throw an interrupted exception
     */
    void terminate() throws InterruptedException;

    /**
     * Returns the name of the service which is terminated by this termination.
     * The name is used for logging/sending events like {@link StoppingSystemServiceEvent},
     * {@link ServiceItemStoppedEvent} or {@link SystemServiceStoppedEvent}.
     */
    String getServiceName();
}
