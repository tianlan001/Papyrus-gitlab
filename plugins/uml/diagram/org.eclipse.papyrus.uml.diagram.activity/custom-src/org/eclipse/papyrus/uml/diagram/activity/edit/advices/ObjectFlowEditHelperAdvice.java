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
 *  Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.advices;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.tools.utils.ObjectFlowUtil;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.ObjectFlow;
import org.eclipse.uml2.uml.OutputPin;

/**
 * This Advice Helper is user to create pin between an ObjectFlow and an action (if it is authorized)
 */
public class ObjectFlowEditHelperAdvice extends AbstractEditHelperAdvice {

	@Override
	protected ICommand getAfterConfigureCommand(final ConfigureRequest req) {
		ICommand configureCommand = new ConfigureElementCommand(req) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				ObjectFlow element = (ObjectFlow) req.getElementToConfigure();
				ActivityNode source = getSourceObject(req);
				ActivityNode target = getTargetObject(req);
				// add pin if the source or the target is an action (see Bug 460022)
				if (source instanceof Action) {
					if (ObjectFlowUtil.insertPinForStartingNewObjectFlow(source)) {
						CreateElementRequest req = new CreateElementRequest(source, UMLElementTypes.OUTPUT_PIN);
						CreateElementCommand cmd = new CreateElementCommand(req);
						cmd.execute(null, null);
						CommandResult commandResult = cmd.getCommandResult();
						if (commandResult != null) {
							if (!commandResult.getStatus().isOK()) {
								return commandResult;
							}
						}
						OutputPin pin = (OutputPin) commandResult.getReturnValue();
						ObjectFlowUtil.insertOutputPin((Action) source, pin);
						source = pin;
					}
				}
				if (target instanceof Action) {
					if (ObjectFlowUtil.insertPinForStartingNewObjectFlow(target)) {
						CreateElementRequest req = new CreateElementRequest(target, UMLElementTypes.INPUT_PIN);
						CreateElementCommand cmd = new CreateElementCommand(req);
						cmd.execute(null, null);
						CommandResult commandResult = cmd.getCommandResult();
						if (commandResult != null) {
							if (!commandResult.getStatus().isOK()) {
								return commandResult;
							}
						}
						InputPin pin = (InputPin) commandResult.getReturnValue();
						ObjectFlowUtil.insertInputPin((Action) target, pin);
						target = pin;
					}
				}
				if (source != null) {
					element.setSource(source);
				}
				if (target != null) {
					element.setTarget(target);
				}
				return CommandResult.newOKCommandResult(element);
			}
		};
		return CompositeCommand.compose(configureCommand, super.getAfterConfigureCommand(req));
	}

	/**
	 * This method provides the object to be use as source.
	 *
	 * @return the source value
	 */
	protected ActivityNode getSourceObject(ConfigureRequest req) {
		Object result = req.getParameter(CreateRelationshipRequest.SOURCE);
		return (result instanceof ActivityNode) ? (ActivityNode) result : null;
	}

	/**
	 * This method provides the object to be used as target.
	 *
	 * @return the target value
	 */
	protected ActivityNode getTargetObject(ConfigureRequest req) {
		Object result = req.getParameter(CreateRelationshipRequest.TARGET);
		return (result instanceof ActivityNode) ? (ActivityNode) result : null;
	}
}
