/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 485220
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.xtext.valuespecification.manager.cell;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.ui.internal.UmlValueSpecificationActivator;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.utils.commands.ValueSpecificationSetCommand;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.inject.Injector;

/**
 * This class provides the method to get and set the values in the Cell
 * representing a ValueSpecification.
 */
public class ValueSpecificationCellManager extends EMFFeatureValueCellManager {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager#getSetValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain,
	 *      java.lang.Object, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 */
	@Override
	public Command getSetValueCommand(final TransactionalEditingDomain domain,
			final Object columnElement, final Object rowElement,
			final Object newValue, final INattableModelManager tableManager) {
		if (newValue instanceof IAdaptable) {
			final ICommand cmd = ((IAdaptable) newValue)
					.getAdapter(ICommand.class);
			if (null != cmd && cmd.canExecute()) {
				return new GMFtoEMFCommandWrapper(cmd);
			} else {
				final String editedString = ((IAdaptable) newValue)
						.getAdapter(String.class);
				return getSetStringValueCommand(domain, columnElement,
						rowElement, editedString, null, tableManager);
			}

		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager#getSetStringValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain,
	 *      java.lang.Object, java.lang.Object, java.lang.String,
	 *      org.eclipse.papyrus.infra.tools.converter.AbstractStringValueConverter,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 */
	@Override
	public Command getSetStringValueCommand(
			final TransactionalEditingDomain domain, final Object columnElement,
			final Object rowElement, final String newValue,
			final AbstractStringValueConverter valueSolver,
			final INattableModelManager tableManager) {
		if (columnElement instanceof EStructuralFeatureAxis && rowElement instanceof EObjectAxis || columnElement instanceof EObjectAxis && rowElement instanceof EStructuralFeatureAxis) {

			EStructuralFeature structuralFeature = null;
			if (columnElement instanceof EStructuralFeatureAxis) {
				structuralFeature = ((EStructuralFeatureAxis) columnElement).getElement();
			} else {
				structuralFeature = ((EStructuralFeatureAxis) rowElement).getElement();
			}

			if (UMLPackage.Literals.VALUE_SPECIFICATION == structuralFeature
					.getEType()) {
				EObject eObject = null;
				if (rowElement instanceof EObjectAxis) {
					eObject = ((EObjectAxis) rowElement).getElement();
				} else {
					eObject = ((EObjectAxis) columnElement).getElement();
				}

				CompositeCommand result = ValueSpecificationSetCommand
						.getInstance().createSetCommand(getInjector(), eObject,
								structuralFeature, newValue, getDefaultLanguages());

				return result.isEmpty() ? null : new GMFtoEMFCommandWrapper(
						result);
			}
		}

		return null;
	}

	/**
	 * This allow to define the default languages for an opaque expression.
	 * 
	 * @return The list of default languages.
	 */
	protected Collection<String> getDefaultLanguages() {
		// This method will be redefined to define the default languages of an opaque expression
		return Collections.emptyList();
	}

	/**
	 * This allow to get the injector used for the xtext parser.
	 * 
	 * @return The injector corresponding to the xtext parser.
	 */
	public Injector getInjector() {
		return UmlValueSpecificationActivator
				.getInstance()
				.getInjector(
						UmlValueSpecificationActivator.ORG_ECLIPSE_PAPYRUS_UML_TEXTEDIT_VALUESPECIFICATION_XTEXT_UMLVALUESPECIFICATION);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.cell.EMFFeatureValueCellManager#handles(java.lang.Object, java.lang.Object, INattableModelManager)
	 */
	@Override
	public boolean handles(final Object columnElement, final Object rowElement, INattableModelManager tableManager) {
		boolean result = false;
		Object row = AxisUtils.getRepresentedElement(rowElement);
		Object column = AxisUtils.getRepresentedElement(columnElement);
		if (column instanceof EStructuralFeature) {
			result = UMLPackage.Literals.VALUE_SPECIFICATION == ((EStructuralFeature) column)
					.getEType();
		} else if (row instanceof EStructuralFeature) {
			result = UMLPackage.Literals.VALUE_SPECIFICATION == ((EStructuralFeature) row)
					.getEType();
		}
		return result;
	}

}
