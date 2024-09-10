/**
 * Copyright (c) 2011 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Nicolas Guyomar (Mia-Software) - Bug 349546 - EMF Facet facetSet editor
 */
package org.eclipse.papyrus.emf.facet.efacet.ui.internal.exported.wizard;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.emf.facet.efacet.Facet;

/**
 *
 * @deprecated This interface has been replaced by ICreateFacetInFacetSetWizard2. No tracking bug needed because this API has not been released yet.
 * @since 0.2
 */
@Deprecated
public interface IFacetChildrenWizard {

	public int open();

	public void setFacet(Facet facet);

	public void setChildrenName(String name);

	public void setUpperBound(int upperBound);

	public void setLowerBound(int lowerBound);

	public void setType(EClass type);

	public void canChangeFacet(boolean canChange);

	public void canChangeChildrenName(boolean canChange);

	public void canChangeUpperBound(boolean canChange);

	public void canChangeLowerBound(boolean canChange);

	public void canChangeType(boolean canChange);

}
