/*****************************************************************************
 * Copyright (c) 2008, 2023 CEA LIST.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 581660
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.multidiagram.actionbarcontributor;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.papyrus.infra.core.editor.BackboneException;
import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * Descriptor of an ActionBarContributor. This descriptor is usually loaded from
 * the Eclipse extension mechanism.
 *
 * @author Cedric Dumoulin
 * @author Patrick Tessier
 * @since 1.2
 *
 */
public class ActionBarContributorDescriptor {

	protected Class<? extends EditorActionBarContributor> contextClass;

	protected String contextId;

	/**
	 * Instance is created when requested.
	 */
	protected EditorActionBarContributor instance = null;

	/**
	 * constructor.
	 *
	 * @return the context descriptor
	 * @throws BackboneException
	 */
	protected EditorActionBarContributor getActionBarContributor() throws BackboneException {
		if (instance == null) {
			instance = createActionBarContributor();
		}

		return instance;
	}

	private EditorActionBarContributor createActionBarContributor() throws BackboneException {
		try {
			EditorActionBarContributor context = contextClass.getDeclaredConstructor().newInstance();
			return context;

		} catch (SecurityException e) {
			// Lets propagate. This is an implementation problem that should be
			// solved by programmer.
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			// Lets propagate. This is an implementation problem that should be
			// solved by programmer.
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// Lets propagate. This is an implementation problem that should be
			// solved by programmer.
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			// Lets propagate. This is an implementation problem that should be
			// solved by programmer.
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			// Lets propagate. This is an implementation problem that should be
			// solved by programmer.
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			// Lets propagate. This is an implementation problem that should be
			// solved by programmer.
			throw new RuntimeException(e);
		}
	}

} // end class
