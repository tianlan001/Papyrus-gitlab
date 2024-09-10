/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 433206
 *  Christian W. Damus - bug 420549
 *  Christian W. Damus - bug 477384
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.canonical.strategy;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;

import com.google.common.collect.Lists;

/**
 * A pluggable strategy for determination of semantic "children" of an element, those being
 * either contained elements, relationships of the element, or other related elements.
 */
public interface ISemanticChildrenStrategy {
	/**
	 * Queries the semantic elements that should be presented visually as children of the specified element.
	 *
	 * @param semanticFromEditPart
	 *            the semantic model element represented by a canonical edit-part in the diagram
	 * @param viewFromEditPart
	 *            the visual (notational) representation of the semantic element, to provide diagram context if required (such as
	 *            for determining the appropriate part-with-port match of a connector end)
	 * @return list of semantic elements, or {@code null} to indicate that the strategy does not support the given element.
	 *         <b>Note</b> that the result is specifically a {@link List} type to support synchronization of the order of
	 *         elements presented in list compartments
	 */
	public List<? extends EObject> getCanonicalSemanticChildren(EObject semanticFromEditPart, View viewFromEditPart);

	/**
	 * Queries the semantic elements that should be presented visually as connections attached to the specified element.
	 *
	 * @param semanticFromEditPart
	 *            the semantic model element represented by a canonical edit-part in the diagram
	 * @param viewFromEditPart
	 *            the visual (notational) representation of the semantic element, to provide diagram context if required (such as
	 *            for determining the appropriate part-with-port match of a connector end)
	 * @return list of semantic connection elements, or {@code null} to indicate that the strategy does not support the given element.
	 *         <b>Note</b> that the result is specifically a {@link List} type because the GMF {@link CanonicalEditPolicy} expects lists of children, not other kinds of collections
	 */
	public List<? extends EObject> getCanonicalSemanticConnections(EObject semanticFromEditPart, View viewFromEditPart);

	/**
	 * Retrieves objects, if any, related to the element behind a canonical edit-part
	 * on which the that element depends for canonical refresh updates.
	 * 
	 * @param semanticFromEditPart
	 *            the semantic model element represented by a canonical edit-part in the diagram
	 * @param viewFromEditPart
	 *            the visual (notational) representation of the semantic element, to provide diagram context if required (such as
	 *            for determining the appropriate part-with-port match of a connector end)
	 * @return dependents of the semantic element, or {@code null} to indicate that it does not support dependents
	 */
	public Collection<? extends EObject> getCanonicalDependents(EObject semanticFromEditPart, View viewFromEditPart);

	/**
	 * Obtains the source of a semantic connection element. This may either be an
	 * {@link EObject} in the most common straight-forward case, or a
	 * {@link VisuallyNestedElements} in the more complex case of an element whose
	 * visualization is repeated in a nesting structure that qualifies each repeated
	 * occurrence (such as in the case of connected ports on parts in an UML composite
	 * structure diagram).
	 * 
	 * @param connectionElement
	 *            a semantic connection in the model to be visualized
	 * 
	 * @return the connection's source element, or {@code null} if not known
	 */
	public default Object getSource(EObject connectionElement) {
		return null;
	}

	/**
	 * Obtains the target of a semantic connection element. This may either be an
	 * {@link EObject} in the most common straight-forward case, or a
	 * {@link VisuallyNestedElements} in the more complex case of an element whose
	 * visualization is repeated in a nesting structure that qualifies each repeated
	 * occurrence (such as in the case of connected ports on parts in an UML composite
	 * structure diagram).
	 * 
	 * @param connectionElement
	 *            a semantic connection in the model to be visualized
	 * 
	 * @return the connection's target element, or {@code null} if not known
	 */
	public default Object getTarget(EObject connectionElement) {
		return null;
	}

	public default IGraphicalEditPart resolveSourceEditPart(EObject connectionElement, IGraphicalEditPart context) {
		return VisuallyNestedElementsImpl.resolveEditPart(getSource(connectionElement), context);
	}

	public default IGraphicalEditPart resolveTargetEditPart(EObject connectionElement, IGraphicalEditPart context) {
		return VisuallyNestedElementsImpl.resolveEditPart(getTarget(connectionElement), context);
	}

	public static VisuallyNestedElements createVisualStack(EObject top, EObject nested, EObject... more) {
		return new VisuallyNestedElementsImpl(Lists.asList(top, nested, more));
	}

	//
	// Nested types
	//

	/**
	 * <p>
	 * In the case that the presentation of a connection end is more complex
	 * than simply a shape, where in fact the connection end may be visualized
	 * multiple times in the diagram and each occurrence is semantically qualified
	 * by the elements that visually contain it (possibly recursively to any
	 * depth), then a {@code VisuallyNestedElements} is supplied to represent
	 * that structure.
	 * </p>
	 * <p>
	 * This interface is a sequence of elements where the first is the most
	 * deeply nested element and the last is the element that is, truly, the
	 * connection end that is nested within all those that precede it in the list.
	 * This is convenient for iterative searching of the diagram for edit-parts
	 * that present the stack of elements.
	 * </p>
	 * <p>
	 * The canonical (pun intended) case is in the UML Composite Structure Diagram,
	 * where a connector may connect a port that is on a part. Any number of parts
	 * (or the composite classifier, itself) may also feature this port, but the
	 * connector end qualifies the port with the featuring part that is actually
	 * connected via that port.
	 * </p>
	 * 
	 * @see ISemanticChildrenStrategy#getSource(EObject)
	 * @see ISemanticChildrenStrategy#getTarget(EObject)
	 */
	interface VisuallyNestedElements extends List<EObject> {
		/**
		 * Obtains the topmost element of the nesting structure.
		 * 
		 * @return the top element
		 */
		EObject top();

		/**
		 * Obtains the nesting of elements within the {@linkplain #top() top} element,
		 * recursively. The {@code top} of the result is visually presented "within"
		 * my {@code top}.
		 * 
		 * @return the elements visually nested with the {@linkplain #top() top} element,
		 *         or {@code null} if none (when my {@code top} is, in fact, the deepest nested element)
		 */
		VisuallyNestedElements nested();
	}
}
