/*****************************************************************************
 * Copyright (c) 2011, 2016, 2022 Atos Origin Integration, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 485220
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 578434
 *****************************************************************************/
package org.eclipse.papyrus.infra.onefile.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.onefile.model.DiViewFilterHelper;
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
import org.eclipse.papyrus.infra.onefile.model.PapyrusModelHelper;

/**
 * Utility methods
 *
 * @author tristan.faure@atosorigin.com
 *
 */
public class OneFileUtils {

	/**
	 * Determines if a di exist in the container from a file name
	 *
	 * @param fileName
	 * @param parent
	 * @return
	 */
	public static boolean diExists(String fileName, IContainer parent) {
		return getDi(fileName, parent) != null;
	}

	/**
	 * Determines if a di exist in the container from a file name
	 *
	 * @param fileName
	 * @param parent
	 * @return
	 */
	public static IFile getDi(String fileName, IContainer parent) {
		if (parent == null || parent.getType() == IResource.ROOT) {
			return null;
		}
		final String substring = getFileNameForDi(fileName, parent);
		IFile file = parent.getFile(new Path(substring + "." + DiModel.DI_FILE_EXTENSION)); //$NON-NLS-1$
		if (file.exists()) {
			return file;
		}
		return null;
	}

	/**
	 * The file name for di search in parent container.
	 *
	 * @param fileName
	 *            The initial file name.
	 * @return The base of the di to search in the parent container.
	 * @since 2.1
	 * @deprecated since 2.2
	 */
	@Deprecated
	protected static String getFileNameForDi(final String fileName) {
		return DiViewFilterHelper.getInstance().getPapyrusDiViewFilter().getFileNameForDi(fileName, null);
	}

	/**
	 * The file name for di search in parent container.
	 *
	 * @param fileName
	 *            The initial file name.
	 * @return The base of the di to search in the parent container.
	 * @since 2.2
	 */
	protected static String getFileNameForDi(final String fileName, final IContainer parent) {
		return DiViewFilterHelper.getInstance().getPapyrusDiViewFilter().getFileNameForDi(fileName, parent);
	}

	/**
	 * check if the element has children or not
	 *
	 * @param element
	 * @return
	 */
	public static boolean hasChildren(Object element) {
		if (element instanceof IContainer) {
			IContainer container = (IContainer) element;
			try {
				return container.members().length > 0;
			} catch (CoreException e) {
			}
		}
		if (element instanceof IPapyrusFile) {
			IPapyrusFile iPapyrusFile = (IPapyrusFile) element;
			return iPapyrusFile.getMainFile() != null && iPapyrusFile.getAssociatedResources().length > 1;
		}
		return false;
	}

	/**
	 * Check if the element in parameter is visible or not
	 *
	 * @param element
	 * @return
	 */
	public static boolean isVisible(Object element) {
		if (element instanceof IFile) {
			IFile file = (IFile) element;
			return !OneFileUtils.diExists(file.getName(), file.getParent());
		}
		return true; // Don't filter unknown types
	}

	/**
	 * Check if the resource is a Papyrus Di
	 *
	 * @param fileName
	 * @return
	 */
	public static boolean isDi(IResource fileName) {
		return PapyrusModelHelper.getPapyrusModelFactory().isDi(fileName);
	}

	/**
	 * @param resources
	 *            a list of {@link IResource}
	 * @return <code>true</code> if the list contains a di file
	 * @since 3.1
	 */
	public static boolean containsDi(final Collection<IResource> resources) {
		final Iterator<IResource> iter = resources.iterator();
		while (iter.hasNext()) {
			final IResource current = iter.next();
			if (isDi(current)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Returns the name without the extension of the file
	 *
	 * @param res
	 * @return
	 */
	public static String withoutFileExtension(IResource res) {
		String extension = res.getFileExtension();
		if (extension != null) {
			if (extension.length() > 0) {
				return res.getName().substring(0, res.getName().lastIndexOf('.'));
			} else {
				return null;
			}
		} else {
			return res.getName();
		}
	}

	/**
	 * Check if the resource has a DI inside it
	 *
	 * @param resource
	 * @return
	 */
	public static boolean containsModelFiles(IResource resource) {
		if (resource instanceof IContainer) {
			IContainer container = (IContainer) resource;
			try {
				for (IResource m : container.members()) {
					if (isDi(m)) {
						return true;
					}
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public static IFile[] getAssociatedFiles(IPapyrusFile papyrusFile) {
		List<IFile> files = new ArrayList<>();
		for (IResource res : papyrusFile.getAssociatedResources()) {
			if (res instanceof IFile) {
				files.add((IFile) res);
			}
		}
		return files.toArray(new IFile[files.size()]);
	}
}
