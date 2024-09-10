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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Vincent Lorenzo
 *
 */
public class CompletionStyledTextReferenceDialog extends AbstractPropertyEditor {

	/**
	 * The ReferenceDialog widget
	 */
	protected org.eclipse.papyrus.infra.widgets.editors.CompletionStyledTextReferenceDialog editor;

	/**
	 * The ValueFactory used to create or edit Objects directly from
	 * this editor
	 */
	protected ReferenceValueFactory factory;

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 */
	public CompletionStyledTextReferenceDialog(Composite parent, int style) {
		editor = createReferenceDialog(parent, style);
		setEditor(editor);
	}

	/**
	 * Creates the reference dialog.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 * @return the reference dialog.
	 */
	protected org.eclipse.papyrus.infra.widgets.editors.CompletionStyledTextReferenceDialog createReferenceDialog(Composite parent, int style) {
		return new org.eclipse.papyrus.infra.widgets.editors.CompletionStyledTextReferenceDialog(parent, style);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#doBinding()
	 *
	 */
	@Override
	protected void doBinding() {

		IStaticContentProvider provider = input.getContentProvider(propertyPath);
		editor.setLabelProvider(input.getLabelProvider(propertyPath));
		editor.setContentProvider(provider);
		editor.setDirectCreation(input.getDirectCreation(propertyPath));
		editor.setMandatory(input.isMandatory(propertyPath));
		if (factory == null) { // Use the default factory from the DataSource
			editor.setValueFactory(input.getValueFactory(propertyPath));
		} else { // Use the factory explicitly specified
			editor.setValueFactory(factory);
		}
		IPapyrusConverter parser = input.getPapyrusConverter(propertyPath);
		if (parser != null) {
			editor.setPapyrusConverter(parser);

		}
		super.doBinding();
	}

	/**
	 * Sets the ValueFactory used to create or edit Objects directly from
	 * this editor
	 *
	 * @param factory
	 */
	public void setFactory(ReferenceValueFactory factory) {
		this.factory = factory;
		editor.setValueFactory(factory);
	}

	/**
	 * @return The ValueFactory used to create or edit Objects directly from
	 *         this editor
	 */
	public ReferenceValueFactory getFactory() {
		return factory;
	}

}
