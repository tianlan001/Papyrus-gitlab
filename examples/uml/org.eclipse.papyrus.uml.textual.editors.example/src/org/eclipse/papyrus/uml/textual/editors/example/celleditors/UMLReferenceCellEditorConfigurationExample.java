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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.textual.editors.example.celleditors;

import org.eclipse.papyrus.uml.nattable.config.UMLSingleReferenceTextualCellEditorWithButtonConfiguration;

/**
 * 
 * This class provides the configuration to edit UML References single and multi-valued with a text editor
 *
 */
public class UMLReferenceCellEditorConfigurationExample extends UMLSingleReferenceTextualCellEditorWithButtonConfiguration {

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.config.UMLSingleReferenceTextualCellEditorWithButtonConfiguration#getEditorConfigId()
	 *
	 * @return
	 */
	@Override
	public String getEditorConfigId() {
		return "org.eclipse.papyrus.editors.example.textualreferenceeditor"; //$NON-NLS-1$
	}

}
