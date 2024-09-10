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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.internationalization.utils.PropertiesFilesUtils;
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
import org.eclipse.papyrus.infra.onefile.utils.OneFileUtils;

/**
 * Default Implementation of {@link IPapyrusFile}
 *
 * @author tristan.faure@atosorigin.com
 *
 */
public class PapyrusFile implements IPapyrusFile {

	private final IFile file;


	public PapyrusFile(IFile file) {
		this.file = file;
	}

	public IFile getMainFile() {
		return file;
	}

	public IResource[] getAssociatedResources() {
		ArrayList<IResource> files = new ArrayList<IResource>();
		try {
			for (final IResource res : file.getParent().members()) {
				if (res instanceof IFile){
					final String resourceWithoutExtension = OneFileUtils.withoutFileExtension(res);
					final String fileWithoutExtension = OneFileUtils.withoutFileExtension(file);
					if(fileWithoutExtension.equals(resourceWithoutExtension)) {
						files.add(res);
					}else if(PropertiesFilesUtils.PROPERTIES_FILE_EXTENSION.equals(res.getFileExtension()) && resourceWithoutExtension.startsWith(fileWithoutExtension)){
						String possibleLocale = resourceWithoutExtension.substring(fileWithoutExtension.length());
						if(possibleLocale.startsWith("_")){ //$NON-NLS-1$
							possibleLocale = possibleLocale.substring(1);
							Locale localeFound = null;

							// Check about possible locale in available locales
							final Iterator<Locale> availableLocales = Arrays.asList(Locale.getAvailableLocales())
									.iterator();
							while (availableLocales.hasNext() && null == localeFound) {
								final Locale currentAvailableLocale = availableLocales.next();

								if (currentAvailableLocale.toString().equals(possibleLocale)) {
									localeFound = currentAvailableLocale;
								}
							}

							// The file contains a locale, load it
							if (null != localeFound) {
								files.add(res);
							}
						}
					}
				}
			}
		} catch (final CoreException e) {
			Activator.log.error("The file members cannot be found", e); //$NON-NLS-1$
		}
		return files.toArray(new IResource[] {});
	}

	public String getLabel() {
		return file.getName();
	}



	@Override
	public int hashCode() {
		return getMainFile().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PapyrusFile) {
			PapyrusFile papy = (PapyrusFile) obj;
			return getMainFile().equals(papy.getMainFile());
		}
		return super.equals(obj);
	}

	public IProject getProject() {
		return file.getProject();
	}

	public IContainer getParent() {
		return file.getParent();
	}

	public String getName() {
		return file.getName();
	}

	public String getText() {
		return getName().substring(0, getName().lastIndexOf('.'));
	}

	public Object getAdapter(Class adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

}
