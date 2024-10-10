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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.creation.topnodes;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateTopNodeOnDiagramTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.MappingTypes;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class groups all Tests about allowed creations on the root of the UseCase Diagram represented by a {@link Model}.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/creation/topNodes/TopNode_CreationTest.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class UCDCreateTopNodeOnUseCaseTest extends AbstractCreateTopNodeOnDiagramTests {

	/**
	 * The name of the diagram to open
	 */
	protected static final String DIAGRAM_NAME = "TopNode_UseCaseDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private final EReference containmentFeature;

	private final Class<? extends Element> expectedType;


	/**
	 * Default Constructor.
	 *
	 */
	public UCDCreateTopNodeOnUseCaseTest(String creationToolId, String nodeMappingType, EReference containmentFeature, Class<? extends Element> expectedType) {
		this.creationToolId = creationToolId;
		this.nodeMappingType = nodeMappingType;
		this.containmentFeature = containmentFeature;
		this.expectedType = expectedType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createTopNodeTest() {
		final Diagram diagram = getDiagram();
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(getSemanticOwner(), this.containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeCreationChecker(diagram, getTopGraphicalContainer(), nodeMappingType);
		createNode(creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker));
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__ACTOR__TOOL, MappingTypes.ACTOR_NODE_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Actor.class },
				{ CreationToolsIds.CREATE__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE, UMLPackage.eINSTANCE.getElement_OwnedComment(), Comment.class },
				{ CreationToolsIds.CREATE__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE, UMLPackage.eINSTANCE.getNamespace_OwnedRule(), Constraint.class },
				{ CreationToolsIds.CREATE__USE_CASE__TOOL, MappingTypes.USE_CASE_NODE_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), UseCase.class }
		});
	}

}
