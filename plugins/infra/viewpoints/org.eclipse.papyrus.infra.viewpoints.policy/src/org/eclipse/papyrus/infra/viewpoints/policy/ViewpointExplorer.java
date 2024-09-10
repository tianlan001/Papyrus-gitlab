/*****************************************************************************
 * Copyright (c) 2014, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 570486
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.viewpoints.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

/**
 * Eclipse view for the user to explore the current viewpoint
 *
 * @author Laurent Wouters
 *
 */
public class ViewpointExplorer extends ViewPart {

	private TreeViewer tree;

	private IPartListener listener;

	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryContentProvider contentProvider;
	private AdapterFactoryLabelProvider labelProvider;
	private AdapterFactoryEditingDomain editingDomain;

	private class ViewpointAdapterFactory extends ComposedAdapterFactory implements IEditingDomainProvider {
		public ViewpointAdapterFactory(Descriptor.Registry adapterFactoryDescriptorRegistry) {
			super(adapterFactoryDescriptorRegistry);
		}

		@Override
		public EditingDomain getEditingDomain() {
			return editingDomain;
		}
	}

	/**
	 * Constructor.
	 */
	public ViewpointExplorer() {
		adapterFactory = new ViewpointAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		contentProvider = createContentProvider(adapterFactory);
		labelProvider = createLabelProvider(adapterFactory);
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, null, new HashMap<Resource, Boolean>()) {
			@Override
			public boolean isReadOnly(Resource resource) {
				return true; // to make the editing domain non-editable
			}
		};
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		parent.setLayout(gridLayout);

		Composite inner = new Composite(parent, SWT.NONE);
		inner.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.FILL_VERTICAL));
		inner.setLayout(new FillLayout());

		tree = new TreeViewer(inner, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setLabelProvider(labelProvider);
		tree.setContentProvider(contentProvider);

		getSite().getPage().addPartListener(listener = createSelectionListener(tree));
		getSite().setSelectionProvider(tree);
	}

	AdapterFactoryLabelProvider createLabelProvider(AdapterFactory adapterFactory) {
		return new AdapterFactoryLabelProvider(adapterFactory) {

			@Override
			public String getText(Object object) {
				return super.getText(unwrapPropertySource(object));
			}

			@Override
			public Image getImage(Object object) {
				return super.getImage(unwrapPropertySource(object));
			}

			@Override
			public StyledString getStyledText(Object object) {
				return super.getStyledText(unwrapPropertySource(object));
			}

		};
	}

	AdapterFactoryContentProvider createContentProvider(AdapterFactory adapterFactory) {
		return new AdapterFactoryContentProvider(adapterFactory) {

			@Override
			public Object[] getElements(Object object) {
				Object[] result;

				if (object instanceof Collection<?>) {
					result = ((Collection<?>) object).toArray();
				} else if (object instanceof Object[]) {
					result = (Object[]) object;
				} else {
					result = super.getElements(object);
				}

				return getPropertySources(result);
			}

			@Override
			public boolean hasChildren(Object object) {
				return super.hasChildren(unwrapPropertySource(object));
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				Object[] result = super.getChildren(unwrapPropertySource(parentElement));
				return getPropertySources(result);
			}

		};
	}

	@Override
	public void dispose() {
		labelProvider.dispose();
		adapterFactory.dispose();
		if (listener != null) {
			getSite().getPage().removePartListener(listener);
		}
	}

	@Override
	public void setFocus() {

	}

	private IPartListener createSelectionListener(TreeViewer viewer) {
		return new IPartListener() {
			private IWorkbenchPart editor;

			@Override
			public void partActivated(IWorkbenchPart part) {
				if (part == editor) {
					viewer.refresh();
				} else if (part instanceof IMultiDiagramEditor) {
					EditingDomain domain = part.getAdapter(EditingDomain.class);
					if (domain != null) {
						ResourceSet resourceSet = domain.getResourceSet();
						if (resourceSet instanceof ModelSet) {
							editor = part;
							ArchitectureDescriptionUtils utils = new ArchitectureDescriptionUtils((ModelSet) resourceSet);
							viewer.setInput(List.of(utils.getArchitectureContext()));
						}
					}
				}
			}

			@Override
			public void partBroughtToTop(IWorkbenchPart part) {
				if (part instanceof IMultiDiagramEditor) {

				}
			}

			@Override
			public void partClosed(IWorkbenchPart part) {
				if (part == editor) {
					viewer.setInput(null);
				}
			}

			@Override
			public void partDeactivated(IWorkbenchPart part) {
				if (part instanceof IMultiDiagramEditor) {

				}
			}

			@Override
			public void partOpened(IWorkbenchPart part) {
				if (part instanceof IMultiDiagramEditor) {

				}
			}
		};
	}

	/**
	 * @since 3.0
	 */
	public Object[] getPropertySources(Object[] objects) {
		ArrayList<Object> propertySources = new ArrayList<>();
		for (Object object : objects) {
			propertySources.add(getPropertySource(object));
		}
		return propertySources.toArray();
	}

	/**
	 * @since 3.0
	 */
	public Object getPropertySource(Object object) {
		return contentProvider.getPropertySource(object);
	}

	Object unwrapPropertySource(Object object) {
		return object instanceof PropertySource ? ((PropertySource) object).getObject() : object;
	}

}
