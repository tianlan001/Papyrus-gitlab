/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Thibault Le Ouay (Sherpa Engineering) t.leouay@sherpa-eng.com  - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.kind;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
//import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.uml.diagram.wizards.Activator;
import org.eclipse.papyrus.uml.diagram.wizards.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


public class RepresentationKindComposite extends Composite {

	private static final Image CHECKED = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("org.eclipse.papyrus.uml.diagram.wizards", "icons/checked.gif"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final Image UNCHECKED = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("org.eclipse.papyrus.uml.diagram.wizards", "icons/unchecked.gif"); //$NON-NLS-1$ //$NON-NLS-2$

	private Map<RepresentationKind, String> representationNames = new HashMap<RepresentationKind, String>();

	List<String> listNames = new ArrayList<String>();


	private Map<RepresentationKind, Integer> selectedRepresentations = new HashMap<RepresentationKind, Integer>();

	public TableViewer viewer;

	private GridData gridData;

	private ImageRegistry imageRegistry;


	public RepresentationKindComposite(Composite parent) {
		super(parent, SWT.NONE);
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createTableViewer(this);
		imageRegistry = new ImageRegistry();
	}

	public void createTableViewer(final Composite container) {

		viewer = new TableViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		viewer.getTable().setLayoutData(gridData);

		// viewer.setContentProvider(new ArrayContentProvider());
		final Table table = viewer.getTable();
		viewer.setContentProvider(new RepresentationKindContentProvider());
		table.setHeaderVisible(true);

		// The check column
		TableViewerColumn colCheckbox = createTableViewerColumn("", 20, viewer); //$NON-NLS-1$
		colCheckbox.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return ""; //$NON-NLS-1$
			}

			@Override
			public Image getImage(Object element) {
				if (selectedRepresentations.containsKey(element)) {
					return CHECKED;
				} else {
					return UNCHECKED;
				}
			}

		});
		colCheckbox.setEditingSupport(new EditingSupport(viewer) {

			private CheckboxCellEditor checkboxCellEditor;

			@Override
			protected void setValue(Object element, Object value) {
				if (checkboxCellEditor.getValue() == Boolean.TRUE) {
					selectedRepresentations.put((RepresentationKind) element, 1);
				} else {
					selectedRepresentations.remove(element);
				}
				viewer.update(element, null);
			}

			@Override
			protected Object getValue(Object element) {
				return selectedRepresentations.containsKey(element);
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				checkboxCellEditor = new CheckboxCellEditor(container, SWT.CHECK | SWT.READ_ONLY);
				return checkboxCellEditor;
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		// no need to enable the resize on the check box column
		colCheckbox.getColumn().setResizable(false);


		// The Diagram name column
		TableViewerColumn colDiagramKind = createTableViewerColumn(Messages.RepresentationKindComposite_0, 200, viewer);
		colDiagramKind.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof RepresentationKind) {
					return ((RepresentationKind) element).getName();
				}
				return ""; //$NON-NLS-1$
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof RepresentationKind) {
					return getIcon((RepresentationKind) element);
				}
				return null;
			}


		});


		// the name of the diagram the user can edit it to set his onw name
		TableViewerColumn colDiagramName = createTableViewerColumn(Messages.RepresentationKindComposite_2, 300, viewer);
		colDiagramName.setEditingSupport(new EditingSupport(viewer) {

			@Override
			protected void setValue(Object element, Object value) {

				representationNames.put((RepresentationKind) element, (String) value);
				viewer.update(element, null);

			}

			@Override
			protected Object getValue(Object element) {

				if (representationNames.containsKey(element)) {
					return representationNames.get(element);
				}
				return ""; //$NON-NLS-1$

			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(viewer.getTable());
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		colDiagramName.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {

				return representationNames.get(element);
			}
		});


		// the quantity of the diagram the user would like to create
		TableViewerColumn colDiagramQuantity = createTableViewerColumn(Messages.RepresentationKindComposite_3, 70, viewer);
		colDiagramQuantity.setEditingSupport(new EditingSupport(viewer) {

			@Override
			protected void setValue(Object element, Object value) {
				selectedRepresentations.put((RepresentationKind) element, Integer.parseInt((String) value));
				viewer.update(element, null);
			}

			@Override
			protected Object getValue(Object element) {
				if (selectedRepresentations.containsKey(element)) {
					return selectedRepresentations.get(element).toString();
				}
				return "0"; //$NON-NLS-1$
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				TextCellEditor textCellEditor = new TextCellEditor(viewer.getTable());
				textCellEditor.setValidator(new ICellEditorValidator() {

					@Override
					public String isValid(Object value) {
						if (!(value instanceof Integer)) {
							return null;
						}
						return value.toString();
					}
				});
				return textCellEditor;
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		colDiagramQuantity.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (selectedRepresentations.containsKey(element)) {
					return selectedRepresentations.get(element).toString();
				}
				// return "0"; //$NON-NLS-1$
				return ""; //$NON-NLS-1$
			}
		});


		// TODO - The description of the diagram not yet implemented, should be implemented will generating the diagram
		// TableViewerColumn colDiagramDescription = createTableViewerColumn("Description", 400, viewer);
		// colDiagramDescription.setLabelProvider(new ColumnLabelProvider() {
		//
		// @Override
		// public String getText(Object element) {
		// return null;
		// }
		//
		// });
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				// if(selectedConfigs.size() > 0) {
				// setPageComplete(true);
				// } else {
				// setPageComplete(false);
				// }
			}
		});

	}

	/**
	 * Constructor.
	 *
	 * @param title
	 * @param bound
	 * @param viewer
	 * @return
	 */
	private TableViewerColumn createTableViewerColumn(String title, int bound, TableViewer viewer) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}


	/**
	 * Creates the diagram kind label provider.
	 *
	 * @return the i base label provider
	 */
	protected IBaseLabelProvider createDiagramKindLabelProvider() {
		return new DiagramKindLabelProvider();
	}


	public void setInput(Object input) {
		viewer.setInput(input);

		// Resize the diagram table to minimize the space lost
		int tableItemHeight = viewer.getTable().getItemHeight();
		int tableItemCount = ((RepresentationKindContentProvider) viewer.getContentProvider()).getElements(input).length;
		gridData.minimumHeight = tableItemHeight * Math.max(3, Math.round((tableItemCount / 2)));
		gridData.heightHint = tableItemHeight * Math.max(3, Math.round((tableItemCount / 2)));

		// Notifies the shell that the layout needs to be resized
		((Composite) viewer.getControl()).layout(true, true);
	}

	public ArrayList<RepresentationKind> getCheckElement() {
		ArrayList<RepresentationKind> list = new ArrayList<RepresentationKind>();
		Set<Entry<RepresentationKind, Integer>> set = selectedRepresentations.entrySet();
		Iterator<Entry<RepresentationKind, Integer>> ite = set.iterator();
		while (ite.hasNext()) {
			Entry<RepresentationKind, Integer> me = ite.next();
			int nb = me.getValue();
			String tempname = representationNames.get(me.getKey());
			// If the name field was not filled
			if (tempname == null || tempname.equalsIgnoreCase("")) {
				tempname = me.getKey().getName();
			}

			for (int i = 0; i < nb; i++) {
				list.add(me.getKey());
				if (nb > 1) {
					listNames.add(tempname + "_" + i); //$NON-NLS-1$
				} else {
					listNames.add(tempname);
				}
			}
		}
		return list;
	}


	/**
	 * @return
	 */
	public List<String> getDiagramName() {
		return listNames;
	}

	private Image getIcon(final RepresentationKind kind) {
		Image image = imageRegistry.get(kind.getIcon());
		if (image == null) {
			String uri = kind.getIcon();
			if (uri != null) {
				try {
					ImageDescriptor descriptor = ImageDescriptor.createFromURL(new URL(uri));
					imageRegistry.put(kind.getIcon(), descriptor);
					image = imageRegistry.get(kind.getIcon());
				} catch (MalformedURLException e) {
					Activator.log.error(e);
				}
			}
		}
		return image;
	}

	@Override
	public void dispose() {
		super.dispose();
		imageRegistry.dispose();
	}
	
}
