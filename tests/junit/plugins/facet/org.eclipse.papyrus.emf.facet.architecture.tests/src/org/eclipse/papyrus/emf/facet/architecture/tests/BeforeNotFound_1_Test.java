/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.emf.facet.architecture.tests;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder;
import org.eclipse.papyrus.emf.facet.architecture.internal.customizationconfiguration.comparators.CustomizationMergeErrorCode;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Assert;
import org.junit.Test;

@PluginResource("resources/BeforeNotFound/BeforeNotFound_1_Test.architecture")
public class BeforeNotFound_1_Test extends AbstractTest {

	@Test
	public void test() {
		final List<Diagnostic> diagnostics = validate();
		Assert.assertEquals("Diagnostic not found", 1, diagnostics.size()); //$NON-NLS-1$
		for (final Diagnostic diag : diagnostics) {
			Assert.assertTrue(diag.getData().size() == 1);
			Assert.assertTrue(diag.getData().get(0) instanceof RelativeOrder);
			Assert.assertEquals(CustomizationMergeErrorCode.MERGE_ERROR__BEFORE_NOT_FOUND, diag.getCode());
		}
	}

}
