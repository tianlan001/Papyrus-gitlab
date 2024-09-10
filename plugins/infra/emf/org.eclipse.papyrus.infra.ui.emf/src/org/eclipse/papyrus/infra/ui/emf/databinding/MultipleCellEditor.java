/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrus.infra.ui.emf.databinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.ui.emf.utils.Constants;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelector;
import org.eclipse.papyrus.infra.widgets.editors.MultipleValueSelectionDialog;
import org.eclipse.papyrus.infra.widgets.selectors.UnlimitedNaturalSelector;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A CellEditor uses to set multiple values of an object.
 * 
 * @since 2.0
 */
public class MultipleCellEditor extends DialogCellEditor {

	/**
	 * The object to edit.
	 */
	private Object context;

	/**
	 * The IElementSelector
	 */
	private IElementSelector selector;

	/**
	 * 
	 */
	private EStructuralFeature feature;

	/**
	 * Constructor.
	 *
	 * @param composite
	 * @param labelProvider
	 */
	public MultipleCellEditor(Composite composite, Object context, EStructuralFeature feature) {
		super(composite);
		this.context = context;
		this.feature = feature;

	}

	/**
	 * Set the IElementSelector.
	 * 
	 * @param selector
	 *            The IElementSelector.
	 */
	public void setSelector(IElementSelector selector) {
		this.selector = selector;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		MultipleValueSelectionDialog multipleValueSelectionDialog = new MultipleValueSelectionDialog(cellEditorWindow.getShell(), selector);
		multipleValueSelectionDialog.setContextElement(context);

		List<Object> result = null;

		if (context instanceof EObject) {
			Object value = ((EObject) context).eGet(this.feature);
			if ((null != value) && (value instanceof Collection)) {
				Collection<?> coll = (Collection<?>) value;
				if (!coll.isEmpty()) {
					multipleValueSelectionDialog.setInitialSelections(coll.toArray());
					result = new ArrayList<Object>(coll);
				}
			}
		}

		// Add a LabelProvider for the UnlimitedNatural values.
		if (selector instanceof UnlimitedNaturalSelector) {
			multipleValueSelectionDialog.setLabelProvider(new UnlimitedNaturalLabelProvider());
		}

		if (Window.OK == multipleValueSelectionDialog.open()) {
			result = Arrays.asList(multipleValueSelectionDialog.getResult());
		}

		return result;
	}

	/**
	 * 
	 * LabelProvider used to change the display of an UnlimitedNatural.
	 * 
	 * @since 2.0
	 */
	private class UnlimitedNaturalLabelProvider extends LabelProvider {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getText(Object element) {
			String text = element.toString();
			if (Constants.INFINITE_MINUS_ONE.equals(text)) {
				text = Constants.INFINITY_STAR;
			}

			return text;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Image getImage(Object element) {
			return null;
		}
	}

}
