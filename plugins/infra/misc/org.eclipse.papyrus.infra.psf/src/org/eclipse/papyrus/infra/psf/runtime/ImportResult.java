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
package org.eclipse.papyrus.infra.psf.runtime;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;

/**
 * Represents the result of an Import PSF action.
 * Contains the set of sucessfully imported projects,
 * and a list of diagnostics
 *
 * @author Camille Letavernier
 *
 */
public class ImportResult {

	private IProject[] importedProjects;

	private final List<IStatus> diagnostic = new LinkedList<IStatus>();

	public IProject[] getImportedProjects() {
		return importedProjects;
	}

	public void setImportedProjects(IProject[] importedProjects) {
		this.importedProjects = importedProjects;
	}

	public List<IStatus> getDiagnostic() {
		return diagnostic;
	}

}
