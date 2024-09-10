/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 485255
 *  
 /*****************************************************************************/
package org.eclipse.papyrus.infra.nattable.provider;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.Cell;
import org.eclipse.papyrus.infra.nattable.parsers.CSVParser;
import org.eclipse.papyrus.infra.nattable.parsers.CellIterator;
import org.eclipse.papyrus.infra.nattable.parsers.RowIterator;
import org.eclipse.papyrus.infra.nattable.paste.IValueSetter;
import org.eclipse.papyrus.infra.nattable.paste.PastePostActionRegistry;
import org.eclipse.papyrus.infra.nattable.utils.AxisConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.ExtendedCompoundCommand;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter;

/**
 * Paste command manager for the paste in a flat table.
 */
public class PasteEObjectAxisInNattableCommandProvider extends AbstractPasteEObjectInNattableCommandProvider {

	/**
	 * the containment feature to use
	 */
	private EStructuralFeature containmentFeature;

	/**
	 * the type to create
	 */
	private IElementType typeToCreate;

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
	public PasteEObjectAxisInNattableCommandProvider(final INattableModelManager tableManager, final boolean pasteColumn, final Reader reader, final CSVPasteHelper pasteHelper, final long totalSize) {
		super(tableManager, pasteColumn, reader, pasteHelper, totalSize);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteEObjectInNattableCommandProvider#createParser()
	 */
	@Override
	protected CSVParser createParser() {
		return this.pasteHelper.createParser(reader);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteEObjectInNattableCommandProvider#init()
	 */
	@Override
	protected void init() {
		if (this.pasteColumn) {
			this.secondAxis = tableManager.getRowElementsList();
		} else {
			this.secondAxis = tableManager.getColumnElementsList();
		}

		final PasteEObjectConfiguration configuration = (PasteEObjectConfiguration) AxisConfigurationUtils.getIAxisConfigurationUsedInTable(this.table, NattableaxisconfigurationPackage.eINSTANCE.getPasteEObjectConfiguration(), this.pasteColumn);
		if (null != configuration) {
			this.containmentFeature = configuration.getPasteElementContainementFeature();
			this.typeToCreate = ElementTypeRegistry.getInstance().getType(configuration.getPastedElementId());
			this.postActions = new ArrayList<String>(configuration.getPostActions());
			this.detachedMode = configuration.isDetachedMode();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteEObjectInNattableCommandProvider#getPasteRowFromStringInDetachedModeCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, org.eclipse.emf.transaction.TransactionalEditingDomain,
	 *      org.eclipse.core.runtime.IProgressMonitor, java.util.Map)
	 */
	protected ICommand getPasteRowFromStringInDetachedModeCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final IProgressMonitor progressMonitor, final Map<Object, Object> sharedMap) {
		if (null != progressMonitor) {
			progressMonitor.beginTask(PASTE_ACTION_TASK_NAME, this.nbOperationsToDo);// +1 to add the created elements to the table
		}
		// the list of the created elements
		final List<Object> createdElements = new ArrayList<Object>();

		// 2.2 create the creation request and find the command provider
		final EClass eClassToCreate = this.typeToCreate.getEClass();
		final EFactory eFactory = eClassToCreate.getEPackage().getEFactoryInstance();

		// 2.3 create the axis
		int nbCreatedElements = 0;

		// we refresh the dialog each X read char
		long readChar = 0;
		long previousreadChar = 0;
		final RowIterator rowIter = this.parser.parse();
		while (rowIter.hasNext()) {
			final CellIterator cellIter = rowIter.next();
			if (!cellIter.hasNext()) {
				continue;// to avoid blank line
			}
			if (null != progressMonitor && progressMonitor.isCanceled()) {
				// the user click on the cancel button
				progressMonitor.done();
				localDispose();
				return null;
			}

			readChar = readChar + (parser.getReadCharacters() - previousreadChar);
			previousreadChar = parser.getReadCharacters();


			if (null != progressMonitor && readChar > refreshEachReadChar) {
				readChar = 0;
				progressMonitor.subTask(NLS.bind("{0} {1} have been created.", new Object[] { nbCreatedElements, typeToCreate.getEClass().getName() }));
				progressMonitor.worked(refreshEachReadChar);
			}
			nbCreatedElements++;

			// 2.3.3 we create the element itself
			final EObject createdElement = eFactory.create(eClassToCreate);

			createdElements.add(createdElement);
			nbCreatedElements++;
			for (final String currentPostActions : this.postActions) {
				PastePostActionRegistry.INSTANCE.doPostAction(this.tableManager, currentPostActions, tableContext, createdElement, sharedMap, null);// TODO : remove this parameter
			}

			// 2.3.4 we set these properties values
			final Iterator<Object> secondAxisIterator = secondAxis.iterator();
			while (secondAxisIterator.hasNext() && cellIter.hasNext()) {
				final Object currentAxis = secondAxisIterator.next();
				final String valueAsString = cellIter.next();
				final Object columnObject = currentAxis;
				final Object rowObject = createdElement;

				final boolean isEditable = CellManagerFactory.INSTANCE.isCellEditable(columnObject, rowObject, sharedMap, tableManager);
				if (isEditable) {
					final AbstractStringValueConverter converter = CellManagerFactory.INSTANCE.getOrCreateStringValueConverterClass(columnObject, rowObject, existingConverters, this.pasteHelper.getMultiValueSeparator(), tableManager);
					CellManagerFactory.INSTANCE.setStringValue(columnObject, rowObject, valueAsString, converter, sharedMap, tableManager);
				}
			}

			// TODO : do something to say that the number of cell is not correct!
			while (cellIter.hasNext()) {
				cellIter.next();// required!
			}
		}

		// 2.4 we add the created elements to the table
		final AbstractTransactionalCommand pasteCommand = new AbstractTransactionalCommand(tableEditingDomain, PASTE_COMMAND_NAME, null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				final ExtendedCompoundCommand compoundCommand = new ExtendedCompoundCommand(Messages.PasteEObjectAxisInTableCommandProvider_PasteInTableCommandName);

				// initialize lists
				final Collection<String> postActions = getPostActions();

				// we add the post actions added by cell manager
				// see bug 431691: [Table 2] Paste from Spreadsheet must be able to apply required stereotypes for column properties in all usecases
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=431691
				@SuppressWarnings("unchecked")
				final Collection<String> postActionsAddedByCellManagers = (Collection<String>) sharedMap.get(Constants.ADDITIONAL_POST_ACTIONS_TO_CONCLUDE_PASTE_KEY);
				postActions.addAll(postActionsAddedByCellManagers);

				@SuppressWarnings("unchecked")
				final List<Cell> cells = (List<Cell>) sharedMap.get(Constants.CELLS_TO_ADD_KEY);
				@SuppressWarnings("unchecked")
				final List<IValueSetter> valueToSet = (List<IValueSetter>) sharedMap.get(Constants.REFERENCES_TO_SET_KEY);

				int nbTasks = 1; // to add created elements to the model
				nbTasks = nbTasks + 1; // to add createds elements to the table
				nbTasks = nbTasks + postActions.size();// to do post actions after the attachment to the model
				nbTasks = nbTasks + 1; // to attach the cells to the model
				nbTasks = nbTasks + valueToSet.size(); // to set the references values

				if (null != progressMonitor) {
					if (progressMonitor.isCanceled()) {
						progressMonitor.done();
						localDispose();
						return CommandResult.newCancelledCommandResult();
					}
					progressMonitor.beginTask(Messages.PasteEObjectAxisInTableCommandProvider_FinishingThePaste, nbTasks);
				}

				// 1. Add the elements to the context
				if(!createdElements.isEmpty()){
					compoundCommand.append(AddCommand.create(contextEditingDomain, tableContext, containmentFeature, createdElements));
				}

				if (null != progressMonitor) {
					if (progressMonitor.isCanceled()) {
						progressMonitor.done();
						localDispose();
						return CommandResult.newCancelledCommandResult();
					}
					progressMonitor.worked(1);
					progressMonitor.subTask(Messages.PasteEObjectAxisInTableCommandProvider_AddingElementToTheTable);
				}

				Command cmd = null;
				if (pasteColumn) {
					cmd = tableManager.getAddColumnElementCommand(createdElements); // TODO remove one of these 2 lines
				} else {
					cmd = tableManager.getAddRowElementCommand(createdElements);
				}
				if (null != cmd) {// could be null
					compoundCommand.appendIfCanExecute(cmd);
				}

				if (null != progressMonitor) {
					if (progressMonitor.isCanceled()) {
						progressMonitor.done();
						localDispose();
						return CommandResult.newCancelledCommandResult();
					}
					progressMonitor.worked(1);
					progressMonitor.subTask(Messages.PasteEObjectAxisInTableCommandProvider_DoingAdditionalActions);
				}


				for (final String currentPostActions : postActions) {
					PastePostActionRegistry.INSTANCE.concludePostAction(tableManager, currentPostActions, sharedMap);
					progressMonitor.worked(1);
				}


				if (null != progressMonitor) {
					if (progressMonitor.isCanceled()) {
						progressMonitor.done();
						localDispose();
						return CommandResult.newCancelledCommandResult();
					}
					progressMonitor.worked(1);
					progressMonitor.subTask(Messages.PasteEObjectAxisInTableCommandProvider_LinkingReferencesToTheModel);
				}

				// we set the references

				if (valueToSet.size() > 0) {
					for (final IValueSetter current : valueToSet) {
						current.doSetValue(contextEditingDomain);
						if (null != progressMonitor) {
							if (progressMonitor.isCanceled()) {
								progressMonitor.done();
								localDispose();
								return CommandResult.newCancelledCommandResult();
							}
							progressMonitor.worked(1);
						}
					}
				}

				// the cells must be attached at the end (in order to update properly the cell map in the table manager
				if (null != progressMonitor) {
					if (progressMonitor.isCanceled()) {
						progressMonitor.done();
						localDispose();
						return CommandResult.newCancelledCommandResult();
					}
					progressMonitor.worked(1);
				}

				// add the created cells to the table
				if(!cells.isEmpty()){
					compoundCommand.append(AddCommand.create(tableEditingDomain, table, NattablePackage.eINSTANCE.getTable_Cells(), cells));
				}

				// Execute the global command
				if (null != compoundCommand && !compoundCommand.isEmpty() && compoundCommand.canExecute()) {
					tableEditingDomain.getCommandStack().execute(compoundCommand);
				}

				if (null != progressMonitor) {
					progressMonitor.done();
				}
				localDispose();
				return CommandResult.newOKCommandResult();
			}
		};

		return pasteCommand;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteEObjectInNattableCommandProvider#getPasteRowFromStringInAttachedModeCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, org.eclipse.emf.transaction.TransactionalEditingDomain,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected ICommand getPasteRowFromStringInAttachedModeCommand(final TransactionalEditingDomain contextEditingDomain, final TransactionalEditingDomain tableEditingDomain, final IProgressMonitor progressMonitor) {
		// initialize the progress monitor
		if (null != progressMonitor) {
			progressMonitor.beginTask(PASTE_ACTION_TASK_NAME, this.nbOperationsToDo);
		}

		// 2.2 create the creation request and find the command provider
		final CreateElementRequest createRequest = new CreateElementRequest(contextEditingDomain, this.tableContext, this.typeToCreate, (EReference) this.containmentFeature);
		final IElementEditService tableContextCommandProvider = ElementEditServiceUtils.getCommandProvider(tableContext);

		final ICommand pasteAllCommand = new AbstractTransactionalCommand(contextEditingDomain, PASTE_COMMAND_NAME, null) {

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				final ExtendedCompoundCommand compoundCommand = new ExtendedCompoundCommand(Messages.PasteEObjectAxisInTableCommandProvider_PasteInTableCommandName);

				final List<IStatus> resultStatus = new ArrayList<IStatus>();

				long readChar = 0;
				long previousreadChar = 0;

				final RowIterator rowIter = parser.parse();
				int nbCreatedElements = 0;
				while (rowIter.hasNext()) {
					final CellIterator cellIter = rowIter.next();
					if (!cellIter.hasNext()) {
						continue;// to avoid blank line
					}
					if (null != progressMonitor && progressMonitor.isCanceled()) {
						progressMonitor.done();
						localDispose();
						return CommandResult.newCancelledCommandResult();
					}
					readChar = readChar + (parser.getReadCharacters() - previousreadChar);
					previousreadChar = parser.getReadCharacters();
					if (null != progressMonitor && readChar > refreshEachReadChar) {
						readChar = 0;
						progressMonitor.subTask(NLS.bind("{0} {1} have been created.", new Object[] { typeToCreate.getEClass().getName(), nbCreatedElements }));
						progressMonitor.worked(refreshEachReadChar);
					}
					nbCreatedElements++;
					final ICommand commandCreation = tableContextCommandProvider.getEditCommand(createRequest);
					if (commandCreation.canExecute()) {
						// 1. we create the element
						// we execute the creation command
						final Command emfCommandCreation = GMFtoEMFCommandWrapper.wrap(commandCreation);
						emfCommandCreation.execute();

						// Add the creation command to the compound command
						compoundCommand.append(emfCommandCreation);

						// 2. we add it to the table
						final CommandResult res = commandCreation.getCommandResult();

						final Object createdElement = res.getReturnValue();
						final Command addCommand;
						if (pasteColumn) {
							addCommand = tableManager.getAddColumnElementCommand(Collections.singleton(createdElement));
						} else {
							addCommand = tableManager.getAddRowElementCommand(Collections.singleton(createdElement));
						}
						if (null != addCommand) {// can be null
							compoundCommand.append(addCommand);
						}

						// 3. we set the values
						final Iterator<?> secondAxisIterator = secondAxis.iterator();
						while (secondAxisIterator.hasNext() && cellIter.hasNext()) {
							final Object currentAxis = secondAxisIterator.next();
							final String valueAsString = cellIter.next();
							final Object columnObject;
							final Object rowObject;
							if (pasteColumn) {
								columnObject = createdElement;
								rowObject = currentAxis;
							} else {
								columnObject = currentAxis;
								rowObject = createdElement;
							}

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
						// TODO inform the user
						while (cellIter.hasNext()) {
							cellIter.next();// required
						}
					}
				}

				// Execute the global command
				if (null != compoundCommand && !compoundCommand.isEmpty() && compoundCommand.canExecute()) {
					tableEditingDomain.getCommandStack().execute(compoundCommand);
				}

				progressMonitor.done();
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteEObjectInNattableCommandProvider#localDispose()
	 */
	protected void localDispose() {
		super.localDispose();
		this.typeToCreate = null;
		this.containmentFeature = null;
	}

}
