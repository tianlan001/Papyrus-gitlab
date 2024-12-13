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

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.GraphicalOwnerUtils;
import org.eclipse.papyrus.sirius.junit.utils.rules.SiriusDiagramEditorFixture;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DDiagramElementContainer;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.diagram.business.api.query.DDiagramQuery;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Assert;
import org.junit.Rule;

/**
 * Abstract tests to use for Sirius Diagram Tests
 */
public abstract class AbstractSiriusDiagramTests {// extends AbstractPapyrusTest {

	/** Indicates if the tested diagram should be synchronized. */
	private boolean synchronization = false;

	/**
	 * Sets if diagram is synchronized.
	 *
	 * @param value
	 *            the synchronizedDiagram to set
	 */
	public void setSynchronization(boolean value) {
		this.synchronization = value;
	}

	/**
	 * Returns if diagram is synchronized.
	 *
	 * @return the synchronization
	 */
	public boolean isSynchronization() {
		return synchronization;
	}

	/**
	 * The editor fixture
	 */
	@Rule
	public final SiriusDiagramEditorFixture fixture = new SiriusDiagramEditorFixture();

	/**
	 * This method is used to check if the current diagram has the expected synchronization status
	 *
	 * @param isSynchronized
	 *            <code>true</code> if the diagram must be synchronized
	 */
	protected void checkSiriusDiagramSynchronization(final boolean isSynchronized) {
		final DSemanticDiagram siriusDiagram = this.fixture.getActiveSiriusDiagram();
		Assert.assertNotNull("We don't found a Sirius active diagram", siriusDiagram); //$NON-NLS-1$
		Assert.assertEquals("The synchronization status of the diagram is not the expected one", Boolean.valueOf(isSynchronized), Boolean.valueOf(siriusDiagram.isSynchronized())); //$NON-NLS-1$
	}

	/**
	 * Get Node from a container representation which is on the root of the diagram.
	 *
	 * @param elementNameNode
	 *            the name of the node to extract,
	 * @param mappingTypeName
	 *            the mapping type name of the node to extract,
	 * @param graphicalContainer
	 *            the graphical container of the node to extract,
	 * @return Node from a container representation which is on the root of the diagram.
	 */
	protected AbstractDNode getNodeFromContainer(final String elementNameNode, final String mappingTypeName, EObject graphicalContainer) {
		return this.getNodeFromContainer(elementNameNode, mappingTypeName, graphicalContainer, null);
	}

	/**
	 * Get Node from a container representation which is on the root of the diagram.
	 *
	 * @param elementNameNode
	 *            the name of the node to extract,
	 * @param mappingTypeName
	 *            the mapping type name of the node to extract,
	 * @param graphicalContainer
	 *            the graphical container of the node to extract,
	 * @param semanticContainer
	 *            the semantic container of the semantic element to extract; <code>null</code> if the graphical container is the semantic container,
	 * @return Node from a container representation which is on the root of the diagram.
	 */
	protected AbstractDNode getNodeFromContainer(final String elementNameNode, final String mappingTypeName, EObject graphicalContainer, EObject semanticContainer) {
		this.checkSiriusDiagramSynchronization(isSynchronization());

		// setup
		Assert.assertTrue(this.getRootElement() instanceof Namespace);
		Namespace rootElement = (Namespace) this.getRootElement();
		NamedElement element = null;
		if (graphicalContainer instanceof DDiagramElementContainer) {
			EObject semanticParent = null;
			if (semanticContainer == null) {
				semanticParent = ((DRepresentationElement) graphicalContainer).getSemanticElements().get(0);
			} else {
				semanticParent = semanticContainer;
			}
			for (EObject child : semanticParent.eContents()) {
				if (child instanceof NamedElement && elementNameNode.equals(((NamedElement) child).getName())) {
					element = (NamedElement) child;
					break;
				}
			}
		} else if (graphicalContainer instanceof DSemanticDiagram) {
			if (rootElement.getName().equals(elementNameNode)) {
				element = rootElement;
			} else {
				element = rootElement.getMember(elementNameNode);
			}
		}
		Assert.assertNotNull("We can't find the semantic element", element); //$NON-NLS-1$

		Diagram diagram = this.getDiagram();
		Assert.assertNotNull("We can't find the diagram", diagram); //$NON-NLS-1$

		for (EObject dDiagramElement : GraphicalOwnerUtils.getChildren(graphicalContainer)) {
			if (dDiagramElement instanceof AbstractDNode abstractNode && mappingTypeName.equals(((AbstractDNode) dDiagramElement).getMapping().getName())) {
				if (element.equals(abstractNode.getSemanticElements().get(0))) {
					return abstractNode;
				}
			}
		}
		return null;
	}

