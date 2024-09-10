/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.canonical.strategy;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewType;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

import com.google.common.collect.Lists;

/**
 * A pluggable strategy for determination of visual "children" of an element, those being
 * either child nodes or connected edges.
 */
public interface IVisualChildrenStrategy {
	/**
	 * A default implementation of the visual-children strategy that provides:
	 * 
	 * <ul>
	 * <li>for child nodes, all immediately contained (groups are expanded implicitly) nodes
	 * that represent a different element than the parent (so, excluding normal compartments
	 * and labels)</li>
	 * <li>for edges, all unique incoming and outgoing edges that represent actual elements
	 * (not just references)</li>
	 * </ul>
	 * 
	 * The lists provided by this implementation are mutable, so it is useful for delegation
	 * with filtering.
	 */
	IVisualChildrenStrategy DEFAULT = new IVisualChildrenStrategy() {

		@Override
		public List<? extends View> getCanonicalChildren(EditPart editPart, View view) {
			List<View> result = Lists.newArrayListWithCapacity(view.getChildren().size());

			collectChildren(view, result);

			// Filter out child views that visualize the same element as my view (these
			// should be compartments, decorations, labels)
			final EObject semantic = ((IGraphicalEditPart) editPart).resolveSemanticElement();
			for (Iterator<? extends View> iter = result.iterator(); iter.hasNext();) {
				final EObject nextElement = iter.next().getElement();

				if (nextElement == semantic) {
					iter.remove();
				}
			}

			return result;
		}

		protected void collectChildren(View view, List<? super View> result) {
			for (Object child : view.getChildren()) {
				if (child instanceof View) {
					View childView = (View) child;

					// Elide groups
					if (ViewType.GROUP.equals(childView.getType())) {
						collectChildren(childView, result);
					} else {
						result.add(childView);
					}
				}
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<? extends View> getCanonicalEdges(EditPart editPart, View view) {
			// Get distinct incoming and outgoing edges of top shapes and border items.
			// Compartments of top shapes take care of their own contents
			List<View> result = Lists.newArrayList();

			if (view.eIsSet(NotationPackage.Literals.VIEW__SOURCE_EDGES)) {
				result.addAll(view.getSourceEdges());
			}
			if (view.eIsSet(NotationPackage.Literals.VIEW__TARGET_EDGES)) {
				for (Edge next : (Iterable<? extends Edge>) view.getTargetEdges()) {
					if (next.getSource() != view) {
						// Didn't already get a self-edge from the source edges
						result.add(next);
					}
				}
			}

			// And filter out any edges that do not represent a semantic element (reference links, such as
			// for Constraint::constrainedElement)
			for (ListIterator<View> iter = result.listIterator(); iter.hasNext();) {
				View next = iter.next();

				// An unset-null would mean that it represents the parent view's semantic element
				if (next.isSetElement() && (next.getElement() == null)) {
					iter.remove();
				}
			}

			return result;
		}
	};

	/**
	 * Queries the visual elements that are children a view that should be synchronized
	 * with the represented element's semantic "children".
	 *
	 * @param editPart
	 *            the edit-part being synchronized
	 * @param view
	 *            the edit-part's notation view
	 * @return a list of child views, or {@code null} to indicate that the strategy does not support the given edit-part.
	 *         <b>Note</b> that the result is specifically a {@link List} type to support synchronization of the order of
	 *         elements presented in list compartments
	 */
	public List<? extends View> getCanonicalChildren(EditPart editPart, View view);

	/**
	 * Queries the visual elements that are edges connected to a view that should be synchronized
	 * with the represented element's semantic "children" or "connections". The returned views may
	 * alternatively or also include child views of connected edges, in the case that those children
	 * (e.g., labels) are the significant representative views.
	 *
	 * @param editPart
	 *            the edit-part being synchronized
	 * @param view
	 *            the edit-part's notation view
	 * @return a list of connected edges (or some child views of connected edges), or {@code null} to indicate that the strategy does not support the given edit-part.
	 *         <b>Note</b> that the result is specifically a {@link List} type because the GMF {@link CanonicalEditPolicy} expects lists of views, not other kinds of collections
	 */
	public List<? extends View> getCanonicalEdges(EditPart editPart, View view);

}
