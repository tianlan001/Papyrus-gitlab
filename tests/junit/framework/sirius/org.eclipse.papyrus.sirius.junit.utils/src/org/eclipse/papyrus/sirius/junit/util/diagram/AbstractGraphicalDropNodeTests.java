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
package org.eclipse.papyrus.sirius.junit.util.diagram;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DDiagramElementContainer;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Assert;

/**
 * Abstract Test for Graphical Drag and Drop.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public abstract class AbstractGraphicalDropNodeTests extends AbstractSiriusDiagramTests {


	/**
	 * Drop the given element into the given container.
	 *
	 * @param elementToDrop
	 *            the semantic name of the element to drop.
	 * @param graphicalOwnerName
	 *            the name of the container where the element should be dropped.
	 * @param containerMappingType
	 *            the mapping type of the target container where the element should be dropped.
	 * @param compartmentMappingType
	 *            the compartment mapping type of the target container.
	 * @param dropToolId
	 *            the drop tool id to use.
	 * @param expectedMappingType
	 *            the dropped element expected mapping once dropped.
	 */
	protected void dropElementIntoContainer(String elementToDrop, String graphicalOwnerName, String containerMappingType, String compartmentMappingType, String dropToolId, String expectedMappingType) {
		this.dropElementIntoContainer(elementToDrop, graphicalOwnerName, containerMappingType, compartmentMappingType, dropToolId, expectedMappingType, null, null);
	}

	/**
	 * Drop the given element into the given container.
	 *
	 * @param elementToDrop
	 *            the semantic name of the element to drop.
	 * @param graphicalOwnerName
	 *            the name of the container where the element should be dropped.
	 * @param containerMappingType
	 *            the mapping type of the target container where the element should be dropped.
	 * @param compartmentMappingType
	 *            the compartment mapping type of the target container.
	 * @param dropToolId
	 *            the drop tool id to use.
	 * @param expectedMappingType
	 *            the dropped element expected mapping once dropped.
	 * @param newSemanticOwnerName
	 *            the new semantic owner name (can be different from the graphical one). Can be null.
	 * @param newContainmentFeatureName
	 *            the containment feature name used to reference the elementToDrop from the new target. Can be null.
	 */
	protected void dropElementIntoContainer(String elementToDrop, String graphicalOwnerName, String containerMappingType, String compartmentMappingType, String dropToolId, String expectedMappingType, String newSemanticOwnerName,
			String newContainmentFeatureName) {
		this.checkSiriusDiagramSynchronization(false);
		final DSemanticDiagram siriusDiagram = this.fixture.getActiveSiriusDiagram();
		assertNotNull(siriusDiagram);
		assertFalse("The diagram must be unsynchronized to test drop", siriusDiagram.isSynchronized()); //$NON-NLS-1$
		List<DDiagramElement> elementsToDrop = this.getElementsFromDiagramBySemanticName(elementToDrop, expectedMappingType);
		assertTrue("We should find only one element to drop.", elementsToDrop.size() == 1); //$NON-NLS-1$
		Element rootElement = this.getRootElement();
		Assert.assertTrue(rootElement instanceof Namespace);
		Optional<DNodeContainer> optionalContainerNode = this.getElementsFromDiagramBySemanticName(graphicalOwnerName, containerMappingType).stream()
				.filter(DNodeContainer.class::isInstance)//
				.map(DNodeContainer.class::cast)//
				.findFirst();//
		if (optionalContainerNode.isPresent()) {
			NamedElement newSemanticOwner = Optional.ofNullable(newSemanticOwnerName)
					.flatMap(this::getElementByNameFromRoot)
					.orElse(null);
			this.dropInDNodeContainer(optionalContainerNode.get(), compartmentMappingType, dropToolId, expectedMappingType, elementsToDrop.get(0), newSemanticOwner, newContainmentFeatureName);
		} else {
			fail(String.format("Impossible to find the container matching the given graphical owner '%s' and container mapping '%s'", graphicalOwnerName, containerMappingType)); //$NON-NLS-1$
		}
	}


	/**
	 * Drop the given element to diagram.
	 *
	 * @param elementToDrop
	 *            the semantic name of the element to drop.
	 * @param dropToolId
	 *            the drop tool id to use.
	 * @param expectedMappingType
	 *            the dropped element expected mapping once dropped.
	 * @param newContainmentFeatureName
	 *            the containment feature name used to reference the elementToDrop from the new target.
	 */
	protected void dropElementToDiagram(String elementToDrop, String dropToolId, String expectedMappingType, String newContainmentFeatureName) {
		this.checkSiriusDiagramSynchronization(false);
		final DSemanticDiagram siriusDiagram = this.fixture.getActiveSiriusDiagram();
		assertNotNull(siriusDiagram);
		assertFalse("The diagram must be unsynchronized to test drop", siriusDiagram.isSynchronized()); //$NON-NLS-1$
		List<DDiagramElement> elementsToDrop = this.getElementsFromDiagramBySemanticName(elementToDrop, expectedMappingType);
		assertTrue("We should find only one element to drop.", elementsToDrop.size() == 1); //$NON-NLS-1$
		this.dropNodeInDiagram(dropToolId, expectedMappingType, elementsToDrop.get(0), newContainmentFeatureName);
	}

	/**
	 * Drop the given element to diagram.
	 *
	 * @param elementToDrop
	 *            the semantic name of the element to drop.
	 * @param dropToolId
	 *            the drop tool id to use.
	 * @param expectedMappingType
	 *            the dropped element expected mapping once dropped.
	 */
	protected void dropElementToDiagram(String elementToDrop, String dropToolId, String expectedMappingType) {
		this.dropElementToDiagram(elementToDrop, dropToolId, expectedMappingType, null);
	}


	/**
	 * Drop the given element to the given container.
	 *
	 * @param containerNode
	 *            the node container in which the element will be dropped.
	 * @param compartmentMappingType
	 *            the compartment mapping type of the target container.
	 * @param dropToolId
	 *            the drop tool id to use.
	 * @param expectedMappingType
	 *            the element expected mapping once dropped.
	 * @param elementToBeDropped
	 *            the element to drop
	 * @param newSemanticOwner
	 *            the new semantic owner if different from the graphical container. Can be null.
	 * @param newContainmentFeatureName
	 *            the containment feature name used to reference the elementToDrop from the new target. Can be null.
	 * @return the created diagram element.
	 */
	private final DDiagramElement dropInDNodeContainer(final DNodeContainer containerNode, final String compartmentMappingType, final String dropToolId, final String expectedMappingType, final DDiagramElement elementToBeDropped,
			final Element newSemanticOwner, final String newContainmentFeatureName) {
		final Diagram diagram = this.getDiagram();
		EObject semanticContainer;
		if (newSemanticOwner != null) {
			semanticContainer = newSemanticOwner;
		} else {
			semanticContainer = containerNode.getTarget();
		}
		final DDiagramElementContainer subNodeContainer;
		if (this.isBorderNode(expectedMappingType)) {
			subNodeContainer = containerNode;
		} else {
			subNodeContainer = this.getDDiagramElementContainerInContainer(containerNode, compartmentMappingType);
		}
		assertNotNull(NLS.bind("We didn't find the compartment type {0}", compartmentMappingType), subNodeContainer); //$NON-NLS-1$
		int initialSize = subNodeContainer.getElements().size();
		List<DDiagramElement> initialDiagramElements = List.copyOf(subNodeContainer.getElements());
		final DDiagram diagramRepresentation = (DDiagram) diagram.getElement();
		EObject semanticDroppedElement = elementToBeDropped.getTarget();
		String containmentFeatureNameToUse = newContainmentFeatureName;
		if (containmentFeatureNameToUse == null) {
			containmentFeatureNameToUse = semanticDroppedElement.eContainmentFeature().getName();
		}
		EStructuralFeature targetContainmentFeature = semanticContainer.eClass().getEStructuralFeature(containmentFeatureNameToUse);
		int containementFeatureSizeBefore = ((List<?>) semanticContainer.eGet(targetContainmentFeature)).size();


		// create the element in the container
		boolean result = this.fixture.applyContainerDropDescriptionTool(diagramRepresentation, dropToolId, subNodeContainer, elementToBeDropped);
		assertTrue("The drop of element failed", result); //$NON-NLS-1$
		this.fixture.flushDisplayEvents();

		assertEquals(initialSize + 1, subNodeContainer.getElements().size());
		// We make sure the new container contains the dropped element
		assertEquals(containementFeatureSizeBefore + 1, ((List<?>) semanticContainer.eGet(targetContainmentFeature)).size());

		List<DDiagramElement> subElements = subNodeContainer.getElements();
		assertEquals(initialSize + 1, subElements.size());
		DDiagramElement createdElementRepresentation = this.computeCreatedElement(initialDiagramElements, subNodeContainer.getElements());

		assertEquals("The mapping is not the expected one", expectedMappingType, createdElementRepresentation.getMapping().getName()); //$NON-NLS-1$
		assertEquals("The created element representation must have 1 associated semantic element", 1, createdElementRepresentation.getSemanticElements().size()); //$NON-NLS-1$

		// undo
		this.fixture.getEditingDomain().getCommandStack().undo();
		this.fixture.flushDisplayEvents();
		assertEquals(initialSize, subNodeContainer.getElements().size());
		// We make sure the new container do not contains the dropped element anymore
		assertEquals(containementFeatureSizeBefore, ((List<?>) semanticContainer.eGet(targetContainmentFeature)).size());

		// redo
		this.fixture.getEditingDomain().getCommandStack().redo();
		this.fixture.flushDisplayEvents();
		assertEquals(initialSize + 1, subNodeContainer.getElements().size());
		// We make sure the new container contains the dropped element
		assertEquals(containementFeatureSizeBefore + 1, ((List<?>) semanticContainer.eGet(targetContainmentFeature)).size());
		return this.computeCreatedElement(initialDiagramElements, subNodeContainer.getElements());
	}

	/**
	 * Drop the given element to the diagram.
	 *
	 * @param dropToolId
	 *            the drop tool id to use.
	 * @param expectedMappingType
	 *            the element expected mapping once dropped.
	 * @param elementToBeDropped
	 *            the element to drop.
	 * @param newContainmentFeatureName
	 *            the containment feature name used to reference the elementToDrop from the new target.
	 * @return the created diagram element.
	 */
	protected final DDiagramElement dropNodeInDiagram(final String dropToolId, final String expectedMappingType, final DDiagramElement elementToBeDropped, String newContainmentFeatureName) {
		final Diagram diagram = this.getDiagram();
		final DSemanticDiagram diagramRepresentation = (DSemanticDiagram) diagram.getElement();
		EObject semanticDroppedElement = elementToBeDropped.getTarget();
		EReference containmentFeature = semanticDroppedElement.eContainmentFeature();
		String targetContainmentFeatureName = containmentFeature.getName();
		if (newContainmentFeatureName != null) {
			targetContainmentFeatureName = newContainmentFeatureName;
		}
		EStructuralFeature targetContainmentFeature = diagramRepresentation.getTarget().eClass().getEStructuralFeature(targetContainmentFeatureName);
		int containementFeatureSizeBefore = ((List<?>) diagramRepresentation.getTarget().eGet(targetContainmentFeature)).size();
		int initialDiagramElementsSize = diagramRepresentation.getOwnedDiagramElements().size();
		List<DDiagramElement> initialDiagramElements = List.copyOf(diagramRepresentation.getOwnedDiagramElements());


		// create the element in the container
		boolean result = this.fixture.applyContainerDropDescriptionTool(diagramRepresentation, dropToolId, diagramRepresentation, elementToBeDropped);
		assertTrue("The drop of element failed", result); //$NON-NLS-1$
		this.fixture.flushDisplayEvents();

		// We make sure the new container contains the dropped element
		assertEquals(containementFeatureSizeBefore + 1, ((List<?>) diagramRepresentation.getTarget().eGet(targetContainmentFeature)).size());

		assertEquals(initialDiagramElementsSize + 1, diagramRepresentation.getOwnedDiagramElements().size());
		DDiagramElement createdElementRepresentation = this.computeCreatedElement(initialDiagramElements, diagramRepresentation.getOwnedDiagramElements());

		assertEquals("The mapping is not the expected one", expectedMappingType, createdElementRepresentation.getMapping().getName()); //$NON-NLS-1$
		assertEquals("The created element representation must have 1 associated semantic element", 1, createdElementRepresentation.getSemanticElements().size()); //$NON-NLS-1$

		// undo
		this.fixture.getEditingDomain().getCommandStack().undo();
		this.fixture.flushDisplayEvents();
		assertEquals(initialDiagramElementsSize, diagramRepresentation.getOwnedDiagramElements().size());
		// We make sure the new container do not contains the dropped element anymore
		assertEquals(containementFeatureSizeBefore, ((List<?>) diagramRepresentation.getTarget().eGet(targetContainmentFeature)).size());

		// redo
		this.fixture.getEditingDomain().getCommandStack().redo();
		this.fixture.flushDisplayEvents();
		assertEquals(initialDiagramElementsSize + 1, diagramRepresentation.getOwnedDiagramElements().size());
		// We make sure the new container contains the dropped element
		assertEquals(containementFeatureSizeBefore + 1, ((List<?>) diagramRepresentation.getTarget().eGet(targetContainmentFeature)).size());
		return this.computeCreatedElement(initialDiagramElements, diagramRepresentation.getOwnedDiagramElements());
	}

	private DDiagramElement computeCreatedElement(List<DDiagramElement> initialDiagramElements, EList<DDiagramElement> ownedDiagramElements) {
		List<DDiagramElement> currentDiagramElements = new ArrayList<>(ownedDiagramElements);
		currentDiagramElements.removeAll(initialDiagramElements);
		assertEquals(1, currentDiagramElements.size());
		return currentDiagramElements.get(0);
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
