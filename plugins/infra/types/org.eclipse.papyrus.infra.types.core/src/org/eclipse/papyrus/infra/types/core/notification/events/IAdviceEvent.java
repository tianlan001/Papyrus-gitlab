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

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;

public interface IAdviceEvent extends ITypesEvent {

	/**
	 * @return the advice
	 */
	public IEditHelperAdvice getAdvice();

	/**
	 * @return the advices
	 */
	public IEditHelperAdvice[] getAdvices();
}
