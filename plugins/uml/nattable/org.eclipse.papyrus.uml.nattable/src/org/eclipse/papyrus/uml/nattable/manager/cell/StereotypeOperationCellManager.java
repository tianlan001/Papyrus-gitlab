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

package org.eclipse.papyrus.uml.nattable.manager.cell;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFOperationValueCellManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;
import org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter;
import org.eclipse.papyrus.uml.nattable.Activator;
import org.eclipse.papyrus.uml.nattable.utils.StereotypeOperationUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Cell Manager which allows to get the value of an operation provided by a stereotype for a stereotyped element.
 *
 * @since 5.4
 *
 */
public class StereotypeOperationCellManager extends EMFOperationValueCellManager {

	/**
	 * the error message when several stereotypes with the same operation are applied
	 * TODO : externalize me in Papyrus 5.0 (bug 561258)
	 */
	public static final String SEVERAL_STEREOTYPES_WITH_THIS_OPERATION_ARE_APPLIED = "Several stereotypes with this operation are applied -> not yet managed"; //$NON-NLS-1$

	/**
	 * the error message when the called operation is not implemented
	 * TODO : externalize me in Papyrus 5.0 (bug 561258)
	 */
	private static final String OPERATION_NOT_IMPLEMENTED = "Not Implemented";

	/**
	 *
	 * @see org.eclipse.papyrus.uml.nattable.manager.cell.UMLFeatureCellManager#handles(java.lang.Object, java.lang.Object, INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @return
	 */
	@Override
	public boolean handles(final Object columnElement, final Object rowElement, INattableModelManager tableManager) {
		return organizeAndResolvedObjects(columnElement, rowElement, null) != null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager#doGetValue(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @param tableManager
	 * @return
	 */
	@Override
	protected Object doGetValue(final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		final List<Object> umlObjects = organizeAndResolvedObjects(columnElement, rowElement, null);
		if (umlObjects.size() == 2) {
			final Element el = (Element) umlObjects.get(0);
			final String id = (String) umlObjects.get(1);
			final Operation operation = (Operation) StereotypeOperationUtils.getRealStereotypeOperation(el, id);
			final List<Stereotype> stereotypesWithThisOperation = StereotypeOperationUtils.getAppliedStereotypesWithThisOperation(el, id);
			if (stereotypesWithThisOperation.size() == 1) {
				final EObject steAppl = el.getStereotypeApplication(stereotypesWithThisOperation.get(0));
				final Collection<EOperation> operations = steAppl.eClass().getEAllOperations();
				EOperation eOp = null;
				while (eOp == null) {
					for (final EOperation current : operations) {
						if (current.getName().equalsIgnoreCase(operation.getName())) {
							eOp = current;
						}

					}
				}

				try {
					Object result = steAppl.eInvoke(eOp, ECollections.emptyEList());
					return result;
				} catch (UnsupportedOperationException e) {
					return OPERATION_NOT_IMPLEMENTED;
				} catch (InvocationTargetException e) {
					Activator.log.error(e);
				}
			} else if (stereotypesWithThisOperation.size() > 1) {
				return SEVERAL_STEREOTYPES_WITH_THIS_OPERATION_ARE_APPLIED;
			}
		}
		return CellHelper.getUnsupportedCellContentsText();
	}

	/**
	 *
	 * @param columnElement
	 * @param rowElement
	 * @return
	 *         a list with 2 elements : the first one is the Element and the second one the string representing the property of stereotypes
	 */
	@Override
	protected List<Object> organizeAndResolvedObjects(final Object columnElement, final Object rowElement, final Map<?, ?> sharedMap) {
		List<Object> objects = null;
		final Object column = AxisUtils.getRepresentedElement(columnElement);
		final Object row = AxisUtils.getRepresentedElement(rowElement);
		if (column instanceof String && ((String) column).startsWith(StereotypeOperationUtils.OPERATION_OF_STEREOTYPE_PREFIX) && row instanceof Element) {
			objects = new ArrayList<>();
			objects.add(row);
			objects.add(column);
		}
		if (row instanceof String && ((String) row).startsWith(StereotypeOperationUtils.OPERATION_OF_STEREOTYPE_PREFIX) && column instanceof Element) {
			objects = new ArrayList<>();
			objects.add(column);
			objects.add(row);
		}
		return objects;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.uml.nattable.manager.cell.UMLFeatureCellManager#isCellEditable(java.lang.Object, java.lang.Object, INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @return
	 */
	@Override
	public boolean isCellEditable(final Object columnElement, final Object rowElement, INattableModelManager tableManager) {
		return false;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager#isCellEditable(java.lang.Object, java.lang.Object, java.util.Map, INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @param sharedMap
	 * @return
	 */
	@Override
	public boolean isCellEditable(final Object columnElement, final Object rowElement, final Map<?, ?> sharedMap, INattableModelManager tableManager) {
		return false;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager#getSetValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param newValue
	 * @param tableManager
	 * @return
	 */
	@Override
	public Command getSetValueCommand(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final Object newValue, final INattableModelManager tableManager) {
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager#getSetStringValueCommand(org.eclipse.emf.edit.domain.EditingDomain, EObject, java.lang.Object, java.lang.Object, java.lang.String, java.util.Map)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param newValue
	 * @param stringResolvers
	 * @return
	 */
	@Override
	public Command getSetStringValueCommand(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final String newValue, final AbstractStringValueConverter valueSolver, final INattableModelManager tableManager) {
		return null;
	}

	/**
	 * This method is used when we are pasting elements in detached mode
	 *
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @param valueAsString
	 *            the value as string
	 * @param valueConverter
	 *            the value converter to use
	 * @param sharedMap
	 *            a map with shared elements. The method may read/add elements to the shared map. These contributions will be managed by a paste post
	 *            action or by the paste manager itself
	 * @param tableManager
	 *            the table manager
	 */
	@Override
	public void setStringValue(Object columnElement, Object rowElement, String valueAsString, AbstractStringValueConverter valueSolver, Map<?, ?> sharedMap, INattableModelManager tableManager) {

	}


	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager#getUnsetCellValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param tableManager
	 * @return
	 */
	@Override
	public Command getUnsetCellValueCommand(TransactionalEditingDomain domain, Object columnElement, Object rowElement, INattableModelManager tableManager) {
		return null;
	}

}
