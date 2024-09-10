/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
 *  Mathieu Velten (Atos Origin) mathieu.velten@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.navigation;

import org.eclipse.emf.ecore.EObject;

/**
 * This interface allows to set the base name of an element. The base can then
 * be used to derive a more useful name, containing the name of the element type
 * for example.
 *
 * @author mvelten
 *
 */
public interface IBaseNameSetter {

	public void setBaseName(String base, EObject toName);
}
