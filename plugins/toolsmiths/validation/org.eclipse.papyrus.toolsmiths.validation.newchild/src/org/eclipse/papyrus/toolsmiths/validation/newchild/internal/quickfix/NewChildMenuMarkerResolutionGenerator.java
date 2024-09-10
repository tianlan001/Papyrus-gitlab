/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.newchild.internal.quickfix;

import static org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionUtils.getModelPath;
import static org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleMissingExtensionMarkerResolution.optionalAttribute;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.ATTR_MODEL;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.ELEM_MENU_CREATION_MODEL;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.MAX_PROBLEM_ID;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.MISSING_NEW_CHILD_MENU_MODEL_EXTENSION_ID;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.NEWCHILD_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.PROBLEM_ID_BASE;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IPath;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionGenerator;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleMissingExtensionMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.newchild.internal.messages.Messages;
import org.eclipse.ui.IMarkerResolution;

/**
 * Resolution generator for markers created by the validation of <em>New Child Menu</em> models.
 */
public class NewChildMenuMarkerResolutionGenerator extends CommonMarkerResolutionGenerator {

	public NewChildMenuMarkerResolutionGenerator() {
		super();
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		if (hasCommonResolutions(marker)) {
			return super.getResolutions(marker);
		}

		int problemID = getProblemID(marker);
		switch (problemID) {
		case MISSING_NEW_CHILD_MENU_MODEL_EXTENSION_ID:
			return only(new SimpleMissingExtensionMarkerResolution(problemID,
					Messages.MissingNewChildMenuExtension_0, Messages.MissingNewChildMenuExtension_1,
					NEWCHILD_EXTENSION_POINT_IDENTIFIER, ELEM_MENU_CREATION_MODEL,
					optionalAttribute(ATTR_MODEL, m -> getModelPath(m).map(IPath::toPortableString))));
		default:
			return noResolutions();
		}
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		return super.hasResolutions(marker)
				|| matchProblemID(marker, PROBLEM_ID_BASE, MAX_PROBLEM_ID);
	}

}
