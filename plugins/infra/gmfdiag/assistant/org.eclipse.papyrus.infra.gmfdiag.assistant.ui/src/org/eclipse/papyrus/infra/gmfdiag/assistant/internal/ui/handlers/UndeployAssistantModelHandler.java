/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.assistant.internal.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.ModelingAssistantModelRegistry;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Command handler that deactivates the assistant model in a workspace resource.
 */
public class UndeployAssistantModelHandler extends AbstractHandler {

	/**
	 * Constructor.
	 *
	 */
	public UndeployAssistantModelHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			for (Object next : ((IStructuredSelection) selection).toList()) {
				IFile file = AdapterUtils.adapt(next, IFile.class, null);
				if (file != null) {
					ModelingAssistantModelRegistry.getInstance().deregisterWorkspaceAssistantModel(URI.createPlatformResourceURI(file.getFullPath().toString(), true));
				}
			}
		}
		return null;
	}

}
