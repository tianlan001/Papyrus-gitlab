/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.stereotypecollector;

import java.util.Collection;

import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.uml2.uml.Stereotype;


public interface IStereotypeCollector {

	public Collection<Stereotype> computeAvailableStereotypes(ISearchPageContainer container);
}
