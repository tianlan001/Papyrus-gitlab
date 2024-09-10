/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.api;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IComponentModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Papyrus shall provide to the developer, elements to display its own swt.composite as an editor as inside the sash editor.
 * see also org.eclipse.papyrus.infra.ui.extension.diagrameditor.AbstractEditorFactory to create a factory that provide it
 * see also the extension point to reference the factory org.eclipse.papyrus.infra.ui.papyrusDiagram
 */
public interface ISWTCompositeWrapper extends IComponentModel, IPapyrusEditorDeclaration {

	/**
	 * Create the Control corresponding to this model.
	 *
	 * @param parent
	 *            The parent of the created container.
	 * @return
	 */
	@Override
	public Composite createPartControl(Composite parent);

}
