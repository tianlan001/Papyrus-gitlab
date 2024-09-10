/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.wizards.widget;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * A filter for file extensions
 * 
 * @author Camille Letavernier
 */
public class ExtensionFilter extends ViewerFilter {

	private Set<String> extensions;

	/**
	 * 
	 * Constructs a ViewerFilter that will only accept filenames with one of the
	 * given extensions
	 * 
	 * @param extensions
	 *        The authorized extensions
	 */
	public ExtensionFilter(String[] extensions) {
		this.extensions = new HashSet<String>(Arrays.asList(extensions));
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof IFile) {
			IFile file = (IFile)element;
			for(String ext : extensions) {
				if(file.getFullPath().toString().endsWith(ext)) {
					return true;
				}
			}
		} else if(element instanceof IProject || element instanceof IFolder) {
			return true;
		}

		return false;
	}

}
