/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.infra.ui.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Basic implementation of {@link IPapyrusPageInput}
 *
 * @author Camille Letavernier
 * @since 1.2
 */
public class PapyrusPageInput extends FileEditorInput implements IPapyrusPageInput {

	private final URI[] pages;

	private final boolean closeOtherPages;

	/**
	 * Creates a new PapyrusPageInput
	 *
	 * @param diFile
	 *            The file resource
	 * @param pages
	 *            The pageIdentifiers of the pages to open
	 * @param closeOtherPages
	 *            True if only the selected pages should be opened. All other pages will be closed.
	 */
	public PapyrusPageInput(IFile diFile, URI[] pages, boolean closeOtherPages) {
		super(diFile);
		this.pages = pages;
		this.closeOtherPages = closeOtherPages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public URI[] getPages() {
		return pages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean closeOtherPages() {
		return closeOtherPages;
	}


}
