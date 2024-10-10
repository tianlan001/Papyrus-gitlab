/*******************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.services.internal;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.eef.properties.ui.api.IEEFTabDescriptor;
import org.eclipse.eef.properties.ui.api.IEEFTabDescriptorFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

/**
 * The {@link IEEFTabDescriptorFilter} used to hide tab from Properties view.
 * 
 * @author Jessy Mallet <jessy.mallet@obeo.fr>
 *
 */
public class EEFTabDescriptorFilter implements IEEFTabDescriptorFilter {

	/**
	 * Ids of the semantic tabs to hide.
	 */
	private static final Collection<String> HIDE_TAB_ID = Arrays.asList("property.tab.behaviors"); //$NON-NLS-1$

	@Override
	public boolean filter(IEEFTabDescriptor tabDescriptor, IWorkbenchPart part, ISelection selection) {
		return !HIDE_TAB_ID.contains(tabDescriptor.getId());
	}

}
