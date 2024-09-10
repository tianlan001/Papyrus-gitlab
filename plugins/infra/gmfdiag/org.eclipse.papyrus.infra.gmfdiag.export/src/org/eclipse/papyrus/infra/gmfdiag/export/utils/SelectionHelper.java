/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.export.utils;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForSelection;

/**
 * Helper to extract file from selection in Project Explorer.
 *
 * @author Gabriel Pascual
 *
 */
public class SelectionHelper {

	/**
	 * Constructor.
	 *
	 */
	private SelectionHelper() {

	}

	/**
	 * Try to retrieve the selected file from the given selection.
	 *
	 * @param selection
	 *            the selection
	 * @return the selected file
	 */
	public static IFile convertSelection2File(ISelection selection) {
		IFile foundFile = null;

		// get the selected diagrams file
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			// Only one file should be selected
			if (!structuredSelection.isEmpty() && structuredSelection.size() == 1) {
				Object selectedObject = structuredSelection.getFirstElement();
				foundFile = getIFile(selectedObject);
			}
		}
		return foundFile;
	}

	/**
	 * Return the IFile corresponding to the selection.
	 *
	 * @param selectedObj
	 *            selected file
	 * @return the i file
	 */
	public static IFile getIFile(Object selectedObj) {
		IFile result = null;
		if (selectedObj instanceof IFile) {
			result = (IFile) selectedObj;
		}
		// Try to adapt
		if (result == null && selectedObj instanceof IAdaptable) {
			result = ((IAdaptable) selectedObj).getAdapter(IFile.class);
		}
		// adapt in ifile
		if (result == null) {
			result = Platform.getAdapterManager().getAdapter(selectedObj, IFile.class);
		}
		if (result == null) {
			// try to check if it is a collection
			Collection<?> collec = Platform.getAdapterManager().getAdapter(selectedObj, Collection.class);
			if (collec != null) {
				for (Object o : collec) {
					if (o instanceof IFile) {
						IFile f = (IFile) o;
						if (DiModel.MODEL_FILE_EXTENSION.equals(f.getFileExtension())) {
							result = f;
							break;
						}
					}
				}
			}
		}
		return result != null && DiModel.MODEL_FILE_EXTENSION.equals(result.getFileExtension()) ? result
				: null;
	}


	/**
	 * Checks if selection is exportable model. To export a model to images, it must contain diagrams.
	 *
	 *
	 * @param receiver
	 *            the receiver
	 * @return true, if is exportable mosdel
	 */
	public static boolean isExportableModel(IStructuredSelection receiver) {
		boolean result = false;

		try {
			ModelSet modelSet = ServiceUtilsForSelection.getInstance().getModelSet(receiver);
			result = (modelSet != null) && ExportUtils.hasExportableDiagrams(modelSet);
		} catch (ServiceException e) {
			// Ignore; it's normal for some selections
		}

		return result;
	}
}
