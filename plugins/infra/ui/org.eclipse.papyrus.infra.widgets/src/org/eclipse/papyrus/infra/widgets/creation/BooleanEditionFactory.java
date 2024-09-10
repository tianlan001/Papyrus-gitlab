/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST and others.
 *
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
 *  Christian W. Damus (CEA) - bug 402525
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.creation;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.papyrus.infra.widgets.validator.BooleanInputValidator;
import org.eclipse.swt.widgets.Control;

/**
 * The factory for the boolean
 *
 * @author Vincent Lorenzo
 *
 */
public class BooleanEditionFactory extends StringEditionFactory {
	/**
	 *
	 * Constructor.
	 *
	 */
	public BooleanEditionFactory() {
		this(new BooleanInputValidator());
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param validator
	 *            The InputValidator used to check the entered String
	 */
	public BooleanEditionFactory(IInputValidator validator) {
		super(validator);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param title
	 *            The title of the dialog opened by this factory when editing a Boolean
	 * @param label
	 *            The Label used to describe the kind of value being edited
	 * @param validator
	 *            The validator used to check the Booleans being edited
	 */
	public BooleanEditionFactory(String title, String label, IInputValidator validator) {
		super(title, label, validator);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param title
	 *            The title of the dialog opened by this factory when editing a Boolean
	 * @param label
	 *            The Label used to describe the kind of value being edited
	 */
	public BooleanEditionFactory(String title, String label) {
		super(title, label, new BooleanInputValidator());
	}

	@Override
	public Object createObject(Control widget, Object context) {
		String txt = super.createObject(widget, context).toString();
		if (txt != null) {
			return Boolean.parseBoolean(txt);
		}
		return null;
	}

}
