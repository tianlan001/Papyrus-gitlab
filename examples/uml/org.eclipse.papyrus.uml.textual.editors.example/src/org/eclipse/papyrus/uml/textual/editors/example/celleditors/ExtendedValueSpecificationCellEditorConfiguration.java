/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.textual.editors.example.celleditors;

import org.eclipse.papyrus.uml.nattable.xtext.valuespecification.celleditor.ValueSpecificationCellEditorConfiguration;

/**
 * The configuration for the ConnectorEnd cell editor
 */
public class ExtendedValueSpecificationCellEditorConfiguration extends
		ValueSpecificationCellEditorConfiguration {
	
	/**
	 * The identifier of xtext cell editor for the ValueSpecification.
	 */
	private static final String XTEXT_VALUE_SPECIFICATION_CELL_EDITOR_IDENTIFIER = "XTEXT_ValueSpecification_Cell_Editor_Example"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public ExtendedValueSpecificationCellEditorConfiguration() {
		super();
	}
	
	@Override
	public String getEditorConfigId() {
		return XTEXT_VALUE_SPECIFICATION_CELL_EDITOR_IDENTIFIER;
	}

}
