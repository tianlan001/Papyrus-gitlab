/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.mouse.action;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.swt.events.MouseEvent;

/**
 * The action to destroy the axis element
 * 
 * @since 6.7
 */
public class DestroyAxisElementCellMouseAction extends AbstractCellMouseAction {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.DestroyAxisElementCellEditorAxisConfiguration.AbstractCellMouseAction#doRun(org.eclipse.nebula.widgets.nattable.NatTable, org.eclipse.swt.events.MouseEvent, java.lang.Object, java.lang.Object)
	 *
	 * @param natTable
	 * @param event
	 * @param rowElement
	 * @param columnElement
	 */
	@Override
	public void doRun(final NatTable natTable, final MouseEvent event, final Object rowElement, final Object columnElement) {
		final Object realRow = AxisUtils.getRepresentedElement(rowElement);
		final Object realColumn = AxisUtils.getRepresentedElement(columnElement);
		EObject toDestroy = null;
		if (realColumn instanceof String && realRow instanceof EObject) {
			toDestroy = (EObject) realRow;
		}
		if (realRow instanceof String && realColumn instanceof EObject) {
			toDestroy = (EObject) realColumn;
		}
		if (toDestroy != null) {
			final DestroyElementRequest request = new DestroyElementRequest(toDestroy, false);
			final IElementEditService commandProvider = ElementEditServiceUtils.getCommandProvider(toDestroy);
			final ICommand cmd = commandProvider.getEditCommand(request);
			request.getEditingDomain().getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(cmd));
		}
	}
}