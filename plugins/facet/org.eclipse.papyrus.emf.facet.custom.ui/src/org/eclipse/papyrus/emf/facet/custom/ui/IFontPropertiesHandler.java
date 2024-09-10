/**
 *  Copyright (c) 2013 Soft-Maint.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *      David Couvrand (Soft-Maint) - Bug 422058 - Implementation of strikethrough and underline in the CustomizedLabelProvider
 */
package org.eclipse.papyrus.emf.facet.custom.ui;

import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetOperation;

/**
 * @since 0.4
 */
public interface IFontPropertiesHandler {

	FacetOperation getBackgroundProperty();

	FacetOperation getForegroundProperty();

	FacetOperation getFontNameProperty();

	FacetOperation getFontSizeProperty();

	FacetOperation getIsBoldProperty();

	FacetOperation getIsItalicProperty();

	FacetOperation getIsVisible();

	FacetOperation getIsUnderlinedProperty();

	FacetOperation getIsStruckthroughProperty();
}
