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

package org.eclipse.papyrus.toolsmiths.validation.newchild.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.ATTR_MODEL;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.ELEM_MENU_CREATION_MODEL;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.NEWCHILD_EXTENSION_POINT_IDENTIFIER;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu;
import org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Validation of the <tt>plugin.xml</tt> for new child menu model extensions.
 */
final class NewChildMenuPluginXMLValidator {

	static final String CATEGORY = "creation_menus"; //$NON-NLS-1$

	private final IFile modelFile;

	/**
	 * Initializes me with the model file that I validate.
	 *
	 * @param modelFile
	 *            the model file
	 */
	NewChildMenuPluginXMLValidator(IFile modelFile) {
		super();

		this.modelFile = modelFile;
	}

	Optional<Element> matchExtension(Element element, String point, Menu model) {
		switch (point) {
		case NEWCHILD_EXTENSION_POINT_IDENTIFIER:
			NodeList children = element.getElementsByTagName(ELEM_MENU_CREATION_MODEL);
			for (int i = 0; i < children.getLength(); i++) {
				Element menuCreationModel = (Element) children.item(i);
				if (matchMenuCreationModel(menuCreationModel, model)) {
					return Optional.of(menuCreationModel);
				}
			}
			break;
		default:
			break;
		}

		return Optional.empty();
	}

	boolean matchMenuCreationModel(Element element, Menu model) {
		String path = element.getAttribute(ATTR_MODEL);
		return Objects.equals(path, modelFile.getProjectRelativePath().toString());
	}

	int problemID(String point, Menu menu) {
		switch (point) {
		case NEWCHILD_EXTENSION_POINT_IDENTIFIER:
			return NewChildMenuPluginValidationConstants.MISSING_NEW_CHILD_MENU_MODEL_EXTENSION_ID;
		default:
			return -1;
		}
	}

}
