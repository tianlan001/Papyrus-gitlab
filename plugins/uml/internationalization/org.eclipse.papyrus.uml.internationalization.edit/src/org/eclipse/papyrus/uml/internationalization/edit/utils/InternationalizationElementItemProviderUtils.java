/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 528343
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.edit.utils;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.edit.providers.ElementItemProvider;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;

/**
 * The appendLabel and appendKeywords inspired from {@link ElementItemProvider}
 * and avoid code duplication in the inherited from
 * {@link UMLItemProviderAdapterFactory}.
 */
public class InternationalizationElementItemProviderUtils {

	/**
	 * This allows to append the label if the internationalization preference is set to <code>true</code>.
	 * 
	 * @param text The initial String buffer.
	 * @param object The object.
	 * @param shouldTranslate The boolean shouldTranslate.
	 * @return The modified {@link StringBuffer}.
	 */
	public static StringBuffer appendLabel(final StringBuffer text, final Object object, final boolean shouldTranslate) {
		 StringBuffer result = text;
		if (object instanceof NamedElement) {
			if (InternationalizationPreferencesUtils.getInternationalizationPreference((NamedElement) object)) {
				result = appendString(text, UMLLabelInternationalization.getInstance().getLabel((NamedElement) object, shouldTranslate));
			} else {
				result = appendString(text, ((NamedElement) object).getName());
			}
		}
		return result;
	}

	/**
	 * This allows to append a string to the current {@link StringBuffer}.
	 * 
	 * @param text The initial {@link StringBuffer}.
	 * @param string The string to append.
	 * @return The modified {@link StringBuffer}.
	 */
	private static StringBuffer appendString(final StringBuffer text, final String string) {

		if (!UML2Util.isEmpty(string)) {

			if (text.length() > 0) {
				text.append(' '); // $NON-NLS-1$
			}

			text.append(string);
		}

		return text;
	}

	/**
	 * This allows to append the keywords if the internationalization preference is set to <code>true</code>.
	 * 
	 * @param text The initial String buffer.
	 * @param object The object.
	 * @param souldTranslate The boolean shouldTranslate.
	 * @return The modified {@link StringBuffer}.
	 */
	public static StringBuffer appendKeywords(final StringBuffer text, final Object object,
			final boolean shouldTranslate) {
		if (object instanceof Element) {
			final Element element = (Element) object;
			final boolean i18nEnabled = InternationalizationPreferencesUtils.getInternationalizationPreference(element);

			final Iterator<Stereotype> appliedStereotypes = element.getAppliedStereotypes().iterator();
			Iterator<String> keywords = new ArrayList<String>().iterator();
			if (i18nEnabled) {
				keywords = element.getKeywords().iterator();
			}

			if (appliedStereotypes.hasNext() || keywords.hasNext()) {

				if (text.length() > 0) {
					text.append(' '); // $NON-NLS-1$
				}

				text.append("<<"); //$NON-NLS-1$

				while (appliedStereotypes.hasNext()) {
					if (i18nEnabled) {
						text.append(UMLLabelInternationalization.getInstance().getKeyword(appliedStereotypes.next(), shouldTranslate));
					} else {
						text.append(appliedStereotypes.next().getName());
					}

					if (appliedStereotypes.hasNext() || keywords.hasNext()) {
						text.append(", "); //$NON-NLS-1$
					}
				}

				while (keywords.hasNext()) {
					text.append(keywords.next());

					if (keywords.hasNext()) {
						text.append(", "); //$NON-NLS-1$
					}
				}

				text.append(">>"); //$NON-NLS-1$
			}
		}

		return text;
	}

}
