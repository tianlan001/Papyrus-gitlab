/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 516526
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.editors.AbstractMultipleValueEditor;
import org.eclipse.papyrus.infra.widgets.editors.ICommitListener;
import org.eclipse.papyrus.infra.widgets.editors.IMultipleReferenceEditor;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.swt.widgets.Composite;

/**
 * Partial implementation of a PropertyEditor for editing multiple references in some kind
 * of widget that can present values in list, tree, or tabular form..
 *
 * @author Camille Letavernier
 * @since 2.0
 */
public abstract class AbstractMultiReference<T extends AbstractMultipleValueEditor<? extends ReferenceSelector> & IMultipleReferenceEditor> extends AbstractPropertyEditor {

	/**
	 * The MultipleReferenceEditor widget
	 */
	protected T editor;

	/**
	 * The ReferenceValueFactory allowing creation and direct editing of values
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
	public AbstractMultiReference(Composite parent, int style) {
		editor = createMultipleReferenceEditor(parent, style);
		super.setEditor(editor);
	}

	/**
	 * Creates the reference editor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 * @return The reference editor
	 */
	protected abstract T createMultipleReferenceEditor(Composite parent, int style);

	@Override
	protected void doBinding() {
		IStaticContentProvider contentProvider = input.getContentProvider(propertyPath);
		ILabelProvider labelProvider = input.getLabelProvider(propertyPath);

		if (getInputObservableList() instanceof ICommitListener) {
			editor.addCommitListener((ICommitListener) getInputObservableList());
		}

		editor.setProviders(contentProvider, labelProvider);
		editor.setOrdered(input.isOrdered(propertyPath));
		editor.setUnique(input.isUnique(propertyPath));
		if (factory == null) {
			editor.setFactory(input.getValueFactory(propertyPath));
		} else {
			editor.setFactory(factory);
		}
		editor.setDirectCreation(input.getDirectCreation(propertyPath));

		super.doBinding();
	}

	/**
	 * Sets the {@link ReferenceValueFactory} for this Editor. The factory
	 * allows creation and direct edition of objects.
	 *
	 * @param factory
	 */
	public void setFactory(ReferenceValueFactory factory) {
		this.factory = factory;
		editor.setFactory(factory);
	}

	/**
	 * @return the {@link ReferenceValueFactory} used by this editor
	 */
	public ReferenceValueFactory getFactory() {
		return factory;
	}

}
