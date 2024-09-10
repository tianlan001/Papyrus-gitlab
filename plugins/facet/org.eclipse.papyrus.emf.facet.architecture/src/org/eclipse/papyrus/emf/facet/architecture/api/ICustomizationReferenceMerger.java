/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Tessier (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.facet.architecture.api;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;


/**
 * This interface explains provided operations to make merge of customization configuration
 */
public interface ICustomizationReferenceMerger {

	/**
	 *
	 * @return
	 *         <code>true</code> if the validation process works until the final merge, <code>false</code> otherwise
	 */
	boolean doValidationAndMerge();

	/**
	 *
	 * @return
	 *         a map with eobject for which a status is associated. The status can be a {@link MultiStatus}
	 *
	 */
	Map<EObject, IStatus> getStatus();

	/**
	 *
	 * @return
	 *         the build list of {@link Customization} to apply. if the method {@link #doValidationAndMerge()} returned <code>true</code>,
	 *         we return the full merge of {@link Customization}, if the method {@link #doValidationAndMerge()} returned <code>false</code>, we return the merge built until the error appears
	 */
	List<Customization> getMergedCustomizations();

	/**
	 *
	 * @return
	 *         <code>true</code> if the merge was a success, <code>false</code> otherwise.
	 */
	boolean isValid();

}