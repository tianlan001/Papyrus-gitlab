/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.component.custom.parts;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.ConnectorEditPart;
import org.eclipse.papyrus.uml.diagram.component.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.component.part.UMLLinkDescriptor;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This class provides incoming links logic for port/connector and property/connector pairs
 *
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=483573
 * @since 3.2
 */
public class CustomUMLDiagramUpdater extends UMLDiagramUpdater {

	public static final CustomUMLDiagramUpdater INSTANCE = new CustomUMLDiagramUpdater();

	private CustomUMLDiagramUpdater() {
		super();
	}

	@Override
	public List<UMLLinkDescriptor> getPort_Shape_IncomingLinks(View view) {
		Port modelElement = (Port) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(super.getPort_Shape_IncomingLinks(view));
		result.addAll(getIncomingFeatureModelFacetLinks_Connector_Edge(modelElement, crossReferencer));
		return result;
	}

	@Override
	public List<UMLLinkDescriptor> getProperty_Shape_IncomingLinks(View view) {
		Property modelElement = (Property) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(super.getProperty_Shape_IncomingLinks(view));
		result.addAll(getIncomingFeatureModelFacetLinks_Connector_Edge(modelElement, crossReferencer));
		return result;
	}

	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Connector_Edge(Element target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getConnectorEnd_Role()) {
				continue;
			}
			ConnectorEnd source = (ConnectorEnd) setting.getEObject();
			Connector connector = (Connector) source.eContainer();
			List<ConnectorEnd> ends = connector.getEnds();
			if (ends == null || ends.size() != 2) {
				continue;
			}
			result.add(new UMLLinkDescriptor(ends.get(0).getRole(), ends.get(1).getRole(), connector, UMLElementTypes.Connector_Edge, ConnectorEditPart.VISUAL_ID));
		}
		return result;
	}
}
