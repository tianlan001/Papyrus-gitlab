/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
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
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.editablereference.internal;

import java.util.ArrayList;

import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.ext.widgets.reference.internal.EEFExtReferenceController;
import org.eclipse.eef.ide.ui.api.widgets.IEEFLifecycleManager;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.sirius.properties.common.dialog.PropertiesUtils;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;

/**
 * The controller of the EEF Extension editable reference widget is used to
 * manage the behavior of the reference widget on click event.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
@SuppressWarnings("restriction")
public class EditableReferenceController extends EEFExtReferenceController {

	/**
	 * The LifecycleManager of the widget to control. It is mainly used to refresh
	 * the widget.
	 */
	private IEEFLifecycleManager lifecycleManager;

	/**
	 * Utils used to open Properties Dialog.
	 */
	private PropertiesUtils propertiesUtils;

	/**
	 * The enablement of the widget : <code>true</code> when the widget should have
	 * its default behavior, <code>false</code> when the widget should be in a read
	 * only mode.
	 */
	private boolean isEnabled;

	/**
	 * Constructor.
	 * 
	 * @param lifecycleManager
	 *            the LifecycleManager of the widget to control
	 * @param description
	 *            the reference description to manage
	 * @param variableManager
	 *            the variable manager which contain variables
	 *            used by Interpreter to evaluate AQL expression
	 * @param interpreter
	 *            the interpreter to evaluate AQL expressions
	 * @param editingContextAdapter
	 *            the adapter used to modify model elements
	 * @param isEnabled
	 *            the enablement of the widget
	 */
	public EditableReferenceController(IEEFLifecycleManager lifecycleManager,
			EEFExtEditableReferenceDescription description, IVariableManager variableManager, IInterpreter interpreter,
			EditingContextAdapter editingContextAdapter, boolean isEnabled) {
		super(description, variableManager, interpreter, editingContextAdapter);
		this.lifecycleManager = lifecycleManager;
		this.propertiesUtils = new PropertiesUtils();
		this.isEnabled = isEnabled;
	}

	@Override
	protected EEFExtEditableReferenceDescription getDescription() {
		return (EEFExtEditableReferenceDescription) super.getDescription();
	}

	@Override
	public String getOnClickExpression() {
		return getDescription().getOnClickExpression();
	}


	/**
	 * Returns the Create expression.
	 *
	 * @return The Create expression
	 */
	public String getCreateExpression() {
		return getDescription().getCreateExpression();
	}

	/**
	 * Returns the Remove expression.
	 *
	 * @return The Remove expression
	 */
	public String getRemoveExpression() {
		return getDescription().getRemoveExpression();
	}

	/**
	 * Returns the Browse expression.
	 *
	 * @return The Browse expression
	 */
	public String getBrowseExpression() {
		return getDescription().getBrowseExpression();
	}

	@Override
	public void onClick(Object element, String onClickEventKind) {
		if (EEFExpressionUtils.EEFList.DOUBLE_CLICK.equals(onClickEventKind) && isEnabled) {
			this.editingContextAdapter.performModelChange(() -> {
				EObject target = null;
				if (element instanceof EObject) {
					target = (EObject) element;
				} else if (element instanceof ArrayList && !((ArrayList<?>) element).isEmpty()) {
					target = (EObject) ((ArrayList<?>) element).get(0);
				}
				if (target != null) {
					int returnCode = getPropertiesUtils().displayEditionProperties(this.editingContextAdapter, target,
							variableManager, interpreter);
					if (returnCode == Window.OK) {
						this.lifecycleManager.refresh();
					}
				}
			});
		}
	}

	/**
	 * Get the utils to open Properties Dialog.
	 * 
	 * @return the utils to open Properties Dialog
	 */
	public PropertiesUtils getPropertiesUtils() {
		return propertiesUtils;
	}

}
