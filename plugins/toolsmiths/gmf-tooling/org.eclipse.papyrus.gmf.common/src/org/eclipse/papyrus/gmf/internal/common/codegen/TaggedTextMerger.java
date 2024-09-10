/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors: 
 *     Dmitri Stadnik (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.common.codegen;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author dstadnik
 */
public class TaggedTextMerger {

	private String beginTag;

	private String endTag;

	public TaggedTextMerger(String beginTag, String endTag) {
		this.beginTag = beginTag;
		this.endTag = endTag;
	}

	public String getBeginTag() {
		return beginTag;
	}

	public String getEndTag() {
		return endTag;
	}

	public String process(String oldText, String newText) {
		int bpos = newText.indexOf(getBeginTag());
		if (bpos < 0) {
			// TODO : warn user of missed region anchor in new text
			return newText;
		}
		int epos = newText.indexOf(getEndTag());
		if (epos < 0 || epos <= bpos) {
			// TODO : warn user of unclosed region anchor
			return newText;
		}
		StringBuffer sb = new StringBuffer();
		try {
			RegionsIterator regions = new RegionsIterator(oldText);
			if (regions.hasNext()) {
				do {
					sb.append(regions.next());
				} while (regions.hasNext());
			} else {
				// add anchor text if there were no regions in old text
				// this way we'll preserve original template in new text
				sb.append(newText.substring(bpos + getBeginTag().length(), epos));
			}
		} catch (NoSuchElementException nsee) {
			// TODO : warn user of invalid regions in old text
			return newText;
		}
		return newText.substring(0, bpos + getBeginTag().length()) + sb.toString() + newText.substring(epos);
	}

	protected class RegionsIterator implements Iterator<String> {

		private String text;

		private int offset;

		public RegionsIterator(String text) {
			assert text != null;
			this.text = text;
		}

		public boolean hasNext() {
			return text.indexOf(getBeginTag(), offset) > 0;
		}

		public String next() {
			int bpos = text.indexOf(getBeginTag(), offset);
			if (bpos < 0) {
				throw new NoSuchElementException();
			}
			int epos = text.indexOf(getEndTag(), bpos + getBeginTag().length());
			if (epos < 0) {
				throw new NoSuchElementException();
			}
			offset = epos + getEndTag().length();
			return text.substring(bpos + getBeginTag().length(), epos);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
