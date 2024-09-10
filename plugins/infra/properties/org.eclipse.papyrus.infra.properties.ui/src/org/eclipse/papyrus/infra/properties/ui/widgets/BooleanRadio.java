/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Esterel Technologies SAS and others.
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
 *  Sebastien Gabel (Esterel Technologies SAS) - Bug 497361 
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.papyrus.infra.widgets.editors.AbstractEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * A PropertyEditor for editing booleans with two Radio buttons
 *
 * @see org.eclipse.papyrus.infra.widgets.editors.BooleanRadio
 *
 * @author Camille Letavernier
 *
 */
public class BooleanRadio extends AbstractPropertyEditor {

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 */
	public BooleanRadio(Composite parent, int style) {
		super(new org.eclipse.papyrus.infra.widgets.editors.BooleanRadio(parent, style));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.eclipse.papyrus.infra.widgets.editors.BooleanRadio getEditor() {
		AbstractEditor editor = super.getEditor();
		if (editor instanceof org.eclipse.papyrus.infra.widgets.editors.BooleanRadio) {
			return (org.eclipse.papyrus.infra.widgets.editors.BooleanRadio) editor;
		}
		return null;
	}

	/**
	 * Replaces the <b>true</b> String by the text given in parameter.
	 * 
	 * @param label
	 *            The text to display instead of <em>true</em>.
	 */
	public void setTrueLabel(String label) {
		org.eclipse.papyrus.infra.widgets.editors.BooleanRadio editor = getEditor();
		if (editor != null) {
			editor.setTrueLabel(label);
		}
	}

	/**
	 * Gets the label associated to the <b>true</b> value.
	 * 
	 * @return Return the label associated to the true value.
	 */
	public String getTrueLabel() {
		org.eclipse.papyrus.infra.widgets.editors.BooleanRadio editor = getEditor();
		return getEditor() == null ? null : editor.getTrueLabel();
	}

	/**
	 * Replaces the <b>false</b> String by the text given in parameter.
	 * 
	 * @param label
	 *            The text to display instead of <em>false</em>.
	 */
	public void setFalseLabel(String label) {
		org.eclipse.papyrus.infra.widgets.editors.BooleanRadio editor = getEditor();
		if (editor != null) {
			editor.setFalseLabel(label);
		}
	}

	/**
	 * Gets the label associated to the <b>false</b> value.
	 * 
	 * @return Return the label associated to the false value.
	 */
	public String getFalseLabel() {
		org.eclipse.papyrus.infra.widgets.editors.BooleanRadio editor = getEditor();
		return getEditor() == null ? null : editor.getFalseLabel();
	}	
}
