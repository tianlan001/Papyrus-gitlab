/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.tests.editors;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.ui.architecture.commands.CloseEditorsForViewpointsCommand;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class allows to test the switch of viewpoints and its behavior.
 */
@SuppressWarnings("nls")
@PluginResource("/resources/SwitchViewpoints.di")
public class SwitchViewpointsTest extends AbstractPapyrusTest {

	/**
	 * The editor fixture to manage the opening model.
	 */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	/**
	 * This allows to test the switch of viewpoint.
	 */
	@Test
	public void switchViewpoints() {

		// Get the active editor
		final IMultiDiagramEditor activeEditor = editorFixture.getEditor();

		// Get the page manager
		final IPageManager pageManager = ((IMultiPageEditorPart) activeEditor).getAdapter(IPageManager.class);
		Assert.assertNotNull("The page manager cannot be null", null != pageManager);

		// Open the diagram and tables
		int numberOpenedPage = 0;
		for (final Object pageIdentifier : pageManager.allPages()) {
			pageManager.openPage(pageIdentifier);
			numberOpenedPage++;
		}

		// Check the pages number
		editorFixture.flushDisplayEvents();
		Assert.assertEquals("The number of pages is not the correct one", 4, numberOpenedPage);

		// Change the architecture viewpoints
		final ArchitectureDescriptionUtils helper = new ArchitectureDescriptionUtils(editorFixture.getModelSet());
		// Only keep the analysis viewpoint
		final Set<String> viewpointIds = helper.getArchitectureViewpointIds().stream()
				.filter(id -> id.equals("org.eclipse.papyrus.uml.analysis")).collect(Collectors.toSet());
		final Collection<MergedArchitectureViewpoint> viewpoints = helper.getArchitectureViewpoints();

		// execute the command to change viewpoint and close editors
		final EditingDomain domain = editorFixture.getEditingDomain();
		final CompoundCommand cc = new CompoundCommand();
		// It is needed to be the first action to avoid opened dialog for viewpoints selection
		cc.append(new CloseEditorsForViewpointsCommand(viewpoints.stream().filter(viewpoint -> viewpointIds.contains(viewpoint.getId())).collect(Collectors.toList())));
		cc.append(helper.switchArchitectureViewpointIds(viewpointIds.toArray(new String[0])));
		// More than set the architecture viewpoints used, close needed editors
		domain.getCommandStack().execute(cc);

		// Get the number of opened page
		numberOpenedPage = getNumberOfOpenedPages(pageManager);

		// Now, re-check the active editors number
		editorFixture.flushDisplayEvents();
		Assert.assertEquals("The number of pages is not the correct one", 2, numberOpenedPage);

		// check the undo
		domain.getCommandStack().undo();

		// Get the number of opened page
		numberOpenedPage = getNumberOfOpenedPages(pageManager);

		// Check the pages number
		editorFixture.flushDisplayEvents();
		Assert.assertEquals("The number of pages is not the correct one", 4, numberOpenedPage);

		// check the redo
		domain.getCommandStack().redo();

		// Get the number of opened page
		numberOpenedPage = getNumberOfOpenedPages(pageManager);

		// Now, re-check the active editors number
		editorFixture.flushDisplayEvents();
		Assert.assertEquals("The number of pages is not the correct one", 2, numberOpenedPage);
	}

	/**
	 * Get the number of opened pages.
	 * 
	 * @param pageManager The page manager.
	 * @return The number of opened diagrams and tables.
	 */
	private int getNumberOfOpenedPages(final IPageManager pageManager) {
		int numberOpenedPage = 0;
		for (final Object pageIdentifier : pageManager.allPages()) {
			if (pageManager.isOpen(pageIdentifier)) {
				numberOpenedPage++;
			}
		}
		return numberOpenedPage;
	}
}
