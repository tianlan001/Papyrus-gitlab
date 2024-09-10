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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 448065, 485255
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.provider;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IPasteConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.parsers.CSVParser;
import org.eclipse.papyrus.infra.nattable.parsers.CellIterator;
import org.eclipse.papyrus.infra.nattable.parsers.RowIterator;
import org.eclipse.papyrus.infra.nattable.utils.AxisConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.ExtendedCompoundCommand;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.PasteSeverityCode;
import org.eclipse.papyrus.infra.nattable.utils.PasteTreeUtils;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter;

/**
 * Paste command manager for the paste in a tree table.
 */
public class PasteEObjectTreeAxisInNattableCommandProvider extends AbstractPasteEObjectInNattableCommandProvider {

	/**
	 * The character of the indentation for the single column.
	 */
	private static final char INDENTATION_CHARACTER = ' '; // $NON-NLS-1$

	/**
	 * Determinate if the table contains a single header column or multiple.
	 */
	private boolean isSingleHeaderColumnTreeTable;

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
	public PasteEObjectTreeAxisInNattableCommandProvider(final INattableModelManager tableManager, final boolean pasteColumn, final Reader reader, final CSVPasteHelper pasteHelper, final long totalSize) {
		super(tableManager, pasteColumn, reader, pasteHelper, totalSize);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteEObjectInNattableCommandProvider#createParser()
	 */
	@Override
	protected CSVParser createParser() {
		this.isSingleHeaderColumnTreeTable = TableHelper.isSingleColumnTreeTable(table);
		return this.pasteHelper.createParser(reader, isSingleHeaderColumnTreeTable);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteEObjectInNattableCommandProvider#init()
	 */
	@Override
	protected void init() {
		final PasteEObjectConfiguration configuration = (PasteEObjectConfiguration) AxisConfigurationUtils.getIAxisConfigurationUsedInTable(this.table, NattableaxisconfigurationPackage.eINSTANCE.getPasteEObjectConfiguration(), false);
		this.secondAxis = tableManager.getColumnElementsList();
		if (null != configuration) {
			this.postActions = configuration.getPostActions();
			this.detachedMode = configuration.isDetachedMode();
		}
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
			if (current.isDetachedMode()) {
				return true;
			}
		}
		return false;
	}

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
		for (final TreeFillingConfiguration current : FillingConfigurationUtils.getAllTreeFillingConfiguration(table)) {
			if (current.getDepth() == depth) {
				final IPasteConfiguration pasteConf = current.getPasteConfiguration();
				Assert.isNotNull(pasteConf);// must not be null here! (so must be verified before
				pasteConfs.add(pasteConf);
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
						// TODO : doesn't work for stereotype properties
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
		// TODO : verify category name
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteEObjectInNattableCommandProvider#getPasteRowFromStringInDetachedModeCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, org.eclipse.emf.transaction.TransactionalEditingDomain,
	 *      org.eclipse.core.runtime.IProgressMonitor, java.util.Map)
	 */
	protected ICommand getPasteRowFromStringInDetachedModeCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final IProgressMonitor progressMonitor, final Map<Object, Object> sharedMap) {
		return getPasteRowFromStringCommand(contextEditingDomain, tableEditingDomain, progressMonitor, true, sharedMap);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteEObjectInNattableCommandProvider#getPasteRowFromStringInAttachedModeCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, org.eclipse.emf.transaction.TransactionalEditingDomain,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected ICommand getPasteRowFromStringInAttachedModeCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final IProgressMonitor progressMonitor) {
		return getPasteRowFromStringCommand(contextEditingDomain, tableEditingDomain, progressMonitor, false, null);
	}

	/**
	 * This allows to execute the paste from String command (for detached and attached mode).
	 * 
	 * @param contextEditingDomain
	 *            The context editing domain.
	 * @param tableEditingDomain
	 *            The table editing domain.
	 * @param progressMonitor
	 *            The progress monitor used.
	 * @param isDetachedMode
	 *            <code>true</code> when the paste is in the detached mode, <code>false</code> otherwise.
	 * @param sharedMap
	 *            A map used to share objects and informations during the paste between this class and the cell value manager.
	 * @return The command to use to finish the paste
	 */
	protected ICommand getPasteRowFromStringCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final IProgressMonitor progressMonitor, final boolean isDetachedMode,
			final Map<Object, Object> sharedMap) {
		// initialize the progress monitor
		if (null != progressMonitor) {
			progressMonitor.beginTask(PASTE_ACTION_TASK_NAME, this.nbOperationsToDo);
		}

		final boolean isSingleHeaderColumnTreeTable = TableHelper.isSingleColumnTreeTable(table);

		// 2.2 create the creation request and find the command provider
		final ICommand pasteAllCommand = new AbstractTransactionalCommand(contextEditingDomain, PASTE_COMMAND_NAME, null) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
			 */
			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				final ExtendedCompoundCommand compoundCommand = new ExtendedCompoundCommand(Messages.PasteEObjectAxisInTableCommandProvider_PasteInTableCommandName);

				// Manage a list of created elements to create a unique add command for all created elements
				final List<Object> objectsToAdd = new ArrayList<Object>();

				final List<IStatus> resultStatus = new ArrayList<IStatus>();

				long readChar = 0;
				long previousreadChar = 0;

				// this map stores the last created object to a depth.
				// its allows us to find easily the context to use for each created element
				final Map<Integer, EObject> contextMap = new HashMap<Integer, EObject>();
				contextMap.put(Integer.valueOf(-1), table.getContext());

				// 2. create a map with the last paste configuration used by depth
				final Map<Integer, PasteEObjectConfiguration> confMap = new HashMap<Integer, PasteEObjectConfiguration>();

				final RowIterator rowIter = parser.parse();
				int nbReadLine = 0;

				// Manage the rows paste
				while (rowIter.hasNext()) {
					final CellIterator cellIter = rowIter.next();
					nbReadLine++;
					if (!cellIter.hasNext()) {
						continue;// to avoid blank line
					}

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

					// the iterator on columns
					final Iterator<?> secondAxisIterator = secondAxis.iterator();

					// Manage the first column of the row
					while (cellIter.hasNext()) {
						String valueAsString = cellIter.next();
						int nbReadCell = 1;

						if (isSingleHeaderColumnTreeTable && !valueAsString.isEmpty()) {
							// If the table is a single header column, parse the value string to manage the correct depth
							// (manage each separator character as empty cell)
							while (INDENTATION_CHARACTER == valueAsString.charAt(0)) {
								nbReadCell++;
								valueAsString = valueAsString.substring(1);
							}
						} else {
							// test if the value is empty (we are in the tree header)
							while (cellIter.hasNext() && valueAsString.isEmpty()) {
								valueAsString = cellIter.next();
								nbReadCell++;
							}
							// Remove the whitespace on beginning
							if (isSingleHeaderColumnTreeTable && !valueAsString.isEmpty()) {
								while (INDENTATION_CHARACTER == valueAsString.charAt(0)) {
									valueAsString = valueAsString.substring(1);
								}
							}
						}

						int depth = -1;
						boolean isCategory = false;
						try {
							depth = getDepth(nbReadCell);
							isCategory = isCategory(nbReadCell);
						} catch (final UnsupportedOperationException ex) {
							final String message = NLS.bind("No defined depth for line {0}", nbReadCell); //$NON-NLS-1$
							// The following lines allows to cancel all the paste if a problem of depth appears
							// If a partial paste is authorized, remove this lines
							Activator.log.error(message, ex);
							final IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, PasteSeverityCode.PASTE_ERROR__MORE_LINES_THAN_DEPTH, message, null);
							if (null != progressMonitor) {
								progressMonitor.done();
							}
							localDispose();
							return new CommandResult(status);
						}

						if (isCategory) {
							confMap.put(Integer.valueOf(depth), (PasteEObjectConfiguration) getPasteConfigurationsFor(depth, valueAsString));
							// lastConfiguration = (PasteEObjectConfiguration) getPasteConfigurationsFor(depth, valueAsString);
							while (cellIter.hasNext()) {
								cellIter.next();
							}
							break;
						}

						// we get the paste configuration to use
						PasteEObjectConfiguration pasteConfToUse = confMap.get(Integer.valueOf(depth));
						if (null == pasteConfToUse) {
							pasteConfToUse = (PasteEObjectConfiguration) getPasteConfigurationsFor(depth, null);
							if (null != pasteConfToUse) {
								confMap.put(Integer.valueOf(depth), pasteConfToUse);
							} else {
								final IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, PasteSeverityCode.PASTE_ERROR__NO_PASTE_CONFIGURATION, NLS.bind("No paste configuration found for the depth {0}", depth), null);
								if (null != progressMonitor) {
									progressMonitor.done();
								}
								localDispose();
								return new CommandResult(status);
							}
						}

						// Create the element (depending to the detached or attached mode)
						final Object createdElement = createElement(isDetachedMode, compoundCommand, contextMap, depth, valueAsString, pasteConfToUse, monitor, info);

						if (null != createdElement) {
							if (createdElement instanceof EObject) {

								final EObject context = contextMap.get(depth - 1);

								// we add the created element to the table, only if its parent is the context of the table and if the table is filled by DnD
								if (!FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0) && context.equals(tableContext)) {
									objectsToAdd.add(createdElement);
								}
							}

							crossCellIteratorToFirstBodyCell(cellIter, nbReadCell);

							while (secondAxisIterator.hasNext() && cellIter.hasNext()) {
								// we must exit of the header part!
								valueAsString = cellIter.next();
								// Remove the whitespace on beginning
								if (isSingleHeaderColumnTreeTable && !valueAsString.isEmpty()) {
									while (INDENTATION_CHARACTER == valueAsString.charAt(0)) {
										valueAsString = valueAsString.substring(1);
									}
								}

								final Object currentAxis = secondAxisIterator.next();
								final Object columnObject = currentAxis;
								final Object rowObject = createdElement;

								setValue(isDetachedMode, compoundCommand, resultStatus, rowObject, columnObject, valueAsString, sharedMap);
							}

							int tooManyCellOnRow = 0;
							while (cellIter.hasNext()) {
								cellIter.next();// required
								tooManyCellOnRow++;
							}

							if (tooManyCellOnRow != 0) {
								final String message = NLS.bind("There are too many cells on the rows number {0}", nbReadLine);
								final IStatus status = new Status(IStatus.WARNING, Activator.PLUGIN_ID, PasteSeverityCode.PASTE_WARNING__TOO_MANY_CELLS_ON_ROWS, message, null);
								resultStatus.add(status);
							}
						}
					}
				}

				// Manage the rows to add by a final command (manage it at the end of the global command)
				final CompoundCommand addRowsCommand = new CompoundCommand(Messages.PasteEObjectAxisInTableCommandProvider_AddRowsCommandName);
				for (final Object createdElement : objectsToAdd) {
					final Command addRowElementCommand = tableManager.getAddRowElementCommand(Collections.singleton(createdElement));
					if (addRowElementCommand.canExecute()) {
						addRowsCommand.append(addRowElementCommand);
					}
				}
				if (!addRowsCommand.isEmpty()) {
					compoundCommand.append(addRowsCommand);
				}

				// Execute the global command
				if (null != compoundCommand && !compoundCommand.isEmpty() && compoundCommand.canExecute()) {
					tableEditingDomain.getCommandStack().execute(compoundCommand);
				}

				if (null != progressMonitor) {
					progressMonitor.done();
				}
				localDispose();
				if (resultStatus.isEmpty()) {
					return CommandResult.newOKCommandResult();
				} else {
					final IStatus resultingStatus = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, resultStatus.toArray(new IStatus[resultStatus.size()]), "The paste has been done, but we found some problems", null);
					return new CommandResult(resultingStatus);
				}
			}
		};
		return pasteAllCommand;
	}

	/**
	 * 
	 * @param cellIter
	 *            The cellIterator
	 * @param nbReadCell
	 *            The number of cells read.
	 */
	protected void crossCellIteratorToFirstBodyCell(final CellIterator cellIter, int nbReadCell) {
		// If this is a single column header tree table, we don't need to do anything, only the first column is used for the header in the excel spearsheet
		if (!TableHelper.isSingleColumnTreeTable(table)) {
			int nbColumns = (FillingConfigurationUtils.getMaxDepthForTree(table) + 1) * 2;
			if (!FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0)) {
				nbColumns--;
			}

			// exit of the header part
			final List<Integer> hiddenDepth = StyleUtils.getHiddenDepths(table);
			final int nbVisibleColumns = nbColumns - hiddenDepth.size();

			while (nbReadCell < nbVisibleColumns) {
				cellIter.next();
				nbReadCell++;
			}
		}
	}

	/**
	 * Check if this is a category.
	 * 
	 * @param nbReadCell
	 *            The number of cells read.
	 * @return <code>true</code> if this is a category, <code>false</code> otherwise.
	 */
	private boolean isCategory(final int nbReadCell) {
		return PasteTreeUtils.isCategory(nbReadCell, FillingConfigurationUtils.getMaxDepthForTree(table), StyleUtils.getHiddenDepths(table), FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0));
	}

	/**
	 * Get the depth corresponding to the number of cells read
	 * 
	 * @param nbReadCell
	 *            The number of cell read.
	 * @return The depth number.
	 */
	private int getDepth(final int nbReadCell) {
		return PasteTreeUtils.getDepth(nbReadCell, FillingConfigurationUtils.getMaxDepthForTree(table), StyleUtils.getHiddenDepths(table), FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0));
	}

	/**
	 * This allows to manage the creation of an element and return it.
	 * 
	 * @param isDetachedMode
	 *            <code>true</code> when the paste is in the detached mode, <code>false</code> otherwise.
	 * @param compoundCommand
	 *            The main compound command.
	 * @param contextMap
	 *            The map which stores the last created object to a depth.
	 * @param depth
	 *            The depth for the element to create.
	 * @param valueAsString
	 *            The value got in the clipboard.
	 * @param pasteConfToUse
	 *            The paste configuration to use.
	 * @param monitor
	 *            The progress monitor.
	 * @param info
	 *            The adaptable provided by the operation history.
	 * @return The created element.
	 * @throws ExecutionException
	 *             The exception during the element's creation.
	 */
	private Object createElement(final boolean isDetachedMode, final ExtendedCompoundCommand compoundCommand, final Map<Integer, EObject> contextMap, final int depth, final String valueAsString, final PasteEObjectConfiguration pasteConfToUse,
			final IProgressMonitor monitor, final IAdaptable info)
			throws ExecutionException {
		return isDetachedMode ? createElementInDetachedMode(compoundCommand, contextMap, depth, valueAsString, pasteConfToUse, monitor, info) : createElementInAttachedMode(compoundCommand, contextMap, depth, valueAsString, pasteConfToUse, monitor, info);
	}

	/**
	 * This allows to manage the creation of an element in detached mode and return it.
	 * 
	 * @param compoundCommand
	 *            The main compound command.
	 * @param contextMap
	 *            The map which stores the last created object to a depth.
	 * @param depth
	 *            The depth for the element to create.
	 * @param valueAsString
	 *            The value got in the clipboard.
	 * @param pasteConfToUse
	 *            The paste configuration to use.
	 * @param monitor
	 *            The progress monitor.
	 * @param info
	 *            The adaptable provided by the operation history.
	 * @return The created element.
	 */
	protected Object createElementInDetachedMode(final ExtendedCompoundCommand compoundCommand, final Map<Integer, EObject> contextMap, final int depth, final String valueAsString, final PasteEObjectConfiguration pasteConfToUse, final IProgressMonitor monitor,
			final IAdaptable info) {
		// get the element type to use to create the element
		final IElementType typeToCreate = ElementTypeRegistry.getInstance().getType(pasteConfToUse.getPastedElementId());

		// Get the class type to create and get its factory
		final EClass eClassToCreate = typeToCreate.getEClass();
		final EFactory eFactory = eClassToCreate.getEPackage().getEFactoryInstance();

		// get the element type to use to create the element
		final Object createdElement = eFactory.create(eClassToCreate);

		// 4. we use the label to do a set name command on the created element
		if (createdElement instanceof EObject) {
			final EObject eobject = (EObject) createdElement;

			// add the created element to the context map
			contextMap.put(Integer.valueOf(depth), (EObject) createdElement);

			// get the context to use
			final EObject context = contextMap.get(depth - 1);
			final EStructuralFeature containmentFeature = pasteConfToUse.getPasteElementContainementFeature();
			if (containmentFeature.isMany()) {
				compoundCommand.append(AddCommand.create(tableEditingDomain, context, containmentFeature, eobject));
			} else {
				compoundCommand.append(SetCommand.create(tableEditingDomain, context, containmentFeature, eobject));
			}

			// get the feature used as ID for the element
			final EStructuralFeature nameFeature = ((EObject) createdElement).eClass().getEStructuralFeature("name"); //$NON-NLS-1$
			if (null != nameFeature) {
				eobject.eSet(nameFeature, valueAsString);
			}
		}

		return createdElement;
	}

	/**
	 * This allows to manage the creation of an element in attached mode and return it.
	 * 
	 * @param compoundCommand
	 *            The main compound command.
	 * @param contextMap
	 *            The map which stores the last created object to a depth.
	 * @param depth
	 *            The depth for the element to create.
	 * @param valueAsString
	 *            The value got in the clipboard.
	 * @param pasteConfToUse
	 *            The paste configuration to use.
	 * @param monitor
	 *            The progress monitor.
	 * @param info
	 *            The adaptable provided by the operation history.
	 * @return The created element.
	 * @throws ExecutionException
	 *             The exception during the element's creation.
	 */
	protected Object createElementInAttachedMode(final ExtendedCompoundCommand compoundCommand, final Map<Integer, EObject> contextMap, final int depth, final String valueAsString, final PasteEObjectConfiguration pasteConfToUse, final IProgressMonitor monitor,
			final IAdaptable info)
			throws ExecutionException {
		EObject createdElement = null;

		// get the element type to use to create the element
		final IElementType typeToCreate = ElementTypeRegistry.getInstance().getType(pasteConfToUse.getPastedElementId());

		final EStructuralFeature containmentFeature = pasteConfToUse.getPasteElementContainementFeature();

		// get the context to use
		final EObject context = contextMap.get(depth - 1);
		final CreateElementRequest createRequest1 = new CreateElementRequest(contextEditingDomain, context, typeToCreate, (EReference) containmentFeature);
		final IElementEditService creationContextCommandProvider = ElementEditServiceUtils.getCommandProvider(context);

		final ICommand commandCreation = creationContextCommandProvider.getEditCommand(createRequest1);
		if (null != commandCreation && commandCreation.canExecute()) {

			// 1. we create the element
			final Command emfCommandCreation = GMFtoEMFCommandWrapper.wrap(commandCreation);
			emfCommandCreation.execute();

			// Add the creation command to the compound command
			compoundCommand.append(emfCommandCreation);

			// 2. we get the result of the command
			final CommandResult res = commandCreation.getCommandResult();

			// 3 we update the map
			createdElement = (EObject) res.getReturnValue();
			contextMap.put(Integer.valueOf(depth), createdElement);

			// 4. we use the label to do a set name command on the created element
			// get the feature used as ID for the element
			final EStructuralFeature nameFeature = createdElement.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
			if (null != nameFeature) {
				final SetRequest setNameRequest = new SetRequest(contextEditingDomain, createdElement, nameFeature, valueAsString);
				final IElementEditService createdElementCommandProvider = ElementEditServiceUtils.getCommandProvider(createdElement);
				if (null != createdElementCommandProvider) {
					final ICommand setName = createdElementCommandProvider.getEditCommand(setNameRequest);
					if (setName != null && setName.canExecute()) {

						// We create the set command
						final Command emfSetNameCommandCreation = GMFtoEMFCommandWrapper.wrap(setName);
						emfSetNameCommandCreation.execute();

						// Add the set name command to the compound command
						compoundCommand.append(emfSetNameCommandCreation);
					}
				}
			}
		}

		return createdElement;
	}

	/**
	 * This allows to set a feature value.
	 * 
	 * @param isDetachedMode
	 *            <code>true</code> when the paste is in the detached mode, <code>false</code> otherwise.
	 * @param compoundCommand
	 *            The main compound command.
	 * @param resultStatus
	 *            the main result status.
	 * @param rowObject
	 *            The row object to edit.
	 * @param columnObject
	 *            The column object to edit.
	 * @param valueAsString
	 *            The value to set.
	 * @param sharedMap
	 *            A map used to share objects and informations during the paste between this class and the cell value manager.
	 */
	private void setValue(final boolean isDetachedMode, final ExtendedCompoundCommand compoundCommand, final List<IStatus> resultStatus, final Object rowObject, final Object columnObject, final String valueAsString, final Map<Object, Object> sharedMap) {
		if (isDetachedMode) {
			setValueInDetachedMode(rowObject, columnObject, valueAsString, sharedMap);
		} else {
			setValueInAttachedMode(compoundCommand, resultStatus, rowObject, columnObject, valueAsString);
		}
	}

	/**
	 * This allows to set a feature value in the detached mode.
	 * 
	 * @param rowObject
	 *            The row object to edit.
	 * @param columnObject
	 *            The column object to edit.
	 * @param valueAsString
	 *            The value to set.
	 * @param sharedMap
	 *            A map used to share objects and informations during the paste between this class and the cell value manager.
	 */
	private void setValueInDetachedMode(final Object rowObject, final Object columnObject, final String valueAsString, final Map<Object, Object> sharedMap) {
		final boolean isEditable = CellManagerFactory.INSTANCE.isCellEditable(columnObject, rowObject, sharedMap, tableManager);
		if (isEditable) {
			final AbstractStringValueConverter converter = CellManagerFactory.INSTANCE.getOrCreateStringValueConverterClass(columnObject, rowObject, existingConverters, pasteHelper.getMultiValueSeparator(), tableManager);
			CellManagerFactory.INSTANCE.setStringValue(columnObject, rowObject, valueAsString, converter, sharedMap, tableManager);
		}
	}

	/**
	 * This allows to set a feature value in the attached mode.
	 * 
	 * @param compoundCommand
	 *            The main compound command.
	 * @param resultStatus
	 *            the main result status.
	 * @param rowObject
	 *            The row object to edit.
	 * @param columnObject
	 *            The column object to edit.
	 * @param valueAsString
	 *            The value to set.
	 */
	private void setValueInAttachedMode(final ExtendedCompoundCommand compoundCommand, final List<IStatus> resultStatus, final Object rowObject, final Object columnObject, final String valueAsString) {
		final boolean isEditable = CellManagerFactory.INSTANCE.isCellEditable(columnObject, rowObject, tableManager);

		if (isEditable) {
			final AbstractStringValueConverter converter = CellManagerFactory.INSTANCE.getOrCreateStringValueConverterClass(columnObject, rowObject, existingConverters, pasteHelper.getMultiValueSeparator(), tableManager);
			final Command setValueCommand = CellManagerFactory.INSTANCE.getSetStringValueCommand(contextEditingDomain, columnObject, rowObject, valueAsString, converter, tableManager);
			final IStatus commandStatus = getStatusCommand(setValueCommand);
			if (!commandStatus.isOK()) {
				resultStatus.add(commandStatus);
			} else {
				if (null != setValueCommand) {
					compoundCommand.append(setValueCommand);
				}
			}
		}
	}
}
