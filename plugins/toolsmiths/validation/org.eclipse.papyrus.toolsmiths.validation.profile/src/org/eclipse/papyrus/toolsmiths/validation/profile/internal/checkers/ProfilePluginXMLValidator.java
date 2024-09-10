/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, EclipseSource, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Remi Schnekenburger - Initial API and implementation
 *   Christian W. Damus - bugs 570097, 571125, 573886
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.profile.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.common.checkers.CommonProblemConstants.MODEL_NAME;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.PluginErrorReporter;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.eclipse.papyrus.toolsmiths.validation.profile.internal.messages.Messages;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.utils.StaticProfileUtil;
import org.eclipse.uml2.uml.Profile;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Validation of the <tt>plugin.xml</tt> for static profiles extensions.
 */
final class ProfilePluginXMLValidator {

	public static final String CATEGORY = "profile"; //$NON-NLS-1$
	private static final String PACKAGE = "package"; //$NON-NLS-1$
	private static final String GEN_MODEL_ATTRIBUTE = "genModel"; //$NON-NLS-1$
	private static final String GENMODEL_EXTENSION = "genmodel"; //$NON-NLS-1$
	private static final String URI_ATTRIBUTE = "uri"; //$NON-NLS-1$
	private static final String PROFILE = "profile"; //$NON-NLS-1$
	private static final String LOCATION = "location"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String PATH = "path"; //$NON-NLS-1$
	private static final String PATH_SEPARATOR = "/"; //$NON-NLS-1$

	/** URIs */
	private static final String PATHMAP = "pathmap://"; //$NON-NLS-1$
	private static final String PLATFORM_PLUGIN = "platform:/plugin/"; //$NON-NLS-1$

	private final IFile profileFile;

	/**
	 * Initializes me with the model file that I validate.
	 *
	 * @param modelFile
	 *            the model file
	 */
	ProfilePluginXMLValidator(IFile profileFile) {
		super();
		this.profileFile = profileFile;
	}

	Optional<Element> matchExtension(Element element, String point, Profile profile) {
		switch (point) {
		case ProfilePluginValidationConstants.ECORE_GENERATED_PACKAGE_EXTENSION_POINT:
			NodeList children = element.getElementsByTagName(PACKAGE);
			for (int i = 0; i < children.getLength(); i++) {
				Element package_ = (Element) children.item(i);
				if (matchEcorePackage(package_, profile)) {
					return Optional.of(package_);
				}
			}
			break;
		case ProfilePluginValidationConstants.UML_GENERATED_PACKAGE_EXTENSION_POINT:
			NodeList profiles = element.getElementsByTagName(PROFILE);
			for (int i = 0; i < profiles.getLength(); i++) {
				Element profileElement = (Element) profiles.item(i);
				if (matchUML2Package(profileElement, profile)) {
					return Optional.of(profileElement);
				}
			}
			break;
		case ProfilePluginValidationConstants.UMLPROFILE_EXTENSION_POINT:
			if (profile.getNestingPackage() != null) {
				break;
			}
			profiles = element.getElementsByTagName(PROFILE);
			for (int i = 0; i < profiles.getLength(); i++) {
				Element profileElement = (Element) profiles.item(i);
				if (matchPapyrusProfile(profileElement, profile)) {
					return Optional.of(profileElement);
				}
			}
			break;
		default:
			break;
		}

		return Optional.empty();
	}

	private boolean matchPapyrusProfile(Element profileElement, Profile profile) {
		String profilePath = profileFile.getProjectRelativePath().removeFileExtension().addFileExtension(UmlModel.UML_FILE_EXTENSION).toString();
		URIConverter converter = profile.eResource().getResourceSet().getURIConverter();
		URI profileFileURI = converter.normalize(URI.createPlatformPluginURI(profileFile.getProject().getName() + PATH_SEPARATOR + profilePath, true));

		String path = profileElement.getAttribute(PATH);
		if (path == null) {
			return false;
		}
		URI profileElementURI = null;
		// check pathmap, relative URI or platform based uri
		if (!(path.startsWith(PATHMAP) || path.startsWith(PLATFORM_PLUGIN))) {
			profileElementURI = URI.createPlatformPluginURI(profileFile.getProject().getName() + PATH_SEPARATOR + path, false);
		} else {
			profileElementURI = URI.createURI(path);
		}

		URI normalizedProfileElementURI = converter.normalize(profileElementURI);

		// check path
		return normalizedProfileElementURI.equals(profileFileURI);
	}

	private boolean matchUML2Package(Element profileElement, Profile profile) {
		String extensionNsURI = profileElement.getAttribute(URI_ATTRIBUTE);
		String stereotypeNsURI = profileURI(profile);
		if (stereotypeNsURI == null || stereotypeNsURI.isBlank()) {
			// can't check this profile
			return false;
		}
		return Objects.equals(stereotypeNsURI, extensionNsURI);
	}

