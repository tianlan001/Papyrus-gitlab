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

package org.eclipse.papyrus.infra.doc.internal.filters;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * An output stream that injects content before the close of the {@code <HEAD>}
 * element and at the beginning of the {@code <BODY>} element of the page.
 */
public class HTMLHeadAndBodyInjectionStream extends FilterOutputStream {

	private final OutputStream output;
	private final ByteBuffer currentTag;
	private final String encoding;
	private final String headContent;
	private final String bodyContent;

	private boolean scanningTag;
	private boolean done;

	/**
	 * Initializes me with head and/or body content to inject. Having neither is pointless.
	 * It is best that the strings provided comprise just 7-bit ASCII characters for maximal
	 * encoding compatibility.
	 *
	 * @param out
	 *            the output stream to inject into
	 * @param encoding
	 *            the encoding (character set) of the content of the output stream
	 * @param headContent
	 *            content to inject just before the closing {@link </HEAD>} tag, or {@code null} if none
	 * @param bodyContent
	 *            content to inject just after the opening {@link <BODY>} tag, or {@code null} if none
	 */
	public HTMLHeadAndBodyInjectionStream(OutputStream out, String encoding, String headContent, String bodyContent) {
		super(out);

		this.output = out;
		this.currentTag = ByteBuffer.allocate(1024);
		this.encoding = encoding == null ? "ASCII" : encoding;
		this.headContent = headContent;
		this.bodyContent = bodyContent;
	}

	@Override
	public void write(int b) throws IOException {
		if (done) {
			super.write(b);
			return;
		}

		switch (b) {
		case '<':
			// Opening a tag
			currentTag.clear();
			scanningTag = true;
			break;
		case '?':
		case '!':
			// Processing instruction or comment. Bail
			if (scanningTag) {
				emitCurrentTag();
				scanningTag = false;
			}
			break;
		case '>':
			if (scanningTag && appendCurrentTag(b)) {
				if (isHeadClose()) {
					injectHead();
					// Stop now if there will be no body injection
					done = bodyContent == null;
				} else if (isBodyOpen()) {
					injectBody();
					// There is nothing to do after body injection
					done = true;
				} else {
					// It's not a tag of interest. Emit it now
					emitCurrentTag();
				}
				scanningTag = false;
				return; // We already emitted the incoming '>'
			}
			break;
		}

		if (scanningTag) {
			appendCurrentTag(b);
		} else {
			super.write(b);
		}
	}

	private boolean appendCurrentTag(int b) throws IOException {
		if (currentTag.hasRemaining()) {
			currentTag.put((byte) b);
			return true;
		} else {
			// Bail on this tag: if it's this long, we don't want it
			emitCurrentTag();
			scanningTag = false;
			super.write(b);
			return false;
		}
	}

	private void emitCurrentTag() throws IOException {
		currentTag.flip();
		out.write(currentTag.array(), currentTag.position(), currentTag.remaining());
	}

	/**
	 * Is the current tag the closing tag of the head?
	 */
	final boolean isHeadClose() {
		byte b;
		return currentTag.position() >= 7
				&& (b = currentTag.get(0)) == '<'
				&& (b = currentTag.get(1)) == '/'
				&& ((b = currentTag.get(2)) == 'h' || b == 'H')
				&& ((b = currentTag.get(3)) == 'e' || b == 'E')
				&& ((b = currentTag.get(4)) == 'a' || b == 'A')
				&& ((b = currentTag.get(5)) == 'd' || b == 'D')
				&& ((b = currentTag.get(6)) == '>' || b == ' ' || b == '\t' || b == '\r' || b == '\n');
	}

	/**
	 * Is the current tag the opening tag of the body?
	 */
	final boolean isBodyOpen() {
		byte b;
		return currentTag.position() >= 6
				&& (b = currentTag.get(0)) == '<'
				&& ((b = currentTag.get(1)) == 'b' || b == 'B')
				&& ((b = currentTag.get(2)) == 'o' || b == 'O')
				&& ((b = currentTag.get(3)) == 'd' || b == 'D')
				&& ((b = currentTag.get(4)) == 'y' || b == 'Y')
				&& ((b = currentTag.get(5)) == '>' || b == ' ' || b == '\t' || b == '\r' || b == '\n');
	}

	private void injectHead() throws IOException {
		if (headContent != null) {
			emit(headContent);
			output.write('\n');
		}
		emitCurrentTag(); // The head close
	}

	private void injectBody() throws IOException {
		emitCurrentTag(); // The body open
		if (bodyContent != null) {
			emit(bodyContent);
			output.write('\n');
		}
	}

	private void emit(String content) throws IOException {
		byte[] toEmit = content.getBytes(encoding);
		output.write(toEmit);
	}

}
