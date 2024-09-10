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
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference;
import org.eclipse.papyrus.emf.facet.architecture.internal.customizationconfiguration.comparators.CustomizationMergeErrorCode;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Assert;
import org.junit.Test;

@PluginResource("resources/TooManyRedefines/TooManyRedefines_2_Test.architecture")
public class TooManyRedefines_2_Test extends AbstractTest {

	@Test
	public void test() {
		final List<Diagnostic> diagnostics = validate();
		Assert.assertEquals("Diagnostic not found", 3, diagnostics.size()); //$NON-NLS-1$
		int nbTooManyRedefinesForMe = 0;
		int nbExtraRedefines = 0;
		for (final Diagnostic diag : diagnostics) {
			Assert.assertTrue(diag.getData().size() == 1);
			Assert.assertTrue(diag.getData().get(0) instanceof CustomizationReference);
			if (CustomizationMergeErrorCode.CHECK_ERROR__TOO_MANY_REDEFINES_FOR_ME == diag.getCode()) {
				nbTooManyRedefinesForMe++;
			}
			if (CustomizationMergeErrorCode.CHECK_ERROR__EXTRA_REDEFINES == diag.getCode()) {
				nbExtraRedefines++;
			}
		}
		Assert.assertEquals("I don't found the expected error TOO_MANY_REDEFINES_FOR_ME", 1, nbTooManyRedefinesForMe); //$NON-NLS-1$
		Assert.assertEquals("I don't found the expected error EXTRA_REDEFINES", 2, nbExtraRedefines); //$NON-NLS-1$
	}

}
