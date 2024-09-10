/*****************************************************************************
 * Copyright (c) 2009, 2017, 2018 CEA LIST, LIFL, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr
 *  Christian W. Damus (CEA) - bug 430880
 *  Christian W. Damus (CEA) - bug 437217
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 459220
 *  Vincent Lorenzo (CEA) - bug 536300
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.editor;


import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.nattable.selection.EObjectSelectionExtractor;
import org.eclipse.papyrus.infra.internationalization.common.editor.IInternationalizationEditor;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationUtils;
import org.eclipse.papyrus.infra.nattable.common.Activator;
import org.eclipse.papyrus.infra.nattable.common.helper.TableReconcileHelper;
import org.eclipse.papyrus.infra.nattable.common.reconciler.TableVersioningUtils;
import org.eclipse.papyrus.infra.nattable.common.utils.TableEditorInput;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.NattableconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.utils.NattableModelManagerFactory;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.infra.ui.editor.reload.IReloadContextProvider;
import org.eclipse.papyrus.infra.widgets.util.NavigationTarget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;


/**
 * Abstract class for TableEditor.
 */
public abstract class AbstractEMFNattableEditor extends EditorPart implements NavigationTarget, IInternationalizationEditor {

	/** the service registry */
	protected ServicesRegistry servicesRegistry;

	/**
	 * the table manager
	 */
	protected INattableModelManager tableManager;

	/**
	 * the part name synchronizer
	 */
	private PartNameSynchronizer synchronizer;

	/**
	 * the workspace preference store for this table editor
	 */
	@SuppressWarnings("unused")
	// not yet used in the table
	private PreferenceStore workspacePreferenceStore;

	/**
	 * the table we are loading in the editor
	 */
	private Table table;

	/**
	 * @param servicesRegistry
	 * @param rawModel
	 *
	 */
	public AbstractEMFNattableEditor(final ServicesRegistry servicesRegistry, final Table rawModel) {
		this.servicesRegistry = servicesRegistry;
		this.table = rawModel;
		if (false == isTableConfigurationProxy()) {
			this.tableManager = NattableModelManagerFactory.INSTANCE.createNatTableModelManager(rawModel, new EObjectSelectionExtractor());
			this.synchronizer = new PartNameSynchronizer(rawModel);
			this.workspacePreferenceStore = getWorkspaceViewerPreferenceStore();

			// Need to manage the part label synchronizer for the table labels
			LabelInternationalizationUtils.managePartLabelSynchronizer(rawModel, this);
		}
	}

	/**
	 * Returns the workspace viewer <code>PreferenceStore</code>
	 *
	 * @return the workspace viewer <code>PreferenceStore</code>
	 */
	public PreferenceStore getWorkspaceViewerPreferenceStore() {
		// not yet used by tables
		return null;

		// if(this.workspacePreferenceStore != null) {
		// return this.workspacePreferenceStore;
		// } else {
		// // Try to load it
		// IPath path = Activator.getDefault().getStateLocation();
		// String id = getIdStr(this.tableManager.getTable());
		//
		// String fileName = path.toString() + "/" + id;//$NON-NLS-1$
		// java.io.File file = new File(fileName);
		// this.workspacePreferenceStore = new PreferenceStore(fileName);
		// if(file.exists()) {
		// // Load it
		// try {
		// this.workspacePreferenceStore.load();
		// } catch (Exception e) {
		// // Create the default
		// addDefaultPreferences();
		// }
		// } else {
		// // Create it
		// addDefaultPreferences();
		// }
		// return this.workspacePreferenceStore;
		// }
	}

	// /**
	// * returns the unique GUID of the view
	// *
	// * @param view
	// * the view
	// * @return String the GUID of a view (constant)
	// */
	// private static String getIdStr(Table table) {
	// Resource resource = table.eResource();
	// if(resource instanceof XMLResource) {
	// String id = ((XMLResource)resource).getID(table);
	// if(id != null) {
	// return id;
	// }
	// }
	//
	// // Remain compatible with previous behavior.
	// return StringStatics.BLANK;
	// }

	// not used : add a workspace preference to the table
	// private void addDefaultPreferences() {
	// final IPreferenceStore globalPreferenceStore = Activator.getDefault().getPreferenceStore();
	// String tableKind = this.tableManager.getTable().getTableConfiguration().getType();
	// }

