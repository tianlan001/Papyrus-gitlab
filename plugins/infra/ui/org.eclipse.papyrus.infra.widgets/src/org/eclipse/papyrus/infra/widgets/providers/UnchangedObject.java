/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;

import org.eclipse.papyrus.infra.widgets.messages.Messages;


public class UnchangedObject {

	private UnchangedObject() {

	}

	@Override
	public String toString() {
		return Messages.ReferenceDialogObservable_Unchanged;
	}

	public static final UnchangedObject instance = new UnchangedObject();
}
