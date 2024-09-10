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

package org.eclipse.papyrus.infra.nattable.manager;

import java.io.Reader;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IPasteConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.parsers.CSVParser;
import org.eclipse.papyrus.infra.nattable.parsers.CellIterator;
import org.eclipse.papyrus.infra.nattable.parsers.RowIterator;
import org.eclipse.papyrus.infra.nattable.utils.AxisConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.CreatableEObjectAxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.IPapyrusNattableStatus;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.PapyrusNattableMultiStatus;
import org.eclipse.papyrus.infra.nattable.utils.PapyrusNattableStatus;
import org.eclipse.papyrus.infra.nattable.utils.PasteConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.PasteEnablementStatus;
import org.eclipse.papyrus.infra.nattable.utils.PasteSeverityCode;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;

/**
 * Abstract class for the copy/paste, insert and import file in the table.
 */
public abstract class AbstractPasteImportInsertInNattableManager {

	/**
	 * The preferred user action for the insert row action.
	 */
	protected final int preferredUserAction;

	/**
	 * The helper used to do the paste (help for the split of the string).
	 */
	protected final CSVPasteHelper pasteHelper;

	/**
	 * The table manager.
	 */
	protected final INattableModelManager tableManager;

	/**
	 * <code>true</code> if we must use progress monitor.
	 */
	protected final boolean useProgressMonitorDialog;

	/**
	 * The current selection.
	 */
	protected final TableSelectionWrapper tableSelectionWrapper;

	/**
	 * Determinate if dialog have to be opened during the execution (need to be <code>false</code> for the JUnit tests).
	 */
	protected final boolean openDialog;

	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            The nattable model manager.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param useProgressMonitorDialog
	 *            Boolean to determinate if a progress monitor dialog must be used.
	 * @param openDialog
	 *            Boolean to determinate if the dialog must be opened during the process.
	 * @param preferredUserAction
	 *            The preferred user action for the insert row action.
	 */
	public AbstractPasteImportInsertInNattableManager(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final boolean useProgressMonitorDialog, final boolean openDialog, final int preferredUserAction) {
		this(tableManager, pasteHelper, useProgressMonitorDialog, openDialog, preferredUserAction, null);
	}

	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            The nattable model manager.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param useProgressMonitorDialog
	 *            Boolean to determinate if a progress monitor dialog must be used.
	 * @param openDialog
	 *            Boolean to determinate if the dialog must be opened during the process.
	 * @param preferredUserAction
	 *            The preferred user action for the insert row action.
	 * @param tableSelectionWrapper
	 *            The current selection in the table.
	 */
	public AbstractPasteImportInsertInNattableManager(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final boolean useProgressMonitorDialog, final boolean openDialog, final int preferredUserAction,
			final TableSelectionWrapper tableSelectionWrapper) {
		this.pasteHelper = pasteHelper;
		this.tableManager = tableManager;
		this.useProgressMonitorDialog = useProgressMonitorDialog;
		this.openDialog = openDialog;
		this.preferredUserAction = preferredUserAction;
		this.tableSelectionWrapper = tableSelectionWrapper;
	}

	/**
	 * This method manages the action and the possible error dialog before doing the action.
	 *
	 * @return
	 *         <code>true</code> if the action can be done (as it is done in a job, it will be after the return of this method)
	 */
	public abstract IStatus doAction();

