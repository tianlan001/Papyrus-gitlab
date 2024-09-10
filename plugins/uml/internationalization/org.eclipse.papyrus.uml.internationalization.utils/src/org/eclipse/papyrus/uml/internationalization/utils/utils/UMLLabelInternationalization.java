/*****************************************************************************
 * Copyright (c) 2016, 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   Ibtihel KHEMIR (CEA-LIST) ibtihel.khemir@cea.fr - Bug 311044
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.utils.utils;

import java.util.Locale;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationUtils;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Stereotype;

/**
 * The internationalization label manager for UML elements.
 */
public class UMLLabelInternationalization {

	/**
	 * The singleton instance.
	 */
	private static UMLLabelInternationalization instance;

	/**
	 * Constructor.
	 */
	protected UMLLabelInternationalization() {

	}

	/**
	 * Get the singleton instance (create it if not existing).
	 *
	 * @return The singleton instance.
	 */
	public static UMLLabelInternationalization getInstance() {
		if (null == instance) {
			instance = new UMLLabelInternationalization();
		}
		return instance;
	}

	/**
	 * This allows to get the label of the named element.
	 *
	 * @param namedElement
	 *            The named element.
	 * @return The label of the named element or the name if the label is null.
	 */
	public String getLabel(final NamedElement namedElement) {
		return getLabel(namedElement, true);
	}

	/**
	 * This allows to get the label of the named element.
	 *
	 * @param namedElement
	 *            The named element.
	 * @param localize
	 *            Boolean to determinate if the locale must be used.
	 * @return The label of the named element or the name if the label is null.
	 */
	public String getLabel(final NamedElement namedElement, final boolean localize) {
		String result = null;

		if (null != namedElement.eResource()) {
			URI resourceURI = namedElement.eResource().getURI();
			final ResourceSet resourceSet = namedElement.eResource().getResourceSet();
			if (resourceSet instanceof ModelSet && ((ModelSet) resourceSet).getURIWithoutExtension() != null) {
				resourceURI = ((ModelSet) resourceSet).getURIWithoutExtension().appendFileExtension(DiModel.DI_FILE_EXTENSION);
			}
			if (InternationalizationPreferencesUtils.getInternationalizationPreference(resourceURI)) {
				result = getLabelWithoutUML(namedElement, localize);
			}
		}
		String name = null;

		if (namedElement.getName() == null) {
			name = ""; //$NON-NLS-1$
		} else {
			name = namedElement.getName();
		}

		return null != result ? result : name;

	}

	/**
	 * This allows to get the label of the named element without the getName
	 * when the label is null.
	 *
	 * @param namedElement
	 *            The named element.
	 * @return The label of the named element.
	 */
	public String getLabelWithoutUML(final NamedElement namedElement) {
		return getLabelWithoutUML(namedElement, true);
	}

	/**
	 * This allows to get the label of the named element without the getName
	 * when the label is null.
	 *
	 * @param namedElement
	 *            The named element.
	 * @param localize
	 *            Boolean to determinate if the locale must be used.
	 * @return The label of the named element.
	 */
	public String getLabelWithoutUML(final NamedElement namedElement, final boolean localize) {
		return LabelInternationalizationUtils.getLabelWithoutSubstract(namedElement, localize);
	}

	/**
	 * This allows to set the named element label.
	 *
	 * @param namedElement
	 *            The named element.
	 * @param value
	 *            The label value.
	 * @param locale
	 *            The locale for which set the value (if <code>null</code> set
	 *            it for the current locale).
	 */
	public void setLabel(final NamedElement namedElement, final String value, final Locale locale) {
		LabelInternationalizationUtils.setLabel(namedElement, value, locale);
	}

	/**
	 * This allows to get the set named element label command.
	 *
	 * @param domain
	 *            The editing domain to use.
	 * @param namedElement
	 *            The named element.
	 * @param value
	 *            The value to set.
	 * @param locale
	 *            The locale for which set the value (if <code>null</code> set
	 *            it for the current locale).
	 * @return The command which allow to set the named element label.
	 */
	public Command getSetLabelCommand(final EditingDomain domain, final NamedElement namedElement, final String value,
			final Locale locale) {
		return LabelInternationalizationUtils.getSetLabelCommand(domain, namedElement, value, locale);
	}

