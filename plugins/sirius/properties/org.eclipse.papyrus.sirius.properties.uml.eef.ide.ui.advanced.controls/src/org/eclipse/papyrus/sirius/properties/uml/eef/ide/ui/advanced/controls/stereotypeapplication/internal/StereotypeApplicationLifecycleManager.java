/*******************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.stereotypeapplication.internal;

import java.util.Optional;

import org.eclipse.eef.common.api.utils.Util;
import org.eclipse.eef.common.ui.api.EEFWidgetFactory;
import org.eclipse.eef.common.ui.api.IEEFFormContainer;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.utils.EvalFactory;
import org.eclipse.eef.ide.ui.api.widgets.IEEFLifecycleManager;
import org.eclipse.eef.ide.ui.internal.EEFIdeUiPlugin;
import org.eclipse.eef.ide.ui.internal.Icons;
import org.eclipse.eef.ide.ui.internal.Messages;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription;
import org.eclipse.papyrus.uml.properties.profile.ui.compositeforview.AppliedStereotypeCompositeWithView;
import org.eclipse.papyrus.uml.properties.widgets.StereotypePropertyEditor;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;

/**
 * This lifecycle manager is used to handle the Stereotype Application Widget
 * to display Stereotypes and manage them with Add/Remove buttons.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@SuppressWarnings("restriction")
public class StereotypeApplicationLifecycleManager implements IEEFLifecycleManager {

	/**
	 * The label.
	 */
	protected StyledText label;

	/**
	 * The help label.
	 */
	protected CLabel help;

	/**
	 * The description of the stereotype application.
	 */
	private EEFStereotypeApplicationDescription description;

	/**
	 * The interpreter.
	 */
	private IInterpreter interpreter;

	/**
	 * The variable manager.
	 */
	private IVariableManager variableManager;

	/**
	 * The editing context adapter.
	 */
	private EditingContextAdapter editingContextAdapter;

	/**
	 * The main composite of the widget, which contains the two parts widget.
	 */
	private Composite mainComposite;

	/**
	 * The main object managed by the widget.
	 */
	private Element target;

	/**
	 * The composite that displays the applied stereotypes.
	 */
	private AppliedStereotypeCompositeWithView stereotypeComposite;

	/**
	 * The listener on the help.
	 */
	private MouseTrackListener mouseTrackListener;

	/**
	 * The controller.
	 */
	private StereotypeApplicationController controller;

	/**
	 * Constructor.
	 *
	 * @param controlDescription
	 *            the description of the stereotype application.
	 * @param target
	 *            the object managed by the widget
	 * @param variableManager
	 *            the variable manager which contain variables used by
	 *            Interpreter to evaluate AQL expression
	 * @param interpreter
	 *            the interpreter to evaluate AQL expressions
	 * @param editingContextAdapter
	 *            the adapter used to modify model elements
	 */
	public StereotypeApplicationLifecycleManager(EEFStereotypeApplicationDescription controlDescription, Element target, IVariableManager variableManager, IInterpreter interpreter, EditingContextAdapter editingContextAdapter) {
		this.description = controlDescription;
		this.target = target;
		this.variableManager = variableManager;
		this.interpreter = interpreter;
		this.editingContextAdapter = editingContextAdapter;
	}

	@Override
	public void createControl(Composite parent, IEEFFormContainer formContainer) {
		EEFWidgetFactory widgetFactory = formContainer.getWidgetFactory();

		Composite composite = parent;
		composite = widgetFactory.createComposite(parent);
		int numColumn = 3;
		GridLayout layout = new GridLayout(numColumn, false);
		// As this composite is "invisible", it must not add border.
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);

		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.horizontalSpan = 1;
		composite.setLayoutData(layoutData);

		this.label = widgetFactory.createStyledText(composite, SWT.READ_ONLY);
		this.label.setEditable(false);
		this.label.setCaret(null);
		this.label.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
		this.label.setDoubleClickEnabled(false);
		this.label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		String labelExpression = Optional.ofNullable(this.description.getLabelExpression()).orElse(""); //$NON-NLS-1$
		if (!labelExpression.isEmpty()) {
			String result = (String) EvalFactory.of(this.interpreter, this.variableManager).evaluate(labelExpression);
			this.label.setText(result);
		}

		this.help = widgetFactory.createCLabel(composite, ""); //$NON-NLS-1$
		if (!Util.isBlank(this.description.getHelpExpression())) {
			this.help.setImage(EEFIdeUiPlugin.getPlugin().getImageRegistry().get(Icons.HELP));
			this.help.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
			this.help.setToolTipText(""); //$NON-NLS-1$
		}


		this.mainComposite = new Composite(composite, SWT.NONE);
		GridLayout mainLayout = new GridLayout(2, false);
		this.mainComposite.setLayout(mainLayout);
		GridData mainLayoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.mainComposite.setLayoutData(mainLayoutData);
		stereotypeComposite = new AppliedStereotypeCompositeWithView(this.mainComposite);
		stereotypeComposite.setElement(this.target);
		StereotypePropertyEditor propertyEditor = new StereotypePropertyEditor(this.mainComposite, SWT.NONE, stereotypeComposite);
		stereotypeComposite.setPropertySelectionChangeListener(propertyEditor);
		stereotypeComposite.createContent(this.mainComposite, org.eclipse.papyrus.infra.widgets.editors.AbstractEditor.factory);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.heightHint = 200;
		stereotypeComposite.setLayoutData(data);

		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.heightHint = 200;
		propertyEditor.setLayoutData(data);

		controller = new StereotypeApplicationController(description, variableManager, interpreter, editingContextAdapter);
	}

	@Override
	public void aboutToBeShown() {
		//
		this.getController().onNewHelp((value) -> {
			if (help != null && !help.isDisposed() && !(help.getText() != null && help.getText().equals(value))) {
				help.setToolTipText(Optional.ofNullable(value).orElse(Messages.AbstractEEFWidgetLifecycleManager_noDescriptionAvailable));
			}
		});

		if (this.help != null) {
			this.mouseTrackListener = new MouseTrackListener() {

				@Override
				public void mouseHover(MouseEvent e) {
					// Defer the computation of the help message when the user hovers the Help label
					getController().computeHelp();
				}

				@Override
				public void mouseExit(MouseEvent e) {
					// Nothing todo
				}

				@Override
				public void mouseEnter(MouseEvent e) {
					// Nothing todo
				}
			};
			this.help.addMouseTrackListener(mouseTrackListener);
		}
	}

	@Override
	public void refresh() {
		stereotypeComposite.refresh();
	}

	@Override
	public void aboutToBeHidden() {
		if (this.help != null && !this.help.isDisposed()) {
			this.help.removeMouseTrackListener(mouseTrackListener);
		}

		this.getController().removeNewLabelConsumer();
	}

	@Override
	public void dispose() {
	}

	/**
	 * Gets the controller for this widget.
	 * 
	 * @return the controller.
	 */
	public StereotypeApplicationController getController() {
		return controller;
	}
}
