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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.provider;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectRowsCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.CheckedOperationHistory;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.command.ErrorTransactionalCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.Cell;
import org.eclipse.papyrus.infra.nattable.parsers.CSVParser;
import org.eclipse.papyrus.infra.nattable.paste.IValueSetter;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter;
import org.eclipse.ui.progress.UIJob;

/**
 * The paste command provider for the paste action in a table.
 */
public abstract class AbstractPasteEObjectInNattableCommandProvider implements PasteNattableCommandProvider {

	/**
	 * The paste action task label.
	 */
	protected static final String PASTE_ACTION_TASK_NAME = Messages.PasteEObjectAxisInTableCommandProvider_PasteAction;

	/**
	 * The paste rows job label.
	 */
	protected static final String PASTE_ROWS_JOB_NAME = Messages.PasteEObjectAxisInTableCommandProvider_PasteRows;

	/**
	 * The paste command cancelled label.
	 */
	protected static final String PASTE_COMMAND_HAS_BEEN_CANCELLED = Messages.PasteEObjectAxisInTableCommandProvider_CommandCreationHasBeenCancelled;

	/**
	 * The paste command can't be executed label.
	 */
	protected static final String PASTE_COMMAND_CANT_BE_EXECUTED = "The Paste command can't be executed"; //$NON-NLS-1$

	/**
	 * The paste command name.
	 */
	protected static final String PASTE_COMMAND_NAME = Messages.PasteEObjectAxisInTableCommandProvider_PasteFromStringCommand;

	/**
	 * The label for the created element.
	 */
	protected static final String CREATING_ELEMENT_A_NUMBER_X_Y = Messages.PasteEObjectAxisInTableCommandProvider_CreatingAnumberXonY;

	
	/**
	 * The table manager.
	 */
	protected INattableModelManager tableManager;

	/**
	 * The current table.
	 */
	protected final Table table;

	/**
	 * The editing domain for the table.
	 */
	protected final TransactionalEditingDomain tableEditingDomain;

	/**
	 * The editing domain of the context.
	 */
	protected final TransactionalEditingDomain contextEditingDomain;

	/**
	 * The table context.
	 */
	protected final EObject tableContext;

	/**
	 * the list of the post actions do do.
	 */
	protected List<String> postActions;

	/**
	 * The factor used for the progress monitor.
	 */
	protected int factor;

	/**
	 * The number of operations to do.
	 */
	protected final int nbOperationsToDo;

	/**
	 * The axis which to loop for the horizontal values.
	 */
	protected List<Object> secondAxis;

	/**
	 * if <code>true</code> the command can't be created and executed
	 */
	protected boolean isDisposed = false;

	/**
	 * The converter map
	 */
	protected final Map<Class<? extends AbstractStringValueConverter>, AbstractStringValueConverter> existingConverters;

	/**
	 * If true, we are pasting in detached mode
	 */
	protected boolean detachedMode;

	/**
	 * This allows to determinate if this is a column pasted.
	 */
	protected final boolean pasteColumn;

	/**
	 * The paste helper
	 */
	protected final CSVPasteHelper pasteHelper;

	/**
	 * the reader to parse
	 */
	protected final Reader reader;

	/**
	 * the parser to use
	 */
	protected final CSVParser parser;

	/**
	 * We refresh the dialog each X read char
	 */
	protected final int refreshEachReadChar = 1000;

	
	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            The table manager.
	 * @param pasteColumn
	 *            Boolean to determinate if the column are pasted.
	 * @param reader
	 *            The reader of the pasted text.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param totalSize
	 *            The total size of element pasted.
	 */
	public AbstractPasteEObjectInNattableCommandProvider(final INattableModelManager tableManager, final boolean pasteColumn, final Reader reader, final CSVPasteHelper pasteHelper, final long totalSize) {
		this.tableManager = tableManager;
		this.pasteColumn = pasteColumn;
		
		this.existingConverters = new HashMap<Class<? extends AbstractStringValueConverter>, AbstractStringValueConverter>();
		this.table = tableManager.getTable();
		this.tableContext = table.getContext();
		tableEditingDomain = TableEditingDomainUtils.getTableEditingDomain(table);
		contextEditingDomain = TableEditingDomainUtils.getTableContextEditingDomain(table);
		
		this.reader = reader;
		this.pasteHelper = pasteHelper;
		this.parser = createParser();

		// Variables needed for the progress monitor
		long div = -1;
		if (totalSize > Integer.MAX_VALUE) {
			div = totalSize / Integer.MAX_VALUE;
			if (div > Integer.MAX_VALUE) {
				div = 2 * div;
			}
			this.factor = (int) div;
			this.nbOperationsToDo = (int) (totalSize / div);
		} else {
			this.factor = 1;
			this.nbOperationsToDo = (int) totalSize;
		}
		init();
	}

