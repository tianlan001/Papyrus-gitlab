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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.creation.subnodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UseCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the creation of subnode containers for UCD.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/creation/subNodes/subNodes.di")
@RunWith(value = Parameterized.class)
public class UCDSubNodesCreationTest extends AbstractCreateNodeTests {

	private static final String DIAGRAM_NAME = "SubNodeUseCaseDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private EReference containmentFeature;

	private String semanticOwnerName;

	private final Class<? extends Element> expectedType;

	/**
	 * Default Constructor.
	 *
	 */
	public UCDSubNodesCreationTest(String creationToolId, String nodeMappingType, EReference containmentFeature, Class<? extends Element> expectedType) {
		this.creationToolId = creationToolId;
		this.nodeMappingType = nodeMappingType;
		this.containmentFeature = containmentFeature;
		this.expectedType = expectedType;
	}

	@Override
	protected Element getSemanticOwner() {
		return this.root.getMember(this.semanticOwnerName);
	}

	@Override
	protected EObject getTopGraphicalContainer() {
		final DDiagram ddiagram = getDDiagram();
		Element semanticOwner = getSemanticOwner();
		// @formatter:off
		Optional<DSemanticDecorator> optionalDSemanticDecorator = ddiagram.getOwnedDiagramElements().stream()
				.filter(DSemanticDecorator.class::isInstance)
				.map(DSemanticDecorator.class::cast)
				.filter(semanticDecorator -> semanticDecorator.getTarget().equals(semanticOwner))
				.findFirst();
		// @formatter:on
		Assert.assertTrue(optionalDSemanticDecorator.isPresent());
		final DSemanticDecorator element = optionalDSemanticDecorator.get();
		Assert.assertEquals(semanticOwner, element.getTarget());
		return element;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoActivity() {
		List<String> forbiddenTools = List.of(CreationToolsIds.CREATE__ACTOR__TOOL);
		if (!forbiddenTools.contains(this.creationToolId)) {
			createNodeIntoContainer("Activity1", MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_NODE_USECASES_COMPARTMENTS_TYPE); //$NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoClass() {
		List<String> forbiddenTools = List.of(CreationToolsIds.CREATE__ACTOR__TOOL);
		if (!forbiddenTools.contains(this.creationToolId)) {
			createNodeIntoContainer("Class1", MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_USECASES_COMPARTMENTS_TYPE); //$NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoComponent() {
		List<String> forbiddenTools = List.of(CreationToolsIds.CREATE__ACTOR__TOOL);
		if (!forbiddenTools.contains(this.creationToolId)) {
			createNodeIntoContainer("Component1", MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_USECASES_COMPARTMENTS_TYPE); //$NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoInteraction() {
		List<String> forbiddenTools = List.of(CreationToolsIds.CREATE__ACTOR__TOOL);
		if (!forbiddenTools.contains(this.creationToolId)) {
			createNodeIntoContainer("Interaction1", MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_USECASES_COMPARTMENTS_TYPE); //$NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoPackage() {
		List<String> forbiddenTools = List.of(CreationToolsIds.CREATE__ACTOR__TOOL);
		if (!forbiddenTools.contains(this.creationToolId)) {
			if (CreationToolsIds.CREATE__USE_CASE__TOOL.equals(this.creationToolId)) {
				this.containmentFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
			}
			createNodeIntoContainer("Package1", MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PACKAGEDELEMENTS_COMPARTMENTS_TYPE); //$NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoStateMachine() {
		List<String> forbiddenTools = List.of(CreationToolsIds.CREATE__ACTOR__TOOL);
		if (!forbiddenTools.contains(this.creationToolId)) {
			createNodeIntoContainer("StateMachine1", MappingTypes.STATE_MACHINE_NODE_TYPE, MappingTypes.STATE_MACHINE_NODE_USECASES_COMPARTMENTS_TYPE); //$NON-NLS-1$
		}
	}

	private void createNodeIntoContainer(final String semanticOwnerName, final String nodeContainerType, final String nodeCompartmentContainerType) {
		final String containerType = nodeCompartmentContainerType;
		this.semanticOwnerName = semanticOwnerName;
		final EObject graphicalContainer = getSubNodeOfGraphicalContainer(containerType);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(getSemanticOwner(), this.containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeCreationChecker(getDiagram(), graphicalContainer, this.nodeMappingType);
		createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__ACTOR__TOOL, MappingTypes.ACTOR_NODE_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Actor.class },
				{ CreationToolsIds.CREATE__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE, UMLPackage.eINSTANCE.getElement_OwnedComment(), Comment.class },
				{ CreationToolsIds.CREATE__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE, UMLPackage.eINSTANCE.getNamespace_OwnedRule(), Constraint.class },
				{ CreationToolsIds.CREATE__USE_CASE__TOOL, MappingTypes.USE_CASE_NODE_TYPE, UMLPackage.eINSTANCE.getClassifier_OwnedUseCase(), UseCase.class },
		});
	}

}
