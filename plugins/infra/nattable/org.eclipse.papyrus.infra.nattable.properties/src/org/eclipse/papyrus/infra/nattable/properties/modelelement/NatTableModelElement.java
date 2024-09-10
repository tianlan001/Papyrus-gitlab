/*****************************************************************************
 * Copyright (c) 2013, 2017, 2019 CEA LIST and others.
 *
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
 *  Christian W. Damus (CEA) - bug 323802
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 476618, 192891, 496905, 508175
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 520188
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550568
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.properties.modelelement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.nattable.contentprovider.ColumnAxisIdentifierContentProvider;
import org.eclipse.papyrus.infra.nattable.contentprovider.ColumnContainmentFeatureContentProvider;
import org.eclipse.papyrus.infra.nattable.contentprovider.ColumnElementTypeIdContentProvider;
import org.eclipse.papyrus.infra.nattable.contentprovider.ContextFeatureContentProvider;
import org.eclipse.papyrus.infra.nattable.contentprovider.RowAxisIdentifierContentProvider;
import org.eclipse.papyrus.infra.nattable.contentprovider.RowContainmentFeatureContentProvider;
import org.eclipse.papyrus.infra.nattable.contentprovider.RowElementTypeIdContentProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.NattablelabelproviderPackage;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnDisplayFilterHeaderObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnDisplayIndexHeaderObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnDisplayLabelHeaderObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnFeatureLabelDisplayIconObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnFeatureLabelDisplayIsDerivedObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnFeatureLabelDisplayLabelObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnFeatureLabelDisplayMultiplicityObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnFeatureLabelDisplayNameObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnFeatureLabelDisplayTypeObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnIndexHeaderStyleObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnObjectLabelDisplayIconObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnObjectLabelDisplayLabelObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnPasteEObjectAxisIdentifierObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnPasteEObjectContainmentFeatureObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnPasteEObjectElementTypeIdObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnPasteObjectDetachedModeObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.ColumnPasteObjectPostActionsObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowDisplayIndexHeaderObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowDisplayLabelHeaderObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowFeatureLabelDisplayIconObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowFeatureLabelDisplayIsDerivedObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowFeatureLabelDisplayLabelObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowFeatureLabelDisplayMultiplicityObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowFeatureLabelDisplayNameObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowFeatureLabelDisplayTypeObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowIndexHeaderStyleObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowObjectLabelDisplayIconObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowObjectLabelDisplayLabelObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowPasteEObjectAxisIdentifierObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowPasteEObjectContainmentFeatureObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowPasteEObjectElementTypeIdObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowPasteObjectDetachedModeObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.RowPasteObjectPostActionsObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.TableLabelObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.observable.TablePrototypeObservableValue;
import org.eclipse.papyrus.infra.nattable.properties.provider.AxisIdentifierLabelProvider;
import org.eclipse.papyrus.infra.nattable.properties.provider.ColumnPostActionIdsProvider;
import org.eclipse.papyrus.infra.nattable.properties.provider.RowPostActionIdsProvider;
import org.eclipse.papyrus.infra.nattable.properties.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


public class NatTableModelElement extends EMFModelElement {

	/**
	 * this listener used to update the value of the property view
	 */
	private Adapter tableListener;

	/**
	 * the collection of the interesting features to update the property view
	 */
	private Collection<EStructuralFeature> interestingFeatures;

	/**
	 * the row header axis configuration
	 */
	private AbstractHeaderAxisConfiguration rowHeaderAxisConfiguration;

	/**
	 * the column header axis configuration
	 */
	private AbstractHeaderAxisConfiguration columnHeaderAxisConfiguration;

	/**
	 * the list of the column label provider configuration
	 */
	private Collection<ILabelProviderConfiguration> columnLabelProviderConfigurations;


	/**
	 * the list of the row label provider configuration
	 */
	private Collection<ILabelProviderConfiguration> rowLabelProviderConfigurations;

	/**
	 * a table manager created to get possible axis contents
	 */
	private INattableModelManager tableModelManager;

	/**
	 * This allows to keep a reference to the table to manage (because the source must be modified when the edited object is not a table).
	 *
	 * @since 2.1
	 */
	protected Table table;

	/**
	 *
	 * Constructor.
	 *
	 * @param tableManager
	 *            the table manager, must not be <code>null</code>
	 * @param domain
	 *            the editing domain, must not be <code>null</code>
	 */
	public NatTableModelElement(final INattableModelManager tableManager, final EditingDomain domain) {
		super(tableManager.getTable(), domain);
		this.tableModelManager = tableManager;
		this.table = tableManager.getTable();
		init();
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param source
	 *            the table
	 * @param domain
	 *            the editing domain
	 * @deprecated since 4.0
	 */
	@Deprecated
	public NatTableModelElement(final Table source, final EditingDomain domain) {
		super(source, domain);
		table = source;
		init();
	}

	/**
	 * Add the listener
	 */
	private void init() {
		this.interestingFeatures = new ArrayList<EStructuralFeature>();
		interestingFeatures.add(NattablePackage.eINSTANCE.getTable_TableKindId());
		interestingFeatures.add(NattablePackage.eINSTANCE.getTable_Owner());
		interestingFeatures.add(NattablePackage.eINSTANCE.getTable_Context());
		interestingFeatures.add(NattablePackage.eINSTANCE.getTable_InvertAxis());
		interestingFeatures.add(NattablePackage.eINSTANCE.getTable_LocalColumnHeaderAxisConfiguration());
		interestingFeatures.add(NattablePackage.eINSTANCE.getTable_LocalRowHeaderAxisConfiguration());
		interestingFeatures.add(NattableaxisconfigurationPackage.eINSTANCE.getAbstractHeaderAxisConfiguration_DisplayFilter());
		interestingFeatures.add(NattableaxisconfigurationPackage.eINSTANCE.getAbstractHeaderAxisConfiguration_DisplayIndex());
		interestingFeatures.add(NattableaxisconfigurationPackage.eINSTANCE.getAbstractHeaderAxisConfiguration_DisplayLabel());
		interestingFeatures.add(NattableaxisconfigurationPackage.eINSTANCE.getAbstractHeaderAxisConfiguration_IndexStyle());
		interestingFeatures.add(NattablelabelproviderPackage.eINSTANCE.getFeatureLabelProviderConfiguration_DisplayIsDerived());
		interestingFeatures.add(NattablelabelproviderPackage.eINSTANCE.getFeatureLabelProviderConfiguration_DisplayMultiplicity());
		interestingFeatures.add(NattablelabelproviderPackage.eINSTANCE.getFeatureLabelProviderConfiguration_DisplayName());
		interestingFeatures.add(NattablelabelproviderPackage.eINSTANCE.getFeatureLabelProviderConfiguration_DisplayType());
		interestingFeatures.add(NattablelabelproviderPackage.eINSTANCE.getObjectLabelProviderConfiguration_DisplayIcon());
		interestingFeatures.add(NattablelabelproviderPackage.eINSTANCE.getObjectLabelProviderConfiguration_DisplayLabel());

		if (null != table) {
			this.tableListener = new AdapterImpl() {

				/**
				 *
				 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
				 *
				 * @param msg
				 */
				@Override
				public void notifyChanged(Notification msg) {
					if (interestingFeatures.contains(msg.getFeature())) {
						updateValues();
					}
				}
			};
			// we add a listener on the main object managed by the property view
			table.eAdapters().add(tableListener);
			initOtherFieldsAndAddOthersListeners();
		}
	}

	/**
	 * init the fields of the elements to listen (excepted the table itself)+ add listeners on them
	 */
	private void initOtherFieldsAndAddOthersListeners() {

		columnHeaderAxisConfiguration = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(getEditedTable());
		rowHeaderAxisConfiguration = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTable(getEditedTable());

		// COLUMNS MANAGEMENT
		// we add a listener on the columnHeaderAxisConfiguration
		if (columnHeaderAxisConfiguration != null) {
			columnHeaderAxisConfiguration.eAdapters().add(tableListener);
		}

		if (columnHeaderAxisConfiguration != null) {
			columnLabelProviderConfigurations = columnHeaderAxisConfiguration.getOwnedLabelConfigurations();
		}

		if (this.columnLabelProviderConfigurations != null) {
			// we add listener on all column label configurations
			for (final ILabelProviderConfiguration conf : columnLabelProviderConfigurations) {
				conf.eAdapters().add(tableListener);
			}
		}

		// ROW MANAGEMENT
		// we add a listener on the rowHeaderAxisConfiguration
		if (rowHeaderAxisConfiguration != null) {
			rowHeaderAxisConfiguration.eAdapters().add(tableListener);
		}

		if (rowHeaderAxisConfiguration != null) {
			rowLabelProviderConfigurations = rowHeaderAxisConfiguration.getOwnedLabelConfigurations();
		}

		if (this.rowLabelProviderConfigurations != null) {
			// we add listener on all column label configurations
			for (final ILabelProviderConfiguration conf : rowLabelProviderConfigurations) {
				conf.eAdapters().add(tableListener);
			}
		}



	}

	/**
	 * remove the listener
	 */
	private void removeListeners() {
		// COLUMNS MANAGEMENT
		// we remove the listener on the columnHeaderAxisConfiguration
		if (columnHeaderAxisConfiguration != null) {
			columnHeaderAxisConfiguration.eAdapters().remove(tableListener);
		}



		if (this.columnLabelProviderConfigurations != null) {
			// we remove the listener on all column label configurations
			for (final ILabelProviderConfiguration conf : columnLabelProviderConfigurations) {
				conf.eAdapters().remove(tableListener);
			}
		}

		// ROW MANAGEMENT
		// we remove the listener on the rowHeaderAxisConfiguration
		if (rowHeaderAxisConfiguration != null) {
			rowHeaderAxisConfiguration.eAdapters().remove(tableListener);
		}

		if (this.rowLabelProviderConfigurations != null) {
			// we remove the listener on all row label configurations
			for (final ILabelProviderConfiguration conf : rowLabelProviderConfigurations) {
				conf.eAdapters().remove(tableListener);
			}
		}
	}

	/**
	 * update all values in the property view
	 */
	private void updateValues() {
		// We have to manage it as synExec because this is notified that the value changed (so done after)
		Display.getDefault().syncExec(new Runnable() {

			/**
			 *
			 * @see java.lang.Runnable#run()
			 *
			 */
			@Override
			public void run() {
				removeListeners();
				initOtherFieldsAndAddOthersListeners();

				final Event event = new Event();
				final Iterator<IObservable> observableIterator = getObservables().values().iterator();
				while (observableIterator.hasNext()) {
					final IObservable current = observableIterator.next();
					if (!current.isDisposed() && current instanceof Listener) {
						((Listener) current).handleEvent(event);
					}
				}
			}
		});
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement#dispose()
	 *
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (null != table) {
			table.eAdapters().remove(tableListener);
			removeListeners();
			table = null;
		}
		columnLabelProviderConfigurations = null;
		rowLabelProviderConfigurations = null;
		tableModelManager = null;
	}


	/**
	 *
	 * @return
	 */
	private Table getEditedTable() {
		return table;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#doGetObservable(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	protected IObservable doGetObservable(final String propertyPath) {
		IObservable value = null;

		Table table = getEditedTable();
		// column header properties
		if (Constants.LOCAL_COLUMN_HEADER_AXIS_CONFIGURATION_DISPLAY_FILTER.equals(propertyPath)) {
			value = new ColumnDisplayFilterHeaderObservableValue(table);
		} else if (Constants.LOCAL_COLUMN_HEADER_AXIS_CONFIGURATION_DISPLAY_INDEX.equals(propertyPath)) {
			value = new ColumnDisplayIndexHeaderObservableValue(table);
		} else if (Constants.LOCAL_COLUMN_HEADER_AXIS_CONFIGURATION_DISPLAY_LABEL.equals(propertyPath)) {
			value = new ColumnDisplayLabelHeaderObservableValue(table);
		} else if (Constants.LOCAL_COLUMN_HEADER_AXIS_CONFIGURATION_INDEX_STYLE.equals(propertyPath)) {
			value = new ColumnIndexHeaderStyleObservableValue(table);


			// row header property
		} else if (Constants.LOCAL_ROW_HEADER_AXIS_CONFIGURATION_DISPLAY_INDEX.equals(propertyPath)) {
			value = new RowDisplayIndexHeaderObservableValue(table);
		} else if (Constants.LOCAL_ROW_HEADER_AXIS_CONFIGURATION_DISPLAY_LABEL.equals(propertyPath)) {
			value = new RowDisplayLabelHeaderObservableValue(table);
		} else if (Constants.LOCAL_ROW_HEADER_AXIS_CONFIGURATION_INDEX_STYLE.equals(propertyPath)) {
			value = new RowIndexHeaderStyleObservableValue(table);


			// feature column label property
		} else if (Constants.COLUMN_FEATURE_LABEL_CONFIGURATION_DISPLAY_ICON.equals(propertyPath)) {
			value = new ColumnFeatureLabelDisplayIconObservableValue(table);
		} else if (Constants.COLUMN_FEATURE_LABEL_CONFIGURATION_DISPLAY_LABEL.equals(propertyPath)) {
			value = new ColumnFeatureLabelDisplayLabelObservableValue(table);
		} else if (Constants.COLUMN_FEATURE_LABEL_CONFIGURATION_DISPLAY_IS_DERIVED.equals(propertyPath)) {
			value = new ColumnFeatureLabelDisplayIsDerivedObservableValue(table);
		} else if (Constants.COLUMN__FEATURE_LABEL_CONFIGURATION_DISPLAY_NAME.equals(propertyPath)) {
			value = new ColumnFeatureLabelDisplayNameObservableValue(table);
		} else if (Constants.COLUMN_FEATURE_LABEL_CONFIGURATION_DISPLAY_TYPE.equals(propertyPath)) {
			value = new ColumnFeatureLabelDisplayTypeObservableValue(table);
		} else if (Constants.COLUMN_FEATURE_LABEL_CONFIGURATION_DISPLAY_MULTIPLICITY.equals(propertyPath)) {
			value = new ColumnFeatureLabelDisplayMultiplicityObservableValue(table);


			// object column label property
		} else if (Constants.COLUMN_OBJECT_LABEL_CONFIGURATION_DISPLAY_ICON.equals(propertyPath)) {
			value = new ColumnObjectLabelDisplayIconObservableValue(table);
		} else if (Constants.COLUMN_OBJECT_LABEL_CONFIGURATION_DISPLAY_LABEL.equals(propertyPath)) {
			value = new ColumnObjectLabelDisplayLabelObservableValue(table);
		} else

		// feature row label property
		if (Constants.ROW_FEATURE_LABEL_CONFIGURATION_DISPLAY_ICON.equals(propertyPath)) {
			value = new RowFeatureLabelDisplayIconObservableValue(table);
		} else if (Constants.ROW_FEATURE_LABEL_CONFIGURATION_DISPLAY_LABEL.equals(propertyPath)) {
			value = new RowFeatureLabelDisplayLabelObservableValue(table);
		} else if (Constants.ROW_FEATURE_LABEL_CONFIGURATION_DISPLAY_IS_DERIVED.equals(propertyPath)) {
			value = new RowFeatureLabelDisplayIsDerivedObservableValue(table);
		} else if (Constants.ROW__FEATURE_LABEL_CONFIGURATION_DISPLAY_NAME.equals(propertyPath)) {
			value = new RowFeatureLabelDisplayNameObservableValue(table);
		} else if (Constants.ROW_FEATURE_LABEL_CONFIGURATION_DISPLAY_TYPE.equals(propertyPath)) {
			value = new RowFeatureLabelDisplayTypeObservableValue(table);
		} else if (Constants.ROW_FEATURE_LABEL_CONFIGURATION_DISPLAY_MULTIPLICITY.equals(propertyPath)) {
			value = new RowFeatureLabelDisplayMultiplicityObservableValue(table);


			// object row label property
		} else if (Constants.ROW_OBJECT_LABEL_CONFIGURATION_DISPLAY_ICON.equals(propertyPath)) {
			value = new RowObjectLabelDisplayIconObservableValue(table);
		} else if (Constants.ROW_OBJECT_LABEL_CONFIGURATION_DISPLAY_LABEL.equals(propertyPath)) {
			value = new RowObjectLabelDisplayLabelObservableValue(table);
		}

		// paste row EObject
		else if (Constants.ROW_PASTED_EOBJECT_CONTAINMENT_FEATURE.equals(propertyPath)) {
			value = new RowPasteEObjectContainmentFeatureObservableValue(table);
		} else if (Constants.ROW_PASTED_EOBJECT_ID.equals(propertyPath)) {
			value = new RowPasteEObjectElementTypeIdObservableValue(table);
		} else if (Constants.ROW_PASTED_OBJECT_DETACHED_MODE_FEATURE.equals(propertyPath)) {
			value = new RowPasteObjectDetachedModeObservableValue(getEditedTable());
		} else if (Constants.ROW_PASTED_OBJECT_POST_ACTIONS_FEATURE.equals(propertyPath)) {
			value = new RowPasteObjectPostActionsObservableValue(getDomain(), getEditedTable());
		} else if (Constants.ROW_PASTED_EOBJECT_AXIS_IDENTIFIER_FEATURE.equals(propertyPath)) {
			value = new RowPasteEObjectAxisIdentifierObservableValue(table);
		}

		// paste column EObject
		else if (Constants.COLUMN_PASTED_EOBJECT_CONTAINMENT_FEATURE.equals(propertyPath)) {
			value = new ColumnPasteEObjectContainmentFeatureObservableValue(table);
		} else if (Constants.COLUMN_PASTED_EOBJECT_ID.equals(propertyPath)) {
			value = new ColumnPasteEObjectElementTypeIdObservableValue(table);
		} else if (Constants.COLUMN_PASTED_OBJECT_DETACHED_MODE_FEATURE.equals(propertyPath)) {
			value = new ColumnPasteObjectDetachedModeObservableValue(getEditedTable());
		} else if (Constants.COLUMN_PASTED_OBJECT_POST_ACTIONS_FEATURE.equals(propertyPath)) {
			value = new ColumnPasteObjectPostActionsObservableValue(getDomain(), getEditedTable());
		} else if (Constants.COLUMN_PASTED_EOBJECT_AXIS_IDENTIFIER_FEATURE.equals(propertyPath)) {
			value = new ColumnPasteEObjectAxisIdentifierObservableValue(table);
		}

		else if (Constants.TABLE_LABEL.equals(propertyPath)) {
			value = new TableLabelObservableValue(table);
		} else if (Constants.TABLE_PROTOTYPE.equals(propertyPath)) {
			value = new TablePrototypeObservableValue(table);
		}

		if (value != null) {
			return value;
		}
		return super.doGetObservable(propertyPath);
	}

	/**
	 * @param propertyPath
	 * @return
	 */
	@Override
	protected boolean isFeatureEditable(String propertyPath) {
		boolean res = super.isFeatureEditable(propertyPath);
		if (!res) {
			// feature column label property
			if (Constants.COLUMN_FEATURE_LABEL_CONFIGURATION_DISPLAY_ICON.equals(propertyPath)) {
				res = true;
			} else if (Constants.COLUMN_FEATURE_LABEL_CONFIGURATION_DISPLAY_LABEL.equals(propertyPath)) {
				res = true;
			} else if (Constants.COLUMN_FEATURE_LABEL_CONFIGURATION_DISPLAY_IS_DERIVED.equals(propertyPath)) {
				res = true;
			} else if (Constants.COLUMN__FEATURE_LABEL_CONFIGURATION_DISPLAY_NAME.equals(propertyPath)) {
				res = true;
			} else if (Constants.COLUMN_FEATURE_LABEL_CONFIGURATION_DISPLAY_TYPE.equals(propertyPath)) {
				res = true;
			} else if (Constants.COLUMN_FEATURE_LABEL_CONFIGURATION_DISPLAY_MULTIPLICITY.equals(propertyPath)) {
				res = true;

				// object column label property
			} else if (Constants.COLUMN_OBJECT_LABEL_CONFIGURATION_DISPLAY_ICON.equals(propertyPath)) {
				res = true;
			} else if (Constants.COLUMN_OBJECT_LABEL_CONFIGURATION_DISPLAY_LABEL.equals(propertyPath)) {
				res = true;
			}



			// feature row label property
			else if (Constants.ROW_FEATURE_LABEL_CONFIGURATION_DISPLAY_ICON.equals(propertyPath)) {
				res = true;
			} else if (Constants.ROW_FEATURE_LABEL_CONFIGURATION_DISPLAY_LABEL.equals(propertyPath)) {
				res = true;
			} else if (Constants.ROW_FEATURE_LABEL_CONFIGURATION_DISPLAY_IS_DERIVED.equals(propertyPath)) {
				res = true;
			} else if (Constants.ROW__FEATURE_LABEL_CONFIGURATION_DISPLAY_NAME.equals(propertyPath)) {
				res = true;
			} else if (Constants.ROW_FEATURE_LABEL_CONFIGURATION_DISPLAY_TYPE.equals(propertyPath)) {
				res = true;
			} else if (Constants.ROW_FEATURE_LABEL_CONFIGURATION_DISPLAY_MULTIPLICITY.equals(propertyPath)) {
				res = true;

				// object row label property
			} else if (Constants.ROW_OBJECT_LABEL_CONFIGURATION_DISPLAY_ICON.equals(propertyPath)) {
				res = true;
			} else if (Constants.ROW_OBJECT_LABEL_CONFIGURATION_DISPLAY_LABEL.equals(propertyPath)) {
				res = true;
			}

			// paste row EObject
			else if (Constants.ROW_PASTED_EOBJECT_CONTAINMENT_FEATURE.equals(propertyPath)) {
				res = new RowContainmentFeatureContentProvider(getEditedTable(), getEditedTable().getContext().eClass()).getElements().length != 0;
			} else if (Constants.ROW_PASTED_EOBJECT_ID.equals(propertyPath)) {
				res = new RowElementTypeIdContentProvider(this.tableModelManager).getElements().length != 0;
			} else if (Constants.ROW_PASTED_OBJECT_DETACHED_MODE_FEATURE.equals(propertyPath)) {
				res = true;
			} else if (Constants.ROW_PASTED_OBJECT_POST_ACTIONS_FEATURE.equals(propertyPath)) {
				res = new RowPostActionIdsProvider(this.tableModelManager).getElements().length != 0;
			} else if (Constants.ROW_PASTED_EOBJECT_AXIS_IDENTIFIER_FEATURE.equals(propertyPath)) {
				res = new RowAxisIdentifierContentProvider(this.tableModelManager).getElements().length != 0;

				// paste column EObject
			} else if (Constants.COLUMN_PASTED_EOBJECT_CONTAINMENT_FEATURE.equals(propertyPath)) {
				res = new ColumnContainmentFeatureContentProvider(getEditedTable(), getEditedTable().getContext().eClass()).getElements().length != 0;
			} else if (Constants.COLUMN_PASTED_EOBJECT_ID.equals(propertyPath)) {
				res = new ColumnElementTypeIdContentProvider(this.tableModelManager).getElements().length != 0;
			} else if (Constants.COLUMN_PASTED_OBJECT_DETACHED_MODE_FEATURE.equals(propertyPath)) {
				res = true;
			} else if (Constants.COLUMN_PASTED_OBJECT_POST_ACTIONS_FEATURE.equals(propertyPath)) {
				res = new ColumnPostActionIdsProvider(this.tableModelManager).getElements().length != 0;
			} else if (Constants.COLUMN_PASTED_EOBJECT_AXIS_IDENTIFIER_FEATURE.equals(propertyPath)) {
				res = new ColumnAxisIdentifierContentProvider(this.tableModelManager).getElements().length != 0;
			}

			else if (Constants.TABLE_LABEL.equals(propertyPath)) {
				res = true;
			}
		}

		return res;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getContentProvider(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public IStaticContentProvider getContentProvider(final String propertyPath) {
		IStaticContentProvider provider = null;
		if (Constants.TABLE_CONTEXT.equals(propertyPath)) {
			Table table = getEditedTable();
			provider = new ContextFeatureContentProvider(table, getRoot(table.getContext()));
		} else if (Constants.TABLE_OWNER.equals(propertyPath)) {
			Table table = getEditedTable();
			provider = new ContextFeatureContentProvider(table, getRoot(table.getOwner()));
		} else if (Constants.ROW_PASTED_EOBJECT_CONTAINMENT_FEATURE.equals(propertyPath)) {
			provider = new RowContainmentFeatureContentProvider(getEditedTable(), getEditedTable().getContext().eClass());
		} else if (Constants.COLUMN_PASTED_EOBJECT_CONTAINMENT_FEATURE.equals(propertyPath)) {
			provider = new ColumnContainmentFeatureContentProvider(getEditedTable(), getEditedTable().getContext().eClass());
		} else if (Constants.ROW_PASTED_EOBJECT_ID.equals(propertyPath)) {
			provider = new RowElementTypeIdContentProvider(this.tableModelManager);
		} else if (Constants.COLUMN_PASTED_EOBJECT_ID.equals(propertyPath)) {
			provider = new ColumnElementTypeIdContentProvider(this.tableModelManager);
		} else if (Constants.ROW_PASTED_OBJECT_POST_ACTIONS_FEATURE.equals(propertyPath)) {
			provider = new RowPostActionIdsProvider(this.tableModelManager);
		} else if (Constants.COLUMN_PASTED_OBJECT_POST_ACTIONS_FEATURE.equals(propertyPath)) {
			provider = new RowPostActionIdsProvider(this.tableModelManager);
		} else if (Constants.ROW_PASTED_EOBJECT_AXIS_IDENTIFIER_FEATURE.equals(propertyPath)) {
			provider = new RowAxisIdentifierContentProvider(tableModelManager);
		} else if (Constants.COLUMN_PASTED_EOBJECT_AXIS_IDENTIFIER_FEATURE.equals(propertyPath)) {
			provider = new ColumnAxisIdentifierContentProvider(tableModelManager);
		}
		if (provider != null) {
			return provider;
		}
		return super.getContentProvider(propertyPath);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#isUnique(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public boolean isUnique(final String propertyPath) {
		if (Constants.ROW_PASTED_OBJECT_POST_ACTIONS_FEATURE.equals(propertyPath)) {
			return true;
		} else if (Constants.COLUMN_PASTED_OBJECT_POST_ACTIONS_FEATURE.equals(propertyPath)) {
			return true;
		}
		return super.isUnique(propertyPath);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#isOrdered(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public boolean isOrdered(final String propertyPath) {
		if (Constants.ROW_PASTED_OBJECT_POST_ACTIONS_FEATURE.equals(propertyPath)) {
			return true;
		} else if (Constants.COLUMN_PASTED_OBJECT_POST_ACTIONS_FEATURE.equals(propertyPath)) {
			return true;
		}
		return super.isOrdered(propertyPath);
	}

	@Override
	public ILabelProvider getLabelProvider(String propertyPath) {
		ILabelProvider provider = null;
		if (propertyPath.endsWith(Constants.TABLE_PROTOTYPE)) {
			provider = new ILabelProvider() {
				@Override
				public void addListener(ILabelProviderListener listener) {
				}

				@Override
				public void removeListener(ILabelProviderListener listener) {
				}

				@Override
				public void dispose() {
				}

				@Override
				public boolean isLabelProperty(Object element, String property) {
					return false;
				}

				@Override
				public Image getImage(Object element) {
					if (element instanceof TableViewPrototype) {
						PolicyChecker checker = PolicyChecker.getFor(table);
						TableViewPrototype prototype = (TableViewPrototype) element;
						return null != prototype.getRepresentationKind() && null != checker && !checker.isInViewpoint(prototype.getRepresentationKind()) ? prototype.getGrayedIcon() : prototype.getIcon();
					}
					return ViewPrototype.UNAVAILABLE_VIEW.getIcon();
				}

				@Override
				public String getText(Object element) {
					if (!(element instanceof TableViewPrototype)) {
						return null;
					}
					return ((TableViewPrototype) element).getQualifiedName();
				}
			};
		}

		if (Constants.COLUMN_PASTED_EOBJECT_AXIS_IDENTIFIER_FEATURE.equals(propertyPath) || Constants.ROW_PASTED_EOBJECT_AXIS_IDENTIFIER_FEATURE.equals(propertyPath)) {
			provider = new AxisIdentifierLabelProvider(tableModelManager);
		}

		if (null == provider) {
			provider = super.getLabelProvider(propertyPath);
		}
		return provider;
	}

	/**
	 * Gets the root EObject from the given one
	 *
	 * @param obj
	 *            An object
	 * @return The root object which is an ancestor of the given one
	 */
	private EObject getRoot(EObject obj) {
		EObject current = obj;
		EObject parent = obj.eContainer();
		while (parent != null) {
			current = parent;
			parent = parent.eContainer();
		}
		return current;
	}
}
