/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.preferences;

/**
 * Constant to use for the management of stereotype strategy
 */

public interface IStereotypePasteStrategyPreferenceConstant {

	public String PROFILE_STRATEGY = "PROFILE_STRATEGY";

	// choices for PROFILE_STRATEGY

	public String ASK_POPUP = "ASK_POPUP";

	public String IMPORT_MISSING_PROFILE = "IMPORT_MISSING_PROFILE";

	public String IGNORE_MISSING_PROFILE = "IGNORE_MISSING_PROFILE";

	// / end
}
