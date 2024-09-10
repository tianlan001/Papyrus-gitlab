/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.jdt.internal.ui.util.TableLayoutComposite;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.papyrus.uml.export.Activator;
import org.eclipse.papyrus.uml.export.extension.HTMLExtensionRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * The Class HTMLExportPreferencesPage.
 */
public class HTMLExportPreferencesPage extends PreferencePage implements IWorkbenchPreferencePage{


	/** The Constant ACTIVE_STRATEGIES. */
	public static final String ACTIVE_STRATEGIES = "active.strategy";//$NON-NLS-1$
	
	/** The Constant INACTIVE_STRATEGIES. */
	public static final String INACTIVE_STRATEGIES = "inactive.strategy";//$NON-NLS-1$
	
	/** The filter viewer label. */
	private Label fFilterViewerLabel;
	
	/** The filter table. */
	private Table fFilterTable;
	
	/** The filter viewer. */
	private CheckboxTableViewer fFilterViewer;
	
	/** The table editor. */
	private TableEditor fTableEditor;
	
	/** The stack filter content provider. */
	private IdentifiedEntryStructuredContentProvider fStackFilterContentProvider;

	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 *
	 * @param workbench
	 */
	@Override
	public void init(IWorkbench workbench) {}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 * @return
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite composite= new Composite(parent, SWT.NULL);
		GridLayout layout= new GridLayout();
		layout.numColumns= 1;
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		composite.setLayout(layout);
		GridData data= new GridData();
		data.verticalAlignment= GridData.FILL;
		data.horizontalAlignment= GridData.FILL;
		composite.setLayoutData(data);

