/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.nattable.registry;

import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.graphics.Image;

/**
 * Image registry to use for EOperation
 */
public class EOperationImageRegistry {

	/**
	 * The EOperation icon path.
	 */
	private static final String OPERATION_ICON_PATH = "/icons/EOperation.gif"; //$NON-NLS-1$

	/**
	 * This allow to get the image.
	 *
	 * @param path
	 *            a path.
	 * @return
	 *         the image loaded from this path.
	 */
	private static final Image getImage(final String path) {
		return Activator.getDefault().getImage(org.eclipse.papyrus.infra.emf.nattable.Activator.PLUGIN_ID, path);
	}

	/**
	 * Get the operation icon.
	 *
	 * @return
	 *         the operation icon.
	 */
	public static Image getOperationIcon() {
		return EOperationImageRegistry.getImage(OPERATION_ICON_PATH);
	}
}
