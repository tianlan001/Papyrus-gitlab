/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.tests.canonical;

import java.util.List;

import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.uml.diagram.common.editparts.RoundedBorderNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test if lists of class return by getRoundedCompartmentEditParts and by
 * getRoundedBorderNamedElementEditParts extends respectively RoundedCompartmentEditPart and
 * RoundedBorderNamedElementEditPart.
 */
public abstract class AbstractGenericShapeTest extends AbstractPapyrusTest {

	/**
	 * Test rounded compartment edit parts.
	 */
	@Test
	public void testRoundedCompartmentEditParts() {

		List<Class<?>> roundedCompartmentEditParts = getRoundedCompartmentEditParts();

		for (Class<?> clazz : roundedCompartmentEditParts) {
			Assert.assertTrue(clazz.getName() + " have to extends RoundedCompartmentEditPart", classExtends(clazz, RoundedCompartmentEditPart.class));
		}
	}


	/**
	 * Test rounded border named element edit parts.
	 */
	@Test
	public void testRoundedBorderNamedElementEditParts() {

		List<Class<?>> roundedBorderNamedElementEditParts = getRoundedBorderNamedElementEditParts();

		for (Class<?> clazz : roundedBorderNamedElementEditParts) {
			Assert.assertTrue(clazz.getName() + " have to extends RoundedBorderNamedElementEditPart", classExtends(clazz, RoundedBorderNamedElementEditPart.class));
		}
	}


	/**
	 * test class1 extends directly or not of class2.
	 *
	 * @param class1
	 *            the class1
	 * @param class2
	 *            the class2
	 * @return true, if successful
	 */
	private boolean classExtends(Class<?> class1, Class<?> class2) {
		boolean extend = false;
		while (null != class1 && !extend) {
			extend = class1.getCanonicalName().equals(class2.getCanonicalName());
			class1 = class1.getSuperclass();
		}
		return extend;
	}

	/**
	 * Gets the rounded compartment edit parts.
	 *
	 * @return the rounded compartment edit parts
	 */
	public abstract List<Class<?>> getRoundedCompartmentEditParts();

	/**
	 * Gets the rounded border named element edit parts.
	 *
	 * @return the rounded border named element edit parts
	 */
	public abstract List<Class<?>> getRoundedBorderNamedElementEditParts();
}