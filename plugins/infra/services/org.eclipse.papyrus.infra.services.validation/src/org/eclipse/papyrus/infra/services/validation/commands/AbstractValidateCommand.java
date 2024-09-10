/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - refactor for non-workspace abstraction of problem markers (CDO)
 *  Patrick Tessier (CEA LIST) refacor to add allowing adding validation specific to UML
 *  Christian W. Damus (CEA) - bug 432813
 *  Christian W. Damus - bugs 497379, 533676, 572532
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.validation.commands;

import static org.eclipse.papyrus.infra.ui.util.TransactionUIHelper.createPrivilegedRunnableWithProgress;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.papyrus.infra.emf.gmf.command.INonDirtying;
import org.eclipse.papyrus.infra.services.validation.IPapyrusDiagnostician;
import org.eclipse.papyrus.infra.services.validation.Messages;
import org.eclipse.papyrus.infra.services.validation.ValidationTool;
import org.eclipse.papyrus.infra.services.validation.ValidationUtils;
import org.eclipse.papyrus.infra.services.validation.internal.ValidationRegistry;
import org.eclipse.papyrus.infra.services.validation.internal.ValidationRegistry.HookType;
import org.eclipse.papyrus.infra.services.validation.preferences.PreferenceUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Abstract validation command. While being abstract (and refined by concrete validation commands)
 * it contains all validation related code.
 *
 *
 * @author Ansgar Radermacher (CEA LIST)
 */
public abstract class AbstractValidateCommand extends AbstractTransactionalCommand implements INonDirtying {

	final String modelValidationViewID = "org.eclipse.papyrus.views.validation.ModelValidationView"; //$NON-NLS-1$

	protected TransactionalEditingDomain domain;

	protected EObject selectedElement;

	/**
	 * Current diagnostic within a validation run
	 */
	protected Diagnostic diagnostic;

	protected IPapyrusDiagnostician diagnostician;

	protected boolean showUIfeedback;

	/**
	 * Creates a new ValidationCommand
	 *
	 * @param label
	 *            the command label
	 * @param domain
	 *            the editing domain
	 * @param selectedElement
	 *            the selected element
	 */
	public AbstractValidateCommand(String label, TransactionalEditingDomain domain, EObject selectedElement) {
		this(label, domain, selectedElement, null);
	}

	/**
	 * Creates a new ImportLibraryFromRepositoryCommand
	 *
	 * @param label
	 *            the command label
	 * @param domain
	 *            the editing domain
	 * @param selectedElement
	 *            the selected element
	 * @param diagnostician
	 *            a diagnostician adapted to a domain see {@link IPapyrusDiagnostician}
	 */
	public AbstractValidateCommand(String label, TransactionalEditingDomain domain, EObject selectedElement, IPapyrusDiagnostician diagnostician) {
		super(domain, label, Collections.EMPTY_LIST);
		this.domain = domain;
		this.selectedElement = selectedElement;
		this.diagnostician = diagnostician;
		this.showUIfeedback = true; // default is true;
	}

	/**
	 * don't use a progress monitor to show validation progress. This is quite useful
	 * for diagnostics that are executed on a (shallow) subtree and do not take much time.
	 */
	public void disableUIFeedback() {
		this.showUIfeedback = false;
	}

	/**
	 * @return
	 */
	public Diagnostic getDiagnostic() {
		return diagnostic;
	}

	/**
	 * @return The resource on which markers should be applied.
	 */
	protected Resource getValidationResource() {
		return ValidationUtils.getValidationResource(selectedElement);
	}

	protected void runValidation(final EObject validateElement) {
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		ValidationOperation runValidationWithProgress = new ValidationOperation(validateElement, this) {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				// bug 572532: The OCL environment and the diagnostician that uses it need to be initialized
				// on the same thread as performs validation (that being the Modal Context thread) because
				// since the 2021-03 release, OCL Pivot maintains the environments on a per-thread basis
				ValidationRegistry.executeHooks(selectedElement, HookType.BEFORE);
				if (diagnostician == null) {
					diagnostician = ValidationRegistry.getDiagnostician(selectedElement);
				}

				super.run(monitor);
			}
		};

