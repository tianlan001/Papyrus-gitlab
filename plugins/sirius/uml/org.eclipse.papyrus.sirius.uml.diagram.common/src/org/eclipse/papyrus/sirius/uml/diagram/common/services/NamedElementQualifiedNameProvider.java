/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.services;

import java.util.Optional;

import org.eclipse.papyrus.uml.domain.services.labels.INamedElementNameProvider;
import org.eclipse.papyrus.uml.domain.services.labels.UMLCharacters;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Utility class to display qualified name of
 * <code>org.eclipse.uml2.uml.NamedElement</code>.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class NamedElementQualifiedNameProvider implements INamedElementNameProvider {

	/**
	 * This allows to get the qualified name of the named element.
	 *
	 * @param namedElement
	 *            The named element.
	 * @return The qualified name of the named element or empty string if null.
	 */
	@Override
	public String getName(final NamedElement namedElement) {
		if (namedElement == null) {
			return null;
		}
		UMLLabelInternationalization umlLabelInternationalization = UMLLabelInternationalization.getInstance();
		String qualifiedName = Optional.ofNullable(umlLabelInternationalization.getQualifiedLabel(namedElement)).orElseGet(() -> namedElement.getName());
		if (qualifiedName == null) {
			return UMLCharacters.EMPTY;
		} else {
			return qualifiedName;
		}
	}
}
