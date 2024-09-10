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
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.canonical.strategy;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.canonical.editpolicy.PapyrusCanonicalEditPolicy;

/**
 * A pluggable strategy for determining which edit part should be requested to create
 * canonical views on behalf of a requesting edit part.
 * 
 * @deprecated As of Neon, this strategy is no longer needed because the {@link PapyrusCanonicalEditPolicy}
 *             no longer uses the drag-and-drop infrastructure to create views of existing elements in the diagram.
 */
@Deprecated
public interface ICreationTargetStrategy {
	/**
	 * A creation-target strategy that always returns the requesting host edit-part as the creation target.
	 */
	ICreationTargetStrategy IDENTITY = new ICreationTargetStrategy() {

		@Override
		public EditPart getTargetEditPart(EditPart host, EObject element) {
			return host;
		}
	};

	/**
	 * Obtains the edit part to which the view creation request should be sent
	 * for the given model {@code element}, which was provided as a {@linkplain ISemanticChildrenStrategy semantic child} of the specified {@code host} edit-part.
	 * 
	 * @param host
	 *            the host edit-part of the {@link CanonicalEditPolicy} requesting creation of a view of an {@code element}
	 * @param element
	 *            the element for which a view is required
	 * 
	 * @return the target edit part, which may just be the {@code host} as is, or {@code null} if this strategy does not
	 *         provide a target
	 */
	EditPart getTargetEditPart(EditPart host, EObject element);

	//
	// Nested types
	//

	/**
	 * A factory for null-safe creation-target strategies.
	 */
	class Safe implements ICreationTargetStrategy {
		private final ICreationTargetStrategy delegate;

		private Safe(ICreationTargetStrategy delegate) {
			super();

			this.delegate = delegate;
		}

		/**
		 * Obtains a creation-target strategy that is ensured never to return a {@code null} target edit part.
		 * 
		 * @param strategy
		 *            a strategy to wrap. May be {@code null} if no actual strategy is applicable
		 * 
		 * @return a strategy, perhaps a wrapper or perhaps the original {@code strategy}, that is guaranteed always to provide a creation target
		 */
		public static ICreationTargetStrategy safe(ICreationTargetStrategy strategy) {
			return (strategy == null) ? IDENTITY : (strategy instanceof Safe) ? strategy : new Safe(strategy);
		}

		@Override
		public EditPart getTargetEditPart(EditPart host, EObject element) {
			EditPart specific = delegate.getTargetEditPart(host, element);
			return (specific == null) ? host : specific;
		}
	}
}
