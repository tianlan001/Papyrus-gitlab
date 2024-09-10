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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;
import org.eclipse.papyrus.uml.nattable.dataprovider.UMLStereotypeSingleEnumerationComboBoxDataProvider;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Test filters for UML Enumeration
 *
 */
public class FilterUMLEnumerationMatcherTest extends AbstractFilterMatcherTest {

	private int nbElementInTheTable = 4;

	/**
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.tests.AbstractFilterMatcherTest#initModel()
	 *
	 * @throws Exception
	 */
	@Before
	@Override
	public void initModel() throws Exception {
		Collection<String> additionalModels = new ArrayList<String>();
		additionalModels.add("FilterUMLEnumerationMatcherTestProfile.profile"); //$NON-NLS-1$
		initModelWithAdditionalModels(additionalModels);
	}

	protected List<?> getAvailablePriorityLiterals() {
		StringBuilder builder = new StringBuilder(UMLTableUtils.PROPERTY_OF_STEREOTYPE_PREFIX);
		builder.append("profile::Task::priority"); //$NON-NLS-1$
		UMLStereotypeSingleEnumerationComboBoxDataProvider p = new UMLStereotypeSingleEnumerationComboBoxDataProvider(builder.toString(), getTableManager());
		return p.getValues(0, 1);
	}

	@Test
	public void test1() throws Exception {
		List<?> literals = getAvailablePriorityLiterals();
		for (Object current : literals) {
			if (current instanceof Enumerator && ((Enumerator) current).getName().equals("Hight")) { //$NON-NLS-1$
				checkFilter(Collections.singleton(current), 2, 2, nbElementInTheTable, 1);
			}
		}
	}

	@Test
	public void test2() throws Exception {
		List<?> literals = getAvailablePriorityLiterals();
		for (Object current : literals) {
			if (current instanceof Enumerator && ((Enumerator) current).getName().equals("Medium")) { //$NON-NLS-1$
				checkFilter(Collections.singleton(current), 2, 2, nbElementInTheTable, 1);
			}
		}
	}

	@Test
	public void test3() throws Exception {
		List<?> literals = getAvailablePriorityLiterals();
		for (Object current : literals) {
			if (current instanceof Enumerator && ((Enumerator) current).getName().equals("Low")) { //$NON-NLS-1$
				checkFilter(Collections.singleton(current), 2, 2, nbElementInTheTable, 1);
			}
		}
	}

	@Test
	public void test4() throws Exception {
		checkFilter(Collections.singleton(CellHelper.getUnsupportedCellContentsText()), 2, 2, nbElementInTheTable, 1);
	}

	@Test
	public void test5() throws Exception {
		List<?> literals = getAvailablePriorityLiterals();
		Collection<Object> matchOn = new ArrayList<Object>();
		matchOn.add(literals.get(0));
		matchOn.add(literals.get(1));
		checkFilter(matchOn, 2, 2, nbElementInTheTable, 2);
	}

	@Test
	public void test6() throws Exception {
		List<?> literals = getAvailablePriorityLiterals();
		Collection<Object> matchOn = new ArrayList<Object>();
		matchOn.add(literals.get(0));
		matchOn.add(CellHelper.getUnsupportedCellContentsText());
		checkFilter(matchOn, 2, 2, nbElementInTheTable, 2);
	}
}
