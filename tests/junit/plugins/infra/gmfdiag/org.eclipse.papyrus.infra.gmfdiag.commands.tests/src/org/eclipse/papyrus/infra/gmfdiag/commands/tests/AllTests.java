/*
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
 *  Christian W. Damus (CEA) - initial API and implementation
 *  Christian W. Damus - bugs 459746, 433206, 491542
 */
package org.eclipse.papyrus.infra.gmfdiag.commands.tests;

import org.eclipse.papyrus.commands.CheckedDiagramCommandStackTest;
import org.eclipse.papyrus.commands.NestingNotifyingWorkspaceCommandStackTest;
import org.eclipse.papyrus.commands.NotifyingWorkspaceCommandStackTest;
import org.eclipse.papyrus.commands.util.CommandTreeIteratorTest;
import org.eclipse.papyrus.junit.framework.classification.ClassificationSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Master test suite for this test fragment.
 */
@RunWith(ClassificationSuite.class)
@SuiteClasses({
		// {oep.commands}
		NotifyingWorkspaceCommandStackTest.class, NestingNotifyingWorkspaceCommandStackTest.class,
		// {oep.commands.util}
		CommandTreeIteratorTest.class,
		CheckedDiagramCommandStackTest.class })
public class AllTests {

}
