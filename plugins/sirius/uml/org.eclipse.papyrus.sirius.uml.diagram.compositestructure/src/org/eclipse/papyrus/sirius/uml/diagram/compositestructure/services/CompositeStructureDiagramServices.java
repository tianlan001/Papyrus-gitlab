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
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.services;


import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.Activator;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.EditableChecker;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.RepresentationQuerier;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DomainBasedEdgeServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.ReconnectServices;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.papyrus.uml.domain.services.create.ElementDomainBasedEdgeCreationChecker;
import org.eclipse.papyrus.uml.domain.services.create.diagrams.CompositeStructureDiagramElementCreator;
import org.eclipse.papyrus.uml.domain.services.edges.diagrams.CompositeStructureDomainBasedEdgeContainerProvider;
import org.eclipse.papyrus.uml.domain.services.status.CheckStatus;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Services for the "Composite Structure" diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class CompositeStructureDiagramServices extends AbstractDiagramServices {

	// nestedClassifier reference name
	private static final String NESTED_CLASSIFIER = "nestedClassifier"; //$NON-NLS-1$

	// packagedElement reference name
	private static final String PACKAGED_ELEMENT = "packagedElement"; //$NON-NLS-1$

	/**
	 * Creates a new semantic element, initialize and create a view.
	 *
	 * @param parent
	 *            the semantic parent
	 * @param type
	 *            the type of element to create
	 * @return a new instance or <code>null</code> if the creation failed
	 */
	public EObject createStructuredClassifierCSD(Element parent, String type, DSemanticDecorator targetView) {
		EObject result = null;
		if (parent == null) {
			Activator.log.warn("Unable to create an element on nothing"); //$NON-NLS-1$
		} else {
			if (parent instanceof org.eclipse.uml2.uml.Package) {
				result = this.createCSD(parent, type, PACKAGED_ELEMENT, targetView);
			} else if (parent instanceof org.eclipse.uml2.uml.Class) {
				result = this.createCSD(parent, type, NESTED_CLASSIFIER, targetView);
			}
		}
		return result;
	}

	/**
	 * Creates a new semantic element, initialize and create a view.
	 *
	 * @param parent
	 *            the semantic parent
	 * @param type
	 *            the type of element to create
	 * @param referenceName
	 *            the name of the containment reference
	 * @return a new instance or <code>null</code> if the creation failed
	 */
	public EObject createCSD(Element parent, String type, String referenceName, DSemanticDecorator targetView) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		CompositeStructureDiagramElementCreator compositeStructureDiagramElementCreator = CompositeStructureDiagramElementCreator.buildDefault(this.getECrossReferenceAdapter(parent), new EditableChecker());
		return commonDiagramServices.createElement(parent, type, referenceName, targetView, compositeStructureDiagramElementCreator);
	}

	/**
	 * Create a {@link Dependency} kind relationship between the provided source and target elements.
	 *
	 * @param source
	 *            the semantic source.
	 * @param target
	 *            the semantic target.
	 * @param type
	 *            the type of element to create
	 * @param sourceView
	 *            the source view.
	 * @param targetView
	 *            the target view.
	 */
	public void createDependencyCSD(EObject source, EObject target, String type, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		if (source instanceof CollaborationUse) {
			this.createDomainBasedEdgeCSD(source, target, type, UMLPackage.eINSTANCE.getCollaborationUse_RoleBinding().getName(), sourceView, targetView);
		} else {
			this.createDomainBasedEdgeCSD(source, target, type, UMLPackage.eINSTANCE.getPackage_PackagedElement().getName(), sourceView, targetView);
		}
	}

	/**
	 * Service used to create a domain base edge.
	 *
	 * @param source
	 *            the semantic source
	 * @param target
	 *            the semantic target
	 * @param type
	 *            the new element type
	 * @param containementReferenceName
	 *            the containment reference name
	 * @param sourceNode
	 *            the source {@link DSemanticDecorator} of the new edge
	 * @param targetNode
	 *            the target {@link DSemanticDecorator} of the new edge
	 * @return a new element or <code>null</code>
	 */
	public EObject createDomainBasedEdgeCSD(EObject source, EObject target, String type, String containementReferenceName, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		DomainBasedEdgeServices domainBasedEdgeServices = new DomainBasedEdgeServices();
		return domainBasedEdgeServices.createDomainBasedEdge(source, target, type, containementReferenceName, sourceView, targetView, CompositeStructureDomainBasedEdgeContainerProvider.buildDefault(new EditableChecker()));
	}

	/**
	 * Service used to create a domain base edge.
	 *
	 * @param source
	 *            the semantic source
	 * @param target
	 *            the semantic target
	 * @param type
	 *            the new element type
	 * @param containementReferenceName
	 *            the containment reference name
	 * @param sourceNode
	 *            the source {@link DSemanticDecorator} of the new edge
	 * @param targetNode
	 *            the target {@link DSemanticDecorator} of the new edge
	 * @return a new element or <code>null</code>
	 */
	public boolean canCreateDomainBasedEdgeCSD(EObject source, EObject target, String type, String containementReferenceName, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		boolean canCreate = true;

		// check if edge can be semantically created
		RepresentationQuerier representationQuery = new RepresentationQuerier(((DDiagramElement) sourceView).getParentDiagram());
		CheckStatus canCreateStatus = new ElementDomainBasedEdgeCreationChecker().canCreate(source, target, type, containementReferenceName, representationQuery, sourceView, targetView);
		canCreate = canCreateStatus.isValid();

		if (canCreate) {
			// check if a container exist
			CompositeStructureDomainBasedEdgeContainerProvider containerProvider = CompositeStructureDomainBasedEdgeContainerProvider.buildDefault(new EditableChecker());
			EClass eClass = UMLHelper.toEClass(type);
			if (eClass != null) {
				EObject newInstance = UMLFactory.eINSTANCE.create(eClass);
				EObject container = containerProvider.getContainer(source, target, newInstance, representationQuery, sourceView, targetView);
				canCreate = container != null;
			}
		}

		return canCreate;
	}

	/**
	 * Check if the domain based edge can reconnect with the new source.
	 *
	 * @param semanticElementEdge
	 *            the semantic element of the domain based edge
	 *
	 * @param edgeView
	 *            the graphical edge view.
	 *
	 * @param oldSource
	 *            the old source of the domain based edge
	 * @param newSource
	 *            the new source of the domain based edge
	 * @param newSourceView
	 *            the new source view of the domain based edge
	 * @return boolean
	 */
	public boolean canReconnectSourceCSD(EObject semanticElementEdge, DSemanticDecorator edgeView, EObject oldSource, EObject newSource, DSemanticDecorator newSourceView) {
		ReconnectServices reconnectServices = new ReconnectServices();
		CompositeStructureDomainBasedEdgeContainerProvider containerProvider = CompositeStructureDomainBasedEdgeContainerProvider.buildDefault(new EditableChecker());
		return reconnectServices.canReconnectSource(semanticElementEdge, edgeView, oldSource, newSource, newSourceView, containerProvider);
	}

	/**
	 * Check if the domain based edge can reconnect with the new target.
	 *
	 * @param semanticElementEdge
	 *            the semantic element of the domain based edge
	 * @param oldTarget
	 *            the old target of the domain based edge
	 * @param newTarget
	 *            the new target of the domain based edge
	 * @param newTargetView
	 *            the new target view of the domain based edge
	 * @return boolean
	 */
	public boolean canReconnectTargetCSD(EObject semanticElementEdge, DSemanticDecorator edgeView, EObject oldTarget, EObject newTarget, DSemanticDecorator newTargetView) {
		ReconnectServices reconnectServices = new ReconnectServices();
		CompositeStructureDomainBasedEdgeContainerProvider containerProvider = CompositeStructureDomainBasedEdgeContainerProvider.buildDefault(new EditableChecker());
		return reconnectServices.canReconnectTarget(semanticElementEdge, edgeView, oldTarget, newTarget, newTargetView, containerProvider);
	}
}
