/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.generic.tests.bugs;

import org.eclipse.papyrus.junit.utils.rules.PluginResource;

/**
 * This allow to test the change index style for the table without configuration.
 */
@PluginResource("resources/bugs/bug473155/AxisChangeIndexWithoutConfiguration.di")
public class AxisChangeIndexWithoutConfigurationTest extends AbstractAxisChangeIndexTest {

	/**
	 * Constructor.
	 */
	public AxisChangeIndexWithoutConfigurationTest() {
		super();
	}

}
