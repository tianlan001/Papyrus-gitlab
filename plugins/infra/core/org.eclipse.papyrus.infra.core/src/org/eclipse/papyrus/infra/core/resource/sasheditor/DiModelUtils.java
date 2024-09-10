/*****************************************************************************
 * Copyright (c) 2012-2017 CEA LIST, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource.sasheditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescription;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFactory;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;

/**
 * Set of utility methods linked to Di resource.
 *
 * @author cedric dumoulin
 *
 */
public class DiModelUtils {

	/**
	 * Returns the related di file. Warning : this method is here for historical
	 * reasons. It should be removed asap.
	 *
	 * @param file
	 *            A file (di, model or notation).
	 * @return The associated DI file.
	 * @deprecated No replacement.
	 */
	@Deprecated
	public static IFile getRelatedDiFile(IFile file) {
		if (file == null) {
			return null;
		}
		IFile diFile;
		if (DiModel.MODEL_FILE_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
			diFile = file;
		} else {
			// Find the correct file
			IPath diPath = file.getFullPath().removeFileExtension().addFileExtension(DiModel.MODEL_FILE_EXTENSION);
			diFile = file.getParent().getFile(diPath.makeRelativeTo(file.getParent().getFullPath()));
		}
		return diFile;
	}

	/**
	 * Returns the DI Resource (.di) associated to the model set. May be null.
	 *
	 * @param modelSet
	 * @return
	 */
	public static Resource getDiResource(ModelSet modelSet) {
		IModel diModel = modelSet.getModel(DiModel.DI_MODEL_ID);
		if (diModel instanceof DiModel) {
			return ((DiModel) diModel).getResource();
		}
		return null;
	}

	/**
	 * Gets an architecture description element if available in the given model set
	 * 
	 * @param modelSet
	 *            the given model set
	 * @return an architecture description (can be null)
	 * @since 3.0
	 */
	public static ArchitectureDescription getArchitectureDescription(ModelSet modelSet) {
		Resource resource = getDiResource(modelSet);
		if (resource != null) {
			return (ArchitectureDescription) EcoreUtil.getObjectByType(
					resource.getContents(), ArchitecturePackage.Literals.ARCHITECTURE_DESCRIPTION);
		}
		return null;
	}

	/**
	 * Gets an architecture description element if available in the given model set or adds one if not available
	 * 
	 * @param modelSet
	 *            the given model set
	 * @return an architecture description
	 * @since 3.0
	 */
	public static ArchitectureDescription getOrAddArchitectureDescription(ModelSet modelSet) {
		Resource resource = getDiResource(modelSet);
		if (resource != null) {
			ArchitectureDescription description = (ArchitectureDescription) EcoreUtil.getObjectByType(resource.getContents(),
					ArchitecturePackage.Literals.ARCHITECTURE_DESCRIPTION);
			if (description == null) {
				description = ArchitectureFactory.eINSTANCE.createArchitectureDescription();
				resource.getContents().add(description);
			}
			return description;
		}
		return null;
	}

}
