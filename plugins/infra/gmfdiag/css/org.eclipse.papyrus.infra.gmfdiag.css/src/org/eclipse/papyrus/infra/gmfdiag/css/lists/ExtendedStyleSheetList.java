/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.lists;

import java.util.Collection;
import java.util.LinkedList;

import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;

/**
 * More usable implementation for StyleSheetList
 *
 * @author Camille Letavernier
 */
public class ExtendedStyleSheetList extends LinkedList<StyleSheet> implements StyleSheetList {

	private static final long serialVersionUID = 1L;

	public ExtendedStyleSheetList() {

	}

	public ExtendedStyleSheetList(StyleSheetList listToCopy) {
		addAll(listToCopy);
	}

	public ExtendedStyleSheetList(Collection<? extends StyleSheet> listToCopy) {
		super(listToCopy);
	}

	@Override
	public int getLength() {
		return size();
	}

	@Override
	public StyleSheet item(int index) {
		return get(index);
	}

	public void addAll(StyleSheetList styleSheets) {
		for (int i = 0; i < styleSheets.getLength(); i++) {
			add(styleSheets.item(i));
		}
	}

}