	/**
	 *
	 * @see org.eclipse.emf.facet.widgets.nattable.workbench.editor.NatTableEditor#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 *
	 * @param site
	 * @param input
	 * @throws PartInitException
	 */
	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		final TableEditorInput tableEditorInput = new TableEditorInput(this.table, getEditingDomain());
		setSite(site);
		setInput(tableEditorInput);
		setPartName(LabelInternationalization.getInstance().getTableLabel(this.table));
	}

	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		if (getTable() != null && !TableVersioningUtils.isOfCurrentPapyrusVersion(getTable())) {
			try {
				new TableReconcileHelper(getEditingDomain()).reconcileTable(getTable());
			} catch (CoreException e) {
				Activator.log.error(e);
			}
		}
	}

	/**
	 *
	 * @see org.eclipse.emf.facet.widgets.nattable.workbench.editor.NatTableEditor#getEditingDomain()
	 *
	 * @return
	 */
	public TransactionalEditingDomain getEditingDomain() {
		try {
			return ServiceUtils.getInstance().getTransactionalEditingDomain(this.servicesRegistry);
		} catch (final ServiceException e) {
			Activator.log.error(e);
		}
		return null;
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// nothing to do
	}

	@Override
	public void doSaveAs() {
		// nothing to do

	}

	@Override
	public boolean isDirty() {
		// nothing to do
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// nothing to do
		return false;
	}

	@Override
	public void setFocus() {
		// nothing to do
	}


	@Override
	public void createPartControl(final Composite parent) {
		if (isTableConfigurationProxy()) {
			final String errorMessage = NLS.bind("The table configuration file {0} can't be resolved in your current Papyrus installation. Please, check all required plugins are installed.",
					((MinimalEObjectImpl) this.table.getTableConfiguration()).eProxyURI());
			new StatusPart(parent, new Status(IStatus.ERROR, Activator.PLUGIN_ID, errorMessage));
		} else {
			this.tableManager.createNattable(parent, SWT.NONE, getSite());
		}
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		if (adapter == INattableModelManager.class) {
			return this.tableManager;
		}
		if (adapter == IMatrixTableWidgetManager.class) {
			if (this.tableManager instanceof IMatrixTableWidgetManager) {
				return this.tableManager;
			}
			return null;
		}

		// Give direct access to the Table model element
		// Most actions should be done through the TableManager.
		// Be careful when using directly the Table element.
		if (adapter == Table.class) {
			if (tableManager != null) {
				return this.tableManager.getTable();
			}
		}

		if (adapter == IReloadContextProvider.class) {
			return new NattableReloadContextProvider(this);
		}

		return super.getAdapter(adapter);
	}

	/**
	 * this method is used dispose the existing nattable widget and recreate a new one.
	 * It has been created to be able to reload a table when a bug broke the table after a user action.
	 *
	 * see bug 466447: [TreeTable] Missing method to reload a (hierarchic) table
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=466447
	 */
	public void reloadNattableModelManager() {
		if (null != this.tableManager) {
			if (TableHelper.isTreeTable(tableManager)) {
				reloadTreeNattableModelManager();
			} else {
				Table rawModel = this.tableManager.getTable();
				// we dispose the previous nattable widget
				NatTable nattable = this.tableManager.getAdapter(NatTable.class);
				Composite parent = nattable.getParent();
				this.tableManager.dispose();
				this.tableManager = NattableModelManagerFactory.INSTANCE.createNatTableModelManager(rawModel, new EObjectSelectionExtractor());
				nattable = this.tableManager.createNattable(parent, SWT.NONE, getSite());
				nattable.getParent().layout();
			}
		}
	}

	/**
	 * the reload method for Treetable (due to bug Bug 488234)
	 */
	protected void reloadTreeNattableModelManager() {
		Table rawModel = this.tableManager.getTable();
		// we dispose the previous nattable widget
		NatTable nattable = this.tableManager.getAdapter(NatTable.class);
		Composite sliderComposite = nattable.getParent();
		Composite greatParent = sliderComposite.getParent();
		this.tableManager.dispose();
		sliderComposite.dispose();

		this.tableManager = NattableModelManagerFactory.INSTANCE.createNatTableModelManager(rawModel, new EObjectSelectionExtractor());
		nattable = this.tableManager.createNattable(greatParent, SWT.NONE, getSite());
		greatParent.layout();
	}

	@Override
	public void dispose() {
		saveLocalPreferenceStoreValues();
		if (null != this.tableManager) { // null when the table configuration is a proxy
			this.tableManager.dispose();
		}
		if (null != this.synchronizer) {// null when the table configuration is a proxy
			this.synchronizer.dispose();
		}
		this.tableManager = null;
		this.servicesRegistry = null;
		this.synchronizer = null;
		super.dispose();
	}

	protected void saveLocalPreferenceStoreValues() {
		// Write the settings, if necessary
		try {
			if (getWorkspaceViewerPreferenceStore() != null && getWorkspaceViewerPreferenceStore().needsSaving()) {
				getWorkspaceViewerPreferenceStore().save();
			}
		} catch (IOException ioe) {
			Activator.log.warn("Preferences can' be saved"); //$NON-NLS-1$
		}
	}

	/**
	 * A class taking in charge the synchronization of the partName and the table name.
	 * When table name change, the other is automatically updated.
	 *
	 *
	 */
	public class PartNameSynchronizer {

		/** the papyrus table */
		private Table papyrusTable;

		/**
		 * Listener on diagram name change.
		 */
		private final Adapter tableNameListener = new AdapterImpl() {

			/**
			 *
			 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 *
			 * @param notification
			 */
			@Override
			public void notifyChanged(final Notification notification) {
				if (notification.getFeature() == NattableconfigurationPackage.eINSTANCE.getTableNamedElement_Name()) {
					setPartName(LabelInternationalization.getInstance().getTableLabel(PartNameSynchronizer.this.papyrusTable));
				}
			}
		};

		/**
		 *
		 * Constructor.
		 *
		 * @param diagram
		 */
		public PartNameSynchronizer(final Table papyrusTable) {
			setTable(papyrusTable);
		}

		public void dispose() {
			this.papyrusTable.eAdapters().remove(this.tableNameListener);
			this.papyrusTable = null;
		}

		/**
		 * Change the associated diagram.
		 *
		 * @param papyrusTable
		 */
		public void setTable(final Table papyrusTable) {
			// Remove from old table, if any
			if (this.papyrusTable != null) {
				papyrusTable.eAdapters().remove(this.tableNameListener);
			}
			// Set new table
			this.papyrusTable = papyrusTable;
			// Set editor name
			setPartName(LabelInternationalization.getInstance().getTableLabel(papyrusTable));
			// Listen to name change
			papyrusTable.eAdapters().add(this.tableNameListener);
		}
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#setPartName(java.lang.String)
	 *
	 * @param partName
	 */
	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}

	/**
	 *
	 * used to link the selection between the model explorer and the table
	 *
	 * @param element
	 */
	@Override
	public boolean revealElement(Object element) {
		return revealElement(Collections.singleton(element));
	}

	/**
	 *
	 * used to link the selection between the model explorer and the table
	 *
	 * @param elements
	 */
	@Override
	public boolean revealElement(Collection<?> elements) {
		if (tableManager instanceof NavigationTarget) {
			return ((NavigationTarget) tableManager).revealElement(elements);
		}
		return false;
	}

	/**
	 *
	 * @return
	 * 		the table model displayed by the editor
	 */
	public Table getTable() {
		return this.table;
	}

	/**
	 *
	 * @return
	 * 		the table editing domain
	 */
	public TransactionalEditingDomain getTableEditingDomain() {
		return TableEditingDomainUtils.getTableEditingDomain(getTable());
	}

	/**
	 *
	 * @return
	 * 		the table context editing domain
	 */
	public TransactionalEditingDomain getTableContextEditingDomain() {
		return TableEditingDomainUtils.getTableContextEditingDomain(getTable());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.internationalization.common.editor.IInternationalizationEditor#modifyPartName(java.lang.String)
	 */
	@Override
	public void modifyPartName(final String name) {
		setPartName(name);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.internationalization.common.editor.IInternationalizationEditor#refreshEditorPart()
	 */
	@Override
	public void refreshEditorPart() {
		// We don't need to refresh the editor part, the table is refreshed alone
	}

	/**
	 * 
	 * @return
	 * 		<code>true</code> if the table Configuration can't be resolved (is a proxy)
	 */
	private boolean isTableConfigurationProxy() {
		return this.table.getTableConfiguration().eIsProxy();
	}


	/**
	 * 
	 * Adapted from {@link StatusPart}
	 * 
	 * @author Vincent LORENZO
	 *
	 */
	private class StatusPart {

		private IStatus reason;

		public StatusPart(final Composite parent, IStatus reason_) {
			Color bgColor = parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
			Color fgColor = parent.getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND);

			parent.setBackground(bgColor);
			parent.setForeground(fgColor);

			this.reason = reason_;
			GridLayout layout = new GridLayout();

			layout.numColumns = 3;

			int spacing = 8;
			int margins = 8;
			layout.marginBottom = margins;
			layout.marginTop = margins;
			layout.marginLeft = margins;
			layout.marginRight = margins;
			layout.horizontalSpacing = spacing;
			layout.verticalSpacing = spacing;
			parent.setLayout(layout);

			Label imageLabel = new Label(parent, SWT.NONE);
			imageLabel.setBackground(bgColor);
			Image image = getImage();
			if (image != null) {
				image.setBackground(bgColor);
				imageLabel.setImage(image);
				GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_BEGINNING);
				imageLabel.setLayoutData(gridData);
			}

			Text text = new Text(parent, SWT.MULTI | SWT.READ_ONLY | SWT.WRAP);
			text.setBackground(bgColor);
			text.setForeground(fgColor);

			text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			text.setText(reason.getMessage());

			Composite buttonParent = new Composite(parent, SWT.NONE);
			buttonParent.setBackground(parent.getBackground());
			GridLayout buttonsLayout = new GridLayout();
			buttonsLayout.numColumns = 2;
			buttonsLayout.marginHeight = 0;
			buttonsLayout.marginWidth = 0;
			buttonsLayout.horizontalSpacing = 0;
			buttonParent.setLayout(buttonsLayout);

			parent.layout(true);
		}

		/**
		 * Return the image for the upper-left corner of this part
		 *
		 * @return the image
		 */
		private Image getImage() {
			Display d = Display.getCurrent();

			switch (reason.getSeverity()) {
			case IStatus.ERROR:
				return d.getSystemImage(SWT.ICON_ERROR);
			case IStatus.WARNING:
				return d.getSystemImage(SWT.ICON_WARNING);
			default:
				return d.getSystemImage(SWT.ICON_INFORMATION);
			}
		}
	}

}
