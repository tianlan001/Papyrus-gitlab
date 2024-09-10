/*****************************************************************************
 * Copyright (c) 2015, 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 533667
 *
 *****************************************************************************/

package org.eclipse.papyrus.extensionpoints.editors.preferences;

import org.eclipse.jface.viewers.TableViewer;

/**
 * Editing Support for cell of Priority editor.
 *
 * @author Gabriel Pascual
 *
 * @deprecated since 3.1. Use {@link org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences.PriorityEditorEditingSupport} instead.
 */
@Deprecated
public class PriorityEditorEditingSupport extends org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.preferences.PriorityEditorEditingSupport {

	/**
	 * Constructor.
	 *
	 * @param viewer
	 *            the viewer
	 */
	public PriorityEditorEditingSupport(TableViewer viewer) {
		super(viewer);
	}
}
