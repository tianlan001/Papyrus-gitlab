/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515737
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.tester;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableWidgetPropertyTester;
import org.eclipse.papyrus.uml.nattable.utils.UMLNattableStereotypeUtils;
import org.eclipse.uml2.uml.Property;

/**
 * Property testers for UML NatTable widgets.
 * Check if an axis corresponding to a stereotype property is multiple values or not.
 *
 * @since 4.0
 */
public class UMLNattableWidgetPropertyTester extends NattableWidgetPropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		final INattableModelManager manager = getNattableModelManager();

		if (null != manager && expectedValue instanceof Boolean && IS_ONE_MULTIPLE_VALUES_AXIS_SELECTED.equals(property)) {

			// Get the selected axis index
			final int axisIndex = AxisUtils.getUniqueSelectedAxisIndex(manager);

			// Always get the column axis provider for invert or non-invert table
			final AbstractAxisProvider axisProvider = manager.getTable().getCurrentColumnAxisProvider();

			// If the index is in range
			if (null != axisProvider && null != axisProvider.getAxis() && 0 <= axisIndex && axisIndex < axisProvider.getAxis().size()) {
				final IAxis selectedAxis = axisProvider.getAxis().get(axisIndex);
				// Get the selected axis element
				final Object axisElement = selectedAxis.getElement();

				// Check if the selected axis defined by properties of a stereotype
				// we need to find the corresponding property defined in stereotype
				if (selectedAxis instanceof FeatureIdAxis) {

					final FeatureIdAxis selectedFeatureAxis = (FeatureIdAxis) selectedAxis;

					final String propertyStereotypeID = axisElement.toString();

					// Get the corresponding property defined in the stereotype and check its upper bound
					if (propertyStereotypeID.startsWith(UMLNattableStereotypeUtils.PROPERTY_OF_STEREOTYPE_PREFIX) && null != selectedFeatureAxis.eContainer() && selectedFeatureAxis.eContainer().eContainer() instanceof Table) {
						final Table table = (Table) selectedFeatureAxis.eContainer().eContainer();
						final Property propertyStereotype = UMLNattableStereotypeUtils.getRealStereotypeProperty(table.getOwner(), propertyStereotypeID);
						if (null != propertyStereotype) {
							return expectedValue.equals(isMultipleValues(propertyStereotype.getUpper()));
						}
					}
				}
			}
		}

		return false;
	}
}
