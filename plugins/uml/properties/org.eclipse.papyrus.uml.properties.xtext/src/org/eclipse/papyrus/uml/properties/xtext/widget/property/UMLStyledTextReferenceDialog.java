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

import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.properties.xtext.widget.UMLXtextReferenceValueEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * An editor representing a single reference in a StyledText widget with
 * completion . A filtered selection dialog is used to edit the value. Also
 * offers support for unsetting the value. This Editor needs a ContentProvider,
 * and may use an optional LabelProvider, describing the objects that can be
 * referred by this property.
 */
public class UMLStyledTextReferenceDialog extends AbstractPropertyEditor {

	/**
	 * The ValueFactory used to create or edit Objects directly from this
	 * editor.
	 */
	protected ReferenceValueFactory factory;

	/**
	 * The SimpleTextReferenceDialog widget.
	 */
	protected UMLXtextReferenceValueEditor editor;

	/**
	 * The direct editor configuration class name.
	 */
	private String directEditorConfiguration;
	
	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed.
	 * @param style
	 *            The style for the widget.
	 */
	public UMLStyledTextReferenceDialog(final Composite parent, final int style) {
		setEditor(createReferenceDialog(parent, style));
	}

	/**
	 * Creates the reference dialog.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed.
	 * @param style
	 *            The style for the widget.
	 * @return The reference dialog.
	 */
	protected UMLXtextReferenceValueEditor createReferenceDialog(
			final Composite parent, final int style) {
		return editor = new UMLXtextReferenceValueEditor(parent, style);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#doBinding()
	 */
	@Override
	protected void doBinding() {
		IStaticContentProvider provider = input
				.getContentProvider(propertyPath);
		if(null != getDirectEditorConfiguration()){
			editor.setDirectEditorConfiguration(getDirectEditorConfiguration());
		}
		editor.setLabelProvider(input.getLabelProvider(propertyPath));
		editor.setContentProvider(provider);
		editor.setDirectCreation(input.getDirectCreation(propertyPath));
		editor.setMandatory(input.isMandatory(propertyPath));
		if (null == factory) {
			// Use the default factory from the DataSource
			editor.setValueFactory(input.getValueFactory(propertyPath));
		} else {
			// Use the factory explicitly specified
			editor.setValueFactory(factory);
		}

		super.doBinding();
	}

	/**
	 * Sets the ValueFactory used to create or edit Objects directly from this
	 * editor.
	 *
	 * @param factory
	 *            The reference value factory.
	 */
	public void setFactory(final ReferenceValueFactory factory) {
		this.factory = factory;
		editor.setValueFactory(factory);
	}

	/**
	 * Get the factory.
	 * 
	 * @return The ValueFactory used to create or edit Objects directly from
	 *         this editor.
	 */
	public ReferenceValueFactory getFactory() {
		return factory;
	}
	
	/**
	 * Sets the direct editor configuration class name from XWT file.
	 * 
	 * @param directEditorConfiguration The direct editor configuration class name value.
	 */
	public void setDirectEditorConfiguration(final String directEditorConfiguration) {
		this.directEditorConfiguration = directEditorConfiguration;
		if(null != editor){
			editor.setDirectEditorConfiguration(directEditorConfiguration);
		}
	}

	/**
	 * Gets the direct editor configuration class name value.
	 * 
	 * @return The direct editor configuration class name value.
	 */
	public String getDirectEditorConfiguration() {
		return directEditorConfiguration;
	}

}
