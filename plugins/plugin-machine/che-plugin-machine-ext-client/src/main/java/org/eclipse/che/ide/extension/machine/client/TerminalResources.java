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
package org.eclipse.che.ide.extension.machine.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * @author Alexander Andrienko
 */
public interface TerminalResources extends ClientBundle {

    /** Returns the CSS resource for the Terminal. Terminal css for this resource adds in build process from external module. */
    @CssResource.NotStrict
    @Source({"org/eclipse/che/ide/extension/machine/public/term/xterm.css"})
    TerminalCss getCss();

    interface TerminalCss extends CssResource {
    }
}