	/**
	 * This allows to get the keyword of the stereotype.
	 *
	 * @param stereotype
	 *            The stereotype.
	 * @return The keyword of the stereotype or the name if the keyword is null.
	 */
	public String getKeyword(final Stereotype stereotype) {
		return getKeyword(stereotype, true);
	}

	/**
	 * This allows to get the keyword of the stereotype.
	 *
	 * @param stereotype
	 *            The stereotype.
	 * @param localize
	 *            Boolean to determinate if the locale must be used.
	 * @return The keyword of the stereotype or the name if the keyword is null.
	 */
	public String getKeyword(final Stereotype stereotype, final boolean localize) {
		String result = null;

		if (null != stereotype.eResource()) {
			URI resourceURI = stereotype.eResource().getURI();
			final ResourceSet resourceSet = stereotype.eResource().getResourceSet();
			if (resourceSet instanceof ModelSet) {
				resourceURI = ((ModelSet) resourceSet).getURIWithoutExtension().appendFileExtension(DiModel.DI_FILE_EXTENSION);
			}
			if (InternationalizationPreferencesUtils.getInternationalizationPreference(resourceURI)) {
				result = LabelInternationalizationUtils.getLabelWithoutSubstract(stereotype, localize);
			}
		}
		return null != result ? result : stereotype.getName();
	}

	/**
	 * This allows to get the keyword of the stereotype without the getName when
	 * the keyword is null.
	 *
	 * @param stereotype
	 *            The stereotype.
	 * @return The keyword of the stereotype.
	 */
	public String getKeywordWithoutUML(final Stereotype stereotype) {
		return getKeywordWithoutUML(stereotype, true);
	}

	/**
	 * This allows to get the keyword of the stereotype without the getName when
	 * the keyword is null.
	 *
	 * @param stereotype
	 *            The stereotype.
	 * @param localize
	 *            Boolean to determinate if the locale must be used.
	 * @return The keyword of the stereotype.
	 */
	public String getKeywordWithoutUML(final Stereotype stereotype, final boolean localize) {
		return LabelInternationalizationUtils.getLabelWithoutSubstract(stereotype, localize);
	}

	/**
	 * This allows to set the stereotype keyword.
	 *
	 * @param stereotype
	 *            The stereotype.
	 * @param value
	 *            The label value.
	 * @param locale
	 *            The locale for which set the value (if <code>null</code> set
	 *            it for the current locale).
	 */
	public void setKeyword(final Stereotype stereotype, final String value, final Locale locale) {
		LabelInternationalizationUtils.setLabel(stereotype, value, locale);
	}

	/**
	 * This allows to get the set stereotype keyword command.
	 *
	 * @param domain
	 *            The editing domain to use.
	 * @param stereotype
	 *            The stereotype.
	 * @param value
	 *            The value to set.
	 * @param locale
	 *            The locale for which set the value (if <code>null</code> set
	 *            it for the current locale).
	 * @return The command which allow to set the stereotype keyword.
	 */
	public Command getSetKeywordCommand(final EditingDomain domain, final Stereotype stereotype, final String value,
			final Locale locale) {
		return LabelInternationalizationUtils.getSetLabelCommand(domain, stereotype, value, locale);
	}

	/**
	 * This allows to get the qualified label (this means the qualified name
	 * with labels).
	 *
	 * @param namedElement
	 *            The named element to get its qualified label.
	 * @return The qualified label or <code>null</code>.
	 */
	public String getQualifiedLabel(final NamedElement namedElement) {
		StringBuilder result = null;

		final String name = getLabel(namedElement);

		if (!UML2Util.isEmpty(name)) {
			result = new StringBuilder(name);

			for (final Namespace namespace : namedElement.allNamespaces()) {
				final String namespaceLabel = getLabel(namespace);

				if (UML2Util.isEmpty(namespaceLabel)) {
					result = null;
					break;
				} else {
					result.insert(0, namespace.separator());
					result.insert(0, namespaceLabel);
				}
			}
		}

		return null != result ? result.toString() : null;
	}
}
