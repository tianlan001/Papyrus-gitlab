/*******************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.sirius.properties.uml.Activator;
import org.eclipse.papyrus.uml.tools.profile.definition.Version;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * This service class includes all services used to display and manage definitions of a profile.
 * Inspired by org.eclipse.papyrus.uml.profile.utils.Util.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesProfileDefinitionServices {

	/**
	 * Empty String constant.
	 */
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * Key for the Author annotation detail.
	 */
	public static final String PAPYRUS_AUTHOR_KEY = "Author"; //$NON-NLS-1$

	/**
	 * Key for the Comment annotation detail.
	 */
	public static final String PAPYRUS_COMMENT_KEY = "Comment"; //$NON-NLS-1$

	/**
	 * Key for the Copyright annotation detail.
	 */
	public static final String PAPYRUS_COPYRIGHT_KEY = "Copyright"; //$NON-NLS-1$

	/**
	 * Key for the Date annotation detail.
	 */
	public static final String PAPYRUS_DATE_KEY = "Date"; //$NON-NLS-1$

	/**
	 * Key for the Version annotation detail.
	 */
	public static final String PAPYRUS_VERSION_KEY = "Version"; //$NON-NLS-1$

	/**
	 * Annotation source that qualifies the profile definition.
	 */
	public static final String PAPYRUS_VERSION = "PapyrusVersion"; //$NON-NLS-1$

	/**
	 * Get the list of existing definitions for a profile.
	 * 
	 * @param profile
	 *            the {@link Profile} which contains the definitions in an annotation.
	 * @return the list of profile definitions of the given profile
	 */
	public List<EPackage> getDefinitions(Profile profile) {
		List<EPackage> ePackages = new ArrayList<>();
		if (profile != null) {
			EAnnotation annotation = profile.getEAnnotation(UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI);
			if (annotation != null) {
				List<EPackage> filteredEPackages = annotation.getContents().stream()
						.filter(EPackage.class::isInstance)
						.map(EPackage.class::cast)
						.collect(Collectors.toList());
				ePackages.addAll(filteredEPackages);
			}
		}
		return ePackages;
	}

	/**
	 * Get the version of a profile definition if it exists; an empty version "0.0.0" otherwise.
	 * 
	 * @param definition
	 *            the {@link EPackage} annotated with the version
	 * @return the version of the specified profile definition
	 */
	public String getProfileDefinitionVersion(EPackage definition) {
		Version version = Version.emptyVersion;
		EAnnotation annotation = getPapyrusVersionAnnotation(definition);
		if (annotation != null) {
			String value = annotation.getDetails().get(PAPYRUS_VERSION_KEY);
			try {
				version = new Version(Optional.ofNullable(value).orElse(EMPTY_STRING));
				// CHECKSTYLE:OFF
			} catch (Exception e) {
				// CHECKSTYLE:ON
				Activator.getDefault().getLog()
						.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Impossible to parse the version value: " + value, e)); //$NON-NLS-1$
			}
		}
		return version.toString();
	}

	/**
	 * Get the date of a profile definition if it exists; an empty string otherwise.
	 * 
	 * @param definition
	 *            the {@link EPackage} annotated with the date
	 * @return the date of the specified profile definition
	 */
	public String getProfileDefinitionDate(EPackage definition) {
		String date = EMPTY_STRING;
		EAnnotation annotation = getPapyrusVersionAnnotation(definition);
		if (annotation != null) {
			final String value = annotation.getDetails().get(PAPYRUS_DATE_KEY);
			date = Optional.ofNullable(value).orElse(EMPTY_STRING);
		}
		return date;
	}

	/**
	 * Get the author of a profile definition if it exists; an empty string otherwise.
	 * 
	 * @param definition
	 *            the {@link EPackage} annotated with the author
	 * @return the author of the specified profile definition
	 */
	public String getProfileDefinitionAuthor(EPackage definition) {
		String author = EMPTY_STRING;
		EAnnotation annotation = getPapyrusVersionAnnotation(definition);
		if (annotation != null) {
			final String value = annotation.getDetails().get(PAPYRUS_AUTHOR_KEY);
			author = Optional.ofNullable(value).orElse(EMPTY_STRING);
		}
		return author;
	}

	/**
	 * Get the copyright of a profile definition if it exists; an empty string otherwise.
	 * 
	 * @param definition
	 *            the {@link EPackage} annotated with the copyright
	 * @return the copyright of the specified profile definition
	 */
	public String getProfileDefinitionCopyright(EPackage definition) {
		String copyright = EMPTY_STRING;
		EAnnotation annotation = getPapyrusVersionAnnotation(definition);
		if (annotation != null) {
			final String value = annotation.getDetails().get(PAPYRUS_COPYRIGHT_KEY);
			copyright = Optional.ofNullable(value).orElse(EMPTY_STRING);
		}
		return copyright;
	}

	/**
	 * Get the comment of a profile definition if it exists; an empty string otherwise.
	 * 
	 * @param definition
	 *            the {@link EPackage} annotated with the comment
	 * @return the comment of the specified profile definition
	 */
	public String getProfileDefinitionComment(EPackage definition) {
		String comment = EMPTY_STRING;
		EAnnotation annotation = getPapyrusVersionAnnotation(definition);
		if (annotation != null) {
			final String value = annotation.getDetails().get(PAPYRUS_COMMENT_KEY);
			comment = Optional.ofNullable(value).orElse(EMPTY_STRING);
		}
		return comment;
	}

	/**
	 * Remove a profile definition from the annotation of the specified {@link Profile}.
	 * 
	 * @param definition
	 *            the {@link EPackage} profile definition to remove
	 * @param profile
	 *            the {@link Profile} which contains the annotation referencing the profile definition
	 * @return <code>true</code> if the profile definition has been removed; <code>false</code> otherwise
	 */
	public boolean removeProfileDefinition(EPackage definition, Profile profile) {
		boolean result = false;
		if (profile != null && definition != null) {
			EAnnotation annotation = profile.getEAnnotation(UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI);
			if (annotation != null) {
				EList<EObject> contents = annotation.getContents();
				result = contents.remove(definition);
			}
		}
		return result;
	}

	private EAnnotation getPapyrusVersionAnnotation(EPackage definition) {
		EAnnotation annotation = null;
		if (definition != null) {
			annotation = definition.getEAnnotation(PAPYRUS_VERSION);
		}
		return annotation;
	}
}