		IRunnableWithProgress createMarkersWithProgress = new IRunnableWithProgress() {

			@Override
			public void run(final IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {
				try {
					handleDiagnostic(progressMonitor, diagnostic, validateElement, shell);
				} finally {
					progressMonitor.done();
					ValidationRegistry.executeHooks(selectedElement, HookType.AFTER);
				}
			}
		};

		createMarkersWithProgress = new ValidationTool(validateElement)
				.wrap(createMarkersWithProgress);

		try {
			// runs the operation, and shows progress.
			try {
				if (showUIfeedback) {
					// Share our current transaction with the modal context
					IRunnableWithProgress privileged = createPrivilegedRunnableWithProgress(
							getEditingDomain(), runValidationWithProgress);
					new ProgressMonitorDialog(shell).run(true, true, privileged);
				} else {
					runValidationWithProgress.run(new NullProgressMonitor());
				}
			} finally {
				diagnostic = runValidationWithProgress.getDiagnostics();
				runValidationWithProgress.dispose();
			}

			if (diagnostic != null) {
				int markersToCreate = diagnostic.getChildren().size();
				if ((markersToCreate > 0) && PreferenceUtils.getAutoShowValidation() && showUIfeedback) {
					// activate model view, if activated in configuration
					// IViewRegistry viewRegistry = PlatformUI.getWorkbench().getViewRegistry();
					// IViewDescriptor desc = viewRegistry.find(modelValidationViewID);
					if (PlatformUI.isWorkbenchRunning()) {
						IWorkbench workbench = PlatformUI.getWorkbench();
						if (workbench != null) {
							IWorkbenchWindow activeWindow = workbench.getActiveWorkbenchWindow();
							if (activeWindow != null) {
								IWorkbenchPage activePage = activeWindow.getActivePage();
								if (activePage != null) {
									activePage.showView(modelValidationViewID);
								}
							}
						}
					}
				}
				// don't fork this dialog, i.e. run it in the UI thread. This avoids that the diagrams are constantly refreshing *while*
				// markers/decorations are changing. This greatly enhances update performance. See also bug 400593
				if (showUIfeedback) {
					// Running in the same thread, we don't have to share the transaction
					new ProgressMonitorDialog(shell).run(false, true, createMarkersWithProgress);
				} else {
					createMarkersWithProgress.run(new NullProgressMonitor());
				}
			}
		} catch (Exception exception) {
			EMFEditUIPlugin.INSTANCE.log(exception);
		}
	}

	/**
	 * This simply executes the command.
	 */
	protected Diagnostic validate(IProgressMonitor progressMonitor, EObject validateElement) {
		int validationSteps = 0;
		for (Iterator<?> i = validateElement.eAllContents(); i.hasNext(); i.next()) {
			++validationSteps;
		}

		progressMonitor.beginTask("", validationSteps); //$NON-NLS-1$
		AdapterFactory adapterFactory = domain instanceof AdapterFactoryEditingDomain ? ((AdapterFactoryEditingDomain) domain).getAdapterFactory() : null;
		diagnostician.initialize(adapterFactory, progressMonitor);

		BasicDiagnostic diagnostic = diagnostician.createDefaultDiagnostic(validateElement);
		Map<Object, Object> context = diagnostician.createDefaultContext();

		progressMonitor.setTaskName(EMFEditUIPlugin.INSTANCE.getString("_UI_Validating_message", new Object[] { diagnostician.getObjectLabel(validateElement) })); //$NON-NLS-1$
		diagnostician.validate(validateElement, diagnostic, context);

		if (progressMonitor.isCanceled()) {
			return null;
		}
		return diagnostic;
	}




	protected void handleDiagnostic(IProgressMonitor monitor, Diagnostic diagnostic, final EObject validateElement, final Shell shell) {
		// Do not show a dialog, as in the original version since the user sees the result directly
		// in the model explorer
		Resource resource = getValidationResource();
		// final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		if (resource != null) {
			if (validateElement != null) {
				SubMonitor sub = SubMonitor.convert(monitor, 2);

				ValidationTool vt = new ValidationTool(validateElement, resource);
				int markersToCreate = diagnostic.getChildren().size();

				sub.beginTask(Messages.AbstractValidateCommand_DeleteExistingMarkers, 1);
				flushDisplayEvents(shell.getDisplay());

				vt.deleteSubMarkers(sub.newChild(1));

				monitor.setTaskName(String.format(Messages.AbstractValidateCommand_CreateNMarkers, markersToCreate));
				flushDisplayEvents(shell.getDisplay());

				vt.createMarkers(diagnostic, sub.newChild(1));

				sub.done();
			}
		}
	}

	protected void flushDisplayEvents(Display display) {
		while (display.readAndDispatch()) {
			;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canExecute() {
		return (selectedElement != null);
	}
}
