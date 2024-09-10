/*****************************************************************************
 * Copyright (c) 2009 LIFL, CEA LIST and others.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.contentprovider;

import org.eclipse.swt.widgets.Composite;

/**
 * This model represent a ui Component to be shown in a sash window folder.
 *
 * @author dumoulin
 *
 */
public interface IComponentModel extends IPageModel {

	/**
	 * Create the Control corresponding to this model.
	 *
	 * @param parent
	 *            The parent of the created container.
	 * @return
	 */
	public Composite createPartControl(Composite parent);

}
