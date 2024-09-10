/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Adapted code from the class diagram
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.profile.custom.preferences;

import java.rmi.server.Operation;

/**
 * Constants for {@link Operation} preferences.
 */
public interface IPapyrusOperationPreferencesConstant {

	/** preference id for operation display label */
	public String OPERATION_LABEL_DISPLAY_PREFERENCE = "Operation.label.display"; //$NON-NLS-1$

	/** preference id for operation display label */
	public String OPERATION_PREF_FONT = "Operation.font"; //$NON-NLS-1$

	/** preference id for operation display label */
	public String OPERATION_PREF_FONT_COLOR = "Operation.font.color"; //$NON-NLS-1$

	/** preference id for operation display label */
	public String OPERATION_PREF_LINE_COLOR = "Operation.line.color"; //$NON-NLS-1$
}