		createStackFilterPreferences(composite);
		Dialog.applyDialogFont(composite);
		return composite;
	}

	/**
	 * Creates the stack filter preferences.
	 *
	 * @param composite the composite
	 */
	/*
	 * Create a group to contain the step filter related widgets
	 */
	private void createStackFilterPreferences(Composite composite) {
		fFilterViewerLabel= new Label(composite, SWT.SINGLE | SWT.LEFT);
		fFilterViewerLabel.setText("Available strategies to enhance default generation");

		Composite container= new Composite(composite, SWT.NONE);
		GridLayout layout= new GridLayout();
		layout.numColumns= 2;
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		container.setLayout(layout);
		GridData gd= new GridData(GridData.FILL_BOTH);
		container.setLayoutData(gd);

		createFilterTable(container);
	}
	
	/**
	 * Creates the filter table.
	 *
	 * @param container the container
	 */
	private void createFilterTable(Composite container) {
		TableLayoutComposite layouter= new TableLayoutComposite(container, SWT.NONE);
		layouter.addColumnData(new ColumnWeightData(100));
		layouter.setLayoutData(new GridData(GridData.FILL_BOTH));

		fFilterTable= new Table(layouter, SWT.CHECK | SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);

		@SuppressWarnings("unused")
		TableColumn tableColumn= new TableColumn(fFilterTable, SWT.NONE);
		fFilterViewer= new CheckboxTableViewer(fFilterTable);
		fStackFilterContentProvider= new IdentifiedEntryStructuredContentProvider();		

		
		fFilterViewer.setLabelProvider(new MyFilterLabelProvider());
		fFilterViewer.setComparator(new MyFilterViewerSorter());

		fFilterViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				IdentifiedEntry filter= (IdentifiedEntry) event.getElement();
				fStackFilterContentProvider.toggleFilter(filter);
			}
		});		
		
		fFilterViewer.setContentProvider(fStackFilterContentProvider);
		fFilterViewer.setInput(this);		// input just needs to be non-null
		
		fStackFilterContentProvider.refresh();
	}
	
	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 *
	 * @return
	 */
	@Override
	public boolean performOk() {
		fStackFilterContentProvider.saveFilters();
		return true;
	}

	/**
	 * Content provider for the filter table.  Content consists of instances of IdentifiedEntry.
	 */
	private class IdentifiedEntryStructuredContentProvider implements IStructuredContentProvider {

		/** The filters. */
		private List<IdentifiedEntry> fFilters;

		/**
		 * Instantiates a new identified entry structured content provider.
		 */
		public IdentifiedEntryStructuredContentProvider() {
			Map<String,String> active= new HashMap<>();
			Map<String,String> inactive= new HashMap<>();
			
			List<String> activeStrategies = parseList(Activator.getDefault().getPreferenceStore().getString(ACTIVE_STRATEGIES));
			List<String> inactiveStrategies = parseList(Activator.getDefault().getPreferenceStore().getString(INACTIVE_STRATEGIES));

			List<IExtension> allExtension = HTMLExtensionRegistry.getInstance().getAllExtension();
			
			for (IExtension iExtension : allExtension) {
				String uniqueIdentifier = iExtension.getUniqueIdentifier();
				if (inactiveStrategies.contains(uniqueIdentifier)) {
					inactive.put(uniqueIdentifier, iExtension.getLabel());
				} else if (activeStrategies.contains(uniqueIdentifier)) {
					active.put(uniqueIdentifier, iExtension.getLabel());
				} else {
					active.put(uniqueIdentifier, iExtension.getLabel()); // active by default
				}
				
			}	
			

			populateFilters(active, inactive);
		}

		/**
		 * Sets the defaults.
		 */
		public void setDefaults() {
			fFilterViewer.remove(fFilters.toArray());
			Map<String,String> active= new HashMap<>();
			Map<String,String> inactive= new HashMap<>();
			populateFilters(active, inactive);
		}

		/**
		 * Populate filters.
		 *
		 * @param activeList the active list
		 * @param inactiveList the inactive list
		 */
		protected void populateFilters(Map<String,String> activeList, Map<String,String> inactiveList) {
			fFilters= new ArrayList<>(activeList.size() + inactiveList.size());
			populateList(activeList, true);
			if (!inactiveList.isEmpty())
				populateList(inactiveList, false);
		}

		/**
		 * Populate list.
		 *
		 * @param list the list
		 * @param checked the checked
		 */
		protected void populateList(Map<String,String> list, boolean checked) {
			for (Entry<String,String> entry: list.entrySet()) {
				addFilter(entry.getKey(),entry.getValue(), checked);
			}			
		}

		/**
		 * Adds the filter.
		 *
		 * @param id the id
		 * @param name the name
		 * @param checked the checked
		 * @return the identified entry
		 */
		public IdentifiedEntry addFilter(String id, String name, boolean checked) {
			IdentifiedEntry filter= new IdentifiedEntry(id, name, checked);
			if (!fFilters.contains(filter)) {
				fFilters.add(filter);
				fFilterViewer.add(filter);
				fFilterViewer.setChecked(filter, checked);
			}
			return filter;
		}

		/**
		 * Save filters.
		 */
		public void saveFilters() {
			List<String> active= new ArrayList<>(fFilters.size());
			List<String> inactive= new ArrayList<>(fFilters.size());
			Iterator<IdentifiedEntry> iterator= fFilters.iterator();
			while (iterator.hasNext()) {
				IdentifiedEntry filter= iterator.next();
				String id= filter.getId();
				if (filter.isChecked())
					active.add(id);
				else
					inactive.add(id);
			}
			Activator.getDefault().getPreferenceStore().setValue(ACTIVE_STRATEGIES,serializeList(active.toArray(new String[active.size()])));
			Activator.getDefault().getPreferenceStore().setValue(INACTIVE_STRATEGIES,serializeList(inactive.toArray(new String[inactive.size()])));
		}

		/**
		 * Serializes the array of strings into one comma-separated string.
		 *
		 * @param list array of strings
		 * @return a single string composed of the given list
		 */
		public String serializeList(String[] list) {
			if (list == null)
				return ""; //$NON-NLS-1$

			StringBuffer buffer= new StringBuffer();
			for (int i= 0; i < list.length; i++) {
				if (i > 0)
					buffer.append(',');

				buffer.append(list[i]);
			}
			return buffer.toString();
		}		
	
		/**
		 * Parses the comma-separated string into an array of strings.
		 * 
		 * @param listString a comma-separated string
		 * @return an array of strings
		 */
		public List<String> parseList(String listString) {
			List<String> list= new ArrayList<>(10);
			StringTokenizer tokenizer= new StringTokenizer(listString, ","); //$NON-NLS-1$
			while (tokenizer.hasMoreTokens())
				list.add(tokenizer.nextToken());
			return list;
		}

		/**
		 * Toggle filter.
		 *
		 * @param filter the filter
		 */
		public void toggleFilter(IdentifiedEntry filter) {
			boolean newState= !filter.isChecked();
			filter.setChecked(newState);
			fFilterViewer.setChecked(filter, newState);
		}

		/**
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 *
		 * @param inputElement
		 * @return
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			return fFilters.toArray();
		}

		/**
		 * Refresh.
		 */
		public void refresh() {
			for (IdentifiedEntry filter2 : fFilters) {
				fFilterViewer.setChecked(filter2, filter2.isChecked());
			}
		}
		
		
		/**
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 *
		 * @param viewer
		 * @param oldInput
		 * @param newInput
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
		
		/**
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 *
		 */
		@Override
		public void dispose() {}

	}

	/**
	 * Model object that represents a single entry in the table.
	 */
	private static class IdentifiedEntry {

		/** The id. */
		private String id;
		
		/** The label. */
		private String label;
		
		/** The checked. */
		private boolean checked;

		/**
		 * Instantiates a new identified entry.
		 *
		 * @param id the id
		 * @param label the label
		 * @param checked the checked
		 */
		public IdentifiedEntry(String id, String label, boolean checked) {
			this.id = id;
			this.label = label;
			setChecked(checked);
		}

		/**
		 * Gets the label.
		 *
		 * @return the label
		 */
		public String getLabel() {
			return label;
		}
		
		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		
		/**
		 * Checks if is checked.
		 *
		 * @return true, if is checked
		 */
		public boolean isChecked() {
			return checked;
		}

		/**
		 * Sets the checked.
		 *
		 * @param checked the new checked
		 */
		public void setChecked(boolean checked) {
			this.checked= checked;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 *
		 * @param o
		 * @return
		 */
		@Override
		public boolean equals(Object o) {
			if (!(o instanceof IdentifiedEntry))
				return false;

			IdentifiedEntry other= (IdentifiedEntry) o;
			return (getLabel().equals(other.getLabel()));
		}

		/**
		 * @see java.lang.Object#hashCode()
		 *
		 * @return
		 */
		@Override
		public int hashCode() {
			return id.hashCode();
		}
	}

	/**
	 * Sorter for the filter table; sorts alphabetically ascending.
	 */
	private static class MyFilterViewerSorter extends ViewerComparator {
		
		/**
		 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 *
		 * @param viewer
		 * @param e1
		 * @param e2
		 * @return
		 */
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			ILabelProvider lprov= (ILabelProvider) ((ContentViewer) viewer).getLabelProvider();
			String name1= lprov.getText(e1);
			String name2= lprov.getText(e2);
			if (name1 == null)
				name1= ""; //$NON-NLS-1$

			if (name2 == null)
				name2= ""; //$NON-NLS-1$

			if (name1.length() > 0 && name2.length() > 0) {
				boolean isPackage1= name1.indexOf('*') != -1;
				boolean isPackage2= name2.indexOf('*') != -1;
				if (isPackage1 && !isPackage2)
					return -1;

				if (isPackage2 && !isPackage1)
					return 1;
			}
			return getComparator().compare(name1, name2);
		}
	}
	
	/**
	 * Label provider for Filter model objects.
	 */
	private static class MyFilterLabelProvider extends LabelProvider implements ITableLabelProvider {

		/**
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
		 *
		 * @param object
		 * @param column
		 * @return
		 */
		@Override
		public String getColumnText(Object object, int column) {
			return (column == 0) ? getText(object) : ""; //$NON-NLS-1$
		}

		/**
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 *
		 * @param element
		 * @return
		 */
		@Override
		public String getText(Object element) {
			return TextProcessor.process(((IdentifiedEntry) element).getLabel());
		}

		/**
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
		 *
		 * @param object
		 * @param column
		 * @return
		 */
		@Override
		public Image getColumnImage(Object object, int column) {

			//other filter
			return null;
		}
	}
}
