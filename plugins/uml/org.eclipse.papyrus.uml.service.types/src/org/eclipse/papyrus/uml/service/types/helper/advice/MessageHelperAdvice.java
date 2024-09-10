/*****************************************************************************
 * Copyright (c) 2010 - 2017 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 
 * 		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 * 		Celine JANSSENS (All4tec) - Refactor and update for Bug 520154 (Gate into account)
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.service.types.command.ConfigureMessageEventCommand;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Gate;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Helper advice for all {@link Message} elements.
 */
public class MessageHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * This method provides the source type provided as {@link ConfigureRequest} parameter.
	 * 
	 * @return the target role
	 * @since 3.1
	 */
	protected Element getSource(ConfigureRequest req) {
		Element result = null;
		Object paramObject = req.getParameter(CreateRelationshipRequest.SOURCE);
		if (paramObject instanceof Element) {
			result = (Element) paramObject;
		}

		return result;
	}

	/**
	 * This method provides the target type provided as {@link ConfigureRequest} parameter.
	 * 
	 * @return the target role
	 * @since 3.1
	 */
	protected Element getTarget(ConfigureRequest req) {
		Element result = null;
		Object paramObject = req.getParameter(CreateRelationshipRequest.TARGET);
		if (paramObject instanceof Element) {
			result = (Element) paramObject;
		}

		return result;
	}

	/**
	 * Return if the Configure Request is Valid
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 * @since 3.1
	 * @return true by default
	 *
	 */
	protected boolean isValidConfigureRequest(ConfigureRequest request) {

		ConfigureRequest req = request;
		boolean valid = true;
		if ((getSource(req) == null) || (getTarget(req) == null)) {
			valid = false;
		} else if ((!(getSource(req) instanceof Lifeline)) && (!(getSource(req) instanceof Interaction)) && (!(getSource(req) instanceof Gate)) && (!(getSource(req) instanceof ExecutionSpecification))) {
			valid = false;
		} else if ((!(getTarget(req) instanceof Lifeline)) && (!(getTarget(req) instanceof Interaction)) && (!(getTarget(req) instanceof Gate)) && (!(getTarget(req) instanceof ExecutionSpecification))) {
			valid = false;
		}

		return valid;
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 * 
	 * Complete the {@link Association} creation by:
	 * 		adding its {@link Property} ends in the model
	 * 		adding the UML Nature on the {@link Association}.
	 * 
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {

		final Element source = getSource(request);
		final Element target = getTarget(request);
		ICommand beforeConfigCommand = null;

		// Check if the Request is allowed
		if (!isValidConfigureRequest(request)) {
			beforeConfigCommand = UnexecutableCommand.INSTANCE;
		} else {

			// Get the main Command that configure the Message (Event creation, request Parameters update, ...)
			ICommand configureMessageCommand = getConfigureCommand(request);

			CompositeCommand command = new CompositeCommand(configureMessageCommand.getLabel());
			if (null != configureMessageCommand) {
				command.compose(configureMessageCommand);
			}

			// in case of Gate Update the Gate Message as well
			// For Source
			if (source instanceof MessageEnd) {
				ICommand setSourceEndCommand = getSetMessageEndCommand((MessageEnd) source, request);
				if (null != setSourceEndCommand && setSourceEndCommand.canExecute()) {
					command.compose(setSourceEndCommand);
				}
			}

			// For Target
			if (target instanceof MessageEnd) {
				ICommand setTargetEndCommand = getSetMessageEndCommand((MessageEnd) target, request);
				if (null != setTargetEndCommand && setTargetEndCommand.canExecute()) {
					command.compose(setTargetEndCommand);
				}
			}

			if (!command.isEmpty()) {
				beforeConfigCommand = command;
			}
		}

		return beforeConfigCommand;

	}

	/**
	 * Get the Command that would set the Message feature to the Message End (Gate or MOS) .
	 * 
	 * @param msgEnd
	 *            The gate to be set
	 * @param request
	 *            The Initial Configure Request of the Message
	 * @return The Command according to the ElementTypes of the Gate.
	 * @since 3.1
	 */
	protected ICommand getSetMessageEndCommand(MessageEnd msgEnd, ConfigureRequest request) {
		ICommand semanticCommand = null;

		IElementEditService commandService = ElementEditServiceUtils.getCommandProvider(msgEnd);
		SetRequest setMsgEndRequest = new SetRequest(msgEnd, UMLPackage.eINSTANCE.getMessageEnd_Message(), request.getElementToConfigure());

		if (commandService != null) {
			semanticCommand = commandService.getEditCommand(setMsgEndRequest);
		}

		return semanticCommand;
	}

	/**
	 * This get a Command in charge to update the Request Parameters and Create the different Event (OccurenceSpecification)
	 * 
	 * @param request
	 * @return
	 * @since 3.1
	 */
	protected ICommand getConfigureCommand(ConfigureRequest request) {
		return new ConfigureMessageEventCommand(request);
	}



	/**
	 * <pre>
	 * Add a command to destroy {@link MessageEnd} referenced by the {@link Message} 
	 * to delete.
	 * This command is only added if the send - receive event referenced is not 
	 * referenced by another element.
	 * </pre>
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeDestroyDependentsCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest)
	 * 
	 * @param request
	 *            the request
	 * @return the command to execute before the edit helper work is done
	 */
	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {

		List<EObject> dependentsToDestroy = new ArrayList<EObject>();

		Message message = (Message) request.getElementToDestroy();

		// Add send - receive referenced MessageEnd to the dependents list
		// if they are not used by another element.
		MessageEnd sendEvent = message.getSendEvent();
		if ((sendEvent != null) && (!isSharedEvent(sendEvent, message))) {
			dependentsToDestroy.add(sendEvent);
		}

		MessageEnd recvEvent = message.getReceiveEvent();
		if ((recvEvent != null) && (!isSharedEvent(recvEvent, message))) {
			dependentsToDestroy.add(recvEvent);
		}

		// return command to destroy dependents MessageEnd
		if (!dependentsToDestroy.isEmpty()) {
			return request.getDestroyDependentsCommand(dependentsToDestroy);
		}

		return null;
	}

	/**
	 * <pre>
	 * Test if the used element is referenced by other elements than the known
	 * referencer (except its container). It ignores references from an other meta-model.
	 * </pre>
	 *
	 * @param usedObject
	 *            the used object
	 * @param knownReferencer
	 *            the known referencer
	 * @return true if the known referencer is the only referencer.
	 * @since 3.0
	 */
	public static boolean isSharedEvent(MessageEnd usedObject, EObject knownReferencer) {
		EPackage mmPackage = usedObject.eClass().getEPackage();

		// Retrieve the list of elements referencing the usedObject.
		Set<EObject> crossReferences = new HashSet<EObject>();
		for (Setting setting : EMFHelper.getUsages(usedObject)) {
			EObject eObj = setting.getEObject();
			if (!setting.getEStructuralFeature().equals(UMLPackage.eINSTANCE.getLifeline_CoveredBy())) {
				if (eObj.eClass().getEPackage().equals(mmPackage)) {
					crossReferences.add(eObj);
				}
			}
		}

		// Remove the container of used object.
		crossReferences.remove(usedObject.eContainer());
		// Remove the knownReferencer from the list of references.
		crossReferences.remove(knownReferencer);

		// If no referencer remains in the list, the known element is the only
		// usage.
		return !(crossReferences.isEmpty());
	}

	/**
	 * Create a MessageEnd
	 *
	 * @param message
	 *            the message that reference the message end always !=null
	 * @param lifeline
	 *            the lifeLine where is set the message end ,always !=null
	 * @since 3.0
	 * @deprecated
	 */
	@Deprecated
	public static MessageEnd createMessageEnd(Message message, Lifeline lifeline, final MessageEnd previous) {
		MessageOccurrenceSpecification messageOccurrenceSpecification = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
		if (previous == null) {
			messageOccurrenceSpecification.setCovered(lifeline);
		} else {
			lifeline.getCoveredBys().add(lifeline.getCoveredBys().indexOf(previous) + 1, messageOccurrenceSpecification);
		}
		messageOccurrenceSpecification.setMessage(message);
		messageOccurrenceSpecification.setMessage(message);
		((Interaction) message.getOwner()).getFragments().add(messageOccurrenceSpecification);
		return messageOccurrenceSpecification;
	}

	/**
	 * Create a MessageEnd
	 *
	 * @param message
	 *            the message that reference the message end always !=null
	 * @param lifeline
	 *            the lifeLine where is set the message end ,always !=null
	 * @since 3.0
	 * @deprecated
	 */
	@Deprecated
	public static MessageEnd createDestroyMessageEnd(Message message, Lifeline lifeline) {
		DestructionOccurrenceSpecification messageOccurrenceSpecification = UMLFactory.eINSTANCE.createDestructionOccurrenceSpecification();
		messageOccurrenceSpecification.setCovered(lifeline);
		messageOccurrenceSpecification.setMessage(message);
		((Interaction) message.getOwner()).getFragments().add(messageOccurrenceSpecification);
		return messageOccurrenceSpecification;
	}
}
