/*******************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Juan Cadavid <juan.cadavid@cea.fr> - Initial API and implementation
 *     Christian W. Damus - bug 497865
 *     
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.tests;

import org.eclipse.papyrus.infra.services.controlmode.tests.control.ControlLegacyModelTest;
import org.eclipse.papyrus.infra.services.controlmode.tests.control.ControlModeManagerTest;
import org.eclipse.papyrus.infra.services.controlmode.tests.control.ControlModelTest;
import org.eclipse.papyrus.infra.services.controlmode.tests.control.ControlModelWithProfileTest;
import org.eclipse.papyrus.infra.services.controlmode.tests.control.ShardModeTest;
import org.eclipse.papyrus.infra.services.controlmode.tests.uncontrol.UncontrolModelTest;
import org.eclipse.papyrus.infra.services.controlmode.tests.uncontrol.UncontrolModelWithProfileTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ClassificationSuite.class)
@SuiteClasses({ ControlModelWithProfileTest.class,
		UncontrolModelTest.class,
		ControlModelTest.class,
		UncontrolModelWithProfileTest.class,
		ControlLegacyModelTest.class,
		ControlModeManagerTest.class,
		ShardModeTest.class,
})
public class AllTests {

}
