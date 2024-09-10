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
package org.eclipse.papyrus.infra.properties.ui.providers;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;


public interface CreateInFeatureContentProvider extends IStaticContentProvider, IStructuredContentProvider {

	/**
	 * Sets the type of feature we're looking for
	 *
	 * @param type
	 */
	public void setType(EClass type);
}
