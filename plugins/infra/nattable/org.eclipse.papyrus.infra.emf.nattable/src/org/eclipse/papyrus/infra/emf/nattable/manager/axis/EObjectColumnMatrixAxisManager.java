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
 *   Vincent Lorenzo (CEA-LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   Vincent Lorenzo (CEA-LIST) - bug 520566
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 520602, 522721
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.nattable.manager.axis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.emf.expressions.ExpressionsPackage;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsFactory;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralTrueExpression;
import org.eclipse.papyrus.infra.nattable.manager.axis.ITreeItemAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.LocalTableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.MasterObjectAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;

/**
 * This axis manager has been developed to manage the columns for Matrix. It can't be used row manager
 * 
 * @since 3.0
 *
 *
 *        TODO : update contents when a property of a displayed object changes (in case of custom expression used to filter contents)
 */
public class EObjectColumnMatrixAxisManager extends AbstractSynchronizedOnEStructuralFeatureAxisManager {

	// The next 2 event types are duplicated from oep.uml.tools.listeners.StereotypeElementListener.StereotypeExtensionNotification
	// to avoid the plug-in dependency from oep.infra.emf.nattable to oep.uml.tools.
	/** Event type notification for stereotype application action. */
	private static final int STEREOTYPE_APPLIED_TO_ELEMENT = Notification.EVENT_TYPE_COUNT + 21;

	/** Event type notification for stereotype unapplication action. */
	private static final int STEREOTYPE_UNAPPLIED_FROM_ELEMENT = Notification.EVENT_TYPE_COUNT + 22;

	/**
	 * the expression returned when there is no expression registered in the TreeFillingConfiguration
	 */
	private final IBooleanEObjectExpression defaultFilter = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

	/**
	 * The table eobjects to listen to be notified when the table configuration changes
	 */
	private List<EObject> listenEObjects = new ArrayList<EObject>();

	/**
	 * The list of column source EObjects to be listened when model element changes occur.
	 */
	private List<EObject> listenColumnSourceEObjects = new ArrayList<EObject>();

	/**
	 * This map takes the {@link TreeFillingConfiguration} as key and its helper as value
	 */
	private Map<TreeFillingConfiguration, TreeFillingConfigurationHelper> map;

	/**
	 * This map takes a {@link EStructuralFeature} as key and the TreeFillingConfiguration providing it as value
	 */
	private Map<EStructuralFeature, TreeFillingConfiguration> featureVSConfiguration;

	/**
	 * The list of feature to listen to be notified when the table configuration change
	 */
	private List<EStructuralFeature> tableFeatureToListen = new ArrayList<EStructuralFeature>();


	/**
	 * the listener used to be notified when the table configuration change
	 */
	private Adapter tableConfigurationChangesListener = new AdapterImpl() {

		public void notifyChanged(final Notification msg) {
			if (msg.isTouch()) {
				return;
			}
			final Object listenFeature = msg.getFeature();
			boolean toListen = false;
			if (listenFeature instanceof EStructuralFeature) {
				final EClass eClass = (EClass) ((EStructuralFeature) listenFeature).getEContainingClass();// eContainer();
				toListen = ExpressionsPackage.eINSTANCE.getIExpression().isSuperTypeOf(eClass);

			}
			// final boolean toListen = listenFeature instanceof EStructuralFeature && ((EClass) ((EStructuralFeature) listenFeature).eContainer()).isInstance(ExpressionsPackage.eINSTANCE.getIExpression());
			if (toListen || tableFeatureToListen.contains(msg.getFeature())) {
				updateAxisAfterConfigurationChange();
				// 2. we init the listener to be notified when the current table configuration changes, to be able to update the columns list
				cleanAndReinitListenObjects();

				// 3. we init the list of feature to listen for the columns sources elements
				cleanAndFillTreeFillingConfigurationMap();
			}
		};
	};

	/**
	 * The listener to be notified when the column source objects are changed (i.e. an element is deleted from a column source object, which requires a table update).
	 */
	private Adapter columnSourceObjectChangesListener = new AdapterImpl() {

		public void notifyChanged(final Notification msg) {
			if (msg.isTouch()) {
				return;
			}

			final int eventType = msg.getEventType();

			// The table axis must be updated in the following cases: 
			// (1) elements are deleted (added) from (to) the column source objects
			if ((Notification.REMOVE == eventType || Notification.ADD == eventType

					// (2) or elements are reordered inside its container (a column source object), a REMOVE_MANY is followed by an ADD_MANY,
					// so we capture the later one in order to refresh the table axis.
					// Without doing this, user will see the incoherent when closing and reopening the relationship matrix table 
					|| Notification.ADD_MANY == eventType

					// (3) or elements are applied/unapplied stereotypes and the column filter expression is "Has Applied Stereotypes Expression"
					|| STEREOTYPE_APPLIED_TO_ELEMENT == eventType || STEREOTYPE_UNAPPLIED_FROM_ELEMENT == eventType)

					// (4) and the table axis are really changed
					&& tableAxisShouldBeUpdated()) {

				updateAxisAfterColumnSourceObjectChanges();
				cleanAndReinitListenColumnSourceObjects();
			}
		};
	};

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.AbstractSynchronizedOnEStructuralFeatureAxisManager#init(org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager,
	 *      org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation, org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider)
	 *
	 * @param manager
	 * @param rep
	 * @param provider
	 */
	@Override
	public void init(final INattableModelManager manager, final AxisManagerRepresentation rep, final AbstractAxisProvider provider) {
		super.init(manager, rep, provider);
		Assert.isTrue(getRepresentedContentProvider() instanceof MasterObjectAxisProvider);
		// 1. we init list of feature to listen
		this.listenEObjects = new ArrayList<EObject>();
		this.tableFeatureToListen = new ArrayList<EStructuralFeature>();
		this.tableFeatureToListen.add(NattablePackage.eINSTANCE.getTable_LocalColumnHeaderAxisConfiguration());
		this.tableFeatureToListen.add(NattableaxisconfigurationPackage.eINSTANCE.getLocalTableHeaderAxisConfiguration_AxisManagerConfigurations());
		this.tableFeatureToListen.add(NattableaxisconfigurationPackage.eINSTANCE.getTreeFillingConfiguration_AxisUsedAsAxisProvider());
		this.tableFeatureToListen.add(NattableaxisconfigurationPackage.eINSTANCE.getTreeFillingConfiguration_FilterRule());
		this.tableFeatureToListen.add(NattableaxisproviderPackage.eINSTANCE.getIMasterAxisProvider_Sources());

		// 2. we init the listener to be notified when the current table configuration changes, to be able to update the columns list
		cleanAndReinitListenObjects();
		cleanAndReinitListenColumnSourceObjects();

		// 3. we init the list of feature to listen for the columns sources elements
		cleanAndFillTreeFillingConfigurationMap();

	}

	/**
	 * This method adds the {@link Adapter} on an {@link EObject}.
	 * The list of listen EObjects is also updated.
	 * 
	 * @param eobject
	 *            the eobject to add
	 * @param adapter
	 *            the adapter to be added
	 * @param listenEObjectsList
	 *            the list of listen EObjects
	 */
	private void addListenerOnEObjects(final EObject eobject, final Adapter adapter, final List<EObject> listenEObjectsList) {
		if (null != eobject) {
			if (!eobject.eAdapters().contains(adapter)) {// to avoid infinite loop in some case ?
				eobject.eAdapters().add(adapter);
				listenEObjectsList.add(eobject);
			}
		}
	}

	/**
	 * This method removes the {@link org.eclipse.emf.common.notify.Adapter} on the list of listen EObjects.
	 * 
	 * @param listenEObjectsList
	 *            The list of listen EObjects
	 * @param adapter
	 *            The adapter to be removed
	 */
	private void removeListenersOnEObjects(final List<EObject> listenEObjectsList, final Adapter adapter) {
		for (final EObject current : listenEObjectsList) {
			current.eAdapters().remove(adapter);
		}
	}

	/**
	 * This method removes the registered listener {@link #tableConfigurationChangesListener} on all objects referenced by {@link #listenEObjects},
	 * Then the listener {@link #tableConfigurationChangesListener} is applied on all interesting objects of the tableS
	 * 
	 */
	private void cleanAndReinitListenObjects() {
		removeListenersOnEObjects(this.listenEObjects, this.tableConfigurationChangesListener);

		final Table table = getTableManager().getTable();
		if (null == table) {
			return;
		}
		addListenerOnEObjects(table, this.tableConfigurationChangesListener, this.listenEObjects);

		final LocalTableHeaderAxisConfiguration columnHeaderAxisConfiguration = table.getLocalColumnHeaderAxisConfiguration();

		// COLUMNS MANAGEMENT
		// we add a listener on the columnHeaderAxisConfiguration
		if (columnHeaderAxisConfiguration instanceof LocalTableHeaderAxisConfiguration) {
			addListenerOnEObjects(columnHeaderAxisConfiguration, this.tableConfigurationChangesListener, this.listenEObjects);

			// the best way will be to listen the TreeFillingConfiguration of the AxisManagerConfigruation, but it is useless, because the referenced TreeFillingConfiguration are also accessible
			// from the owned axis configuration
			for (final IAxisConfiguration current : columnHeaderAxisConfiguration.getOwnedAxisConfigurations()) {
				if (current instanceof TreeFillingConfiguration && ((TreeFillingConfiguration) current).getDepth() == 1) {
					final TreeFillingConfiguration treeFillingConfiguration = (TreeFillingConfiguration) current;
					addListenerOnEObjects(treeFillingConfiguration, this.tableConfigurationChangesListener, this.listenEObjects);

					IAxis provider = treeFillingConfiguration.getAxisUsedAsAxisProvider();
					addListenerOnEObjects(provider, this.tableConfigurationChangesListener, this.listenEObjects);
					final IBooleanEObjectExpression filterRule = treeFillingConfiguration.getFilterRule();
					addListenerOnEObjects(filterRule, this.tableConfigurationChangesListener, this.listenEObjects);
				}
			}
		}

		final ICellEditorConfiguration cellEditorConfiguration = table.getOwnedCellEditorConfigurations();
		if (null != cellEditorConfiguration) {
			addListenerOnEObjects(cellEditorConfiguration, this.tableConfigurationChangesListener, this.listenEObjects);
			if (cellEditorConfiguration instanceof GenericRelationshipMatrixCellEditorConfiguration) {
				final GenericRelationshipMatrixCellEditorConfiguration tmp = (GenericRelationshipMatrixCellEditorConfiguration) cellEditorConfiguration;
				final IBooleanEObjectExpression filter = tmp.getCellContentsFilter();
				addListenerOnEObjects(filter, this.tableConfigurationChangesListener, this.listenEObjects);
			}
		}

		final AbstractAxisProvider axisProvider = table.getCurrentColumnAxisProvider();
		if (axisProvider instanceof IMasterAxisProvider) {
			addListenerOnEObjects(axisProvider, this.tableConfigurationChangesListener, this.listenEObjects);
		}

		final AbstractAxisProvider rowAxisProvider = table.getCurrentRowAxisProvider();
		if (rowAxisProvider instanceof IMasterAxisProvider) {
			addListenerOnEObjects(rowAxisProvider, this.tableConfigurationChangesListener, this.listenEObjects);
		}
	}
	
	/**
	 * This method removes the registered listener {@link #columnSourceObjectChangesListener} on all objects referenced by {@link #listenColumnSourceEObjects},
	 * Then the listener {@link #columnSourceObjectChangesListener} is applied on all interesting objects of the tableS
	 */
	private void cleanAndReinitListenColumnSourceObjects() {
		// Table axis should be refreshed according to the following cases:
		// - Sub-elements of column source object are deleted, added or moved 
		// - Stereotypes are applied/unapplied for sub-elements when column filter expression is "Has Apply Stereotypes Expression"
		// - The column source object is deleted from the model explorer
		// - Undo/redo operation
		List<EObject> columnSources = getColumnSources();
		if (!columnSources.isEmpty()) {
			removeListenersOnEObjects(listenColumnSourceEObjects, columnSourceObjectChangesListener);;
			for (EObject columnContainer : columnSources) {
				// To survey changes in column source objects, i.e. delete or add a child element
				addListenerOnEObjects(columnContainer, columnSourceObjectChangesListener, listenColumnSourceEObjects);

				// Survey the apply/unapply stereotypes of the child elements
				for (EObject childObj : columnContainer.eContents()) {
					addListenerOnEObjects(childObj, columnSourceObjectChangesListener, listenColumnSourceEObjects);
				}
			}
		}
	}

	/**
	 * This method clear the map {@link #map} and {@link #featureVSConfiguration}, then fill them a new time with the new values
	 */
	private void cleanAndFillTreeFillingConfigurationMap() {
		if (null == this.map) {
			this.map = new HashMap<TreeFillingConfiguration, EObjectColumnMatrixAxisManager.TreeFillingConfigurationHelper>();
		}
		if (null == featureVSConfiguration) {
			this.featureVSConfiguration = new HashMap<EStructuralFeature, TreeFillingConfiguration>();
		}
		this.map.clear();
		this.featureVSConfiguration.clear();

		final Table table = getTableManager().getTable();
		if (null != table) {
			for(final TreeFillingConfiguration treeFillingConfiguration : FillingConfigurationUtils.getTreeFillingConfigurationForColumn(table, this.representedAxisManager)){
				if (treeFillingConfiguration.getDepth() == 1) {
					final TreeFillingConfigurationHelper helper = new TreeFillingConfigurationHelper(treeFillingConfiguration);
					this.map.put(treeFillingConfiguration, helper);
					this.featureVSConfiguration.put(helper.getEStructuralFeatureToListen(), treeFillingConfiguration);
				}
			}
		}
	}



	/**
	 * @return
	 * 		the features to listen according to the current table configuration or <code>null</code> if it is not defined
	 */
	protected Collection<EStructuralFeature> getListenFeatures() {// TODO : avoid to update listenfeature each time, maybe we should remove this field ?
		if (null == this.map) {
			cleanAndFillTreeFillingConfigurationMap();
		}
		this.listenFeatures = new HashSet<EStructuralFeature>();
		for (TreeFillingConfigurationHelper helper : this.map.values()) {// TODO avoid to clean a fill each time!
			final EStructuralFeature current = helper.getEStructuralFeatureToListen();
			this.listenFeatures.add(current);
		}
		return this.listenFeatures;
	}


	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.AbstractSynchronizedOnEStructuralFeatureAxisManager#verifyFeatureMultiplicity()
	 *
	 */
	@Override
	protected void verifyFeatureMultiplicity() {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.AbstractSynchronizedOnEStructuralFeatureAxisManager#verifyValues()
	 *
	 */
	@Override
	protected void verifyValues() {
		// nothing to do
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.AbstractSynchronizedOnEStructuralFeatureAxisManager#getFeaturesValue()
	 *
	 * @return
	 */
	@Override
	protected List<Object> getFeaturesValue() {
		getListenFeatures();// for initialization
		final List<Object> values = new ArrayList<Object>();
		for (final EObject source : getColumnSources()) {
			values.addAll(getListenFeatureValueFor(source));

		}
		return values;
	}

	/**
	 * 
	 * @param listenObject
	 * @return
	 */
	protected List<Object> getListenFeatureValueFor(final EObject listenObject) {
		final List<Object> values = new ArrayList<Object>();
		for (final TreeFillingConfigurationHelper current : this.map.values()) {
			final EStructuralFeature feature = current.getEStructuralFeatureToListen();
			final IBooleanEObjectExpression exp = current.getFilterRule();
			if (null != feature) {
				Object value = listenObject.eGet(feature);
				if (feature.isMany()) {
					for (Object tmp : (Collection<?>) value) {
						if (tmp instanceof EObject && exp.evaluate((EObject) tmp)) {
							values.add(tmp);
						}
					}
				} else if (value instanceof EObject && exp.evaluate((EObject) value)) {
					values.add(value);
				}
			}
		}
		return values;

	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#isAllowedContents(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public boolean isAllowedContents(Object object) {
		return true;
	}

	/**
	 * This method returns <code>true</code> if the new object can be added to the column list and <code>false</code> otherwise
	 * 
	 * The signature of this method has been copied from the interface {@link ITreeItemAxisManagerForEventList}
	 * 
	 * @param objectToTest
	 *            an object
	 * @param semanticParent
	 *            the semantic parent of the object to test
	 * @param conf
	 *            the configuration which provides the object to test
	 * @param depth
	 *            the depth on which we want to apply this object
	 * @return
	 * 		<code>true</code> if the object is accepted and false if not
	 */
	public boolean isAllowedContents(final Object objectToTest, final Object semanticParent, final TreeFillingConfiguration conf, final int depth) {
		boolean result = false;
		if (semanticParent instanceof EObject && objectToTest instanceof EObject && null != conf && null != conf.getAxisUsedAsAxisProvider()) {
			final IBooleanEObjectExpression filter = null != conf.getFilterRule() ? conf.getFilterRule() : this.defaultFilter;
			if (filter.evaluate((EObject) objectToTest)) {
				result = CellManagerFactory.INSTANCE.getCrossValueAsCollection(semanticParent, conf.getAxisUsedAsAxisProvider(), this.tableManager).contains(objectToTest);
			}
		}
		return result;
	}

	/**
	 *
	 * @param notification
	 *            update the list of the managed objects if its required
	 */
	@Override
	protected void featureValueHasChanged(final Notification notification) {
		if (notification.isTouch()) {
			return;
		}
		final EStructuralFeature editedFeature = (EStructuralFeature) (notification.getFeature() instanceof EStructuralFeature ? notification.getFeature() : null);
		if (null == editedFeature) {
			return;
		}

		final TreeFillingConfiguration configuration = this.featureVSConfiguration.get(editedFeature);
		if (null == configuration) {
			return;
		}

		final int eventType = notification.getEventType();
		final EObject editedObject = (EObject) (notification.getNotifier() instanceof EObject ? notification.getNotifier() : null);
		if (null == editedObject) {
			return;
		}
		final List<Object> toAdd = new ArrayList<Object>();
		final List<Object> toRemove = new ArrayList<Object>();

		switch (eventType) {
		case Notification.REMOVING_ADAPTER:
			break;// nothing to do
		case Notification.ADD:
			Object newValue = notification.getNewValue();
			if (isAllowedContents(newValue, editedObject, configuration, configuration.getDepth()) && !isAlreadyManaged(newValue)) {
				toAdd.add(newValue);
			}
			break;
		case Notification.ADD_MANY:
			Collection<?> newValues = (Collection<?>) notification.getNewValue();
			for (final Object current : newValues) {
				if (isAllowedContents(current, editedObject, configuration, configuration.getDepth()) && !isAlreadyManaged(current)) {
					toAdd.add(current);
				}
			}
			break;
		case Notification.EVENT_TYPE_COUNT:
			break;
		case Notification.MOVE:
			final Collection<EObject> collection = (Collection<EObject>) ((EObject) notification.getNotifier()).eGet((EStructuralFeature) notification.getFeature());
			final Collection<EObject> subList = getSubFromFirstNotEquals(collection, (Integer) notification.getOldValue(), getIndex(collection, (EObject) notification.getNewValue()));

			toRemove.addAll(subList);
			toAdd.addAll(subList);
			break;
		case Notification.REMOVE:
			final Object oldValue = notification.getOldValue();
			if (this.managedObject.contains(oldValue)) {
				toRemove.add(oldValue);
			}
			break;
		case Notification.REMOVE_MANY:
			Collection<?> oldValues = (Collection<?>) notification.getOldValue();
			for (final Object current : oldValues) {
				if (this.managedObject.contains(oldValues)) {
					toRemove.add(current);
				}
			}
			break;
		case Notification.RESOLVE:
		case Notification.SET:
		case Notification.UNSET:
			// case Notification.NO_FEATURE_ID:
			// case Notification.NO_INDEX:

		default:
			break;
		}
		if (toAdd.size() > 0 || toRemove.size() > 0) {
			updateManagedList(toAdd, toRemove);
		}
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#canBeUsedAsRowManager()
	 *
	 * @return
	 */
	@Override
	public boolean canBeUsedAsRowManager() {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#canBeUsedAsColumnManager()
	 *
	 * @return
	 */
	@Override
	public boolean canBeUsedAsColumnManager() {
		return true;
	}

	/**
	 * 
	 * @return
	 * 		the list of the objects used as sources to provide the columns
	 */
	protected List<EObject> getColumnSources() {
		AbstractAxisProvider columnProvider = getTableManager().getTable().getCurrentColumnAxisProvider();
		List<EObject> context = new ArrayList<EObject>();
		if (columnProvider instanceof MasterObjectAxisProvider) {
			for (IWrapper wrapper : ((MasterObjectAxisProvider) columnProvider).getSources()) {
				if (wrapper.getElement() instanceof EObject) {
					context.add((EObject) wrapper.getElement());
				}
			}
		}
		return context;
	}

	/**
	 * This method allows to update the displayed column after changes in the table configuration
	 */
	protected void updateAxisAfterConfigurationChange() {
		if (null == getTableManager() || null == getTableManager().getTable() || null == getTableManager().getTable().getCurrentColumnAxisProvider()) {
			return;// we are deleting the table
		}

		List<Object> allAxisToDisplay = new ArrayList<Object>();
		for (final IWrapper current : ((IMasterAxisProvider) getTableManager().getTable().getCurrentColumnAxisProvider()).getSources()) {
			if (current.getElement() instanceof EObject) {
				allAxisToDisplay.addAll(getListenFeatureValueFor((EObject) current.getElement()));
			}
		}
		List<Object> toAdd = new ArrayList<Object>(allAxisToDisplay);
		toAdd.removeAll(this.managedObject);


		List<Object> toRemove = new ArrayList<Object>(this.managedObject);
		toRemove.removeAll(allAxisToDisplay);

		updateManagedList(toAdd, toRemove);
	}

	/**
	 * This method allows to update the displayed column axis when changes related to the column source objects occur.
	 * @since 4.0
	 */
	protected void updateAxisAfterColumnSourceObjectChanges() {
		List<Object> allAxisToDisplay = getAllTableAxisToDisplay();

		this.managedObject.clear();
		this.managedObject.addAll(allAxisToDisplay);

		getTableManager().updateAxisContents(getRepresentedContentProvider());
	}

	/**
	 * Check if the table axis should be updated or not.
	 *
	 * @return <code>true</code> if the old axis objects is not equal the new one, <code>false</code> otherwise
	 * @since 4.0
	 */
	protected boolean tableAxisShouldBeUpdated() {
		return (!this.managedObject.equals(getAllTableAxisToDisplay()));
	}

	/**
	 * @return the list of all table axis to be displayed
	 * @since 4.0
	 */
	protected List<Object> getAllTableAxisToDisplay() {
		if (null == getTableManager() || null == getTableManager().getTable() || null == getTableManager().getTable().getCurrentColumnAxisProvider()) {
			return null;
		}

		List<Object> allAxisToDisplay = new ArrayList<Object>();
		for (final IWrapper current : ((IMasterAxisProvider) getTableManager().getTable().getCurrentColumnAxisProvider()).getSources()) {
			if (current.getElement() instanceof EObject) {
				allAxisToDisplay.addAll(getListenFeatureValueFor((EObject) current.getElement()));
			}
		}
		return allAxisToDisplay;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#dispose()
	 *
	 */
	@Override
	public void dispose() {
		removeListenersOnEObjects(this.listenEObjects, this.tableConfigurationChangesListener);
		removeListenersOnEObjects(this.listenColumnSourceEObjects, this.columnSourceObjectChangesListener);
		getTableManager().getTable().eAdapters().remove(this.tableConfigurationChangesListener);
		this.tableFeatureToListen.clear();
		this.featureVSConfiguration.clear();
		this.map.clear();
		super.dispose();
	}

	/**
	 * 
	 * An helper to ease access to TreeFillingConfiguration data
	 *
	 */
	protected class TreeFillingConfigurationHelper {

		/**
		 * the expression returned when there is no expression registered in the TreeFillingConfiguration
		 */
		private final IBooleanEObjectExpression defaultExpression = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

		/**
		 * The TreeFillingConfiguration
		 */
		private final TreeFillingConfiguration fillingConfiguration;

		/**
		 * 
		 * Constructor.
		 *
		 * @param configuration
		 *            the TreeFillingConfiguration on which will work this helper
		 */
		public TreeFillingConfigurationHelper(final TreeFillingConfiguration configuration) {
			this.fillingConfiguration = configuration;
		}

		/**
		 * 
		 * @return
		 * 		the filter rule registered in the TreeFillingConfiguration or {@link LiteralTrueExpression} when no expression is registered
		 */
		public IBooleanEObjectExpression getFilterRule() {
			return null == this.fillingConfiguration.getFilterRule() ? this.defaultExpression : this.fillingConfiguration.getFilterRule();
		}

		private Object getFeatureToListen() {
			if (null != this.fillingConfiguration.getAxisUsedAsAxisProvider()) {
				return this.fillingConfiguration.getAxisUsedAsAxisProvider().getElement();
			}
			return null;
		}

		/**
		 * 
		 * @return
		 * 		the {@link EStructuralFeature} to listen
		 */
		public EStructuralFeature getEStructuralFeatureToListen() {
			return (EStructuralFeature) (getFeatureToListen() instanceof EStructuralFeature ? getFeatureToListen() : null);
		}
	}
}
