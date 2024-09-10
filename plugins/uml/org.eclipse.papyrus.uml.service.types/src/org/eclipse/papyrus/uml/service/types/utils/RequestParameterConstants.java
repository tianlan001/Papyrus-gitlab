/*****************************************************************************
 * Copyright (c) 2011-2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.utils;

/**
 * <pre>
 * IDs of request parameters.
 * </pre>
 * 
 * @use org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants instead
 */
public interface RequestParameterConstants extends org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants {

	/**
	 * Parameter used to override som UML Constraint (for sysml)
	 */
	public static final String UML_STRICT = "UML_STRICT"; //$NON-NLS-1$

}
