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

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.A_CONTEXT_MODEL;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.CONTEXTS_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.E_CONTEXT;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Validation of the <tt>plugin.xml</tt> for properties context model extensions.
 */
final class PropertiesContextPluginXMLValidator {

	static final String CATEGORY = "properties-context"; //$NON-NLS-1$

	private final IFile modelFile;

	/**
	 * Initializes me with the model file that I validate.
	 *
	 * @param modelFile
	 *            the model file
	 */
	PropertiesContextPluginXMLValidator(IFile modelFile) {
		super();

		this.modelFile = modelFile;
	}

	Optional<Element> matchExtension(Element element, String point, Context contextModel) {
		switch (point) {
		case CONTEXTS_EXTENSION_POINT_IDENTIFIER:
			NodeList children = element.getElementsByTagName(E_CONTEXT);
			for (int i = 0; i < children.getLength(); i++) {
				Element model = (Element) children.item(i);
				if (matchContext(model, contextModel)) {
					return Optional.of(model);
				}
			}
			break;
		default:
			break;
		}

		return Optional.empty();
	}

	boolean matchContext(Element element, Context model) {
		String path = element.getAttribute(A_CONTEXT_MODEL);
		return Objects.equals(path, modelFile.getProjectRelativePath().toString());
	}

	int problemID(String point, Context model) {
		switch (point) {
		case CONTEXTS_EXTENSION_POINT_IDENTIFIER:
			return PropertiesPluginValidationConstants.MISSING_CONTEXT_MODEL_EXTENSION_ID;
		default:
			return -1;
		}
	}

}
