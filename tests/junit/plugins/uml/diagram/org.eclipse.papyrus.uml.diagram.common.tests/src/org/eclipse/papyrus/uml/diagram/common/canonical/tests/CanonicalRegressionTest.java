/*****************************************************************************
 * Copyright (c) 2015, 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 551742
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.canonical.tests;

import static org.eclipse.papyrus.junit.framework.runner.ScenarioRunner.verificationPoint;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrus.infra.gmfdiag.canonical.tests.AbstractCanonicalTest;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Regression tests for UML-specific canonical synchronization bugs.
 */
@ActiveDiagram("classes")
@RunWith(ScenarioRunner.class)
public class CanonicalRegressionTest extends AbstractCanonicalTest {

	public CanonicalRegressionTest() {
		super();
	}

	@PluginResource("resources/471954/model.di")
	@Scenario({ "initial", "synch", "undo", "redo" })
	public void appliedStereotypeViewsNotDeleted_bug471954() {
		org.eclipse.uml2.uml.Class class1 = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType("Class1");
		Stereotype utility = class1.getAppliedStereotype("StandardProfile::Utility");

		if (verificationPoint()) {
			requireView(utility, requireView(class1));
		}

		// We know from previous assertion that there's an edit part here
		setCanonical(true, getEditPart(class1));

		if (verificationPoint()) {
			// Still have the stereotype presentation
			requireView(utility, requireView(class1));
		}

		undo();

		if (verificationPoint()) {
			// Still have the stereotype presentation
			requireView(utility, requireView(class1));
		}

		redo();

		if (verificationPoint()) {
			// Still have the stereotype presentation
			requireView(utility, requireView(class1));
		}
	}

	@PluginResource("resources/478558/bug478558.di")
	@ActiveDiagram("IBD")
	@FailingTest  //see Bug 551742
	@Test
	public void visualizePropertyThatIsAssociationEnd_bug478558() {
		org.eclipse.uml2.uml.Class block3 = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType("Block3");
		Property part3 = block3.getOwnedAttribute("part3", null);

		assertThat(part3.getType(), not(instanceOf(Association.class)));
		assertThat(part3.getType(), instanceOf(org.eclipse.uml2.uml.Class.class));
		assertThat(part3.getType().getName(), is("Block1"));
	}

}
