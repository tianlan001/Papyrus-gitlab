/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
package org.eclipse.papyrus.infra.services.labelprovider.service;

import org.eclipse.jface.viewers.ILabelProvider;

/**
 * A LabelProvider which may use a context element to compute its labels.
 * It can be used to compute e.g. relative paths from a base namespace
 *
 * @author Camille Letavernier
 */
public interface ContextualLabelProvider extends ILabelProvider {

	/**
	 * Sets the context element against which the labels will be computed
	 *
	 * @param contextElement
	 *            the context element against which the labels will be computed (e.g. base namespace for relative paths)
	 */
	public void setContext(Object contextElement);
}
