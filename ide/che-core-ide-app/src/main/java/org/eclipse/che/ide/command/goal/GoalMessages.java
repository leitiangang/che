/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.command.goal;

import com.google.gwt.i18n.client.Messages;

/**
 * I18n messages for the command goals.
 *
 * @author Artem Zatsarynnyi
 */
public interface GoalMessages extends Messages {

    @Key("goal.build.name")
    String goalBuildName();

    @Key("goal.common.name")
    String goalCommonName();

    @Key("goal.deploy.name")
    String goalDeployName();

    @Key("goal.run.name")
    String goalRunName();

    @Key("goal.test.name")
    String goalTestName();

    @Key("message.goal_already_registered")
    String messageGoalAlreadyRegistered(String id);
}
