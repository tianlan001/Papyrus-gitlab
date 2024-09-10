/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * @since 3.0
 */
public class ViewUtils {


	public static String getContainingDiagramType(final View view) {
		if (view instanceof Diagram) {
			final Diagram diagram = (Diagram) view;
			return diagram.getType();
		}
		final EObject eContainer = view.eContainer();
		if (eContainer instanceof View) {
			final View parentView = (View) eContainer;
			return getContainingDiagramType(parentView);
		}
		return null;
	}


	public static boolean isContained(final View child, final View parent) {
		View view = child;
		while (view != null) {
			if (view == parent) {
				return true;
			}
			final EObject eContainer = view.eContainer();
			if (eContainer instanceof View) {
				view = (View) eContainer;
			} else {
				break;
			}
		}
		return false;
	}

	/**
	 * @since 3.0
	 */
	public static boolean isViewFor(final View view, final EClass... eClasses) {
		final EObject element = view.getElement();
		for (final EClass eClass : eClasses) {
			if (eClass.isInstance(element)) {
				return true;
			}
		}
		return false;
	}

	/** Returns whether the given View contains a child View for the given {@link EObject} */
	public static boolean containsViewFor(final View parentView, final EObject eObject) {
		if (parentView.getElement() == eObject) {
			return true;
		}
		@SuppressWarnings("unchecked")
		final EList<View> children = parentView.getChildren();
		for (final View child : children) {
			if (containsViewFor(child, eObject)) {
				return true;
			}
		}
		return false;
	}



	public static void selectInViewer(final View view, final EditPartViewer viewer) {
		final EditPart editPart = (EditPart) viewer.getEditPartRegistry().get(view);
		if (editPart != null) {
			viewer.setSelection(new StructuredSelection(editPart));
		}
	}

}
