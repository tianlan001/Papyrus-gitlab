/*
 * Copyright (c) 2006 Borland Software Corporation
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Michael Golubev (Borland) - initial API and implementation
 */

package org.eclipse.papyrus.uml.diagram.common.parser.lookup;

import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.uml2.uml.NamedElement;

public interface LookupResolver {

	// @unused
	public abstract void addLookupResolveRequest(LookupResolveRequest request, Callback callback);

	public abstract boolean isEmpty();

	public abstract boolean canResolve();

	// @unused
	public abstract AbstractTransactionalCommand getResolveCommand();

	public static interface Callback {

		public void lookupResolved(NamedElement resolution);
	}

	public static final LookupResolver NULL = new LookupResolver() {

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public AbstractTransactionalCommand getResolveCommand() {
			return null;
		}

		@Override
		public boolean canResolve() {
			return false;
		}

		@Override
		public void addLookupResolveRequest(LookupResolveRequest request, Callback callback) {
			//
		}

	};

}
