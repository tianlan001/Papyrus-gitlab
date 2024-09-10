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

import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;

/**
 * More usable implementation for SelectorList
 *
 * @author Camille Letavernier
 */
public class ExtendedSelectorList extends LinkedList<Selector> implements SelectorList {

	private static final long serialVersionUID = 0;

	public ExtendedSelectorList() {

	}

	public ExtendedSelectorList(SelectorList listToCopy) {
		for (int i = 0; i < listToCopy.getLength(); i++) {
			add(listToCopy.item(i));
		}
	}

	public ExtendedSelectorList(Collection<? extends Selector> listToCopy) {
		super(listToCopy);
	}

	@Override
	public int getLength() {
		return size();
	}

	@Override
	public Selector item(int index) {
		return get(index);
	}

}
