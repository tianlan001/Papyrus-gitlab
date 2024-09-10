/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.properties.databinding.tests;

import org.eclipse.uml2.uml.Model;

/**
 * This allows to define an abstract test class for an observable value with UML.
 */
public abstract class AbstractUMLObservableValueTest extends AbstractObservableValueTest {

	/**
	 * The model.
	 */
	protected Model model = null;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#setup()
	 */
	@Override
	public void setup() {
		super.setup();
		model = (Model) editorFixture.getModel();
	}

}
