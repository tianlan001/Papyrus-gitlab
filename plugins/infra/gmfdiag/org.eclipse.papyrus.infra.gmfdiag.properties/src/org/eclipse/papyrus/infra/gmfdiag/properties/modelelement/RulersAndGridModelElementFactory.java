/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 417409
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 491816
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.modelelement;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.gmfdiag.properties.Activator;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElementFactory;

/**
 *
 * @author Vincent Lorenzo
 *         The factory used to edit Rulers and Grid properties
 */
public class RulersAndGridModelElementFactory extends AbstractModelElementFactory<RulerAndGridModelElement> {

	@Override
	protected RulerAndGridModelElement doCreateFromSource(Object sourceElement, DataContextElement context) {
		if (sourceElement instanceof EditPart) {
			final IPreferenceStore preferenceStore = DiagramEditPartsUtil.getDiagramWorkspacePreferenceStore((EditPart) sourceElement);
			View view = NotationHelper.findView(DiagramEditPartsUtil.getDiagramEditPart((EditPart) sourceElement));
			if (preferenceStore != null && view instanceof Diagram) {
				EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(view);
				return new RulerAndGridModelElement((Diagram) view, domain, context, preferenceStore);
			}
		}

		Activator.log.warn("The selected element cannot be resolved to a Diagram View"); //$NON-NLS-1$
		return null;
	}

	@Override
	protected void updateModelElement(RulerAndGridModelElement modelElement, Object newSourceElement) {
		if (!(newSourceElement instanceof EditPart)) {
			throw new IllegalArgumentException("Cannot resolve EditPart selection: " + newSourceElement);
		}
		EditPart editPart = (EditPart) newSourceElement;
		modelElement.store = DiagramEditPartsUtil.getDiagramWorkspacePreferenceStore(editPart);
		modelElement.diagram = (Diagram) NotationHelper.findView(DiagramEditPartsUtil.getDiagramEditPart(editPart));
		modelElement.domain = EMFHelper.resolveEditingDomain(newSourceElement);
	}

}
