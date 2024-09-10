/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.modelelement;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElementFactory;
import org.eclipse.papyrus.uml.diagram.common.Activator;

/**
 * A factory for handling the GMF Notation Activity elements.
 *
 * @since 3.2
 */
public class ActivityNotationModelElementFactory extends AbstractModelElementFactory<ActivityNotationModelElement> {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElementFactory#doCreateFromSource(java.lang.Object, org.eclipse.papyrus.infra.properties.contexts.DataContextElement)
	 */
	@Override
	protected ActivityNotationModelElement doCreateFromSource(final Object sourceElement, final DataContextElement context) {
		ActivityNotationModelElement result = null;
		final IGraphicalEditPart editPart = AdapterUtils.adapt(sourceElement, IGraphicalEditPart.class, null);

		if (editPart == null) {
			Activator.log.warn("The selected element cannot be resolved to a GEF EditPart"); //$NON-NLS-1$
		} else {
			result = new ActivityNotationModelElement(editPart.getEditingDomain(), editPart);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElementFactory#updateModelElement(org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement, java.lang.Object)
	 */
	@Override
	protected void updateModelElement(final ActivityNotationModelElement modelElement, final Object newSourceElement) {
		final IGraphicalEditPart editPart = AdapterUtils.adapt(newSourceElement, IGraphicalEditPart.class, null);
		if (editPart == null) {
			throw new IllegalArgumentException("Cannot resolve EditPart selection: " + newSourceElement); //$NON-NLS-1$
		}

		modelElement.domain = editPart.getEditingDomain();
		modelElement.editPart = editPart;
	}
}
