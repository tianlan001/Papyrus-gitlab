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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.GraphicalOwnerUtils;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DDiagramElementContainer;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;

/**
 * Abstract Test for semantic node deletion.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class AbstractDeleteSemanticNodeTest extends AbstractSiriusDiagramTests {

	/**
	 * This method deletes the node from diagram and checks the status of the current diagram (synchronized or not synchronized)
	 * 
	 * @param elementNameToDestroy
	 *            the name of the semantic element to destroy
	 * @param mappingTypeName
	 *            the type of the element to destroy
	 * @param isSynchronized
	 *            <code>true</code> if the diagram must be synchronized, <code>false</code> otherwise.
	 */
	protected void deleteNode(final String elementNameToDestroy, final String mappingTypeName, final boolean isSynchronized) {
		deleteNode(elementNameToDestroy, mappingTypeName, getDDiagram(), isSynchronized);
	}

	/**
	 * This method deletes the node from a graphical container and checks the status of the current diagram (synchronized or not synchronized)
	 * 
	 * @param elementNameToDestroy
	 *            the name of the semantic element to destroy,
	 * @param mappingTypeName
	 *            the mapping type name of the node to destroy,
	 * @param graphicalContainer
	 *            the graphical container to use to delete the node,
	 * @param semanticContainer
	 *            the semantic container of the semantic element to destroy; <code>null</code> if the graphical container is the semantic container,
	 * @param isSynchronized
	 *            <code>true</code> if the diagram must be synchronized.
	 */
	protected void deleteNode(final String elementNameToDestroy, final String mappingTypeName, EObject graphicalContainer, NamedElement semanticContainer, final boolean isSynchronized) {
		NamedElement parentElement = null;
		if (semanticContainer == null) {
			Package rootModel = getModel();
			if (graphicalContainer instanceof DSemanticDiagram) {
				parentElement = rootModel;
			} else if (graphicalContainer instanceof DDiagramElementContainer) {
				parentElement = (NamedElement) ((DRepresentationElement) graphicalContainer).getSemanticElements().get(0);
			}
		} else {
			parentElement = semanticContainer;
		}
		int nbContainerElementInitial = GraphicalOwnerUtils.getGraphicalOwnerChildrenSize(graphicalContainer);
		checkSiriusDiagramSynchronization(isSynchronized);
		String elementType = null;
		Object semanticElement = getMemberFromParent(parentElement, elementNameToDestroy);
		if (semanticElement != null) {
			elementType = semanticElement.getClass().getSimpleName();
		}

		AbstractDNode nodeToDelete = getNodeFromContainer(elementNameToDestroy, mappingTypeName, graphicalContainer, parentElement);
		Assert.assertNotNull("Element to delete not found on diagram.", nodeToDelete); //$NON-NLS-1$

		fixture.applySemanticDeletionTool(nodeToDelete);
		fixture.flushDisplayEvents();

		// the semantic element has been destroyed
		Assert.assertNull("The UML model must not contain the destroyed " + elementType + " element", getMemberFromParent(parentElement, elementNameToDestroy)); //$NON-NLS-1$ //$NON-NLS-2$
		// the view has been destroyed from its container and so from the diagram
		Assert.assertEquals("The graphical container must not contains view after destruction of the " + elementType + " element", nbContainerElementInitial - 1, GraphicalOwnerUtils.getGraphicalOwnerChildrenSize(graphicalContainer)); //$NON-NLS-1$ //$NON-NLS-2$

		// undo
		fixture.getEditingDomain().getCommandStack().undo();
		fixture.flushDisplayEvents();
		// the semantic and the view elements have been reset
		Assert.assertNotNull("The UML model must contain the destroyed " + elementType + " element after undoing the destruction", getMemberFromParent(parentElement, elementNameToDestroy)); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The graphical container must contain the view of the " + elementType + " element after undoing the destruction", nbContainerElementInitial, GraphicalOwnerUtils.getGraphicalOwnerChildrenSize(graphicalContainer)); //$NON-NLS-1$ //$NON-NLS-2$

		// redo
		fixture.getEditingDomain().getCommandStack().redo();
		fixture.flushDisplayEvents();
		Assert.assertNull("The UML model must not contain the destroyed " + elementType + " element after redoing the destruction", getMemberFromParent(parentElement, elementNameToDestroy)); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The graphical container must not contain the view of the " + elementType + " element after redoing the destruction", nbContainerElementInitial - 1, GraphicalOwnerUtils.getGraphicalOwnerChildrenSize(graphicalContainer)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This method deletes the node from a graphical container and checks the status of the current diagram (synchronized or not synchronized)
	 * 
	 * @param elementNameToDestroy
	 *            the name of the semantic element to destroy,
	 * @param mappingTypeName
	 *            the mapping type name of the node to destroy,
	 * @param graphicalContainer
	 *            the graphical container to use to destroy the node,
	 * @param isSynchronized
	 *            <code>true</code> if the diagram must be synchronized.
	 */
	protected void deleteNode(final String elementNameToDestroy, final String mappingTypeName, EObject graphicalContainer, final boolean isSynchronized) {
		deleteNode(elementNameToDestroy, mappingTypeName, graphicalContainer, null, isSynchronized);
	}

	/**
	 * Get semantic element from a parent element.
	 * 
	 * @param parentElement
	 *            the parent element which contain the element to find,
	 * @param elementNameToDestroy
	 *            the name of the element to extract,
	 * @return semantic element from a parent element.
	 */
	private Object getMemberFromParent(NamedElement parentElement, String elementNameToDestroy) {
		NamedElement element = null;
		if (parentElement instanceof Model) {
			element = ((Model) parentElement).getMember(elementNameToDestroy);
		} else {
			for (EObject child : parentElement.eContents()) {
				if (child instanceof NamedElement && elementNameToDestroy.equals(((NamedElement) child).getName())) {
					element = (NamedElement) child;
				}
			}
		}
		return element;
	}

}
