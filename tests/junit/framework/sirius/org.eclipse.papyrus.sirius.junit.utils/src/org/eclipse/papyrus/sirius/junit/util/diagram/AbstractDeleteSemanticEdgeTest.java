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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;

/**
 * Abstract Test for semantic edge deletion.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class AbstractDeleteSemanticEdgeTest extends AbstractSiriusDiagramTests {

	/**
	 * This method deletes the edge and checks the status of the current diagram (synchronized or not synchronized)
	 * 
	 * @param edgeToDelete
	 *            the DEdge to delete.
	 * @param semanticContainer
	 *            the semantic owner containing the relationship.
	 * @param containmentFeature
	 *            the containment feature.
	 */
	public void deleteEdge(DEdge edgeToDelete, EObject semanticContainer, EReference containmentFeature) {
		checkSiriusDiagramSynchronization(false);

		// setup
		assertTrue(fixture.getModel() instanceof Package);
		Element edgeToBeDeleted = (Element) edgeToDelete.getTarget();
		assertNotNull("We can't find the edge to destroy", edgeToBeDeleted); //$NON-NLS-1$

		DiagramEditPart diagramEditpart = fixture.getActiveDiagram();
		Diagram diagram = diagramEditpart.getDiagramView();
		assertNotNull("We can't find the class diagram", diagram); //$NON-NLS-1$
		int diagramEdgesSizeBefore = diagram.getEdges().size();
		int featureSizeBefore = getContainmentList(semanticContainer, containmentFeature).size();

		fixture.applySemanticDeletionTool(edgeToDelete);
		fixture.flushDisplayEvents();

		// the semantic element has been destroyed
		assertFalse("The UML model must not contain the destroyed edge", getContainmentList(semanticContainer, containmentFeature).contains(edgeToBeDeleted)); //$NON-NLS-1$

		// the view has been destroyed too in the diagram
		assertEquals("The UseCase diagram must not contain the view of the edge after the destruction", diagramEdgesSizeBefore - 1, diagram.getEdges().size()); //$NON-NLS-1$

		// undo
		fixture.getEditingDomain().getCommandStack().undo();
		fixture.flushDisplayEvents();

		// the semantic and the view elements have been reset
		assertNotNull("The UML model must contain the destroyed edge after undoing the destruction", edgeToBeDeleted); //$NON-NLS-1$
		assertEquals("The diagram must contain the view of the edge after undoing the destruction", diagramEdgesSizeBefore, diagram.getEdges().size()); //$NON-NLS-1$

		// redo
		fixture.getEditingDomain().getCommandStack().redo();
		fixture.flushDisplayEvents();
		assertEquals("The UML model must not contain the destroyed edge after redoing the destruction", featureSizeBefore - 1, getContainmentList(semanticContainer, containmentFeature).size()); //$NON-NLS-1$
		assertEquals("The diagram must not contain the view of the edge after redoing the destruction", diagramEdgesSizeBefore - 1, diagram.getEdges().size()); //$NON-NLS-1$
	}


	@SuppressWarnings("unchecked")
	private List<? extends Element> getContainmentList(EObject semanticContainer, EReference containmentFeature) {
		return (List<? extends Element>) Optional.of(semanticContainer.eGet(containmentFeature))
				.filter(List.class::isInstance)
				.map(List.class::cast)
				.stream()
				.flatMap(List::stream)
				.filter(Element.class::isInstance)
				.map(Element.class::cast)
				.collect(Collectors.toList());
	}

}
