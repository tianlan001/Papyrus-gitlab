/**
 * Copyright (c) 2017, 2021 CEA LIST, Christian W. Damus, and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  Christian W. Damus - bug 570486
 *
 *
 */
package org.eclipse.papyrus.infra.ui.architecture.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

/**
 * A composite widget that shows the visible architecture contexts and their viewpoints
 * and allows for changing their selection
 *
 * @since 1.0
 */
public class ArchitectureContextComposite extends Composite {

	/**
	 * An interface for doing updates upon selection changes
	 */
	public static interface Updater {
		void update();
	}

	// allow selection of multiple contexts
	private boolean allowSeveralContexts;

	// the set of selected contexts
	private Set<String> selectedContexts;

	// the set of selected viewpoints
	private Set<String> selectedViewpoints;

	// the viewer for architecture contexts
	private CheckboxTreeViewer contextsViewer;

	// the viewer for architecture viewpoints
	private CheckboxTableViewer viewpointViewer;

	// the adapter factory of the architecture metadata
	private ComposedAdapterFactory composedAdapterFactory;

	// an updater to call upon selection changes
	private Updater updater;

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param columns
	 * @param hspan
	 * @param fill
	 * @param marginwidth
	 * @param marginheight
	 */
	public ArchitectureContextComposite(Composite parent, int columns, int hspan, int fill, int marginwidth, int marginheight) {
		super(parent, SWT.NONE);
		layoutComposite(this, parent, columns, hspan, fill, marginwidth, marginheight);

		composedAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		Composite tableComposite = createComposite(this, 1, 1, GridData.FILL_BOTH, 0, 0);
		createLabel(tableComposite, "Architecture Contexts:", 2);

		contextsViewer = new ContainerCheckedTreeViewer(tableComposite, SWT.MULTI | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 250;
		contextsViewer.getControl().setLayoutData(gd);

		contextsViewer.setContentProvider(new ITreeContentProvider() {
			private Collection<MergedArchitectureContext> allContexts;

			@Override
			public boolean hasChildren(Object element) {
				return element instanceof MergedArchitectureDomain;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				Set<MergedArchitectureDomain> allDomains = new LinkedHashSet<>();
				allContexts = new LinkedHashSet<>();
				for (Object obj : ((Object[]) inputElement)) {
					MergedArchitectureContext context = (MergedArchitectureContext) obj;
					allContexts.add(context);
					allDomains.add(context.getDomain());
				}
				return allDomains.toArray();
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof MergedArchitectureDomain) {
					MergedArchitectureDomain domain = (MergedArchitectureDomain) parentElement;
					List<MergedArchitectureContext> possibleContexts = new ArrayList<>(domain.getContexts());
					possibleContexts.retainAll(allContexts);
					return possibleContexts.toArray();
				}
				return null;
			}
		});
		contextsViewer.setLabelProvider(new AdapterFactoryLabelProvider(composedAdapterFactory));
		contextsViewer.setComparator(new ViewerComparator());
		contextsViewer.setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				if (element instanceof MergedArchitectureContext) {
					return selectedContexts.contains(((MergedArchitectureContext) element).getId());
				} else {
					return contextsViewer.getChecked(element);
				}
			}
		});
		contextsViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				ArchitectureDomainManager manager = ArchitectureDomainManager.getInstance();

				if (!allowSeveralContexts) {
					if (event.getChecked() || contextsViewer.getCheckedElements().length == 0) {
						if (event.getElement() instanceof MergedArchitectureContext) {
							contextsViewer.setCheckedElements(new Object[] { event.getElement() });
						} else {
							for (TreeItem item : contextsViewer.getTree().getItems()) {
								if (item.getData() == event.getElement()) {
									contextsViewer.setCheckedElements(new Object[] { item.getItem(0).getData() });
									break;
								}
							}
						}
					}
				}

				selectedContexts.clear();
				for (Object element : contextsViewer.getCheckedElements()) {
					if (element instanceof MergedArchitectureContext) {
						selectedContexts.add(((MergedArchitectureContext) element).getId());
					}
				}

				selectedViewpoints.clear();
				for (String contextId : selectedContexts) {
					MergedArchitectureContext context = manager.getArchitectureContextById(contextId);
					Collection<MergedArchitectureViewpoint> viewpoints = context.getDefaultViewpoints();
					if (viewpoints.isEmpty()) {
						viewpoints = context.getViewpoints();
					}
					for (MergedArchitectureViewpoint viewpoint : viewpoints) {
						selectedViewpoints.add(viewpoint.getId());
					}
				}

				updateViewpoints();
				if (updater != null) {
					updater.update();
				}
			}
		});

		ColumnViewerToolTipSupport.enableFor(contextsViewer, ToolTip.NO_RECREATE);

		Composite viewpointComposite = createComposite(this, 1, 1, GridData.FILL_HORIZONTAL, 0, 0);

		createLabel(viewpointComposite, "Architecture Viewpoints:", 1);

		viewpointViewer = CheckboxTableViewer.newCheckList(viewpointComposite, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 100;
		viewpointViewer.getControl().setLayoutData(gd);
		viewpointViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				Set<MergedADElement> viewpoints = new TreeSet<>(new Comparator<MergedADElement>() {
					@Override
					public int compare(MergedADElement o1, MergedADElement o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				for (Object obj : ((Object[]) inputElement)) {
					if (obj instanceof MergedArchitectureContext) {
						viewpoints.addAll(((MergedArchitectureContext) obj).getViewpoints());
					}
				}
				return viewpoints.toArray();
			}
		});
		viewpointViewer.setLabelProvider(new AdapterFactoryLabelProvider(composedAdapterFactory));
		viewpointViewer.setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				return selectedViewpoints.contains(((MergedArchitectureViewpoint) element).getId());
			}
		});
		viewpointViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				MergedArchitectureViewpoint viewpoint = (MergedArchitectureViewpoint) event.getElement();
				if (event.getChecked()) {
					selectedViewpoints.add(viewpoint.getId());
				} else {
					selectedViewpoints.remove(viewpoint.getId());
				}
				updateViewpoints();
				if (updater != null) {
					updater.update();
				}
			}
		});
		updateViewpoints();
	}

	/**
	 * Sets the input object of the composite that initializes its viewers
	 *
	 * @param input
	 *            the input object
	 */
	public void setInput(Object input) {
		contextsViewer.setInput(input);
		contextsViewer.expandAll();
		updateViewpoints();
	}

	/**
	 * Sets whether to show several context
	 * 
	 * @param allowSeveralContexts
	 *            boolean value
	 */
	public void setAllowSeveralContexts(boolean allowSeveralContexts) {
		this.allowSeveralContexts = allowSeveralContexts;
	}

	/**
	 * @return an array of selected contexts
	 */
	public String[] getSelectedContexts() {
		return selectedContexts.toArray(new String[0]);
	}

	/**
	 * Sets the selected contexts
	 *
	 * @param selectedContexts
	 */
	public void setSelectedContexts(String[] selectedContexts) {
		this.selectedContexts = new HashSet<>(Arrays.asList(selectedContexts));
	}

	/**
	 * @return an array of selected viewpoints
	 */
	public String[] getSelectedViewpoints() {
		return selectedViewpoints.toArray(new String[0]);
	}

	/**
	 * Sets the selected viewpoints
	 *
	 * @param selectedViewpoints
	 */
	public void setSelectedViewpoints(String[] selectedViewpoints) {
		this.selectedViewpoints = new HashSet<>(Arrays.asList(selectedViewpoints));
	}

	/**
	 * Sets the updater instance
	 *
	 * @param updater
	 */
	public void setUpdater(Updater updater) {
		this.updater = updater;
	}

	/*
	 * update the viewpoint viewer based on changes to context viewer
	 */
	private void updateViewpoints() {
		viewpointViewer.setInput(contextsViewer.getCheckedElements());
	}

	private static Composite createComposite(Composite parent, int columns, int hspan, int fill, int marginwidth, int marginheight) {
		Composite g = new Composite(parent, SWT.NONE);
		layoutComposite(g, parent, columns, hspan, fill, marginwidth, marginheight);
		return g;
	}

	private static Composite layoutComposite(Composite g, Composite parent, int columns, int hspan, int fill, int marginwidth, int marginheight) {
		GridLayout layout = new GridLayout(columns, false);
		layout.marginWidth = marginwidth;
		layout.marginHeight = marginheight;
		g.setLayout(layout);
		g.setFont(parent.getFont());
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	private static Label createLabel(Composite parent, String text, int hspan) {
		Label l = new Label(parent, SWT.NONE);
		l.setFont(parent.getFont());
		l.setText(text);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = hspan;
		gd.grabExcessHorizontalSpace = false;
		l.setLayoutData(gd);
		return l;
	}

}
