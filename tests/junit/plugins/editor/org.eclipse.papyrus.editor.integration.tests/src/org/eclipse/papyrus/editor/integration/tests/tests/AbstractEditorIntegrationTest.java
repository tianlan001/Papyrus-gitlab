/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.editor.integration.tests.tests;

import org.eclipse.papyrus.editor.integration.tests.Activator;
import org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest;
import org.osgi.framework.Bundle;


public abstract class AbstractEditorIntegrationTest extends AbstractEditorTest {

	@Override
	protected Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

}
