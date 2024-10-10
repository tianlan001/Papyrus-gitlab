/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
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
package org.eclipse.papyrus.sirius.properties.common.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.eef.common.ui.api.IEEFFormContainer;
import org.eclipse.eef.core.api.EEFPage;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.ide.ui.api.EEFTab;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.papyrus.sirius.properties.common.utils.SiriusInterpreterHelper;
import org.eclipse.sirius.common.interpreter.api.IEvaluationResult;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.sirius.properties.core.api.SiriusInputDescriptor;
import org.eclipse.sirius.ui.properties.internal.EditingContextAdapterWrapper;
import org.eclipse.sirius.ui.properties.internal.dialog.DialogFormContainer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.IMessageManager;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * The form dialog parameterized by several pages which show (editing)
 * properties of a given object.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@SuppressWarnings("restriction")
public class PropertiesFormDialog extends FormDialog {

	/**
	 * The interpreter used to evaluate AQL expressions.
	 */
	private IInterpreter interpreter;

	/**
	 * The variable manager which contain variables used by Interpreter to evaluate
	 * AQL expressions.
	 */
	private IVariableManager variableManager;

	/**
	 * The EEF pages displayed in tab of properties dialog.
	 */
	private List<EEFPage> eefPages;

	/**
	 * Tabs of the properties dialog.
	 */
	private List<EEFTab> eefTabs;

	/**
	 * The consumer used to refresh the dialog when a change is performed with
	 * session.
	 */
	private Consumer<IStatus> refreshConsumer;

	/**
	 * The consumer used to validate the dialog when a change is performed with
	 * session.
	 */
	private Consumer<IStatus> validateConsumer;

	/**
	 * Object with the properties to display in the dialog.
	 */
	private EObject target;

	/**
	 * Message manager used to display message in the dialog.
	 */
	private IMessageManager messageManager;

	/**
	 * A part of the title of the dialog. These are usually the "Create" and "Edit"
	 * actions for example.
	 */
	private String actionTitle;

	private SiriusInputDescriptor inputDescriptor;

	/**
	 * The constructor.
	 * 
	 * @param shell
	 *            the parent shell in which the dialog will be displayed
	 * @param currentObject
	 *            object with properties to display
	 * @param interpreter
	 *            the interpreter used to evaluate AQL expressions
	 * @param variableManager
	 *            the variable manager which contain variables used by
	 *            Interpreter to evaluate AQL expressions.
	 * @param eefPage
	 *            the EEF pages displayed in tab of properties dialog
	 * @param actionTitle
	 *            a part of the title of the dialog. These are usually
	 *            the "Create" and "Edit" actions
	 */
	public PropertiesFormDialog(Shell shell, EObject currentObject, IInterpreter interpreter,
			IVariableManager variableManager, List<EEFPage> eefPages) {
		super(shell);
		Assert.isNotNull(currentObject, "currentObject should not be null"); //$NON-NLS-1$
		Assert.isNotNull(interpreter, "interpreter should not be null"); //$NON-NLS-1$
		Assert.isNotNull(variableManager, "variableManager should not be null"); //$NON-NLS-1$
		Assert.isNotNull(eefPages, "eefPages should not be null"); //$NON-NLS-1$
		this.target = currentObject;
		this.interpreter = interpreter;
		this.variableManager = variableManager;
		this.eefPages = eefPages;
		this.eefTabs = new ArrayList<EEFTab>();
		this.actionTitle = ""; //$NON-NLS-1$
		this.inputDescriptor = new SiriusInputDescriptor(this.target);
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		SiriusInterpreterHelper.getInstance().clearValidationVariablesFromInterpreter(this.inputDescriptor);
		// set titles Window
		String name = this.target.eClass().getName();
		this.getShell().setText(this.actionTitle + name);

		// content of the window
		managedForm.getForm().setMessage(null, IMessageProvider.NONE);
		messageManager = managedForm.getMessageManager();
		managedForm.getMessageManager().setDecorationPosition(SWT.TOP | SWT.LEFT);
		Composite composite = managedForm.getForm().getForm().getBody();
		composite.setLayout(new FillLayout());
		int style = GridData.FILL_HORIZONTAL;
		GridData data = new GridData(style);
		composite.setLayoutData(data);

		// creation of tabs
		CTabFolder tabFolder = new CTabFolder(composite, SWT.BORDER);
		tabFolder.setMaximized(true);

		this.eefTabs = new ArrayList<>();
		CTabItem first = null;
		for (EEFPage eefPage : eefPages) {
			CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
			if (first == null) {
				first = tabItem;
			}

			// label of the tab
			IEvaluationResult evaluationResult = this.interpreter.evaluateExpression(
					this.variableManager.getVariables(), eefPage.getDescription().getLabelExpression());
			if (Diagnostic.OK == evaluationResult.getDiagnostic().getSeverity()
					&& evaluationResult.getValue() instanceof String) {
				String label = (String) evaluationResult.getValue();
				tabItem.setText(label);
			}

			// fill the tab with the eefPage
			Composite tabContentHolder = new Composite(tabFolder, SWT.NONE);
			tabContentHolder.setLayout(new GridLayout(1, false));

			FormToolkit toolkit = new FormToolkit(tabContentHolder.getDisplay());
			ScrolledForm sform = toolkit.createScrolledForm(tabContentHolder);
			sform.setLayoutData(new GridData(GridData.FILL_BOTH));
			IManagedForm managedFormTab = new ManagedForm(toolkit, sform);
			managedFormTab.getMessageManager().setDecorationPosition(SWT.TOP | SWT.LEFT);
			managedFormTab.getForm().setText(null);
			IEEFFormContainer eefContainer = new DialogFormContainer(this.getShell(),
					managedFormTab.getForm().getForm());
			Composite compositeTab = managedFormTab.getForm().getForm().getBody();
			tabContentHolder.setBackground(compositeTab.getBackground());

			FillLayout layout = new FillLayout();
			compositeTab.setLayout(layout);

			compositeTab.setLayoutData(data);
			sform.setExpandVertical(false);

			EEFTab eefTab = new EEFTab(eefPage);

			eefTab.createControls(compositeTab, eefContainer);
			eefTab.aboutToBeShown();
			eefTab.refresh();

			tabItem.setData(eefTab);
			tabItem.setControl(tabContentHolder);
			applyDialogFont(sform.getBody());
			// Reflow to false to allow scroll in form
			managedFormTab.getForm().reflow(false);
			eefTabs.add(eefTab);
		}

		managedForm.getForm().reflow(true);

		this.configureModelChangeRefresh();
	}

