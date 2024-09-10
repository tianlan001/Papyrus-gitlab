/*****************************************************************************
 * Copyright (c) 2014, 2017, 2021 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 515201
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 563212
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLLinkDescriptor;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.util.AssociationUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Type;


public class CustomUMLDiagramUpdater extends UMLDiagramUpdater {

	public static final CustomUMLDiagramUpdater INSTANCE = new CustomUMLDiagramUpdater();

	private CustomUMLDiagramUpdater() {
		// to prevent instantiation
		super();
	}

	@Override
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Association_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Association) {
				continue;
			}
			Association link = (Association) linkObject;
			if (!AssociationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getEndTypes();
			Object theTarget = targets.size() >= 2 ? targets.get(1) : null;
			if (false == theTarget instanceof Type) {
				continue;
			}
			Type dst = (Type) theTarget;
			List<?> sources = link.getEndTypes();
			Object theSource = sources.size() >= 2 ? sources.get(0) : null;
			if (false == theSource instanceof Type) {
				continue;
			}
			Type src = (Type) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Association_Edge, AssociationEditPart.VISUAL_ID));
		}
		return result;
	}

	@Override
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Association_Edge(Type target, CrossReferenceAdapter crossReferencer) {
		return new TypeModelFacetLinksRetriever_Association_Edge(target) {

			@Override
			protected boolean check(Type source, Type target) {
				return getRoot() == target;
			}
		}.getTypeModelFacetLinks_Association_Edge();
	}

	@Override
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Association_Edge(Type source) {
		return new TypeModelFacetLinksRetriever_Association_Edge(source) {

			@Override
			protected boolean check(Type source, Type target) {
				return getRoot() == source;
			}
		}.getTypeModelFacetLinks_Association_Edge();
	}

	private abstract static class TypeModelFacetLinksRetriever_Association_Edge {
		private final Type myRoot;

		public TypeModelFacetLinksRetriever_Association_Edge(Type root) {
			myRoot = root;
		}

		public Collection<UMLLinkDescriptor> getTypeModelFacetLinks_Association_Edge() {
			Package container = null;
			// Find container element for the link.
			// Climb up by containment hierarchy starting from the source
			// and return the first element that is instance of the container class.
			for (EObject element = myRoot; element != null && container == null; element = element.eContainer()) {
				if (element instanceof Package) {
					container = (Package) element;
				}
			}
			if (container == null) {
				return Collections.emptyList();
			}
			LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
			for (Iterator<?> links = container.getPackagedElements().iterator(); links.hasNext();) {
				EObject linkObject = (EObject) links.next();
				if (false == linkObject instanceof Association) {
					continue;
				}
				Association link = (Association) linkObject;
				if (!AssociationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
					continue;
				}
				List<?> ends = link.getEndTypes();
				if (ends == null || ends.isEmpty()) {
					continue;
				}
				Object theTarget = ends.size() == 2 ? AssociationUtil.getInitialTargetFirstEnd(link).getType() : AssociationUtil.getInitialSourceSecondEnd(link).getType();
				if (false == theTarget instanceof Type) {
					continue;
				}
				Type dst = (Type) theTarget;
				Object theSource = AssociationUtil.getInitialSourceSecondEnd(link).getType();
				if (false == theSource instanceof Type) {
					continue;
				}
				Type src = (Type) theSource;
				if (!check(src, dst)) {
					continue;
				}
				result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Association_Edge, AssociationEditPart.VISUAL_ID));
			}
			return result;
		}

		protected Type getRoot() {
			return myRoot;
		}

		protected abstract boolean check(Type source, Type target);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.clazz.part.UMLDiagramUpdater#getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(org.eclipse.uml2.uml.Element)
	 *
	 * @param source
	 * @return
	 */
	@Override
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Element_ContainmentEdge(Element source) {
		return Collections.emptyList();// this prevent to have containment link in canonical mode
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.clazz.part.UMLDiagramUpdater#getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(org.eclipse.uml2.uml.Element, org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter)
	 *
	 * @param target
	 * @param crossReferencer
	 * @return
	 */
	@Override
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Element_ContainmentEdge(Element target, CrossReferenceAdapter crossReferencer) {
		return Collections.emptyList();// this prevent to have containment link in canonical mode
	}
}
