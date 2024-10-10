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

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.CompositeStructureExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.CompositeStructureExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.CompositeStructureInternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.CompositeStructureInternalSourceToRepresentationDropChecker;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Services for D&D on the "Composite Structure" diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class CompositeStructureDropBehaviorProvider extends AbstractDiagramServices {

	/**
	 * Drag and drop a given semantic droppedElement from ModelExplorer into a graphical containerView.
	 *
	 * @param droppedElement
	 *            the semantic uml element to D&D,
	 * @param containerView
	 *            the graphical container
	 */
	public void dragAndDropSemanticCSD(EObject droppedElement, Object containerView) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropBehaviorProvider(new CompositeStructureExternalSourceToRepresentationDropBehaviorProvider())
				.build();
		commonDropBehaviorProvider.dragAndDropSemantic(droppedElement, containerView);
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
	public boolean canDragAndDropSemanticCSD(EObject droppedElement, EObject newContainer) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropChecker(new CompositeStructureExternalSourceToRepresentationDropChecker())
				.build();
		return commonDropBehaviorProvider.candDragAndDropSemantic(droppedElement, newContainer);
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
	public void dragAndDropGraphicCSD(EObject droppedElement, EObject oldContainer, EObject newContainer, Object containerView) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropBehaviorProvider(new CompositeStructureInternalSourceToRepresentationDropBehaviorProvider())
				.build();
		commonDropBehaviorProvider.dragAndDropGraphic(droppedElement, oldContainer, newContainer, containerView);
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
	public boolean canDragAndDropGraphicCSD(EObject droppedElement, EObject newContainer) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropChecker(new CompositeStructureInternalSourceToRepresentationDropChecker())
				.build();
		return commonDropBehaviorProvider.candDragAndDropGraphic(droppedElement, newContainer);
	}

	/**
	 * Returns whether the provided {@code semanticElement} is a UML {@link Type} that is represented in the CompositeStructure Diagram.
	 *
	 * @param semanticElement
	 *            the element to check
	 * @return {@code true} if the provided {@code semanticElement} is a sub-class of {@link Type} that is represented on the diagram, {@code false} otherwise
	 */
	public boolean isTypeRepresentedOnDiagram(EObject semanticElement) {
		List<EClass> representedTypes = List.of(
				UMLPackage.eINSTANCE.getActivity(),
				UMLPackage.eINSTANCE.getClass_(),
				UMLPackage.eINSTANCE.getCollaboration(),
				UMLPackage.eINSTANCE.getFunctionBehavior(),
				UMLPackage.eINSTANCE.getInformationItem(),
				UMLPackage.eINSTANCE.getInteraction(),
				UMLPackage.eINSTANCE.getOpaqueBehavior(),
				UMLPackage.eINSTANCE.getProtocolStateMachine(),
				UMLPackage.eINSTANCE.getStateMachine());
		return representedTypes.contains(semanticElement.eClass()) && UMLPackage.eINSTANCE.getType().isInstance(semanticElement);
	}

}
