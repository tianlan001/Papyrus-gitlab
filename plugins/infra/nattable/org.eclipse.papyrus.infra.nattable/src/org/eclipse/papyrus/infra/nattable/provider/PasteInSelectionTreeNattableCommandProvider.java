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

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IPasteConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.AxisConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.InsertedElementInNattable;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

/**
 * This class manage the paste in tree table when a selection is available.
 */
public class PasteInSelectionTreeNattableCommandProvider extends PasteInSelectionNattableCommandProvider {

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
	public PasteInSelectionTreeNattableCommandProvider(final INattableModelManager tableManager, final boolean pasteColumn, final boolean isInsert, final Reader reader, final CSVPasteHelper pasteHelper,
			final TableSelectionWrapper tableSelectionWrapper, final int preferredUserAction, final long totalSize) {
		super(tableManager, pasteColumn, isInsert, reader, pasteHelper, tableSelectionWrapper, preferredUserAction, totalSize);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionNattableCommandProvider#init()
	 */
	@Override
	protected void init() {
		super.init();
		this.detachedMode = isPasteInDetachedMode(table);
	}

	/**
	 * Get the paste configuration for the depth and the category name.
	 * 
	 * @param table
	 *            The table to manage.
	 * @param depth
	 *            The depth to search.
	 * @param categoryName
	 *            The category name.
	 * @return The paste configuration corresponding to the depth and to the category name.
	 */
	protected PasteEObjectConfiguration getPasteConfigurationsFor(final Table table, final int depth, final String categoryName) {
		if (depth == 0 && !FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0)) {
			AbstractHeaderAxisConfiguration conf = table.getLocalRowHeaderAxisConfiguration();
			if (conf != null) {
				conf = table.getTableConfiguration().getRowHeaderAxisConfiguration();
			}
			final List<TreeFillingConfiguration> filling = FillingConfigurationUtils.getAllTreeFillingConfigurationForDepth(table, depth);
			final List<IAxisConfiguration> referencedPasteConf = new ArrayList<IAxisConfiguration>();
			for (final TreeFillingConfiguration tmp : filling) {
				if (tmp.getPasteConfiguration() != null) {
					referencedPasteConf.add(tmp.getPasteConfiguration());
				}
			}
			if (conf != null) {
				for (final IAxisConfiguration axisConf : conf.getOwnedAxisConfigurations()) {
					if (axisConf instanceof PasteEObjectConfiguration && !referencedPasteConf.contains(axisConf)) {
						return (PasteEObjectConfiguration) axisConf;
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
					if (featureName == null || "".equals(featureName)) {
						final Object element = curr.getAxisUsedAsAxisProvider().getElement();
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionNattableCommandProvider#getPasteConfiguration(org.eclipse.papyrus.infra.nattable.model.nattable.Table, int, java.lang.String)
	 */
	@Override
	protected PasteEObjectConfiguration getPasteConfiguration(final Table table, final int currentDepth, final String categoryName) {
		return (PasteEObjectConfiguration) getPasteConfigurationsFor(table, currentDepth, categoryName);
	}

	/**
	 * Get the paste configuration for the full synchro table.
	 * 
	 * @param table
	 *            The table.
	 * @return The paste configuration of the full synchro table.
	 */
	protected PasteEObjectConfiguration getPasteConfigurationForFullSynchroTable(final Table table) {
		PasteEObjectConfiguration pasteEObjectConfiguration = null;
		final IPasteConfiguration pasteConfiguration = (IPasteConfiguration) AxisConfigurationUtils.getIAxisConfigurationUsedInTable(table, NattableaxisconfigurationPackage.eINSTANCE.getPasteEObjectConfiguration(), false);

		if (pasteConfiguration instanceof PasteEObjectConfiguration) {
			pasteEObjectConfiguration = (PasteEObjectConfiguration) pasteConfiguration;
		}

		return pasteEObjectConfiguration;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getPasteconfiguration(org.eclipse.papyrus.infra.nattable.model.nattable.Table)
	 */
	@Override
	protected PasteEObjectConfiguration getPasteconfigurationTopLevel(final Table table) {
		if (null == pasteConfigurationTopLevel) {
			final boolean fullSynchro = FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(tableManager.getTable(), 0);
			if (fullSynchro) {
				pasteConfigurationTopLevel = getPasteConfigurationForFullSynchroTable(tableManager.getTable());
			} else {
				pasteConfigurationTopLevel = getPasteConfigurationsFor(table, 0, null);
			}
		}
		return pasteConfigurationTopLevel;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getDepthFromObject(java.lang.Object)
	 */
	@Override
	protected int getDepthFromObject(final Object object) {
		int depth = -1;
		if (object instanceof ITreeItemAxis) {
			depth = ((TreeNattableModelManager) tableManager).getSemanticDepth((ITreeItemAxis) object);
		}
		return -1 == depth ? super.getDepthFromObject(object) : depth;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionNattableCommandProvider#getCategoryFromObject(java.lang.Object)
	 */
	@Override
	protected String getCategoryFromObject(final Object object) {
		String category = "";
		if (object instanceof ITreeItemAxis) {
			if (((ITreeItemAxis) object).getElement() instanceof TreeFillingConfiguration) {
				final TreeFillingConfiguration configuration = (TreeFillingConfiguration) ((ITreeItemAxis) object).getElement();
				category = configuration.getAxisUsedAsAxisProvider().getAlias();
				if (category == null || "".equals(category)) {
					final Object element = configuration.getAxisUsedAsAxisProvider().getElement();
					if (element instanceof EStructuralFeature) {
						category = ((EStructuralFeature) element).getName();
					}
				}
			} else if (null != ((ITreeItemAxis) object).getParent()) {
				final ITreeItemAxis parentAxis = ((ITreeItemAxis) object).getParent();
				category = getCategoryFromObject(parentAxis);
			}
		}
		return category;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionNattableCommandProvider#getRowElementCreatedInAttachedMode()
	 */
	@Override
	protected Object getRowElementCreatedInAttachedMode(final EObject context, final EStructuralFeature containmentFeature, final IElementType typeToCreate, final IProgressMonitor monitor, final IAdaptable info, final CompoundCommand compoundCommand) {
		Object createdElement = null;

		if (!containmentFeature.isDerived() && containmentFeature.isChangeable()) {
			final CreateElementRequest createRequest1 = new CreateElementRequest(contextEditingDomain, context, typeToCreate, (EReference) containmentFeature);
			final IElementEditService creationContextCommandProvider = ElementEditServiceUtils.getCommandProvider(context);

			final ICommand commandCreation = creationContextCommandProvider.getEditCommand(createRequest1);
			if (null != commandCreation && commandCreation.canExecute()) {

				// we create the element
				final Command emfCommandCreation = GMFtoEMFCommandWrapper.wrap(commandCreation);
				emfCommandCreation.execute();
				compoundCommand.append(emfCommandCreation);

				// we get the result of the command
				final CommandResult res = commandCreation.getCommandResult();

				// we update the map
				createdElement = res.getReturnValue();
			}
		}

		return createdElement;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionNattableCommandProvider#createTableRow(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected void createTableRowInAttachedModeCommand(final CompoundCommand compoundCommand, final List<InsertedElementInNattable> createdElements) throws ExecutionException {
		final CompoundCommand addCommand = new CompoundCommand("Create table row");

		// Move the element created at the correct position
		for (int insertedElementIndex = createdElements.size() - 1; insertedElementIndex >= 0; insertedElementIndex--) {
			final InsertedElementInNattable currentInsertedElement = createdElements.get(insertedElementIndex);
			if (-1 != currentInsertedElement.getIndexInParent()) {
				int addedIndex = 0;
				for (Object createdElement : currentInsertedElement.getCreatedElements()) {
					addCommand.append(MoveCommand.create(contextEditingDomain, currentInsertedElement.getContext(), currentInsertedElement.getContainementFeature(), createdElement, currentInsertedElement.getIndexInParent() + addedIndex));
					addedIndex++;
				}
			}
		}

		// Add the row in the table only if the command can be executed
		// (and create an other if can be executed because it was already prepared)
		if (!FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0)) {
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
		}

		if (!addCommand.isEmpty()) {
			compoundCommand.append(addCommand);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionNattableCommandProvider#getRowElementCreatedInDetachedMode(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable, org.eclipse.emf.common.command.CompoundCommand)
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
	 * @see org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionNattableCommandProvider#createTableRow(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected void createTableRowInDetachedModeCommand(final CompoundCommand compoundCommand, final List<InsertedElementInNattable> createdElements) throws ExecutionException {
		final CompoundCommand addCommand = new CompoundCommand("Create table row");

		// we use the label to do a set name command on the created element
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
		if (!FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0)) {
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
		}

		if (!addCommand.isEmpty()) {
			compoundCommand.append(addCommand);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getFirstSelectedElementIndexOfTableContext(java.util.Map, int)
	 */
	@Override
	protected int getFirstSelectedElementIndexOfTableContext(final Map<Integer, Object> rows, final int currentRowPosition, final EObject context) {
		int initialFirstIndexInParent = -1;

		if (isInsert) {
			Object axis = rows.get(currentRowPosition);
			Object object = AxisUtils.getRepresentedElement(axis);
			if (object instanceof EObject) {
				if (object instanceof TreeFillingConfiguration) {
					initialFirstIndexInParent = 0;
				} else {
					initialFirstIndexInParent = super.getFirstSelectedElementIndexOfTableContext(rows, currentRowPosition, context);
				}
			}
		}

		return initialFirstIndexInParent;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getColumnElement(int)
	 *
	 * @param realColumnIndex
	 * @return
	 */
	@Override
	protected Object getColumnElement(final int realColumnIndex) {
		return AxisUtils.getRepresentedElement(tableManager.getColumnElement(realColumnIndex));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractPasteInSelectionNattableCommandProvider#getContentOfSelection(java.util.Map, int)
	 */
	@Override
	protected EObject getContentOfSelection(final Map<Integer, Object> rows, final int currentRowPosition) {
		EObject context = null;

		if (0 == currentRowPosition) {
			context = table.getContext();
		} else {
			Object axis = rows.get(currentRowPosition);
			Object object = AxisUtils.getRepresentedElement(axis);
			if (object instanceof TreeFillingConfiguration && axis instanceof ITreeItemAxis) {
				axis = ((ITreeItemAxis) axis).getParent();
				if (null != axis) {
					context = (EObject) AxisUtils.getRepresentedElement(axis);
				}
			} else if (object instanceof EObject) {
				context = ((EObject) object).eContainer();
			}
		}

		return context;
	}
}
