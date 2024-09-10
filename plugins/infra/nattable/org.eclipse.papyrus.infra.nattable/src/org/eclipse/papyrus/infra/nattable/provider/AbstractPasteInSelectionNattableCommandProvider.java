/*****************************************************************************
 * Copyright (c) 2015, 2020 CEA LIST and others.
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
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - bug 517617, 532452
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.provider;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectCellCommand;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectColumnCommand;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectRowsCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.CheckedOperationHistory;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.command.ErrorTransactionalCommand;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IPasteConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.Cell;
import org.eclipse.papyrus.infra.nattable.parsers.CSVParser;
import org.eclipse.papyrus.infra.nattable.parsers.CellIterator;
import org.eclipse.papyrus.infra.nattable.parsers.RowIterator;
import org.eclipse.papyrus.infra.nattable.paste.IValueSetter;
import org.eclipse.papyrus.infra.nattable.paste.PastePostActionRegistry;
import org.eclipse.papyrus.infra.nattable.utils.AxisConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.ExtendedCompoundCommand;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.InsertedElementInNattable;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.papyrus.infra.nattable.utils.UserActionConstants;
import org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter;
import org.eclipse.papyrus.infra.widgets.util.ImageConstants;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.UIJob;

/**
 * This class manage the paste in tree table when a selection is available.
 */
public abstract class AbstractPasteInSelectionNattableCommandProvider implements PasteNattableCommandProvider {

	/**
	 * The paste rows job name.
	 */
	private static final String PASTE_ROWS_JOB_NAME = Messages.PasteEObjectAxisInTableCommandProvider_PasteRows;

	/**
	 * The paste action job name.
	 */
	private static final String PASTE_ACTION_TASK_NAME = Messages.PasteEObjectAxisInTableCommandProvider_PasteAction;

	/**
	 * The command has been cancelled label.
	 */
	private static final String PASTE_COMMAND_HAS_BEEN_CANCELLED = Messages.PasteEObjectAxisInTableCommandProvider_CommandCreationHasBeenCancelled;

	/**
	 * The command can't be executed label.
	 */
	private static final String PASTE_COMMAND_CANT_BE_EXECUTED = Messages.AbstractPasteInSelectionNattableCommandProvider_TheCommandCantBeExecuted;

	/**
	 * The paste command name.
	 */
	private static final String PASTE_COMMAND_NAME = Messages.PasteEObjectAxisInTableCommandProvider_PasteFromStringCommand;


	/**
	 * the table manager
	 */
	protected INattableModelManager tableManager;

	/**
	 * the list of the post actions do do
	 */
	protected List<String> postActions;

	/**
	 * the paste helper
	 */
	protected final CSVPasteHelper pasteHelper;

	/**
	 * the converter map
	 */
	protected final Map<Class<? extends AbstractStringValueConverter>, AbstractStringValueConverter> existingConverters;

	/**
	 * We refresh the dialog each X read char
	 */
	private final int refreshEachReadChar = 1000;

	/**
	 * the reader to parse
	 */
	protected final Reader reader;

	/**
	 * the parser to use
	 */
	protected final CSVParser parser;

	/**
	 * If true, we are pasting in detached mode
	 */
	protected boolean detachedMode;

	/**
	 * If <code>true</code> the command can't be created and executed
	 */
	private boolean isDisposed = false;

	/**
	 * The current table.
	 */
	protected final Table table;

	/**
	 * The table editing domain.
	 */
	protected final TransactionalEditingDomain tableEditingDomain;

	/**
	 * The context editing domain.
	 */
	protected final TransactionalEditingDomain contextEditingDomain;

	/**
	 * The table context.
	 */
	protected final EObject tableContext;

	/**
	 * The number of operation to do.
	 */
	private final int nbOperationsToDo;

	/**
	 * The current selection in the table.
	 */
	protected TableSelectionWrapper tableSelectionWrapper;

	/**
	 * This allows to determinate if a single axis is pasted and must be repeat.
	 */
	protected boolean isSingleAxisPasted = false;

	/**
	 * This allows to determinate how much axis must be repeat when a single axis is pasted.
	 */
	protected long numberSelectedAxis = 0;

	/**
	 * This allows to store the values of the pasted text.
	 */
	protected Map<Object, List<String>> pastedValues = null;

	/**
	 * The first row position need to select the cells after paste.
	 */
	private int firstRowPosition = 0;

	/**
	 * The first column position need to select the cells after paste.
	 */
	private int firstColumnPosition = 0;

	/**
	 * The last row position need to select the cells after paste.
	 */
	private int lastRowPosition = 0;

	/**
	 * The last column position need to select the cells after paste.
	 */
	private int lastColumnPosition = 0;

	/**
	 * This allows to manage the status result in job (and avoid the dialog opened by eclipse worker)
	 */
	protected IStatus statusResult = null;

	/**
	 * Determinate if the paste is a column paste.
	 */
	protected boolean pasteColumn;

	/**
	 * Determinate if the pasted objects must be inserted if not exist.
	 */
	protected boolean isInsert;

	/**
	 * This allows to determinate the current action used by a user in the case of insert.
	 */
	protected int currentUserAction = UserActionConstants.UNDEFINED_USER_ACTION;

	/**
	 * This allows to keep the action used by user in the case of insert and with the 'apply to all' choice.
	 */
	protected int userAction = UserActionConstants.UNDEFINED_USER_ACTION;

	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            The nattable model manager.
	 * @param pasteColumn
	 *            Boolean to determinate if this is a paste in column.
	 * @param isInsert
	 *            Boolean to determinate if this is an insert action.
	 * @param axisAsIdentifier
	 *            The axis used ad identifier.
	 * @param reader
	 *            The reader of the pasted text.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param tableSelectionWrapper
	 *            The selection as table selection wrapper.
	 * @param preferredUserAction
	 *            The preferred user action for the insert row action.
	 * @param totalSize
	 *            The total size of pasted elements.
	 */
	public AbstractPasteInSelectionNattableCommandProvider(final INattableModelManager tableManager, final boolean pasteColumn, final boolean isInsert, final Reader reader, final CSVPasteHelper pasteHelper,
			final TableSelectionWrapper tableSelectionWrapper, final int preferredUserAction, final long totalSize) {
		this.tableManager = tableManager;
		this.existingConverters = new HashMap<Class<? extends AbstractStringValueConverter>, AbstractStringValueConverter>();
		this.pasteHelper = pasteHelper;
		this.reader = reader;
		this.table = tableManager.getTable();
		this.tableContext = table.getContext();
		this.tableEditingDomain = TableEditingDomainUtils.getTableEditingDomain(table);
		this.contextEditingDomain = TableEditingDomainUtils.getTableContextEditingDomain(table);
		this.tableSelectionWrapper = tableSelectionWrapper;
		this.numberSelectedAxis = 0;
		this.isSingleAxisPasted = false;
		this.pastedValues = new LinkedHashMap<Object, List<String>>();
		this.pasteColumn = pasteColumn;
		this.isInsert = isInsert;
		this.userAction = preferredUserAction;
		long div = -1;
		if (totalSize > Integer.MAX_VALUE) {
			div = totalSize / Integer.MAX_VALUE;
			if (div > Integer.MAX_VALUE) {
				div = 2 * div;
			}
			this.nbOperationsToDo = (int) (totalSize / div);
		} else {
			this.nbOperationsToDo = (int) totalSize;
		}
		this.parser = this.pasteHelper.createParser(reader);
		init();
	}

	/**
	 * Initialize the field of this class.
	 */
	protected abstract void init();

