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
 *   Ansgar Radermacher (CEA) - Initial API and Implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.tests;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLSemanticChildrenStrategy;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class Bug495430_DuplicatedTransitionsTest.
 */
public class Bug495430_DuplicatedTransitionsTest extends AbstractPapyrusTest {

	@Before
	public void init() {
	}

	/**
	 * Tests of the move of one generalization.
	 */
	@Test
	public void checkDuplication() {
		DefaultUMLSemanticChildrenStrategy semanticChildrenStrategy = new DefaultUMLSemanticChildrenStrategy();
		State state = UMLFactory.eINSTANCE.createState();
		Transition transition = UMLFactory.eINSTANCE.createTransition();
		// transition is a self-transition, i.e. appears in the incomings and outgoings list
		state.getIncomings().add(transition);
		state.getOutgoings().add(transition);
		// yet, the transition should appear only once in the result list.
		List<? extends EObject> tst = semanticChildrenStrategy.getCanonicalSemanticConnections(state, null);
		assert tst.size() == 1;
	}
}
