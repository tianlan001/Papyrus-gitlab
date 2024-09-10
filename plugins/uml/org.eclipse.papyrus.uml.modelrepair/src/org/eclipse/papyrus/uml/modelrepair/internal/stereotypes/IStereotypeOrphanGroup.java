/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.modelrepair.internal.stereotypes;

import org.eclipse.emf.ecore.EPackage;

/**
 * Protocol for a grouping of stereotype instances of some schema that are orphaned (no longer attached to any UML elements).
 */
public interface IStereotypeOrphanGroup {
	/**
	 * Queries the schema of the orphaned stereotype instances.
	 * 
	 * @return the profile schema
	 */
	EPackage getSchema();
}