	@Override
	protected void initializeBounds() {
		// Resize the dialog to a correct size and location
		Point initialLocation = getInitialLocation(getInitialSize());
		Rectangle r = new Rectangle(initialLocation.x, initialLocation.y / 2, getShell().getBounds().width / 2,
				getShell().getBounds().height);
		getShell().setBounds(r);
	}

	/**
	 * Configures a consumer executed when
	 * {@link EditingContextAdapter#performModelChange(Runnable)} is called in order
	 * to refresh the dialog.
	 */
	private void configureModelChangeRefresh() {
		if (this.eefPages != null && this.eefPages.size() != 0) {
			EditingContextAdapter editingContextAdapter = this.eefPages.get(0).getView().getContextAdapter();
			if (editingContextAdapter instanceof EditingContextAdapterWrapper) {
				// session case
				EditingContextAdapterWrapper wrapper = (EditingContextAdapterWrapper) editingContextAdapter;
				this.refreshConsumer = (status) -> this.refreshDialog();
				wrapper.addPerformedModelChangeConsumer(this.refreshConsumer);
				this.validateConsumer = (status) -> this.validateTarget(this.target, this.messageManager, null);
				wrapper.addPerformedModelChangeConsumer(this.validateConsumer);
			}
		}
	}

	/**
	 * Refresh tabs of the dialog.
	 */
	private void refreshDialog() {
		for (EEFTab eefTab : eefTabs) {
			eefTab.refresh();
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (IDialogConstants.OK_ID == buttonId) {
			this.setReturnCode(OK);
			this.close();
		} else if (IDialogConstants.CANCEL_ID == buttonId) {
			this.setReturnCode(CANCEL);
			this.close();
		}
	}

	@Override
	public boolean close() {
		boolean result = super.close();
		SiriusInterpreterHelper.getInstance().clearValidationVariablesFromInterpreter(this.inputDescriptor);

		for (EEFPage eefPage : eefPages) {
			EditingContextAdapter editingContextAdapter = eefPage.getView().getContextAdapter();
			if (editingContextAdapter instanceof EditingContextAdapterWrapper) {
				// session case
				EditingContextAdapterWrapper wrapper = (EditingContextAdapterWrapper) editingContextAdapter;
				wrapper.removePerformedModelChangeConsumer(this.refreshConsumer);
				wrapper.removePerformedModelChangeConsumer(this.validateConsumer);
			}
		}

		for (EEFTab eefTab : eefTabs) {
			eefTab.aboutToBeHidden();
			eefTab.dispose();
		}

		return result;
	}

	/**
	 * Get list of tabs displayed.
	 */
	public List<EEFTab> getEefTabs() {
		return eefTabs;
	}

	/**
	 * Set the action title.
	 */
	public void setActionTitle(String actionTitle) {
		this.actionTitle = actionTitle;
	}

	/**
	 * Method used to launch validation on a semantic object and display error in a
	 * dialog by using its message manager.
	 * 
	 * @param eObject
	 *            semantic object with the properties to display in the dialog,
	 * @param manager
	 *            message manager of Dialog used to display error message,
	 * @param message
	 *            message to display when validation is ok.
	 */
	private void validateTarget(EObject eObject, IMessageManager manager, String message) {
		Diagnostician diagnostician = new Diagnostician() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.ecore.util.Diagnostician#doValidateContents(org.eclipse.emf.ecore.EObject,
			 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
			 */
			@Override
			protected boolean doValidateContents(EObject eObject, DiagnosticChain diagnostics,
					Map<Object, Object> context) {
				return true;
			}

		};
		Diagnostic validation = diagnostician.validate(eObject);
		int severity = IMessageProvider.NONE;
		if (validation.getSeverity() != Diagnostic.OK) {
			manager.removeAllMessages();
			for (Diagnostic diag : validation.getChildren()) {
				if (diag.getData().contains(eObject)) {
					if (diag.getSeverity() == Diagnostic.ERROR) {
						severity = IMessageProvider.ERROR;
					} else {
						severity = diag.getSeverity();
					}
					manager.addMessage(diag, diag.getMessage(), null, severity);
				}
			}
		} else {
			manager.removeAllMessages();
			manager.addMessage(eObject, message, null, severity);
		}
	}

}
