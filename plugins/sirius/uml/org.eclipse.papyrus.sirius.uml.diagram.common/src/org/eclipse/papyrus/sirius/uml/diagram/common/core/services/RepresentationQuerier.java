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

package org.eclipse.papyrus.sirius.uml.diagram.common.core.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.domain.services.IViewQuerier;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.model.business.internal.query.DNodeContainerExperimentalQuery;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;

/**
 * Implementation of a {@link IViewQuerier} for Sirius. It allows to retrieve parent and children node from a {@link DSemanticDecorator}.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@SuppressWarnings("restriction")
public class RepresentationQuerier implements IViewQuerier {

	private Object diagram;

	/**
	 * 
	 * Constructor.
	 *
	 * @param diagram
	 *            the diagram to modify
	 */
	public RepresentationQuerier(Object diagram) {
		super();
		this.diagram = diagram;
	}

	@Override
	public Object getVisualParent(Object element) {
		Object parent = null;
		if (element instanceof DSemanticDecorator) {
			parent = ((DSemanticDecorator) element).eContainer();
			if (element instanceof DNodeContainer && parent instanceof DNodeContainer && new DNodeContainerExperimentalQuery((DNodeContainer) parent).isRegionContainer()) {
				// case element is a compartment view
				parent = ((EObject) parent).eContainer();
			}
		}
		return parent;
	}

	@Override
	public EObject getSemanticElement(Object view) {
		if (view instanceof DSemanticDecorator) {
			DSemanticDecorator semanticDecorator = (DSemanticDecorator) view;
			return semanticDecorator.getTarget();
		}
		return null;
	}

	@Override
	public List<? extends Object> getVisualAncestorNodes(Object view) {
		List<Object> parents = new ArrayList<>();
		if (view instanceof DSemanticDecorator) {
			DSemanticDecorator semanticDecorator = (DSemanticDecorator) view;
			EObject parent = semanticDecorator.eContainer();
			while (parent != null) {
				parents.add(parent);
				parent = parent.eContainer();
			}
		}
		return parents;
	}

	@Override
	public List<? extends Object> getChildrenNodes(Object view) {
		if (view instanceof DNodeContainer) {
			DNodeContainer node = (DNodeContainer) view;
			return node.getNodes();
		}
		return Collections.emptyList();
	}

	@Override
	public List<? extends Object> getBorderedNodes(Object view) {
		if (view instanceof DNodeContainer) {
			DNodeContainer node = (DNodeContainer) view;
			return node.getOwnedBorderedNodes();
		}
		return Collections.emptyList();
	}

	@Override
	public Object getDiagram() {
		return diagram;
	}

}
