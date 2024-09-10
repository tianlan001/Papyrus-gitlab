/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.viewpoints.policy;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;


/**
 * Represents a helper for the handling of view creation commands
 *
 * @author Laurent Wouters
 */
public interface IViewTypeHelper {
	/**
	 * Determines whether the given type of view is supported by this helper
	 *
	 * @return <code>true</code> if the type of view is supported by this helper
	 */
	boolean isSupported(EClass type);

	/**
	 * Determines whether the given object represents a view that is supported by this helper
	 *
	 * @param view
	 *            The object to enquire
	 * @return <code>true</code> if the object represents a view supported by this helper
	 */
	boolean isSupported(EObject view);

	/**
	 * Gets the prototype for the given configuration element
	 *
	 * @param configuration
	 *            A view configuration element
	 * @return The associated prototype
	 * @since 2.0
	 */
	ViewPrototype getPrototypeFor(PapyrusRepresentationKind configuration);

	/**
	 * Gets the prototype of the given view
	 *
	 * @param view
	 *            The view
	 * @return The prototype of the given view
	 */
	ViewPrototype getPrototypeOf(EObject view);
}
