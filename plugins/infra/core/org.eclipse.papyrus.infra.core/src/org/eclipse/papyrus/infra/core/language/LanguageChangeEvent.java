/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.language;

import java.util.EventObject;
import java.util.Set;

import org.eclipse.emf.common.util.URI;

import com.google.common.collect.ImmutableSet;

/**
 * An asynchronous notification of changes in the languages instantiated in some resource(s).
 */
public class LanguageChangeEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	/** Event type indicating that the event's languages were added in the resource(s). */
	public static final int ADDED = 1;

	/** Event type indicating that the event's languages were removed from the resource(s). */
	public static final int REMOVED = 2;

	private final int type;

	private final URI modelURI;

	private final boolean uriHasExtension;

	private final Set<ILanguage> languages;

	/**
	 * Initializes me with the details of what changed.
	 */
	public LanguageChangeEvent(Object source, int type, URI modelURI, boolean uriHasExtension, Iterable<ILanguage> languages) {
		super(source);

		this.type = type;
		this.modelURI = modelURI;
		this.uriHasExtension = uriHasExtension;
		this.languages = ImmutableSet.copyOf(languages);
	}

	public int getType() {
		return type;
	}

	public URI getModelURI() {
		return modelURI;
	}

	public boolean getURIHasExtension() {
		return uriHasExtension;
	}

	public Set<ILanguage> getLanguages() {
		return languages;
	}
}
