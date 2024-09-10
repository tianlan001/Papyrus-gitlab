/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.canonical.tests;

import static org.eclipse.papyrus.junit.framework.runner.ScenarioRunner.verificationPoint;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.papyrus.junit.framework.classification.InvalidTest;
import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Specific regression tests for generic canonical model-view synchronization bugs.
 */
@ActiveDiagram("classes")
@RunWith(ScenarioRunner.class)
public class RegressionTest extends AbstractCanonicalTest {

	public RegressionTest() {
		super();
	}

	@PluginResource("models/bugs/472155.di")
	@Scenario({ "initial", "synch", "undo", "redo" })
	public void referenceConnectionNotDeleted_bug472155() {
		org.eclipse.uml2.uml.Class class1 = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType("Class1");
		Constraint constraint = class1.getOwnedRule("Constraint1");

		if (verificationPoint()) {
			requireEdge(requireView(class1), requireView(constraint));
		}

		// We know from previous assertion that there's an edit part here
		setCanonical(true, getEditPart(class1));

		if (verificationPoint()) {
			// Still have the edge
			requireEdge(requireView(class1), requireView(constraint));
		}

		undo();

		if (verificationPoint()) {
			// Still have the edge
			requireEdge(requireView(class1), requireView(constraint));
		}

		redo();

		if (verificationPoint()) {
			// Still have the edge
			requireEdge(requireView(class1), requireView(constraint));
		}
	}

	@PluginResource("models/composite.di")
	@ActiveDiagram("main")
	@NeedsUIEvents
	@Test
	public void nestedClassifiersDontCauseLoopInCompositeDiagram_bug474489() {
		class Trap extends AdapterImpl {
			boolean caught;

			@Override
			public void notifyChanged(Notification msg) {
				switch (msg.getEventType()) {
				case Notification.ADD:
					if (msg.getFeature() == UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE) {
						caught = true;
						fail();
					}
					break;
				default:
					break;
				}
			}
		}

		final org.eclipse.uml2.uml.Class blackBox = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType("BlackBox");
		setCanonical(true, requireEditPart(blackBox));

		// Add an owned behavior
		StateMachine behavior = UMLFactory.eINSTANCE.createStateMachine();
		behavior.setName("StateMachine1");

		// Listen for attempts to drop the nested behavior as new property typed by it
		final Trap trap = new Trap();
		blackBox.eAdapters().add(0, trap);

		add(blackBox, behavior, UMLPackage.Literals.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR);
		assertThat("Canonical edit-policy dropped the nested behavior as a part", trap.caught, is(false));

		assertNoView(behavior);

		// No part was created for this behavior (as its type), either
		for (Property next : blackBox.getAllAttributes()) {
			assertThat(next.getType(), not(is((Type) behavior)));
		}
	}

	@PluginResource("models/bugs/473148.di")
	@Test
	public void associationEndCanonicalPresentation_bug473148() {
		org.eclipse.uml2.uml.Class class1 = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType("Class1");
		org.eclipse.uml2.uml.Class class2 = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType("Class2");
		Property name = class1.getOwnedAttribute("name", null);
		Property end = class1.getOwnedAttribute("class2", class2);

		setCanonical(true, requireEditPart(class1));

		requireEditPart(name); // the attribute should be shown
		assertNoView(end); // but not the association end
	}

	@InvalidTest("The class diagram doesn't want ever to show association ends in the attributes compartment")
	@PluginResource("models/bugs/473148.di")
	@Test
	public void associationEndWhenNoAssociationEdge_bug473148() {
		org.eclipse.uml2.uml.Class class1 = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType("Class1");
		org.eclipse.uml2.uml.Class class2 = (org.eclipse.uml2.uml.Class) editor.getModel().getOwnedType("Class2");
		Property name = class1.getOwnedAttribute("name", null);
		Property end = class1.getOwnedAttribute("class2", class2);

		// Ensure that we will not canonically present the association
		delete(requireEditPart(class2));
		assertAttached(class2); // we didn't accidentally delete the class
		assertThat(end.getType(), is((Type) class2)); // nor mess with the association end
		assertThat(end.getAssociation(), not(nullValue()));

		setCanonical(true, requireEditPart(class1));

		requireEditPart(name); // the attribute should be shown
		requireEditPart(end); // and also the association end because we can't show the association
	}
}
