/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.wizards.template;

import org.eclipse.jface.viewers.ICheckStateProvider;

/**
 * This Provider is used to determine if the template is selected by default of not
 */
public class ModelTemplateCheckStateProvider implements ICheckStateProvider {

	/**
	 * @see org.eclipse.jface.viewers.ICheckStateProvider#isChecked(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean isChecked(Object element) {
		if (element instanceof ModelTemplateDescription) {
			ModelTemplateDescription description = (ModelTemplateDescription) element;
			return description.isSelectedByDefault();
		}
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICheckStateProvider#isGrayed(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean isGrayed(Object element) {
		return false;
	}

}
