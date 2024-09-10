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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.tests;

/**
 * This allows to manage the paste with categories for the single column.
 */
public class AbstractPasteWithCategoriesSingleColumnAttachedModeTests extends AbstractPasteWithCategoriesTests {

	/**
	 * The paste folder paste for the single column and for the attached mode.
	 */
	public static final String ATTACHED_MODE_SINGLE_COLUMN = "attachedMode/single_column/"; //$NON-NLS-1$
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.tests.AbstractPasteWithCategoriesTests#getSourcePath()
	 */
	@Override
	protected String getSourcePath() {
		return new StringBuilder(super.getSourcePath()).append(ATTACHED_MODE_SINGLE_COLUMN).toString();
	}
	
}
