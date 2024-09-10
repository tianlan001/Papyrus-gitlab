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

package org.eclipse.papyrus.infra.tools.util;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.AbstractIterator;

/**
 * An iterator over all of the {@link Element}s in an XML document.
 */
public class XMLTreeIterator extends AbstractTreeIterator<Element> {

	private static final long serialVersionUID = 1L;

	public XMLTreeIterator(Document document) {
		super(document, false);
	}

	public XMLTreeIterator(Element root) {
		super(root, true);
	}

	@Override
	protected Iterator<? extends Element> getChildren(Object object) {
		if (object instanceof Document) {
			return new Noderator(((Document) object).getChildNodes());
		}
		if (object instanceof Element) {
			return new Noderator(((Element) object).getChildNodes());
		}

		return Collections.emptyIterator();
	}

	//
	// Nested types
	//

	private static final class Noderator extends AbstractIterator<Element> {
		private final NodeList nodes;
		private int index;

		Noderator(NodeList nodes) {
			super();

			this.nodes = nodes;
		}

		@Override
		protected Element computeNext() {
			while (index < nodes.getLength()) {
				Node node = nodes.item(index);
				index = index + 1;

				if (node instanceof Element) {
					return (Element) node;
				}
			}

			return endOfData();
		}

	}

}
