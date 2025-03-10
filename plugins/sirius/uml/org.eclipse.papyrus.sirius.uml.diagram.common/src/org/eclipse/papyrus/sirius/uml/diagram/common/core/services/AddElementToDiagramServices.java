/******************************************************************************
 * Copyright (c) 2015, 2023 Obeo, CEA LIST, Artal Technologies
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - initial API and implementation
 *  Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - adaptation to integrate in Papyrus
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.core.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.model.business.internal.helper.ContentHelper;
import org.eclipse.sirius.ecore.extender.business.api.accessor.ModelAccessor;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Package;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * A set of services used by the add to diagram feature.
 *
 */
@SuppressWarnings("restriction")
public class AddElementToDiagramServices {
	/**
	 * A singleton instance to be accessed by other java services.
	 */
	public static final AddElementToDiagramServices INSTANCE = new AddElementToDiagramServices();

	/**
	 * Hidden constructor.
	 */
	private AddElementToDiagramServices() {

	}

	/**
	 * Get mappings available for a semantic element and a given diagram.
	 *
	 * @param semanticElement Semantic element
	 * @param diagram         Container view
	 * @param session         Session
	 * @return List of mappings which could not be null
	 */
	public List<DiagramElementMapping> getValidMappingsForDiagram(final EObject semanticElement,
			final DSemanticDiagram diagram, Session session) {
		final List<DiagramElementMapping> mappings = new ArrayList<DiagramElementMapping>();
		// check semantic element could be added to diagram
		if (!isValidForDiagram(diagram, null).apply(semanticElement)) {
			return mappings;
		}

		final ModelAccessor modelAccessor = session.getModelAccessor();
		for (final DiagramElementMapping mapping : getDiagramMappings(diagram)) {
			String domainClass = null;
			if (mapping instanceof AbstractNodeMapping) {
				domainClass = ((AbstractNodeMapping) mapping).getDomainClass();
			} else if (mapping instanceof EdgeMapping) {
				domainClass = ((EdgeMapping) mapping).getDomainClass();
			}
			if (modelAccessor.eInstanceOf(semanticElement, domainClass) && !diagram.isSynchronized()) {
					mappings.add(mapping);
			}
		}

		return mappings;
	}
	
	/**
	 * Get all mappings available for a diagram.
	 * 
	 * @param diagram
	 *            the diagram which contains the description with mappings
	 * @return all mappings available for a diagram.
	 */
	private Set<DiagramElementMapping> getDiagramMappings(final DSemanticDiagram diagram) {
		DiagramDescription desc = diagram.getDescription();
		Set<DiagramElementMapping> diagMappings = new HashSet<>();
		diagMappings.addAll(ContentHelper.getAllContainerMappings(desc, false));
		diagMappings.addAll(ContentHelper.getAllNodeMappings(desc, false));
		diagMappings.addAll(ContentHelper.getAllEdgeMappings(desc, false));
		return diagMappings;
	}

	private Predicate<Object> getValidsForClassDiagram() {
		final Predicate<Object> validForClassDiagram = new Predicate<Object>() {

			public boolean apply(Object input) {
				return input instanceof Package || input instanceof Interface || input instanceof DataType
						|| "Class".equals(((EObject) input).eClass().getName()) //$NON-NLS-1$
						|| "Component".equals(((EObject) input).eClass().getName()); //$NON-NLS-1$
			}
		};
		return validForClassDiagram;
	}


	/**
	 * Get valid elements for a diagram.
	 *
	 * @param diagram   diagram
	 * @param container selected container
	 * @return List of valid elements for the current representation
	 */
	public Predicate<Object> isValidForDiagram(DDiagram diagram, EObject container) {
		Predicate<Object> results = Predicates.alwaysTrue();
		if (diagram instanceof DSemanticDiagram) {
			final DiagramDescription description = ((DSemanticDiagram) diagram).getDescription();

			if ("Class Diagram".equals(description.getName()) //$NON-NLS-1$
					|| "Profile Diagram".equals(description.getName())) { //$NON-NLS-1$
				results = getValidsForClassDiagram();
			}
		}

		return results;
	}
}
