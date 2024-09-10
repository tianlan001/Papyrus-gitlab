/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.service;

import java.awt.datatransfer.Clipboard;
import java.util.Collection;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;

/**
 * this singleton looks for in extension point to find a pasteCommandProvider
 * and can provide a paste command
 * 
 * @deprecated Use the {@link org.eclipse.papyrus.infra.gmfdiag.common.service.PasteCommandService} API, instead.
 */
@Deprecated
public class PasteCommandService {

	private static PasteCommandService instance = null;

	private org.eclipse.papyrus.infra.gmfdiag.common.service.PasteCommandService delegate = org.eclipse.papyrus.infra.gmfdiag.common.service.PasteCommandService.getInstance();

	public static PasteCommandService getInstance() {
		if (instance == null) {
			instance = new PasteCommandService();
		}
		return instance;
	}

	/**
	 * return the paste command to execute by taking account parameter
	 *
	 * @param targetEditPart
	 *            the target where object will be paste
	 * @param systemClipboard
	 *            contains info form the system copy paste
	 * @param papyrusCliboard
	 *            the list of views to paste
	 * @return a command
	 */
	public ICommand getPasteViewCommand(GraphicalEditPart targetEditPart, Clipboard systemClipboard, Collection<Object> papyrusCliboard) {
		return delegate.getPasteViewCommand(targetEditPart, systemClipboard, papyrusCliboard);
	}

	/**
	 * return the paste command to execute by taking account parameter. It copy also element of the semantic model
	 *
	 * @param targetEditPart
	 *            the target where object will be paste
	 * @param systemClipboard
	 *            contains info form the system copy paste
	 * @param papyrusCliboard
	 *            the list of views to paste
	 * @return a command
	 */
	public ICommand getPasteWithModelCommand(GraphicalEditPart targetEditPart, Clipboard systemClipboard, Collection<Object> papyrusCliboard) {
		return delegate.getPasteWithModelCommand(targetEditPart, systemClipboard, papyrusCliboard);
	}
}
