/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
package org.eclipse.papyrus.infra.services.semantic.service;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.services.IService;

/**
 * A service to retrieve the semantic root(s) and IModel(s) for the current context
 *
 * @author Camille Letavernier
 *
 */
public interface SemanticService extends IService {

	/**
	 * Retrieves the semantic root EObject(s) for the current context
	 *
	 * @return
	 */
	public EObject[] getSemanticRoots();

	/**
	 * Retrieves the semantic root IModel for the current context
	 *
	 * @return
	 */
	public IModel[] getSemanticIModels();

}
