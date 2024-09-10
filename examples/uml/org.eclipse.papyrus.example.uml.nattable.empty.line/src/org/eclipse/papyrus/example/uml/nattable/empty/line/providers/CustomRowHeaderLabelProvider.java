/*****************************************************************************
 * Copyright (c) 2020,2021 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Asma SMAOUI (CEA LIST) - bug 573840
  *****************************************************************************/

package org.eclipse.papyrus.example.uml.nattable.empty.line.providers;

import org.eclipse.papyrus.example.uml.nattable.empty.line.Activator;
import org.eclipse.papyrus.infra.emf.nattable.provider.AbstractEmptyLineRowHeaderLabelProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;

/**
 * @author Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>
 *
 */
public class CustomRowHeaderLabelProvider extends AbstractEmptyLineRowHeaderLabelProvider {

	/**
	 * Constructor.
	 *
	 * @param tableKind
	 */
	public CustomRowHeaderLabelProvider() {
		super(Activator.TABLE_TYPE);
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.AbstractEmptyLineRowHeaderLabelProvider#getCreationHeaderMessage(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration)
	 *
	 * @param configuration
	 * @return
	 */
	@Override
	protected String getCreationHeaderMessage(final TreeFillingConfiguration configuration) {
		if (configuration.getDepth() == 0) {// a check about the listen feature and the filter expression will be great in a real usage
			return "Create a new class"; //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

}
