/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.notification.events;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

public class UnexecutableAdviceEvent extends AbstractUnexecutableEvent implements IAdviceEvent {

	private IEditHelperAdvice advice;

	private IEditHelperAdvice[] advices;

	private AdvicePhase advicePhase;

	public UnexecutableAdviceEvent(IEditCommandRequest req, IEditHelper editHelper, IEditHelperAdvice advice, ICommand command, IEditHelperAdvice[] advices, AdvicePhase advicePhase) {
		super(req, editHelper, command);
		this.advice = advice;
		this.advices = advices;
		this.advicePhase = advicePhase;
	}

	/**
	 * @return the advice
	 */
	@Override
	public IEditHelperAdvice getAdvice() {
		return advice;
	}

	/**
	 * @return the advices
	 */
	@Override
	public IEditHelperAdvice[] getAdvices() {
		return advices;
	}

	/**
	 * @return the advicePhase
	 */
	public AdvicePhase getAdvicePhase() {
		return advicePhase;
	}
}
