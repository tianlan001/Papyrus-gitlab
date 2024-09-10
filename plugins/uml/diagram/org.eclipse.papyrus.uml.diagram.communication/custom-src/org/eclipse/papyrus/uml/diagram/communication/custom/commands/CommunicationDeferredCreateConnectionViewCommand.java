/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Saadia Dhouib saadia.dhouib@cea.fr  - Initial API and Implementation
 *  Christian W. Damus - bug 462958
 *  Mickaël ADAM - mickael.adam@all4tec.net - Bug 459678
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.communication.custom.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest.ConnectionViewDescriptor;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CommonDeferredCreateConnectionViewCommand;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.MessageEditPart;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.MessageNameEditPart;
import org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry;



/**
 * The Class CommunicationDeferredCreateConnectionViewCommand is intended to create a new message link between two lifelines.
 * This class is only used in the case of Drop of a message
 */
// TODO reactivate one link between 2 lifeline
public class CommunicationDeferredCreateConnectionViewCommand extends CommonDeferredCreateConnectionViewCommand {

	/** the element for the connection's label semantic element */
	protected EObject labelElement = null;

	/**
	 * Instantiates a new communication deferred create connection view command.
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param semanticHint
	 *            the semantic hint
	 * @param sourceViewAdapter
	 *            the source view adapter
	 * @param targetViewAdapter
	 *            the target view adapter
	 * @param viewer
	 *            the viewer
	 * @param preferencesHint
	 *            the preferences hint
	 * @param viewDescriptor
	 *            the view descriptor
	 * @param command
	 *            the command
	 */
	public CommunicationDeferredCreateConnectionViewCommand(TransactionalEditingDomain editingDomain, String semanticHint, IAdaptable sourceViewAdapter, IAdaptable targetViewAdapter, EditPartViewer viewer, PreferencesHint preferencesHint,
			ConnectionViewDescriptor viewDescriptor, ICommand command, EObject labelElement) {
		super(editingDomain, semanticHint, sourceViewAdapter, targetViewAdapter, viewer, preferencesHint, viewDescriptor, command);
		this.labelElement = labelElement;
	}

	/**
	 * Creates a connection view between the source and target.
	 *
	 * @param progressMonitor
	 *            the progress monitor
	 * @param info
	 *            the info
	 * @param sourceEditPart
	 *            the source edit part
	 * @param targetEditPart
	 *            the target edit part
	 * @return the command result
	 * @throws ExecutionException
	 *             the execution exception
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info, IGraphicalEditPart sourceEditPart, IGraphicalEditPart targetEditPart) throws ExecutionException {

		// If these are null, then the diagram's editparts may not
		// have been refreshed yet.
		Assert.isNotNull(sourceEditPart);
		Assert.isNotNull(targetEditPart);

		CreateConnectionViewRequest createRequest = new CreateConnectionViewRequest(viewDescriptor);
		createConnectionCmd = CreateConnectionViewRequest.getCreateCommand(createRequest, sourceEditPart, targetEditPart);

		// Double-check that another drop command didn't already create the edge
		View messagePath = findCommunicationPath(sourceEditPart, targetEditPart);
		if (createConnectionCmd.canExecute()) {
			createConnectionCmd.execute();
		}
		if (labelElement != null) {

			messagePath = (View) createRequest.getConnectionViewDescriptor().getAdapter(View.class);
			if (messagePath == null) {
				viewer = null;// for garbage collection
				return CommandResult.newErrorCommandResult("Failed to obtain or create communication-path link.");
			}

			// set element of the label of the connector to element
			if (messagePath.getChildren().size() > 1) {
				messagePath.setElement(labelElement);// one link per message. TOFIX
				for (int i = 0; i < messagePath.getChildren().size(); i++) {
					View label = (View) (messagePath.getChildren().get(i));
					if (UMLVisualIDRegistry.getType(MessageNameEditPart.VISUAL_ID).equals(label.getType())) {
						// This is a message label. Is it the one we need to set?
						if (!label.eIsSet(NotationPackage.Literals.VIEW__ELEMENT) || (label.getElement() == null)) {
							label.setElement(labelElement);
						}
					}
				}
			}

		}

		viewer = null;// for garbage collection
		return CommandResult.newOKCommandResult(createRequest.getConnectionViewDescriptor());
	}

	@SuppressWarnings("unchecked")
	protected View findCommunicationPath(IGraphicalEditPart sourceEditPart, IGraphicalEditPart targetEditPart) {
		View result = null;

		View sourceView = sourceEditPart.getNotationView();
		View targetView = targetEditPart.getNotationView();

		if ((sourceView != null) && (targetView != null)) {
			for (Edge next : (Iterable<? extends Edge>) sourceView.getSourceEdges()) {
				if ((UMLVisualIDRegistry.getType(MessageEditPart.VISUAL_ID)).equals(next.getType())
						&& (next.getTarget() == targetView)) {
					result = next;
					break;
				}
			}
			if (result == null) {
				// Try the reverse orientation
				for (Edge next : (Iterable<? extends Edge>) sourceView.getTargetEdges()) {
					if ((UMLVisualIDRegistry.getType(MessageEditPart.VISUAL_ID)).equals(next.getType())
							&& (next.getSource() == targetView)) {
						result = next;
						break;
					}
				}
			}
		}

		return result;
	}
}
