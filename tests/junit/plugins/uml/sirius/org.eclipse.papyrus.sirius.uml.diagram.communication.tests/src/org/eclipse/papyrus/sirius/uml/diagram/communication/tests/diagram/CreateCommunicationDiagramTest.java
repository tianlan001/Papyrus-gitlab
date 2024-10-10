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
package org.eclipse.papyrus.sirius.uml.diagram.communication.tests.diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDiagramCreationTests;
import org.eclipse.uml2.uml.FunctionBehavior;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.junit.Test;

/**
 * Check Communication diagram creation.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/createDiagram/createDiagram.di")
public class CreateCommunicationDiagramTest extends AbstractDiagramCreationTests {

	private static final String INTERACTION1 = "Interaction1"; //$NON-NLS-1$

	private static final String OPAQUE_BEHAVIOR1 = "OpaqueBehavior1"; //$NON-NLS-1$

	private static final String FUNCTION_BEHAVIOR1 = "FunctionBehavior1"; //$NON-NLS-1$

	private static final String DIAGRAM_TYPE = "org.eclipse.papyrus.sirius.uml.diagram.communication"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "newName"; //$NON-NLS-1$

	@Test
	public void createCommunicationDiagramOnCommunicationTestFromPackageRoot() throws Exception {
		checkDiagramCreationFromSiriusDiagramPrototypeWithRootCreation(this.rootModel, DIAGRAM_NAME, DIAGRAM_TYPE, Interaction.class);
	}

	@Test
	public void createCommunicationDiagramOnCommunicationTestFromBehaviour() throws Exception {
		FunctionBehavior functionBehavior = (FunctionBehavior) this.rootModel.getMember(FUNCTION_BEHAVIOR1);
		checkDiagramCreationFromSiriusDiagramPrototypeWithRootCreation(functionBehavior, DIAGRAM_NAME, DIAGRAM_TYPE, Interaction.class);
		assertEquals(1, functionBehavior.getOwnedBehaviors().size());
		assertTrue(functionBehavior.getOwnedBehaviors().get(0) instanceof Interaction);
		assertNotNull(functionBehavior.getClassifierBehavior());
		assertTrue(functionBehavior.getClassifierBehavior() instanceof Interaction);
	}

	@Test
	public void createCommunicationDiagramOnCommunicationTestFromInteraction() throws Exception {
		OpaqueBehavior opaqueBehavior = (OpaqueBehavior) this.rootModel.getMember(OPAQUE_BEHAVIOR1);
		Interaction interaction = (Interaction) opaqueBehavior.getMember(INTERACTION1);
		checkDiagramCreationFromSiriusDiagramPrototypeWithRootCreation(interaction, DIAGRAM_NAME, DIAGRAM_TYPE, Interaction.class);
		// check that no new Interaction has been created
		Stream<EObject> rootContentStream = StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(this.rootModel.eAllContents(), Spliterator.NONNULL), false);// ;
		int interactionNumber = rootContentStream.filter(Interaction.class::isInstance)//
				.collect(Collectors.toList())//
				.size();
		assertEquals(1, interactionNumber);
	}
}
