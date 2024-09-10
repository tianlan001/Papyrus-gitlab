/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Extends the Abstract Class
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.strategy.copy;

import java.util.Map;

import org.eclipse.papyrus.infra.gmfdiag.common.strategy.copy.AbstractCopyStrategy;
import org.eclipse.papyrus.uml.diagram.common.Activator;

/**
 * Copy all the content of a compartment into a Classifier
 */
public class AllContentCopyStrategy extends AbstractCopyStrategy {

	@Override
	public String getLabel() {
		return "AllContentCopyStrategy"; //$NON-NLS-1$
	}

	@Override
	public String getID() {
		return Activator.ID + ".AllContentCopyStrategy";// ".ClassifierToStructureCompDrop"; //$NON-NLS-1$
	}

	@Override
	public String getDescription() {
		return "Drops a classifier into the structure compartment of a class."; //$NON-NLS-1$
	}

	public String getCategoryID() {
		return "org.eclipse.papyrus.strategy.copy"; //$NON-NLS-1$
	}

	public String getCategoryLabel() {
		return "Select elements to copy"; //$NON-NLS-1$
	}

	public void setOptions(Map<String, Object> options) {
		// Nothing
	}


}
