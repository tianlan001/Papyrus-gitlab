/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.AxisConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.InsertedElementInNattable;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

/**
 * This class manage the paste in tree table when a selection is available.
 */
public class PasteInSelectionNattableCommandProvider extends AbstractPasteInSelectionNattableCommandProvider {

	/**
	 * The paste configuration of the top level.
	 */
	protected PasteEObjectConfiguration pasteConfigurationTopLevel = null;

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
	public PasteInSelectionNattableCommandProvider(final INattableModelManager tableManager, final boolean pasteColumn, final boolean isInsert, final Reader reader, final CSVPasteHelper pasteHelper,
			final TableSelectionWrapper tableSelectionWrapper, final int preferredUserAction, final long totalSize) {
		super(tableManager, pasteColumn, isInsert, reader, pasteHelper, tableSelectionWrapper, preferredUserAction, totalSize);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#init()
	 */
	@Override
	protected void init() {
		this.detachedMode = isPasteInDetachedMode(table);
		PasteEObjectConfiguration configuration = getPasteconfigurationTopLevel(this.table);
		if (configuration != null) {
			this.postActions = new ArrayList<String>(configuration.getPostActions());
			this.detachedMode = configuration.isDetachedMode();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getPasteconfiguration(org.eclipse.papyrus.infra.nattable.model.nattable.Table)
	 */
	@Override
	protected PasteEObjectConfiguration getPasteconfigurationTopLevel(final Table table) {
		if (null == pasteConfigurationTopLevel) {
			if (this.pasteColumn) {
				pasteConfigurationTopLevel = (PasteEObjectConfiguration) AxisConfigurationUtils.getIAxisConfigurationUsedInTable(this.table, NattableaxisconfigurationPackage.eINSTANCE.getPasteEObjectConfiguration(), true);
			} else {
				pasteConfigurationTopLevel = (PasteEObjectConfiguration) AxisConfigurationUtils.getIAxisConfigurationUsedInTable(this.table, NattableaxisconfigurationPackage.eINSTANCE.getPasteEObjectConfiguration(), false);
			}
		}
		return pasteConfigurationTopLevel;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getPasteConfiguration(org.eclipse.papyrus.infra.nattable.model.nattable.Table, int, java.lang.String)
	 */
	@Override
	protected PasteEObjectConfiguration getPasteConfiguration(final Table table, final int currentDepth, final String categoryName) {
		return getPasteconfigurationTopLevel(table);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getDepthFromObject(java.lang.Object)
	 */
	@Override
	protected int getDepthFromObject(final Object object) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getCategoryFromObject(java.lang.Object)
	 */
	@Override
	protected String getCategoryFromObject(final Object object) {
		return "";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getRowElementCreatedInAttachedMode()
	 */
	@Override
	protected Object getRowElementCreatedInAttachedMode(final EObject context, final EStructuralFeature containmentFeature, final IElementType typeToCreate, final IProgressMonitor monitor, final IAdaptable info, final CompoundCommand compoundCommand) {
		Object createdElement = null;

		if (!containmentFeature.isDerived() && containmentFeature.isChangeable()) {
			final CreateElementRequest createRequest = new CreateElementRequest(contextEditingDomain, context, typeToCreate, (EReference) containmentFeature);
			final IElementEditService tableContextCommandProvider = ElementEditServiceUtils.getCommandProvider(context);

			final ICommand commandCreation = tableContextCommandProvider.getEditCommand(createRequest);
			if (commandCreation.canExecute()) {
				// we create the element
				final Command emfCommandCreation = GMFtoEMFCommandWrapper.wrap(commandCreation);
				emfCommandCreation.execute();
				compoundCommand.append(emfCommandCreation);

				// we add it to the table
				final CommandResult res = commandCreation.getCommandResult();

				createdElement = res.getReturnValue();
			}
		}

		return createdElement;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#createTableRow(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected void createTableRowInAttachedModeCommand(final CompoundCommand compoundCommand, final List<InsertedElementInNattable> createdElements) throws ExecutionException {
		CompoundCommand addCommand = new CompoundCommand("Create table row");

		// Move the element created at the correct position
		for (int insertedElementIndex = createdElements.size() - 1; insertedElementIndex >= 0; insertedElementIndex--) {
			final InsertedElementInNattable currentInsertedElement = createdElements.get(insertedElementIndex);
			if (-1 != currentInsertedElement.getIndexInParent()) {
				int addedIndex = 0;
				for(Object createdElement : currentInsertedElement.getCreatedElements()){
					addCommand.append(MoveCommand.create(contextEditingDomain, currentInsertedElement.getContext(), currentInsertedElement.getContainementFeature(), createdElement, currentInsertedElement.getIndexInParent()+addedIndex));
					addedIndex++;
				}
			}
		}

		// Add the row in the table only if the command can be executed
		// (and create an other if can be executed because it was already prepared)
		for (final InsertedElementInNattable currentInsertedElement : createdElements) {
			if (-1 != currentInsertedElement.getIndexInTable()) {
				if (pasteColumn) {
					final Command addColumnElementCommand = tableManager.getAddColumnElementCommand(currentInsertedElement.getCreatedElements(), currentInsertedElement.getIndexInTable());
					if (addColumnElementCommand.canExecute()) {
						addCommand.append(addColumnElementCommand);
					}
				} else {
					final Command addRowElementCommand = tableManager.getAddRowElementCommand(currentInsertedElement.getCreatedElements(), currentInsertedElement.getIndexInTable());
					if (addRowElementCommand.canExecute()) {
						addCommand.append(addRowElementCommand);
					}
				}
			} else {
				if (pasteColumn) {
					final Command addColumnElementCommand = tableManager.getAddColumnElementCommand(currentInsertedElement.getCreatedElements());
					if (addColumnElementCommand.canExecute()) {
						addCommand.append(addColumnElementCommand);
					}
				} else {
					final Command addRowElementCommand = tableManager.getAddRowElementCommand(currentInsertedElement.getCreatedElements());
					if (addRowElementCommand.canExecute()) {
						addCommand.append(addRowElementCommand);
					}
				}
			}
		}

		if (!addCommand.isEmpty()) {
			compoundCommand.append(addCommand);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getRowElementCreatedInDetachedMode()
	 */
	@Override
	protected Object getRowElementCreatedInDetachedMode(final EStructuralFeature containmentFeature, final IElementType typeToCreate, final IProgressMonitor monitor, final IAdaptable info, final CompoundCommand compoundCommand) {
		Object result = null;
		if (!containmentFeature.isDerived() && containmentFeature.isChangeable()) {
			// Get the class type to create and get its factory
			final EClass eClassToCreate = typeToCreate.getEClass();
			final EFactory eFactory = eClassToCreate.getEPackage().getEFactoryInstance();

			// get the element type to use to create the element
			result = eFactory.create(eClassToCreate);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#createTableRow(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected void createTableRowInDetachedModeCommand(final CompoundCommand compoundCommand, final List<InsertedElementInNattable> createdElements) throws ExecutionException {
		final CompoundCommand addCommand = new CompoundCommand("Create table row");

		for (int insertedElementIndex = createdElements.size() - 1; insertedElementIndex >= 0; insertedElementIndex--) {
			final InsertedElementInNattable currentInsertedElement = createdElements.get(insertedElementIndex);
			if (currentInsertedElement.getContainementFeature().isMany()) {
				if (-1 != currentInsertedElement.getIndexInParent()) {
					addCommand.append(AddCommand.create(contextEditingDomain, currentInsertedElement.getContext(), currentInsertedElement.getContainementFeature(), currentInsertedElement.getCreatedElements(), currentInsertedElement.getIndexInParent()));
				} else {
					addCommand.append(AddCommand.create(contextEditingDomain, currentInsertedElement.getContext(), currentInsertedElement.getContainementFeature(), currentInsertedElement.getCreatedElements()));
				}
			} else {
				addCommand.append(SetCommand.create(contextEditingDomain, currentInsertedElement.getContext(), currentInsertedElement.getContainementFeature(), currentInsertedElement.getCreatedElements().get(0)));
			}
		}

		// Add the row in the table only if the command can be executed
		// (and create an other if can be executed because it was already prepared)
		for (final InsertedElementInNattable currentInsertedElement : createdElements) {
			if (-1 != currentInsertedElement.getIndexInTable()) {
				if (pasteColumn) {
					final Command addColumnElementCommand = tableManager.getAddColumnElementCommand(currentInsertedElement.getCreatedElements(), currentInsertedElement.getIndexInTable());
					if (addColumnElementCommand.canExecute()) {
						addCommand.append(addColumnElementCommand);
					}
				} else {
					final Command addRowElementCommand = tableManager.getAddRowElementCommand(currentInsertedElement.getCreatedElements(), currentInsertedElement.getIndexInTable());
					if (addRowElementCommand.canExecute()) {
						addCommand.append(addRowElementCommand);
					}
				}
			} else {
				if (pasteColumn) {
					final Command addColumnElementCommand = tableManager.getAddColumnElementCommand(currentInsertedElement.getCreatedElements());
					if (addColumnElementCommand.canExecute()) {
						addCommand.append(addColumnElementCommand);
					}
				} else {
					final Command addRowElementCommand = tableManager.getAddRowElementCommand(currentInsertedElement.getCreatedElements());
					if (addRowElementCommand.canExecute()) {
						addCommand.append(addRowElementCommand);
					}
				}
			}
		}

		if (!addCommand.isEmpty()) {
			compoundCommand.append(addCommand);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#localDispose()
	 */
	@Override
	protected void localDispose() {
		super.localDispose();
		this.pasteConfigurationTopLevel = null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getColumnElement(int)
	 *
	 * @param realColumnIndex
	 * @return
	 */
	@Override
	protected Object getColumnElement(int realColumnIndex) {
		return tableManager.getColumnElementsList().get(realColumnIndex);
	}
}
