/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets;

import org.eclipse.jface.viewers.DoubleClickEvent;

/**
 * An interface to add the possibility to fire programmatically double click and element such as viewer.
 * @since 3.0
 */
public interface IFireDoubleClick {

	/**
	 * fire the double click.
	 * 
	 * @param event
	 *            The Doubleclick event.
	 */
	public void fireDoubleClick(DoubleClickEvent event);

}
