/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.presentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.StrictCompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceSetItemProvider;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.TransactionalCommandStackImpl;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;
import org.eclipse.emf.workspace.AbstractEMFOperation;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.emf.workspace.WorkspaceEditingDomainFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.filters.CompoundFilter;
import org.eclipse.papyrus.infra.filters.util.FiltersAdapterFactory;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Activator;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ChildConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Configuration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.DrawerConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.LeafConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationFactory;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.SeparatorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.StackConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.editor.messages.Messages;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider.CustomPaletteconfigurationItemProviderAdapterFactory;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.utils.CreatePaletteItemUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;


/**
 * Custom Editor for palette configuration.
 */
public class CustomPaletteconfigurationEditor extends PaletteconfigurationEditor implements ITabbedPropertySheetPageContributor {

	private static final String CONTRIBUTION_ID = "PaletteConfiguration"; //$NON-NLS-1$

	/** the property sheet page */
	private IPropertySheetPage propertySheetPage;

	/** the tool bar */
	private ToolBar toolbar;

	/** icon path for the create drawer button */
	protected static final String CREATE_DRAWERS_ICON = "/icons/new_drawer.gif";//$NON-NLS-1$

	/** icon path for the create separator button */
	protected static final String CREATE_SEPARATOR_ICON = "/icons/new_separator.gif";//$NON-NLS-1$

	/** icon path for the create stack button */
	protected static final String CREATE_STACK_ICON = "/icons/new_stack.gif";//$NON-NLS-1$

	/** icon path for the create stack button */
	protected static final String CREATE_TOOL_ICON = "/icons/new_tool.gif";//$NON-NLS-1$

	/** icon path for the delete button */
	protected static final String DELETE_ICON = "/icons/delete.gif";//$NON-NLS-1$

	/** icon path for the filters button */
	protected static final String FILTERS_ICON = "/icons/filter.gif";//$NON-NLS-1$

	private static final String NEW_TOOL_LABEL = Messages.CustomPaletteconfigurationEditor_NewTool;

	private static final String STACK_LABEL = "Stack"; //$NON-NLS-1$

	private static final String DRAWER_LABEL = "Drawer"; //$NON-NLS-1$

	private static final String SEPARATOR_LABEL = "Separator"; //$NON-NLS-1$

	/** validator key for toolbar items */
	protected final static String VALIDATOR = "validator"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initializeEditingDomain() {
		// Create an adapter factory that yields item providers.
		//
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory() {
			@Override
			public Adapter createResourceSetAdapter() {
				return new ResourceSetItemProvider(this) {
					@Override
					public Collection<?> getChildren(Object object) {
						ResourceSet resourceSet = (ResourceSet) object;
						if (!resourceSet.getResources().isEmpty() && !resourceSet.getResources().get(0).getContents().isEmpty() && resourceSet.getResources().get(0).getContents().get(0) instanceof PaletteConfiguration) {
							return ((PaletteConfiguration) resourceSet.getResources().get(0).getContents().get(0)).getDrawerConfigurations(); // For Drawers at root
						}
						return Collections.emptyList();
					}
				};
			}
		});
		adapterFactory.addAdapterFactory(new CustomPaletteconfigurationItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new FiltersAdapterFactory());

		// Create the command stack that will notify this editor as commands are executed.
		TransactionalCommandStack commandStack = new TransactionalCommandStackImpl();

