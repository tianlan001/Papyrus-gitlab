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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;

/**
 * Services used to handle {@link Connector}.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ConnectorServices extends AbstractDiagramServices {

	/**
	 * Check if a connector should be displayed.
	 * <p>
	 * If a connector is linked between a property typed by a classifier and other element,
	 * the connector between the classifier (used as typed) should not be displayed.
	 * </p>
	 *
	 * @param connector
	 *            the connector to display,
	 * @param sourceView
	 *            the source view of the connector edge,
	 * @param targetView
	 *            the target view of the connector edge,
	 * @return <code>true</code> if the connector should be displayed, <code>false</code> otherwise.
	 */
	public boolean displayConnector(Connector connector, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		boolean shouldDiplayConnector = true;

		// compute sourceEnd
		ConnectorEnd sourceEnd = connector.getEnds().get(0);
		Property partWithPortSource = sourceEnd.getPartWithPort();
		if (partWithPortSource != null) {
			// connector source is a Port on a Property
			EObject target = sourceView.getTarget();
			if (target instanceof Port) {
				EObject parentGraphicalSourceProperty = ((DSemanticDecorator) sourceView.eContainer()).getTarget();
				shouldDiplayConnector = shouldDiplayConnector && parentGraphicalSourceProperty.equals(partWithPortSource);
			} else {
				shouldDiplayConnector = false;
			}
		}


		// compute target end
		ConnectorEnd taregtEnd = connector.getEnds().get(1);
		Property partWithPortTarget = taregtEnd.getPartWithPort();
		if (partWithPortTarget != null) {
			// connector target is a Port on a Property
			EObject target = targetView.getTarget();
			if (target instanceof Port) {
				EObject parentGraphicalTargetProperty = ((DSemanticDecorator) targetView.eContainer()).getTarget();
				shouldDiplayConnector = shouldDiplayConnector && parentGraphicalTargetProperty.equals(partWithPortTarget);
			} else {
				shouldDiplayConnector = false;
			}
		}

		// check if graphical ancestor for source and target view matches semantic container of connector
		DSemanticDecorator ancestorContainer = getConnectorAncestorContainer(sourceView, targetView);
		if (ancestorContainer == null || ancestorContainer.getTarget() != connector.eContainer()) {
			shouldDiplayConnector = false;
		}

		return shouldDiplayConnector;
	}

	/**
	 * Get common graphical ancestor of two graphical elements, <code>null</code> if no ancestor is found.
	 *
	 * @param sourceView
	 *            the first graphical element with ancestor to search,
	 * @param targetView
	 *            the second graphical element with ancestor to search,
	 * @return the common graphical ancestor of two graphical elements.
	 */
	private DSemanticDecorator getConnectorAncestorContainer(DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		List<DSemanticDecorator> sourceStructureClassifiers = getStructuredClassifiersAncestors(sourceView);
		List<DSemanticDecorator> targetStructureClassifiers = getStructuredClassifiersAncestors(targetView);

		Iterator<DSemanticDecorator> targetIte = targetStructureClassifiers.iterator();
		while (targetIte.hasNext()) {
			DSemanticDecorator dsemanticDecorator = targetIte.next();
			if (sourceStructureClassifiers.contains(dsemanticDecorator)) {
				return dsemanticDecorator;
			}
		}
		return null;
	}

	/**
	 * Get the list of graphical ancestors of a given graphical element.
	 *
	 * @param object
	 *            the graphical element with ancestor to search,
	 * @return the list of graphical ancestors of a given graphical element.
	 */
	private List<DSemanticDecorator> getStructuredClassifiersAncestors(DSemanticDecorator object) {
		EObject current = object;
		List<DSemanticDecorator> results = new ArrayList<>();
		while (current != null) {
			if (current instanceof DSemanticDecorator) {
				EObject target = ((DSemanticDecorator) current).getTarget();
				if (target instanceof StructuredClassifier) {
					results.add((DSemanticDecorator) current);
				}
			}
			current = current.eContainer();
		}
		return results;
	}

}
