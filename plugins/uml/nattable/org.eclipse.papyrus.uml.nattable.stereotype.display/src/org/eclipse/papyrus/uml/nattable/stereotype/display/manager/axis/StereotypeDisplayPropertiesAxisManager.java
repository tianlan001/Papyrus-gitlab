/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
package org.eclipse.papyrus.uml.nattable.stereotype.display.manager.axis;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager;

public class StereotypeDisplayPropertiesAxisManager extends AbstractAxisManager {

	public StereotypeDisplayPropertiesAxisManager() {

	}

	@Override
	public boolean isSlave() {

		return false;
	}

	@Override
	public boolean isDynamic() {

		return false;
	}

	@Override
	public boolean canDestroyAxisElement(Integer axisIndex) {

		return false;
	}


	@Override
	public Command getDestroyAxisElementCommand(TransactionalEditingDomain domain, Integer axisPosition) {

		return null;
	}

}
