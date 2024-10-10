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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.UseCaseExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.UseCaseExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.UseCaseInternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.UseCaseInternalSourceTorepresentationDropBehaviorProvider;

/**
 * Services for D&D on the "Use Case" diagram.
 *
 * @author <a href="mailto:vincent.blain@obeosoft.com">Vincent BLAIN</a>
 */
public class UseCaseDropBehaviorProvider extends AbstractDiagramServices {

	/**
	 * Drag and drop a given semantic droppedElement from ModelExplorer into a graphical containerView.
	 * 
	 * @param droppedElement
	 *            the semantic uml element to D&D,
	 * @param containerView
	 *            the graphical container
	 */
	public void dragAndDropSemanticUCD(EObject droppedElement, Object containerView) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropBehaviorProvider(new UseCaseExternalSourceToRepresentationDropBehaviorProvider())
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
	public boolean canDragAndDropSemanticUCD(EObject droppedElement, EObject newContainer) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropChecker(new UseCaseExternalSourceToRepresentationDropChecker())
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
	public void dragAndDropGraphicUCD(EObject droppedElement, EObject oldContainer, EObject newContainer, Object containerView) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropBehaviorProvider(new UseCaseInternalSourceTorepresentationDropBehaviorProvider())
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
	public boolean canDragAndDropGraphicUCD(EObject droppedElement, EObject newContainer) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropChecker(new UseCaseInternalSourceToRepresentationDropChecker())
				.build();
		return commonDropBehaviorProvider.candDragAndDropGraphic(droppedElement, newContainer);
	}
}
