/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 417409
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.constraints.runtime;

import java.util.Collection;
import java.util.Set;

import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.DisplayUnit;

/**
 * An interface representing a Constraint Engine.
 * The Constraint Engine is responsible for retrieving the DisplayUnits
 * to display for a given ISelection.
 *
 * @author Camille Letavernier
 * @param <E>
 *            The type of DisplayUnit managed by this Constraint Engine
 */
public interface ConstraintEngine<E extends DisplayUnit> {

	/**
	 * Returns the DisplayUnits matching the given selection
	 *
	 * @param a
	 *            plastic "selection" of objects, which may be a {@link Collection} of
	 *            some kind, something convertible to a collection (like a JFace {@code IStructuredSelection}),
	 *            or just a single object
	 * 
	 * @return the display units applicable to the selection
	 * @since 2.0
	 */
	public Set<E> getDisplayUnits(Object selection);

	/**
	 * Adds a constraint descriptor to this engine
	 *
	 * @param descriptor
	 */
	public void addConstraint(ConstraintDescriptor descriptor);

	/**
	 * Indicate that the available constraints might have changed
	 * Refreshes the Constraint Engine
	 */
	public void refresh();

	void addConstraintEngineListener(ConstraintEngineListener listener);

	void removeConstraintEngineListener(ConstraintEngineListener listener);
}
