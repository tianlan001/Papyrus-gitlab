/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
package org.eclipse.papyrus.uml.properties.xtext.widget.property;

import org.eclipse.papyrus.uml.properties.preferences.MultiplicityEditorPreferences;
import org.eclipse.papyrus.uml.properties.widgets.MultiplicityDialog;
import org.eclipse.papyrus.uml.properties.xtext.widget.MultiplicityXTextValueEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * The multiplicity dialog which allow to manage the XText editors for the multiplicity lower and upper values.
 */
public class MultiplicityXTextDialog extends MultiplicityDialog {

	/**
	 * The lower value direct editor configuration name class.
	 */
	private String lowerValueDirectEditorConfiguration = null;

	/**
	 * The lower value direct editor configuration name class.
	 */
	private String upperValueDirectEditorConfiguration = null;


	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed.
	 * @param style
	 *            The style for the widget.
	 */
	public MultiplicityXTextDialog(final Composite parent, final int style) {
		super(parent, style);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.widgets.MultiplicityDialog#createMultiplicityDialog(org.eclipse.swt.widgets.Composite, int)
	 */
	@Override
	protected org.eclipse.papyrus.infra.widgets.editors.MultiplicityDialog createMultiplicityDialog(final Composite parent, final int style) {
		return editor = new MultiplicityXTextValueEditor(parent, style, MultiplicityEditorPreferences.instance.getPreferenceStore());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.widgets.MultiplicityDialog#doBinding()
	 */
	@Override
	protected void doBinding() {
		super.doBinding();
		if (null != editor && null != getLowerValueDirectEditorConfiguration()) {
			((MultiplicityXTextValueEditor) editor).setLowerValueDirectEditorConfiguration(getLowerValueDirectEditorConfiguration());
		}
		if (null != editor && null != getUpperValueDirectEditorConfiguration()) {
			((MultiplicityXTextValueEditor) editor).setUpperValueDirectEditorConfiguration(getUpperValueDirectEditorConfiguration());
		}
	}

	/**
	 * Sets the lower value editor configuration attribute from XWT file.
	 * 
	 * @param lowerValueDirectEditorConfiguration
	 *            The lower value direct editor configuration class name.
	 */
	public void setLowerValueDirectEditorConfiguration(final String lowerValueDirectEditorConfiguration) {
		this.lowerValueDirectEditorConfiguration = lowerValueDirectEditorConfiguration;
		if (null != editor && null != lowerValueDirectEditorConfiguration) {
			((MultiplicityXTextValueEditor) editor).setLowerValueDirectEditorConfiguration(lowerValueDirectEditorConfiguration);
		}
	}

	/**
	 * Gets the lower value direct editor configuration attribute value.
	 * 
	 * @return The lower value direct editor configuration attribute value.
	 */
	public String getLowerValueDirectEditorConfiguration() {
		return lowerValueDirectEditorConfiguration;
	}

	/**
	 * Sets the lower value editor configuration attribute from XWT file.
	 * 
	 * @param upperValueDirectEditorConfiguration
	 *            The lower value direct editor configuration class name.
	 */
	public void setUpperValueDirectEditorConfiguration(final String upperValueDirectEditorConfiguration) {
		this.upperValueDirectEditorConfiguration = upperValueDirectEditorConfiguration;
		if (null != editor && null != upperValueDirectEditorConfiguration) {
			((MultiplicityXTextValueEditor) editor).setUpperValueDirectEditorConfiguration(upperValueDirectEditorConfiguration);
		}
	}

	/**
	 * Gets the upper value direct editor configuration attribute value.
	 * 
	 * @return The upper value direct editor configuration attribute value.
	 */
	public String getUpperValueDirectEditorConfiguration() {
		return upperValueDirectEditorConfiguration;
	}
}
