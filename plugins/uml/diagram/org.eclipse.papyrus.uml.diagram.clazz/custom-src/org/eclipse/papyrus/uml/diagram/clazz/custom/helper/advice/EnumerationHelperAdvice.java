/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bonnabesse Fanch (ALL4TEC) fanch.bonnabesse@alltec.net - Bug 476872
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.helper.advice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPart;
import org.eclipse.papyrus.uml.diagram.common.util.CrossReferencerUtil;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This HelperAdvice completes {@link Enumeration} edit commands with diagram specific
 * commands in order to remove inconsistent views.
 */
public class EnumerationHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getBeforeSetCommand(final SetRequest request) {
		ICommand moveCommand = super.getBeforeSetCommand(request);

		EObject elementToEdit = request.getElementToEdit();

		if ((null != elementToEdit) && (elementToEdit instanceof Enumeration)) {
			EStructuralFeature feature = request.getFeature();

			if (UMLPackage.eINSTANCE.getEnumeration_OwnedLiteral().equals(feature)) {
				// The new lists of owned literals of the Enumeration
				Object value = request.getValue();

				if (value instanceof ArrayList<?>) {
					EList<EnumerationLiteral> ownedLiterals = ((Enumeration) elementToEdit).getOwnedLiterals();

					List<EObject> elementsToMove = substractLists(new ArrayList<EObject>(ownedLiterals), (ArrayList<EObject>) value);

					if (null != elementsToMove) {
						// After compare
						Set<View> viewsToDestroy = new HashSet<>();

						Iterator<EObject> it = elementsToMove.iterator();

						while (it.hasNext()) {
							EObject eObject = it.next();
							viewsToDestroy.addAll(getMemberViewsToDestroy(eObject));
						}

						Iterator<View> viewToDestroyIterator = viewsToDestroy.iterator();
						while (viewToDestroyIterator.hasNext()) {
							View view = viewToDestroyIterator.next();
							DeleteCommand destroyViewsCommand = new DeleteCommand(request.getEditingDomain(), view);
							moveCommand = CompositeCommand.compose(moveCommand, destroyViewsCommand);
						}
					}
				}
			}
		}

		return moveCommand;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterMoveCommand(org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterMoveCommand(final MoveRequest request) {
		ICommand moveCommand = super.getAfterMoveCommand(request);

		Set<View> viewsToDestroy = new HashSet<>();

		Object parameter = request.getParameter(RequestParameterConstants.TYPE_MOVING);

		if ((null == parameter) || (!RequestParameterConstants.TYPE_MOVING_DIAGRAM.equals(parameter))) {
			@SuppressWarnings("unchecked")
			Iterator<EObject> it = request.getElementsToMove().keySet().iterator();
			while (it.hasNext()) {
				EObject eObject = it.next();
				viewsToDestroy.addAll(getMemberViewsToDestroy(eObject));
			}
			Iterator<View> viewToDestroyIterator = viewsToDestroy.iterator();
			while (viewToDestroyIterator.hasNext()) {
				View view = viewToDestroyIterator.next();
				DeleteCommand destroyViewsCommand = new DeleteCommand(request.getEditingDomain(), view);
				moveCommand = CompositeCommand.compose(moveCommand, destroyViewsCommand);
			}
		}

		return moveCommand;

	}

	/**
	 * This methods looks for inconsistent views to delete in case the Enumeration or a child is deleted or
	 * re-oriented.
	 *
	 * @param object
	 *            the modified Enumeration
	 * @return the list of {@link View} to delete
	 */
	protected Set<View> getMemberViewsToDestroy(final EObject object) {
		Set<View> viewsToDestroy = new HashSet<>();
		// Find Views in Class Diagram that are referencing current member
		Iterator<View> viewIt = CrossReferencerUtil.getCrossReferencingViews(object, ModelEditPart.MODEL_ID).iterator();
		while (viewIt.hasNext()) {
			View view = viewIt.next();

			View viewContainer = ViewUtil.getViewContainer(view);

			String containerType = viewContainer != null ? viewContainer.getType() : null;

			// Views are to be destroyed if they are not the diagram itself (containerType == null)
			// and not a view directly owned by the diagram (the current policy in Papyrus allows
			// to drop nearly anything in the diagram whatever the semantic container).
			if ((containerType != null) && !ModelEditPart.MODEL_ID.equals(containerType)) {
				viewsToDestroy.add(view);
			}
		}
		return viewsToDestroy;
	}

	/**
	 *
	 * @param listOne
	 * @param listTwo
	 * @return
	 */
	protected List<EObject> substractLists(final List<EObject> listOne, final List<EObject> listTwo) {
		List<EObject> sourceList = new ArrayList<>(listOne);
		List<EObject> destinationList = new ArrayList<>(listTwo);

		sourceList.removeAll(listTwo);
		destinationList.removeAll(listOne);

		if (!sourceList.isEmpty()) {
			return sourceList;
		} else if (!destinationList.isEmpty()) {
			return destinationList;
		} else {
			return null;
		}
	}
}
