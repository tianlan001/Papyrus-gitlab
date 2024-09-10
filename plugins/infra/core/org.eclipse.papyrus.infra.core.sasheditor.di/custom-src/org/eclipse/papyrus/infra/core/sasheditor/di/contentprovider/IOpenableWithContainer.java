/*****************************************************************************
 * Copyright (c) 2011 Atos
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
 *  Tristan FAURE - tristan.faure@atos.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider;

/**
 * Interface for adaptation, an IOpenable provides the getPageIdentifier to know how to open it
 *
 * @author tfaure
 *
 */
public interface IOpenableWithContainer extends IOpenable {

	Object getContainer();

	static class Openable extends IOpenable.Openable implements IOpenableWithContainer {

		private final Object container;

		public Openable(Object identifier, Object container) {
			super(identifier);
			this.container = container;
		}

		@Override
		public Object getContainer() {
			return container;
		}
	}
}
