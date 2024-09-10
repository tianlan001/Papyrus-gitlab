/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 482443
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.nattable.common.Activator;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.nattable.common.wizards.CreateNattableFromCatalogWizard;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForHandlers;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Handler to display the wizard to display the catalog of existing Nattable
 * configurations.
 */
public class CreateNatTableFromCatalogHandler extends AbstractHandler {



	// OLD code before ViewPrototype

	// /**
	// * We open the dialog, request the user for the desired configuration
	// *
	// * @see org.eclipse.papyrus.infra.nattable.common.handlers.AbstractCreateNattableEditorHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	// *
	// * @param event
	// * @return
	// * @throws ExecutionException
	// */
	// @Override
	// public Object execute(ExecutionEvent event) throws ExecutionException {
	// final EObject context = getSelection().get(0);
	// CreateNattableFromCatalogWizard wizard = new CreateNattableFromCatalogWizard(context);
	// WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
	// ServicesRegistry serviceRegistry = null;
	// TransactionalEditingDomain domain = null;
	// try {
	// serviceRegistry = ServiceUtilsForHandlers.getInstance().getServiceRegistry(event);
	// domain = ServiceUtils.getInstance().getTransactionalEditingDomain(serviceRegistry);
	// } catch (ServiceException e) {
	// Activator.log.error(e);
	// return null;
	// }
	//
	// if (dialog.open() == Window.OK) {
	// CompoundCommand compoundCommand = new CompoundCommand("Create tables from Catalog"); //$NON-NLS-1$
	//
	// for (TableConfiguration tableConfiguration : wizard.getSelectedConfig().keySet()) {
	// CreateNatTableEditorHandler handler = new CreateNatTableEditorHandler();
	// handler.setType(tableConfiguration.getType());
	//
	// // See how many tables were required for this type
	// Integer tablesQuantity = wizard.getSelectedConfig().get(tableConfiguration);
	//
	// // Get the chosen name for the tables under this configuration
	// final String tableConfigName = wizard.getTableNames().get(tableConfiguration);
	// for (int i = 0; i < tablesQuantity; i++) {
	// try {
	// Command cmd = handler.getCreateNattableEditorCommandWithNameInitialization(domain, serviceRegistry, event, tableConfigName + "_" + i);//$NON-NLS-1$
	// compoundCommand.append(cmd);
	// } catch (ServiceException e) {
	// Activator.log.error(e);
	// }
	// }
	// }
	// if (!compoundCommand.isEmpty()) {
	// domain.getCommandStack().execute(compoundCommand);
	// }
	//
	// }
	// return null;
	// }

	/**
	 * The view prototypes to create by the table creation wizard.
	 */
	private Map<ViewPrototype, Integer> viewPrototypes = null;

	/**
	 * The view prototypes names of the table to create (names from wizard).
	 */
	private Map<ViewPrototype, String> tableNames = null;

	// NEW CODE using ViewPrototype
	/**
	 * We open the dialog, request the user for the desired configuration.
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.nattable.common.handlers.AbstractCreateNattableEditorHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final EObject context = getSelection().get(0);
		final CreateNattableFromCatalogWizard wizard = new CreateNattableFromCatalogWizard(context) {

			@Override
			public void dispose() {
				// Get the view prototypes and the table names before the dispose values
				// Create a new hashmap because the reference must be deleted with the dispose
				if (this.getSelectedViewPrototypes() != null && this.getTableNames() != null) {
					viewPrototypes = new HashMap<ViewPrototype, Integer>(this.getSelectedViewPrototypes());
					tableNames = new HashMap<ViewPrototype, String>(this.getTableNames());
				}
				super.dispose();
			}
		};
		final WizardDialog dialog = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
		ServicesRegistry serviceRegistry = null;
		TransactionalEditingDomain domain = null;
		try {
			serviceRegistry = ServiceUtilsForHandlers.getInstance().getServiceRegistry(event);
			domain = ServiceUtils.getInstance().getTransactionalEditingDomain(serviceRegistry);
		} catch (ServiceException e) {
			Activator.log.error(e);
			return null;
		}

		if (Window.OK == dialog.open()) {

			final RecordingCommand rc = new RecordingCommand(domain) {

				@Override
				protected void doExecute() {
					for (final ViewPrototype viewPrototype : viewPrototypes.keySet()) {
						if (null != viewPrototype) {
							// See how many tables were required for this type
							final Integer tablesQuantity = viewPrototypes.get(viewPrototype);

							// Get the chosen name for the tables under this configuration
							final String tableConfigName = null != tableNames.get(viewPrototype) && !tableNames.get(viewPrototype).isEmpty() ? tableNames.get(viewPrototype) : viewPrototype.getLabel();
							for (int i = 0; i < tablesQuantity; i++) {
								final String tableNameToCreate = 1 < tablesQuantity ? tableConfigName + "_" + i : tableConfigName; //$NON-NLS-1$
								// TODO : The following code line must be replaced by TableEditorCreationHelper.getTableConfigurationURI when the API for table creation is merged
								PolicyDefinedTableHandler handler = new PolicyDefinedTableHandler(getTableConfiguration((TableViewPrototype) viewPrototype), context, tableNameToCreate);
								handler.execute(viewPrototype);
							}
						}

					}
					// Re-initialize the global variables because we don't need it after
					viewPrototypes = null;
					tableNames = null;
				}
			};
			domain.getCommandStack().execute(rc);
		}
		return null;
	}

	/**
	 * TODO : To remove when the API for table creation is merged
	 *
	 * @param viewPrototype
	 *            a view {@link TableViewPrototype}, must not be <code>null</code>
	 * @return
	 * 		a TableConfiguration, or <code>null</code> if not found
	 */
	private TableConfiguration getTableConfiguration(final TableViewPrototype viewPrototype) {
		if (viewPrototype.getRepresentationKind() instanceof PapyrusTable) {
			PapyrusTable papyrusTable = (PapyrusTable) viewPrototype.getRepresentationKind();
			return papyrusTable.getConfiguration();
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.common.handlers.CreateNatTableEditorHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		setBaseEnabled(getSelection().size() == 1);
	}

	/**
	 *
	 * @return
	 */
	protected List<EObject> getSelection() {
		final List<EObject> selectedElements = new ArrayList<EObject>();
		final IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (ww != null) {
			final ISelection selection = ww.getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				final Iterator<?> it = structuredSelection.iterator();
				while (it.hasNext()) {
					final Object object = it.next();
					final EObject currentEObject = EMFHelper.getEObject(object);
					if (currentEObject != null) {
						selectedElements.add(currentEObject);
					}
				}
			}
		}
		return selectedElements;
	}

}
