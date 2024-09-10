/*****************************************************************************
 * Copyright (c) 2010, 2018 CEA LIST, Christian W. Damus, and others.
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
 Christian W. Damus - bug 536486
 *
 * 		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;


import java.util.ArrayList;
import java.util.Collections;
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
import org.eclipse.uml2.uml.TimeConstraint;

/**
 * This advice add automatically the constrained element (occurrence specification) for a time constraint
 *
 * @since 3.0
 */
public class TimeConstraintHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(final ConfigureRequest request) {
		return new ConfigureElementCommand(request) {

			@SuppressWarnings("unlikely-arg-type")
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

				TimeConstraint newElement = (TimeConstraint) request.getElementToConfigure();
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
						newElement.getConstrainedElements().add(occurrence);
						break;
					}
				}

				return CommandResult.newOKCommandResult(newElement);
			}
		};
	}

	/**
	 * Get the object safely casted as a list of OccurrenceSpecification
	 *
	 * @param occurrenceSpecificationList
	 *            the object which is supposed to be a list of OccurrenceSpecification
	 */
	public static List<OccurrenceSpecification> getAsOccSpecList(Object occurrenceSpecificationList) {
		if (occurrenceSpecificationList instanceof List<?>) {
			List<?> list = (List<?>) occurrenceSpecificationList;
			if (!list.isEmpty()) {
				List<OccurrenceSpecification> newList = new ArrayList<>(list.size());
				for (Object elt : list) {
					if (elt instanceof OccurrenceSpecification) {
						newList.add((OccurrenceSpecification) elt);
					}
				}
				return newList;
			}
		}
		return Collections.emptyList();
	}

}
