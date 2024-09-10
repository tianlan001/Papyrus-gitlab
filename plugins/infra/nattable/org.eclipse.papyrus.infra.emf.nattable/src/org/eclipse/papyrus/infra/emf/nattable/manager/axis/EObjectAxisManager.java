/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.nattable.manager.axis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

/**
 *
 * This manager is a master manager, that's to say, it doesn't listen the contents of the others managers.
 * This manager modify the model for each changes on axis (so each axis is stored)
 *
 */
public class EObjectAxisManager extends AbstractAxisManager {

	// /**
	// *
	// * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#canDestroyAxisElement(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis)
	// *
	// * @param axis
	// * @return
	// */
	// public boolean canDestroyAxisElement(final IAxis axis) {
	// final EObject object = (EObject)axis.getElement();
	// return !EMFHelper.isReadOnly(object);
	// }

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#canDestroyAxisElement(java.lang.Integer)
	 *
	 * @param axisPosition
	 * @return
	 */
	@Override
	public boolean canDestroyAxisElement(Integer axisPosition) {
		final Object current = getElements().get(axisPosition);
		if (current instanceof EObjectAxis) {
			return !EMFHelper.isReadOnly(((EObjectAxis) current).getElement());
		}
		return false;
	}



	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getAddAxisCommand(TransactionalEditingDomain, java.util.Collection)
	 *
	 * @param domain
	 *            the editing domain
	 * @param objectToAdd
	 *            the object to add to this axis
	 * @return
	 *         the command to create the required axis in the model
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
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getAddAxisCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.util.Collection, int)
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
	 * @param objectToAdd The objects to add.
	 * @return The axis to add.
	 */
	protected Collection<IAxis> getAxisToAdd(final Collection<Object> objectToAdd){
		final Collection<IAxis> toAdd = new ArrayList<IAxis>();
		for (final Object object : objectToAdd) {
			if (isAllowedContents(object) && !isAlreadyManaged(object)) {
				final EObjectAxis horizontalAxis = NattableaxisFactory.eINSTANCE.createEObjectAxis();
				horizontalAxis.setElement((EObject) object);
				horizontalAxis.setManager(this.representedAxisManager);
				toAdd.add(horizontalAxis);
			}
		}
		return toAdd;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#getDestroyAxisElementCommand(TransactionalEditingDomain, java.lang.Integer)
	 *
	 * @param domain
	 * @param axisPosition
	 * @return
	 */
	@Override
	public Command getDestroyAxisElementCommand(final TransactionalEditingDomain domain, final Integer axisPosition) {
		final Object current = getElements().get(axisPosition);
		if (current instanceof EObjectAxis) {
			final EObject element = ((EObjectAxis) current).getElement();
			final DestroyElementRequest request = new DestroyElementRequest(getContextEditingDomain(), element, false);
			final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(element);
			return new RemoveCommandWrapper(new GMFtoEMFCommandWrapper(provider.getEditCommand(request)), Collections.singleton((Object) ((EObjectAxis) current).getElement()));
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getElementAxisName(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis)
	 *
	 * @param axis
	 * @return
	 */
	@Override
	public String getElementAxisName(final IAxis axis) {
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#isAllowedContents(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public boolean isAllowedContents(Object object) {
		return object instanceof EObject;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.ISubAxisManager#isDynamic()
	 *
	 * @return
	 */
	@Override
	public boolean isDynamic() {
		return false;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#isSlave()
	 *
	 * @return
	 */
	@Override
	public boolean isSlave() {
		return false;
	}


}
