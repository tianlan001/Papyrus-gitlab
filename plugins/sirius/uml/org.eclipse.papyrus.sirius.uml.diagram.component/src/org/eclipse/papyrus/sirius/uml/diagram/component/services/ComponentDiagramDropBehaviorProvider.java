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
package org.eclipse.papyrus.sirius.uml.diagram.component.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.ComponentExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.ComponentExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.ComponentInternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.ComponentInternalSourceToRepresentationDropChecker;

/**
 * Services for D&D on the Component diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ComponentDiagramDropBehaviorProvider extends AbstractDiagramServices {

	/**
	 * Checks if a given droppedElement can be drag and dropped from Model Explorer view into a given newContainer.
	 *
	 * @param droppedElement
	 *            the UML element to drag and drop
	 * @param newContainer
	 *            the new container of the drop
	 * @return {@code true} if droppedElement can be drag and dropped into a newContainer, {@code false} otherwise
	 */
	public boolean canDragAndDropSemanticCPD(EObject droppedElement, EObject newContainer) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropChecker(new ComponentExternalSourceToRepresentationDropChecker())
				.build();
		return commonDropBehaviorProvider.candDragAndDropSemantic(droppedElement, newContainer);
	}

	/**
	 * Drag and drop a given semantic droppedElement from Model Explorer view into a graphical containerView.
	 *
	 * @param droppedElement
	 *            the semantic UML element to drag and drop
	 * @param containerView
	 *            the graphical container
	 */
	public void dragAndDropSemanticCPD(EObject droppedElement, Object containerView) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropBehaviorProvider(new ComponentExternalSourceToRepresentationDropBehaviorProvider())
				.build();
		commonDropBehaviorProvider.dragAndDropSemantic(droppedElement, containerView);
	}

	/**
	 * Checks if a given droppedElement can be drag and dropped from diagram into a given newContainer.
	 *
	 * @param droppedElement
	 *            the UML element to drag and drop
	 * @param newContainer
	 *            the new container of the drop
	 * @return {@code true} if droppedElement can be drag and dropped into a newContainer, {@code false} otherwise
	 */
	public boolean canDragAndDropGraphicCPD(EObject droppedElement, EObject newContainer) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropChecker(new ComponentInternalSourceToRepresentationDropChecker())
				.build();
		return commonDropBehaviorProvider.candDragAndDropGraphic(droppedElement, newContainer);
	}

	/**
	 * Drag and drop a given droppedElement from diagram into a graphical containerView.
	 *
	 * @param droppedElement
	 *            the UML element to drag and drop
	 * @param oldContainer
	 *            the old semantic container of the element to drag and drop
	 * @param newContainer
	 *            the new semantic container of the element to drag and drop
	 * @param containerView
	 *            the new graphical container
	 */
	public void dragAndDropGraphicCPD(EObject droppedElement, EObject oldContainer, EObject newContainer, Object containerView) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropBehaviorProvider(new ComponentInternalSourceToRepresentationDropBehaviorProvider())
				.build();
		commonDropBehaviorProvider.dragAndDropGraphic(droppedElement, oldContainer, newContainer, containerView);
	}

}
