/*****************************************************************************
 * Copyright (c) 2017 CEA, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Pierre GAUTIER (CEA LIST) - Initial API and implementation
 *	Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 521902, Bug 526304
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.papyrus.infra.widgets.selectors.BooleanSelector;
import org.eclipse.papyrus.infra.widgets.selectors.StringSelector;
import org.eclipse.swt.widgets.Composite;

/**
 * MultipleStringEditor with fixed type parameter to be used as MultipleStringEditor<?> as can its specified child classes can be.
 *
 * @since 3.1
 */
public class TypedMultipleStringEditor extends MultipleStringEditor<StringSelector> {

	/**
	 * Constructs an Editor for multiple strings values
	 * The widget is a List, with controls to move values up/down, add values and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 */
	public TypedMultipleStringEditor(final Composite parent, final int style) {
		super(parent, style, true);
	}

	/**
	 * Constructs an Editor for multiple strings values.
	 * The widget is a List, with controls to move values up/down, add values and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param directCreation
	 *            Indicates if the creation and modification are directed.
	 * @param directCreationWithTreeViewer
	 *            Indicates if the creation and modification are directed on the TreeViewer.
	 * @param style
	 *            The List's style
	 * 
	 * @since 3.1
	 */
	public TypedMultipleStringEditor(Composite parent, boolean directCreation, boolean directCreationWithTreeViewer, int style) {
		super(parent, style, new BooleanSelector(), directCreation, directCreationWithTreeViewer);
	}

}
