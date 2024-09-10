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
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *
  *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading.tests.testModel2;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

/**
 * Test strategy 0 = Load all the needed resources
 * The model opened is Package0 (controlled resource)
 * 
 * @author eperico
 * 
 */
public class Strategy0TestModel2WithPackage0 extends AbstractResourceLoadingTestModel2 {

	@Override
	public int getStrategy() {
		// Load all the needed resources
		return 0;
	}
	
	@Override
	public IFile getResourceToLoad(IProject project) {
		return project.getFile(INITIAL_PATH + "Package0.di");
	}

}
