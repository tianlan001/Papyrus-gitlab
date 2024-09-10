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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.manager.cell;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFOperationValueCellManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Cell Manager which allows to get the value of an UML {@link EOperation} for an {@link EObject}.
 * 
 * @since 3.0
 */
public class UMLOperationValueCellManager extends EMFOperationValueCellManager {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFOperationValueCellManager#doGetValue(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 */
	@Override
	protected Object doGetValue(final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		Object result = null;

		final List<Object> objects = organizeAndResolvedObjects(columnElement, rowElement, null);
		final EObject eobject = (EObject) objects.get(0);
		final EOperation operation = (EOperation) objects.get(1);

		// If the operation called if the 'getLabel' from UML, call the papyrus internationalization 'getLabel'
		if (eobject instanceof NamedElement && operation.equals(UMLPackage.eINSTANCE.getNamedElement__GetLabel())) {
			result = UMLLabelInternationalization.getInstance().getLabel((NamedElement) eobject);
			// If the operation called if the 'getKeyword' from UML, call the papyrus internationalization 'getKeyword'
		} else if (eobject instanceof Stereotype && operation.equals(UMLPackage.eINSTANCE.getStereotype__GetKeyword())) {
			result = UMLLabelInternationalization.getInstance().getKeyword((Stereotype) eobject);
		} else {
			result = super.doGetValue(columnElement, rowElement, tableManager);
		}
		return result;
	}

}
