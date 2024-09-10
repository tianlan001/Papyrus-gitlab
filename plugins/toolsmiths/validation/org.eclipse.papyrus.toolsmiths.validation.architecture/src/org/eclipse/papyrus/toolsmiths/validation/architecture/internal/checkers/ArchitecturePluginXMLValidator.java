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

package org.eclipse.papyrus.toolsmiths.validation.architecture.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.ARCHITECTURE_EXTENSION_POINT_IDENTIFIER;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Validation of the <tt>plugin.xml</tt> for architecture model extensions.
 */
final class ArchitecturePluginXMLValidator {

	static final String CATEGORY = "architecture"; //$NON-NLS-1$
	static final String MODEL = "model"; //$NON-NLS-1$
	static final String PATH = "path"; //$NON-NLS-1$

	private final IFile modelFile;

	/**
	 * Initializes me with the model file that I validate.
	 *
	 * @param modelFile
	 *            the model file
	 */
	ArchitecturePluginXMLValidator(IFile modelFile) {
		super();

		this.modelFile = modelFile;
	}

	Optional<Element> matchExtension(Element element, String point, ArchitectureDomain architectureModel) {
		switch (point) {
		case ARCHITECTURE_EXTENSION_POINT_IDENTIFIER:
			NodeList children = element.getElementsByTagName(MODEL);
			for (int i = 0; i < children.getLength(); i++) {
				Element model = (Element) children.item(i);
				if (matchArchitectureDomain(model, architectureModel)) {
					return Optional.of(model);
				}
			}
			break;
		default:
			break;
		}

		return Optional.empty();
	}

	boolean matchArchitectureDomain(Element element, ArchitectureDomain model) {
		String path = element.getAttribute(PATH);
		return Objects.equals(path, modelFile.getProjectRelativePath().toString());
	}

	int problemID(String point, ArchitectureDomain architectureDomain) {
		switch (point) {
		case ARCHITECTURE_EXTENSION_POINT_IDENTIFIER:
			return ArchitecturePluginValidationConstants.MISSING_ARCHITECTURE_MODEL_EXTENSION_ID;
		default:
			return -1;
		}
	}

}
