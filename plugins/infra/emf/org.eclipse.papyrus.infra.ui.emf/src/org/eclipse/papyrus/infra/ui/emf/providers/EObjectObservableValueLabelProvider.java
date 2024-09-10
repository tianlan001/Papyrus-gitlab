/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation, Bug 521908
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.emf.providers;

import org.eclipse.emf.databinding.EObjectObservableValue;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.ui.provider.DelegatingStyledCellLabelProvider;
import org.eclipse.papyrus.infra.ui.emf.utils.Constants;

/**
 * LabelProvider used to show feature name and EObject of an EObjectObservaleValue.
 * 
 * @since 2.0
 *
 */
public class EObjectObservableValueLabelProvider extends DelegatingStyledCellLabelProvider {

	/**
	 * Index of the EStructuralFeature.
	 */
	private static final int ESTRUCTURAL_FEATURE = 0;

	/**
	 * Index of the EObject.
	 */
	private static final int EOBJECT = 1;

	/**
	 * Constructor.
	 *
	 * @param styledLabelProvider
	 *            The {@link IStyledLabelProvider}.
	 */
	public EObjectObservableValueLabelProvider(final IStyledLabelProvider styledLabelProvider) {
		super(styledLabelProvider);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof EObjectObservableValue) {
			String columnText = Constants.EMPTY_STRING;
			EObjectObservableValue elementObservableValue = (EObjectObservableValue) element;
			Object valueType = elementObservableValue.getValueType();
			if (valueType instanceof EAttribute) {
				switch (columnIndex) {
				case ESTRUCTURAL_FEATURE:
					if (valueType instanceof EStructuralFeature) {
						columnText = ((EStructuralFeature) valueType).getName();
					}
					break;
				case EOBJECT:
					Object value = elementObservableValue.getValue();
					if (null != value) {
						columnText = value.toString();
						if (isUnlimitedNatural((EAttribute) valueType)) {
							columnText = columnText.replace(Constants.INFINITE_MINUS_ONE, Constants.INFINITY_STAR);
						}
					}
					break;
				default:
					columnText = super.getColumnText(element, columnIndex);
					break;
				}
			} else {
				if (ESTRUCTURAL_FEATURE == columnIndex) {
					if (valueType instanceof ENamedElement) {
						columnText = ((ENamedElement) valueType).getName();
					}
				}
			}

			return columnText;
		}
		return super.getColumnText(element, columnIndex);
	}

	/**
	 * Check if the EAttribute is a UnlimitedNatural.
	 * 
	 * @param valueType
	 *            The EAttribute.
	 * @return The boolean result.
	 */
	private boolean isUnlimitedNatural(final EAttribute valueType) {
		boolean isUnlimitedNatural = false;
		EClassifier eType = ((EAttribute) valueType).getEType();
		if (null != eType) {
			String name = eType.getName();
			if (Constants.UNLIMITED_NATURAL.equals(name)) {
				isUnlimitedNatural = true;
			}
		}
		return isUnlimitedNatural;
	}
}
