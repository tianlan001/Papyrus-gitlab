/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.properties.observables;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.LocalTableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.properties.observable.AbstractConfigurationElementObservableValue;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

/**
 * This abstract class provide the observable value to edit the filer defined in the first TreeFillingConfiguration found for depth=0
 *
 */
public abstract class AbstractMatrixFirstTreeFillingConfigurationFilterEMFObservable extends AbstractConfigurationElementObservableValue {

	/**
	 * the depth of the managed TreeFillingConfiguration
	 */
	protected final int depth;

	/**
	 * <code>true</code> if we are working on column
	 */
	protected final boolean isColumn;

	/**
	 * Constructor.
	 *
	 * @param table
	 *            the current edited table
	 * @param managedFeature
	 *            the managed feature
	 */
	public AbstractMatrixFirstTreeFillingConfigurationFilterEMFObservable(final Table table, final int depth, final boolean isColumn) {
		super(table, NattableaxisconfigurationPackage.eINSTANCE.getTreeFillingConfiguration_FilterRule());
		this.depth = depth;
		this.isColumn = isColumn;
		oldValue = doGetValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 */
	@Override
	public Object getValueType() {
		return NattableaxisconfigurationPackage.eINSTANCE.getTreeFillingConfiguration_FilterRule().getEType();
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.properties.observable.AbstractConfigurationElementObservableValue#getEditedEObject()
	 *
	 * @return
	 */
	@Override
	protected EObject getEditedEObject() {
		final Table table = getTable();
		final AbstractHeaderAxisConfiguration conf;
		if (isColumn) {
			conf = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisConfigurationUsedInTable(table);
		} else {
			conf = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisConfigurationUsedInTable(table);
		}
		if (null != conf) {
			// we assume that there is only one AxisManagerConfiguration and only one TreeFillingConfiguration with depth==1 for it
			if (conf instanceof LocalTableHeaderAxisConfiguration) {
				final List<AxisManagerConfiguration> axisManagerConfigurations = ((LocalTableHeaderAxisConfiguration) conf).getAxisManagerConfigurations();
				for (final AxisManagerConfiguration current : axisManagerConfigurations) {
					for (IAxisConfiguration axisConfig : current.getLocalSpecificConfigurations()) {
						if (axisConfig instanceof TreeFillingConfiguration) {
							if (this.depth == ((TreeFillingConfiguration) axisConfig).getDepth()) {
								return axisConfig;
							}
						}
					}
				}
				// we assume that there is only one AxisManagerConfiguration and only one TreeFillingConfiguration with depth==1 for it
			} else if (conf instanceof TableHeaderAxisConfiguration) {
				final List<AxisManagerRepresentation> axisManagers = ((TableHeaderAxisConfiguration) conf).getAxisManagers();
				for (final AxisManagerRepresentation rep : axisManagers) {
					for (final IAxisConfiguration axisConfig : rep.getSpecificAxisConfigurations()) {
						if (axisConfig instanceof TreeFillingConfiguration) {
							if (this.depth == ((TreeFillingConfiguration) axisConfig).getDepth()) {
								return axisConfig;
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	protected void doSetValue(Object value) {
		String labelCommand;
		if (isColumn) {
			labelCommand = "ChangeColumnExpressionCommand"; //$NON-NLS-1$
		} else {
			labelCommand = "ChangeRowExpressionCommand"; //$NON-NLS-1$
		}
		final CompositeCommand compositeCommand = new CompositeCommand(labelCommand);

		Table table = getTable();
		TransactionalEditingDomain domain = TableEditingDomainUtils.getTableEditingDomain(table);
		TreeFillingConfiguration configuration = (TreeFillingConfiguration) getEditedEObject();
		if (null != configuration) {
			if (configuration.eContainer() instanceof TableHeaderAxisConfiguration) {
				// we must copy it in the table;
				LocalTableHeaderAxisConfiguration localHeaderconfig = HeaderAxisConfigurationManagementUtils.transformToLocalHeaderConfigurationIncludingAllConfigurations((TableHeaderAxisConfiguration) configuration.eContainer());

				SetRequest request = null;
				if ((isColumn && !table.isInvertAxis()) || (!isColumn && table.isInvertAxis())) {
					request = new SetRequest(domain, table, NattablePackage.eINSTANCE.getTable_LocalColumnHeaderAxisConfiguration(), localHeaderconfig);
				} else {
					request = new SetRequest(domain, table, NattablePackage.eINSTANCE.getTable_LocalRowHeaderAxisConfiguration(), localHeaderconfig);
				}
				final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(table);
				final ICommand cmd = provider.getEditCommand(request);
				compositeCommand.add(cmd);

				// we need to found the real edited TreeFillingConfiguration
				// we assume that there is only one AxisManagerConfiguration and only one TreeFillingConfiguration with depth==1 for it
				for (final AxisManagerConfiguration config : localHeaderconfig.getAxisManagerConfigurations()) {
					for (IAxisConfiguration axisConfiguration : config.getLocalSpecificConfigurations()) {
						if (axisConfiguration instanceof TreeFillingConfiguration && this.depth == ((TreeFillingConfiguration) axisConfiguration).getDepth()) {
							configuration = (TreeFillingConfiguration) axisConfiguration;
							break;
						}
					}
				}
			}

			// now we have the configuration
			SetRequest request = new SetRequest(domain, configuration, getManagedFeature(), value);
			final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(configuration);
			final ICommand cmd = provider.getEditCommand(request);
			compositeCommand.add(cmd);
			domain.getCommandStack().execute(new GMFtoEMFCommandWrapper(compositeCommand));
		}
	}

}
