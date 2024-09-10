/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.Zone;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;

public class CustomStateMachineWithDefaultRegionCreateNodeCommand extends AbstractTransactionalCommand {
	IAdaptable adaptable;
	PreferencesHint prefHints;
	CreateViewRequest.ViewDescriptor viewDescriptor;

	public CustomStateMachineWithDefaultRegionCreateNodeCommand(IAdaptable adaptable, PreferencesHint prefHints, TransactionalEditingDomain domain, String label, List<?> affectedFiles) {
		super(domain, label, affectedFiles);
		this.adaptable = adaptable;
		this.prefHints = prefHints;
		viewDescriptor = new ViewDescriptor(adaptable, prefHints);
		// make sure the return object is available even before
		// executing/undoing/redoing
		setResult(CommandResult.newOKCommandResult(viewDescriptor));
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// adapt the view at execution time
		View stateMachineView = adaptable.getAdapter(View.class);
		View compartmentView = null;
		Iterator<?> it = stateMachineView.getChildren().iterator();
		while ((compartmentView == null) && it.hasNext()) {
			View currentView = (View) it.next();
			if (UMLVisualIDRegistry.getVisualID(currentView.getType()).equals(StateMachineCompartmentEditPart.VISUAL_ID)) {
				compartmentView = currentView;
			}
		}
		EObject represented = stateMachineView.getElement();
		if (!(represented instanceof StateMachine)) {
			return CommandResult.newErrorCommandResult("Impossible to get the state machine to create the region from");
		}
		StateMachine stateMachine = (StateMachine) stateMachineView.getElement();
		Iterator<Region> regions = stateMachine.getRegions().iterator();
		while (regions.hasNext()) {
			Region region = regions.next();
			IAdaptable regionAdaptable = new SemanticAdapter(region, null);
			String semanticHint = ((IHintedType) UMLElementTypes.Region_Shape).getSemanticHint();
			if (compartmentView != null) {
				Node regionNode = ViewService.getInstance().createNode(regionAdaptable, compartmentView, semanticHint, -1, prefHints);
				// add region specifics
				Zone.createRegionDefaultAnnotation(regionNode);
				if (regionNode != null) {
					viewDescriptor.setView(regionNode);
				}
			}
		}
		return CommandResult.newOKCommandResult(viewDescriptor);
	}
}
