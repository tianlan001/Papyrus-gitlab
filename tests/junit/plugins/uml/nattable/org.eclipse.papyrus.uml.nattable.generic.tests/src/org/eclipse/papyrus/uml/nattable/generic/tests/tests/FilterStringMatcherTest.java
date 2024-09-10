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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.generic.tests.tests;

import org.eclipse.papyrus.infra.nattable.filter.PapyrusTextMatchingMode;
import org.junit.Before;
import org.junit.Test;

/**
 * This class allows us to test text filters, with the different mode
 *
 */
public class FilterStringMatcherTest extends AbstractFilterMatcherTest {


	private static final int NB_ELEMENTS = 9;

	@Before
	public void initModel() throws Exception {
		super.initModel();
	}

	@Test
	public void testContainsMatchingMode() throws Exception {
		checkFilter("Block", 2,  2, NB_ELEMENTS, 0); //$NON-NLS-1$
	}
	
	@Test
	public void testNumeric_1_MatchingMode() throws Exception {
		checkFilter("num:>3", 2,  2, NB_ELEMENTS, 1); //$NON-NLS-1$
	}
	
	@Test
	public void testNumeric_2_MatchingMode() throws Exception {
		checkFilter("num:>=3", 2,  2, NB_ELEMENTS, 2); //$NON-NLS-1$
	}
	
	@Test
	public void testNumeric_3_MatchingMode() throws Exception {
		checkFilter("num:<4", 2,  2, NB_ELEMENTS, 3); //$NON-NLS-1$
	}
	
	@Test
	public void testNumeric_4_MatchingMode() throws Exception {
		checkFilter("num:<=4", 2,  2, NB_ELEMENTS, 4); //$NON-NLS-1$
	}
	
	@Test
	public void testNumeric_5_MatchingMode() throws Exception {
		checkFilter("num:<>4", 2,  2, NB_ELEMENTS, 3); //$NON-NLS-1$
	}
	
	@Test
	public void testStartWith_1_MatchingMode() throws Exception {
		checkFilter("start:Class", 2,  2, NB_ELEMENTS, 4); //$NON-NLS-1$
	}
	
	@Test
	public void testStartWith_2_MatchingMode() throws Exception {
		checkFilter("start:Class1", 2,  2, NB_ELEMENTS, 2); //$NON-NLS-1$
	}
	
	
	@Test
	public void testRegexFind_1_MatchingMode() throws Exception {
		checkFilter("regex:^Cl", 2,  2, NB_ELEMENTS, 4); //$NON-NLS-1$
	}
	
	@Test
	public void testRegexFind_2_MatchingMode() throws Exception {
		checkFilter("regex:^aCl", 2,  2, NB_ELEMENTS, 1); //$NON-NLS-1$
	}
	
	
	@Test
	public void testRegexMatch_1_MatchingMode() throws Exception {
		String matchOn = "^C\\D*\\d";//match all ligne where the string start with C, contains some NON digit char and ends with 0 or 1 digit //$NON-NLS-1$
		StringBuilder builder = new StringBuilder();
		builder.append(PapyrusTextMatchingMode.REGEX_MATCH.getMode());
		builder.append(matchOn);
		checkFilter(builder.toString(), 2,  2, NB_ELEMENTS, 3);
	}

}
