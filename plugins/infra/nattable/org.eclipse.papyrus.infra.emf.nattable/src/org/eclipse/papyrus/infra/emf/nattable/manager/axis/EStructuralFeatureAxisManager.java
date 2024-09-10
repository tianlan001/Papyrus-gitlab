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
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;

/**
 * the axis manager for EStructuralFeature
 *
 * @author Vincent Lorenzo
 *
 */
public class EStructuralFeatureAxisManager extends EObjectAxisManager {

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
	 * @param objectToAdd The objects to add.
	 * @return The axis to add.
	 */
	protected Collection<IAxis> getAxisToAdd(final Collection<Object> objectToAdd){
		final Collection<IAxis> toAdd = new ArrayList<IAxis>();
		for (final Object current : objectToAdd) {
			if (isAllowedContents(current) && !isAlreadyManaged(current)) {
				final EStructuralFeatureAxis newAxis = NattableaxisFactory.eINSTANCE.createEStructuralFeatureAxis();
				newAxis.setElement((EStructuralFeature) current);
				newAxis.setManager(this.representedAxisManager);
				toAdd.add(newAxis);
			}
		}
		return toAdd;
	}

	/**
	 * calculus of the contents of the axis
	 */
	public Collection<Object> getAllPossibleAxis() {
		Set<Object> objects = new HashSet<Object>();
		for (final Object current : getAllManagedAxis()) {
			EClass eClass = (EClass) current;
			EPackage ePackage = eClass.getEPackage();
			if (!eClass.getEStructuralFeatures().isEmpty()) {
				objects.add(ePackage);
			}
		}
		return objects;
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
		final Set<Object> features = getFeaturesToAdd(objectToAdd);
		if (!features.isEmpty()) {
			return getAddAxisCommand(domain, features);
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
		final Set<Object> features = getFeaturesToAdd(objectToAdd);
		if (!features.isEmpty()) {
			return getAddAxisCommand(domain, features, index);
		}
		return null;
	}

	/**
	 * Get the features to add.
	 * 
	 * @param objectToAdd The initial objects to add.
	 * @return The features to add.
	 */
	protected Set<Object> getFeaturesToAdd(final Collection<Object> objectToAdd){
		final Set<Object> features = new HashSet<Object>();
		for (final Object current : objectToAdd) {
			if (current instanceof EObject) {
				features.addAll(((EObject) current).eClass().getEAllStructuralFeatures());
			}
		}
		features.removeAll(getElements());
		return features;
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
		if (axis instanceof EStructuralFeatureAxis) {
			return ((EStructuralFeatureAxis) axis).getElement().getName();
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
		return object instanceof EStructuralFeature;
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
