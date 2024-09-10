/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST and others.
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
 *   Vincent Lorenzo (CEA-LIST) - vincent.lorenzo@cea.fr - bug 517731
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.nattable.manager.axis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;

/**
 * @author Céline JANSSENS
 *
 */
public class EOperationAxisManager extends EObjectAxisManager {
	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#canBeSavedAsConfig()
	 *
	 * @return
	 */
	@Override
	public boolean canBeSavedAsConfig() {
		return true;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#canDestroyAxisElement(java.lang.Integer)
	 *
	 * @param axisPosition
	 * @return
	 */
	@Override
	public boolean canDestroyAxisElement(Integer axisPosition) {
		return false;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#canEditAxisHeader()
	 *
	 * @return
	 */
	@Override
	public boolean canEditAxisHeader() {
		return true;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getAddAxisCommand(TransactionalEditingDomain, java.util.Collection)
	 *
	 * @param domain
	 * @param objectToAdd
	 * @return
	 */
	@Override
	public Command getAddAxisCommand(final TransactionalEditingDomain domain, final Collection<Object> objectToAdd) {
		final Collection<IAxis> toAdd = getAxisToAdd(objectToAdd);
		if (!toAdd.isEmpty()) {
			return new AddCommandWrapper(AddCommand.create(domain, getRepresentedContentProvider(), NattableaxisproviderPackage.eINSTANCE.getAxisProvider_Axis(), toAdd), objectToAdd);
		}
		return null;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.EObjectAxisManager#getAddAxisCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.util.Collection, int)
	 *
	 * @param domain
	 * @param objectToAdd
	 * @param index
	 * @return
	 */
	@Override
	public Command getAddAxisCommand(final TransactionalEditingDomain domain, final Collection<Object> objectToAdd, final int index) {
		final Collection<IAxis> toAdd = getAxisToAdd(objectToAdd);
		if (!toAdd.isEmpty()) {
			return new AddCommandWrapper(AddCommand.create(domain, getRepresentedContentProvider(), NattableaxisproviderPackage.eINSTANCE.getAxisProvider_Axis(), toAdd, index), objectToAdd);
		}
		return null;
	}

	/**
	 * Get the axis to add from the objects to add.
	 *
	 * @param objectToAdd
	 *            The objects to add.
	 * @return The axis to add.
	 */
	@Override
	protected Collection<IAxis> getAxisToAdd(final Collection<Object> objectToAdd) {
		final Collection<IAxis> toAdd = new ArrayList<>();
		for (final Object current : objectToAdd) {
			if (isAllowedContents(current) && !isAlreadyManaged(current)) {
				final EOperationAxis newAxis = NattableaxisFactory.eINSTANCE.createEOperationAxis();
				newAxis.setElement((EOperation) current);
				newAxis.setManager(this.representedAxisManager);
				toAdd.add(newAxis);
			}
		}
		return toAdd;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getComplementaryAddAxisCommand(TransactionalEditingDomain, java.util.Collection)
	 *
	 * @param domain
	 * @param objectToAdd
	 * @return
	 */
	@Override
	public Command getComplementaryAddAxisCommand(final TransactionalEditingDomain domain, final Collection<Object> objectToAdd) {
		final Set<Object> operations = getOperationsToAdd(objectToAdd);
		if (!operations.isEmpty()) {
			return getAddAxisCommand(domain, operations);
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getComplementaryAddAxisCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.util.Collection, int)
	 *
	 * @param domain
	 * @param objectToAdd
	 * @param index
	 * @return
	 */
	@Override
	public Command getComplementaryAddAxisCommand(final TransactionalEditingDomain domain, final Collection<Object> objectToAdd, final int index) {
		final Set<Object> operations = getOperationsToAdd(objectToAdd);
		if (!operations.isEmpty()) {
			return getAddAxisCommand(domain, operations, index);
		}
		return null;
	}

	/**
	 * Get the operations to add.
	 *
	 * @param objectToAdd
	 *            The initial objects to add.
	 * @return The operations to add.
	 */
	protected Set<Object> getOperationsToAdd(final Collection<Object> objectToAdd) {
		final Set<Object> operations = new HashSet<>();
		for (final Object current : objectToAdd) {
			if (current instanceof EObject) {
				operations.addAll(((EObject) current).eClass().getEAllOperations());
			}
		}
		operations.removeAll(getElements());
		removeOperations(operations);
		return operations;
	}


	/**
	 * This allows to remove the needed EOperation.
	 *
	 * @param objects
	 *            The list of objects
	 * @since 5.1
	 */
	protected void removeOperations(final Collection<?> objects) {
		removeVoidOperations(objects);
		removeOperationsWithParameters(objects);
	}

	/**
	 * This allows to remove the void EOperation.
	 *
	 * @param objects
	 *            The list of objects
	 */
	protected void removeVoidOperations(final Collection<?> objects) {
		Iterator<?> objectsIterator = objects.iterator();
		while (objectsIterator.hasNext()) {
			Object currentObject = objectsIterator.next();

			if (currentObject instanceof EOperation && null == ((EOperation) currentObject).getEType()) {
				objectsIterator.remove();
			}
		}
	}

	/**
	 * This allows to remove the EOperation with parameters.
	 *
	 * @param objects
	 *            The list of objects
	 * @since 5.1
	 */
	protected void removeOperationsWithParameters(final Collection<?> objects) {
		Iterator<?> objectsIterator = objects.iterator();
		while (objectsIterator.hasNext()) {
			Object currentObject = objectsIterator.next();

			if (currentObject instanceof EOperation && !((EOperation) currentObject).getEParameters().isEmpty()) {
				objectsIterator.remove();
			}
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#getDestroyAxisElementCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Integer)
	 *
	 * @param domain
	 * @param axisPosition
	 * @return
	 */
	@Override
	public Command getDestroyAxisElementCommand(final TransactionalEditingDomain domain, final Integer axisPosition) {
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getElementAxisName(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis)
	 *
	 * @param axis
	 * @return
	 */
	@Override
	public String getElementAxisName(IAxis axis) {
		if (axis instanceof EOperationAxis) {
			return ((EOperationAxis) axis).getElement().getName();
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#isAllowedContents(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public boolean isAllowedContents(Object object) {
		return object instanceof EOperation;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#isSlave()
	 *
	 * @return
	 */
	@Override
	public boolean isSlave() {
		return true;
	}
}
