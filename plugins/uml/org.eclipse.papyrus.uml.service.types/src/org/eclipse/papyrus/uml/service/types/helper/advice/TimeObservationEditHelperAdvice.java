/*****************************************************************************
 * Copyright (c) 2010, 2018 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	 CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 536486
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;


import static org.eclipse.papyrus.uml.service.types.helper.advice.TimeConstraintHelperAdvice.getAsOccSpecList;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.papyrus.uml.service.types.utils.SequenceRequestConstant;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.TimeObservation;

/**
 * Advice for configuration and editing of {@link TimeObservation}s.
 */
public class TimeObservationEditHelperAdvice extends AbstractEditHelperAdvice {

	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		return new ConfigureElementCommand(request) {

			@SuppressWarnings("unlikely-arg-type")
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				TimeObservation newElement = (TimeObservation) request.getElementToConfigure();

				// assign the occurrence specification
				Object paramOccurrence = getRequest().getParameter(SequenceRequestConstant.NEAREST_OCCURRENCE_SPECIFICATION);
				List<OccurrenceSpecification> occList = getAsOccSpecList(paramOccurrence);
				if (!occList.isEmpty()) {
					for (OccurrenceSpecification occurrence : occList) {
						if (occurrence instanceof MessageOccurrenceSpecification) {
							Message mess = ((MessageOccurrenceSpecification) occurrence).getMessage();
							if (mess != null && occurrence.equals(mess.getReceiveEvent()) && MessageSort.SYNCH_CALL_LITERAL.equals(mess.getMessageSort())
									&& occList.contains(mess.getSendEvent())) {
								// filter receive event, we prefer the corresponding start event at the same location
								continue;
							}
						}
						// otherwise, first occ is just fine
						newElement.setEvent(occurrence);
						break;
					}
				}

				return CommandResult.newOKCommandResult(newElement);
			}
		};
	}

}
