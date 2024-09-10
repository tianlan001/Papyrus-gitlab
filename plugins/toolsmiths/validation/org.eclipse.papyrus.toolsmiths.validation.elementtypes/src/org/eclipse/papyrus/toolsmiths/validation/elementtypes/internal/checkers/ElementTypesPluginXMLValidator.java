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

import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ATTR_CLIENT_CONTEXT_ID;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ATTR_PATH;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEM_ELEMENT_TYPE_SET;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.PluginErrorReporter;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.ProjectManagementUtils;
import org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants;
import org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.messages.Messages;
import org.eclipse.pde.core.plugin.IPluginElement;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Validation of the <tt>plugin.xml</tt> for element-type set configuration extensions.
 */
final class ElementTypesPluginXMLValidator {

	static final String CATEGORY = "element_types"; //$NON-NLS-1$

	private static final String EXTPT_ELEMENT_TYPE_BINDINGS = "org.eclipse.gmf.runtime.emf.type.core.elementTypeBindings"; //$NON-NLS-1$
	private static final String ELEM_CLIENT_CONTEXT = "clientContext"; //$NON-NLS-1$
	private static final String ATT_ID = "id"; //$NON-NLS-1$

	private final IFile modelFile;

	/**
	 * Initializes me with the model file that I validate.
	 *
	 * @param modelFile
	 *            the model file
	 */
	ElementTypesPluginXMLValidator(IFile modelFile) {
		super();

		this.modelFile = modelFile;
	}

	Optional<Element> matchExtension(Element element, String point, ElementTypeSetConfiguration model) {
		switch (point) {
		case ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER:
			NodeList children = element.getElementsByTagName(ELEM_ELEMENT_TYPE_SET);
			for (int i = 0; i < children.getLength(); i++) {
				Element typeSet = (Element) children.item(i);
				if (matchElementTypeSet(typeSet, model)) {
					return Optional.of(typeSet);
				}
			}
			break;
		default:
			break;
		}

		return Optional.empty();
	}

	boolean matchElementTypeSet(Element element, ElementTypeSetConfiguration model) {
		String path = element.getAttribute(ATTR_PATH);
		return Objects.equals(path, modelFile.getProjectRelativePath().toString());
	}

	void checkExtension(Element element, String point, ElementTypeSetConfiguration model, PluginErrorReporter.ProblemReport problems) {
		switch (element.getTagName()) {
		case ELEM_ELEMENT_TYPE_SET:
			String clientContextID = element.getAttribute(ATTR_CLIENT_CONTEXT_ID);
			if (clientContextID == null || clientContextID.isBlank()) {
				problems.reportProblem(Diagnostic.ERROR, element, Messages.ElementTypesPluginXMLValidator_0,
						ElementTypesPluginValidationConstants.MISSING_ELEMENT_TYPES_CONFIGURATIONS_MODEL_CLIENT_CONTEXT_ID,
						CATEGORY, null);
			} else if (!findArchitectureContext(clientContextID) && !findGMFClientContext(clientContextID)) {
				problems.reportProblem(Diagnostic.WARNING, element, NLS.bind(Messages.ElementTypesPluginXMLValidator_1, clientContextID),
						ElementTypesPluginValidationConstants.UNKNOWN_ELEMENT_TYPES_CONFIGURATIONS_MODEL_CLIENT_CONTEXT_ID,
						CATEGORY, null);
			}
			break;
		default:
			break;
		}
	}

	private boolean findArchitectureContext(String id) {
		return ArchitectureDomainManager.getInstance().getArchitectureContextById(id) != null;
		}

	private boolean findGMFClientContext(String id) {
		return getClientContextRegistration(id).isPresent();
	}

	/**
	 * Find the registration of the given client context on the GMF extension point.
	 *
	 * @param clientContextID
	 *            a client context ID
	 * @return the registration of it on the GMF extension point
	 */
	public static Optional<IPluginElement> getClientContextRegistration(String clientContextID) {
		return ProjectManagementUtils.findExtensionElement(EXTPT_ELEMENT_TYPE_BINDINGS,
				ELEM_CLIENT_CONTEXT,
				ATT_ID, clientContextID);
	}

	int problemID(String point, ElementTypeSetConfiguration typesSet) {
		switch (point) {
		case ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER:
			return ElementTypesPluginValidationConstants.MISSING_ELEMENT_TYPES_CONFIGURATIONS_MODEL_EXTENSION_ID;
		default:
			return -1;
		}
	}

}