	protected Element getRootElement() {
		return this.fixture.getModel();
	}

	/**
	 * Get the node at the root of the diagram.
	 *
	 * @param elementNameNode
	 *            the name of the node to extract,
	 * @param mappingTypeName
	 *            the mapping type name of the node to extract,
	 * @return the node at the root of the diagram.
	 */
	protected AbstractDNode getNodeFromDiagram(final String elementNameNode, final String mappingTypeName) {
		return this.getNodeFromContainer(elementNameNode, mappingTypeName, this.getDDiagram());
	}

	/**
	 * Get all elements contained in the diagram matching the given name and mapping.
	 *
	 * @param elementNameNode
	 *            the name of the node.s to extract, Can be null.
	 * @param mappingTypeName
	 *            the mapping type name of the node.s to extract, Can be null.
	 * @return the list of nodes contained in the diagram.
	 */
	protected List<DDiagramElement> getElementsFromDiagram(final String elementNameNode, final String mappingTypeName) {
		DDiagramQuery dDiagramQuery = new DDiagramQuery(this.getDDiagram());
		return dDiagramQuery.getAllDiagramElements().stream()
				.filter(ddiagramElement -> {
					return this.hasSameMappingTypeOrIsNull(mappingTypeName, ddiagramElement) && this.hasSameNameOrIsNull(elementNameNode, ddiagramElement);
				})
				.collect(Collectors.toList());
	}

	/**
	 * Get all elements contained in the diagram matching the given target semantic name and mapping.
	 *
	 * @param semanticTargetName
	 *            the name of target semantic element to extract, Can be null.
	 * @param mappingTypeName
	 *            the mapping type name of the node.s to extract, Can be null.
	 * @return the list of nodes contained in the diagram.
	 */
	protected List<DDiagramElement> getElementsFromDiagramBySemanticName(final String semanticTargetName, final String mappingTypeName) {
		DDiagramQuery dDiagramQuery = new DDiagramQuery(this.getDDiagram());
		return dDiagramQuery.getAllDiagramElements().stream()
				.filter(ddiagramElement -> {
					return this.hasSameMappingTypeOrIsNull(mappingTypeName, ddiagramElement) && this.hasSameTargetNameOrIsNull(semanticTargetName, ddiagramElement);
				})
				.collect(Collectors.toList());
	}

	/**
	 * Provides the first {@link NamedElement} found in the whole hierarchy from the root model.
	 *
	 * @param elementName
	 *            the name of the element to looking for.
	 * @return the optional found element.
	 */
	protected Optional<NamedElement> getElementByNameFromRoot(String elementName) {
		return this.getElementByName(this.getRootElement(), elementName);
	}

	/**
	 * Provides the first {@link NamedElement} found in the hierarchy from the specified container.
	 *
	 * @param container
	 *            the container that may contain the searched element
	 * @param elementName
	 *            the name of the element to looking for.
	 * @return the optional found element.
	 */
	protected Optional<NamedElement> getElementByName(Element container, String elementName) {
		Optional<NamedElement> result = Optional.empty();
		if ((container instanceof NamedElement namedElement) && elementName.equals(namedElement.getName())) {
			result = Optional.of(namedElement);
		} else {
			result = StreamSupport.stream(Spliterators.spliteratorUnknownSize(container.eAllContents(), Spliterator.NONNULL), false)
					.filter(NamedElement.class::isInstance)
					.map(NamedElement.class::cast)
					.filter(namedElement -> namedElement.getName() != null && namedElement.getName().equals(elementName))
					.findFirst();
		}
		return result;
	}

