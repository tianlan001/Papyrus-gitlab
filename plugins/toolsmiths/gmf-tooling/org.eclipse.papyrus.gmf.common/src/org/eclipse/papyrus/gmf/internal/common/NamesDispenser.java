/******************************************************************************
 * Copyright (c) 2006, 2020 Eclipse.org, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common;

/**
 * Maintains names pool and provides names accordingly to some convention.
 * 
 * @author dstadnik
 */
public interface NamesDispenser {

	/**
	 * @return true if the name was dispensed
	 */
	public boolean contains(String name);

	/**
	 * Dispenses a name. Returned name is added to the pool of dispensed names.
	 * 
	 * @param prefixPart prefix of the name, null means no prefix
	 * @param semanticPart middle part of the name
	 * @param suffixPart suffix of the name, null means no suffix
	 * @return a name
	 */
	public String get(String prefixPart, String semanticPart, String suffixPart);

	/**
	 * Dispenses a name with null prefix.
	 */
	public String get(String semanticPart, String suffixPart);

	/**
	 * Dispenses a name with null prefix and suffix.
	 */
	public String get(String semanticPart);

	/**
	 * Notifies dispenser that the name should be considered being dispensed.
	 * 
	 * @return true if the name was not dispensed before
	 */
	public boolean add(String name);

	/**
	 * Clears all dispensed names data
	 */
	public void clear();
}
