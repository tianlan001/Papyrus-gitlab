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

package org.eclipse.papyrus.toolsmiths.validation.architecture.internal.quickfix;

import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.ARCHITECTURE_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.ATTR_PATH;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.ELEM_MODEL;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.MAX_PROBLEM_ID;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.MISSING_ARCHITECTURE_MODEL_EXTENSION_ID;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.MISSING_REPRESENTATIONS_ADVICE_ID;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.PROBLEM_ID_BASE;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.REPRESENTATIONS_ADVICE_URI;
import static org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionUtils.getModelPath;
import static org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleMissingExtensionMarkerResolution.optionalAttribute;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.toolsmiths.validation.architecture.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.CommonMarkerResolutionGenerator;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleMissingExtensionMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.SimpleModelEditMarkerResolution;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.uml2.common.util.UML2Util;

/**
 * Resolution generator for markers created by the validation of <em>Architecture Description</em> models.
 */
public class ArchitectureMarkerResolutionGenerator extends CommonMarkerResolutionGenerator {

	public ArchitectureMarkerResolutionGenerator() {
		super();
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		if (hasCommonResolutions(marker)) {
			return super.getResolutions(marker);
		}

		int problemID = getProblemID(marker);
		switch (problemID) {
		case MISSING_ARCHITECTURE_MODEL_EXTENSION_ID:
			return only(new SimpleMissingExtensionMarkerResolution(problemID,
					Messages.MissingArchitectureExtension_0, Messages.MissingArchitectureExtension_1,
					ARCHITECTURE_EXTENSION_POINT_IDENTIFIER, ELEM_MODEL,
					optionalAttribute(ATTR_PATH, m -> getModelPath(m).map(IPath::toPortableString))));
		case MISSING_REPRESENTATIONS_ADVICE_ID:
			return only(SimpleModelEditMarkerResolution.create(problemID, Messages.MissingRepresentationsAdvice_0, Messages.MissingRepresentationsAdvice_1,
					ArchitectureContext.class, this::getAddRepresentationsAdviceCommand));
		default:
			return noResolutions();
		}
	}

	@Override
	public boolean hasResolutions(IMarker marker) {
		return super.hasResolutions(marker)
				|| matchProblemID(marker, PROBLEM_ID_BASE, MAX_PROBLEM_ID);
	}

	private Command getAddRepresentationsAdviceCommand(EditingDomain domain, ArchitectureContext context) {
		ElementTypeSetConfiguration set = UML2Util.load(domain.getResourceSet(), REPRESENTATIONS_ADVICE_URI, ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION);
		return AddCommand.create(domain, context, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__ELEMENT_TYPES, set);
	}

}
