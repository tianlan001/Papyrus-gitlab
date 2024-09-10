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
package org.eclipse.papyrus.infra.tools.databinding;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.common.command.Command;


/**
 * @since 2.0
 */
public interface CommandBasedObservable extends IObservable {

	/**
	 * Returns the EMF Command for modifying this Observable's value
	 *
	 * @param value
	 * @return
	 */
	public Command getCommand(Object value);
}
