/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.notification.events;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

public abstract class AbstractIdentityEvent extends AbstractTypesEvent {

	public AbstractIdentityEvent(IEditCommandRequest req, IEditHelper editHelper) {
		super(req, editHelper);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.types.core.notification.events.ITypesEvent#getEventType()
	 *
	 * @return
	 */
	@Override
	public TypesEventKind getEventType() {
		return TypesEventKind.Identity;
	}
}
