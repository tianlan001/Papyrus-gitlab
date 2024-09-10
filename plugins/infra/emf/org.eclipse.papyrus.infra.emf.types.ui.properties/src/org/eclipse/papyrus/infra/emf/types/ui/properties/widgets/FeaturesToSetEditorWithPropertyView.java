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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.types.ui.properties.widgets;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferenceEditorWithPropertyView;
import org.eclipse.papyrus.infra.widgets.editors.MultipleReferenceEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * Multiple value editor for {@link StereotypeToApply}.
 */
public class FeaturesToSetEditorWithPropertyView extends MultiReferenceEditorWithPropertyView {

	/**
	 * Constructor.
	 */
	public FeaturesToSetEditorWithPropertyView(final Composite parent, final int style) {
		super(parent, style);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferenceEditorWithPropertyView#setModelObservable(org.eclipse.core.databinding.observable.list.IObservableList)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setModelObservable(final IObservableList modelObservable) {
		super.setModelObservable(modelObservable);

		// Select the first element
		if (!modelObservable.isEmpty()) {
			multiReferenceEditor.getViewer().setSelection(new StructuredSelection(modelObservable.get(0)));
		}
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.MultiReferenceEditorWithPropertyView#createMultipleReferenceEditor(int)
	 */
	@Override
	protected MultipleReferenceEditor createMultipleReferenceEditor(final int style) {
		return new FeaturesToSetMultipleEditor(this, style);

	}

}
