/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.xtext.custom;

import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage;

/**
 * a custom OutlinePage where dispose is inside a try catch to avoid a Papyrus crash in some case
 */
public class PapyrusOutlinePage extends OutlinePage {

	/**
	 * @see org.eclipse.xtext.ui.editor.outline.impl.OutlinePage#dispose()
	 *
	 */
	@Override
	public void dispose() {
		try {
			super.dispose();
		} catch (Exception e) {
			// silent exception
		}
	}
}
