/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation - bug 465297
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.properties.modelelement;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.gmfdiag.properties.Activator;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractEMFModelElementFactory;

/**
 * A factory for creating {@link AdvanceStyleModelElementFactory} objects. Use for advance property view of appearance.
 */
public class AdvanceStyleModelElementFactory extends AbstractEMFModelElementFactory<AdvanceStyleModelElement> {

	/**
	 * Do create from source.
	 *
	 * @param sourceElement
	 *            the source element
	 * @param context
	 *            the context
	 * @return the Advance Style ModelElement
	 * 
	 * @see org.eclipse.papyrus.infra.properties.modelelement.AbstractModelElementFactory#doCreateFromSource(java.lang.Object, org.eclipse.papyrus.infra.properties.contexts.DataContextElement)
	 */
	@Override
	protected AdvanceStyleModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		View view = NotationHelper.findView(sourceElement);
		AdvanceStyleModelElement customStyleModelElement = null;

		if (null != view) {
			EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(view);
			customStyleModelElement = new AdvanceStyleModelElement(view, domain, context);
		} else {
			Activator.log.warn("The selected element cannot be resolved to a View");//$NON-NLS-1$
		}
		return customStyleModelElement;
	}

	/**
	 * Update model element.
	 *
	 * @param modelElement
	 *            the model element
	 * @param newSourceElement
	 *            the new source element
	 * @see org.eclipse.papyrus.infra.properties.modelelement.AbstractEMFModelElementFactory#updateModelElement(org.eclipse.papyrus.infra.properties.modelelement.EMFModelElement, java.lang.Object)
	 */
	@Override
	protected void updateModelElement(AdvanceStyleModelElement modelElement, Object newSourceElement) {
		View view = NotationHelper.findView(newSourceElement);
		if (view == null) {
			throw new IllegalArgumentException("Cannot resolve notation view selection: " + newSourceElement);//$NON-NLS-1$
		}

		updateEMFModelElement(modelElement, view);
		modelElement.view = view;
	}

}
