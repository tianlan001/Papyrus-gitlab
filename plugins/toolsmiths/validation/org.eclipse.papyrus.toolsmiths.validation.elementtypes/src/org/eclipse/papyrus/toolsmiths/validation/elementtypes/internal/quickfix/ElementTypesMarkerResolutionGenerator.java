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

package org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.quickfix;

import static org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionUtils.getModelPath;
import static org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleMissingExtensionMarkerResolution.attribute;
import static org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleMissingExtensionMarkerResolution.optionalAttribute;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ATTR_CLIENT_CONTEXT_ID;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ATTR_PATH;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEM_ELEMENT_TYPE_SET;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.MAX_PROBLEM_ID;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.MISSING_ELEMENT_TYPES_CONFIGURATIONS_MODEL_CLIENT_CONTEXT_ID;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.MISSING_ELEMENT_TYPES_CONFIGURATIONS_MODEL_EXTENSION_ID;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.PAPYRUS_UML_CLIENT_CONTEXT;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.PROBLEM_ID_BASE;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.UNKNOWN_ELEMENT_TYPES_CONFIGURATIONS_MODEL_CLIENT_CONTEXT_ID;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IPath;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionGenerator;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleMissingAttributeMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleMissingExtensionMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.messages.Messages;
import org.eclipse.ui.IMarkerResolution;

/**
 * Resolution generator for markers created by the validation of <em>Element Types Configurations</em> models.
 */
public class ElementTypesMarkerResolutionGenerator extends CommonMarkerResolutionGenerator {

	public ElementTypesMarkerResolutionGenerator() {
		super();
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		if (hasCommonResolutions(marker)) {
			return super.getResolutions(marker);
		}

		int problemID = getProblemID(marker);
		switch (problemID) {
		case MISSING_ELEMENT_TYPES_CONFIGURATIONS_MODEL_EXTENSION_ID:
			return only(new SimpleMissingExtensionMarkerResolution(problemID,
					Messages.MissingElementTypesExtension_0, Messages.MissingElementTypesExtension_1,
					ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER, ELEM_ELEMENT_TYPE_SET,
					attribute(ATTR_CLIENT_CONTEXT_ID, PAPYRUS_UML_CLIENT_CONTEXT),
					optionalAttribute(ATTR_PATH, m -> getModelPath(m).map(IPath::toPortableString))));
		case MISSING_ELEMENT_TYPES_CONFIGURATIONS_MODEL_CLIENT_CONTEXT_ID:
		case UNKNOWN_ELEMENT_TYPES_CONFIGURATIONS_MODEL_CLIENT_CONTEXT_ID:
			return only(new SimpleMissingAttributeMarkerResolution(problemID,
					Messages.MissingClientContextID_0, Messages.MissingClientContextID_1,
					ATTR_CLIENT_CONTEXT_ID, PAPYRUS_UML_CLIENT_CONTEXT));
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
