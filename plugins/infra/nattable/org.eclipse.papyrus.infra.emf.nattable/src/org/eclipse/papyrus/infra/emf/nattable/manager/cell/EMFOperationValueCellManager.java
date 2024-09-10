/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 465009 : Allow EOperation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515806
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.nattable.manager.cell;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;

/**
 *
 * Cell Manager which allows to get the value of an {@link EOperation} for an {@link EObject}
 *
 * @author Céline JANSSENS
 *
 */
public class EMFOperationValueCellManager extends AbstractCellManager {

	/**
	 * The not implemented message.
	 */
	protected final static String NOT_IMPLEMENTED_MESSAGE = " This Operation target is not implemented "; //$NON-NLS-1$

	/**
	 * The no parameter message.
	 */
	protected final static String NO_PARAMETER_MESSAGE = "N/A: This operation requires some parameters"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#handles(java.lang.Object, java.lang.Object, INattableModelManager)
	 *
	 */
	@Override
	public boolean handles(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		return organizeAndResolvedObjects(columnElement, rowElement, null) != null;

	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager#doGetValue(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 */
	@Override
	protected Object doGetValue(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		final List<Object> objects = organizeAndResolvedObjects(columnElement, rowElement, null);
		final EObject eobject = (EObject) objects.get(0);
		final EOperation operation = (EOperation) objects.get(1);
		if (eobject.eClass().getEAllOperations().contains(operation)) {
			try {
				if (operation.getEParameters().size() != 0) {
					return NO_PARAMETER_MESSAGE;
				} else {
					return eobject.eInvoke(operation, null);
				}
			} catch (InvocationTargetException e) {
				return NOT_IMPLEMENTED_MESSAGE;
			}
		}
		return CellHelper.getUnsupportedCellContentsText();
	}

	/**
	 *
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @param sharedMap
	 *            a map with interesting informations
	 * @return
	 * 		<code>null</code> or a list of 2 objects.
	 *         <ul>
	 *         <li>the first element is the edited EObject</li>
	 *         <li>the second one is the edited feature</li>
	 *         </ul>
	 */
	protected List<Object> organizeAndResolvedObjects(final Object columnElement, final Object rowElement, Map<?, ?> sharedMap) {
		List<Object> objects = null;
		final Object row = AxisUtils.getRepresentedElement(rowElement);
		final Object column = AxisUtils.getRepresentedElement(columnElement);
		if (row instanceof EObject && column instanceof EOperation) {
			objects = new ArrayList<Object>();
			objects.add(row);
			objects.add(column);
		} else if (column instanceof EObject && row instanceof EOperation) {
			objects = new ArrayList<Object>();
			objects.add(column);
			objects.add(row);
		}
		return objects;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#isCellEditable(java.lang.Object, java.lang.Object, INattableModelManager)
	 *
	 */
	@Override
	public boolean isCellEditable(final Object columnElement, final Object rowElement, INattableModelManager tableManager) {
		return false;
	}


}
