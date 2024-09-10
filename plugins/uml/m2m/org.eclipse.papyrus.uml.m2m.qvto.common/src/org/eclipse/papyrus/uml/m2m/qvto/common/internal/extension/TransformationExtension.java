/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.m2m.qvto.common.internal.extension;

import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.m2m.qvt.oml.ExecutionContext;
import org.eclipse.papyrus.uml.m2m.qvto.common.concurrent.ExecutorsPool;
import org.eclipse.papyrus.uml.m2m.qvto.common.transformation.IImportTransformation;


/**
 * Extension to the RSA-to-Papyrus model transformation
 *
 * Provisional API
 *
 * @author Camille Letavernier
 *
 */
public interface TransformationExtension {
	/**
	 * Invoked only once when the extension is loaded for the first time (Before any execution)
	 *
	 * @return
	 * 		The source EPackages required by this transformation (Typically source profile definitions)
	 */
	public Set<EPackage> getAdditionalSourceEPackages();

	/**
	 * The resource set used by the main transformation. Contains the source and target resources
	 *
	 * @param resourceSet
	 */
	public void setResourceSet(ResourceSet resourceSet);

	/**
	 * The ExecutorsPool used for manipulating parallel QVTo transformations
	 *
	 * @param executorsPool
	 */
	public void setExecutorsPool(ExecutorsPool executorsPool);

	/**
	 *
	 * @param importTransformation
	 */
	public void setTransformation(IImportTransformation importTransformation);

	/**
	 * Part of the transformation that is executed before the main transformation's semantics and graphics import
	 *
	 * @param context
	 *            The QVTo execution context
	 * @param monitor
	 *            The current progress monitor. The monitor is reused from the main transformation; only use subtask/worked methods
	 * @return
	 */
	public IStatus executeBefore(ExecutionContext context, IProgressMonitor monitor);

	/**
	 * Part of the transformation that is executed after both the semantics and graphics have been imported
	 *
	 * @param context
	 *            The QVTo execution context
	 * @param monitor
	 *            The current progress monitor. The monitor is reused from the main transformation; only use subtask/worked methods
	 * @return
	 */
	public IStatus executeAfter(ExecutionContext context, IProgressMonitor monitor);

	/**
	 * The number of work steps required by this transformation. Corresponds to the total number of worked() you can invoke in executeBefore/executeAfter
	 *
	 * @return
	 */
	public int getNumberOfSteps();
}
