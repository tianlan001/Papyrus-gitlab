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
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.modelelement;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.swt.widgets.Control;

/**
 * A ReferenceFactory used to instantiate new DataType.
 * DataType may contain other DataType
 */
public class EObjectStructuredValueFactory extends EObjectDirectEditingValueFactory {

	/**
	 * Constructor.
	 *
	 * @param referenceIn
	 */
	public EObjectStructuredValueFactory(final EReference referenceIn) {
		super(referenceIn);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object doCreateObject(final Control widget, final Object context) {
		Object instance;

		if (referenceIn.isContainment()) {
			instance = createStructuredObject(widget);
		} else {
			instance = createObjectInDifferentContainer(widget);
		}

		return createObject(widget, context, instance);
	}

	/**
	 * Create an object an its child.
	 *
	 * @param widget
	 *            The widget.
	 * @return The created object.
	 */
	protected EObject createStructuredObject(final Control widget) {
		EClass eClass = chooseEClass(widget);
		if (eClass == null) {
			return null;
		}

		EObject instance = eClass.getEPackage().getEFactoryInstance().create(eClass);

		createStructure(instance);

		return instance;
	}

	/**
	 * Creation of the instance of the DataType referenced by the container.
	 *
	 * @param eObject
	 *            The container.
	 */
	private void createStructure(final EObject eObject) {
		if (null != eObject) {
			EList<EStructuralFeature> eStructuralFeatures = eObject.eClass().getEStructuralFeatures();
			for (EStructuralFeature eStructuralFeature : eStructuralFeatures) {
				if (eStructuralFeature instanceof EReference) {
					Object eGet = eObject.eGet(eStructuralFeature);
					if (eGet == null) {
						if (eStructuralFeature.getEType() instanceof EClass) {
							EClass new_name = (EClass) eStructuralFeature.getEType();
							EObject create = new_name.getEPackage().getEFactoryInstance().create(new_name);
							setValue(eObject, create, eGet, eStructuralFeature);
							eGet = eObject.eGet(eStructuralFeature);
						}
					}
					if (eGet instanceof EObject) {
						createStructure((EObject) eGet);
					}

				}
			}
		}
	}

	/**
	 * Set the edit value by command.
	 * 
	 * @param eObject
	 *            The parent object to edit.
	 * @param value
	 *            The new value
	 * @param oldValue
	 *            The old value
	 * @param eStructuralFeature
	 *            The feature.
	 */
	protected void setValue(final EObject eObject, final Object value, final Object oldValue, final EStructuralFeature eStructuralFeature) {
		final TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(eObject);

		final Command command = getSetCommand(eObject, value, oldValue, eStructuralFeature, editingDomain);
		command.execute();
	}

	/**
	 * Returns the command used to edit the value.
	 * 
	 * @param eObject
	 *            The parent object to edit.
	 * @param value
	 *            The new value
	 * @param oldValue
	 *            The old value
	 * @param eStructuralFeature
	 *            The feature.
	 * @param domain
	 *            the current editing domain.
	 * @return The Set command used to edit the value
	 */
	protected Command getSetCommand(final EObject eObject, final Object value, final Object oldValue, final EStructuralFeature eStructuralFeature, final EditingDomain domain) {
		CompoundCommand cc = new CompoundCommand("Edit value"); //$NON-NLS-1$

		if (oldValue instanceof EObject && eStructuralFeature instanceof EReference && ((EReference) eStructuralFeature).isContainment()) {
			cc.append(DeleteCommand.create(domain, oldValue));
		}

		cc.append(new SetCommand(domain, eObject, eStructuralFeature, value));

		return cc;
	}

}
