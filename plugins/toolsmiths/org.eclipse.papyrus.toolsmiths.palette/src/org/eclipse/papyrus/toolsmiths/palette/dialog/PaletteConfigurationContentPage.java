/*****************************************************************************
 * Copyright (c) 2009, 2014 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 454578
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - new implemenatation for palette configuration model case
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.palette.dialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.PaletteToolbar;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.common.core.service.ProviderPriority;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorWithFlyOutPalette;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PaletteUtil;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ChildConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Configuration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.DrawerConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.LeafConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationFactory;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.SeparatorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.StackConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.utils.CreatePaletteItemUtil;
import org.eclipse.papyrus.infra.properties.ui.runtime.DisplayEngine;
import org.eclipse.papyrus.infra.properties.ui.util.PropertiesDisplayHelper;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.toolsmiths.palette.Messages;
import org.eclipse.papyrus.toolsmiths.palette.provider.PaletteLabelProvider;
import org.eclipse.papyrus.toolsmiths.palette.provider.ProfileToolsMetaclassStereotypeTreeContentProvider;
import org.eclipse.papyrus.toolsmiths.palette.provider.ProfileToolsStereotypeMetaclassTreeContentProvider;
import org.eclipse.papyrus.toolsmiths.palette.provider.UMLToolsTreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Wizard page for information about the new local palette definition
 */
public class PaletteConfigurationContentPage extends WizardPage {

	/** icon path for the add button */
	protected static final String ADD_ICON = "/icons/arrow_right.gif"; //$NON-NLS-1$

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

	/** icon path for the remove button */
	protected static final String REMOVE_ICON = "/icons/arrow_left.gif"; //$NON-NLS-1$

	/** icon path when tools are shown */
	protected static final String SHOWN_TOOLS_ICON = "/icons/tools_shown.gif"; //$NON-NLS-1$

	/** path to the icon */
	protected static final String WIZARD_ICON = "/icons/local_desc_wiz.png"; //$NON-NLS-1$

	/** icon for the content provider switch button */
	protected static final String SWITCH_CONTENT_PROVIDER_ICON = "/icons/switch_provider.gif"; //$NON-NLS-1$

	/** the label for drawer */
	private static final String DRAWER_LABEL = Messages.PaletteConfigurationContentPage_Drawer;

	/** The label for new tool */
	private static final String NEW_TOOL_LABEL = Messages.PaletteConfigurationContentPage_NewTool;

	/** the label for separator */
	private static final String SEPARATOR_LABEL = Messages.PaletteConfigurationContentPage_Separator;

	/** the label for stack */
	private static final String STACK_LABEL = Messages.PaletteConfigurationContentPage_Stack;

	/** label for the standard tools */
	private static final String UML_TOOLS_LABEL = Messages.PaletteConfigurationContentPage_UMLTools;

	/** Validator key for toolbar items */
	private final static String VALIDATOR = "validator"; //$NON-NLS-1$

	/** the add button */
	private Button addButton;

	/** the remove button */
	private Button removeButton;

	/** the tree viewer for available tools */
	private TreeViewer availableToolsViewer;

	/** combo to select which profile tools should be visible */
	private Combo profileCombo;

	/** list of profiles that can provide tools */
	private List<String> profileComboList = new ArrayList<String>();

	/** the tree viewer for the edited palette */
	private TreeViewer paletteTreeViewer;

	/** the display engine */
	private DisplayEngine displayEngine;

	/** instance of the filter used to show/hide drawers */
	private final ViewerFilter drawerFilter = new DrawerFilter();

	/** the editing domain */
	private AdapterFactoryEditingDomain editingDomain;

	/** editor part in which the palette is created */
	private IEditorPart editorPart;

	/** the label provider for the edited palette */
	private PaletteLabelProvider paletteLabelProvider;

	/** priority of the current edited palette */
	private ProviderPriority priority;

	/** the properties composite */
	private Composite propertiesComposite;

	/** tool item in charge of toggling content providers in the available tool viewer */
	protected ToolItem toggleContentProvider;

	/** the toolbar for actions on edited palette */
	private ToolBar toolbar;

	/** instance of the filter used to show/hide tools */
	private final ViewerFilter toolFilter = new ToolFilter();

	/** enumeration of existing entry types */
	public enum EntryType {
		ASPECT_TOOL, DRAWER, SEPARATOR, STACK, TOOL
	}

