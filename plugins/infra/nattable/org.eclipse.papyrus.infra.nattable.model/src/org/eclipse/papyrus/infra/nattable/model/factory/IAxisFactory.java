/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.model.factory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IdTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;

/**
 * @author Vincent Lorenzo
 *
 */
public class IAxisFactory {

	/**
	 * Constructor.
	 *
	 */
	private IAxisFactory() {
		// to prevent instanciation
	}

	/**
	 *
	 * @param parent
	 *            the parent for the tree axis item
	 * @param object
	 *            the object to represent with a ITreeItemAxis
	 * @param manager
	 *            the axis manager representation for the new axis
	 * @return
	 * 		the created ITreeAxisItem for the object
	 */
	public static final ITreeItemAxis createITreeItemAxis(ITreeItemAxis parent, Object object, AxisManagerRepresentation manager, String alias) {
		ITreeItemAxis createdAxis = null;
		if (object instanceof Integer || object instanceof Float || object instanceof Boolean) {
			object = object.toString();
		}
		if (object instanceof String) {
			IdTreeItemAxis newAxis = NattableaxisFactory.eINSTANCE.createIdTreeItemAxis();
			newAxis.setElement((String) object);
			createdAxis = newAxis;
		} else if (object instanceof EStructuralFeature) {
			EStructuralFeatureTreeItemAxis newAxis = NattableaxisFactory.eINSTANCE.createEStructuralFeatureTreeItemAxis();
			newAxis.setElement((EStructuralFeature) object);
			createdAxis = newAxis;
		} else if(object instanceof EOperation){
			EOperationTreeItemAxis newAxis = NattableaxisFactory.eINSTANCE.createEOperationTreeItemAxis();
			newAxis.setElement((EOperation) object);
			createdAxis = newAxis;
		} else if (object instanceof EObject) {
			EObjectTreeItemAxis newAxis = NattableaxisFactory.eINSTANCE.createEObjectTreeItemAxis();
			newAxis.setElement((EObject) object);
			createdAxis = newAxis;
		}
		if (createdAxis == null) {
			throw new UnsupportedOperationException(NLS.bind("The creation for {0} is not yet implemented", object)); //$NON-NLS-1$
		} else {
			createdAxis.setParent(parent);
			createdAxis.setManager(manager);
			createdAxis.setAlias(alias);
		}
		return createdAxis;
	}

	/**
	 * This allows to create a feature axis representing the object in parameter.
	 *
	 * @param object
	 *        an object representing a feature
	 * @return
	 *         a feature axis to represent it
	 * @deprecated since 1.2.0, use createAxisForTypedElement isntead of this method
	 */
  	@Deprecated
	public static final FeatureAxis createAxisForFeature(final Object object, final AxisManagerRepresentation manager, final String alias) {
		if(object instanceof String) {
			FeatureIdAxis axis = NattableaxisFactory.eINSTANCE.createFeatureIdAxis();
			axis.setElement((String)object);
			axis.setAlias(alias);
			return axis;
		}
		if(object instanceof EStructuralFeature) {
			EStructuralFeatureAxis axis = NattableaxisFactory.eINSTANCE.createEStructuralFeatureAxis();
			axis.setElement((EStructuralFeature)object);
			axis.setAlias(alias);
			return axis;
		}
		throw new UnsupportedOperationException(NLS.bind("The creation for {0} is not yet implemented", object)); //$NON-NLS-1$
	}
	
	/**
	 * This allows to create an axis representing the object in parameter.
	 *
	 * @param object
	 *            An object representing a feature or an operation
	 * @param manager
	 *            The axis manager representation
	 * @param alias
	 *            The alias of the axis
	 * @return
	 * 		An axis representing the object in parameter
	 */
	public static final IAxis createAxisForTypedElement(final Object object, final AxisManagerRepresentation manager, final String alias) {
		if (object instanceof String) {
			FeatureIdAxis axis = NattableaxisFactory.eINSTANCE.createFeatureIdAxis();
			axis.setElement((String) object);
			axis.setAlias(alias);
			return axis;
		}
		if (object instanceof EStructuralFeature) {
			EStructuralFeatureAxis axis = NattableaxisFactory.eINSTANCE.createEStructuralFeatureAxis();
			axis.setElement((EStructuralFeature) object);
			axis.setAlias(alias);
			return axis;
		}
		if (object instanceof EOperation) {
			EOperationAxis axis = NattableaxisFactory.eINSTANCE.createEOperationAxis();
			axis.setElement((EOperation) object);
			axis.setAlias(alias);
			return axis;
		}
		throw new UnsupportedOperationException(NLS.bind("The creation for {0} is not yet implemented", object)); //$NON-NLS-1$
	}
}
