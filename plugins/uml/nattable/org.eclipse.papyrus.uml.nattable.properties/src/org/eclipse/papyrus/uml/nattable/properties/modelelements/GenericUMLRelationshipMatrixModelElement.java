/*****************************************************************************
 * Copyright (c) 2017, 2018 CEA LIST and others.
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
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Bug 532639
 *   Asma Smaoui (CEA LIST) - asma.smaoui@cea.fr - Bug 533613
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.properties.modelelements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;
import org.eclipse.papyrus.infra.emf.expressions.properties.utils.BooleanEObjectExpressionPropertyEditorFactory;
import org.eclipse.papyrus.infra.nattable.contentprovider.MatrixDirectionContentProvider;
import org.eclipse.papyrus.infra.nattable.contentprovider.MatrixRelationshipOwnerStrategyContentProvider;
import org.eclipse.papyrus.infra.nattable.contentprovider.MatrixSourcesContentProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.LocalTableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.EObjectWrapper;
import org.eclipse.papyrus.infra.nattable.provider.MatrixRelationshipDirectionLabelProvider;
import org.eclipse.papyrus.infra.nattable.provider.MatrixRelationshipOwnerStrategyLabelProvider;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.ui.emf.utils.ProviderHelper;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage;
import org.eclipse.papyrus.uml.nattable.properties.observables.MatrixCellContentsFilterObservableValue;
import org.eclipse.papyrus.uml.nattable.properties.observables.MatrixColumnRootFilterObservableValue;
import org.eclipse.papyrus.uml.nattable.properties.observables.MatrixColumnSourcesEMFObservableList;
import org.eclipse.papyrus.uml.nattable.properties.observables.MatrixDirectionObservableValue;
import org.eclipse.papyrus.uml.nattable.properties.observables.MatrixManagedElementTypeObservableValue;
import org.eclipse.papyrus.uml.nattable.properties.observables.MatrixRelationshipOwnerStrategyObservableValue;
import org.eclipse.papyrus.uml.nattable.properties.observables.MatrixRelationshipOwneryObservableValue;
import org.eclipse.papyrus.uml.nattable.properties.observables.MatrixRowRootFilterObservableValue;
import org.eclipse.papyrus.uml.nattable.properties.observables.MatrixRowSourcesEMFObservableList;
import org.eclipse.papyrus.uml.nattable.properties.providers.GenericRelationshipMatrixElementTypeContentProvider;
import org.eclipse.papyrus.uml.nattable.properties.providers.GenericRelationshipMatrixElementTypeLabelProvider;
import org.eclipse.papyrus.uml.nattable.properties.utils.MatrixPropertyConstants;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Observable used for Generic UML Relationship Matrix
 *
 */
public class GenericUMLRelationshipMatrixModelElement extends EMFModelElement {

	/**
	 * the current table manager of the open editor
	 */
	private IMatrixTableWidgetManager tableModelManager;

	/**
	 * The adapter used to listen all changes in interesting objects
	 */
	private Adapter changeListener;

	/**
	 * the list of the estructural feature to listen (it is the feature shown in the Matrix tab)
	 */
	private List<EStructuralFeature> interestingFeatures;

	/**
	 * the list of the listen object
	 */
	private List<EObject> listenEObjects;

	/**
	 * Constructor.
	 *
	 * @param source
	 * @param domain
	 */
	public GenericUMLRelationshipMatrixModelElement(final IMatrixTableWidgetManager tableManager, final TransactionalEditingDomain domain) {
		super(tableManager.getTable(), domain);
		this.tableModelManager = tableManager;
		init();

	}

