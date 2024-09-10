/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.editors;

import org.eclipse.nebula.widgets.nattable.edit.editor.CheckBoxCellEditor;
import org.eclipse.papyrus.uml.nattable.matrix.configs.CellMatrixRelationshipEnum;

/**
 * A customCheckbox Cell Editor for Relationship Matrix
 *
 */
public class CustomCheckBoxCellEditor extends CheckBoxCellEditor {


	/**
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.CheckBoxCellEditor#setEditorValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	public void setEditorValue(Object value) {
		if (value instanceof CellMatrixRelationshipEnum) {
			final CellMatrixRelationshipEnum val = (CellMatrixRelationshipEnum) value;
			switch (val) {
			case CHECKED:
			case CHECKED_MORE_THAN_2_ENDS:
			case CHECKED_MORE_THAN_ONE_LINK:
				super.setEditorValue(Boolean.TRUE);
				break;
			default:
				super.setEditorValue(Boolean.FALSE);
				break;
			}
			return;
		}
		super.setEditorValue(value);
	}

}
