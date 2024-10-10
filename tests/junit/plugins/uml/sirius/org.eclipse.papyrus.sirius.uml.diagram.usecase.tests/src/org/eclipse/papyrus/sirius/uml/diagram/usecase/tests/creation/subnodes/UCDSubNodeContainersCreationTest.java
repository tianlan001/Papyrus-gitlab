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
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;
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
public class UCDSubNodeContainersCreationTest extends AbstractCreateNodeTests {

	private static final String DIAGRAM_NAME = "SubNodeUseCaseDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private final String nodeCompartmentType;

	private final EReference containmentFeature;

	private String semanticOwnerName;

	private final Class<? extends Element> expectedType;

	/**
	 * Default Constructor.
	 *
	 */
	public UCDSubNodeContainersCreationTest(String creationToolId, String nodeMappingType, String nodeCompartmentType, EReference containmentFeature, Class<? extends Element> expectedType) {
		this.creationToolId = creationToolId;
		this.nodeMappingType = nodeMappingType;
		this.nodeCompartmentType = nodeCompartmentType;
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
	public void createNodeContainerIntoStateMachine() {
		createNodeContainer("Package1", MappingTypes.PACKAGE_NODE_PACKAGEDELEMENTS_COMPARTMENTS_TYPE); //$NON-NLS-1$
	}

	private void createNodeContainer(final String semanticOwnerName, final String containerMappingTypeName) {
		this.semanticOwnerName = semanticOwnerName;
		final EObject graphicalContainer = getSubNodeOfGraphicalContainer(containerMappingTypeName);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(getSemanticOwner(), this.containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeContainerCreationChecker(getDiagram(), graphicalContainer, this.nodeMappingType, this.nodeCompartmentType);
		createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__ACTIVITY__TOOL, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_NODE_USECASES_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Activity.class },
				{ CreationToolsIds.CREATE__CLASS__TOOL, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_USECASES_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), org.eclipse.uml2.uml.Class.class },
				{ CreationToolsIds.CREATE__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_USECASES_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Component.class },
				{ CreationToolsIds.CREATE__INTERACTION__TOOL, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_USECASES_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Interaction.class },
				{ CreationToolsIds.CREATE__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PACKAGEDELEMENTS_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Package.class },
				{ CreationToolsIds.CREATE__STATE_MACHINE__TOOL, MappingTypes.STATE_MACHINE_NODE_TYPE, MappingTypes.STATE_MACHINE_NODE_USECASES_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), StateMachine.class },
		});
	}

}
