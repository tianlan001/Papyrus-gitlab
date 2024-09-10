/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST and others.
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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515806
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.generic.tests.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * This tests check the filter on enumeration
 *
 */
public class FilterEEnumMatcherTest extends AbstractFilterMatcherTest {

	/**
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.tests.AbstractFilterMatcherTest#initModel()
	 *
	 * @throws Exception
	 */
	@Before
	@Override
	public void initModel() throws Exception {
		super.initModel();
	}

	/**
	 *
	 * @param axis
	 *            an axis
	 * @return
	 * 		a list containing the possible Enumerator for the axis
	 */
	private List<Enumerator> getVisibilityLiteral() {
		final List<Enumerator> literals = new ArrayList<Enumerator>();
		EEnum eenum = (EEnum) UMLPackage.eINSTANCE.getNamedElement_Visibility().getEType();
		for (EEnumLiteral current : eenum.getELiterals()) {
			literals.add(current.getInstance());
		}
		return literals;
	}

	@Test
	public void testEEnumMatchingMode_1() throws Exception {
		List<Enumerator> eenum = getVisibilityLiteral();
		for (Enumerator current : eenum) {
			if (current.getName().equals("public")) { //$NON-NLS-1$
				checkFilter(Collections.singleton(current), 2, 2, 5, 1);
				return;
			}
		}
	}

	@Test
	public void testEEnumMatchingMode_2() throws Exception {
		List<Enumerator> eenum = getVisibilityLiteral();
		for (Enumerator current : eenum) {
			if (current.getName().equals("private")) { //$NON-NLS-1$
				checkFilter(Collections.singleton(current), 2, 2, 5, 1);
				return;
			}
		}
	}

	@Test
	public void testEEnumMatchingMode_3() throws Exception {
		List<Enumerator> eenum = getVisibilityLiteral();
		for (Enumerator current : eenum) {
			if (current.getName().equals("protected")) { //$NON-NLS-1$
				checkFilter(Collections.singleton(current), 2, 2, 5, 1);
				return;
			}
		}
	}

	@Test
	public void testEEnumMatchingMode_4() throws Exception {
		List<Enumerator> eenum = getVisibilityLiteral();
		for (Enumerator current : eenum) {
			if (current.getName().equals("package")) { //$NON-NLS-1$
				checkFilter(Collections.singleton(current), 2, 2, 5, 1);
				return;
			}
		}
	}

	@Test
	public void testEEnumMatchingMode_5() throws Exception {
		checkFilter(Collections.singleton(CellHelper.getUnsupportedCellContentsText()), 2, 2, 5, 1);
	}

	@Test
	public void testEEnumMatchingMode_6() throws Exception {
		List<Object> matchOn = new ArrayList<Object>();
		matchOn.add(CellHelper.getUnsupportedCellContentsText());
		matchOn.add(getVisibilityLiteral().get(0));
		checkFilter(matchOn, 2, 2, 5, 2);
	}

}
