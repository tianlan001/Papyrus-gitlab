/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 515967
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.providers.XWTCompliantMaskProvider;
import org.eclipse.papyrus.infra.properties.ui.providers.XWTCompliantMaskProviderListener;
import org.eclipse.papyrus.infra.properties.ui.providers.XWTCompliantMaskProviderUpdater;
import org.eclipse.swt.widgets.Composite;

/**
 * A widget to edit mask-based Integer values
 * The integer value is interpreted as a list of boolean values
 * The widget cannot use more than 32 masks
 *
 * @author Camille Letavernier
 */
public class StringMask extends AbstractPropertyEditor implements XWTCompliantMaskProviderListener {

	private org.eclipse.papyrus.infra.widgets.editors.StringMask editor;

	private XWTCompliantMaskProvider maskProvider;

	private boolean maskProviderReady = false;

	/**
	 * The change listener to update the editor checkboxes when needed.
	 */
	private IChangeListener changeListener;

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this widget will be created
	 * @param style
	 */
	public StringMask(Composite parent, int style) {
		editor = createStringMask(parent, style);
		setEditor(editor);
	}

	/**
	 * Creates the integer mask.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 * @return the integer mask.
	 */
	protected org.eclipse.papyrus.infra.widgets.editors.StringMask createStringMask(Composite parent, int style) {
		return new org.eclipse.papyrus.infra.widgets.editors.StringMask(parent, style);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#checkInput()
	 */
	@Override
	protected void checkInput() {
		if (maskProvider != null && maskProviderReady) {
			super.checkInput();
		}
	}

	/**
	 * @return the number of columns for this editor
	 *
	 */
	public int getNumColumns() {
		return editor.getNumColumns();
	}

	/**
	 * Sets the number of columns for this editor. The mask checkboxes will be
	 * distributed according to this number
	 *
	 * @param numColumns
	 */
	public void setNumColumns(int numColumns) {
		editor.setNumColumns(numColumns);
	}

	/**
	 * Sets the MaskProvider for this editor
	 *
	 * @param provider
	 */
	public void setMaskProvider(XWTCompliantMaskProvider provider) {
		if (this.maskProvider != null) {
			maskProvider.removeMaskProviderListener(this);
		}

		maskProviderReady = false;
		this.maskProvider = provider;
		provider.addMaskProviderListener(this);
		checkInput();
	}

	/**
	 *
	 * @return the MaskProvider associated to this editor
	 */
	public XWTCompliantMaskProvider getMaskProvider() {
		return maskProvider;
	}

	@Override
	public void notifyReady(XWTCompliantMaskProvider provider) {
		this.maskProviderReady = true;
		editor.setMasks(maskProvider.getMasks());
		provider.removeMaskProviderListener(this);
		checkInput();
	}

	/**
	 * Manage the change listener for the input.
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#setInput(org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource)
	 */
	@Override
	public void setInput(final DataSource input) {
		final DataSource oldInput = this.input;

		super.setInput(input);

		if (input != oldInput) {
			if (oldInput != null) {
				oldInput.removeChangeListener(getChangeListener());
			}

			if (input != null) {
				input.addChangeListener(getChangeListener());
			}
		}
	}

	/**
	 * This allows to create the change listener which allow to update the checkboxes of the editor.
	 *
	 * @return The created change listener.
	 * @since 3.4
	 */
	protected IChangeListener getChangeListener() {
		if (changeListener == null) {
			changeListener = new IChangeListener() {

				@Override
				public void handleChange(ChangeEvent event) {
					if (null != maskProvider) {
						if (maskProvider instanceof XWTCompliantMaskProviderUpdater) {
							((XWTCompliantMaskProviderUpdater) maskProvider).setInput(getInput());
						}

						if (null != editor) {
							editor.setMasks(maskProvider.getMasks());
						}
					}
				}
			};
		}
		return changeListener;
	}

}