	/**
	 * init fields and listener
	 */
	protected void init() {
		// TODO : create observable inheriting from PapyrusObservableValue could help to remove these listeners, but not sure
		this.listenEObjects = new ArrayList<EObject>();
		this.interestingFeatures = new ArrayList<EStructuralFeature>();
		this.interestingFeatures.add(NattablePackage.eINSTANCE.getTable_LocalColumnHeaderAxisConfiguration());
		this.interestingFeatures.add(NattableaxisconfigurationPackage.eINSTANCE.getLocalTableHeaderAxisConfiguration_AxisManagerConfigurations());
		this.interestingFeatures.add(NattableaxisconfigurationPackage.eINSTANCE.getTreeFillingConfiguration_AxisUsedAsAxisProvider());
		this.interestingFeatures.add(NattableaxisconfigurationPackage.eINSTANCE.getTreeFillingConfiguration_FilterRule());
		this.interestingFeatures.add(NattableaxisproviderPackage.eINSTANCE.getIMasterAxisProvider_Sources());
		this.interestingFeatures.add(NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_CellContentsFilter());
		this.interestingFeatures.add(NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_Direction());
		this.interestingFeatures.add(NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_EditedElement());
		this.interestingFeatures.add(NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwner());
		this.interestingFeatures.add(NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerStrategy());

		this.changeListener = new AdapterImpl() {

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
		cleanAndReinitListenObjects();
	}

	/**
	 * This method removes the registered listener on all objects referenced by {@link #listenEObjects},
	 * then the listener are reapplied on all interesting objects
	 * 
	 */
	private void cleanAndReinitListenObjects() {
		removeListener();

		final Table table = getEditedTable();
		if (null == table) {
			return;
		}
		registerListener(table);
		final LocalTableHeaderAxisConfiguration columnHeaderAxisConfiguration = table.getLocalColumnHeaderAxisConfiguration();

		// COLUMNS MANAGEMENT
		// we add a listener on the columnHeaderAxisConfiguration
		if (columnHeaderAxisConfiguration instanceof LocalTableHeaderAxisConfiguration) {
			registerListener(columnHeaderAxisConfiguration);

			// the best way will be to listen the TreeFillingConfiguration of the AxisManagerConfigruation, but it is useless, because the referenced TreeFillingConfiguration are also accessible
			// from the owned axis configuration
			for (final IAxisConfiguration current : columnHeaderAxisConfiguration.getOwnedAxisConfigurations()) {
				if (current instanceof TreeFillingConfiguration && ((TreeFillingConfiguration) current).getDepth() == 1) {
					final TreeFillingConfiguration treeFillingConfiguration = (TreeFillingConfiguration) current;
					registerListener(treeFillingConfiguration);

					IAxis provider = treeFillingConfiguration.getAxisUsedAsAxisProvider();
					registerListener(provider);
					final IBooleanEObjectExpression filterRule = treeFillingConfiguration.getFilterRule();
					registerListener(filterRule);
				}
			}
		}
		final LocalTableHeaderAxisConfiguration rowHeaderAxisConfiguration = table.getLocalRowHeaderAxisConfiguration();

		if (rowHeaderAxisConfiguration instanceof LocalTableHeaderAxisConfiguration) {
			registerListener(rowHeaderAxisConfiguration);

			// the best way will be to listen the TreeFillingConfiguration of the AxisManagerConfigruation, but it is useless, because the referenced TreeFillingConfiguration are also accessible
			// from the owned axis configuration
			for (final IAxisConfiguration current : rowHeaderAxisConfiguration.getOwnedAxisConfigurations()) {
				if (current instanceof TreeFillingConfiguration && ((TreeFillingConfiguration) current).getDepth() == 1) {
					final TreeFillingConfiguration treeFillingConfiguration = (TreeFillingConfiguration) current;
					registerListener(treeFillingConfiguration);

					IAxis provider = treeFillingConfiguration.getAxisUsedAsAxisProvider();
					registerListener(provider);
					final IBooleanEObjectExpression filterRule = treeFillingConfiguration.getFilterRule();
					registerListener(filterRule);
				}
			}
		}


		final ICellEditorConfiguration cellEditorConfiguration = table.getOwnedCellEditorConfigurations();
		if (null != cellEditorConfiguration) {
			registerListener(cellEditorConfiguration);
			if (cellEditorConfiguration instanceof GenericRelationshipMatrixCellEditorConfiguration) {
				final GenericRelationshipMatrixCellEditorConfiguration tmp = (GenericRelationshipMatrixCellEditorConfiguration) cellEditorConfiguration;
				final IBooleanEObjectExpression filter = tmp.getCellContentsFilter();
				registerListener(filter);
			}
		}

		final AbstractAxisProvider axisProvider = getEditedTable().getCurrentColumnAxisProvider();
		if (axisProvider instanceof IMasterAxisProvider) {
			registerListener(axisProvider);
		}

		final AbstractAxisProvider axisProvider2 = getEditedTable().getCurrentRowAxisProvider();
		if (axisProvider2 instanceof IMasterAxisProvider) {
			registerListener(axisProvider2);
		}
	}

	/**
	 * This method add the eobject parameter in {@link #listenEObjects} and register the listener {@link #changeListener}
	 * 
	 * @param eobject
	 *            the eobject to listen
	 */
	private void registerListener(final EObject eobject) {
		if (null != eobject && null != this.changeListener) {
			eobject.eAdapters().add(this.changeListener);
			this.listenEObjects.add(eobject);
		}
	}


	/**
	 * This method clear the list {@link #listenEObjects} and remove the listener {@link #changeListener} for each of them
	 */
	private void removeListener() {
		for (final EObject current : this.listenEObjects) {
			current.eAdapters().remove(this.changeListener);
		}
		this.listenEObjects.clear();
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement#dispose()
	 *
	 */
	@Override
	public void dispose() {
		removeListener();
		this.tableModelManager = null;
		super.dispose();
		this.changeListener = null;
	}

	/**
	 * 
	 * @return the edited table
	 */
	private Table getEditedTable() {
		return tableModelManager.getTable();
	}

	/**
	 * 
	 * @param tableModelManager
	 *            this method changes the listen table
	 */
	public void updateTableWidgetMatrixManager(final IMatrixTableWidgetManager tableModelManager) {
		this.tableModelManager = tableModelManager;
		updateValues();
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
				cleanAndReinitListenObjects();
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

	@Override
	protected IObservable doGetObservable(String propertyPath) {
		IObservable value = null;
		if (MatrixPropertyConstants.MATRIX_ROW_SOURCES.equals(propertyPath)) {
			value = new MatrixRowSourcesEMFObservableList(getDomain(), getEditedTable(), this.tableModelManager);
		} else if (MatrixPropertyConstants.MATRIX_COLUMN_SOURCES.equals(propertyPath)) {
			value = new MatrixColumnSourcesEMFObservableList(getDomain(), getEditedTable(), this.tableModelManager);
		} else if (MatrixPropertyConstants.MATRIX_ROW_FILTER.equals(propertyPath)) {
			value = new MatrixRowRootFilterObservableValue(getEditedTable());
		} else if (MatrixPropertyConstants.MATRIX_COLUMN_FILTER.equals(propertyPath)) {
			value = new MatrixColumnRootFilterObservableValue(getEditedTable());
		} else if (MatrixPropertyConstants.MATRIX_CELL_TYPE.equals(propertyPath)) {
			value = new MatrixManagedElementTypeObservableValue(getEditedTable());
		} else if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_DIRECTION.equals(propertyPath)) {
			value = new MatrixDirectionObservableValue(getEditedTable());
		} else if (MatrixPropertyConstants.MATRIX_CELL_FILTER.equals(propertyPath)) {
			value = new MatrixCellContentsFilterObservableValue(getEditedTable());
		} else if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_OWNER_STRATEGY.equals(propertyPath)) {
			value = new MatrixRelationshipOwnerStrategyObservableValue(getEditedTable());
		} else if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_OWNER.equals(propertyPath)) {
			value = new MatrixRelationshipOwneryObservableValue(getEditedTable());
		} else {
			value = super.doGetObservable(propertyPath);
		}
		return value;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getContentProvider(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public IStaticContentProvider getContentProvider(String propertyPath) {
		IStaticContentProvider provider = null;
		
		if (MatrixPropertyConstants.MATRIX_ROW_SOURCES.equals(propertyPath)) {	
			ResourceSet resourceSet= getRoot(getEditedTable().getContext()).eResource().getResourceSet();
			provider  = ProviderHelper.encapsulateProvider(ProviderHelper.getContentProvider(resourceSet), resourceSet, "rowSources");
		} else if (MatrixPropertyConstants.MATRIX_COLUMN_SOURCES.equals(propertyPath)) {
			ResourceSet resourceSet= getRoot(getEditedTable().getContext()).eResource().getResourceSet();
			provider  = ProviderHelper.encapsulateProvider(ProviderHelper.getContentProvider(resourceSet), resourceSet, "columnSources");
		} else if (MatrixPropertyConstants.MATRIX_CELL_TYPE.equals(propertyPath)) {
			provider = new GenericRelationshipMatrixElementTypeContentProvider(getRoot(getEditedTable().getContext()));
		} else if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_DIRECTION.equals(propertyPath)) {
			provider = new MatrixDirectionContentProvider();
		} else if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_OWNER_STRATEGY.equals(propertyPath)) {
			provider = new MatrixRelationshipOwnerStrategyContentProvider();
		} else if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_OWNER.equals(propertyPath)) {
			ResourceSet resourceSet= getRoot(getEditedTable().getContext()).eResource().getResourceSet();
			provider = ProviderHelper.encapsulateProvider(ProviderHelper.getContentProvider(resourceSet), resourceSet, "relationshipOwner");
		} else {
			provider = super.getContentProvider(propertyPath);
		}
		return provider;
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getFeature(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public EStructuralFeature getFeature(final String propertyPath) {
		if (MatrixPropertyConstants.MATRIX_ROW_FILTER.equals(propertyPath) || MatrixPropertyConstants.MATRIX_COLUMN_FILTER.equals(propertyPath)) {
			return NattableaxisconfigurationPackage.eINSTANCE.getTreeFillingConfiguration_FilterRule();
		} else if (MatrixPropertyConstants.MATRIX_ROW_SOURCES.equals(propertyPath) || MatrixPropertyConstants.MATRIX_COLUMN_SOURCES.equals(propertyPath)) {
			return NattableaxisproviderPackage.eINSTANCE.getIMasterAxisProvider_Sources();
		} else if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_DIRECTION.equals(propertyPath)) {
			return NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_Direction();
		} else if (MatrixPropertyConstants.MATRIX_CELL_TYPE.equals(propertyPath)) {
			return NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_EditedElement();
		} else if (MatrixPropertyConstants.MATRIX_CELL_FILTER.equals(propertyPath)) {
			return NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_CellContentsFilter();
		} else if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_OWNER_STRATEGY.equals(propertyPath)) {
			return NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerStrategy();
		} else if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_OWNER.equals(propertyPath)) {
			return NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwner();
		}
		return super.getFeature(propertyPath);
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

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getLabelProvider(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public ILabelProvider getLabelProvider(String propertyPath) {
		ILabelProvider provider = super.getLabelProvider(propertyPath);
		if (MatrixPropertyConstants.MATRIX_COLUMN_SOURCES.equals(propertyPath) 
				|| MatrixPropertyConstants.MATRIX_ROW_SOURCES.equals(propertyPath)
				|| MatrixPropertyConstants.MATRIX_RELATIONSHIP_OWNER.equals(propertyPath)) {
			provider = new WrappedLabelProvider(provider);
		}
		if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_DIRECTION.equals(propertyPath)) {
			provider = new MatrixRelationshipDirectionLabelProvider();
		}
		if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_OWNER_STRATEGY.equals(propertyPath)) {
			provider = new MatrixRelationshipOwnerStrategyLabelProvider();
		}
		if (MatrixPropertyConstants.MATRIX_CELL_TYPE.equals(propertyPath)) {
			provider = new GenericRelationshipMatrixElementTypeLabelProvider();
		}
		return provider;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#isOrdered(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public boolean isOrdered(String propertyPath) {
		if (MatrixPropertyConstants.MATRIX_ROW_SOURCES.equals(propertyPath)) {
			return false; // to avoid refresh problem in the table
		} else if (MatrixPropertyConstants.MATRIX_COLUMN_SOURCES.equals(propertyPath)) {
			return false; // to avoid refresh problem in the table
		} else if (MatrixPropertyConstants.MATRIX_CELL_FILTER.equals(propertyPath)) {
			return false; // to avoid refresh problem in the table
		}
		return super.isOrdered(propertyPath);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getDirectCreation(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public boolean getDirectCreation(String propertyPath) {
		if (MatrixPropertyConstants.MATRIX_ROW_SOURCES.equals(propertyPath) || MatrixPropertyConstants.MATRIX_COLUMN_SOURCES.equals(propertyPath)) {
			return false;// it is a containment feature, but we need to open a dialog to wrapper the selected element
		}
		if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_OWNER.equals(propertyPath)) {
			return false;// it is a containment feature, but we need to open a dialog to wrapper the selected element
		}
		return super.getDirectCreation(propertyPath);
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getValueFactory(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public ReferenceValueFactory getValueFactory(String propertyPath) {
		if (MatrixPropertyConstants.MATRIX_ROW_FILTER.equals(propertyPath) || MatrixPropertyConstants.MATRIX_COLUMN_FILTER.equals(propertyPath)) {
			if (this.domain instanceof AdapterFactoryEditingDomain) {
				BooleanEObjectExpressionPropertyEditorFactory factory = new BooleanEObjectExpressionPropertyEditorFactory((AdapterFactoryEditingDomain) domain, NattableaxisconfigurationPackage.eINSTANCE.getTreeFillingConfiguration_FilterRule());
				factory.setNsUri(UMLExpressionsPackage.eNS_URI);
				return factory;
			}
		} else if (MatrixPropertyConstants.MATRIX_CELL_FILTER.equals(propertyPath)) {
			if (this.domain instanceof AdapterFactoryEditingDomain) {
				BooleanEObjectExpressionPropertyEditorFactory factory = new BooleanEObjectExpressionPropertyEditorFactory((AdapterFactoryEditingDomain) domain,
						NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_CellContentsFilter());
				factory.setNsUri(UMLExpressionsPackage.eNS_URI);
				return factory;
			}
		} else if (MatrixPropertyConstants.MATRIX_RELATIONSHIP_OWNER.equals(propertyPath)) {
			return null;// to remove the green plus
		}
		return super.getValueFactory(propertyPath);
	}

	/**
	 * 
	 * private label provider used to display label for TreeFillingConfiguration#sources
	 * This labelprovider wrap the super labelprovider
	 */
	private class WrappedLabelProvider implements ILabelProvider {

		private ILabelProvider wrappedLabelProvider;

		public WrappedLabelProvider(final ILabelProvider provider) {
			this.wrappedLabelProvider = provider;
		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
		 *
		 * @param listener
		 */
		@Override
		public void addListener(ILabelProviderListener listener) {

		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
		 *
		 */
		@Override
		public void dispose() {

		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
		 *
		 * @param element
		 * @param property
		 * @return
		 */
		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
		 *
		 * @param listener
		 */
		@Override
		public void removeListener(ILabelProviderListener listener) {

		}

		/**
		 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
		 *
		 * @param element
		 * @return
		 */
		@Override
		public Image getImage(Object element) {
			if (element instanceof EObjectWrapper) {
				return this.wrappedLabelProvider.getImage(((EObjectWrapper) element).getElement());
			}
			return this.wrappedLabelProvider.getImage(element);
		}

		/**
		 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
		 *
		 * @param element
		 * @return
		 */
		@Override
		public String getText(Object element) {
			if (element instanceof EObjectWrapper) {
				return this.wrappedLabelProvider.getText(((EObjectWrapper) element).getElement());
			}
			return this.wrappedLabelProvider.getText(element);
		}
	}
}
