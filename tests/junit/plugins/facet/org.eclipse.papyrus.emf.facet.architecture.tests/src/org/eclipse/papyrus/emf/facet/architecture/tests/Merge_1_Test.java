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

import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration;
import org.eclipse.papyrus.emf.facet.architecture.internal.customizationconfiguration.comparators.CustomizationReferenceMerger;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.TreeViewerConfiguration;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Assert;
import org.junit.Test;

@PluginResource("resources/Merge/Merge_1_Test.architecture")
public class Merge_1_Test extends AbstractTest {

	private static final List<String> orderedCustomizations = new ArrayList<>();
	static {
		orderedCustomizations.add("Customization18"); //$NON-NLS-1$
		orderedCustomizations.add("Customization9"); //$NON-NLS-1$
		orderedCustomizations.add("Customization19"); //$NON-NLS-1$
		orderedCustomizations.add("Customization13"); //$NON-NLS-1$
		orderedCustomizations.add("Customization17"); //$NON-NLS-1$
		orderedCustomizations.add("Customization10"); //$NON-NLS-1$
		orderedCustomizations.add("Customization14"); //$NON-NLS-1$
		orderedCustomizations.add("Customization16"); //$NON-NLS-1$
		orderedCustomizations.add("Customization8"); //$NON-NLS-1$
		orderedCustomizations.add("Customization4"); //$NON-NLS-1$
		orderedCustomizations.add("Customization1"); //$NON-NLS-1$
		orderedCustomizations.add("Customization15"); //$NON-NLS-1$
		orderedCustomizations.add("Customization3"); //$NON-NLS-1$
		orderedCustomizations.add("Customization5"); //$NON-NLS-1$
	}

	@Test
	public void test() {
		final List<ArchitectureContext> contexts = this.ad.getContexts();
		Assert.assertEquals(1, contexts.size());
		Assert.assertTrue(contexts.get(0) instanceof ArchitectureDescriptionLanguage);
		final List<TreeViewerConfiguration> configurations = ((ArchitectureDescriptionLanguage) contexts.get(0)).getTreeViewerConfigurations();
		Assert.assertEquals("The tested model has changed?!", 5, configurations.size()); //$NON-NLS-1$

		final List<EMFFacetTreeViewerConfiguration> facetConfiguration = new ArrayList<>();
		for (final TreeViewerConfiguration current : configurations) {
			if (current instanceof EMFFacetTreeViewerConfiguration) {
				facetConfiguration.add((EMFFacetTreeViewerConfiguration) current);
			}
		}
		Assert.assertEquals("The tested model has changed?!", 5, facetConfiguration.size()); //$NON-NLS-1$

		CustomizationReferenceMerger merger = new CustomizationReferenceMerger(facetConfiguration);
		Assert.assertTrue("The tested model is not valid", merger.doValidationAndMerge()); //$NON-NLS-1$
		final List<Customization> customizations = merger.getMergedCustomizations();
		Assert.assertEquals("we don' get the expected number of merge customization", orderedCustomizations.size(), customizations.size()); //$NON-NLS-1$


		for (int i = 0; i < orderedCustomizations.size(); i++) {
			Assert.assertEquals(NLS.bind("The customization {0} is not at the expected location", orderedCustomizations.get(i)), orderedCustomizations.get(i), customizations.get(i).getName()); //$NON-NLS-1$
		}
	}

}
