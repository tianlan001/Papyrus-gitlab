/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.core.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.domain.services.IEditableChecker;

/**
 * Checker used to verify if an element is readOnly.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class EditableChecker implements IEditableChecker {

	@Override
	public boolean canEdit(EObject eObject) {
		EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
		boolean isReadOnly = (eObject.eResource() != null) && (editingDomain != null) && (editingDomain.isReadOnly(eObject.eResource()));
		return !isReadOnly;
	}

}
