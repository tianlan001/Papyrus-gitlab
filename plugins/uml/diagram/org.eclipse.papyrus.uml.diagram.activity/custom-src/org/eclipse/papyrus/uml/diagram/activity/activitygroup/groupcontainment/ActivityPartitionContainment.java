/*****************************************************************************
 * Copyright (c) 2011 Atos.
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
 *   Arthur Daussy (Atos) - Initial API and implementation
 *   Arthur Daussy - 371712 : 372745: [ActivityDiagram] Major refactoring group framework
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.activitygroup.groupcontainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.activity.activitygroup.AbstractContainerNodeDescriptor;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityPartitionActivityPartitionContentCompartmentEditPart;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The descriptor for ActivityPartition node used by
 * org.eclipse.papyrus.uml.diagram.common.groups.groupcontainment extension point.
 *
 * @author vhemery
 */
public class ActivityPartitionContainment extends AbstractContainerNodeDescriptor {

	private List<EReference> parentReferences;

	private Map<EStructuralFeature, EStructuralFeature> parentOpositeReferences;

	/**
	 * Get the eclass of the model eobject represented by the node
	 *
	 * @return ActivityPartition eclass
	 */
	@Override
	public EClass getContainerEClass() {
		return UMLPackage.eINSTANCE.getActivityPartition();
	}

	/**
	 * Get the list of references linking the ActivityPartition to children
	 * element.
	 *
	 * @return the references to contained elements
	 */
	@Override
	public List<EReference> getChildrenReferences() {
		List<EReference> references = new ArrayList<>(3);
		references.add(UMLPackage.eINSTANCE.getActivityPartition_Subpartition());
		references.add(UMLPackage.eINSTANCE.getElement_OwnedComment());
		return references;
	}

	@Override
	public List<EReference> getParentReferences() {
		if (parentReferences == null) {
			parentReferences = new ArrayList<>();
			parentReferences.add(UMLPackage.Literals.ACTIVITY_PARTITION__SUPER_PARTITION);
			parentReferences.add(UMLPackage.Literals.ACTIVITY_GROUP__IN_ACTIVITY);
		}
		return parentReferences;
	}

	@Override
	public Map<EStructuralFeature, EStructuralFeature> getParentEOppositeReferences() {
		if (parentOpositeReferences == null) {
			parentOpositeReferences = new HashMap<>();
			parentOpositeReferences.put(UMLPackage.Literals.ACTIVITY_PARTITION__SUPER_PARTITION, UMLPackage.Literals.ACTIVITY_PARTITION__SUBPARTITION);
			parentOpositeReferences.put(UMLPackage.Literals.ACTIVITY_GROUP__IN_ACTIVITY, UMLPackage.Literals.ACTIVITY__OWNED_GROUP);
		}
		return parentOpositeReferences;
	}

	@Override
	public IGraphicalEditPart getCompartmentPartFromView(IGraphicalEditPart editpart) {
		String hint = "" + ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID;
		return ((GraphicalEditPart) editpart).getChildBySemanticHintOnPrimaryView(hint);
	}

	@Override
	public int getGroupPriority() {
		return IGroupPriority.ACTIVITY_PARTITION_PRIORITY;
	}
}
