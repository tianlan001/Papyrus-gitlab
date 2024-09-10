/*****************************************************************************
 * Copyright (c) 2009 CEA LIST & LIFL 
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
package org.eclipse.papyrus.example.core.sashwindows.fulleditor.texteditor;

import org.eclipse.ui.editors.text.TextEditor;


/**
 * A TextEditor that can be used as Tab of a sashes window.
 * 
 * @author dumoulin
 */
public class TabTextEditor extends TextEditor {

	/**
	 * 
	 */
	public TabTextEditor() {
	}

	/**
	 * Set the name of this part. {@inheritDoc}
	 */
	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}
}
