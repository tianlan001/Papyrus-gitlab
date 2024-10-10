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
package org.eclipse.papyrus.sirius.uml.diagram.common.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.EditableChecker;
import org.eclipse.papyrus.uml.domain.services.drop.DnDStatus;
import org.eclipse.papyrus.uml.domain.services.drop.IExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.IExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.drop.IInternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.IInternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.status.CheckStatus;
import org.eclipse.papyrus.uml.domain.services.status.State;
import org.eclipse.papyrus.uml.domain.services.status.Status;
import org.eclipse.sirius.business.api.query.EObjectQuery;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Services for D&D on diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class CommonDropBehaviorProvider extends AbstractDiagramServices {


	private final IExternalSourceToRepresentationDropBehaviorProvider externalSourceToRepresentationDropBehaviorProvider;
	private final IExternalSourceToRepresentationDropChecker externalSourceToRepresentationDropChecker;
	private final IInternalSourceToRepresentationDropBehaviorProvider internalSourceToRepresentationDropBehaviorProvider;
	private final IInternalSourceToRepresentationDropChecker internalSourceToRepresentationDropChecker;

	/**
	 * Prevent direct instantiation.
	 *
	 */
	private CommonDropBehaviorProvider(Builder builder) {
		this.externalSourceToRepresentationDropBehaviorProvider = builder.externalSourceToRepresentationDropBehaviorProvider;
		this.externalSourceToRepresentationDropChecker = builder.externalSourceToRepresentationDropChecker;
		this.internalSourceToRepresentationDropBehaviorProvider = builder.internalSourceToRepresentationDropBehaviorProvider;
		this.internalSourceToRepresentationDropChecker = builder.internalSourceToRepresentationDropChecker;
	}

	/**
	 * Drag and drop a given semantic droppedElement from ModelExplorer into a graphical containerView.
	 * 
	 * @param droppedElement
	 *            the semantic uml element to D&D,
	 * @param containerView
	 *            the graphical container
	 */
	public void dragAndDropSemantic(EObject droppedElement, Object containerView) {
		dragAndDropSemantic(droppedElement, containerView, false);
	}


	/**
	 * Drag and drop a given semantic droppedElement from ModelExplorer into a graphical containerView.
	 * 
	 * @param droppedElement
	 *            the semantic uml element to D&D,
	 * @param containerView
	 *            the graphical container
	 * @param isDiagramWithRoot
	 *            indicates if the root semantic element is also displayed as a root container
	 */
	public void dragAndDropSemantic(EObject droppedElement, Object containerView, boolean isDiagramWithRoot) {
		if (containerView != null && containerView instanceof DSemanticDecorator) {
			DSemanticDecorator targetView = (DSemanticDecorator) containerView;
			DnDStatus semanticDroppedStatus = this.externalSourceToRepresentationDropBehaviorProvider.drop(droppedElement, targetView.getTarget(), this.getECrossReferenceAdapter(droppedElement), new EditableChecker());
			if (State.DONE == semanticDroppedStatus.getState() || State.NOTHING == semanticDroppedStatus.getState()) {
				for (EObject elementToDisplay : semanticDroppedStatus.getElementsToDisplay()) {
					Status isDroppped = null;
					if (isCompartment(targetView)) {
						if (isBorderNode(elementToDisplay, (DSemanticDecorator) targetView.eContainer())) {
							// case D&D semanticElement as BorderNode on Compartment
							isDroppped = new DropSemanticSwitch((DSemanticDecorator) targetView.eContainer(), "aql:newContainerView.eContainer()", isDiagramWithRoot).doSwitch(droppedElement); //$NON-NLS-1$
						}
					}
					if (isDroppped == null) {
						isDroppped = new DropSemanticSwitch(targetView, "aql:newContainerView", isDiagramWithRoot).doSwitch(elementToDisplay); //$NON-NLS-1$
					}
				}
			}
		}
	}

	/**
	 * Check if a given droppedElement can be drag and dropped from ModelExplorer into a given newContainer.
	 * 
	 * @param droppedElement
	 *            the UML element to drag and drop,
	 * @param newContainer
	 *            the new container of the drop,
	 * @return <code>true</code> if droppedElement can be drag and dropped into a newContainer, <code>false</code> otherwise.
	 */
	public boolean candDragAndDropSemantic(EObject droppedElement, EObject newContainer) {
		CheckStatus canDragAndDrop = this.externalSourceToRepresentationDropChecker.canDragAndDrop(droppedElement, newContainer);
		return canDragAndDrop.isValid();
	}

	/**
	 * Drag and drop a given droppedElement from diagram into a graphical containerView.
	 * 
	 * @param droppedElement
	 *            the UML element to D&D,
	 * @param oldContainer
	 *            the old semantic container of the element to D&D
	 * @param newContainer
	 *            the new semantic container of the element to D&D
	 * @param containerView
	 *            the new graphical container
	 */
	public void dragAndDropGraphic(EObject droppedElement, EObject oldContainer, EObject newContainer, Object containerView) {
		Status dropStatus = this.internalSourceToRepresentationDropBehaviorProvider.drop(droppedElement, oldContainer, newContainer, this.getECrossReferenceAdapter(droppedElement), new EditableChecker());
		if (State.DONE == dropStatus.getState()) {
			if (droppedElement instanceof Property && newContainer instanceof Property && ((Property) newContainer).getType() != null) {
				createView(droppedElement, ((DSemanticDecorator) containerView), "aql:newContainerView"); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Check if a given droppedElement can be drag and dropped from Diagram into a given newContainer.
	 * 
	 * @param droppedElement
	 *            the UML element to drag and drop,
	 * @param newContainer
	 *            the new container of the drop,
	 * @return <code>true</code> if droppedElement can be drag and dropped into a newContainer, <code>false</code> otherwise.
	 */
	public boolean candDragAndDropGraphic(EObject droppedElement, EObject newContainer) {
		CheckStatus canDragAndDrop = this.internalSourceToRepresentationDropChecker.canDragAndDrop(droppedElement, newContainer);
		return canDragAndDrop.isValid();
	}

	public static Builder newCommonDropBehaviorProvider() {
		return new Builder();
	}

	class DropSemanticSwitch extends UMLSwitch<Status> {

		private final DSemanticDecorator targetView;
		private final String targetViewExpression;
		private final boolean isDiagramWithRoot;

		DropSemanticSwitch(DSemanticDecorator targetView, String targetViewExpression, boolean isDiagramWithRoot) {
			super();
			this.targetView = targetView;
			this.targetViewExpression = targetViewExpression;
			this.isDiagramWithRoot = isDiagramWithRoot;
		}

		@Override
		public Status caseElement(Element droppedElement) {
			createView(droppedElement, targetView, targetViewExpression);
			return Status.createOKStatus(droppedElement);
		}

		public Status caseElementImport(ElementImport elementImport) {
			PackageableElement importedElement = elementImport.getImportedElement();
			createView(importedElement, targetView, targetViewExpression);
			return Status.createOKStatus(importedElement);
		}

		@Override
		public Status caseRelationship(Relationship relationship) {
			return createEdgeView(relationship, isDiagramWithRoot);
		}

		@Override
		public Status caseActivityEdge(ActivityEdge activityEdge) {
			return createEdgeView(activityEdge, isDiagramWithRoot);
		}

		private Status createEdgeView(EObject semanticElement, boolean isDiagramWithRoot) {
			Session session = new EObjectQuery(semanticElement).getSession();
			Optional<DDiagram> optionalDiagram = retrieveDDiagram();
			if (session != null && optionalDiagram.isPresent()) {
				createDnDEdgeView(semanticElement, optionalDiagram.get(), session, isDiagramWithRoot);
				return Status.createOKStatus(semanticElement);
			}
			return new Status(State.FAILED, "Cannot retrieve the session or the DDiagram"); //$NON-NLS-1$
		}

		@Override
		public Status caseConnector(Connector connector) {
			return createEdgeView(connector, isDiagramWithRoot);
		}

		@Override
		public Status caseMessage(Message message) {
			return createEdgeView(message, isDiagramWithRoot);
		}

		private Optional<DDiagram> retrieveDDiagram() {
			Optional<DDiagram> optionalDiagram = Optional.empty();
			if (targetView instanceof DDiagram) {
				optionalDiagram = Optional.of((DDiagram) targetView);
			} else if (targetView instanceof DDiagramElement) {
				optionalDiagram = Optional.ofNullable(((DDiagramElement) targetView).getParentDiagram());
			}
			return optionalDiagram;
		}

	}

	public static final class Builder {
		private IExternalSourceToRepresentationDropBehaviorProvider externalSourceToRepresentationDropBehaviorProvider;
		private IExternalSourceToRepresentationDropChecker externalSourceToRepresentationDropChecker;
		private IInternalSourceToRepresentationDropBehaviorProvider internalSourceToRepresentationDropBehaviorProvider;
		private IInternalSourceToRepresentationDropChecker internalSourceToRepresentationDropChecker;

		public Builder externalSourceToRepresentationDropBehaviorProvider(IExternalSourceToRepresentationDropBehaviorProvider externalSourceToRepresentationDropBehaviorProvider) {
			this.externalSourceToRepresentationDropBehaviorProvider = Objects.requireNonNull(externalSourceToRepresentationDropBehaviorProvider);
			return this;
		}

		public Builder externalSourceToRepresentationDropChecker(IExternalSourceToRepresentationDropChecker externalSourceToRepresentationDropChecker) {
			this.externalSourceToRepresentationDropChecker = Objects.requireNonNull(externalSourceToRepresentationDropChecker);
			return this;
		}

		public Builder internalSourceToRepresentationDropBehaviorProvider(IInternalSourceToRepresentationDropBehaviorProvider internalSourceToRepresentationDropBehaviorProvider) {
			this.internalSourceToRepresentationDropBehaviorProvider = Objects.requireNonNull(internalSourceToRepresentationDropBehaviorProvider);
			return this;
		}

		public Builder internalSourceToRepresentationDropChecker(IInternalSourceToRepresentationDropChecker internalSourceToRepresentationDropChecker) {
			this.internalSourceToRepresentationDropChecker = Objects.requireNonNull(internalSourceToRepresentationDropChecker);
			return this;
		}

		public CommonDropBehaviorProvider build() {
			return new CommonDropBehaviorProvider(this);
		}
	}

}