	boolean matchEcorePackage(Element element, Profile profile) {
		String extensionGenModel = element.getAttribute(GEN_MODEL_ATTRIBUTE);

		// retrieve profile file path and compare with given path in extension point
		IPath projectRelativePath = profileFile.getProjectRelativePath();
		IPath genModelFilePath = projectRelativePath.removeFileExtension().addFileExtension(GENMODEL_EXTENSION);
		String genModelFile = genModelFilePath.toString();
		// compare with profile genmodel
		if (Objects.equals(genModelFile, extensionGenModel)) {
			String extensionNsURI = element.getAttribute(URI_ATTRIBUTE);
			String stereotypeNsURI = profileURI(profile);
			if (Objects.equals(stereotypeNsURI, extensionNsURI)) {
				return true;
			}
		}
		return false;
	}

	void checkExtension(Element element, String point, Profile profile, PluginErrorReporter.ProblemReport problems) {
		switch (point) {
		case ProfilePluginValidationConstants.ECORE_GENERATED_PACKAGE_EXTENSION_POINT:
			String extensionNsURI = element.getAttribute(URI_ATTRIBUTE);
			if (extensionNsURI == null || extensionNsURI.isBlank()) {
				problems.reportProblem(Diagnostic.ERROR, element, URI_ATTRIBUTE, NLS.bind(Messages.StaticProfilePluginErrorReporter_missingExtensionPointAttribute, URI_ATTRIBUTE),
						ProfilePluginValidationConstants.NO_URI_MARKER_ID, CATEGORY,
						Collections.singletonMap(MODEL_NAME, profile.getLabel()));
			}
			break;
		case ProfilePluginValidationConstants.UML_GENERATED_PACKAGE_EXTENSION_POINT:
			String extensionlocation = element.getAttribute(LOCATION);
			if (extensionlocation == null || extensionlocation.isBlank()) {
				problems.reportProblem(Diagnostic.ERROR, element, LOCATION, NLS.bind(Messages.StaticProfilePluginErrorReporter_missingExtensionPointAttribute, LOCATION),
						ProfilePluginValidationConstants.NO_UML2_GEN_PACKAGE_LOCATION_MARKER_ID,
						CATEGORY, Collections.singletonMap(MODEL_NAME, profile.getLabel()));
				break; // No point in checking further what the location ends with
			}
			final String profileId = ((XMIResource) profile.eResource()).getID(profile);
			String uml2ProfileFile = profileFile.getProjectRelativePath().removeFileExtension().addFileExtension(UmlModel.UML_FILE_EXTENSION).lastSegment();

			if (!extensionlocation.endsWith("/" + uml2ProfileFile + "#" + profileId)) { //$NON-NLS-1$ //$NON-NLS-2$
				problems.reportProblem(Diagnostic.ERROR, element, LOCATION, NLS.bind(Messages.StaticProfilePluginErrorReporter_wrongLocationForProfile, profile.getLabel()),
						ProfilePluginValidationConstants.NO_UML2_GEN_PACKAGE_LOCATION_MARKER_ID,
						CATEGORY, Collections.singletonMap(MODEL_NAME, profile.getLabel()));
			}
			break;
		case ProfilePluginValidationConstants.UMLPROFILE_EXTENSION_POINT:
			String name = element.getAttribute(NAME);
			if (name == null || name.isEmpty()) {
				problems.reportProblem(Diagnostic.WARNING, element, NAME, NLS.bind(Messages.StaticProfilePluginErrorReporter_uiLabelIsNull, profile.getLabel()),
						ProfilePluginValidationConstants.PAPYRUS_PROFILE_EXTENSION_NO_NAME_MARKER_ID,
						CATEGORY, Collections.singletonMap(MODEL_NAME, profile.getLabel()));
			}
			break;
		default:
			break;
		}
	}

	private static String profileURI(Profile profile) {
		return new StaticProfileUtil(profile).getDefinition().getNsURI();
	}

	int problemId(String point, Profile profile) {
		switch (point) {
		case ProfilePluginValidationConstants.ECORE_GENERATED_PACKAGE_EXTENSION_POINT:
			return ProfilePluginValidationConstants.NO_ECORE_GEN_PACKAGE_MARKER_ID;
		case ProfilePluginValidationConstants.UML_GENERATED_PACKAGE_EXTENSION_POINT:
			return ProfilePluginValidationConstants.NO_UML2_GEN_PACKAGE_MARKER_ID;
		case ProfilePluginValidationConstants.UMLPROFILE_EXTENSION_POINT:
			return ProfilePluginValidationConstants.NO_PAPYRUS_PROFILE_MARKER_ID;
		default:
			break;

		}
		return 0;
	}

}