	/**
	 * Get the first non OK status.
	 *
	 * @param status
	 *            a status
	 * @return
	 * 		the first non ok status
	 */
	protected IStatus getFirstNonOKStatus(final IStatus status) {
		if (status != null && !status.isOK()) {
			for (final IStatus current : status.getChildren()) {
				if (!current.isOK()) {
					if (current.isMultiStatus()) {
						return getFirstNonOKStatus(current);
					} else {
						return current;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Get the label provider service.
	 * 
	 * @param registry
	 *            the config registry
	 * @return
	 * 		the label provider service
	 */
	protected LabelProviderService getLabelProviderService(final IConfigRegistry registry) {
		return registry.getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
	}

	/**
	 * This allows to verify the column count for the first line and return the correct status.
	 *
	 * @param tableManager
	 *            the table manager
	 * @param reader
	 *            the reader to use to do parsing
	 * @return
	 * 		a status indicating if the first line of the file allows to do the paste
	 */
	protected IStatus verifyColumnCountOnFirstLine(final INattableModelManager tableManager, final Reader reader) {
		final int axisCount = tableManager.getColumnCount();
		final CSVParser parser = this.pasteHelper.createParser(reader);
		// we verify the nb of columns
		final RowIterator rowIter = parser.parse();
		int nbCell = 0;
		if (rowIter.hasNext()) {
			final CellIterator cellIter = rowIter.next();
			while (cellIter.hasNext()) {
				cellIter.next();
				nbCell++;
			}
		}
		// 430115: [Table2] Paste/Import must be possible when the number of columns is not the same in the table and in the clipboard/file
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=430115
		if (axisCount == 0) {
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_TheTableDoesntHaveColumns);
		}

		if (axisCount == nbCell) {
			return new Status(IStatus.OK, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_NumberOfColumnsAreEquals);
		} else {
			return new Status(IStatus.OK, Activator.PLUGIN_ID, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_NumberOfColumnsAreNotEquals, nbCell, axisCount));
		}
	}

	/**
	 * This allows to find the paste mode for the table configuration.
	 *
	 * @param tableManager
	 *            the table manager used to calculate the enablement of the paste
	 * @return
	 * 		the status for the paste
	 */
	protected PasteEnablementStatus findPasteModeFromTableConfiguration(final INattableModelManager tableManager) {
		if (TableHelper.isTreeTable(tableManager)) {
			return findPasteModeFromTableConfigurationForTreeTable(tableManager);
		}
		return findPasteModeFromTableConfigurationForNormalTable(tableManager);
	}

	/**
	 * This allows to find the paste mode for the tree table configuration.
	 *
	 * @param tableManager
	 *            the table manager used to calculate the enablement of the paste
	 * @return
	 * 		the status for the paste
	 */
	protected PasteEnablementStatus findPasteModeFromTableConfigurationForTreeTable(final INattableModelManager tableManager) {
		PapyrusNattableMultiStatus pasteRowsStatus = canPasteAxis_verifyPasteConfigurationForTreeTable(tableManager);
		final boolean fullSynchro = FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(tableManager.getTable(), 0);
		if (fullSynchro) {
			pasteRowsStatus = canPasteAxis_verifyPasteConfiguration(tableManager, false);
		}
		final PasteEnablementStatus pasteStatus = new PasteEnablementStatus(null, pasteRowsStatus);
		return pasteStatus;
	}

	/**
	 * This allows to find the paste mode for the simple table configuration.
	 *
	 * @param tableManager
	 *            the table manager used to calculate the enablement of the paste
	 * @return
	 * 		the status for the paste
	 */
	protected PasteEnablementStatus findPasteModeFromTableConfigurationForNormalTable(final INattableModelManager tableManager) {
		final MultiStatus pasteRowsStatus = canPasteAxis_verifyPasteConfiguration(tableManager, false);
		final MultiStatus pasteColumnsStatus = canPasteAxis_verifyPasteConfiguration(tableManager, true);
		final PasteEnablementStatus pasteStatus = new PasteEnablementStatus(pasteColumnsStatus, pasteRowsStatus);
		return pasteStatus;
	}

	/**
	 * Verify the paste configuration for the tree table.
	 * 
	 * @param tableManager
	 *            the table manager
	 * @return
	 * 		a multi status with information on all verified point in the table configuration or <code>null</code> when there is no table
	 *         configuration
	 */
	protected static PapyrusNattableMultiStatus canPasteAxis_verifyPasteConfigurationForTreeTable(final INattableModelManager tableManager) {
		// TODO : do more check + use more ERROR status?

		final String pluginId = Activator.PLUGIN_ID;
		final String axisName = Messages.AbstractPasteImportInsertInNattableManager_row;
		PapyrusNattableMultiStatus status = null;
		for (final TreeFillingConfiguration current : FillingConfigurationUtils.getAllTreeFillingConfiguration(tableManager.getTable())) {
			final IPasteConfiguration conf = current.getPasteConfiguration();
			if (conf instanceof PasteEObjectConfiguration) {
				status = new PapyrusNattableMultiStatus(pluginId, IStatus.OK, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_EnablementStatusForPasteInTheTable, axisName), null);
				status.add(new Status(IStatus.OK, pluginId, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_TheTableHasAConfigurationToPaste, axisName)));

				if (status.isOK() && current.getDepth() == 0) {
					final PasteEObjectConfiguration pasteConfiguration = (PasteEObjectConfiguration) conf;
					final IStatus consistentPasteEObjectConfigurationStatus = PasteConfigurationUtils.hasConsistentPasteEObjectConfiguration(pasteConfiguration);
					if (!consistentPasteEObjectConfigurationStatus.isOK()) {
						status.add(new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, pluginId, consistentPasteEObjectConfigurationStatus.getMessage()));
					} else {
						status.add(consistentPasteEObjectConfigurationStatus);
					}

					// verify that the table context have the required feature
					if (status.isOK()) {
						final EStructuralFeature containmentFeature = pasteConfiguration.getPasteElementContainementFeature();
						if (tableManager.getTable().getContext().eClass().getEAllStructuralFeatures().contains(containmentFeature)) {
							status.add(new Status(IStatus.OK, pluginId, Messages.AbstractPasteImportInsertInNattableManager_TheContextOfTheTableHasTheContainmentFeatureDefinedForThePaste));
						} else {
							status.add(new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, pluginId, Messages.AbstractPasteImportInsertInNattableManager_TheContextOfTheTableHasNotTheContainmentFeatureDefinedForThePaste));
						}

						// verify that the elements to create are supported by the axis manager
						if (status.isOK()) {
							final String elementId = pasteConfiguration.getPastedElementId();
							if (CreatableEObjectAxisUtils.getCreatableElementIds(tableManager, false).contains(elementId)) {
								status.add(new Status(IStatus.OK, pluginId, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_TheTableCanCreateElement, elementId, axisName)));
							} else {
								status.add(new Status(IStatus.OK, pluginId, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_TheTableCantCreateElement, elementId, axisName)));
							}
						}
					}
				}
			}
		}
		return status;
	}

	/**
	 * Verify the paste configuration for the paste axis.
	 *
	 * @param tableManager
	 *            the table manager
	 * @param columnAxis
	 *            if <code>true</code> this method tests the paste configuration for the columns, if not, it tests the paste configuration for the rows
	 * @return
	 * 		a multi status with information on all verified point in the table configuration or <code>null</code> when there is no table
	 *         configuration
	 */
	protected static PapyrusNattableMultiStatus canPasteAxis_verifyPasteConfiguration(final INattableModelManager tableManager, final boolean columnAxis) {
		final IPasteConfiguration conf = (IPasteConfiguration) AxisConfigurationUtils.getIAxisConfigurationUsedInTable(tableManager.getTable(), NattableaxisconfigurationPackage.eINSTANCE.getPasteEObjectConfiguration(), columnAxis);
		final String pluginId = Activator.PLUGIN_ID;
		final String axisName = columnAxis ? Messages.AbstractPasteImportInsertInNattableManager_column : Messages.AbstractPasteImportInsertInNattableManager_row;
		PapyrusNattableMultiStatus status = null;
		if (conf instanceof PasteEObjectConfiguration) {
			status = new PapyrusNattableMultiStatus(pluginId, IStatus.OK, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_EnablementStatusForPasteInTheTable, axisName), null);
			status.add(new Status(IStatus.OK, pluginId, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_TheTableHasAConfigurationToPaste, axisName)));
			if (status.isOK()) {
				final PasteEObjectConfiguration pasteConfiguration = (PasteEObjectConfiguration) conf;
				final IStatus consistentPasteEObjectConfigurationStatus = PasteConfigurationUtils.hasConsistentPasteEObjectConfiguration(pasteConfiguration);
				if (!consistentPasteEObjectConfigurationStatus.isOK()) {
					status.add(new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, pluginId, consistentPasteEObjectConfigurationStatus.getMessage()));
				} else {
					status.add(consistentPasteEObjectConfigurationStatus);
				}

				// verify that the table context have the required feature
				if (status.isOK()) {
					final EStructuralFeature containmentFeature = pasteConfiguration.getPasteElementContainementFeature();
					if (tableManager.getTable().getContext().eClass().getEAllStructuralFeatures().contains(containmentFeature)) {
						status.add(new Status(IStatus.OK, pluginId, Messages.AbstractPasteImportInsertInNattableManager_TheContextOfTheTableHasTheContainmentFeatureDefinedForThePaste));
					} else {
						status.add(new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, pluginId, Messages.AbstractPasteImportInsertInNattableManager_TheContextOfTheTableHasNotTheContainmentFeatureDefinedForThePaste));
					}

					// verify that the elements to create are supported by the axis manager
					if (status.isOK()) {
						final String elementId = pasteConfiguration.getPastedElementId();
						if (CreatableEObjectAxisUtils.getCreatableElementIds(tableManager, columnAxis).contains(elementId)) {
							status.add(new Status(IStatus.OK, pluginId, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_TheTableCanCreateElement, elementId, axisName)));
						} else {
							status.add(new Status(IStatus.OK, pluginId, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_TheTableCantCreateElement, elementId, axisName)));
						}
					}
				}
			}
		}
		return status;
	}

	/**
	 * This allow to find the paste configuration for the axis identifier and check it.
	 * 
	 * @param manager
	 *            The nattable model manager.
	 * @return multi status corresponding to the paste configuration axis identifier.
	 */
	protected static PasteEnablementStatus findPasteConfigurationAxisIdentifier(final INattableModelManager manager) {
		PapyrusNattableMultiStatus status = null;
		if (TableHelper.isTreeTable(manager)) {
			final boolean fullSynchro = FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(manager.getTable(), 0);
			if (fullSynchro) {
				status = canPasteAxis_verifyPasteConfigurationAxisIdentifierForNormalTable(manager);
			} else {
				status = canPasteAxis_verifyPasteConfigurationAxisIdentifierForTreeTable(manager);
			}
		} else {
			status = canPasteAxis_verifyPasteConfigurationAxisIdentifierForNormalTable(manager);
		}
		final PasteEnablementStatus pasteStatus = new PasteEnablementStatus(null, status);
		return pasteStatus;
	}

	/**
	 * Verify the paste configuration axis identifier for the paste axis.
	 *
	 * @param tableManager
	 *            the table manager
	 * @return
	 * 		a multi status with information on all verified point in the table configuration or <code>null</code> when there is no table
	 *         configuration
	 */
	protected static PapyrusNattableMultiStatus canPasteAxis_verifyPasteConfigurationAxisIdentifierForTreeTable(final INattableModelManager manager) {
		PapyrusNattableMultiStatus status = null;

		final String pluginId = Activator.PLUGIN_ID;
		final String axisName = Messages.AbstractPasteImportInsertInNattableManager_row;
		for (final TreeFillingConfiguration current : FillingConfigurationUtils.getAllTreeFillingConfiguration(manager.getTable())) {
			final IPasteConfiguration conf = current.getPasteConfiguration();
			if (current.getDepth() == 0 && conf instanceof PasteEObjectConfiguration) {
				status = new PapyrusNattableMultiStatus(pluginId, IStatus.OK, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_EnablementStatusForPasteInTheTable, axisName), null);
				status.add(new Status(IStatus.OK, pluginId, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_TheTableHasAConfigurationToPaste, axisName)));

				if (status.isOK()) {
					final PasteEObjectConfiguration pasteConfiguration = (PasteEObjectConfiguration) conf;

					final IAxis axisIdentifier = pasteConfiguration.getAxisIdentifier();

					if (null != axisIdentifier) {
						status.add(new Status(IStatus.OK, pluginId, Messages.AbstractPasteImportInsertInNattableManager_PasteConfigurationAxisIdentifierHasBeenSet)); // $NON-NLS-1$
					} else {
						status.add(new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, pluginId, Messages.AbstractPasteImportInsertInNattableManager_PasteConfigurationAxisIdentifierHasNotBeenSet)); // $NON-NLS-1
					}
				}
			}
		}

		return status;
	}

	/**
	 * Verify the paste configuration axis identifier for the paste axis.
	 *
	 * @param tableManager
	 *            the table manager
	 * @return
	 * 		a multi status with information on all verified point in the table configuration or <code>null</code> when there is no table
	 *         configuration
	 */
	protected static PapyrusNattableMultiStatus canPasteAxis_verifyPasteConfigurationAxisIdentifierForNormalTable(final INattableModelManager manager) {
		final IPasteConfiguration conf = (IPasteConfiguration) AxisConfigurationUtils.getIAxisConfigurationUsedInTable(manager.getTable(), NattableaxisconfigurationPackage.eINSTANCE.getPasteEObjectConfiguration(), false);
		final String pluginId = Activator.PLUGIN_ID;
		final String axisName = Messages.AbstractPasteImportInsertInNattableManager_row;
		PapyrusNattableMultiStatus status = null;
		if (conf instanceof PasteEObjectConfiguration) {
			status = new PapyrusNattableMultiStatus(pluginId, IStatus.OK, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_EnablementStatusForPasteInTheTable, axisName), null);
			status.add(new Status(IStatus.OK, pluginId, NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_TheTableHasAConfigurationToPaste, axisName)));
			if (status.isOK()) {
				final PasteEObjectConfiguration pasteConfiguration = (PasteEObjectConfiguration) conf;
				final IAxis axisIdentifier = pasteConfiguration.getAxisIdentifier();

				if (null != axisIdentifier) {
					status.add(new Status(IStatus.OK, pluginId, Messages.AbstractPasteImportInsertInNattableManager_PasteConfigurationAxisIdentifierHasBeenSet)); // $NON-NLS-1$
				} else {
					status.add(new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, pluginId, Messages.AbstractPasteImportInsertInNattableManager_PasteConfigurationAxisIdentifierHasNotBeenSet)); // $NON-NLS-1
				}
			}
		}
		return status;
	}

	/**
	 * This allows to check the table configuration for tree table.
	 * 
	 * @param manager
	 *            The nattable model manager.
	 * @param maxDepth The maximum depth to check.
	 * @return The status of the check.
	 */
	protected IStatus checkTreeTableConfiguration(final INattableModelManager manager, final int maxDepth) {
		IStatus status = Status.OK_STATUS;

		// we check than there is only one categories by hidden depth
		final List<Integer> hiddenCategories = StyleUtils.getHiddenDepths(manager);
		for (final Integer current : hiddenCategories) {
			if(current <= maxDepth){
				final int size = FillingConfigurationUtils.getAllTreeFillingConfigurationForDepth(manager.getTable(), current.intValue()).size();
				if (size > 1) {
					status = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, PasteSeverityCode.PASTE_ERROR__MORE_THAN_ONE_CATEGORY_FOR_A_HIDDEN_DEPTH,
							NLS.bind(Messages.AbstractPasteImportInsertInNattableManager_YouHaveMoreThan1Category, current.intValue()), null);
				}
			}
		}

		if (status.isOK()) {
			for (final TreeFillingConfiguration current : FillingConfigurationUtils.getAllTreeFillingConfiguration(manager.getTable())) {
				if(current.getDepth() <= maxDepth){
					
					final PasteEObjectConfiguration conf = current.getPasteConfiguration();
					if (conf == null) {
						// TODO : add detail of the error in message
						status = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, PasteSeverityCode.PASTE_ERROR__NO_PASTE_CONFIGURATION,
								Messages.AbstractPasteImportInsertInNattableManager_ThereIsNoPasteConfgurationForATreeFillingConfiguration,
								null);
					} else {
	
						final String elementTypeId = conf.getPastedElementId();
						if (elementTypeId == null || elementTypeId.isEmpty()) {
							status = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, PasteSeverityCode.PASTE_ERROR__NO_ELEMENT_TYPE_IN_PASTE_CONFIGURATION,
									Messages.AbstractPasteImportInsertInNattableManager_ThereIsNoElementIdDefinedInThePasteConfiguration, null);
						} else if (!ElementTypeUtils.getAllExistingElementTypesIds().contains(elementTypeId)) {
							status = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, PasteSeverityCode.PASTE_ERROR__UNKNOWN_ELEMENT_TYPE,
									String.format(Messages.AbstractPasteImportInsertInNattableManager_TheElementTypeIsUnknown, elementTypeId), null);
						}
	
	
						if (status.isOK()) {
							final EStructuralFeature feature = conf.getPasteElementContainementFeature();
							if (feature == null) {
								final IAxis axis = current.getAxisUsedAsAxisProvider();
								final NatTable natTable = manager.getAdapter(NatTable.class);
								final LabelProviderContextElementWrapper wrapper = new LabelProviderContextElementWrapper();
								wrapper.setObject(axis);
								wrapper.setConfigRegistry(natTable.getConfigRegistry());
								final LabelProviderService serv = natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
								ILabelProvider p = serv.getLabelProvider(wrapper);
								p = serv.getLabelProvider(Constants.HEADER_LABEL_PROVIDER_CONTEXT);
								final String categoryName = p.getText(axis);
								status = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, PasteSeverityCode.PASTE_ERROR__NO_CONTAINMENT_FEATURE,
										String.format(Messages.AbstractPasteImportInsertInNattableManager_PasteConfigurationFeatureHasNotBeenSet, categoryName, current.getDepth()), null);
							}
						}
					}
				}
			}
		}

		return status;
	}

	/**
	 * This allows to create the reader and returns it.
	 *
	 * @return
	 * 		a new reader
	 */
	protected abstract Reader createReader();

	/**
	 * This allows to create the data size.
	 *
	 * @return
	 * 		the size of the copied data
	 */
	protected abstract long getDataSize();
}