	/**
	 * This allows to create the parser.
	 * 
	 * @return The created parser.
	 */
	protected abstract CSVParser createParser();

	/**
	 * Initialize the fields of this class.
	 */
	protected abstract void init();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.PasteNattableCommandProvider#executePasteFromStringCommand(boolean, boolean)
	 */
	@Override
	public IStatus executePasteFromStringCommand(final boolean useProgressMonitor, final boolean openDialog) {
		IStatus resultStatus = Status.OK_STATUS;

		if (this.isDisposed) {
			throw new RuntimeException("The command provider is disposed"); //$NON-NLS-1$
		}
		final String pasteJobName = PASTE_ROWS_JOB_NAME;

		if (this.detachedMode) {
			executePasteFromStringCommandInDetachedMode(useProgressMonitor, pasteJobName);
		} else {
			executePasteFromStringCommandInAttachedMode(useProgressMonitor, pasteJobName);
		}

		return resultStatus;
	}

	/**
	 * This allows to execute the paste from String command in the detached mode.
	 * 
	 * @param useProgressMonitor
	 *            boolean indicating that we must do the paste with a progress monitor.
	 * @param pasteJobName
	 *            The name of the paste job.
	 */
	protected void executePasteFromStringCommandInDetachedMode(final boolean useProgressMonitor, final String pasteJobName) {
		// the map used to share objects between the paste action and the cell value managers
		final Map<Object, Object> sharedMap = new HashMap<Object, Object>();
		// the map used to store useful information for the paste
		sharedMap.put(Constants.PASTED_ELEMENT_CONTAINER_KEY, tableContext);
		sharedMap.put(Constants.REFERENCES_TO_SET_KEY, new ArrayList<IValueSetter>());
		sharedMap.put(Constants.CELLS_TO_ADD_KEY, new ArrayList<Cell>());

		// used to be able to apply stereotypes required by columns properties, in detached mode even if there is no post actions defined in the table configuration
		// see bug 431691: [Table 2] Paste from Spreadsheet must be able to apply required stereotypes for column properties in all usecases
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=431691
		sharedMap.put(Constants.ADDITIONAL_POST_ACTIONS_TO_CONCLUDE_PASTE_KEY, new ArrayList<String>());

		if (!useProgressMonitor) {
			final ICommand pasteCommand = getPasteFromStringCommandInDetachedMode(contextEditingDomain, tableEditingDomain, new NullProgressMonitor(), sharedMap);
			try {
				CheckedOperationHistory.getInstance().execute(pasteCommand, new NullProgressMonitor(), null);
			} catch (final ExecutionException e) {
				Activator.log.error(e);
			} finally {
				sharedMap.clear();
				this.tableManager = null;
			}
		} else {
			// we create a job in order to don't freeze the UI
			final UIJob job = new UIJob(pasteJobName) {

				@Override
				public IStatus runInUIThread(final IProgressMonitor monitor) {

					final ICommand pasteCommand = getPasteFromStringCommandInDetachedMode(contextEditingDomain, tableEditingDomain, monitor, sharedMap);
					if (pasteCommand == null) {
						tableManager = null;
						return new Status(IStatus.CANCEL, Activator.PLUGIN_ID, PASTE_COMMAND_HAS_BEEN_CANCELLED);
					}
					// we execute the paste command
					if (pasteCommand.canExecute()) {
						try {
							int initialRowsSize = tableManager.getBodyLayerStack().getRowHideShowLayer().getRowCount();

							CheckedOperationHistory.getInstance().execute(pasteCommand, monitor, null);

							int finalRowsSize = tableManager.getBodyLayerStack().getRowHideShowLayer().getRowCount();

							final SelectionLayer selectionLayer = tableManager.getBodyLayerStack().getSelectionLayer();
							selectionLayer.doCommand(new SelectRowsCommand(selectionLayer, 0, initialRowsSize, false, false));
							selectionLayer.doCommand(new SelectRowsCommand(selectionLayer, 0, finalRowsSize, true, false));
						} catch (final ExecutionException e) {
							return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "An exception occured during the paste", e); //$NON-NLS-1$
						} finally {
							tableManager = null;
							sharedMap.clear();
						}

						monitor.done();
						return Status.OK_STATUS;
					} else {
						tableManager = null;
						sharedMap.clear();
						return new Status(IStatus.ERROR, Activator.PLUGIN_ID, PASTE_COMMAND_CANT_BE_EXECUTED);
					}
				}
			};
			job.setUser(true);
			job.schedule();
		}
	}

	/**
	 * Get the paste command for the detached mode.
	 *
	 * @param contextEditingDomain
	 *            The context editing domain.
	 * @param tableEditingDomain
	 *            The table editing domain.
	 * @param progressMonitor
	 *            The progress monitor used.
	 * @param sharedMap
	 *            A map used to share objects and informations during the paste between this class and the cell value manager.
	 * @return
	 * 		The command to use to finish the paste (the main part of the paste is directly done here).
	 */
	private ICommand getPasteFromStringCommandInDetachedMode(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final IProgressMonitor progressMonitor, final Map<Object, Object> sharedMap) {
		if (this.pasteColumn) {
			return new UnexecutableCommand(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.PasteEObjectTreeAxisInNatTableCommandProvider_CantPasteColumnsInTreeTable));
		} else {
			return getPasteRowFromStringInDetachedModeCommand(contextEditingDomain, tableEditingDomain, progressMonitor, sharedMap);
		}
	}
	
	/**
	 * Get the paste command for the detached mode corresponding to the current table.
	 * 
	 * @param contextEditingDomain The context editing domain.
	 * @param tableEditingDomain The table editing domain.
	 * @param progressMonitor The progress monitor used.
	 * @param sharedMap A map used to share objects and informations during the paste between this class and the cell value manager.
	 * @return The command to use to finish the paste (the main part of the paste is directly done here).
	 */
	protected abstract ICommand getPasteRowFromStringInDetachedModeCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final IProgressMonitor progressMonitor, final Map<Object, Object> sharedMap);

	/**
	 * This allows to execute the paste from String command in the attached mode.
	 *
	 * @param useProgressMonitor
	 *            boolean indicating that we must do the paste with a progress monitor
	 * @param pasteJobName
	 *            The name of the paste job.
	 */
	protected void executePasteFromStringCommandInAttachedMode(final boolean useProgressMonitor, final String pasteJobName) {
		if (!useProgressMonitor) {
			final ICommand pasteCommand = getPasteFromStringCommandInAttachedMode(contextEditingDomain, tableEditingDomain, new NullProgressMonitor());
			try {
				CheckedOperationHistory.getInstance().execute(pasteCommand, new NullProgressMonitor(), null);
			} catch (final ExecutionException e) {
				Activator.log.error(e);
			} finally {
				this.tableManager = null;
			}
		} else {
			// we create a job in order to don't freeze the UI
			final UIJob job = new UIJob(pasteJobName) {

				@Override
				public IStatus runInUIThread(final IProgressMonitor monitor) {

					final ICommand pasteCommand = getPasteFromStringCommandInAttachedMode(contextEditingDomain, tableEditingDomain, monitor);
					if (pasteCommand == null) {
						tableManager = null;
						return new Status(IStatus.CANCEL, Activator.PLUGIN_ID, PASTE_COMMAND_HAS_BEEN_CANCELLED);
					}
					// we execute the paste command
					if (pasteCommand.canExecute()) {
						try {
							int initialRowsSize = tableManager.getBodyLayerStack().getRowHideShowLayer().getRowCount();

							final EMFCommandOperation op = new EMFCommandOperation(contextEditingDomain, new GMFtoEMFCommandWrapper(pasteCommand));
							CheckedOperationHistory.getInstance().execute(op, monitor, null);

							int finalRowsSize = tableManager.getBodyLayerStack().getRowHideShowLayer().getRowCount();

							final SelectionLayer selectionLayer = tableManager.getBodyLayerStack().getSelectionLayer();
							selectionLayer.doCommand(new SelectRowsCommand(selectionLayer, 0, initialRowsSize, false, false));
							selectionLayer.doCommand(new SelectRowsCommand(selectionLayer, 0, finalRowsSize, true, false));
						} catch (final Exception e) {
							return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "An exception occured during the paste", e); //$NON-NLS-1$
						} finally {
							tableManager = null;
						}

						monitor.done();
						return Status.OK_STATUS;
					} else {
						tableManager = null;
						return new Status(IStatus.ERROR, Activator.PLUGIN_ID, PASTE_COMMAND_CANT_BE_EXECUTED);
					}
				}
			};
			job.setUser(true);
			job.schedule();
		}
	}

	/**
	 * Get the paste command for the attached mode.
	 *
	 * @param contextEditingDomain
	 *            The context editing domain
	 * @param tableEditingDomain
	 *            The table editing domain
	 * @param progressMonitor
	 *            The progress monitor
	 * @return the command to use to finish the paste (the main part of the paste is directly done here)
	 */
	private ICommand getPasteFromStringCommandInAttachedMode(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final IProgressMonitor progressMonitor) {
		if (this.pasteColumn) {
			return new UnexecutableCommand(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.PasteEObjectTreeAxisInNatTableCommandProvider_CantPasteColumnsInTreeTable));
		} else {
			return getPasteRowFromStringInAttachedModeCommand(contextEditingDomain, tableEditingDomain, progressMonitor);
		}
	}
	
	/**
	 * Get the paste command for the attached mode corresponding to the current table.
	 * 
	 * @param contextEditingDomain The context editing domain.
	 * @param tableEditingDomain The table editing domain.
	 * @param progressMonitor The progress monitor used.
	 * @return The command to use to finish the paste (the main part of the paste is directly done here).
	 */
	protected abstract ICommand getPasteRowFromStringInAttachedModeCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final IProgressMonitor progressMonitor);

	/**
	 * Get the status of the EMF command (containing compound command or gmf command).
	 * 
	 * @param command
	 *            The command.
	 * @return The status of the corresponding command.
	 */
	protected IStatus getStatusCommand(final Command command) {
		IStatus resultStatus = Status.OK_STATUS;

		if (command instanceof CompoundCommand) {
			final Iterator<Command> subCommandIterator = ((CompoundCommand) command).getCommandList().iterator();
			while (subCommandIterator.hasNext() && resultStatus.isOK()) {
				final Command subCommand = subCommandIterator.next();
				if (command instanceof CompoundCommand) {
					IStatus subStatus = getStatusCommand(subCommand);
					if (!subStatus.isOK()) {
						resultStatus = subStatus;
					}
				}
			}
		} else if (command instanceof GMFtoEMFCommandWrapper) {
			ICommand gmfCommand = ((GMFtoEMFCommandWrapper) command).getGMFCommand();
			if (gmfCommand instanceof ErrorTransactionalCommand) {
				resultStatus = ((ErrorTransactionalCommand) gmfCommand).getStatus();
			}
		}

		return resultStatus;
	}
	
	/**
	 * Get the post actions.
	 * 
	 * @return
	 * 		the list of the post actions to do
	 */
	protected Collection<String> getPostActions() {
		return this.postActions;
	}

	/**
	 * Dispose fields of the class
	 */
	protected void localDispose() {
		this.isDisposed = true;
		for (final AbstractStringValueConverter current : existingConverters.values()) {
			current.dispose();
		}
		this.existingConverters.clear();
		try {
			this.reader.close();
		} catch (final IOException e) {
			Activator.log.error(e);
		}
	}

}
