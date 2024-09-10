/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.nattable.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IPasteConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.LocalTableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.NattableconfigurationFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;

/**
 *
 * This class is in charge to convert a Table into TableConfiguration
 */
public class TableToTableConfigurationConverter {

	/**
	 * the table to convert as table configuration
	 */
	private final Table table;

	/**
	 * the new type of the table
	 */
	private final String type;

	/**
	 * the new name of the table configuration
	 */
	private final String name;

	/**
	 * the new icon to use for the table conf
	 */
	private final String iconPath;

	/**
	 * the description
	 */
	private final String description;


	/**
	 *
	 * Constructor.
	 *
	 * @param table
	 *            the Table to convert into a {@link TableConfiguration}. It can't be <code>null</code>
	 * @param name
	 *            the name to use for the {@link TableConfiguration}. Tt can't be <code>null</code> or empty
	 * @param type
	 *            the type to use for the {@link TableConfiguration}. It can't be <code>null</code> or empty
	 * @param iconPath
	 *            the icon's path for the {@link TableConfiguration}
	 * @param description
	 *            the description of the {@link TableConfiguration}
	 */
	public TableToTableConfigurationConverter(final Table table, final String name, final String type, final String iconPath, final String description) {
		Assert.isNotNull(table);
		Assert.isNotNull(type);
		Assert.isNotNull(name);
		Assert.isTrue(type.length() > 0, "The type of the TableConfiguration can't be an empty string"); //$NON-NLS-1$
		Assert.isTrue(name.length() > 0, "The name of the TableConfiguration can't be an empty string"); //$NON-NLS-1$
		this.table = table;
		this.type = type;
		this.name = name;
		this.iconPath = iconPath;
		this.description = description;
	}


	public final TableConfiguration convertTable() {
		// 0.create a copier
		final EcoreUtil.Copier copier = new Copier();


		// 1. we create a new TableConfiguration
		final TableConfiguration ancestorTableConfiguration = this.table.getTableConfiguration();

		// 1.1 we init its obvious field
		final TableConfiguration newConfiguration = NattableconfigurationFactory.eINSTANCE.createTableConfiguration();// EcoreUtil.copy(ancestorTableConfiguration);
		newConfiguration.setName(this.name);
		newConfiguration.setDescription(this.description);
		newConfiguration.setType(this.type);
		newConfiguration.setIconPath(this.iconPath);
		newConfiguration.setCellEditorDeclaration(ancestorTableConfiguration.getCellEditorDeclaration());


		// 1.2 we reset the styles
		newConfiguration.getStyles().addAll(StyleUtils.getCombinedStyles(this.table, ancestorTableConfiguration));

		// 1.3 define the java table tester
		// in fact we don't do it, because this field is not more used

		// 1.4 defined the cell editor configuration (used for Matrix)
		ICellEditorConfiguration ownedCellEditorConfiguration = null;
		if (null == this.table.getOwnedCellEditorConfigurations()) {
			ownedCellEditorConfiguration = ancestorTableConfiguration.getOwnedCellEditorConfigurations();
		} else {
			ownedCellEditorConfiguration = this.table.getOwnedCellEditorConfigurations();
		}
		if (null != ownedCellEditorConfiguration) {
			ownedCellEditorConfiguration = (ICellEditorConfiguration) copier.copy(ownedCellEditorConfiguration);
			newConfiguration.setOwnedCellEditorConfigurations(ownedCellEditorConfiguration);
		}

		// 1.5 create the Table Row Header Configuration
		TableHeaderAxisConfiguration newRowTableHeaderAxisConfiguration = createTableHeaderAxisConfiguration(table.getTableConfiguration().getRowHeaderAxisConfiguration(), table.getLocalRowHeaderAxisConfiguration(), copier);
		newConfiguration.setRowHeaderAxisConfiguration(newRowTableHeaderAxisConfiguration);

		// 1.6 create the table Column Header Configuration
		TableHeaderAxisConfiguration newColulmnTableHeaderAxisConfiguration = createTableHeaderAxisConfiguration(table.getTableConfiguration().getColumnHeaderAxisConfiguration(), table.getLocalColumnHeaderAxisConfiguration(), copier);
		newConfiguration.setColumnHeaderAxisConfiguration(newColulmnTableHeaderAxisConfiguration);

		final AbstractAxisProvider columnAxisProvider = table.getCurrentColumnAxisProvider();

		final AbstractAxisProvider newColumnAxisProvider = (AbstractAxisProvider) copier.copy(columnAxisProvider);
		copier.copyReferences();

		removeCurrentModelAxis(newColumnAxisProvider);
		newConfiguration.getColumnAxisProviders().add(newColumnAxisProvider);
		newConfiguration.setDefaultColumnAxisProvider(newColumnAxisProvider);


		final AbstractAxisProvider rowAxisProvider = table.getCurrentRowAxisProvider();

		final AbstractAxisProvider newRowAxisProvider = (AbstractAxisProvider) copier.copy(rowAxisProvider);
		copier.copyReferences();


		removeCurrentModelAxis(newRowAxisProvider);
		newConfiguration.getRowAxisProviders().add(newRowAxisProvider);
		newConfiguration.setDefaultRowAxisProvider(newRowAxisProvider);

		// remove duplicated configuration... something with the copy does too much stuff...
		for (AxisManagerRepresentation currentRep : newConfiguration.getRowHeaderAxisConfiguration().getAxisManagers()) {
			Set<IAxisConfiguration> conf = new HashSet<>();
			conf.addAll(currentRep.getSpecificAxisConfigurations());
			currentRep.getSpecificAxisConfigurations().clear();
			currentRep.getSpecificAxisConfigurations().addAll(conf);
		}
		for (AxisManagerRepresentation currentRep : newConfiguration.getColumnHeaderAxisConfiguration().getAxisManagers()) {
			Set<IAxisConfiguration> conf = new HashSet<>();
			conf.addAll(currentRep.getSpecificAxisConfigurations());
			currentRep.getSpecificAxisConfigurations().clear();
			currentRep.getSpecificAxisConfigurations().addAll(conf);
		}
		return newConfiguration;
	}

