/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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

package org.eclipse.papyrus.uml.nattable.stereotype.display.label.provider;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFOperationValueCellManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.uml.tools.utils.StereotypeUtil;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The operation value cell manager refined to avoid the non-valid stereotype property.
 */
public class StereotypeDisplayEMFOperationValueCellManager extends EMFOperationValueCellManager {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFOperationValueCellManager#doGetValue(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 */
	@Override
	protected Object doGetValue(final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		final List<Object> objects = organizeAndResolvedObjects(columnElement, rowElement, null);
		final EObject eobject = (EObject) objects.get(0);
		final EOperation operation = (EOperation) objects.get(1);
		if (operation.equals(UMLPackage.Literals.CLASSIFIER___GET_ALL_ATTRIBUTES)) {
			if (eobject.eClass().getEAllOperations().contains(operation)) {
				try {
					if (operation.getEParameters().size() != 0) {
						return NO_PARAMETER_MESSAGE;
					} else {
						Object object = eobject.eInvoke(operation, null);

						// Avoid the non-valid stereotype properties
						if (object instanceof List<?>) {
							List<Object> items = new ArrayList<Object>(((List<?>) object).size());
							for (Object item : (List<?>) object) {
								if (item instanceof Property && StereotypeUtil.isValidStereotypeProperty((Property) item)) {
									items.add(item);
								}
							}
							return items;
						}
						return object;
					}
				} catch (InvocationTargetException e) {
					return NOT_IMPLEMENTED_MESSAGE;
				}
			}
		}
		return super.doGetValue(columnElement, rowElement, tableManager);
	}

}
