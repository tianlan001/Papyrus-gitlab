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

import org.eclipse.core.runtime.IStatus;

/**
 * This allows to manage the "other" status (attach to the existing).
 */
public interface IPapyrusNattableStatus extends IStatus{

	/** 
	 * Status type severity (bit mask, value 16) indicating this status represents a paste configuration info.
	 * @see #getSeverity()
	 * @see #matches(int)
	 */
	public static final int PASTE_CONFIGURATiON_INFO = 0x16;
	
	/** 
	 * Status type severity (bit mask, value 32) indicating this status represents a paste configuration warning.
	 * @see #getSeverity()
	 * @see #matches(int)
	 */
	public static final int PASTE_CONFIGURATiON_WARNING = 0x32;
	
	/** 
	 * Status type severity (bit mask, value 64) indicating this status represents a paste configuration error.
	 * @see #getSeverity()
	 * @see #matches(int)
	 */
	public static final int PASTE_CONFIGURATiON_ERROR = 0x64;
	
}
