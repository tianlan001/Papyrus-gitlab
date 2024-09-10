/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.dnd.commands;

import java.util.Iterator;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityEditPart;

/**
 * Extends the generic class to create and update an ActivityParamterNode
 * Need to specified the targetEditPart container
 *
 * @since 3.5.0
 */
public class CreateActivityParameterNodeAndUpdateCommand<T extends EObject, E extends EObject, S extends EObject> extends CreateTAndUpdateCommand<T, E, S> {

	/**
	 * Constructor.
	 *
	 * @param targetEditPart
	 * @param typeParameterClass
	 * @param targetElement
	 * @param sourceElement
	 * @param headless
	 * @param typeToCreate
	 * @param structuralFeatureToSet
	 * @param location
	 * @param dropEditPartVisualID
	 */
	public CreateActivityParameterNodeAndUpdateCommand(EditPart targetEditPart, Class<T> typeParameterClass, E targetElement, S sourceElement, boolean headless, IHintedType typeToCreate, EStructuralFeature structuralFeatureToSet, Point location,
			String dropEditPartVisualID) {
		super(targetEditPart, typeParameterClass, targetElement, sourceElement, headless, typeToCreate, structuralFeatureToSet, location, dropEditPartVisualID);
	}

	/**
	 * Get the parent edit part, in the case of the activity parameter node it is not
	 * the activityCompartement but the ActivityEditPArt itself since it is a border item
	 */
	@Override
	protected EditPart getOwnerEditPart() {
		ActivityEditPart compartmentEditPart = null;
		if (targetEditPart instanceof ActivityEditPart) {
			compartmentEditPart = (ActivityEditPart) targetEditPart;
		} else {
			Iterator<?> children = targetEditPart.getChildren().iterator();
			while (children.hasNext() && null == compartmentEditPart) {
				Object child = children.next();
				if (child instanceof ActivityEditPart) {
					compartmentEditPart = (ActivityEditPart) child;
				}
			}
		}
		return compartmentEditPart;
	}

}
