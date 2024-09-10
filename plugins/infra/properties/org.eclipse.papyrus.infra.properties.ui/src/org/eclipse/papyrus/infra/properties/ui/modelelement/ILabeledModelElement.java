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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.modelelement;

/**
 * This allows to manage a custom label for the model elements.
 * This will be used for the internationalization in a first step.
 * 
 * @since 3.0
 */
public interface ILabeledModelElement {

	/**
	 * Get a custom label.
	 * 
	 * @param propertyPath
	 *            The property path of the element to manage.
	 * @return The string to display.
	 */
	public String getLabel(final String propertyPath);

}
