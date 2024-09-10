/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.checkers;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.papyrus.emf.helpers.BundleResourceURIHelper;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.messages.Messages;

/**
 * Custom validation rules for namespace references in element-types configuraitons.
 */
public class ElementTypesCustomValidator extends CustomModelChecker.SwitchValidator {

	public ElementTypesCustomValidator(String nsURI) {
		super(nsURI);
	}

	public void validate(ElementTypeSetConfiguration setConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		String nsURI = setConfiguration.getMetamodelNsURI();
		if (nsURI != null && !nsURI.isBlank()) {
			String bundleName = BundleResourceURIHelper.INSTANCE.getBundleNameFromNS_URI(nsURI);
			if (bundleName == null) {
				diagnostics.add(createDiagnostic(Diagnostic.ERROR, setConfiguration, format(Messages.ElementTypesCustomValidator_0, context, setConfiguration, nsURI)));
			}
		}
	}

}
