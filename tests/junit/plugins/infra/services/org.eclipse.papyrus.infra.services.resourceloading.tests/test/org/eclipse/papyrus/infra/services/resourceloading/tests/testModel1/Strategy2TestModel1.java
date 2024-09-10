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
package org.eclipse.papyrus.infra.services.resourceloading.tests.testModel1;



/**
 * Test strategy 2 = Load the additional resources (profile and pathmap) and the needed controlled resources
 * 
 * @author eperico
 * 
 */
public class Strategy2TestModel1 extends AbstractResourceLoadingTestModel1 {

	@Override
	public int getStrategy() {
		// Load the additional resources (profile and pathmap) and the needed controlled resources
		return 2;
	}
}
