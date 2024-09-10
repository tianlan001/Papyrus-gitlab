/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.properties.databinding;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.AddCustomStyleListValueCommand;


public class AddCSSStyleSheetCommand extends AddCustomStyleListValueCommand implements Command {

	protected boolean createdEObject = false;

	public AddCSSStyleSheetCommand(EditingDomain domain, View view, String styleName, EClass styleClass, EStructuralFeature styleFeature, Object value) {
		super(domain, view, styleName, styleClass, styleFeature, value);
	}

	public AddCSSStyleSheetCommand(EditingDomain domain, View view, String styleName, EClass styleClass, EStructuralFeature styleFeature, Object value, int index) {
		super(domain, view, styleName, styleClass, styleFeature, value, index);
	}

	@Override
	public void execute() {
		if (value instanceof EObject) {
			EObject eObject = (EObject) value;
			if (eObject.eResource() == null) {
				view.eResource().getContents().add(eObject);
				createdEObject = true;
			}
		}

		super.execute();
	}

	@Override
	public void undo() {
		if (value instanceof EObject) {
			EObject eObject = (EObject) value;
			if (createdEObject) {
				eObject.eResource().getContents().remove(eObject);
			}
		}
		super.undo();
	}



}
