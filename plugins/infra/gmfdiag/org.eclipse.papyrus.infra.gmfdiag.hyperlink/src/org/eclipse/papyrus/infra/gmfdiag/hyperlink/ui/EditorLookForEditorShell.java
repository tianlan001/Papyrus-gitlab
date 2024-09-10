/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus (CEA) - bug 410346
 *  Christian W. Damus (CEA) - bug 431397
 *  Christian W. Damus - bugs 485220, 488965
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.hyperlink.ui;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.papyrus.commands.CreationCommandDescriptor;
import org.eclipse.papyrus.commands.CreationCommandRegistry;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.commands.ICreationCommandRegistry;
import org.eclipse.papyrus.infra.core.extension.NotFoundException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.ui.AbstractLookForEditorShell;
import org.eclipse.papyrus.infra.hyperlink.util.EditorListContentProvider;
import org.eclipse.papyrus.infra.hyperlink.util.TreeViewContentProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.ui.editorsfactory.IPageIconsRegistry;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFGraphicalContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.strategy.ContainmentBrowseStrategy;
import org.eclipse.papyrus.infra.widgets.editors.ICommitListener;
import org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

//TODO: Refactor. Remove the diagram creation listener
/**
 * @since 2.0
 */
public class EditorLookForEditorShell extends AbstractLookForEditorShell {

	/** The adapter factory. */
	protected AdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

	/** The selected diagram. */
	protected Object selectedEditor;

	/**
	 * Gets the selected editor.
	 *
	 * @return the selectedEditor
	 */
	protected Object getSelectedEditor() {
		return selectedEditor;
	}

	/**
	 * Sets the selected editor
	 *
	 * @param selectedEditor
	 *            the selectedEditor to set
	 */
	protected void setSelectedEditor(Object selectedEditor) {
		this.selectedEditor = selectedEditor;
	}

	/** The model. */
	protected EObject model;

	/** The diagram menu button. */
	protected Menu diagramMenuButton;

	/** The diagram list tree viewer. */
	protected TreeViewer diagramListTreeViewer;

	/** The tree viewer. */
	protected TreeViewer treeViewer;

	/**
	 * The listener interface for receiving diagramCreate events. The class that
	 * is interested in processing a diagramCreate event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's <code>addDiagramCreateListener<code> method. When
	 * the diagramCreate event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see DiagramCreateEvent
	 */
	public class DiagramCreateListener extends SelectionAdapter {

		/** The command descriptor. */
		private final CreationCommandDescriptor commandDescriptor;

		/** The container. */
		private EObject container;

		/** The i creation command registry. */
		private final ICreationCommandRegistry iCreationCommandRegistry;

		/**
		 * {@inheritedDoc}.
		 *
		 * @param e
		 *            the e
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				if (treeViewer.getSelection().isEmpty()) {
					return;
				}

				IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();

				EObject elt = EMFHelper.getEObject(selection.getFirstElement());
				if (elt == null) {
					return;
				}

				setContainer(elt);
				ModelSet modelSet = ServiceUtilsForEObject.getInstance().getModelSet(elt);

				ICreationCommand creationCommand = iCreationCommandRegistry.getCommand(commandDescriptor.getCommandId());
				creationCommand.createDiagram(modelSet, container, null);

				// refresh several filtered tree
				getDiagramfilteredTree().getViewer().refresh();
				getModeFilteredTree().getViewer().refresh();
			} catch (NotFoundException ex) {
				Activator.log.error(ex);
			} catch (ServiceException ex) {
				Activator.log.error(ex);
			}
		}

		/**
		 * Instantiates a new diagram create listener.
		 *
		 * @param commandDescriptor
		 *            the command descriptor
		 * @param backboneContext
		 *            the backbone context
		 * @param container
		 *            the container
		 * @param iCreationCommandRegistry
		 *            the i creation command registry
		 */
		public DiagramCreateListener(CreationCommandDescriptor commandDescriptor, EObject container, ICreationCommandRegistry iCreationCommandRegistry) {
			super();
			this.commandDescriptor = commandDescriptor;
			this.container = container;
			this.iCreationCommandRegistry = iCreationCommandRegistry;
		}

		/**
		 * Sets the container.
		 *
		 * @param container
		 *            the new container
		 */
		public void setContainer(EObject container) {
			this.container = container;
		}
	}


	/**
	 * Instantiates a new editor look for diagram.
	 *
	 * @param editorFactoryRegistry
	 *            the editor factory registry
	 * @param amodel
	 *            the amodel
	 */
	public EditorLookForEditorShell(Shell parentShell, IPageIconsRegistry editorFactoryRegistry, EObject amodel) {
		super(parentShell);

		this.model = amodel;
	}

	/**
	 * Instantiates a new editor look for diagram.
	 *
	 * @param editorFactoryRegistry
	 *            the editor factory registry
	 * @param amodel
	 *            the amodel
	 */
	public EditorLookForEditorShell(IShellProvider parentShell, IPageIconsRegistry editorFactoryRegistry, EObject amodel) {
		super(parentShell);

		this.model = amodel;
	}