	/**
	 * Filter for the viewer. Hide/show Drawers
	 */
	protected class DrawerFilter extends ViewerFilter {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
			boolean notDrawer = true;
			if (element instanceof PaletteDrawer) {
				notDrawer = false;
			}
			return notDrawer;
		}
	}

	/**
	 * A viewer comparator for label of Palette Entry
	 */
	protected class LabelViewerComparator extends ViewerComparator {

		/**
		 * Creates a new LabelViewerComparator.
		 */
		public LabelViewerComparator() {
			super();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compare(final Viewer testViewer, final Object e1, final Object e2) {
			String label1 = "";//$NON-NLS-1$
			String label2 = "";//$NON-NLS-1$

			if (e1 instanceof PaletteEntry) {
				label1 = ((PaletteEntry) e1).getLabel();
			} else if (e1 instanceof Stereotype) {
				label1 = ((Stereotype) e1).getName();
			}
			if (e2 instanceof PaletteEntry) {
				label2 = ((PaletteEntry) e2).getLabel();
			} else if (e2 instanceof Stereotype) {
				label2 = ((Stereotype) e2).getName();
			}

			int comparaison;
			if (null == label1 && null == label2) {
				comparaison = 0;
			} else if (null == label1) {
				comparaison = 1;
			} else if (null == label2) {
				comparaison = -1;
			} else {
				comparaison = label1.compareTo(label2);
			}
			return comparaison;
		}
	}

	/**
	 * Validator for the create separator or stack tool item. Only valid when selection is a {@link PaletteEntryProxy} or a {@link PaletteLocalDrawerProxy}
	 */
	protected class PaletteConfigurationEntrySelectedValidator implements ToolBarItemValidator {

		/**
		 * @{inheritDoc
		 */
		@Override
		public boolean isEnable() {
			boolean enable = false;
			// retrieve selection
			if (null != paletteTreeViewer && !paletteTreeViewer.getControl().isDisposed()) {
				// retrieve selection. first element should be a drawer
				IStructuredSelection selection = (IStructuredSelection) paletteTreeViewer.getSelection();
				if (null != selection) {
					Object object = selection.getFirstElement();
					enable = (object instanceof Configuration);
				}
			}
			return enable;
		}
	}


	/**
	 * validator for the create separator or stack tool item. Only valid when selection is a {@link PaletteEntryProxy} or a {@link PaletteLocalDrawerProxy}
	 */
	protected class PaletteConfigurationStackLeafSelectedValidator implements ToolBarItemValidator {

		/**
		 * @{inheritDoc
		 */
		@Override
		public boolean isEnable() {
			boolean enable = false;
			// retrieve selection
			if (paletteTreeViewer != null && !paletteTreeViewer.getControl().isDisposed()) {
				// retrieve selection. first element should be a drawer
				IStructuredSelection selection = (IStructuredSelection) paletteTreeViewer.getSelection();
				if (null != selection) {
					Object object = selection.getFirstElement();
					enable = (object instanceof Configuration && !(((Configuration) object).eContainer() instanceof StackConfiguration));
				}
			}
			return enable;
		}
	}



	/**
	 * Listener for the profile combo. It changes the input of the following viewer.
	 */
	protected class ProfileComboSelectionListener implements SelectionListener, ModifyListener {

		/**
		 * handles the change selection for the combo
		 */
		protected void handleSelectionChanged() {
			int index = profileCombo.getSelectionIndex();
			if (0 <= index && index < profileCombo.getItems().length) {
				String name = profileComboList.get(index);

				Collection<PaletteEntry> standardEntries = getAllVisibleStandardEntries();
				// retrieve the profile or uml standards tools to display
				if (UML_TOOLS_LABEL.equals(name)) {
					// change content provider
					availableToolsViewer.setContentProvider(new UMLToolsTreeContentProvider());
					availableToolsViewer.setInput(standardEntries);
					toggleContentProvider.setEnabled(false);
				} else {
					if (null != toggleContentProvider && !toggleContentProvider.isDisposed()) {
						toggleContentProvider.setEnabled(true);
					}
					// switch content provider
					// this is a profile in case of uml2 tools
					Profile profile = getAllAppliedProfiles().get(index);
					if (toggleContentProvider.getSelection()) {
						availableToolsViewer.setContentProvider(new ProfileToolsStereotypeMetaclassTreeContentProvider(profile, standardEntries));
					} else {
						availableToolsViewer.setContentProvider(new ProfileToolsMetaclassStereotypeTreeContentProvider(profile, standardEntries));
					}

					// generate tools for given profile
					availableToolsViewer.setInput(profile);
				}
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void modifyText(final ModifyEvent e) {
			handleSelectionChanged();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void widgetDefaultSelected(final SelectionEvent e) {
			// Do nothing
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void widgetSelected(final SelectionEvent e) {
			handleSelectionChanged();
		}
	}


	/**
	 * Item validator interface
	 */
	interface ToolBarItemValidator {

		/**
		 * Checks if the button should be enable or not
		 *
		 * @return <code>true</code> if the button should be enable
		 */
		public boolean isEnable();
	}

	/**
	 * Filter for the viewer. Hide/show Drawers
	 */
	protected class ToolFilter extends ViewerFilter {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
			return !(element instanceof ToolEntry);
		}
	}

	/** Listener to validate page on change. */
	EContentAdapter adapter = new EContentAdapter() {
		/**
		 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 */
		@Override
		public void notifyChanged(Notification msg) {
			setPageComplete(validatePage());
		}
	};

	/** the palette configuration model */
	private PaletteConfiguration paletteConfigurationModel;

	/**
	 * Constructor.
	 *
	 * @param editorPart
	 * @param editingDomain
	 */
	public PaletteConfigurationContentPage(final IEditorPart editorPart, final AdapterFactoryEditingDomain editingDomain) {
		super(Messages.Local_Palette_ContentPage_Name, Messages.Local_Palette_ContentPage_Title, Activator.imageDescriptorFromPlugin(org.eclipse.papyrus.infra.gmfdiag.common.Activator.ID, WIZARD_ICON));
		this.editorPart = editorPart;
		this.editingDomain = editingDomain;
		paletteConfigurationModel = getPaletteConfigurationModel();
		paletteConfigurationModel.eAdapters().add(adapter);
	}

	/**
	 * Constructor.
	 *
	 * @param pageName
	 */
	protected PaletteConfigurationContentPage(final String pageName) {
		super(pageName);
	}

	/**
	 * Add drag support from the available tools viewer
	 */
	protected void addAvailableToolsDragSupport() {
		// transfer types
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance(), LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance() };
		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		// drag listener

		availableToolsViewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(availableToolsViewer));
	}


	/**
	 * Adds drag ability to the palette preview composite
	 */
	protected void addPalettePreviewDragSupport() {
		// transfer types
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance(), LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance() };
		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		// drag listener
		paletteTreeViewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(paletteTreeViewer));
	}

	/**
	 * Add drop behavior for the palette preview
	 */
	protected void addPalettePreviewDropSupport() {
		// transfer types
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance(), LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance() };
		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		EditingDomainViewerDropAdapter listener = new EditingDomainViewerDropAdapter(editingDomain, paletteTreeViewer) {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void dragEnter(final DropTargetEvent event) {
				super.dragEnter(event);
				expandItem(event);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void drop(final DropTargetEvent event) {
				super.drop(event);
				expandItem(event);
			}

			/**
			 * Expand the TreeItem from the event
			 * 
			 * @param event
			 */
			private void expandItem(final DropTargetEvent event) {
				// Expands branch when over it to show children
				if (event.item instanceof TreeItem) {
					// Get the target Object of the drop
					Object target = ((TreeItem) event.item).getData();
					if (target == null) {
						target = paletteTreeViewer.getInput();
					}
					// Expand tree
					paletteTreeViewer.expandToLevel(target, 1);
				}
			}

		};

		paletteTreeViewer.addDropSupport(dndOperations, transfers, listener);
	}

	/**
	 * Creates the add button listener
	 */
	protected MouseListener createAddButtonListener() {
		return new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				// add the element selected on the left to the right tree
				// check the selection.
				IStructuredSelection selection = (IStructuredSelection) availableToolsViewer.getSelection();
				if (selection == null || selection.size() < 1) {
					return;
				}
				PaletteEntry entry = (PaletteEntry) selection.getFirstElement();
				if (entry == null) {
					return;
				}

				// find the selection on the right
				selection = (IStructuredSelection) paletteTreeViewer.getSelection();

				Configuration target = (Configuration) selection.getFirstElement();

				// Create Entry
				CompoundCommand command = new CompoundCommand();
				ToolConfiguration node = CreatePaletteItemUtil.createToolConfiguration(entry, editingDomain.getResourceSet());
				if (null != node) {
					if (target instanceof PaletteConfiguration) {
						// ((PaletteConfiguration) target).getDrawerConfigurations().add((DrawerConfiguration) node);
						command.append(new AddCommand(editingDomain, ((PaletteConfiguration) target).getDrawerConfigurations(), (DrawerConfiguration) node));
					} else if (target instanceof DrawerConfiguration) {
						// ((DrawerConfiguration) target).getOwnedConfigurations().add((ChildConfiguration) node);
						command.append(new AddCommand(editingDomain, ((DrawerConfiguration) target).getOwnedConfigurations(), (ChildConfiguration) node));
					} else if (target instanceof StackConfiguration) {
						// ((StackConfiguration) target).getOwnedConfigurations().add((LeafConfiguration) node);
						command.append(new AddCommand(editingDomain, ((StackConfiguration) target).getOwnedConfigurations(), (LeafConfiguration) node));
					}
				}
				command.append(CreatePaletteItemUtil.createElementTypesElement(editingDomain, (ToolEntry) entry, node));
				execute(command);
				// Expand tree
				paletteTreeViewer.expandToLevel(target, 1);
			}
		};
	}

	/**
	 * creates the buttons to add/remove entries from palette entrey and palette preview
	 */
	protected void createAddRemoveButtons() {
		Composite composite = new Composite((Composite) getControl(), SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		composite.setLayout(layout);

		GridData data = new GridData(SWT.CENTER, SWT.CENTER, false, true);
		composite.setLayoutData(data);

		addButton = new Button(composite, SWT.NONE);
		addButton.setImage(Activator.getDefault().getImage(ADD_ICON));
		addButton.setToolTipText(Messages.PapyrusPaletteCustomizerDialog_AddButtonTooltip);
		addButton.addMouseListener(createAddButtonListener());
		addButton.setEnabled(false);

		removeButton = new Button(composite, SWT.NONE);
		removeButton.setImage(Activator.getDefault().getImage(REMOVE_ICON));
		removeButton.setToolTipText(Messages.PapyrusPaletteCustomizerDialog_RemoveButtonTooltip);
		removeButton.addMouseListener(createRemoveButtonListener());
		removeButton.setEnabled(false);
	}

	/**
	 * creates the available entries group
	 */
	protected void createAvailableToolsGroup() {
		Composite parent = (Composite) getControl();
		Composite availableToolsComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		availableToolsComposite.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		availableToolsComposite.setLayoutData(data);

		Label label = new Label(availableToolsComposite, SWT.NONE);
		label.setText(Messages.Local_Palette_Available_Tools);
		data = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		label.setLayoutData(data);

		ToolBar toolbar = new ToolBar(availableToolsComposite, SWT.HORIZONTAL);
		data = new GridData(SWT.RIGHT, SWT.FILL, false, false);
		toolbar.setLayoutData(data);
		populateAvailableToolsToolBar(toolbar);

		createProfileCombo(availableToolsComposite);

		Tree tree = new Tree(availableToolsComposite, SWT.SINGLE | SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		data.widthHint = 350;
		// Make the tree this tall even when there is nothing in it. This will keep the
		// dialog from shrinking to an unusually small size.
		data.heightHint = 200;
		tree.setLayoutData(data);
		availableToolsViewer = new TreeViewer(tree);
		availableToolsViewer.setContentProvider(new UMLToolsTreeContentProvider());
		paletteLabelProvider = new PaletteLabelProvider();
		availableToolsViewer.setLabelProvider(paletteLabelProvider);
		ViewerComparator labelComparator = new LabelViewerComparator();
		availableToolsViewer.setComparator(labelComparator);
		// remove the note stack and standard group
		availableToolsViewer.addFilter(new ViewerFilter() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
				boolean visible = true;
				if (element instanceof PaletteStack && "noteStack".equals(((PaletteStack) element).getId())) {//$NON-NLS-1$
					visible = false;
				} else if (element instanceof PaletteToolbar && "standardGroup".equals(((PaletteToolbar) element).getId())) {//$NON-NLS-1$
					visible = false;
				}
				return visible;
			}
		});
		availableToolsViewer.addFilter(new DrawerFilter());

		// add drag support
		addAvailableToolsDragSupport();

		// init availableTools combo on the first element
		profileCombo.deselectAll();
		profileCombo.select(profileCombo.getItems().length - 1); // select the last one (UML)
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
	protected ToolItem createCheckToolBarItem(final ToolBar toolbar, final String shownElementsIcon, final String tooltip, final Listener listener) {
		ToolItem item = new ToolItem(toolbar, SWT.CHECK | SWT.BORDER);
		item.setImage(Activator.getDefault().getImage(shownElementsIcon));
		item.setToolTipText(tooltip);
		item.addListener(SWT.Selection, listener);
		return item;
	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createControl(final Composite parent) {

		// initialize dialog units
		initializeDialogUnits(parent);

		// Create a new composite as there is the title bar seperator
		// to deal with
		Composite control = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		control.setLayout(layout);
		control.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setControl(control);

		// create Available Tools Group
		createAvailableToolsGroup();

		// create add/remove buttons
		createAddRemoveButtons();

		// Create palette preview
		createPalettePreviewGroup();

		// Create properties view
		createPropertiesGroup();

		// add listeners inter-groups
		ISelectionChangedListener listener = createToolsViewerSelectionChangeListener();
		availableToolsViewer.addSelectionChangedListener(listener);
		paletteTreeViewer.addSelectionChangedListener(listener);

		// end of the control creation
		Dialog.applyDialogFont(control);

		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setPageComplete(validatePage());
	}

	/**
	 * Creates the listener for the new drawer tool item
	 *
	 * @return the listener created
	 */
	protected Listener createNewDrawerListener() {
		return new Listener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(final Event event) {

				EObject palette = editingDomain.getResourceSet().getResources().get(0).getContents().get(0);
				if (palette instanceof PaletteConfiguration) {
					// Create a void drawer
					DrawerConfiguration drawer = PaletteconfigurationFactory.eINSTANCE.createDrawerConfiguration();
					// Set label
					drawer.setLabel(DRAWER_LABEL);
					// Set the drawer on the palette configuration
					EList<DrawerConfiguration> drawerConfigurations = ((PaletteConfiguration) palette).getDrawerConfigurations();
					// Create add command
					Command addCommand = new AddCommand(editingDomain, drawerConfigurations, drawer);
					// Execute add command
					execute(addCommand);
					paletteTreeViewer.refresh();
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
			public void handleEvent(final Event event) {
				// retrieve selected element
				Object object = ((IStructuredSelection) paletteTreeViewer.getSelection()).getFirstElement();
				// Create a separator
				SeparatorConfiguration separator = PaletteconfigurationFactory.eINSTANCE.createSeparatorConfiguration();
				// Set label & Id
				separator.setLabel(SEPARATOR_LABEL);
				separator.setId(CreatePaletteItemUtil.generateID(SEPARATOR_LABEL));
				Command addCommand = null;
				if (object instanceof DrawerConfiguration) {
					// Add separator at the end of drawer own configuration
					addCommand = new AddCommand(editingDomain, ((DrawerConfiguration) object).getOwnedConfigurations(), separator);
				} else if (object instanceof ChildConfiguration) {
					// retrieve parent
					EList<ChildConfiguration> ownedConfigurations = ((DrawerConfiguration) ((ChildConfiguration) object).eContainer()).getOwnedConfigurations();
					int index = ownedConfigurations.indexOf(object);
					// adds the separator
					addCommand = new AddCommand(editingDomain, ownedConfigurations, separator, index);
				}
				// Execute add command
				execute(addCommand);

				paletteTreeViewer.expandToLevel(object, 1);
				paletteTreeViewer.refresh();
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
			public void handleEvent(final Event event) {
				// retrieve selected element
				Object object = ((IStructuredSelection) paletteTreeViewer.getSelection()).getFirstElement();
				// Create a separator
				StackConfiguration stack = PaletteconfigurationFactory.eINSTANCE.createStackConfiguration();
				// Set label & Id
				stack.setLabel(STACK_LABEL);
				stack.setId(CreatePaletteItemUtil.generateID(STACK_LABEL));
				Command addCommand = null;
				if (object instanceof DrawerConfiguration) {
					// Add separator at the end of drawer own configuration
					addCommand = new AddCommand(editingDomain, ((DrawerConfiguration) object).getOwnedConfigurations(), stack);
				} else if (object instanceof ChildConfiguration) {
					// retrieve parent
					EList<ChildConfiguration> ownedConfigurations = ((DrawerConfiguration) ((ChildConfiguration) object).eContainer()).getOwnedConfigurations();
					int index = ownedConfigurations.indexOf(object);
					// adds the stack
					addCommand = new AddCommand(editingDomain, ownedConfigurations, stack, index);
				}
				execute(addCommand);

				paletteTreeViewer.expandToLevel(object, 1);
				paletteTreeViewer.refresh();
			}

		};
	}

	/**
	 * Execute command in the command stack.
	 */
	protected void execute(final Command command) {
		if (null != command && command.canExecute()) {
			editingDomain.getCommandStack().execute(command);
		}
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
				Object object = ((IStructuredSelection) paletteTreeViewer.getSelection()).getFirstElement();
				// Create a separator
				ToolConfiguration tool = PaletteconfigurationFactory.eINSTANCE.createToolConfiguration();
				// Set label & Id
				tool.setLabel(NEW_TOOL_LABEL);
				Command addCommand = null;
				if (object instanceof DrawerConfiguration || object instanceof StackConfiguration) {
					// Add separator at the end of drawer own configuration
					if (object instanceof DrawerConfiguration) {
						addCommand = new AddCommand(editingDomain, ((DrawerConfiguration) object).getOwnedConfigurations(), tool);
					} else if (object instanceof StackConfiguration) {
						addCommand = new AddCommand(editingDomain, ((StackConfiguration) object).getOwnedConfigurations(), tool);
					}
				} else if (object instanceof ChildConfiguration) {
					// retrieve parent
					EObject eContainer = ((EObject) object).eContainer();
					// add as sibling of the object
					if (eContainer instanceof DrawerConfiguration) {
						EList<ChildConfiguration> ownedConfigurations = ((DrawerConfiguration) eContainer).getOwnedConfigurations();
						int index = ownedConfigurations.indexOf(object);
						addCommand = new AddCommand(editingDomain, ownedConfigurations, tool, index);
					} else if (eContainer instanceof StackConfiguration) {
						EList<LeafConfiguration> ownedConfigurations = ((StackConfiguration) eContainer).getOwnedConfigurations();
						int index = ownedConfigurations.indexOf(object);
						addCommand = new AddCommand(editingDomain, ownedConfigurations, tool, index);
					}
				}
				execute(addCommand);
				paletteTreeViewer.expandToLevel(object, 1);
				paletteTreeViewer.refresh();
			}
		};
	}

	/**
	 * creates the palette preview group
	 */
	protected void createPalettePreviewGroup() {
		Composite parent = (Composite) getControl();
		Composite paletteComposite = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout(2, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		paletteComposite.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		paletteComposite.setLayoutData(data);

		Label label = new Label(paletteComposite, SWT.NONE);
		label.setText(Messages.Local_Palette_Palette_Preview);
		data = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		label.setLayoutData(data);

		toolbar = new ToolBar(paletteComposite, SWT.HORIZONTAL);
		data = new GridData(SWT.RIGHT, SWT.FILL, false, false);
		toolbar.setLayoutData(data);
		populatePalettePreviewToolBar(toolbar);

		Tree tree = new Tree(paletteComposite, SWT.SINGLE | SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		data.widthHint = 350;
		// Make the tree this tall even when there is nothing in it. This will keep the
		// dialog from shrinking to an unusually small size.
		data.heightHint = 200;
		tree.setLayoutData(data);

		paletteTreeViewer = new TreeViewer(tree);

		paletteTreeViewer.setContentProvider(new AdapterFactoryContentProvider(editingDomain.getAdapterFactory()));
		paletteTreeViewer.setLabelProvider(new AdapterFactoryLabelProvider(editingDomain.getAdapterFactory()));
		paletteTreeViewer.setInput(editingDomain.getResourceSet());

		paletteTreeViewer.setSelection(new StructuredSelection(editingDomain.getResourceSet().getResources().get(0)), true);

		new AdapterFactoryTreeEditor(paletteTreeViewer.getTree(), editingDomain.getAdapterFactory());

		paletteTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handlePalettePreviewSelectionChanged(event);
			}
		});

		// Refresh button
		handlePalettePreviewSelectionChanged(null);
		addPalettePreviewDropSupport();
		addPalettePreviewDragSupport();
	}

	/**
	 * Creates the profile combo
	 *
	 * @param availableToolsComposite
	 *            the available tools composite
	 * @return the created combo
	 */
	protected Combo createProfileCombo(final Composite availableToolsComposite) {
		// retrieve top package, to know which profiles are available
		// creates the combo
		profileCombo = new Combo(availableToolsComposite, SWT.BORDER | SWT.READ_ONLY);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		profileCombo.setLayoutData(data);

		// retrieve all applied profiles
		List<Profile> profiles = getAllAppliedProfiles();

		int profileNumber = profiles.size();
		for (int i = 0; i < profileNumber; i++) {
			String name;
			if (profiles.get(i).eIsProxy()) {
				name = NLS.bind("<unresolved: {0}>", URI.decode(EcoreUtil.getURI(profiles.get(i)).lastSegment())); //$NON-NLS-1$
			} else {
				name = profiles.get(i).getName();
				if (name == null) {
					name = NLS.bind("<unnamed: {0}>", URI.decode(EcoreUtil.getURI(profiles.get(i)).lastSegment())); //$NON-NLS-1$
				}
			}
			profileComboList.add(i, name);
		}
		profileComboList.add(UML_TOOLS_LABEL);
		profileCombo.setItems(profileComboList.toArray(new String[] {}));

		// add selection listener for the combo. selects the "UML tools" item
		ProfileComboSelectionListener listener = new ProfileComboSelectionListener();
		profileCombo.addSelectionListener(listener);
		profileCombo.addModifyListener(listener);

		return profileCombo;
	}

	/**
	 * create the properties group
	 */
	protected void createPropertiesGroup() {
		Composite parent = (Composite) getControl();

		Label entryInformationLabel = new Label(parent, SWT.NONE);
		entryInformationLabel.setText(Messages.Local_Palette_Entry_Information);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
			data.horizontalSpan = 3;
			entryInformationLabel.setLayoutData(data);
		}

		propertiesComposite = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		propertiesComposite.setLayout(layout);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalSpan = 3;
			data.heightHint = 340;
			propertiesComposite.setLayoutData(data);
		}

	}

	/**
	 * Creates the add button listener.
	 */
	protected MouseListener createRemoveButtonListener() {
		return new MouseAdapter() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void mouseUp(final MouseEvent e) {
				deleteSelectedConfiguration();
			}
		};
	}

	/**
	 * Creates the listener for the remove item(s) button.
	 *
	 * @return the listener for the remove button
	 */
	protected Listener createRemoveElementListener() {
		return new Listener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(final Event event) {
				deleteSelectedConfiguration();
			}

		};
	}

	/**
	 * Delete the selection configuration in the paletteTreeViewer.
	 */
	protected void deleteSelectedConfiguration() {
		IStructuredSelection selection = (IStructuredSelection) paletteTreeViewer.getSelection();
		if (null != selection && 1 <= selection.size()) {
			Iterator<?> it = selection.iterator();
			while (it.hasNext()) {
				Object object = it.next();
				if (object instanceof Configuration) {
					Command deleteCommand = new AbstractCommand() {

						@Override
						public void redo() {
							execute();
						}

						@Override
						public void execute() {
							EcoreUtil.delete((EObject) object);
						}

						@Override
						public boolean prepare() {
							return true;
						}
					};
					deleteCommand.canExecute();
					editingDomain.getCommandStack().execute(deleteCommand);
				}
			}
			paletteTreeViewer.refresh();
			// remove the properties view
			if (null != displayEngine) {
				displayEngine.removeSection(propertiesComposite);
			}
		}
	}

	/**
	 * creates the tool item for drawers visibility listener
	 *
	 * @return the listener for the tool button
	 */
	protected Listener createShowDrawerListener() {
		return new Listener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(final Event event) {
				if (event.widget instanceof ToolItem) {
					ToolItem item = ((ToolItem) event.widget);
					if (item.getSelection()) {
						// elements should be hidden
						availableToolsViewer.addFilter(drawerFilter);
						item.setSelection(true);
					} else {
						availableToolsViewer.removeFilter(drawerFilter);
						item.setSelection(false);
					}
				}
			}
		};
	}

	/**
	 * creates the tool item for tools visibility listener
	 *
	 * @return the listener for the tool button
	 */
	protected Listener createsShowToolListener() {
		return new Listener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(final Event event) {
				if (event.widget instanceof ToolItem) {
					ToolItem item = ((ToolItem) event.widget);
					if (item.getSelection()) {
						// elements should be hidden
						availableToolsViewer.addFilter(toolFilter);
						item.setSelection(true);
					} else {
						availableToolsViewer.removeFilter(toolFilter);
						item.setSelection(false);
					}
				}
			}
		};
	}

	/**
	 * Creates the listener for the available tools content provider
	 *
	 * @return the listener created
	 */
	protected Listener createSwitchToolsContentProviderListener() {
		return new Listener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void handleEvent(final Event event) {
				if (event.widget instanceof ToolItem) {
					ToolItem item = ((ToolItem) event.widget);
					// retrieve current profile selected in the combo profile
					int index = profileCombo.getSelectionIndex();
					Collection<PaletteEntry> standardEntries = getAllVisibleStandardEntries();
					Profile profile = getAllAppliedProfiles().get(index);

					if (item.getSelection()) {
						availableToolsViewer.setContentProvider(new ProfileToolsStereotypeMetaclassTreeContentProvider(profile, standardEntries));
						item.setSelection(true);
					} else {

						availableToolsViewer.setContentProvider(new ProfileToolsMetaclassStereotypeTreeContentProvider(profile, standardEntries));
						item.setSelection(false);
					}

					// generate tools for given profile
					availableToolsViewer.setInput(profile);
				}
			}
		};
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
	protected void createToolBarItem(final ToolBar toolbar, final String itemIcon, final String tooltip, final Listener listener, final ToolBarItemValidator validator) {
		ToolItem item = new ToolItem(toolbar, SWT.BORDER);
		item.setImage(Activator.getDefault().getImage(org.eclipse.papyrus.infra.gmfdiag.common.Activator.ID, itemIcon));
		item.setToolTipText(tooltip);
		item.addListener(SWT.Selection, listener);
		item.setData(VALIDATOR, validator);
	}

	/**
	 * selection listener for the tools viewer , to update the state of the add button
	 *
	 * @return the new created selection listener
	 */
	protected ISelectionChangedListener createToolsViewerSelectionChangeListener() {
		return new ISelectionChangedListener() {

			/**
			 * Returns true if the source can be added to the target
			 *
			 * @param source
			 *            the source object
			 * @param target
			 *            the target object
			 * @return <code>true</code> if the source can be added to the target
			 */
			protected boolean isAddValidTarget(final Object source, final Object target) {
				boolean isAddValidTarget = false;
				if (source instanceof PaletteEntry) {
					// case1: source is a drawer.
					// it can only be added to the root element (no selection)
					// case2: source is a palette tool
					// it can't be added to the root element
					// it can only be added to a container element (drawer or stack)

					if (source instanceof PaletteDrawer) {
						isAddValidTarget = null == target;
					} else if (source instanceof ToolEntry) {
						isAddValidTarget = target instanceof DrawerConfiguration || target instanceof StackConfiguration;
					}
				}
				return isAddValidTarget;
			}

			/**
			 * Returns true if the source can be added to the target
			 *
			 * @param source
			 *            the source object
			 * @return <code>true</code> if the source can be removed (not null and instanceof
			 *         PaletteEntryProxy)
			 */
			protected boolean isRemoveValidSource(final Object source) {
				return source instanceof Configuration;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				// get source and target selection
				// check source entry can be added to target entry
				Object source = ((IStructuredSelection) availableToolsViewer.getSelection()).getFirstElement();
				Object target = ((IStructuredSelection) paletteTreeViewer.getSelection()).getFirstElement();

				// manage add button
				if (isAddValidTarget(source, target)) {
					addButton.setEnabled(true);
				} else {
					addButton.setEnabled(false);
				}

				// manage remove button
				if (isRemoveValidSource(target)) {
					removeButton.setEnabled(true);
				} else {
					removeButton.setEnabled(false);
				}

			}
		};

	}

	/**
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 *
	 */
	@Override
	public void dispose() {
		// dispose engine
		if (null != displayEngine) {
			displayEngine.dispose();
		}
		// dispose adapter
		paletteConfigurationModel.eAdapters().remove(adapter);
		super.dispose();
	}

	/**
	 * returns the list of applied profile for the nearest package of the top element
	 *
	 * @return the list of applied profile for the nearest package of the top element or an empty
	 *         list
	 */
	protected List<Profile> getAllAppliedProfiles() {
		Package topPackage = null;
		if (editorPart instanceof DiagramEditorWithFlyOutPalette) {
			EObject element = ((DiagramEditorWithFlyOutPalette) editorPart).getDiagram().getElement();
			if (element instanceof org.eclipse.uml2.uml.Element) {
				topPackage = ((org.eclipse.uml2.uml.Element) element).getNearestPackage();
			}
		}
		return null != topPackage ? topPackage.getAllAppliedProfiles() : Collections.emptyList();
	}

	/**
	 * Returns the list of all visible palette entries
	 *
	 * @return the list of all visible palette entries
	 */
	protected Collection<PaletteEntry> getAllVisibleStandardEntries() {
		HashSet<PaletteEntry> result = new HashSet<PaletteEntry>();
		for (PaletteEntry entry : PaletteUtil.getAvailableEntriesSet(editorPart, priority).values()) {
			// the entry is not just a defineOnly entry but a visible one
			if (getRootParent(entry) != null) {
				result.add(entry);
			}
		}
		return result;
	}

	/**
	 * Returns the Root element for the palette entry. It searches recursively from parent to parent, until it find the root element
	 *
	 * @param entry
	 *            the palette entry for which root element is searched
	 * @return the root element or <code>null</code> if none was found
	 */
	protected PaletteRoot getRootParent(final PaletteEntry entry) {
		PaletteRoot paletteRoot = null;

		PaletteContainer parent = entry.getParent();
		if (parent instanceof PaletteRoot) {
			paletteRoot = (PaletteRoot) parent;
		} else if (null != parent) {
			paletteRoot = getRootParent(parent);
		}
		return paletteRoot;
	}

	/**
	 * handle the selection change event for the palette preview
	 *
	 * @param event
	 *            the event that is thrown by the palette viewer
	 */
	protected void handlePalettePreviewSelectionChanged(final SelectionChangedEvent event) {
		// Properties view
		if (null != event) {
			// retrieve current selection
			ITreeSelection selection = (TreeSelection) event.getSelection();
			Object firstSelected = selection.getFirstElement();

			if (firstSelected instanceof Configuration) {
				if (null != displayEngine) {
					displayEngine.removeSection(propertiesComposite);
				}
				displayEngine = PropertiesDisplayHelper.display(firstSelected, propertiesComposite);
				propertiesComposite.layout();
			}
		}
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
	 * Adds elements to the tool bar for available tools viewer
	 *
	 * @param toolbar
	 *            the toolbar to populate
	 */
	protected void populateAvailableToolsToolBar(final ToolBar toolbar) {
		toggleContentProvider = createCheckToolBarItem(toolbar, SWITCH_CONTENT_PROVIDER_ICON, Messages.Local_Palette_SwitchToolsContentProvider_Tooltip, createSwitchToolsContentProviderListener());
		toggleContentProvider.setSelection(true);
		toggleContentProvider.setEnabled(false);
		createCheckToolBarItem(toolbar, SHOWN_TOOLS_ICON, Messages.Local_Palette_ShowTools_Tooltip, createsShowToolListener());
	}

	/**
	 * populates the preview palette toolbar
	 *
	 * @param toolbar
	 *            the toolbar to populate
	 */
	protected void populatePalettePreviewToolBar(final ToolBar toolbar) {
		// create validator
		PaletteConfigurationEntrySelectedValidator validator = new PaletteConfigurationEntrySelectedValidator();
		PaletteConfigurationStackLeafSelectedValidator validatorForNotStackChild = new PaletteConfigurationStackLeafSelectedValidator();

		createToolBarItem(toolbar, DELETE_ICON, Messages.PapyrusPaletteCustomizerDialog_RemoveButtonTooltip, createRemoveElementListener(), validator);
		createToolBarItem(toolbar, CREATE_DRAWERS_ICON, Messages.Local_Palette_Create_Drawer_Tooltip, createNewDrawerListener(), null);
		createToolBarItem(toolbar, CREATE_SEPARATOR_ICON, Messages.Local_Palette_Create_Separator_Tooltip, createNewSeparatorListener(), validatorForNotStackChild);
		createToolBarItem(toolbar, CREATE_TOOL_ICON, Messages.Local_Palette_Create_Tool_Tooltip, createNewToolListener(), validator);
		createToolBarItem(toolbar, CREATE_STACK_ICON, Messages.Local_Palette_Create_Stack_Tooltip, createNewStackListener(), validatorForNotStackChild);
	}

	/**
	 * Sets the priority of the current edited palette
	 *
	 * @param priority
	 *            the priority of the current edited palette
	 */
	public void setPriority(final ProviderPriority priority) {
		this.priority = priority;
	}

	/**
	 * @return the priority
	 */
	public ProviderPriority getPriority() {
		return priority;
	}

	/** The model diagnostician. */
	Diagnostician diagnostician = new Diagnostician() {

		@Override
		public String getObjectLabel(final EObject eObject) {
			String label = null;
			if (eObject instanceof Configuration) {
				label = ((Configuration) eObject).getLabel();
			}
			if (null == label || label.isEmpty()) {
				label = EcoreUtil.getIdentification(eObject);
			}
			return label;
		}
	};

	/**
	 * Validates the content of the fields in this page.
	 */
	protected boolean validatePage() {
		// TODO Adds OCL validation to Configuration to have error if Id is empty.
		Diagnostic validate = diagnostician.validate(paletteConfigurationModel);
		boolean valid = Diagnostic.OK == validate.getSeverity();

		if (!valid) {
			setErrorMessage(validate.getChildren().get(0).getMessage());
		} else {
			setErrorMessage(null);
		}
		return valid;
	}

	/**
	 * @return The {@link PaletteConfiguration} model from resource set.
	 */
	protected PaletteConfiguration getPaletteConfigurationModel() {
		PaletteConfiguration paletteModel = null;
		EList<Resource> resources = editingDomain.getResourceSet().getResources();
		if (!resources.isEmpty()) {
			EList<EObject> contents = resources.get(0).getContents();
			if (!contents.isEmpty()) {
				EObject eObject = (EObject) contents.get(0);
				if (eObject instanceof PaletteConfiguration) {
					paletteModel = (PaletteConfiguration) eObject;
				}
			}
		}
		Assert.isNotNull(paletteModel);
		return paletteModel;
	}

}
