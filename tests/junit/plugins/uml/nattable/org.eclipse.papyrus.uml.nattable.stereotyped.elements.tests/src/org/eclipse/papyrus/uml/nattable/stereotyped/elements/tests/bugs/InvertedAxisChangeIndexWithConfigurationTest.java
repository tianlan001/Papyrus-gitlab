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

package org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.bugs;

import org.eclipse.papyrus.junit.utils.rules.PluginResource;

/**
 * This allow to test the change index style for the table with configuration and with inverted axis.
 */
@PluginResource("resources/bugs/bug473155/InvertedAxisChangeIndexWithConfiguration.di")
public class InvertedAxisChangeIndexWithConfigurationTest extends AbstractInvertedAxisChangeIndexTest {

	/**
	 * Constructor.
	 */
	public InvertedAxisChangeIndexWithConfigurationTest() {
		super();
	}

}
