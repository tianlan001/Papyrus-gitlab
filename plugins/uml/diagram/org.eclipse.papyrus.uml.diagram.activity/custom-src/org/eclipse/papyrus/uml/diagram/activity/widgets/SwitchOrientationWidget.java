/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.widgets;

import java.io.IOException;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor;
import org.eclipse.papyrus.uml.diagram.activity.part.CustomMessages;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.common.ui.helper.HelpComponentFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

/**
 * The switch orientation widget with a simple button and a help button.
 *
 * @since 3.2
 */
public class SwitchOrientationWidget extends AbstractValueEditor {

	/** The path to the switch icon. */
	private static final String ICON_PATH = "icons/switchSegmentOrientation.gif";

	/** The switch image. */
	public static Image switchImage = null;

	/** Load the switch icon once. */
	static {
		try {
			switchImage = new Image(Display.getDefault(), UMLDiagramEditorPlugin.getInstance().getBundle().getResource(ICON_PATH).openStream());
		} catch (IOException e) {
			UMLDiagramEditorPlugin.getInstance().logError(e.getMessage(), e);
		}
	}

	/**
	 * The button to use.
	 */
	private Button button;


	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed.
	 * @param style
	 *            The style for this editor's text box.
	 */
	public SwitchOrientationWidget(final Composite parent, final int style) {
		this(parent, style, null); // $NON-NLS-1$
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which this editor should be displayed.
	 * @param style
	 *            The style for this editor's text box.
	 * @param label
	 *            The label to be displayed.
	 */
	public SwitchOrientationWidget(final Composite parent, final int style, final String label) {
		super(parent, label);

		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));

		button = new Button(composite, SWT.PUSH);
		button.setImage(switchImage);
		GridData gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		button.setLayoutData(gd);

		button.addSelectionListener(new SelectionListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}

			/**
			 * {@inheritDoc}
			 */
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (null != widgetObservable) {
					widgetObservable.setValue(true);
				}
			}
		});

		// create help
		final ImageHyperlink help = HelpComponentFactory.createHelpComponent(composite, new FormToolkit(parent.getDisplay()), CustomMessages.ForkJoinSegmentSwitchOrientation_helpMessage);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		help.setLayoutData(gd);
		help.setBackground(parent.getBackground());

		pack();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#setLabel(java.lang.String)
	 */
	@Override
	public void setLabel(final String label) {
		// Don't display label
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#setModelObservable(org.eclipse.core.databinding.observable.value.IObservableValue)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setModelObservable(final IObservableValue modelProperty) {
		IObservableValue newWidgetObservable = modelProperty;

		if (this.widgetObservable != null) {
			this.widgetObservable.dispose();
		}

		setWidgetObservable(newWidgetObservable, true);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#getValue()
	 */
	@Override
	public Object getValue() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#getEditableType()
	 */
	@Override
	public Object getEditableType() {
		return Boolean.class;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		button.setEnabled(!readOnly);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return !button.isEnabled();
	}

}
