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
package org.eclipse.papyrus.infra.properties.ui.widgets;

import java.util.Map;

/**
 * An interface for providing Masks for editing mask-based integer values
 *
 * @author Camille Letavernier
 *
 * @see StringMask
 */
public interface MaskProvider {

	/**
	 * @return the list of masks and their String descriptions
	 */
	public Map<String, String> getMasks();
}
