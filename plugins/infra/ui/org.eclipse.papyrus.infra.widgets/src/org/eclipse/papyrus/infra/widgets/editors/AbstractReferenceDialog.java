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
package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This class allow to define a reference value editor.
 */
public abstract class AbstractReferenceDialog extends AbstractValueEditor implements IReferenceValueEditor {

	/**
	 * Boolean to detect direct creation.
	 */
	protected boolean directCreation;

	/**
	 * Indicates whether the widget requires a value or not. If it is mandatory,
	 * it cannot delete/unset its value
	 */
	protected boolean mandatory;

	/**
	 * Boolean to determinate if the editors are read-only.
	 */
	protected boolean readOnly;


	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 */
	protected AbstractReferenceDialog(final Composite parent) {
		super(parent);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style.
	 * @param label
	 *            The label.
	 */
	protected AbstractReferenceDialog(final Composite parent, final int style, final String label) {
		super(parent, style, label);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style.
	 */
	protected AbstractReferenceDialog(final Composite parent, final int style) {
		super(parent, style);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param label
	 *            The label.
	 */
	protected AbstractReferenceDialog(final Composite parent, final String label) {
		super(parent, label);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.IReferenceValueEditor#setContentProvider(org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider)
	 */
	@Override
	public abstract void setContentProvider(final IStaticContentProvider provider);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.IReferenceValueEditor#setLabelProvider(org.eclipse.jface.viewers.ILabelProvider)
	 */
	@Override
	public abstract void setLabelProvider(final ILabelProvider provider);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.IReferenceValueEditor#setValueFactory(org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory)
	 */
	@Override
	public abstract void setValueFactory(final ReferenceValueFactory factory);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.IReferenceValueEditor#updateControls()
	 */
	@Override
	public abstract void updateControls();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.IReferenceValueEditor#setDirectCreation(boolean)
	 */
	@Override
	public void setDirectCreation(final boolean directCreation) {
		this.directCreation = directCreation;
		updateControls();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.IReferenceValueEditor#setMandatory(boolean)
	 */
	@Override
	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#getValue()
	 */
	@Override
	public abstract Object getValue();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#getEditableType()
	 */
	@Override
	public Object getEditableType() {
		return Object.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(final boolean readOnly) {
		this.readOnly = readOnly;
		updateControls();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

}
