/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 /*****************************************************************************/
package org.eclipse.papyrus.infra.constraints.ui.providers;

import org.eclipse.papyrus.infra.constraints.runtime.ConstraintsManager;
import org.eclipse.papyrus.infra.widgets.providers.AbstractFilteredContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * A ContentProvider which returns Constraint types
 *
 *
 * @author Camille Letavernier
 */
public class ConstraintTypeContentProvider extends AbstractFilteredContentProvider implements IStaticContentProvider {

	@Override
	public Object[] getElements() {
		return ConstraintsManager.instance.getConstraintTypes().toArray();
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getElements();
	}
}
