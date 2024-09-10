/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.modelelement;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.gmfdiag.properties.Activator;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElementFactory;

/**
 * Model-element factory for synthetic synchronization properties of GMF notation views.
 */
public class SynchronizationModelElementFactory extends AbstractModelElementFactory<SynchronizationModelElement> {
	@Override
	protected SynchronizationModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		SynchronizationModelElement result = null;
		IGraphicalEditPart editPart = AdapterUtils.adapt(sourceElement, IGraphicalEditPart.class, null);

		if (editPart == null) {
			Activator.log.warn("The selected element cannot be resolved to a GEF EditPart");
		} else {
			result = new SynchronizationModelElement(editPart.getEditingDomain(), editPart);
		}

		return result;
	}

	@Override
	protected void updateModelElement(SynchronizationModelElement modelElement, Object newSourceElement) {
		IGraphicalEditPart editPart = AdapterUtils.adapt(newSourceElement, IGraphicalEditPart.class, null);
		if (editPart == null) {
			throw new IllegalArgumentException("Cannot resolve EditPart selection: " + newSourceElement);
		}

		modelElement.domain = editPart.getEditingDomain();
		modelElement.editPart = editPart;
	}
}
