/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.activitygroup.groupcontainment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.activity.activitygroup.AbstractContainerNodeDescriptor;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The descriptor for InterruptibleActivityRegion node used by
 * org.eclipse.papyrus.uml.diagram.common.groups.groupcontainment extension point.
 *
 * @author vhemery
 */
public class InterruptibleActivityRegionContainment extends AbstractContainerNodeDescriptor {

	private Map<EStructuralFeature, EStructuralFeature> parentOpositeReferences;

	/**
	 * Get the eclass of the model eobject represented by the node
	 *
	 * @return InterruptibleActivityRegion eclass
	 */
	@Override
	public EClass getContainerEClass() {
		return UMLPackage.eINSTANCE.getInterruptibleActivityRegion();
	}

	/**
	 * Get the list of references linking the InterruptibleActivityRegion to
	 * children element.
	 *
	 * @return the references to contained elements
	 */
	@Override
	public List<EReference> getChildrenReferences() {
		return Collections.emptyList();
	}

	@Override
	public List<EReference> getParentReferences() {
		return Collections.singletonList(UMLPackage.Literals.ACTIVITY_GROUP__IN_ACTIVITY);
	}

	@Override
	public Map<EStructuralFeature, EStructuralFeature> getParentEOppositeReferences() {
		if (parentOpositeReferences == null) {
			parentOpositeReferences = new HashMap<>();
			parentOpositeReferences.put(UMLPackage.Literals.ACTIVITY_GROUP__IN_ACTIVITY, UMLPackage.Literals.ACTIVITY__OWNED_GROUP);
		}
		return parentOpositeReferences;
	}

	@Override
	public IGraphicalEditPart getCompartmentPartFromView(IGraphicalEditPart editpart) {
		String hint = "" + InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID;
		return ((GraphicalEditPart) editpart).getChildBySemanticHintOnPrimaryView(hint);
	}

	@Override
	public int getGroupPriority() {
		return IGroupPriority.INTERRUPTIBLE_REGION_PRIORITY;
	}
}
