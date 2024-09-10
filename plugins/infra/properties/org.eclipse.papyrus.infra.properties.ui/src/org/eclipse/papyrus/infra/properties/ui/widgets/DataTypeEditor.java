/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.papyrus.infra.ui.emf.dialog.EObjectTreeReferenceValueEditor;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.editors.TreeReferenceValueEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * This class is used to create a Property Editor based on the DataType.
 * @since 2.0
 */
public class DataTypeEditor extends AbstractPropertyEditor {

	/**
	 * The TreeReferenceDialog widget
	 */
	protected TreeReferenceValueEditor editor;

	/**
	 * The ValueFactory used to create or edit Objects directly from this editor.
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
	public DataTypeEditor(final Composite parent, final int style) {
		editor = createTreeReferenceDialog(parent, style);
		setEditor(editor);
	}

	/**
	 * Creates the tree reference dialog.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 * @return the tree reference dialog.
	 */
	protected TreeReferenceValueEditor createTreeReferenceDialog(final Composite parent, final int style) {
		return new EObjectTreeReferenceValueEditor(parent, style);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#doBinding()
	 */
	@Override
	protected void doBinding() {
		super.doBinding();

		editor.setLabelProvider(input.getLabelProvider(propertyPath));
		editor.setProvidersTreeViewer();
		if (factory == null) { // Use the default factory from the DataSource
			factory = input.getValueFactory(propertyPath);
		}
		editor.setValueFactory(factory);
	}

	/**
	 * Sets the ValueFactory used to create or edit Objects directly from this editor.
	 *
	 * @param factory
	 *            The {@link ReferenceValueFactory}.
	 */
	public void setFactory(final ReferenceValueFactory factory) {
		this.factory = factory;
		editor.setValueFactory(factory);
	}

	/**
	 * Get the ValueFactory used to create or edit Objects directly from this editor.
	 * 
	 * @return The ValueFactory used to create or edit Objects directly from this editor.
	 */
	public ReferenceValueFactory getFactory() {
		return factory;
	}
}
