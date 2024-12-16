/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.SequenceExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.SequenceExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.SequenceInternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.SequenceInternalSourceToRepresentationDropChecker;

/**
 * Services for D&D on the "Sequence" diagram.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class SequenceDiagramDropBehaviorProvider extends AbstractDiagramServices {

	/**
	 * Drag and drop a given semantic {@code droppedElement} from ModelExplorer into a graphical {@code containerView}.
	 * 
	 * @param droppedElement
	 *            the semantic uml element to D&D,
	 * @param containerView
	 *            the graphical container
	 */
	public void dragAndDropSemanticSD(EObject droppedElement, Object containerView) {
		CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropBehaviorProvider(new SequenceExternalSourceToRepresentationDropBehaviorProvider())
				.build()
				.dragAndDropSemantic(droppedElement, containerView, true);
	}

	/**
	 * Check if a given {@code droppedElement} can be drag and dropped from ModelExplorer into a given {@code newContainer}.
	 * 
	 * @param droppedElement
	 *            the UML element to drag and drop,
	 * @param newContainer
	 *            the new container of the drop,
	 * @return <code>true</code> if {@code droppedElement} can be drag and dropped into a {@code newContainer}, <code>false</code> otherwise.
	 */
	public boolean canDragAndDropSemanticSD(EObject droppedElement, EObject newContainer) {
		return CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropChecker(new SequenceExternalSourceToRepresentationDropChecker())
				.build()
				.candDragAndDropSemantic(droppedElement, newContainer);
	}

	/**
	 * Check if a given {@code droppedElement} can be drag and dropped into a given {@code newContainer=.
	 * 
	 * @param droppedElement
	 *            the UML element to drag and drop,
	 * @param newContainer
	 *            the new container of the drop,
	 * @return <code>true</code> if {@code droppedElement} can be drag and dropped into a {@code newContainer}, <code>false</code> otherwise.
	 */
	public boolean canDragAndDropGraphicSD(EObject droppedElement, EObject newContainer) {
		return CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropChecker(new SequenceInternalSourceToRepresentationDropChecker())
				.build()
				.candDragAndDropGraphic(droppedElement, newContainer);
	}

	/**
	 * Drag and drop a given {@code droppedElement} into a graphical {@code containerView}.
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
	public void dragAndDropGraphicSD(EObject droppedElement, EObject oldContainer, EObject newContainer, Object containerView) {
		CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropBehaviorProvider(new SequenceInternalSourceToRepresentationDropBehaviorProvider())
				.build()
				.dragAndDropGraphic(droppedElement, oldContainer, newContainer, containerView);
	}
}