	/**
	 * Get the paste configuration for the depth.
	 * 
	 * @param depth
	 *            The depth searched.
	 * @return The paste configuration for the depth.
	 */
	protected List<IPasteConfiguration> getPasteConfigurationFor(final int depth) {
		final List<IPasteConfiguration> pasteConfs = new ArrayList<IPasteConfiguration>();
		if (depth == 0 && FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, depth)) {
			final IPasteConfiguration conf = (IPasteConfiguration) AxisConfigurationUtils.getIAxisConfigurationUsedInTable(tableManager.getTable(), NattableaxisconfigurationPackage.eINSTANCE.getPasteEObjectConfiguration(), false);
			pasteConfs.add(conf);
		}
		if (pasteConfs.isEmpty()) {
			for (final TreeFillingConfiguration current : FillingConfigurationUtils.getAllTreeFillingConfiguration(table)) {
				if (current.getDepth() == depth) {
					final IPasteConfiguration pasteConf = current.getPasteConfiguration();
					Assert.isNotNull(pasteConf);// must not be null here! (so must be verified before
					pasteConfs.add(pasteConf);
				}
			}
		}
		return pasteConfs;
	}

	/**
	 * Get the paste configuration for the depth and the category name.
	 * 
	 * @param depth
	 *            The depth searched.
	 * @param categoryName
	 *            The category name searched.
	 * @returnThe paste configuration for the depth and the category name.
	 */
	protected IPasteConfiguration getPasteConfigurationsFor(final int depth, final String categoryName) {
		if (depth == 0 && !FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0)) {
			AbstractHeaderAxisConfiguration conf = table.getLocalRowHeaderAxisConfiguration();
			if (null != conf) {
				conf = table.getTableConfiguration().getRowHeaderAxisConfiguration();
			}
			final List<TreeFillingConfiguration> filling = FillingConfigurationUtils.getAllTreeFillingConfigurationForDepth(table, depth);
			final List<IAxisConfiguration> referencedPasteConf = new ArrayList<IAxisConfiguration>();
			for (final TreeFillingConfiguration tmp : filling) {
				if (null != tmp.getPasteConfiguration()) {
					referencedPasteConf.add(tmp.getPasteConfiguration());
				}
			}
			if (conf != null) {
				for (final IAxisConfiguration axisConf : conf.getOwnedAxisConfigurations()) {
					if (axisConf instanceof IPasteConfiguration && !referencedPasteConf.contains(axisConf)) {
						return (IPasteConfiguration) axisConf;
					}
				}
			}
		}
		for (final TreeFillingConfiguration curr : FillingConfigurationUtils.getAllTreeFillingConfiguration(table)) {
			if (curr.getDepth() == depth) {
				if (null == categoryName || categoryName.isEmpty()) {
					return curr.getPasteConfiguration();
				} else {
					String featureName = curr.getAxisUsedAsAxisProvider().getAlias();
					if (null == featureName || "".equals(featureName)) {
						final Object element = curr.getAxisUsedAsAxisProvider().getElement();
						// TODO : doesn't work for stereotype propertyes
						// TODO : use label provider
						if (element instanceof EStructuralFeature) {
							featureName = ((EStructuralFeature) element).getName();
						}
					}
					if (categoryName.equals(featureName)) {
						return curr.getPasteConfiguration();
					}

				}
			}
		}
		return null;
	}

	/**
	 * Returns <code>true</code> if the configuration is in the detached mode, <code>false</code> otherwise.
	 * 
	 * @param table
	 *            The table.
	 * @return <code>true</code> if the configuration is in the detached mode, <code>false</code> otherwise.
	 */
	protected boolean isPasteInDetachedMode(final Table table) {
		final List<IPasteConfiguration> confs = getPasteConfigurationFor(0);
		for (final IPasteConfiguration current : confs) {
			if (null != current && current.isDetachedMode()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.PasteNattableCommandProvider#executePasteFromStringCommand(boolean)
	 */
	@Override
	public IStatus executePasteFromStringCommand(final boolean useProgressMonitor, final boolean openDialog) {
		IStatus resultStatus = Status.OK_STATUS;
		if (this.isDisposed) {
			throw new RuntimeException("The command provider is disposed"); //$NON-NLS-1$
		}
		final String pasteJobName = PASTE_ROWS_JOB_NAME;
		if (this.detachedMode) {
			resultStatus = executePasteFromStringCommand(useProgressMonitor, openDialog, pasteJobName, false);
		} else {
			resultStatus = executePasteFromStringCommand(useProgressMonitor, openDialog, pasteJobName, true);
		}

		return resultStatus;
	}

	/**
	 * This allows to execute the paste after create the paste command.
	 * 
	 * @param useProgressMonitor
	 *            Boolean to determinate if the progress monitor must be used.
	 * @param openDialog
	 *            Determinate if the dialog must be opened during the process.
	 * @param pasteJobName
	 *            The paste job name.
	 * @param attachedMode
	 *            Boolean to determinate if the command must be executed in attached mode or in detached mode.
	 * @return The status corresponding to the action.
	 */
	protected IStatus executePasteFromStringCommand(final boolean useProgressMonitor, final boolean openDialog, final String pasteJobName, final boolean attachedMode) {
		IStatus resultStatus = Status.OK_STATUS;

		// the map used to share objects between the paste action and the cell value managers
		Map<Object, Object> currentSharedMap = null;
		if (!attachedMode) {
			currentSharedMap = new HashMap<Object, Object>();
			// the map used to store useful information for the paste
			currentSharedMap.put(Constants.PASTED_ELEMENT_CONTAINER_KEY, tableContext);
			currentSharedMap.put(Constants.REFERENCES_TO_SET_KEY, new ArrayList<IValueSetter>());
			currentSharedMap.put(Constants.CELLS_TO_ADD_KEY, new ArrayList<Cell>());
			currentSharedMap.put(Constants.ADDITIONAL_POST_ACTIONS_TO_CONCLUDE_PASTE_KEY, new ArrayList<String>());
		}
		final Map<Object, Object> sharedMap = currentSharedMap;

		if (!useProgressMonitor) {
			final ICommand pasteCommand = getPasteFromStringCommand(contextEditingDomain, tableEditingDomain, openDialog, new NullProgressMonitor(), sharedMap, attachedMode);
			try {
				CheckedOperationHistory.getInstance().execute(pasteCommand, new NullProgressMonitor(), null);
				resultStatus = pasteCommand.getCommandResult().getStatus();

			} catch (final ExecutionException e) {
				resultStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_AnExceptionOccuredDuringThePaste, e);
			}
			if (null != sharedMap) {
				sharedMap.clear();
			}
		} else {
			// we create a job in order to don't freeze the UI
			final UIJob job = new UIJob(pasteJobName) {

				@Override
				public IStatus runInUIThread(final IProgressMonitor monitor) {
					statusResult = Status.OK_STATUS;

					final ICommand pasteCommand = getPasteFromStringCommand(contextEditingDomain, tableEditingDomain, openDialog, monitor, sharedMap, attachedMode);
					if (null == pasteCommand) {
						statusResult = new Status(IStatus.CANCEL, Activator.PLUGIN_ID, PASTE_COMMAND_HAS_BEEN_CANCELLED);
					} else {
						// we execute the paste command
						if (pasteCommand.canExecute()) {
							try {
								if (attachedMode) {
									final EMFCommandOperation op = new EMFCommandOperation(contextEditingDomain, new GMFtoEMFCommandWrapper(pasteCommand));
									CheckedOperationHistory.getInstance().execute(op, monitor, null);
								} else {
									CheckedOperationHistory.getInstance().execute(pasteCommand, monitor, null);
								}
								statusResult = pasteCommand.getCommandResult().getStatus();

							} catch (final ExecutionException e) {
								statusResult = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_AnExceptionOccuredDuringThePaste, e);
							} finally {
								if (null != sharedMap) {
									sharedMap.clear();
								}
							}
							monitor.done();
						} else {
							if (null != sharedMap) {
								sharedMap.clear();
							}
							statusResult = new Status(IStatus.ERROR, Activator.PLUGIN_ID, PASTE_COMMAND_CANT_BE_EXECUTED);
						}
					}

					// Select the modified cells if the status is not ERROR or CANCEL
					if (canContinue(statusResult)) {
						// The lastColumnPosition and the lastRowPosition was calculated from the number of columns and rows read, so we need to decrement them
						lastColumnPosition--;
						lastRowPosition--;

						final SelectionLayer selectionLayer = tableManager.getBodyLayerStack().getSelectionLayer();
						// If no selection is available, don't select any cells because the modified cells can be discontinuous
						if (null != tableSelectionWrapper) {
							if (!tableSelectionWrapper.getFullySelectedRows().isEmpty()) {
								// Select the first row
								selectionLayer.doCommand(new SelectRowsCommand(selectionLayer, 0, firstRowPosition, false, false));

								// Select the last column position if more than one column modified with Shift mask
								if (firstRowPosition != lastRowPosition) {
									selectionLayer.doCommand(new SelectRowsCommand(selectionLayer, 0, lastRowPosition, true, false));
								}
							} else if (!tableSelectionWrapper.getFullySelectedColumns().isEmpty() && pasteColumn) {

								// Select the first column
								selectionLayer.doCommand(new SelectColumnCommand(selectionLayer, firstColumnPosition, 0, false, false));

								// Select the last column position if more than one column modified with Shift mask
								if (firstColumnPosition != lastColumnPosition) {
									selectionLayer.doCommand(new SelectColumnCommand(selectionLayer, lastColumnPosition, 0, true, false));
								}
							} else {

								// Select the modified cells
								selectionLayer.doCommand(new SelectCellCommand(selectionLayer, firstColumnPosition, firstRowPosition, false, false));

								// Select the last cell position if more than one cell modified with Shift mask
								if (firstColumnPosition != lastColumnPosition || firstRowPosition != lastRowPosition) {
									selectionLayer.doCommand(new SelectCellCommand(selectionLayer, lastColumnPosition, lastRowPosition, true, false));
								}
							}
						}
					}

					tableManager = null;
					tableSelectionWrapper = null;

					if (openDialog) {
						displayDialog(statusResult);
					}

					return Status.OK_STATUS;
				}
			};
			job.setUser(true);
			job.schedule();

			resultStatus = statusResult;
		}

		return resultStatus;
	}

	/**
	 * This allows to display the error message when this is a paste configuration error type.
	 * 
	 * @param result
	 *            the result status of the paste process
	 */
	protected void displayDialog(final IStatus result) {
		if (null != result) {
			// If the error is caracterized by the paste configuration error status, use the dialog concerning the paste configuration error
			if (IStatus.OK != result.getSeverity()) {
				String messageDialog = null;
				switch (result.getSeverity()) {
				case IStatus.INFO:
					messageDialog = Messages.PasteInTableHandler_PasteInformation;
					break;
				case IStatus.WARNING:
					messageDialog = Messages.PasteInTableHandler_PasteWarning;
					break;
				case IStatus.ERROR:
					messageDialog = Messages.PasteInTableHandler_PasteError;
					break;
				case IStatus.CANCEL:
					messageDialog = Messages.PasteInTableHandler_PasteCancelled;
					break;
				}

				final ErrorDialog errorDialog = new ErrorDialog(Display.getDefault().getActiveShell(), Messages.PasteImportStatusDialog_ImportPasteDialogTitle, messageDialog, result, IStatus.OK
						| IStatus.INFO | IStatus.WARNING | IStatus.ERROR) {

					/**
					 * Redefine this method to manage the papyrus icon in shell.
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.dialogs.ErrorDialog#configureShell(org.eclipse.swt.widgets.Shell)
					 */
					@Override
					protected void configureShell(Shell shell) {
						super.configureShell(shell);
						shell.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(ImageConstants.PAPYRUS_ICON_PATH));
					}
				};
				errorDialog.open();
			}
		}
	}

	/**
	 * Get the paste command.
	 * 
	 * @param contextEditingDomain
	 *            The context editing domain.
	 * @param tableEditingDomain
	 *            The table editing domain.
	 * @param openDialog
	 *            Determinate if the dialog must be opened during the process.
	 * @param progressMonitor
	 *            The progress monitor.
	 * @param sharedMap
	 *            The map used to share objects between the paste action and the cell value managers
	 * @param attachedMode
	 *            Boolean to determinate if the command must be executed in attached mode or in detached mode.
	 * @return The status corresponding to the command.
	 */
	protected ICommand getPasteFromStringCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final boolean openDialog, final IProgressMonitor progressMonitor, final Map<Object, Object> sharedMap,
			final boolean attachedMode) {
		ICommand resultCommand = null;
		if (null == tableSelectionWrapper) {
			final IStatus canPasteStatus = canPasteWithoutSelection();
			if (IStatus.ERROR != canPasteStatus.getSeverity()) {
				resultCommand = getPasteRowsFromStringCommand(contextEditingDomain, tableEditingDomain, null, openDialog, progressMonitor, sharedMap, attachedMode);
			} else {
				resultCommand = new ErrorTransactionalCommand(contextEditingDomain, PASTE_COMMAND_NAME, null, canPasteStatus);
			}
		} else {
			final Map<Integer, Object> fullySelectedRows = tableSelectionWrapper.getFullySelectedRows();
			if (!fullySelectedRows.isEmpty()) {
				// Check if this is possible to paste the rows
				final IStatus canPasteStatus = canPasteRows(fullySelectedRows);
				if (IStatus.ERROR != canPasteStatus.getSeverity()) {
					resultCommand = getPasteRowsFromStringCommand(contextEditingDomain, tableEditingDomain, fullySelectedRows, openDialog, progressMonitor, sharedMap, attachedMode);
				} else {
					resultCommand = new ErrorTransactionalCommand(contextEditingDomain, PASTE_COMMAND_NAME, null, canPasteStatus);
				}
			} else {
				final Map<Integer, Object> fullySelectedColumns = tableSelectionWrapper.getFullySelectedColumns();
				if (!fullySelectedColumns.isEmpty() && pasteColumn) {
					// Check if this is possible to paste the columns
					final IStatus canPasteStatus = canPasteColumns(fullySelectedColumns);
					if (IStatus.ERROR != canPasteStatus.getSeverity()) {
						resultCommand = getPasteColumnsFromStringCommand(contextEditingDomain, tableEditingDomain, fullySelectedColumns, openDialog, progressMonitor, sharedMap, attachedMode);
					} else {
						// If only one row is pasted in the columns,
						// transpose the pastedValues to try to repeat for each row in the table
						final Map<Object, List<String>> returnedPastedValues = new LinkedHashMap<Object, List<String>>();
						if (!pastedValues.isEmpty()) {
							for (Object o : pastedValues.keySet()) {
								int i = 0;
								for (String s : pastedValues.get(o)) {
									if (null == returnedPastedValues.get(i)) {
										returnedPastedValues.put(i, new ArrayList<String>());
									}
									returnedPastedValues.get(i).add(s);
									i++;
								}
							}
							pastedValues = returnedPastedValues;

							// The number of columns must be the same to repeat it
							if (pastedValues.size() == 1 && fullySelectedColumns.size() == pastedValues.get(pastedValues.keySet().iterator().next()).size()) {
								this.isSingleAxisPasted = true;
								this.numberSelectedAxis = tableManager.getBodyLayerStack().getRowHideShowLayer().getRowCount();
								resultCommand = getPasteCellsRowFromStringCommand(contextEditingDomain, tableEditingDomain, tableSelectionWrapper.getSelectedCells(), openDialog, progressMonitor, sharedMap, attachedMode);
							}
						}

						// If we can't repeat (not only one line or not the same number of columns), return the error message
						if (null == resultCommand) {
							resultCommand = new ErrorTransactionalCommand(contextEditingDomain, PASTE_COMMAND_NAME, null, canPasteStatus);
						}
					}
				} else {
					final Collection<PositionCoordinate> selectedCells = tableSelectionWrapper.getSelectedCells();
					if (!selectedCells.isEmpty()) {
						// Check if this is possible to paste the cells
						final IStatus canPasteStatus = canPasteCells(selectedCells);
						if (Status.ERROR != canPasteStatus.getSeverity()) {
							resultCommand = getPasteCellsRowFromStringCommand(contextEditingDomain, tableEditingDomain, selectedCells, openDialog, progressMonitor, sharedMap, attachedMode);
						} else {
							resultCommand = new ErrorTransactionalCommand(contextEditingDomain, PASTE_COMMAND_NAME, null, canPasteStatus);
						}
					}
				}
			}
		}
		return resultCommand;
	}

	/**
	 * This allows to manage if the cells can be pasted.
	 * 
	 * @param selectedCells
	 *            The selected cells in table.
	 * @return OK status if the paste can be done, ERROR otherwise.
	 */
	protected IStatus canPasteWithoutSelection() {
		IStatus result = Status.OK_STATUS;

		// Get the pasted text
		final RowIterator rowIter = parser.parse();

		// Calculate the number of read rows and columns from the pasted elements
		// To calculate this :
		// - Loop on rows and increment
		// - Calculate the number of columns by rows and get the maximum columns
		int nbRowRead = -1;
		int nbColumnRead = 0;
		while (rowIter.hasNext()) {
			final List<String> valuesByRow = new ArrayList<String>();
			final CellIterator cellIter = rowIter.next();
			int nbColumnReadByRow = 0;
			if (cellIter.hasNext()) {
				while (cellIter.hasNext()) {
					valuesByRow.add(cellIter.next());
					nbColumnReadByRow++;
				}
			}
			nbColumnRead = nbColumnRead >= nbColumnReadByRow ? nbColumnRead : nbColumnReadByRow;
			nbRowRead++;

			// Don't add the last row because the last rowIter is a blank line
			if (rowIter.hasNext()) {
				pastedValues.put(nbRowRead, valuesByRow);
			}
		}

		if (tableManager.getColumnCount() != nbColumnRead) {
			result = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_readcolumnsexceedsexistingcolumns);
		}

		return result;
	}

	/**
	 * This allows to manage if the cells can be pasted.
	 * 
	 * @param selectedCells
	 *            The selected cells in table.
	 * @return OK status if the paste can be done, ERROR otherwise.
	 */
	protected IStatus canPasteCells(final Collection<PositionCoordinate> selectedCells) {
		IStatus result = Status.OK_STATUS;

		// Get the pasted text
		final RowIterator rowIter = parser.parse();

		// Calculate the number of read rows and columns from the pasted elements
		// To calculate this :
		// - Loop on rows and increment
		// - Calculate the number of columns by rows and get the maximum columns
		int nbRowRead = -1;
		int nbColumnRead = 0;
		while (rowIter.hasNext()) {
			final List<String> valuesByRow = new ArrayList<String>();
			final CellIterator cellIter = rowIter.next();
			int nbColumnReadByRow = 0;
			if (cellIter.hasNext()) {
				while (cellIter.hasNext()) {
					valuesByRow.add(cellIter.next());
					nbColumnReadByRow++;
				}
			}
			nbColumnRead = nbColumnRead >= nbColumnReadByRow ? nbColumnRead : nbColumnReadByRow;
			nbRowRead++;

			// Don't add the last row because the last rowIter is a blank line
			if (rowIter.hasNext()) {
				pastedValues.put(nbRowRead, valuesByRow);
			}
		}

		// If a unique cell is selected, replace all rows and columns from the selection
		if (selectedCells.size() > 1) {
			// Calculate the number of cells selected by rows and columns
			// To calculate this :
			// - Get the minimum coordinate
			// - Get the maximum coordinate
			// - Calculate the rows and columns by substract of (max - min)
			final Iterator<PositionCoordinate> selectedCellsIterator = selectedCells.iterator();
			PositionCoordinate minCoordinate = null;
			PositionCoordinate maxCoordinate = null;
			while (selectedCellsIterator.hasNext()) {
				final PositionCoordinate currentSelectedCell = selectedCellsIterator.next();
				if (null == minCoordinate || (minCoordinate.getRowPosition() > currentSelectedCell.getRowPosition() || minCoordinate.getColumnPosition() > currentSelectedCell.getColumnPosition())) {
					minCoordinate = currentSelectedCell;
				}

				if (null == maxCoordinate || (maxCoordinate.getRowPosition() < currentSelectedCell.getRowPosition() || maxCoordinate.getColumnPosition() < currentSelectedCell.getColumnPosition())) {
					maxCoordinate = currentSelectedCell;
				}
			}
			final int nbRowSelected = maxCoordinate.getRowPosition() - minCoordinate.getRowPosition() + 1;
			final int nbColumnSelected = maxCoordinate.getColumnPosition() - minCoordinate.getColumnPosition() + 1;

			// Check the number of rows and columns selected and to paste
			// 3 Cases to manage :
			// - The rows and columns numbers are equals -> Continue
			// - The rows and/or columns numbers are not equals -> Stop and display error
			// - The columns numbers are equals and rows number read is equals to 1 -> Continue and repeat the row pasted
			if (nbRowRead > 1 && nbRowRead != nbRowSelected) {
				result = new Status(Status.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_readrowsexceedsexistingrows);
			} else if (nbColumnRead != nbColumnSelected) {
				result = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_readcolumnsexceedsexistingcolumns);
			} else if (1 == nbRowRead) {
				this.isSingleAxisPasted = true;
				this.numberSelectedAxis = nbRowSelected;
			}
		}

		return result;
	}

	/**
	 * This allows to manage if the rows can be pasted.
	 * 
	 * @param rows
	 *            The selected rows in table (position and object).
	 * @return OK status if the paste can be done, ERROR otherwise.
	 */
	protected IStatus canPasteRows(final Map<Integer, Object> rows) {
		IStatus result = Status.OK_STATUS;

		// Get the pasted text
		final RowIterator rowIter = parser.parse();

		// Calculate the number of read rows and columns from the pasted elements
		// To calculate this :
		// - Loop on rows and increment
		// - Calculate the number of columns by rows and get the maximum columns
		int nbRowRead = -1;
		int nbColumnRead = 0;
		while (rowIter.hasNext()) {
			final List<String> valuesByRow = new ArrayList<String>();
			final CellIterator cellIter = rowIter.next();
			int nbColumnReadByRow = 0;
			if (cellIter.hasNext()) {
				while (cellIter.hasNext()) {
					valuesByRow.add(cellIter.next());
					nbColumnReadByRow++;
				}
			}
			nbColumnRead = nbColumnRead >= nbColumnReadByRow ? nbColumnRead : nbColumnReadByRow;
			nbRowRead++;

			// Don't add the last row because the last rowIter is a blank line
			if (rowIter.hasNext()) {
				pastedValues.put(nbRowRead, valuesByRow);
			}
		}

		final int nbRowsSelected = rows.size();
		final int nbColumnsSelected = tableManager.getBodyLayerStack().getRowHideShowLayer().getColumnCount();

		// Check the number of rows and columns selected and to paste
		// 3 Cases to manage :
		// - The rows numbers are not equals -> Error
		// - The columns numbers are not equals -> Error
		// - In other cases -> Continue
		if (nbColumnsSelected != nbColumnRead) {
			result = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_readcolumnsexceedsexistingcolumns);
		} else if (nbRowRead != nbRowsSelected) {
			result = new Status(Status.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_readrowsexceedsexistingrows);
		}

		return result;
	}

	/**
	 * This allows to manage if the columns can be pasted.
	 * 
	 * @param columns
	 *            The selected columns in table (position and object).
	 * @return OK status if the paste can be done, ERROR otherwise.
	 */
	protected IStatus canPasteColumns(final Map<Integer, Object> columns) {
		IStatus result = Status.OK_STATUS;

		// Get the pasted text
		final RowIterator rowIter = parser.parse();

		// Calculate the number of read rows and columns from the pasted elements
		// To calculate this :
		// - Loop on rows and increment
		// - Calculate the number of columns by rows and get the maximum columns
		// Store the values by column (instead of rows for others)
		int nbRowRead = -1;
		int nbColumnRead = 0;
		while (rowIter.hasNext()) {
			final CellIterator cellIter = rowIter.next();
			int nbColumnReadByRow = 0;
			if (cellIter.hasNext()) {
				while (cellIter.hasNext()) {
					if (null == pastedValues.get(nbColumnReadByRow)) {
						pastedValues.put(nbColumnReadByRow, new ArrayList<String>());
					}
					pastedValues.get(nbColumnReadByRow).add(cellIter.next());
					nbColumnReadByRow++;
				}
			}
			nbColumnRead = nbColumnRead >= nbColumnReadByRow ? nbColumnRead : nbColumnReadByRow;
			nbRowRead++;
		}

		final int nbRowsSelected = tableManager.getBodyLayerStack().getRowHideShowLayer().getRowCount();
		final int nbColumnsSelected = columns.size();

		// Check the number of rows and columns selected and to paste
		// 3 Cases to manage :
		// - The rows numbers are not equals -> Error
		// - The column read is alone -> Continue and repeat the column pasted
		// - The columns numbers are not equals -> Error
		/*
		 * if (1 == nbRowRead && nbColumnsSelected == nbColumnRead){
		 * this.isSingleAxisPasted = true;
		 * this.numberSelectedAxis = nbRowsSelected;
		 * }else
		 */ if (nbRowRead != nbRowsSelected) {
			result = new Status(Status.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_readrowsexceedsexistingrows);
		} else if (1 == nbColumnRead && 1 < nbColumnsSelected) {
			this.isSingleAxisPasted = true;
			this.numberSelectedAxis = nbColumnsSelected;
		} else if (nbColumnRead != nbColumnsSelected) {
			result = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_readcolumnsexceedsexistingcolumns);
		}

		return result;
	}

	/**
	 * Get the paste action for the cells paste for rows paste.
	 * 
	 * @param contextEditingDomain
	 *            The context editing domain.
	 * @param tableEditingDomain
	 *            The table editing domain.
	 * @param selectedCells
	 *            The selected cells.
	 * @param openDialog
	 *            Determinate if the dialog must be opened during the process.
	 * @param progressMonitor
	 *            The progress monitor.
	 * @param sharedMap
	 *            The shared map.
	 * @param attachedMode
	 *            Boolean to determinate if the command must be executed with the attached mode or with the detached mode.
	 * @return The command for the paste action.
	 */
	protected ICommand getPasteCellsRowFromStringCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final Collection<PositionCoordinate> selectedCells,
			final boolean openDialog, final IProgressMonitor progressMonitor, final Map<Object, Object> sharedMap, final boolean attachedMode) {
		// initialize the progress monitor
		if (null != progressMonitor) {
			progressMonitor.beginTask(PASTE_ACTION_TASK_NAME, this.nbOperationsToDo);
		}

		// 2.2 create the creation request and find the command provider
		final ICommand pasteAllCommand = new AbstractTransactionalCommand(contextEditingDomain, PASTE_COMMAND_NAME, null) {

			/**
			 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
			 *
			 * @param monitor
			 * @param info
			 * @return
			 * @throws ExecutionException
			 */
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				MultiStatus resultStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, Messages.AbstractPasteInSelectionNattableCommandProvider_ThePasteHasBeenDoneWithSomeProblems, null);

				long readChar = 0;
				long previousreadChar = 0;

				// Iterate on selected cells iterator at the same time that on cells
				final PositionCoordinate firstpositionCoordinate = selectedCells.iterator().next();
				firstRowPosition = firstpositionCoordinate.getRowPosition();
				firstColumnPosition = firstpositionCoordinate.getColumnPosition();
				lastRowPosition = firstRowPosition;
				lastColumnPosition = firstColumnPosition;

				// Create the compound command for the paste action
				final CompoundCommand compoundCommand = new CompoundCommand("Paste action"); //$NON-NLS-1$

				final Iterator<Object> rowIter = pastedValues.keySet().iterator();

				Collection<String> rowValuesAsString = new ArrayList<String>();

				// Loop on each row
				while (((!isSingleAxisPasted && rowIter.hasNext()) || (isSingleAxisPasted && numberSelectedAxis > 0)) && canContinue(resultStatus)) {
					if (isSingleAxisPasted) {
						numberSelectedAxis--;
					}

					if (!(isSingleAxisPasted && firstRowPosition < lastRowPosition)) {
						rowValuesAsString = pastedValues.get(rowIter.next());
					}

					// To avoid blank lines
					if (!rowValuesAsString.isEmpty()) {
						int nbColmnRead = firstColumnPosition;
						final Iterator<String> cellIter = rowValuesAsString.iterator();

						// Check if the progress monitor catch a cancel click
						if (null != progressMonitor && progressMonitor.isCanceled()) {
							progressMonitor.done();
							localDispose();
							return CommandResult.newCancelledCommandResult();
						}

						readChar = readChar + (parser.getReadCharacters() - previousreadChar);
						previousreadChar = parser.getReadCharacters();

						if (null != progressMonitor && readChar > refreshEachReadChar) {
							readChar = 0;
							progressMonitor.worked(refreshEachReadChar);
						}

						// Get the row index from its position
						int realRowIndex = tableManager.getBodyLayerStack().getSelectionLayer().getRowIndexByPosition(lastRowPosition);
						
						// Get the row element from its index
						final Object rowElement = AxisUtils.getRepresentedElement(tableManager.getRowElement(realRowIndex));

						// check that the row element is not a tree filling configuration and continue the process for others
						if (!(rowElement instanceof TreeFillingConfiguration)) {

							// Manage the columns of the row
							while (cellIter.hasNext() && canContinue(resultStatus)) {
								final String valueAsString = cellIter.next();

								// Get the column index from its position
								int realColumnIndex = tableManager.getBodyLayerStack().getSelectionLayer().getColumnIndexByPosition(nbColmnRead);
								// Get the column attribute from its index
								final Object columnElement = AxisUtils.getRepresentedElement(tableManager.getColumnElement(realColumnIndex));

								// Edit the value if this is editable
								final boolean isEditable = CellManagerFactory.INSTANCE.isCellEditable(columnElement, rowElement, sharedMap, tableManager);
								if (isEditable) {
									final AbstractStringValueConverter converter = CellManagerFactory.INSTANCE.getOrCreateStringValueConverterClass(columnElement, rowElement, existingConverters, pasteHelper.getMultiValueSeparator(), tableManager);

									// Get the converted value (to compare with the new one
									final Command command = CellManagerFactory.INSTANCE.getSetStringValueCommand(tableEditingDomain, columnElement, rowElement, valueAsString, converter, tableManager);
									final IStatus commandStatus = getStatusCommand(command);
									if (!commandStatus.isOK()) {
										resultStatus.add(commandStatus);
									} else {
										compoundCommand.append(command);
									}
								} else {
									// Manage the warnings when the user tried to add a value (except exmpty, N/A or null) in a non-editable cell
									if (isValidValue(valueAsString)) {
										resultStatus.add(new Status(IStatus.WARNING, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_NonEditableCellTriedToBeOverwrited));
									}
								}
								nbColmnRead++;
							}

							if (lastColumnPosition < nbColmnRead) {
								lastColumnPosition = nbColmnRead;
							}
						}
					}
					lastRowPosition++;
				}

				progressMonitor.done();
				localDispose();

				// Check if the status is not CANCEL or ERROR
				if (canContinue(resultStatus) && null != compoundCommand && !compoundCommand.isEmpty() && compoundCommand.canExecute()) {
					// Execute the compound command
					tableEditingDomain.getCommandStack().execute(compoundCommand);

					return new CommandResult(resultStatus);
				}
				if (!resultStatus.isOK()) {
					// Create an error status
					final MultiStatus errorStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.ERROR, Messages.AbstractPasteInSelectionNattableCommandProvider_ThePasteHasNotBeenDoneBecauseOfSomeProblems, null);
					if (resultStatus.isMultiStatus()) {
						for (final IStatus subStatus : resultStatus.getChildren()) {
							errorStatus.add(subStatus);
						}
					} else {
						errorStatus.add(resultStatus);
					}
					errorStatus.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, null));
					resultStatus = errorStatus;
				}
				return new CommandResult(resultStatus);
			}
		};
		return pasteAllCommand;
	}

	/**
	 * Get the paste action for the cells paste for rows paste.
	 * 
	 * @param contextEditingDomain
	 *            The context editing domain.
	 * @param tableEditingDomain
	 *            The table editing domain.
	 * @param selectedCells
	 *            The selected cells.
	 * @param openDialog
	 *            Determinate if the dialog must be opened during the process.
	 * @param progressMonitor
	 *            The progress monitor.
	 * @param sharedMap
	 *            The shared map.
	 * @param attachedMode
	 *            Boolean to determinate if the command must be executed with the attached mode or with the detached mode.
	 * @return The command for the paste action.
	 */
	protected ICommand getPasteRowsFromStringCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final Map<Integer, Object> rows,
			final boolean openDialog, final IProgressMonitor progressMonitor, final Map<Object, Object> sharedMap, final boolean attachedMode) {
		// initialize the progress monitor
		if (null != progressMonitor) {
			progressMonitor.beginTask(PASTE_ACTION_TASK_NAME, this.nbOperationsToDo);
		}

		// 2.2 create the creation request and find the command provider
		final ICommand pasteAllCommand = new AbstractTransactionalCommand(contextEditingDomain, PASTE_COMMAND_NAME, null) {

			/**
			 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
			 *
			 * @param monitor
			 * @param info
			 * @return
			 * @throws ExecutionException
			 */
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				IStatus resultStatus = Status.OK_STATUS;

				// Check that the axis identifier axis is available in the table
				int axisIdentifierIndex = getColumnIndexOfAxisIdentifier();
				if (-1 == axisIdentifierIndex) {
					resultStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_TheAxisUsedAsIdentifierNotAvailable);
				}

				// Create the compound command for the paste action
				ExtendedCompoundCommand compoundCommand = new ExtendedCompoundCommand("Paste action"); //$NON-NLS-1$

				// Manage a list of created elements to create a unique add command for all created elements
				final List<InsertedElementInNattable> objectsToAdd = new ArrayList<InsertedElementInNattable>();

				if (canContinue(resultStatus)) {
					final MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, Messages.AbstractPasteInSelectionNattableCommandProvider_ThePasteHasBeenDoneWithSomeProblems, null);

					long readChar = 0;
					long previousreadChar = 0;

					// Calculate the first row position of the selection and the position of the first selected element i the root parent (to determinate where the inserted element have to be)
					int initialFirstRowPosition = -1;
					if (null != rows) {
						for (int rowPosition : rows.keySet()) {
							if (-1 == initialFirstRowPosition || rowPosition < initialFirstRowPosition) {
								initialFirstRowPosition = rowPosition;
							}
						}
					}
					firstRowPosition = initialFirstRowPosition;
					firstColumnPosition = 0;
					lastRowPosition = firstRowPosition;
					lastColumnPosition = firstColumnPosition;

					// Calculate the parent context, depth and category from the row position
					int noPosition = 0;
					if (-1 != firstRowPosition) {
						noPosition = firstRowPosition;
					}

					final EObject parentContext = getContentOfSelection(rows, noPosition);
					final int noIndex = tableManager.getBodyLayerStack().getSelectionLayer().getRowIndexByPosition(noPosition);
					final Object firstRowElementSelected = tableManager.getRowElement(noIndex);
					final int parentDepth = getDepthFromObject(firstRowElementSelected);
					final String parentCategory = getCategoryFromObject(firstRowElementSelected);

					// Calculate the index of the first selected item in the parent context
					int initialFirstIndexInParent = -1;
					if (-1 != firstRowPosition) {
						initialFirstIndexInParent = getFirstSelectedElementIndexOfTableContext(rows, firstRowPosition, parentContext);
					}

					final Iterator<Object> rowIter = pastedValues.keySet().iterator();

					// Loop on each row
					while (rowIter.hasNext() && canContinue(multiStatus)) {

						final List<String> rowValuesAsString = pastedValues.get(rowIter.next());

						// To avoid blank lines
						if (!rowValuesAsString.isEmpty()) {

							final String identifierValue = rowValuesAsString.get(axisIdentifierIndex);

							if (!identifierValue.isEmpty()) {

								// Calculate the row to modify
								int rowPositionToModify = -1;
								boolean sameMultipleIdentifier = false;
								if (null != rows) {
									final Iterator<Integer> selectedRowsPosition = rows.keySet().iterator();
									while (selectedRowsPosition.hasNext() && canContinue(multiStatus)) {
										int selectedRowPosition = selectedRowsPosition.next();
										int selectedRowIndex = tableManager.getBodyLayerStack().getSelectionLayer().getRowIndexByPosition(selectedRowPosition);
										// Check the column identifier into the rows selected
										if (identifierValue.equals(tableManager.getDataValue(axisIdentifierIndex, selectedRowIndex))) {
											// If the value is equals and the name is already existing, add an error to specify that the name is corresponding for multiple rows
											if (-1 < rowPositionToModify) {
												sameMultipleIdentifier = true;
											} else {
												rowPositionToModify = selectedRowPosition;
											}
										}
									}
								} else {
									int counter = 0;
									while (counter < tableManager.getRowCount() && canContinue(multiStatus)) {
										// Check the column identifier into the rows selected
										if (identifierValue.equals(tableManager.getDataValue(axisIdentifierIndex, counter))) {
											// If the value is equals and the name is already existing, add an error to specify that the name is corresponding for multiple rows
											if (-1 < rowPositionToModify) {
												sameMultipleIdentifier = true;
											} else {
												rowPositionToModify = tableManager.getBodyLayerStack().getSelectionLayer().getRowPositionByIndex(counter);
											}
										}
										counter++;
									}
								}

								manageUserAction(rowPositionToModify, isInsert, openDialog, identifierValue, multiStatus);

								// If several row found for the same identifier and must be replaced, return an error message
								// because we don't know which one modified
								if (sameMultipleIdentifier && UserActionConstants.REPLACE_USER_ACTION == currentUserAction) {
									multiStatus.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, String.format(Messages.AbstractPasteInSelectionNattableCommandProvider_MultipleSelectedRowsCorrespondingForIdentifier, identifierValue)));
								}

								Object rowElement = null;
								boolean createdElement = false;

								// Manage the cancel action
								if (UserActionConstants.CANCEL_USER_ACTION == currentUserAction) {
									throw new OperationCanceledException();
								}

								// If the row index to modify is not get, create it if this is an insert and alert with a warning message
								if (UserActionConstants.ADD_USER_ACTION == currentUserAction) {
									if (isInsert) {

										// This allows to manage the add of the object in the correct owner

										int currentDepth = parentDepth;
										String currentCategory = parentCategory;
										EObject currentContext = parentContext;

										// If a row is found, the created item must be created as the same depth
										if (-1 != rowPositionToModify) {

											// Get the depth and the category from the object found in the table (because the added once will be at the same level)
											int rowIndexToModify = tableManager.getBodyLayerStack().getSelectionLayer().getRowIndexByPosition(rowPositionToModify);
											Object object = tableManager.getRowElement(rowIndexToModify);
											currentDepth = getDepthFromObject(object);
											currentCategory = getCategoryFromObject(object);

											Object element = AxisUtils.getRepresentedElement(object);
											currentContext = ((EObject) element).eContainer();
										}

										// Get the paste configuration and its attributes
										final PasteEObjectConfiguration currentPasteConfiguration = getPasteConfiguration(table, currentDepth, currentCategory);
										final EStructuralFeature containementFeature = currentPasteConfiguration.getPasteElementContainementFeature();
										final IElementType elementType = ElementTypeRegistry.getInstance().getType(currentPasteConfiguration.getPastedElementId());

										// If this is an insert action, add the row to create at the correct index
										if (detachedMode) {
											rowElement = getRowElementCreatedInDetachedMode(containementFeature, elementType, progressMonitor, info, compoundCommand);
										} else {
											rowElement = getRowElementCreatedInAttachedMode(currentContext, containementFeature, elementType, progressMonitor, info, compoundCommand);
										}

										if (null != rowElement) {
											int indexInParent = -1;
											int indexInTable = -1;
											// Manage the index for the added element :
											// if the parent context is the same than the current context, use the index calculated,
											// else this must be an add to the end of feature
											if (currentContext.equals(parentContext)) {
												indexInParent = initialFirstIndexInParent;
												indexInTable = initialFirstRowPosition;
											}

											// Check if the table context is an owner of other created element.
											InsertedElementInNattable foundInsertedElement = null;
											Iterator<InsertedElementInNattable> objectsToAddIterator = objectsToAdd.iterator();
											while (objectsToAddIterator.hasNext() && null == foundInsertedElement) {
												final InsertedElementInNattable insertedElement = objectsToAddIterator.next();
												if (insertedElement.getContext().equals(currentContext) && insertedElement.getContainementFeature().equals(containementFeature)) {
													foundInsertedElement = insertedElement;
												}
											}

											// Create an inserted element item if the parent context doesn't already exist
											if (null == foundInsertedElement) {
												foundInsertedElement = new InsertedElementInNattable(currentContext, containementFeature, indexInParent, indexInTable);
												objectsToAdd.add(foundInsertedElement);
											}
											// Add the created element
											foundInsertedElement.addCreatedElement(rowElement);

											createdElement = true;
										} else {
											multiStatus.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_ElementCantBeAdded));
										}

										lastRowPosition++;
									}
								}

								// If the row element is not (this is not a created element), calculate it
								if (null == rowElement) {
									int rowIndexToModify = tableManager.getBodyLayerStack().getSelectionLayer().getRowIndexByPosition(rowPositionToModify);
									rowElement = AxisUtils.getRepresentedElement(tableManager.getRowElement(rowIndexToModify));
								}

								// Continue if the action choose by user is replace or add (by default, it is replace action)
								if (UserActionConstants.REPLACE_USER_ACTION == currentUserAction || UserActionConstants.ADD_USER_ACTION == currentUserAction) {
									int nbColmnRead = firstColumnPosition;
									final Iterator<String> cellIter = rowValuesAsString.iterator();

									// Check if the progress monitor catch a cancel click
									if (null != progressMonitor && progressMonitor.isCanceled()) {
										progressMonitor.done();
										localDispose();
										return CommandResult.newCancelledCommandResult();
									}

									readChar = readChar + (parser.getReadCharacters() - previousreadChar);
									previousreadChar = parser.getReadCharacters();

									if (null != progressMonitor && readChar > refreshEachReadChar) {
										readChar = 0;
										progressMonitor.worked(refreshEachReadChar);
									}

									// Manage the columns of the row
									while (cellIter.hasNext() && canContinue(multiStatus)) {
										final String valueAsString = cellIter.next();

										// Get the row and the column indexes from their positions
										final int realColumnIndex = tableManager.getBodyLayerStack().getSelectionLayer().getColumnIndexByPosition(nbColmnRead);

										// Get the row object and column attribute from their indexes
										final Object columnElement = getColumnElement(realColumnIndex);

										// Edit the value if this is editable
										final boolean isEditable = null == sharedMap ? CellManagerFactory.INSTANCE.isCellEditable(columnElement, rowElement, tableManager) : CellManagerFactory.INSTANCE.isCellEditable(columnElement, rowElement, sharedMap, tableManager);
										if (isEditable) {
											final AbstractStringValueConverter converter = CellManagerFactory.INSTANCE.getOrCreateStringValueConverterClass(columnElement, rowElement, existingConverters, pasteHelper.getMultiValueSeparator(), tableManager);

											// Get the converted value (to compare with the new one)
											if (!createdElement || attachedMode) {
												final Command command = CellManagerFactory.INSTANCE.getSetStringValueCommand(tableEditingDomain, columnElement, rowElement, valueAsString, converter, tableManager);
												final IStatus commandStatus = getStatusCommand(command);
												if (!commandStatus.isOK()) {
													multiStatus.add(commandStatus);
												} else {
													if (null != command) {
														compoundCommand.append(command);
													}
												}
											} else {
												try {
													CellManagerFactory.INSTANCE.setStringValue(columnElement, rowElement, valueAsString, converter, sharedMap, tableManager);
												} catch (final Exception e) {
													Activator.log.error("Cannot set value '" + valueAsString + "' for element '" + rowElement + "' and feature '" + columnElement + "'", e);
													multiStatus.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Cannot set value '" + valueAsString + "' for element '" + rowElement + "' and feature '" + columnElement + "'"));
												}
											}
										} else {
											// Manage the warnings when the user tried to add a value (except exmpty, N/A or null) in a non-editable cell
											if (isValidValue(valueAsString)) {
												multiStatus.add(new Status(IStatus.WARNING, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_NonEditableCellTriedToBeOverwrited));
											}
										}

										nbColmnRead++;
									}

									if (lastColumnPosition < nbColmnRead) {
										lastColumnPosition = nbColmnRead;
									}
								}
							}
						}
						lastRowPosition++;
					}

					if (canContinue(multiStatus)) {
						// Need to create the add command to add the created elements to their container and to the table if necessary
						if (!objectsToAdd.isEmpty()) {
							if (detachedMode) {
								createTableRowInDetachedModeCommand(compoundCommand, objectsToAdd);
							} else {
								createTableRowInAttachedModeCommand(compoundCommand, objectsToAdd);
							}
						}

						// Execute some actions available in the share map when this is the detached mode
						if (!attachedMode) {
							// Manage the reference to set
							@SuppressWarnings("unchecked")
							final List<IValueSetter> valueToSet = (List<IValueSetter>) sharedMap.get(Constants.REFERENCES_TO_SET_KEY);

							if (valueToSet.size() > 0) {
								for (final IValueSetter current : valueToSet) {
									current.doSetValue(contextEditingDomain);
								}
							}

							// Manage the cells problems to add
							@SuppressWarnings("unchecked")
							final List<Cell> cells = (List<Cell>) sharedMap.get(Constants.CELLS_TO_ADD_KEY);

							// add the created cells to the table
							if (null != cells && !cells.isEmpty()) {
								compoundCommand.append(AddCommand.create(tableEditingDomain, table, NattablePackage.eINSTANCE.getTable_Cells(), cells));
							}
						}
					}


					if (progressMonitor != null) {
						if (progressMonitor.isCanceled()) {
							progressMonitor.done();
							localDispose();
							return CommandResult.newCancelledCommandResult();
						}

						progressMonitor.done();
					}


					localDispose();

					if (resultStatus.isOK()) {
						resultStatus = multiStatus;
					}
				}

				// Check if the status is not CANCEL or ERROR
				if (canContinue(resultStatus) && null != compoundCommand && !compoundCommand.isEmpty() && compoundCommand.canExecute()) {
					// Execute the compound command
					tableEditingDomain.getCommandStack().execute(compoundCommand);

					// Manage the post actions for the detached mode
					if (!objectsToAdd.isEmpty() && !attachedMode) {
						managePostActions(sharedMap);
					}

					return new CommandResult(resultStatus);
				}
				if (!resultStatus.isOK()) {
					// Create an error status
					final MultiStatus errorStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.ERROR, Messages.AbstractPasteInSelectionNattableCommandProvider_ThePasteHasNotBeenDoneBecauseOfSomeProblems, null);
					if (resultStatus.isMultiStatus()) {
						for (final IStatus subStatus : resultStatus.getChildren()) {
							errorStatus.add(subStatus);
						}
					} else {
						errorStatus.add(resultStatus);
					}
					if (IStatus.ERROR != errorStatus.getSeverity()) {
						errorStatus.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, null));
					}
					resultStatus = errorStatus;
				}
				return new CommandResult(resultStatus);
			}
		};
		return pasteAllCommand;
	}

	/**
	 * Get the paste action for the cells paste for columns paste.
	 * 
	 * @param contextEditingDomain
	 *            The context editing domain.
	 * @param tableEditingDomain
	 *            The table editing domain.
	 * @param selectedCells
	 *            The selected cells.
	 * @param openDialog
	 *            Determinate if the dialog must be opened during the process.
	 * @param progressMonitor
	 *            The progress monitor.
	 * @param sharedMap
	 *            The shared map.
	 * @param attachedMode
	 *            Boolean to determinate if the command must be executed with the attached mode or with the detached mode.
	 * @return The command for the paste action.
	 */
	protected ICommand getPasteColumnsFromStringCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final Map<Integer, Object> columns,
			final boolean openDialog, final IProgressMonitor progressMonitor, final Map<Object, Object> sharedMap, final boolean attachedMode) {
		// initialize the progress monitor
		if (null != progressMonitor) {
			progressMonitor.beginTask(PASTE_ACTION_TASK_NAME, this.nbOperationsToDo);
		}

		// 2.2 create the creation request and find the command provider
		final ICommand pasteAllCommand = new AbstractTransactionalCommand(contextEditingDomain, PASTE_COMMAND_NAME, null) {

			/**
			 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
			 *
			 * @param monitor
			 * @param info
			 * @return
			 * @throws ExecutionException
			 */
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				MultiStatus resultStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, Messages.AbstractPasteInSelectionNattableCommandProvider_ThePasteHasBeenDoneWithSomeProblems, null);

				long readChar = 0;
				long previousreadChar = 0;

				// Iterate on selected cells iterator at the same time that on cells
				firstRowPosition = 0;
				firstColumnPosition = -1;
				for (int columnPosition : columns.keySet()) {
					if (-1 == firstColumnPosition) {
						firstColumnPosition = columnPosition;
					} else if (columnPosition < firstColumnPosition) {
						firstColumnPosition = columnPosition;
					}
				}
				lastRowPosition = firstRowPosition;
				lastColumnPosition = firstColumnPosition;

				// Create the compound command for the paste action
				final CompoundCommand compoundCommand = new CompoundCommand("Paste action"); //$NON-NLS-1$

				final Iterator<Object> columnIter = pastedValues.keySet().iterator();

				Collection<String> columnValuesAsString = new ArrayList<String>();

				// Loop on each row
				while (((!isSingleAxisPasted && columnIter.hasNext()) || (isSingleAxisPasted && numberSelectedAxis > 0)) && canContinue(resultStatus)) {
					if (isSingleAxisPasted) {
						numberSelectedAxis--;
					}

					if (!(isSingleAxisPasted && firstColumnPosition < lastColumnPosition)) {
						columnValuesAsString = pastedValues.get(columnIter.next());
					}

					// To avoid blank lines
					if (!columnValuesAsString.isEmpty()) {
						int nbRowRead = firstRowPosition;
						final Iterator<String> cellIter = columnValuesAsString.iterator();

						// Check if the progress monitor catch a cancel click
						if (null != progressMonitor && progressMonitor.isCanceled()) {
							progressMonitor.done();
							localDispose();
							return CommandResult.newCancelledCommandResult();
						}

						readChar = readChar + (parser.getReadCharacters() - previousreadChar);
						previousreadChar = parser.getReadCharacters();

						if (null != progressMonitor && readChar > refreshEachReadChar) {
							readChar = 0;
							progressMonitor.worked(refreshEachReadChar);
						}

						// Get the column indexes from its position
						int realColumnIndex = tableManager.getBodyLayerStack().getSelectionLayer().getColumnIndexByPosition(lastColumnPosition);
						// Get the column attribute from its index
						final Object columnElement = AxisUtils.getRepresentedElement(tableManager.getColumnElement(realColumnIndex));

						// Manage the columns of the row
						while (cellIter.hasNext() && canContinue(resultStatus)) {
							final String valueAsString = cellIter.next();

							// Get the row index from its position
							int realRowIndex = tableManager.getBodyLayerStack().getSelectionLayer().getRowIndexByPosition(nbRowRead);
							// Get the row object from its index
							final Object rowElement = AxisUtils.getRepresentedElement(tableManager.getRowElement(realRowIndex));

							if (!(rowElement instanceof TreeFillingConfiguration)) {
								// Edit the value if this is editable
								final boolean isEditable = CellManagerFactory.INSTANCE.isCellEditable(columnElement, rowElement, sharedMap, tableManager);
								if (isEditable) {
									final AbstractStringValueConverter converter = CellManagerFactory.INSTANCE.getOrCreateStringValueConverterClass(columnElement, rowElement, existingConverters, pasteHelper.getMultiValueSeparator(), tableManager);

									// Get the converted value (to compare with the new one
									final Command command = CellManagerFactory.INSTANCE.getSetStringValueCommand(tableEditingDomain, columnElement, rowElement, valueAsString, converter, tableManager);
									final IStatus commandStatus = getStatusCommand(command);
									if (!commandStatus.isOK()) {
										resultStatus.add(commandStatus);
									} else {
										compoundCommand.append(command);
									}
								} else {
									// Manage the warnings when the user tried to add a value (except exmpty, N/A or null) in a non-editable cell
									if (isValidValue(valueAsString)) {
										resultStatus.add(new Status(IStatus.WARNING, Activator.PLUGIN_ID, Messages.AbstractPasteInSelectionNattableCommandProvider_NonEditableCellTriedToBeOverwrited));
									}
								}
							}
							nbRowRead++;
						}

						if (lastRowPosition < nbRowRead) {
							lastRowPosition = nbRowRead;
						}
					}
					lastColumnPosition++;
				}

				progressMonitor.done();
				localDispose();

				// Check if the status is not CANCEL or ERROR
				if (canContinue(resultStatus) && null != compoundCommand && !compoundCommand.isEmpty() && compoundCommand.canExecute()) {
					// Execute the compound command
					tableEditingDomain.getCommandStack().execute(compoundCommand);

					return new CommandResult(resultStatus);
				}
				if (!resultStatus.isOK()) {
					// Create an error status
					final MultiStatus errorStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.ERROR, Messages.AbstractPasteInSelectionNattableCommandProvider_ThePasteHasNotBeenDoneBecauseOfSomeProblems, null);
					if (resultStatus.isMultiStatus()) {
						for (final IStatus subStatus : resultStatus.getChildren()) {
							errorStatus.add(subStatus);
						}
					} else {
						errorStatus.add(resultStatus);
					}
					errorStatus.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, null));
					resultStatus = errorStatus;
				}
				return new CommandResult(resultStatus);
			}
		};
		return pasteAllCommand;
	}

	/**
	 * This allows to determinate if the process can be continued (calculated from status).
	 * 
	 * @param status
	 *            the status.
	 * @return <code>false</code> if the status is canceled or in error, <code>true</code> otherwise.
	 */
	private boolean canContinue(final IStatus status) {
		return !((IStatus.CANCEL == status.getSeverity()) || IStatus.ERROR == status.getSeverity());
	}

	/**
	 * This allows to check if the value is a valid value (not null, empty or non available).
	 * 
	 * @param value
	 *            the value to check.
	 * @return <code>true</code> if the value is a valid value, <code>false</code> otherwise.
	 */
	protected boolean isValidValue(final String value) {
		boolean result = true;

		if (null != value) {
			if ("null".equals(value) || "".equals(value) || "N/A".equals(value)) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				result = false;
			}
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * This allows to manage the user action when it is necessary.
	 *
	 * @param rowPositionToModify
	 *            The row position to modify (-1 if the row is not found).
	 * @param isInsert
	 *            Determinate if this an insert or just a paste.
	 * @param openDialog
	 *            Determinate if a dialog have to be opened.
	 * @param identifierValue
	 *            The identifier value.
	 * @param multiStatus
	 *            The multi status.
	 */
	protected void manageUserAction(final int rowPositionToModify, final boolean isInsert, final boolean openDialog, final String identifierValue, final MultiStatus multiStatus) {
		currentUserAction = UserActionConstants.UNDEFINED_USER_ACTION;

		if (-1 != rowPositionToModify) {
			if (isInsert) {
				// If the user action is undefined, open the dialog to ask what it is decided to do
				if (UserActionConstants.UNDEFINED_USER_ACTION == userAction) {
					if (openDialog) {
						// Create the message dialog wih a toggle state and open it

						Display.getDefault().syncExec(new Runnable() {

							@Override
							public void run() {
								final MessageDialogWithToggle messageDialog = new MessageDialogWithToggle(Display.getDefault().getActiveShell(), "Confirmation",
										org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(ImageConstants.PAPYRUS_ICON_PATH), "The identifier \'" + identifierValue + "\' was found. What do you want to do ?",
										MessageDialog.QUESTION_WITH_CANCEL, new String[] { "Replace", "Add", "Skip", "Cancel" }, 0, "Apply to all", false) {

									/**
									 * @see org.eclipse.jface.dialogs.MessageDialogWithToggle#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
									 */
									@Override
									protected void createButtonsForButtonBar(final Composite parent) {
										final String[] buttonLabels = getButtonLabels();
										final Button[] buttons = new Button[buttonLabels.length];
										final int defaultButtonIndex = getDefaultButtonIndex();

										// This allow to define the values to the buttons
										for (int i = 0; i < buttonLabels.length; i++) {
											String label = buttonLabels[i];
											Button button = createButton(parent, i, label, defaultButtonIndex == i);
											buttons[i] = button;
										}
										setButtons(buttons);
									}
								};

								// Open the dialog and get its return
								messageDialog.open();
								currentUserAction = messageDialog.getReturnCode();
								if (messageDialog.getToggleState()) {
									userAction = currentUserAction;
								}
							}
						});
					} else {
						// By default, the replacement is used, But this instruction musn't be called
						currentUserAction = UserActionConstants.REPLACE_USER_ACTION;
					}
				} else {
					// The action was already ask to user and it will be repeat for all rows
					currentUserAction = userAction;
				}
			} else {
				// If this is not an insert action, always replace if this is found
				currentUserAction = UserActionConstants.REPLACE_USER_ACTION;
			}
		} else {
			if (isInsert) {
				currentUserAction = UserActionConstants.ADD_USER_ACTION;
				multiStatus.add(new Status(IStatus.WARNING, Activator.PLUGIN_ID, String.format(Messages.AbstractPasteInSelectionNattableCommandProvider_identifierNotFoundInSelectionSoTheObjectWasCreated, identifierValue)));
			} else {
				multiStatus.add(new Status(IStatus.WARNING, Activator.PLUGIN_ID, String.format(Messages.AbstractPasteInSelectionNattableCommandProvider_identifierNotFoundInSelection, identifierValue)));
			}
		}
	}

	/**
	 * This allows to manage the post actions when it is necessary (in the detached mode).
	 * 
	 * @param sharedMap
	 *            the shared map.
	 */
	protected void managePostActions(final Map<Object, Object> sharedMap) {
		// initialize lists
		final Collection<String> postActions = getPostActions();

		// we add the post actions added by cell manager
		// see bug 431691: [Table 2] Paste from Spreadsheet must be able to apply required stereotypes for column properties in all usecases
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=431691
		@SuppressWarnings("unchecked")
		final Collection<String> postActionsAddedByCellManagers = (Collection<String>) sharedMap.get(Constants.ADDITIONAL_POST_ACTIONS_TO_CONCLUDE_PASTE_KEY);
		postActions.addAll(postActionsAddedByCellManagers);

		for (final String currentPostActions : postActions) {
			PastePostActionRegistry.INSTANCE.concludePostAction(tableManager, currentPostActions, sharedMap);
		}
	}

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
	 * Get the index of the axis used as axis identifier.
	 * 
	 * @return <code>-1</code> if the axis doesn't exist, the axis index otherwise.
	 */
	protected int getColumnIndexOfAxisIdentifier() {
		final PasteEObjectConfiguration pasteconfiguration = getPasteconfigurationTopLevel(table);

		int index = -1;
		List<IAxis> axis = null;
		if (!table.isInvertAxis()) {
			axis = tableManager.getColumnAxisManager().getRepresentedContentProvider().getAxis();
		} else {
			axis = tableManager.getRowAxisManager().getRepresentedContentProvider().getAxis();
		}

		int axisIndex = 0;
		while (axisIndex < axis.size() && -1 == index) {
			if (axis.get(axisIndex).getElement().equals(pasteconfiguration.getAxisIdentifier().getElement())) {
				index = axisIndex;
			}
			axisIndex++;
		}

		return index;
	}

	/**
	 * Get the index of first element selected from the table context.
	 * 
	 * @param rows
	 *            The selected rows.
	 * @param currentRowPosition
	 *            The current row position to check.
	 * @param context
	 *            The context of the selection.
	 * @return The index of first element selected from the table context.
	 */
	@SuppressWarnings("unchecked")
	protected int getFirstSelectedElementIndexOfTableContext(final Map<Integer, Object> rows, final int currentRowPosition, final EObject context) {
		int initialFirstIndexInParent = -1;

		if (isInsert) {
			int currentRowIndex = tableManager.getBodyLayerStack().getSelectionLayer().getRowIndexByPosition(currentRowPosition);

			Object rowElement = tableManager.getRowElement(currentRowIndex);
			int depth = getDepthFromObject(rowElement);
			String categoryName = getCategoryFromObject(rowElement);
			final PasteEObjectConfiguration pasteConfiguration = getPasteConfiguration(table, depth, categoryName);

			Object axis = rows.get(currentRowPosition);
			Object object = AxisUtils.getRepresentedElement(axis);
			if (object instanceof EObject) {
				while (object instanceof EObject && null != ((EObject) object).eContainer()) {
					if (((EObject) object).eContainer().equals(context)) {
						final EStructuralFeature containmentFeature = pasteConfiguration.getPasteElementContainementFeature();
						if (containmentFeature.isMany()) {
							initialFirstIndexInParent = ((EObjectContainmentEList<EObject>) context.eGet(containmentFeature)).indexOf(object);
						}
					}
					object = ((EObject) object).eContainer();
				}
			}
		}

		return initialFirstIndexInParent;
	}

	/**
	 * Get the index of first element selected from the table context.
	 * 
	 * @param rows
	 *            The selected rows.
	 * @param currentRowPosition
	 *            The current row position to check.
	 * @return The index of first element selected from the table context.
	 */
	protected EObject getContentOfSelection(final Map<Integer, Object> rows, final int currentRowPosition) {
		EObject context = null;

		if (0 == currentRowPosition) {
			context = table.getContext();
		} else {
			Object axis = rows.get(currentRowPosition);
			Object object = AxisUtils.getRepresentedElement(axis);
			if (object instanceof EObject) {
				context = ((EObject) object).eContainer();
			}
		}

		return context;
	}

	/**
	 * Get the paste configuration of the table.
	 * 
	 * @param table
	 *            The table to manage.
	 * @return The paste configuration of the table.
	 */
	protected abstract PasteEObjectConfiguration getPasteconfigurationTopLevel(final Table table);

	/**
	 * This allows to get the paste configuration corresponding to the depth.
	 * 
	 * @param table
	 *            The table to manage.
	 * @param currentDepth
	 *            The depth to get the paste configuration.
	 * @param categoryName
	 *            The category name searched (can be null or empty for only depth search).
	 * @return The paste configuration corresponding to the depth.
	 */
	protected abstract PasteEObjectConfiguration getPasteConfiguration(final Table table, final int currentDepth, final String categoryName);

	/**
	 * Get the depth from the object in parameter.
	 * 
	 * @param object
	 *            The object corresponding to element or IAxis.
	 * @return The depth from the object in parameter.
	 */
	protected abstract int getDepthFromObject(final Object object);

	/**
	 * Get the category from the object in parameter.
	 * 
	 * @param object
	 *            The object corresponding to element or IAxis.
	 * @return The category from the object in parameter.
	 */
	protected abstract String getCategoryFromObject(final Object object);

	/**
	 * This allows to get the column object to modify.
	 * 
	 * @param realColumnIndex
	 *            The index of the column.
	 * @return The column object to modify.
	 */
	protected abstract Object getColumnElement(int realColumnIndex);

	/**
	 * This allows to get the object to create in the row element in attached mode.
	 * 
	 * @param monitor
	 *            The progress monitor.
	 * @param info
	 *            The info.
	 * @param compoundCommand
	 *            The current compound command used (The action to create the element must be added).
	 * @return The object corresponding to the row element to create.
	 */
	protected abstract Object getRowElementCreatedInAttachedMode(final EObject context, final EStructuralFeature containmentFeature, final IElementType typeToCreate, final IProgressMonitor monitor, final IAdaptable info, final CompoundCommand compoundCommand);

	/**
	 * This allows to get the object to create in the row element in detached mode.
	 * 
	 * @param monitor
	 *            The progress monitor.
	 * @param info
	 *            The infso.
	 * @param compoundCommand
	 *            The current compound command used (The action to create the element must be added).
	 * @return The object corresponding to the row element to create.
	 */
	protected abstract Object getRowElementCreatedInDetachedMode(final EStructuralFeature containmentFeature, final IElementType typeToCreate, final IProgressMonitor monitor, final IAdaptable info, final CompoundCommand compoundCommand);

	/**
	 * This allows to create a row in the table in detached mode.
	 * 
	 * @param compoundCommand
	 *            The compound command to manage.
	 * @param createdElements
	 *            The created elements to manage.
	 * @return The adaptable.
	 * @throws ExecutionException
	 *             The execution exception caught.
	 */
	protected abstract void createTableRowInDetachedModeCommand(final CompoundCommand compoundCommand, final List<InsertedElementInNattable> createdElements) throws ExecutionException;

	/**
	 * This allows to create a row in the table in attached mode.
	 * 
	 * @param compoundCommand
	 *            The compound command to manage.
	 * @param createdElements
	 *            The created element to manage.
	 * @return The adaptable.
	 * @throws ExecutionException
	 *             The execution exception caught.
	 */
	protected abstract void createTableRowInAttachedModeCommand(final CompoundCommand compoundCommand, final List<InsertedElementInNattable> createdElements) throws ExecutionException;

	/**
	 * Get the list of the post actions to do.
	 *
	 * @return
	 * 		the list of the post actions to do.
	 */
	private Collection<String> getPostActions() {
		return this.postActions;
	}

	/**
	 * Dispose fields of the class.
	 */
	protected void localDispose() {
		this.isDisposed = true;
		for (final AbstractStringValueConverter current : existingConverters.values()) {
			current.dispose();
		}
		this.existingConverters.clear();
		this.pastedValues.clear();
		this.numberSelectedAxis = 0;
		this.isSingleAxisPasted = false;
		try {
			this.reader.close();
		} catch (final IOException e) {
			Activator.log.error(e);
		}
	}
}