	private boolean hasSameMappingTypeOrIsNull(final String mappingTypeName, DDiagramElement ddiagramElement) {
		return (mappingTypeName != null && ddiagramElement.getDiagramElementMapping().getName().equals(mappingTypeName)) || mappingTypeName == null;
	}

	private boolean hasSameNameOrIsNull(final String name, DDiagramElement ddiagramElement) {
		return (name != null && ddiagramElement.getName().equals(name)) || name == null;
	}

	private boolean hasSameTargetNameOrIsNull(final String name, DDiagramElement ddiagramElement) {
		Optional<EObject> target = Optional.ofNullable(ddiagramElement.getTarget());
		boolean hasSameName = target.filter(NamedElement.class::isInstance).filter(namedElement -> ((NamedElement) namedElement).getName().equals(name)).isPresent();
		boolean hasSameBody = target.filter(Comment.class::isInstance).filter(comment -> ((Comment) comment).getBody().equals(name)).isPresent();
		return (name != null && (hasSameName || hasSameBody) || name == null);
	}

	/**
	 * Get Edge from the diagram.
	 *
	 * @param graphicalSource
	 *            the edge source,
	 * @param graphicalTarget
	 *            the edge target,
	 * @param mappingTypeName
	 *            the type of the edge to extract,
	 * @return Edge from the diagram.
	 */
	protected DEdge getEdgeFromDiagram(final EdgeTarget graphicalSource, final EdgeTarget graphicalTarget, final String mappingTypeName) {
		this.checkSiriusDiagramSynchronization(isSynchronization());

		DEdge foundEdge = null;
		for (DEdge dEdge : this.getDDiagram().getEdges()) {
			if (graphicalSource.equals(dEdge.getSourceNode()) && graphicalTarget.equals(dEdge.getTargetNode())) {
				if (mappingTypeName.equals(dEdge.getMapping().getName())) {
					foundEdge = dEdge;
					break;
				}
			}
		}
		Assert.assertNotNull("Edge not found the diagram", foundEdge); //$NON-NLS-1$
		return foundEdge;
	}

	/**
	 * Retrieves the sub container in the given container matching the given mapping type.
	 *
	 * @param container
	 *            a DNodeContainer
	 * @param subNodeMappingType
	 *            the type of the wanted sub DDiagramElementContainer
	 * @return
	 *         the expected {@link DDiagramElementContainer} or <code>null</code>
	 */
	protected final DDiagramElementContainer getDDiagramElementContainerInContainer(final DNodeContainer container, final String subNodeMappingType) {
		for (final DDiagramElement subNode : container.getOwnedDiagramElements()) {
			if (subNode instanceof DDiagramElementContainer && subNode.getMapping().getName().equals(subNodeMappingType)) {
				return (DDiagramElementContainer) subNode;
			}
		}
		return null;
	}



	/**
	 * Returns the active GMF {@link Diagram}.
	 *
	 * @return the active GMF {@link Diagram}.
	 */
	protected final Diagram getDiagram() {
		return this.fixture.getActiveDiagram().getDiagramView();
	}

	/**
	 * Returns the active Sirius {@link DDiagram}.
	 *
	 * @return the active Sirius {@link DDiagram}
	 */
	public final DDiagram getDDiagram() {
		return (DDiagram) this.getDiagram().getElement();
	}

	/**
	 * Returns the semantic {@link Model}.
	 *
	 * @return the semantic {@link Model}.
	 */
	public final org.eclipse.uml2.uml.Package getModel() {
		return this.fixture.getModel();
	}
}
