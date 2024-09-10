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

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class allows to check the icons depending to the enabled viewpoints.
 */
@SuppressWarnings("nls")
@PluginResource("/resources/GrayedIcon.di")
public class GrayedIconTest extends AbstractPapyrusTest {

	/**
	 * The editor fixture to manage the opening model.
	 */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	/**
	 * This allows to check the icon depending to the enabled viewpoints.
	 */
	@Test
	public void checkIcons() {

		// Get the active editor
		final IMultiDiagramEditor activeEditor = editorFixture.getEditor();

		// Get the page manager
		final IPageManager pageManager = ((IMultiPageEditorPart) activeEditor).getAdapter(IPageManager.class);
		Assert.assertNotNull("The page manager cannot be null", null != pageManager);

		// Get the activity diagram
		final Diagram activityDiagram = getActivityDiagram(pageManager);
		Assert.assertNotNull("The activity diagram is not found", activityDiagram);

		// Check the current icon (it won't be the grayed one)
		Assert.assertEquals("The icon is not the correct one", "platform:/plugin/org.eclipse.papyrus.uml.diagram.activity/icons/obj16/Diagram_Activity.gif", getIcon(activityDiagram));

		// Change the architecture viewpoint
		final ArchitectureDescriptionUtils helper = new ArchitectureDescriptionUtils(editorFixture.getModelSet());
		// Only keep the analysis viewpoint
		final Set<String> viewpointIds = helper.getArchitectureViewpointIds().stream().filter(id -> id.equals("org.eclipse.papyrus.uml.analysis")).collect(Collectors.toSet());

		// execute the command to change viewpoint and close editors
		editorFixture.getEditingDomain().getCommandStack().execute(helper.switchArchitectureViewpointIds(viewpointIds.toArray(new String[0])));

		// Check the current icon (it should be the grayed one)
		Assert.assertEquals("The icon is not the correct one", "platform:/plugin/org.eclipse.papyrus.uml.diagram.activity/icons/obj16/Diagram_Activity_grayed.gif", getIcon(activityDiagram));
	}

	/**
	 * Get the activity diagram from the page manager.
	 * 
	 * @param pageManager
	 *            The page manager.
	 * @return The activity diagram.
	 */
	private Diagram getActivityDiagram(final IPageManager pageManager) {
		for (final Object pageIdentifier : pageManager.allPages()) {
			if (pageIdentifier instanceof Diagram && "PapyrusUMLActivityDiagram".equals(((Diagram) pageIdentifier).getDiagram().getType())) {
				return (Diagram) pageIdentifier;
			}
		}
		return null;
	}

	/**
	 * Get the icon for the activity diagram for example.
	 * 
	 * @param activityDiagram
	 *            The activity diagram.
	 * @return The grayed icon if not in viewpoint, otherwise the initial icon.
	 */
	private String getIcon(final Diagram activityDiagram) {
		final PolicyChecker checker = PolicyChecker.getFor(activityDiagram);
		final ViewPrototype viewPrototype = ViewPrototype.get(activityDiagram);
		if (checker.isInViewpoint(viewPrototype.getRepresentationKind())) {
			return viewPrototype.getIconURI();
		} else {
			return viewPrototype.getGrayedIconURI();
		}
	}
}
