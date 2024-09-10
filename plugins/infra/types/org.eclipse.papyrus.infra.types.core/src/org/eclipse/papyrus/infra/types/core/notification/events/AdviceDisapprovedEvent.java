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

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

public class AdviceDisapprovedEvent extends AbstractDisapprovedEvent implements IAdviceEvent {

	private IEditHelperAdvice advice;

	IEditHelperAdvice[] advices;

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

	public AdviceDisapprovedEvent(IEditCommandRequest req, IEditHelper editHelper, IEditHelperAdvice nextAdvice, IEditHelperAdvice[] advices) {
		super(req, editHelper);
		this.advice = nextAdvice;
		this.advices = advices;
	}
}