	/**
	 * This method removes all axis coming from the user model
	 *
	 * @param axisProvider
	 *            an axis provider
	 */
	private void removeCurrentModelAxis(final AbstractAxisProvider axisProvider) {
		final List<IAxis> axisToRemove = new ArrayList<>();
		if (axisProvider instanceof IMasterAxisProvider) {
			axisProvider.getAxis().clear();// we remove all
			((IMasterAxisProvider) axisProvider).getSources().clear();
			return;
		}
		for (final IAxis current : axisProvider.getAxis()) {
			// maybe remove all axis of a master axis provider will do the same thing?
			if (current.getElement() instanceof EObject) {
				final EObject element = (EObject) current.getElement();
				if (null != element.eResource() && null != element.eResource().getURI() && element.eResource().getURI().isPlatformResource()) {
					axisToRemove.add(current);
				}
				if (element instanceof TreeFillingConfiguration) {
					axisToRemove.add(current);
				}
			}
		}
		axisProvider.getAxis().removeAll(axisToRemove);
	}

	private final TableHeaderAxisConfiguration createTableHeaderAxisConfiguration(final TableHeaderAxisConfiguration ancestorTableHeaderAxisConfiguration, final LocalTableHeaderAxisConfiguration localRow, final Copier copier) {
		final TableHeaderAxisConfiguration newRowHeaderAxisConfiguration;
		if (null == localRow) {
			// easy, we are working with the configuration defined in the TableConfiguration
			newRowHeaderAxisConfiguration = (TableHeaderAxisConfiguration) copier.copy(ancestorTableHeaderAxisConfiguration);
		} else {
			newRowHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
			copier.put(ancestorTableHeaderAxisConfiguration, newRowHeaderAxisConfiguration);
			newRowHeaderAxisConfiguration.setDisplayFilter(localRow.isDisplayFilter());
			newRowHeaderAxisConfiguration.setDisplayIndex(localRow.isDisplayIndex());
			newRowHeaderAxisConfiguration.setDisplayLabel(localRow.isDisplayLabel());
			newRowHeaderAxisConfiguration.setIndexStyle(localRow.getIndexStyle());

			// init styles
			newRowHeaderAxisConfiguration.getStyles().addAll(StyleUtils.getCombinedStyles(localRow, ancestorTableHeaderAxisConfiguration));

			List<AxisManagerConfiguration> axisManagerConfigurations = localRow.getAxisManagerConfigurations();
			if (axisManagerConfigurations.size() == 0) {
				// we are working with the configuration defined in the ancestorTableConfiguration
				TableHeaderAxisConfiguration tmpCopy = EcoreUtil.copy(ancestorTableHeaderAxisConfiguration);
				newRowHeaderAxisConfiguration.getAxisManagers().addAll(tmpCopy.getAxisManagers());
				newRowHeaderAxisConfiguration.getOwnedLabelConfigurations().addAll(tmpCopy.getOwnedLabelConfigurations());
				newRowHeaderAxisConfiguration.getOwnedAxisConfigurations().addAll(tmpCopy.getOwnedAxisConfigurations());
				// styles are already init (see some code line upper)
			} else {
				// AxisManagerConfiguration is transformed into AxisManagerRepresentation

				final Iterator<AxisManagerConfiguration> axisManagerConfigurationsIter = axisManagerConfigurations.iterator();
				while (axisManagerConfigurationsIter.hasNext()) {
					final AxisManagerConfiguration currentAxisManagerConfiguration = axisManagerConfigurationsIter.next();

					final AxisManagerRepresentation newAxisManagerRepresentation = NattableaxisconfigurationFactory.eINSTANCE.createAxisManagerRepresentation();
					newAxisManagerRepresentation.setAxisManagerId(currentAxisManagerConfiguration.getAxisManager().getAxisManagerId());
					newAxisManagerRepresentation.setLabelProviderContext(currentAxisManagerConfiguration.getAxisManager().getLabelProviderContext());

					newAxisManagerRepresentation.getStyles().addAll(StyleUtils.getCombinedStyles(currentAxisManagerConfiguration, currentAxisManagerConfiguration.getAxisManager()));
					newRowHeaderAxisConfiguration.getAxisManagers().add(newAxisManagerRepresentation);

					if (null != currentAxisManagerConfiguration.getLocalHeaderLabelConfiguration()) {
						newAxisManagerRepresentation.setHeaderLabelConfiguration((ILabelProviderConfiguration) copier.copy(currentAxisManagerConfiguration.getLocalHeaderLabelConfiguration()));
					} else {
						newAxisManagerRepresentation.setHeaderLabelConfiguration((ILabelProviderConfiguration) copier.copy(currentAxisManagerConfiguration.getAxisManager().getHeaderLabelConfiguration()));
					}
					newRowHeaderAxisConfiguration.getOwnedLabelConfigurations().add(newAxisManagerRepresentation.getHeaderLabelConfiguration());

					if (currentAxisManagerConfiguration.getLocalSpecificConfigurations().size() == 0) {
						// TODO : I don't found example with this usecase
					} else {
						// newRowHeaderAxisConfiguration.getOwnedAxisConfigurations().clear();
						// newAxisManagerRepresentation.getSpecificAxisConfigurations().clear();
						List<IAxisConfiguration> copiedAxisConfig = (List<IAxisConfiguration>) copier.copyAll(currentAxisManagerConfiguration.getLocalSpecificConfigurations());
						copier.copyReferences();
						for (IAxisConfiguration current : copiedAxisConfig) {
							if (current instanceof TreeFillingConfiguration) {
								final ILabelProviderConfiguration provider = ((TreeFillingConfiguration) current).getLabelProvider();
								if (null != provider) {
									EObject copiedValue = copier.get(provider);
									if (null == copiedValue) {
										copiedValue = copier.copy(provider);
										((TreeFillingConfiguration) current).setLabelProvider((ILabelProviderConfiguration) copiedValue);
										newRowHeaderAxisConfiguration.getOwnedLabelConfigurations().add((ILabelProviderConfiguration) copiedValue);
									}
								}
								final IPasteConfiguration pasteConf = ((TreeFillingConfiguration) current).getPasteConfiguration();
								if (null != pasteConf) {
									EObject copiedValue = copier.get(pasteConf);
									if (null == copiedValue) {
										copiedValue = copier.copy(pasteConf);
										((TreeFillingConfiguration) current).setPasteConfiguration((PasteEObjectConfiguration) copiedValue);
										newRowHeaderAxisConfiguration.getOwnedAxisConfigurations().add((IAxisConfiguration) copiedValue);
									}
								}
							}
						}

						newRowHeaderAxisConfiguration.getOwnedAxisConfigurations().addAll(copiedAxisConfig);
						newAxisManagerRepresentation.getSpecificAxisConfigurations().addAll(copiedAxisConfig);

					}
					copier.copyReferences();
					newRowHeaderAxisConfiguration.getAxisManagers().add(newAxisManagerRepresentation);
				}
			}
		}

		return newRowHeaderAxisConfiguration;

	}



}
