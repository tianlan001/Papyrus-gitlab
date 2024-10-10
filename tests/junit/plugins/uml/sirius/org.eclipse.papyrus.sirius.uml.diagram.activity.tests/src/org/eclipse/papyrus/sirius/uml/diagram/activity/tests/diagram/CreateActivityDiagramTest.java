/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests.diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDiagramCreationTests;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.FunctionBehavior;
import org.junit.Test;

/**
 * Check Activity diagram creation.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/createDiagram/createDiagram.di")
public class CreateActivityDiagramTest extends AbstractDiagramCreationTests {

	private static final String ACTIVITY1 = "Activity1"; //$NON-NLS-1$

	private static final String FUNCTION_BEHAVIOR1 = "FunctionBehavior1"; //$NON-NLS-1$

	private static final String DIAGRAM_TYPE = "org.eclipse.papyrus.sirius.uml.diagram.activity"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "newName"; //$NON-NLS-1$

	@Test
	public void createActivityDiagramTestFromPackageRoot() throws Exception {
		this.checkDiagramCreationFromSiriusDiagramPrototypeWithRootCreation(this.rootModel, DIAGRAM_NAME, DIAGRAM_TYPE, Activity.class);
		// Activity1 already exists, the new diagram operation should create Activity2.
		assertEquals(2, this.getAllActivities());
	}

	@Test
	public void createActivityDiagramTestFromActivity() throws Exception {
		Activity activity1 = (Activity) this.rootModel.getMember(ACTIVITY1);
		this.checkDiagramCreationFromSiriusDiagramPrototypeWithRootCreation(activity1, DIAGRAM_NAME, DIAGRAM_TYPE, Activity.class);
		assertTrue(activity1.getOwnedElements().isEmpty());
		assertEquals(1, this.getAllActivities());
	}

	@Test
	public void createActivityDiagramTestFromFunctionBehavior() throws Exception {
		FunctionBehavior functionBehavior1 = (FunctionBehavior) this.rootModel.getMember(FUNCTION_BEHAVIOR1);
		this.checkDiagramCreationFromSiriusDiagramPrototypeWithRootCreation(functionBehavior1, DIAGRAM_NAME, DIAGRAM_TYPE, Activity.class);
		assertEquals(1, this.ownedActivities(functionBehavior1).size());
		assertEquals(2, this.getAllActivities());
	}

	private List<Activity> ownedActivities(Element element) {
		return element.getOwnedElements().stream().filter(Activity.class::isInstance).map(Activity.class::cast).collect(Collectors.toList());
	}

	private int getAllActivities() {
		Stream<EObject> rootContentStream = StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(this.rootModel.eAllContents(), Spliterator.NONNULL), false);
		int interactionNumber = rootContentStream.filter(Activity.class::isInstance)//
				.collect(Collectors.toList())//
				.size();
		return interactionNumber;
	}
}
