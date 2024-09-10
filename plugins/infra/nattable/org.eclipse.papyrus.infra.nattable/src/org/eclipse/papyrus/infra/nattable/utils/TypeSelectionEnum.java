/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.infra.nattable.utils;

/**
 * The enumeration to manage the table selection (none, cell, row or column type)
 */
public enum TypeSelectionEnum {
	NONE("None"), 
	CELL("cell"), 
	COLUMN("column"), 
	ROW("row");
	
	/**
	 * The text corresponding to the enum item.
	 */
	private final String text;

	/**
	 * Constructor.
	 *
	 * @param text The text corresponding to the enum item.
	 */
	private TypeSelectionEnum(final String text) {
	    this.text = text;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
	    return text;
	}
	
	/**
	 * Get the enum item corresponding to the value.
	 * 
	 * @param value The string value.
	 * @return The corresponding item.
	 */
	public static TypeSelectionEnum getTypeSelection(final String value){
		TypeSelectionEnum result = NONE;
		if(CELL.toString().equals(value)){
			result = CELL;
		} else if(COLUMN.toString().equals(value)){
			result = COLUMN;
		} else if(ROW.toString().equals(value)){
			result = ROW;
		}
		
		return result;
	}
}