	@Override
	protected void contentsCreated() {

		// intall tree with uml element
		treeViewer = getModeFilteredTree().getViewer();
		treeViewer.setUseHashlookup(true);

		ILabelProvider labelProvider;
		ServicesRegistry registry = null;

		try {
			registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(model);
			labelProvider = registry.getService(LabelProviderService.class).getLabelProvider();
		} catch (ServiceException ex) {
			Activator.log.error(ex);
			labelProvider = new LabelProvider();
		}

		treeViewer.setLabelProvider(labelProvider);

		// treeViewer.setContentProvider(new
		// CustomAdapterFactoryContentProvider(adapterFactory));
		// treeViewer.setContentProvider(new
		// SemanticEMFContentProvider(amodel)); //This content provider will
		// only display the selected element, instead of the root element
		// FIXME: Use a standard, non-deprecated content
		IHierarchicContentProvider semanticProvider = new TreeViewContentProvider(new EObject[] { EcoreUtil.getRootContainer(model) });
		ContainmentBrowseStrategy strategy = new ContainmentBrowseStrategy(semanticProvider);
		IGraphicalContentProvider graphicalContentProvider = new EMFGraphicalContentProvider(strategy, model.eResource().getResourceSet(), Activator.PLUGIN_ID + ".editorTreeView");
		treeViewer.setContentProvider(graphicalContentProvider);
		// treeViewer.setInput(model.eResource());
		treeViewer.setInput(registry);
		graphicalContentProvider.createAfter(getAfterTreeViewComposite());

		// install diagramlist
		diagramListTreeViewer = getDiagramfilteredTree().getViewer();
		diagramListTreeViewer.setUseHashlookup(true);

		// fill list of diagram
		// TODO
		// diagramListTreeViewer.setLabelProvider(new ObjectLabelProvider(null));

		// we can't reuse the same instance of the label provider see bug 385599: [Hyperlink] We can't select the diagram/table for referencing them
		diagramListTreeViewer.setLabelProvider(labelProvider);


		diagramListTreeViewer.setContentProvider(new EditorListContentProvider(model));
		diagramListTreeViewer.setInput(""); //$NON-NLS-1$

		// add listner on the new button to display menu for each diagram
		diagramMenuButton = new Menu(getNewDiagrambutton());
		getNewDiagrambutton().setMenu(diagramMenuButton);
		CreationCommandRegistry commandRegistry = CreationCommandRegistry.getInstance(org.eclipse.papyrus.infra.ui.Activator.PLUGIN_ID);
		for (CreationCommandDescriptor desc : commandRegistry.getCommandDescriptors()) {
			MenuItem menuItem = new MenuItem(diagramMenuButton, SWT.PUSH);
			menuItem.addSelectionListener(new DiagramCreateListener(desc, null, commandRegistry));
			menuItem.setText(desc.getLabel());
		}
		getNewDiagrambutton().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				diagramMenuButton.setVisible(true);
			}
		});

		// add listener to remove diagram
		getRemoveDiagrambutton().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				IStructuredSelection iSelection = (IStructuredSelection) getModeFilteredTree().getViewer().getSelection();
				Iterator<?> iterator = iSelection.iterator();

				final IPageManager pageManager;
				try {
					pageManager = ServiceUtilsForEObject.getInstance().getService(IPageManager.class, model);
				} catch (ServiceException ex) {
					Activator.log.error(ex);
					return;
				}

				final List<Object> pagesToDelete = new LinkedList<Object>();
				while (iterator.hasNext()) {
					EObject selectedElement = EMFHelper.getEObject(iterator.next());
					if (pageManager.allPages().contains(selectedElement)) {
						pagesToDelete.add(selectedElement);
					}
				}

				if (pagesToDelete.isEmpty()) {
					return;
				}

				for (Object page : pagesToDelete) {
					pageManager.closeAllOpenedPages(page);
				}

				// getDiagramfilteredTree().getViewer().setInput(""); //$NON-NLS-1$
				getModeFilteredTree().getViewer().refresh();
			}
		});

		IDoubleClickListener doubleClickHandler = new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				okPressed();
			}
		};

		// add listener to keep the selected diagram in the list for the model
		// view
		getModeFilteredTree().getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				Object selection = ((IStructuredSelection) getModeFilteredTree().getViewer().getSelection()).getFirstElement();
				refresh(selection);
			}
		});
		getModeFilteredTree().getViewer().addDoubleClickListener(doubleClickHandler);

		// add listener to keep in mind the selected diagram in the list for the
		// view of digram list
		getDiagramfilteredTree().getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				Object selection = ((IStructuredSelection) getDiagramfilteredTree().getViewer().getSelection()).getFirstElement();
				refresh(selection);
			}
		});
		getDiagramfilteredTree().getViewer().addDoubleClickListener(doubleClickHandler);

		// dispose the adapter factory when the shell is closed
		getShell().addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				// we created the adapter factory, so we should dispose it
				if (adapterFactory instanceof IDisposable) {
					((IDisposable) adapterFactory).dispose();
				}
			}
		});
	}

	@Override
	protected void okPressed() {
		if (treeViewer.getContentProvider() instanceof ICommitListener) {
			((ICommitListener) treeViewer.getContentProvider()).commit(null);
		}
		super.okPressed();
	}

	@Override
	protected void cancelPressed() {
		setSelectedEditor(null);
		super.cancelPressed();
	}


	protected void refresh(Object selectedElement) {
		selectedElement = EMFHelper.getEObject(selectedElement);
		Button but = getButton(OK);
		if (isAValidEditor(selectedElement)) {
			but.setEnabled(true);
			selectedEditor = selectedElement;
		} else {
			but.setEnabled(false);
			selectedEditor = null;
		}
	}

	protected boolean isAValidEditor(final Object object) {
		if (!(object instanceof EObject)) {
			return false;
		}

		EObject eObject = (EObject) object;

		try {
			return ServiceUtilsForEObject.getInstance().getService(IPageManager.class, eObject).allPages().contains(object);
		} catch (ServiceException ex) {
			return false;
		}
	}

}
