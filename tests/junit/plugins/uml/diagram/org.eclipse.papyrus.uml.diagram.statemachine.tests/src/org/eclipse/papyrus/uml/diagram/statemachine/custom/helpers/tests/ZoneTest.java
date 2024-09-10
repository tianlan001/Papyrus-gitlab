/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.tests;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.Zone;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.junit.Rule;
import org.junit.Test;

/**
 * Regression tests for the {@link Zone} utility class.
 */
public class ZoneTest extends AbstractPapyrusTest {
	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	/**
	 * Verifies that no exception occurs to block the deletion of a diagram
	 * that has a region with an empty zone annotation, as occurs in
	 * Papyrus-RT real-time state machine diagrams.
	 */
	@Test
	@PluginResource("resources/bug468207/model.di")
	@ActiveDiagram("SmDiagram")
	public void deleteDiagramWithEmptyZoneAnnotation_bug468207() {
		Diagram smDiagram = editor.getActiveDiagram().getDiagramView();
		Behavior stateMachine = ((BehavioredClassifier) editor.getModel().getOwnedType("Class1")).getOwnedBehaviors().get(0);

		// Delete it, which deletes the diagram
		DestroyElementRequest req = new DestroyElementRequest(editor.getEditingDomain(), stateMachine, false);
		ICommand cmd = ElementEditServiceUtils.getCommandProvider(stateMachine.getContext()).getEditCommand(req);
		editor.execute(cmd);

		// The diagram was successfully deleted
		assertThat(smDiagram.eResource(), nullValue());
	}

}
