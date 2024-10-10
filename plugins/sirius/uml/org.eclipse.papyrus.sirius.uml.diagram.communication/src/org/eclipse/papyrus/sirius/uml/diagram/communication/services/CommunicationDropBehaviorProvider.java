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
package org.eclipse.papyrus.sirius.uml.diagram.communication.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.CommunicationExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.CommunicationExternalSourceToRepresentationDropChecker;

/**
 * Services for D&D on the "Communication" diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class CommunicationDropBehaviorProvider extends AbstractDiagramServices {

	/**
	 * Drag and drop a given semantic droppedElement from ModelExplorer into a graphical containerView.
	 * 
	 * @param droppedElement
	 *            the semantic uml element to D&D,
	 * @param containerView
	 *            the graphical container
	 */
	public void dragAndDropSemanticCD(EObject droppedElement, Object containerView) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropBehaviorProvider(new CommunicationExternalSourceToRepresentationDropBehaviorProvider())
				.build();
		commonDropBehaviorProvider.dragAndDropSemantic(droppedElement, containerView, true);
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
	public boolean canDragAndDropSemanticCD(EObject droppedElement, EObject newContainer) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropChecker(new CommunicationExternalSourceToRepresentationDropChecker())
				.build();
		return commonDropBehaviorProvider.candDragAndDropSemantic(droppedElement, newContainer);
	}

}