		// Add a listener to set the most recent command's affected objects to be the selection of the viewer with focus.
		commandStack.addCommandStackListener(new CommandStackListener() {
			@Override
			public void commandStackChanged(final EventObject event) {
				getContainer().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						firePropertyChange(IEditorPart.PROP_DIRTY);

						// Try to select the affected objects.
						//
						Command mostRecentCommand = ((CommandStack) event.getSource()).getMostRecentCommand();
						if (null != mostRecentCommand) {
							setSelectionToViewer(mostRecentCommand.getAffectedObjects());
						}
						for (Iterator<PropertySheetPage> i = propertySheetPages.iterator(); i.hasNext();) {
							PropertySheetPage propertySheetPage = i.next();
							if (propertySheetPage.getControl().isDisposed()) {
								i.remove();
							} else {
								propertySheetPage.refresh();
							}
						}
					}
				});
			}
		});

		// Create the editing domain with a special command stack.
		TransactionalEditingDomainImpl editingDomain = new TransactionalEditingDomainImpl(adapterFactory, commandStack);
		WorkspaceEditingDomainFactory.INSTANCE.mapResourceSet(editingDomain);

		this.editingDomain = editingDomain;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPropertySheetPage getPropertySheetPage() {
		if (null == propertySheetPage) {
			propertySheetPage = new TabbedPropertySheetPage(this);
		}
		return propertySheetPage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getContributorId() {
		return CONTRIBUTION_ID;
	}

	/**
	 * Creates a toolbar item which can be checked.
	 *
	 * @param toolbar
	 *            the parent toolbar
	 * @param shownElementsIcon
	 *            path for shown elements icon
	 * @param listener
	 *            listener for button action
	 * @param tooltip
	 *            tooltip text for the toolbar item
	 */
	protected ToolItem createCheckToolBarItem(ToolBar toolbar, String shownElementsIcon, String tooltip, Listener listener) {
		ToolItem item = new ToolItem(toolbar, SWT.CHECK | SWT.BORDER);
		item.setImage(Activator.getPluginIconImage(Activator.PLUGIN_ID, shownElementsIcon));
		item.setToolTipText(tooltip);
		item.addListener(SWT.Selection, listener);
		return item;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createPages() {
		// Creates the model from the editor input
		createModel();

		Composite parent = getContainer();

		Composite paletteComposite = new Composite(parent, SWT.NONE);
		{
			GridLayout layout = new GridLayout(1, true);
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			paletteComposite.setLayout(layout);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			paletteComposite.setLayoutData(data);
		}

		toolbar = new ToolBar(paletteComposite, SWT.HORIZONTAL);
		{
			GridData data = new GridData(SWT.LEFT, SWT.CENTER, true, false);
			data.horizontalIndent = 20;
			toolbar.setLayoutData(data);
		}
		populatePalettePreviewToolBar(toolbar);

		// Only creates the other pages if there is something that can be edited
		if (!getEditingDomain().getResourceSet().getResources().isEmpty()) {
			// Create a page for the selection tree view.
			Tree tree = new Tree(paletteComposite, SWT.MULTI);
			{
				GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
				tree.setLayoutData(data);
			}
			selectionViewer = new TreeViewer(tree);
			setCurrentViewer(selectionViewer);

			selectionViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
			selectionViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
			selectionViewer.setInput(editingDomain.getResourceSet());
			selectionViewer.setSelection(new StructuredSelection(editingDomain.getResourceSet().getResources().get(0)), true);

			new AdapterFactoryTreeEditor(selectionViewer.getTree(), adapterFactory);

			selectionViewer.addSelectionChangedListener(new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					handleSelectionChanged(event);
				}
			});

			// Refresh buttons
			handleSelectionChanged(null);
			//

			// createContextMenuFor(selectionViewer);
			addDragDropSupport(selectionViewer);
			int pageIndex = addPage(paletteComposite);
			setPageText(pageIndex, PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_SelectionPage_label"));

			getSite().getShell().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					setActivePage(0);
				}
			});
		}

		// Ensures that this editor will only display the page's tab
		// area if there are more than one page
		getContainer().addControlListener(new ControlAdapter() {
			boolean guard = false;

			@Override
			public void controlResized(ControlEvent event) {
				if (!guard) {
					guard = true;
					hideTabs();
					guard = false;
				}
			}
		});

		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				updateProblemIndication();
			}
		});
	}

	/**
	 * Adds the dragDrop support to the viewer.
	 */
	protected void addDragDropSupport(StructuredViewer viewer) {
		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance(), LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance() };
		viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
		viewer.addDropSupport(dndOperations, transfers, new EditingDomainViewerDropAdapter(editingDomain, viewer));
	}

	/**
	 * Populates the preview palette tool bar.
	 *
	 * @param toolbar
	 *            the tool bar to populate
	 */
	protected void populatePalettePreviewToolBar(ToolBar toolbar) {
		// create validator
		PaletteConfigurationEntrySelectedValidator validator = new PaletteConfigurationEntrySelectedValidator();
		PaletteConfigurationStackLeafSelectedValidator validatorForNotStackChild = new PaletteConfigurationStackLeafSelectedValidator();

		createToolBarItem(toolbar, DELETE_ICON, Messages.CustomPaletteconfigurationEditor_RemoveButtonTooltip, createRemoveElementListener(), validator);
		createToolBarItem(toolbar, CREATE_DRAWERS_ICON, Messages.CustomPaletteconfigurationEditor_Create_Drawer_Tooltip, createNewDrawerListener(), null);
		createToolBarItem(toolbar, CREATE_SEPARATOR_ICON, Messages.CustomPaletteconfigurationEditor_Create_Separator_Tooltip, createNewSeparatorListener(), validatorForNotStackChild);
		createToolBarItem(toolbar, CREATE_TOOL_ICON, Messages.CustomPaletteconfigurationEditor_Create_Tool_Tooltip, createNewToolListener(), validator);
		createToolBarItem(toolbar, CREATE_STACK_ICON, Messages.CustomPaletteconfigurationEditor_Create_Stack_Tooltip, createNewStackListener(), validatorForNotStackChild);
		createToolBarItem(toolbar, FILTERS_ICON, "Create a new Filter", createFilterListener(), () -> {
			Object firstElement = ((IStructuredSelection) getSelection()).getFirstElement();
			return firstElement instanceof Configuration || firstElement instanceof CompoundFilter;
		});
	}

	private Listener createFilterListener() {
		return event -> {
			IStructuredSelection selection = (IStructuredSelection) selectionViewer.getSelection();
			if (selection == null || selection.size() != 1) {
				return;
			}

			Object selected = selection.getFirstElement();

			EObject parent;

			CompoundCommand command = new StrictCompoundCommand();

			if (selected instanceof Configuration) {
				Configuration configuration = (Configuration) selected;
				if (configuration.getFilter() != null) {
					command.append(new DeleteCommand(getEditingDomain(), Collections.singleton(configuration.getFilter())));
				}
				parent = configuration;
			} else if (selected instanceof CompoundFilter) {
				parent = (CompoundFilter) selected;
			} else {
				return;
			}

			List<CommandParameter> newChildDescriptors = editingDomain.getNewChildDescriptors(parent, null).stream()
					.filter(CommandParameter.class::isInstance)
					.map(CommandParameter.class::cast)
					.collect(Collectors.toList());

			NewFilterDialog dialog = new NewFilterDialog(getSite().getShell(), newChildDescriptors);
			if (dialog.open() == Dialog.OK && dialog.getNewChild() != null) {
				CommandParameter newChild = dialog.getNewChild();

				Command createCommand = editingDomain.createCommand(CreateChildCommand.class, new CommandParameter(parent, null, newChild));
				command.append(createCommand);

				TransactionalEditingDomain ted = (TransactionalEditingDomain) editingDomain;
				
				Command operation = new EMFOperationCommand(ted, new AbstractEMFOperation(ted, "Create filter") {

					@Override
					protected IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						if (command.canExecute()) {
							command.execute();
							return Status.OK_STATUS;
						} else {
							throw new ExecutionException("Unable to create a new filter - the operation is not enabled");
						}
					}
					
				});

				ted.getCommandStack().execute(operation);
			}
		};
	}

	/**
	 * handle the selection change event for the palette preview
	 *
	 * @param event
	 *            the event that is thrown by the palette viewer
	 */
	protected void handleSelectionChanged(SelectionChangedEvent event) {

		// update toolbar
		if (null != toolbar && !toolbar.isDisposed()) {
			for (int i = 0; i < toolbar.getItemCount(); i++) {
				ToolItem item = toolbar.getItem(i);
				Object validator = item.getData(VALIDATOR);
				if (validator instanceof ToolBarItemValidator) {
					item.setEnabled(((ToolBarItemValidator) validator).isEnable());
				}
			}
		}
	}

	/**
	 * Creates a toolbar item.
	 *
	 * @param toolbar
	 *            the parent toolbar
	 * @param itemIcon
	 *            path for icon
	 * @param tooltip
	 *            tooltip text for the toolbar item
	 * @param listener
	 *            listener for tool bar item
	 */
	protected void createToolBarItem(ToolBar toolbar, String itemIcon, String tooltip, Listener listener, ToolBarItemValidator validator) {
		ToolItem item = new ToolItem(toolbar, SWT.BORDER);
		item.setImage(Activator.getPluginIconImage(org.eclipse.papyrus.infra.gmfdiag.common.Activator.ID, itemIcon));
		item.setToolTipText(tooltip);
		item.addListener(SWT.Selection, listener);
		item.setData(VALIDATOR, validator);
	}

	/**
	 * Creates the listener for the remove item(s) button
	 *
	 * @return the listener for the remove button
	 */
	protected Listener createRemoveElementListener() {
		return new Listener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(Event event) {
				IStructuredSelection selection = (IStructuredSelection) selectionViewer.getSelection();
				if (selection == null || selection.size() < 1) {
					return;
				}

				Iterator<?> it = selection.iterator();
				Collection<Configuration> toDelete = new ArrayList<>();
				while (it.hasNext()) {
					Object object = it.next();
					if (object instanceof Configuration) {
						toDelete.add((Configuration)object);
					}
				}
				Command command = new DeleteCommand(editingDomain, toDelete);
				editingDomain.getCommandStack().execute(command);
				selectionViewer.refresh();
			}
		};
	}

	/**
	 * Creates the listener for the new drawer tool item.
	 *
	 * @return the listener created
	 */
	protected Listener createNewDrawerListener() {
		return new Listener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(Event event) {

				EObject palette = editingDomain.getResourceSet().getResources().get(0).getContents().get(0);
				if (palette instanceof PaletteConfiguration) {
					// Create a void drawer
					DrawerConfiguration drawer = PaletteconfigurationFactory.eINSTANCE.createDrawerConfiguration();
					// Set label
					drawer.setLabel(DRAWER_LABEL);
					// Set the drawer on the palette configuration
					EList<DrawerConfiguration> drawerConfigurations = ((PaletteConfiguration) palette).getDrawerConfigurations();
					Command command = new AddCommand(editingDomain, drawerConfigurations, drawer);
					editingDomain.getCommandStack().execute(command);
					selectionViewer.refresh();
				}
			}
		};
	}

	/**
	 * Creates the listener for the new separator tool item
	 *
	 * @return the listener created
	 */
	protected Listener createNewSeparatorListener() {
		return new Listener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(Event event) {
				// retrieve selected element
				Object object = ((IStructuredSelection) selectionViewer.getSelection()).getFirstElement();
				// Create a separator
				SeparatorConfiguration separator = PaletteconfigurationFactory.eINSTANCE.createSeparatorConfiguration();
				// Set label & Id
				separator.setLabel(SEPARATOR_LABEL);
				separator.setId(CreatePaletteItemUtil.generateID(SEPARATOR_LABEL));

				final Command command;
				if (object instanceof DrawerConfiguration) {
					// Add separator at the end of drawer own configuration
					command = new AddCommand(editingDomain, ((DrawerConfiguration) object).getOwnedConfigurations(), separator);
				} else if (object instanceof ChildConfiguration) {
					// retrieve parent
					EList<ChildConfiguration> ownedConfigurations = ((DrawerConfiguration) ((ChildConfiguration) object).eContainer()).getOwnedConfigurations();
					int index = ownedConfigurations.indexOf(object);

					// adds the separator
					command = new AddCommand(editingDomain, ownedConfigurations, separator, index);
				} else {
					return;
				}
				
				editingDomain.getCommandStack().execute(command);
				selectionViewer.expandToLevel(object, 1);
				selectionViewer.refresh();
			}
		};
	}

	/**
	 * Creates the listener for the new stack tool item
	 *
	 * @return the listener created
	 */
	protected Listener createNewStackListener() {
		return new Listener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(Event event) {
				// retrieve selected element
				Object object = ((IStructuredSelection) selectionViewer.getSelection()).getFirstElement();
				// Create a separator
				StackConfiguration stack = PaletteconfigurationFactory.eINSTANCE.createStackConfiguration();
				// Set label & Id
				stack.setLabel(STACK_LABEL);
				stack.setId(CreatePaletteItemUtil.generateID(STACK_LABEL));

				final Command command;
				if (object instanceof DrawerConfiguration) {
					// Add separator at the end of drawer own configuration
					command = new AddCommand(editingDomain, ((DrawerConfiguration) object).getOwnedConfigurations(), stack);
				} else if (object instanceof ChildConfiguration) {
					// retrieve parent
					EList<ChildConfiguration> ownedConfigurations = ((DrawerConfiguration) ((ChildConfiguration) object).eContainer()).getOwnedConfigurations();
					int index = ownedConfigurations.indexOf(object);

					// adds the stack
					command = new AddCommand(editingDomain, ownedConfigurations, stack, index);
				} else {
					return;
				}
				editingDomain.getCommandStack().execute(command);
				selectionViewer.expandToLevel(object, 1);
				selectionViewer.refresh();
			}
		};
	}

	/**
	 * Creates the listener for the new tool item
	 *
	 * @return the listener created
	 */
	protected Listener createNewToolListener() {
		return new Listener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(Event event) {
				// retrieve selected element
				Object object = ((IStructuredSelection) selectionViewer.getSelection()).getFirstElement();
				// Create a separator
				ToolConfiguration tool = PaletteconfigurationFactory.eINSTANCE.createToolConfiguration();
				// Set label & Id
				tool.setLabel(NEW_TOOL_LABEL);

				final Command command;
				if (object instanceof DrawerConfiguration || object instanceof StackConfiguration) {
					// Add tool at the end of drawer own configuration
					if (object instanceof DrawerConfiguration) {
						command = new AddCommand(editingDomain, ((DrawerConfiguration) object).getOwnedConfigurations(), tool);
					} else if (object instanceof StackConfiguration) {
						command = new AddCommand(editingDomain, ((StackConfiguration) object).getOwnedConfigurations(), tool);
					} else {
						return;
					}
				} else if (object instanceof ChildConfiguration) {
					// retrieve parent
					EObject eContainer = ((EObject) object).eContainer();
					// add as sibling of the object
					if (eContainer instanceof DrawerConfiguration) {
						EList<ChildConfiguration> ownedConfigurations = ((DrawerConfiguration) eContainer).getOwnedConfigurations();
						int index = ownedConfigurations.indexOf(object);
						command = new AddCommand(editingDomain, ownedConfigurations, tool, index);
					} else if (eContainer instanceof StackConfiguration) {
						EList<LeafConfiguration> ownedConfigurations = ((StackConfiguration) eContainer).getOwnedConfigurations();
						int index = ownedConfigurations.indexOf(object);
						command = new AddCommand(editingDomain, ownedConfigurations, tool, index);
					} else {
						return;
					}
				} else {
					return;
				}
				
				editingDomain.getCommandStack().execute(command);
				selectionViewer.expandToLevel(object, 1);
				selectionViewer.refresh();
			}
		};
	}

	/**
	 * Validator for the create separator or stack tool item. Only valid when selection is a {@link PaletteEntryProxy} or a {@link PaletteLocalDrawerProxy}.
	 */
	protected class PaletteConfigurationEntrySelectedValidator implements ToolBarItemValidator {

		/**
		 * @{inheritDoc}
		 */
		@Override
		public boolean isEnable() {
			// retrieve selection
			if (selectionViewer != null && !selectionViewer.getControl().isDisposed()) {
				// retrieve selection. first element should be a drawer
				IStructuredSelection selection = (IStructuredSelection) selectionViewer.getSelection();
				if (selection == null) {
					return false;
				} else {
					Object object = selection.getFirstElement();
					return (object instanceof Configuration);
				}
			}
			return false;
		}
	}

	/**
	 * Validator for the create separator or stack tool item. Only valid when selection is a {@link PaletteEntryProxy} or a {@link PaletteLocalDrawerProxy}.
	 */
	protected class PaletteConfigurationStackLeafSelectedValidator implements ToolBarItemValidator {

		/**
		 * @{inheritDoc}
		 */
		@Override
		public boolean isEnable() {
			// retrieve selection
			if (selectionViewer != null && !selectionViewer.getControl().isDisposed()) {
				// retrieve selection. first element should be a drawer
				IStructuredSelection selection = (IStructuredSelection) selectionViewer.getSelection();
				if (selection == null) {
					return false;
				} else {
					Object object = selection.getFirstElement();
					return (object instanceof Configuration && !(((Configuration) object).eContainer() instanceof StackConfiguration));
				}
			}
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Overridden to add save option URIHandlerImpl.PlatformSchemeAware().
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		// Save only resources that have actually changed.
		//
		final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
		saveOptions.put(Resource.OPTION_LINE_DELIMITER, Resource.OPTION_LINE_DELIMITER_UNSPECIFIED);
		saveOptions.put(XMLResource.OPTION_URI_HANDLER, new URIHandlerImpl.PlatformSchemeAware());

		// Do the work within an operation because this is a long running activity that modifies the workbench.
		//
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			// This is the method that gets invoked when the operation runs.
			//
			@Override
			public void execute(IProgressMonitor monitor) {
				// Save the resources to the file system.
				//
				boolean first = true;
				for (Resource resource : editingDomain.getResourceSet().getResources()) {
					if ((first || !resource.getContents().isEmpty() || isPersisted(resource)) && !editingDomain.isReadOnly(resource)) {
						try {
							long timeStamp = resource.getTimeStamp();
							resource.save(saveOptions);
							if (resource.getTimeStamp() != timeStamp) {
								savedResources.add(resource);
							}
						} catch (Exception exception) {
							resourceToDiagnosticMap.put(resource, analyzeResourceProblems(resource, exception));
						}
						first = false;
					}
				}
			}
		};

		updateProblemIndication = false;
		try {
			// This runs the options, and shows progress.
			//
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);

			// Refresh the necessary state.
			//
			((BasicCommandStack) editingDomain.getCommandStack()).saveIsDone();
			firePropertyChange(IEditorPart.PROP_DIRTY);
		} catch (Exception exception) {
			// Something went wrong that shouldn't.
			//
			PaletteConfigurationEditorPlugin.INSTANCE.log(exception);
		}
		updateProblemIndication = true;
		updateProblemIndication();
	}

	/**
	 * Item validator.
	 */
	protected interface ToolBarItemValidator {

		/**
		 * Checks if the button should be enable or not
		 *
		 * @return <code>true</code> if the button should be enable
		 */
		public abstract boolean isEnable();
	}
}
