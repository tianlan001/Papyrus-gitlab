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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.util.CustomizationConfigurationValidator;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.junit.utils.rules.ResourceSetFixture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Abstract tests for validation of {@link EMFFacetTreeViewerConfiguration}
 */
public abstract class AbstractTest {

	@Rule
	public ResourceSetFixture fixture = new ResourceSetFixture();

	protected ArchitectureDomain ad;

	@Before
	public void init() {
		this.ad = (ArchitectureDomain) fixture.getModelResource().getContents().get(0);
	}

	/**
	 * This test checks we are working on the expected mode (to avoid Copy/Paste mistake)
	 */
	@Test
	public void checkTestDefinition() {
		final String lastSegment = fixture.getModelResourceURI().trimFileExtension().lastSegment();
		Assert.assertEquals(getClass().getSimpleName(), lastSegment);
	}

	/**
	 *
	 * @return
	 *         the list of diagnostics produced by the {@link CustomizationConfigurationValidator}
	 */
	protected List<Diagnostic> validate() {
		final Diagnostic d = Diagnostician.INSTANCE.validate(this.ad);
		Assert.assertEquals(Diagnostic.ERROR, d.getSeverity());

		final List<Diagnostic> diagnostics = new ArrayList<>();

		// we filter diagnostic because there are others errors in the tested file (due to creation command not found)
		for (Diagnostic current : d.getChildren()) {
			if (CustomizationConfigurationValidator.DIAGNOSTIC_SOURCE.equals(current.getSource())) {
				diagnostics.add(current);
			}
		}
		return diagnostics;
	}

}
