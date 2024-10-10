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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.creation.subnodes;

import java.util.Arrays;
import java.util.Collection;
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
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to sub-node creation into a Package container for the Deployment diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/creation/subNodes/subNodes.di")
@RunWith(value = Parameterized.class)
public class SubNodeCreationIntoPackageContainerTest extends AbstractCreateNodeTests {

	private static final String DIAGRAM_NAME = "SubNodes_DeploymentDiagramSirius"; //$NON-NLS-1$

	private static final String MODEL1 = "Model1"; //$NON-NLS-1$

	private static final String PACKAGE1 = "Package1"; //$NON-NLS-1$

	private EReference containmentFeature;

	private final String creationToolId;

	private final Class<? extends Element> expectedType;

	private final String nodeMappingType;

	private String semanticOwnerName;

	public SubNodeCreationIntoPackageContainerTest(EReference containmentFeature, Class<? extends Element> expectedType) {
		this.creationToolId = CreationToolsIds.getToolId(expectedType.getSimpleName());
		this.nodeMappingType = MappingTypes.getMappingType(expectedType.getSimpleName());
		this.expectedType = expectedType;
		this.containmentFeature = containmentFeature;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoModel() {
		String modelName = Model.class.getSimpleName();
		this.createNodeIntoContainer(MODEL1, MappingTypes.getMappingType(modelName), MappingTypes.getCompartmentMappingType(modelName), this.containmentFeature);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoPackage() {
		String packageName = Package.class.getSimpleName();
		this.createNodeIntoContainer(PACKAGE1, MappingTypes.getMappingType(packageName), MappingTypes.getCompartmentMappingType(packageName), this.containmentFeature);
	}

	@Override
	protected Element getSemanticOwner() {
		return this.root.getMember(this.semanticOwnerName);
	}

	@Override
	protected EObject getTopGraphicalContainer() {
		final DDiagram ddiagram = this.getDDiagram();

		final DSemanticDecorator result;
		Element semanticOwner = this.getSemanticOwner();
		// @formatter:off
		Optional<DSemanticDecorator> optionalDSemanticDecorator = ddiagram.getOwnedDiagramElements().stream()
				.filter(DSemanticDecorator.class::isInstance)
				.map(DSemanticDecorator.class::cast)
				.filter(semanticDecorator -> semanticDecorator.getTarget().equals(semanticOwner))
				.findFirst();
		// @formatter:on
		Assert.assertTrue(optionalDSemanticDecorator.isPresent());
		result = optionalDSemanticDecorator.get();
		Assert.assertEquals(semanticOwner, result.getTarget());
		return result;
	}

	private void createNodeIntoContainer(final String semanticOwnerName, final String nodeContainerType, final String nodeCompartmentContainerType, final EReference containmentFeature) {
		this.semanticOwnerName = semanticOwnerName;
		final EObject graphicalContainer = this.getSubNodeOfGraphicalContainer(nodeCompartmentContainerType);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(this.getSemanticOwner(), containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeCreationChecker(this.getDiagram(), graphicalContainer, this.nodeMappingType);
		this.createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	@Parameters(name = "{index} Test {1} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{ UMLPackage.eINSTANCE.getElement_OwnedComment(), Comment.class },
			{ UMLPackage.eINSTANCE.getNamespace_OwnedRule(), Constraint.class },
			{ UMLPackage.eINSTANCE.getPackage_PackagedElement(), DeploymentSpecification.class }
		});
	}
}
