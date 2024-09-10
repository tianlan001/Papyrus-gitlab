/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.validation;

import org.eclipse.emf.ecore.EObject;

/**
 * A simple hook that can be executed before and after validation commands. A possible
 * use for the former is to register additional constraints.
 * @since 3.0
 *
 */
public interface IValidationHook {
	/**
	 * This operation is called, before the validation is run
	 */
	public void beforeValidation(EObject element);

	/**
	 * This operation is called after validation has been run
	 */
	public void afterValidation(EObject element);
}
