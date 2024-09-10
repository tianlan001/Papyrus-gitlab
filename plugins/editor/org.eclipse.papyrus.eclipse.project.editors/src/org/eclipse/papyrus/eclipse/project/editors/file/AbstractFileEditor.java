/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.papyrus.eclipse.project.editors.Activator;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IFileEditor;

/**
 *
 * An abstract class for File Editor
 *
 */
public abstract class AbstractFileEditor implements IFileEditor {

	/** the Eclipse Project */
	private final IProject project;

	private boolean dirty;

	/**
	 *
	 * Constructor.
	 *
	 * @param project
	 *            the eclipse project
	 */
	public AbstractFileEditor(final IProject project) {
		this.project = project;
	}

	/**
	 *
	 * @return
	 * 		the eclipse project
	 */
	@Override
	public IProject getProject() {
		return this.project;
	}

	@Override
	public void init() {
		// Pass
	}

	@Override
	public Set<String> getMissingFiles() {
		return new HashSet<String>();
	}

	/**
	 *
	 * @param text
	 *            the initial text
	 * @return
	 * 		an input stream
	 */
	protected InputStream getInputStream(final String text) {
		if (text == null) {
			Activator.log.warn("Cannot open an input stream for a null text"); //$NON-NLS-1$
			return null;
		}

		final StringReader sr = new StringReader(text);
		InputStream is = new InputStream() {

			@Override
			public int read() throws IOException {
				return sr.read();
			}

		};
		return is;
	}

	@Override
	public boolean exists() {
		return getMissingFiles().size() == 0;
	}

	@Override
	public void create() {
		createFiles(getMissingFiles());
		init();
	}

	/**
	 * @since 2.0
	 */
	@Override
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * Marks me {@linkplain #isDirty() dirty}.
	 * 
	 * @since 2.0
	 */
	protected final void touch() {
		this.dirty = true;
	}

	/**
	 * @since 2.0
	 */
	@Override
	public final void save() {
		if (isDirty()) {
			doSave();
			dirty = false;
		}
	}

	/**
	 * Implemented by subclasses to perform the save behaviour.
	 * 
	 * @since 2.0
	 */
	protected abstract void doSave();
}
