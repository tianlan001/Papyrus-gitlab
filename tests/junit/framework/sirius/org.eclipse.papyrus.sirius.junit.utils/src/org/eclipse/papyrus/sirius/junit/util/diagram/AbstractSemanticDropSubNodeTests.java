/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *  Obeo - Additional contributions.
 *****************************************************************************/
package org.eclipse.papyrus.sirius.junit.util.diagram;

import static org.junit.Assert.fail;

import java.util.Optional;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DDiagramElementContainer;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Assert;

/**
 * 
 * TODO //adapter from AbstractSubNodeListElementCreationTests maybe a common class could help us
 */
public abstract class AbstractSemanticDropSubNodeTests extends AbstractSiriusDiagramTests {



	/**
	 * Drop the element to a container.
	 * 
	 * @param semanticOwner
	 *            the semantic parent containing the element to drop.
	 * @param elementToDrop
	 *            the semantic element to drop.
	 * @param containerMappingType
	 *            the mapping of the graphical container where the element is dropped.
	 * @param compartmentMappingType
	 *            the type of the compartment in which we want to drop the element.
	 * @param dropToolId
	 *            the drop tool to test.
	 * @param expectedMappingType
	 *            the expected mapping once the element is dropped.
	 */
	protected void dropElementToContainer(NamedElement semanticOwner, Element elementToDrop, String containerMappingType, String compartmentMappingType, String dropToolId, String expectedMappingType) {
		checkSiriusDiagramSynchronization(false);
		final DSemanticDiagram siriusDiagram = this.fixture.getActiveSiriusDiagram();
		Assert.assertNotNull(siriusDiagram);
		Assert.assertFalse("The diagram must be unsynchronized to test drop", siriusDiagram.isSynchronized()); //$NON-NLS-1$
		DNodeContainer containerNode = (DNodeContainer) getNodeFromDiagram(semanticOwner.getName(), containerMappingType);
		final DDiagramElement createdElement = dropNodeInDNodeContainer(containerNode, compartmentMappingType, dropToolId, expectedMappingType, elementToDrop);
		String createdElementMappingName = createdElement.getDiagramElementMapping().getName();
		Assert.assertEquals("The dropped element is not the expected one", expectedMappingType, createdElementMappingName); //$NON-NLS-1$
	}

	/**
	 * Drop the element to a sub container in the diagram elements hierarchy. It will search the container in all {@link DDiagramElement}s.
	 * 
	 * @param semanticOwner
	 *            the semantic parent containing the element to drop.
	 * @param elementToDrop
	 *            the semantic element to drop.
	 * @param containerMappingType
	 *            the mapping of the graphical container where the element is dropped.
	 * @param compartmentMappingType
	 *            the type of the compartment in which we want to drop the element.
	 * @param dropToolId
	 *            the drop tool to test.
	 * @param expectedMappingType
	 *            the expected mapping once the element is dropped.
	 */
	protected void dropElementToSubContainers(NamedElement semanticOwner, Element elementToDrop, String containerMappingType, String compartmentMappingType, String dropToolId, String expectedMappingType) {
		checkSiriusDiagramSynchronization(false);
		final DSemanticDiagram siriusDiagram = this.fixture.getActiveSiriusDiagram();
		Assert.assertNotNull(siriusDiagram);
		Assert.assertFalse("The diagram must be unsynchronized to test drop", siriusDiagram.isSynchronized()); //$NON-NLS-1$
		Optional<DNodeContainer> optionalContainerNode = getElementsFromDiagramBySemanticName(semanticOwner.getName(), containerMappingType).stream().filter(DNodeContainer.class::isInstance).map(DNodeContainer.class::cast).findFirst();
		if (optionalContainerNode.isPresent()) {
			final DDiagramElement createdElement = dropNodeInDNodeContainer(optionalContainerNode.get(), compartmentMappingType, dropToolId, expectedMappingType, elementToDrop);
			String createdElementMappingName = createdElement.getDiagramElementMapping().getName();
			Assert.assertEquals("The dropped element is not the expected one", expectedMappingType, createdElementMappingName); //$NON-NLS-1$
		} else {
			fail("Impossible to find the container matching the given semantic owner and container mapping"); //$NON-NLS-1$
		}
	}

	/**
	 * 
	 * @param containerNode
	 *            the containerNode in which the UML element should be dropped.
	 * @param compartmentMappingType
	 *            the type of the compartment in which we want to do the creation
	 * @param dropToolId
	 *            the id of the creation tool to test
	 * @param expectedMappingType
	 *            the expected mapping type for the created view
	 * @param elementToBeDropped
	 *            the semantic element to drop.
	 * @return
	 *         the created NodeListElement
	 */
	private final DDiagramElement dropNodeInDNodeContainer(final DNodeContainer containerNode, final String compartmentMappingType, final String dropToolId, final String expectedMappingType, final Element elementToBeDropped) {
		final Diagram diagram = getDiagram();

		final DDiagramElementContainer subNodeContainer = getDDiagramElementContainerInContainer(containerNode, compartmentMappingType);
		Assert.assertNotNull(NLS.bind("We didn't find the compartment type {0}", compartmentMappingType), subNodeContainer); //$NON-NLS-1$
		Assert.assertEquals("The compartment must be empty", 0, subNodeContainer.getElements().size()); //$NON-NLS-1$
		final DDiagram diagramRepresentation = (DDiagram) diagram.getElement();

		DDiagramElementContainer graphicalNodeContainer = null;
		if (isBorderNode(expectedMappingType)) {
			graphicalNodeContainer = containerNode;
		} else {
			graphicalNodeContainer = subNodeContainer;
		}
		int intialElementNumber = graphicalNodeContainer.getElements().size();

		// create the element in the container
		boolean result = this.fixture.applyContainerDropDescriptionTool(diagramRepresentation, dropToolId, subNodeContainer, elementToBeDropped);
		Assert.assertTrue("The drop of element failed", result); //$NON-NLS-1$
		this.fixture.flushDisplayEvents();

		Assert.assertEquals(intialElementNumber + 1, graphicalNodeContainer.getElements().size());

		DDiagramElement createdElementRepresentation = graphicalNodeContainer.getElements().get(0);

		Assert.assertEquals("The mapping is not the expected one", expectedMappingType, createdElementRepresentation.getMapping().getName()); //$NON-NLS-1$
		Assert.assertEquals("The created element representation must have 1 associated semantic element", 1, createdElementRepresentation.getSemanticElements().size()); //$NON-NLS-1$

		// undo
		this.fixture.getEditingDomain().getCommandStack().undo();
		this.fixture.flushDisplayEvents();
		Assert.assertEquals(intialElementNumber, graphicalNodeContainer.getElements().size());

		// redo
		this.fixture.getEditingDomain().getCommandStack().redo();
		this.fixture.flushDisplayEvents();
		Assert.assertEquals(intialElementNumber + 1, graphicalNodeContainer.getElements().size());
		return graphicalNodeContainer.getElements().get(0);
	}



	/**
	 * Check if a BorderNode will be created.
	 * 
	 * @param elementToDropMappingType
	 *            the expected type after creation of the element
	 * @return <code>true</code> if the element is a BorderNode, <code>false</code> otherwise.
	 */
	protected boolean isBorderNode(String elementToDropMappingType) {
		return false;
	}

}
