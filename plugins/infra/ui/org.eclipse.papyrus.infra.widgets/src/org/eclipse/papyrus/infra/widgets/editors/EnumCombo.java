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
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 475369
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.swt.widgets.Composite;

/**
 * An editor representing an Enumeration as a Combo
 * This Editor needs a ContentProvider describing the Enumerated values,
 * and an optional label provider
 *
 * @author Camille Letavernier
 */
public class EnumCombo extends ReferenceCombo {

	/**
	 *
	 * Constructs an editor for an Enumeration. The widget is a CCombo.
	 *
	 * @param parent
	 *            The composite is which this editor is created
	 * @param style
	 *            The CCombo's style
	 */
	public EnumCombo(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 *
	 * Constructs an editor for an Enumeration. The widget is a CCombo.
	 *
	 * @param parent
	 *            The composite is which this editor is created
	 * @param style
	 *            The CCombo's style
	 * @param label
	 *            The editor's label
	 */
	public EnumCombo(Composite parent, int style, String label) {
		super(parent, style, label);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style used.
	 * @param label
	 *            The initial label.
	 * @param commitOnFocusLost
	 *            Determinate if the focus lost ill commit the value or not.
	 * @since 3.3
	 */
	public EnumCombo(final Composite parent, final int style, final String label, final boolean commitOnFocusLost) {
		super(parent, style, label, commitOnFocusLost);
	}
}
