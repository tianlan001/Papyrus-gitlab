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
package org.eclipse.papyrus.sirius.uml.diagram.profile.services;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.ProfileExternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.ProfileExternalSourceToRepresentationDropChecker;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.ProfileInternalSourceToRepresentationDropBehaviorProvider;
import org.eclipse.papyrus.uml.domain.services.drop.diagrams.ProfileInternalSourceToRepresentationDropChecker;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;

/**
 * Services for D&D on the "Profile" diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class ProfileDiagramDropBehaviorProvider extends AbstractDiagramServices {

	/**
	 * Drag and drop a given semantic droppedElement from ModelExplorer into a graphical containerView.
	 * 
	 * @param droppedElement
	 *            the semantic uml element to D&D,
	 * @param containerView
	 *            the graphical container
	 */
	public void dragAndDropSemanticPRD(EObject droppedElement, Object containerView) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropBehaviorProvider(new ProfileExternalSourceToRepresentationDropBehaviorProvider())
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
	public boolean canDragAndDropSemanticPRD(EObject droppedElement, EObject newContainer) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.externalSourceToRepresentationDropChecker(new ProfileExternalSourceToRepresentationDropChecker())
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
	public void dragAndDropGraphicPRD(EObject droppedElement, EObject oldContainer, EObject newContainer, Object containerView) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropBehaviorProvider(new ProfileInternalSourceToRepresentationDropBehaviorProvider())
				.build();
		commonDropBehaviorProvider.dragAndDropGraphic(droppedElement, oldContainer, newContainer, containerView);
	}

	/**
	 * Drag and drop a given metaClass from diagram into a graphical containerView.
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
	public void metaClassDragAndDropGraphicPRD(EObject droppedElement, EObject oldContainer, EObject newContainer, Object containerView) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropBehaviorProvider(new ProfileInternalSourceToRepresentationDropBehaviorProvider())
				.build();
		Optional<ElementImport> optionalElementImport = Optional.of(oldContainer)
				.filter(Profile.class::isInstance)
				.map(Profile.class::cast)
				.map(profile -> profile.getElementImport((PackageableElement) droppedElement));
		if (optionalElementImport.isPresent()) {
			commonDropBehaviorProvider.dragAndDropGraphic(optionalElementImport.get(), oldContainer, newContainer, containerView);
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
	public boolean canDragAndDropGraphicPRD(EObject droppedElement, EObject newContainer) {
		CommonDropBehaviorProvider commonDropBehaviorProvider = CommonDropBehaviorProvider.newCommonDropBehaviorProvider()
				.internalSourceToRepresentationDropChecker(new ProfileInternalSourceToRepresentationDropChecker())
				.build();
		return commonDropBehaviorProvider.candDragAndDropGraphic(droppedElement, newContainer);
	}

}
