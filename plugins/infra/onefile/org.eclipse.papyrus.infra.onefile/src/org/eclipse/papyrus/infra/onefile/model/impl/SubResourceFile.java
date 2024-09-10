/*****************************************************************************
 * Copyright (c) 2011 Atos Origin Integration.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tristan Faure (Atos Origin Integration) tristan.faure@atosorigin.com - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.onefile.model.impl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.internationalization.utils.PropertiesFilesUtils;
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
import org.eclipse.papyrus.infra.onefile.model.ISubResourceFile;

/**
 * Default implementation of {@link ISubResourceFile}
 *
 * @author tristan.faure@atosorigin.com
 *
 */
public class SubResourceFile implements ISubResourceFile {

	private IPapyrusFile parent;

	private final IFile subResource;

	public SubResourceFile(IPapyrusFile papy, IFile r) {
		this.parent = papy;
		this.subResource = r;

	}

	public IProject getProject() {
		return subResource.getProject();
	}

	@Override
	public String toString() {
		String result = subResource.getName();
		String fileExtension = subResource.getFileExtension();
		if(null != fileExtension){
			result = fileExtension;
			if(fileExtension.equals(PropertiesFilesUtils.PROPERTIES_FILE_EXTENSION)){
				final String resourceWithoutExtension = subResource.getName().substring(0, subResource.getName().lastIndexOf('.')); //$NON-NLS-1$
				if(!resourceWithoutExtension.equals(parent.getText())){
					result = subResource.getName().substring(parent.getText().length());
				}
			}
		}
		return result;
	}

	public IFile getFile() {
		return subResource;
	}

	public String getText() {
		return toString();
	}

	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	public IPapyrusFile getParent() {
		return parent;
	}

}
