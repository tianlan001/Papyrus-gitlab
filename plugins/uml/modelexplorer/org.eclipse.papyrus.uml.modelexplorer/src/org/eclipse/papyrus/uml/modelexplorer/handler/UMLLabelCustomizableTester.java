/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 500219 - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.modelexplorer.handler;

import java.util.Iterator;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;
import org.eclipse.papyrus.views.modelexplorer.Activator;

/**
 * A tester use to test if the CustomizableUMLLabel facet model is loaded.
 * @since 2.0
 */
public class UMLLabelCustomizableTester extends PropertyTester {

	/** The name of the facet model used to customize uml label */
	private static final String CUSTOMIZABLE_UML_LABEL = "CustomizableUMLLabel";

	/** the property key */
	private static final String IS_UML_LABEL_CUSTOMIZABLE = "isUMLLabelCustomizable";//$NON-NLS-1$

	public UMLLabelCustomizableTester() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		boolean result = false;
		if (IS_UML_LABEL_CUSTOMIZABLE.equals(property)) {
			ICustomizationManager customizationManager = Activator.getDefault().getCustomizationManager();

			for (Iterator<Customization> iterator = customizationManager.getManagedCustomizations().iterator(); !result && iterator.hasNext();) {
				Customization customization = iterator.next();

				if (CUSTOMIZABLE_UML_LABEL.equals(customization.getName())) {
					result = true;
				}
			}
		}
		return result;
	}

}
