/*******************************************************************************
 * Copyright (c) 2010, 2018 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA LIST - initial API and implementation
 *     Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 497289
 *     Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Bug 528199
 *     Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 533667
 *
 *******************************************************************************/
package org.eclipse.papyrus.extensionpoints.editors.definition;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Represented class for Extension point of Direct Editor.
 *
 * @deprecated since 3.1. Use {@link org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.DirectEditorExtensionPoint} instead.
 */
@Deprecated
public class DirectEditorExtensionPoint extends org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.DirectEditorExtensionPoint {

	/**
	 * Creates a new DirectEditorExtensionPoint, according to the ConfigurationElement
	 *
	 * @param configElt
	 *            the configuration element corresponding to the configuration
	 */
	public DirectEditorExtensionPoint(IConfigurationElement configElt) {
		super(configElt);
	}

}
