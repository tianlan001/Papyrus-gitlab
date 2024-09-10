/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.architecture.tests.merged;

import org.eclipse.papyrus.infra.architecture.commands.IModelConversionCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;

public class ConvertTestModelCommand implements IModelConversionCommand {

	@Override
	public void convertModel(ModelSet modelSet) {
		throw new UnsupportedOperationException("This class is defined for tests only."); //$NON-NLS-1$
	}

}
