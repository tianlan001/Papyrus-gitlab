/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.tests.regression;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Iterables;

/**
 * Test cases checking the load of palette.
 */
@PluginResource("resources/Model.di")
public class PaletteConfigurationTest extends AbstractPapyrusTest {

	/**
	 * The fixture.
	 */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	/**
	 * Constructor.
	 */
	public PaletteConfigurationTest() {
		super();
	}

	/**
	 * Verify that tool from palette configuration models is well loaded.
	 */
	@Test
	public void testPaletteConfigurationLoad() {
		final String diagramTitle = "TestPalette"; //$NON-NLS-1$
		final String StoOnClassTool = "StoOnClass"; //$NON-NLS-1$
		final String FakeTool = "FakeTool"; //$NON-NLS-1$
		final String NoProfileCreationTool = "NoProfileCreationTool"; //$NON-NLS-1$

		PaletteViewer palette = editorFixture.openDiagram(diagramTitle).getPalette();

		// Test the stereotype on class tool
		palette.setActiveTool(find(palette.getPaletteRoot(), StoOnClassTool, ToolEntry.class));
		editorFixture.flushDisplayEvents();
		assertThat(palette.getActiveTool().getLabel(), is(StoOnClassTool));

		// Test the fake tool
		palette.setActiveTool(find(palette.getPaletteRoot(), FakeTool, ToolEntry.class));
		editorFixture.flushDisplayEvents();
		assertThat(palette.getActiveTool().getLabel(), is(FakeTool));

		// Test the NoProfileCreationTool
		palette.setActiveTool(find(palette.getPaletteRoot(), NoProfileCreationTool, ToolEntry.class));
		editorFixture.flushDisplayEvents();
		assertThat(palette.getActiveTool().getLabel(), is(NoProfileCreationTool));
	}

	private <P extends PaletteEntry> P find(PaletteContainer container, String label, Class<P> type) {
		P result = null;

		for (P next : Iterables.filter(container.getChildren(), type)) {
			if (label.equalsIgnoreCase(next.getLabel())) {
				result = next;
				break;
			}
		}
		if (null == result) {
			for (PaletteContainer next : Iterables.filter(container.getChildren(), PaletteContainer.class)) {
				result = find(next, label, type);
				if (result != null) {
					break;
				}
			}
		}
		return result;
	}
}
