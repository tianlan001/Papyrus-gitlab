/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.papyrus.infra.widgets.editors.CompletionStyledTextMultipleReferenceEditor;
import org.eclipse.papyrus.infra.widgets.editors.MultipleReferenceEditor;
import org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter;
import org.eclipse.swt.widgets.Composite;

/**
 * A PropertyEditor for editing multiple references in a List
 *
 * @see org.eclipse.papyrus.widgets.editors.BooleanCheckbox
 *
 * @author Camille Letavernier
 */
public class CompletionStyledTextMultiReference extends MultiReference {

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 */
	public CompletionStyledTextMultiReference(Composite parent, int style) {
		super(parent, style);
	}


	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.MultiReference#createMultipleReferenceEditor(org.eclipse.swt.widgets.Composite, int)
	 *
	 * @param parent
	 * @param style
	 * @return
	 */
	@Override
	protected MultipleReferenceEditor createMultipleReferenceEditor(Composite parent, int style) {
		return new CompletionStyledTextMultipleReferenceEditor(parent, style);
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.MultiReference#doBinding()
	 *
	 */
	@Override
	protected void doBinding() {
		if (editor instanceof ISetPapyrusConverter) {
			((ISetPapyrusConverter) editor).setPapyrusConverter(input.getPapyrusConverter(propertyPath));
		}
		super.doBinding();
	}


}
