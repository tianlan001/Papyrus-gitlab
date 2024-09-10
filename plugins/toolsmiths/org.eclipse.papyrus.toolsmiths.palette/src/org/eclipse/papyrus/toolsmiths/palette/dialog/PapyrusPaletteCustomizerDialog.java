/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
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
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@gmail.com	- Bug 350134
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 482669
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.palette.dialog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteCustomizer;
import org.eclipse.gmf.runtime.common.core.service.IProvider;
import org.eclipse.gmf.runtime.gef.ui.palette.customize.PaletteCustomizerDialogEx;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.ExtendedPluginPaletteProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.IPapyrusPaletteConstant;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPalettePreferences;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.ExtendedProviderDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.ProviderDescriptor;
import org.eclipse.papyrus.toolsmiths.palette.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;

/**
 * Specific dialog window for the customisation of the palette.
 */
public class PapyrusPaletteCustomizerDialog extends PaletteCustomizerDialogEx implements IPreferenceChangeListener {

	/** Id for restore button of extended customization palette. */
	private static final int RESTORE_DEFAULT_PALETTE_BUTTON_ID = 23;

	/** Id for restore button of extended customization palette. */
	private static final int EXPORT_DEFAULT_PALETTE_BUTTON_ID = 24;

	/** Id for delete button of local palette. */
	private static final int DELETE_PALETTE_BUTTON_ID = 22;

	/** Id for edit button of extended and local palette. */
	private static final int EDIT_PALETTE_BUTTON_ID = 21;

	/** Id for new button to create local extended palette. */
	private static final int NEW_EXTENDED_PALETTE_BUTTON_ID = 25;

	/** new local palette icon */
	private static final String NEW_LOCAL_DESC_IMAGE = "/icons/local_desc_new.gif"; //$NON-NLS-1$

	/** delete palette icon */
	private static final String DELETE_LOCAL_DESC_IMAGE = "/icons/local_desc_destroy.gif"; //$NON-NLS-1$

	/** edit palette icon */
	private static final String EDIT_LOCAL_DESC_IMAGE = "/icons/local_desc_edit.gif"; //$NON-NLS-1$

	/** restore palette icon */
	private static final String RESTORE_DEFAULT_DESC_IMAGE = "/icons/eraser.gif"; //$NON-NLS-1$

	/** restore palette icon */
	private static final String EXPORT_DEFAULT_DESC_IMAGE = "/icons/Export.gif"; //$NON-NLS-1$

	/** path to the local descriptor icon */
	protected static final String LOCAL_DESCRIPTOR = "/icons/local_desc.gif"; //$NON-NLS-1$

	/** path to the plugin descriptor icon */
	protected static final String PLUGIN_DESCRIPTOR = "/icons/plugin_desc.gif"; //$NON-NLS-1$

	/** path to the plugin descriptor icon */
	protected static final String EXTENDED_PLUGIN_DESCRIPTOR = "/icons/extended_plugin_desc.gif"; //$NON-NLS-1$

	public static final String DEFAULT_IMAGE = "icons/PapyrusLogo16x16.gif";//$NON-NLS-1$

	/** table viewed by the availablePalettesTreeViewer */
	protected Table availablePalettesTable;

	/** viewer for the available palettes */
	protected CheckboxTableViewer availablePalettesTableViewer;

	/** label provider for palette provider */
	protected PaletteLabelProvider providersLabelProvider;

	private Map<Integer, Button> paletteButtonsMap = new HashMap<Integer, Button>();

	/**
	 * Creates a new PapyrusPaletteCustomizerDialog
	 *
	 * @param shell
	 *            the shell that hosts the dialog window
	 * @param customizer
	 *            the customizer used to customize the palette
	 * @param root
	 *            the root of the palette
	 */
	public PapyrusPaletteCustomizerDialog(Shell shell, PaletteCustomizer customizer, PaletteRoot root) {
		super(shell, customizer, root);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int open() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.ID);
		prefs.addPreferenceChangeListener(this);
		return super.open();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean close() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(Activator.ID);
		prefs.removePreferenceChangeListener(this);
		return super.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		// RS: does not call super, as the composite should not be drawn like they are in parent
		Composite mainComposite = createMainComposite(parent);

		final Control availableToolsComposite = createAvailablePalettesViewer(mainComposite);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		// data.right = new FormAttachment(40, 0);
		availableToolsComposite.setLayoutData(data);

		final Label nameLabel = new Label(mainComposite, SWT.NONE);
		nameLabel.setText(Messages.Palette_Viewer);
		data = new FormData();
		data.left = new FormAttachment(availableToolsComposite, ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(0, 0);
		nameLabel.setLayoutData(data);

		// Create the tree
		Control outline = createOutline(mainComposite);
		data = new FormData();
		data.left = new FormAttachment(availableToolsComposite, ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(nameLabel, 0);
		data.bottom = new FormAttachment(100, 0);
		// data.right = new FormAttachment(90, 0);
		outline.setLayoutData(data);

		// Create the panel where the properties of the selected palette entry will
		// be shown
		Control properties = createPropertiesPanel(mainComposite);
		data = new FormData();
		data.left = new FormAttachment(outline, ITabbedPropertyConstants.HSPACE);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		properties.setLayoutData(data);

		// add listeners
		ISelectionChangedListener listener = createSelectionChangedListener();
		if (listener != null) {
			availablePalettesTableViewer.addSelectionChangedListener(listener);
		}
		return mainComposite;
	}

	/**
	 * the selection changed listener to available tools, which update buttons status
	 * 
	 * @return the listener
	 */
	protected ISelectionChangedListener createSelectionChangedListener() {
		return event -> {
			// retrieve element selected
			Object selectedElement = ((IStructuredSelection) event.getSelection()).getFirstElement();

			// Plug-in contribution palette
			if (selectedElement instanceof PapyrusPaletteService.ExtendedProviderDescriptor) {
				IProvider provider = ((PapyrusPaletteService.ExtendedProviderDescriptor) selectedElement).getProvider();

				// Modelconfiguration model definition
				if (provider instanceof ExtendedPluginPaletteProvider) {
					paletteButtonsMap.get(DELETE_PALETTE_BUTTON_ID).setEnabled(false);
					paletteButtonsMap.get(EDIT_PALETTE_BUTTON_ID).setEnabled(true);
					paletteButtonsMap.get(RESTORE_DEFAULT_PALETTE_BUTTON_ID).setEnabled(isRedefined((ProviderDescriptor) selectedElement));
					paletteButtonsMap.get(EXPORT_DEFAULT_PALETTE_BUTTON_ID).setEnabled(true);
				}
			} else
			// Local Palette configuration model
			if (selectedElement instanceof PapyrusPaletteService.LocalExtendedProviderDescriptor) {
				paletteButtonsMap.get(DELETE_PALETTE_BUTTON_ID).setEnabled(true);
				paletteButtonsMap.get(EDIT_PALETTE_BUTTON_ID).setEnabled(true);
				paletteButtonsMap.get(RESTORE_DEFAULT_PALETTE_BUTTON_ID).setEnabled(false);
				paletteButtonsMap.get(EXPORT_DEFAULT_PALETTE_BUTTON_ID).setEnabled(true);
			} else {
				paletteButtonsMap.get(DELETE_PALETTE_BUTTON_ID).setEnabled(false);
				paletteButtonsMap.get(EDIT_PALETTE_BUTTON_ID).setEnabled(false);
				paletteButtonsMap.get(RESTORE_DEFAULT_PALETTE_BUTTON_ID).setEnabled(false);
				paletteButtonsMap.get(EXPORT_DEFAULT_PALETTE_BUTTON_ID).setEnabled(false);
			}
		};
	}

	/**
	 * Creates the available palettes viewer part of the dialog.
	 *
	 * @param container
	 *            The Composite within which the viewer has to be created
	 * @return The newly created Control that has the viewer
	 */
	protected Control createAvailablePalettesViewer(final Composite container) {
		// Create the Composite that will contain the available tools
		Composite composite = new Composite(container, SWT.NONE);
		composite.setFont(container.getFont());
		GridLayout layout = new GridLayout(6, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);

		final Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText(Messages.Available_Palettes);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		nameLabel.setLayoutData(data);

		// Create action buttons
		createEditionPaletteButton(composite, NEW_EXTENDED_PALETTE_BUTTON_ID, Activator.getInstance().getBundledImage(NEW_LOCAL_DESC_IMAGE), Messages.Dialog_Create_Palette_Tooltip);
		createEditionPaletteButton(composite, EDIT_PALETTE_BUTTON_ID, Activator.getInstance().getBundledImage(EDIT_LOCAL_DESC_IMAGE), Messages.Dialog_Edit_Palette_Tooltip);
		createEditionPaletteButton(composite, DELETE_PALETTE_BUTTON_ID, Activator.getInstance().getBundledImage(DELETE_LOCAL_DESC_IMAGE), Messages.Dialog_Delete_Palette_Tooltip);
		createEditionPaletteButton(composite, RESTORE_DEFAULT_PALETTE_BUTTON_ID, Activator.getInstance().getBundledImage(RESTORE_DEFAULT_DESC_IMAGE), Messages.Dialog_Restore_Palette_Tooltip);
		createEditionPaletteButton(composite, EXPORT_DEFAULT_PALETTE_BUTTON_ID, Activator.getInstance().getBundledImage(EXPORT_DEFAULT_DESC_IMAGE), Messages.Dialog_Export_Palette_Tooltip);

		// initialize it
		paletteButtonsMap.get(DELETE_PALETTE_BUTTON_ID).setEnabled(false);
		paletteButtonsMap.get(EDIT_PALETTE_BUTTON_ID).setEnabled(false);
		paletteButtonsMap.get(RESTORE_DEFAULT_PALETTE_BUTTON_ID).setEnabled(false);
		paletteButtonsMap.get(EXPORT_DEFAULT_PALETTE_BUTTON_ID).setEnabled(false);

		availablePalettesTable = new Table(composite, SWT.BORDER | SWT.CHECK);
		availablePalettesTable.setFont(composite.getFont());
		availablePalettesTable.addSelectionListener(new SelectionListener() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (e.detail == SWT.CHECK) {
					TableItem item = (TableItem) e.item;
					// one item was checked => display/hide the given provider
					changeProviderVisibility((PapyrusPaletteService.ProviderDescriptor) item.getData(), item.getChecked());
				}
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// does nothing
			}
		});
		data = new GridData(GridData.FILL_VERTICAL | GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 6;
		data.widthHint = 185;
		// Make the tree this tall even when there is nothing in it. This will keep the
		// dialog from shrinking to an unusually small size.
		data.heightHint = 200;
		availablePalettesTable.setLayoutData(data);
		availablePalettesTableViewer = new CheckboxTableViewer(availablePalettesTable);
		AvailablePalettesCheckStateProvider availablePalettesCheckStateProvider = new AvailablePalettesCheckStateProvider();
		availablePalettesTableViewer.setCheckStateProvider(availablePalettesCheckStateProvider);
		availablePalettesTableViewer.setContentProvider(new PalettesTableContentProvider(availablePalettesTableViewer));
		providersLabelProvider = new PaletteLabelProvider(availablePalettesTableViewer);
		availablePalettesTableViewer.setLabelProvider(providersLabelProvider);
		availablePalettesTableViewer.setInput(PapyrusPaletteService.getInstance());
		availablePalettesTableViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				// get selection. if local palette: open the wizard to edit this local palette
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				editSelectedPalette(selection);
			}
		});

		return composite;
	}

	/**
	 * Create button to edit palette in table.
	 *
	 * @param parentConposite
	 * @return the edition button
	 */
	protected Button createEditionPaletteButton(final Composite parentConposite, final int id, final Image icon, final String toolTip) {
		Button button = new Button(parentConposite, SWT.NONE);
		button.setData(new Integer(id));
		button.setImage(icon);
		button.setToolTipText(toolTip);
		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(final MouseEvent e) {
				buttonPressed(((Integer) e.widget.getData()).intValue());
			}

		});

		// Register created button
		paletteButtonsMap.put(new Integer(id), button);

		return button;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param
	 */
	@Override
	protected void buttonPressed(final int buttonId) {
		if (paletteButtonsMap.containsKey(buttonId)) {

			switch (buttonId) {
			case NEW_EXTENDED_PALETTE_BUTTON_ID:
				createPaletteConfigurationPalette();
				break;
			case EDIT_PALETTE_BUTTON_ID: {
				IStructuredSelection selection = getPalettesTableSelection();
				editSelectedPalette(selection);
			}
				break;
			case DELETE_PALETTE_BUTTON_ID: {
				IStructuredSelection selection = getPalettesTableSelection();
				deleteSelectedPalette(selection);
			}
				break;
			case RESTORE_DEFAULT_PALETTE_BUTTON_ID:
				restoreExtendedPaletteToDefault();
				break;

			case EXPORT_DEFAULT_PALETTE_BUTTON_ID: {
				IStructuredSelection selection = getPalettesTableSelection();
				exportSelectedPalette(selection);
				break;
			}
			default:
				break;
			}
		} else {
			super.buttonPressed(buttonId);
		}
	}

	/**
	 * Open wizard to export palette configuration model file to the wanted location
	 * 
	 * @param selection
	 *            the selected palette
	 */
	protected void exportSelectedPalette(final IStructuredSelection selection) {
		/*
		 * Two palettes type to export:
		 * - ExtendedProviderDescriptor : plug-in definition : needs to create local file if not exist
		 * - LocalExtendedProviderDescriptor : local definition
		 */

		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof PapyrusPaletteService.ExtendedProviderDescriptor) {

			if (!isRedefined((ExtendedProviderDescriptor) firstElement)) {
				// create a local redefinition of this palette contribution
				PapyrusPalettePreferences.createPaletteRedefinition((ExtendedProviderDescriptor) firstElement);
			}
			exportPalette((PapyrusPaletteService.ProviderDescriptor) firstElement);

		} else if (firstElement instanceof PapyrusPaletteService.LocalExtendedProviderDescriptor) {
			exportPalette((PapyrusPaletteService.ProviderDescriptor) firstElement);
		}
	}

	/**
	 * export the palette corresponding to the descriptor. The ressource have to exist.
	 * 
	 * @param descriptor
	 */
	protected void exportPalette(final PapyrusPaletteService.ProviderDescriptor descriptor) {
		ExportPaletteConfigurationWizard wizard = new ExportPaletteConfigurationWizard(getActiveSashPage(), descriptor);
		WizardDialog wizardDialog = new WizardDialog(new Shell(), wizard);
		wizardDialog.open();
	}

	/**
	 * Get selection in palettes table viewer.
	 *
	 * @return The {@link IStructuredSelection} if there is one, otherwise <code>null</code>
	 */
	private IStructuredSelection getPalettesTableSelection() {
		IStructuredSelection structuredSelection = null;
		if (availablePalettesTableViewer != null) {
			ISelection selection = availablePalettesTableViewer.getSelection();
			if (selection instanceof IStructuredSelection) {
				structuredSelection = (IStructuredSelection) selection;
			}
		}
		return structuredSelection;
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#setButtonLayoutData(org.eclipse.swt.widgets.Button)
	 *
	 * @param button
	 */
	@Override
	protected void setButtonLayoutData(final Button button) {
		if (paletteButtonsMap.containsValue(button)) {
			GridData data = new GridData(SWT.CENTER, SWT.CENTER, false, false);
			button.setLayoutData(data);
			button.setEnabled(false);
		} else {
			super.setButtonLayoutData(button);
		}
	}

	/**
	 * Launch the wizard for the palette creation
	 *
	 * @param shell
	 *            the shell where to display the wizard
	 */
	protected void createPaletteConfigurationPalette() {
		PaletteConfigurationWizard wizard = new PaletteConfigurationWizard(getActiveSashPage());
		WizardDialog wizardDialog = new WizardDialog(new Shell(), wizard);
		wizardDialog.open();
	}

	/**
	 * Deletes the current selected local extended palette
	 */
	protected void deleteLocalExtendedPalette(final PapyrusPaletteService.LocalExtendedProviderDescriptor descriptor) {
		if (null == descriptor) {
			MessageDialog.openError(getShell(), Messages.Dialog_Not_Local_Palette_Title, Messages.Dialog_Not_Local_Palette_Message);
		} else {
			String id = descriptor.getContributionID();
			deleteAssociatedRessource(descriptor);
			PapyrusPalettePreferences.deleteLocalExtendedPalette(id);
		}
	}

	/**
	 * Deletes the current selected local palette
	 */
	protected void deleteWorkspaceExtendedPalette(final PapyrusPaletteService.LocalProviderDescriptor descriptor) {
		if (null == descriptor) {
			MessageDialog.openError(getShell(), Messages.Dialog_Not_Extended_Palette_Title, Messages.Dialog_Not_Extended_Palette_Message);
		} else {
			String id = descriptor.getContributionID();
			PapyrusPalettePreferences.deleteWorkspaceExtendedPalette(id);
		}
	}

	/**
	 * Reset the current selected extended palette to the initial configuration in the plugin
	 */
	protected void restoreExtendedPaletteToDefault() {
		IStructuredSelection selection = (IStructuredSelection) availablePalettesTableViewer.getSelection();
		if (null == selection || !(selection.getFirstElement() instanceof PapyrusPaletteService.ExtendedProviderDescriptor)) {
			MessageDialog.openError(getShell(), Messages.Dialog_Not_Extended_Palette_Title, Messages.Dialog_Not_Extended_Palette_Message);
		} else {
			PapyrusPaletteService.ExtendedProviderDescriptor descriptor = ((PapyrusPaletteService.ExtendedProviderDescriptor) selection.getFirstElement());
			String id = descriptor.getContributionID();

			deleteAssociatedRessource(descriptor);

			PapyrusPalettePreferences.unregisterLocalRedefinition(id);

			// Refresh element on Palette preview only if is checked
			if (availablePalettesTableViewer.getChecked(descriptor)) {
				// toggle visibility to refresh the content
				changeProviderVisibility(descriptor, false);
				changeProviderVisibility(descriptor, true);
			}

			paletteButtonsMap.get(RESTORE_DEFAULT_PALETTE_BUTTON_ID).setEnabled(false);
		}
	}


	/**
	 * Delete associated resource and unloads models.
	 * 
	 * @param descriptor
	 * 
	 */
	protected void deleteAssociatedRessource(ProviderDescriptor descriptor) {
		PaletteConfigurationWizard wizard = new PaletteConfigurationWizard(getActiveSashPage(), descriptor);
		wizard.deleteResource();
		wizard.dispose();
	}

	/**
	 * Method to launch edition dialog according to selection.
	 *
	 * @param selection
	 *            Selection which determine kind of edition
	 */
	protected void editSelectedPalette(IStructuredSelection selection) {
		if (null != selection) {
			Object descriptor = selection.getFirstElement();
			if (descriptor instanceof PapyrusPaletteService.WorkspaceExtendedProviderDescriptor || descriptor instanceof PapyrusPaletteService.LocalExtendedProviderDescriptor) {
				editPaletteConfiguration((ProviderDescriptor) descriptor);
			} else if (descriptor instanceof PapyrusPaletteService.ExtendedProviderDescriptor) {
				editExtendedPalette((PapyrusPaletteService.ExtendedProviderDescriptor) descriptor);
			}
		}
	}

	/**
	 * Method to delete palette according to selection.
	 *
	 * @param selection
	 *            Selection which determine kind of delectino
	 */
	protected void deleteSelectedPalette(final IStructuredSelection selection) {
		if (null != selection) {
			if (selection.getFirstElement() instanceof PapyrusPaletteService.WorkspaceExtendedProviderDescriptor) {
				deleteWorkspaceExtendedPalette((PapyrusPaletteService.LocalProviderDescriptor) selection.getFirstElement());
			} else if (selection.getFirstElement() instanceof PapyrusPaletteService.LocalExtendedProviderDescriptor) {
				deleteLocalExtendedPalette((PapyrusPaletteService.LocalExtendedProviderDescriptor) selection.getFirstElement());
			}
			availablePalettesTableViewer.refresh();
		}
	}

	/**
	 * Open wizard to edit palette configuration model.
	 * 
	 * @param descriptor
	 *            the provider descriptor of the palette
	 */
	protected void editPaletteConfiguration(final ProviderDescriptor descriptor) {
		PaletteConfigurationWizard wizard = new PaletteConfigurationWizard(getActiveSashPage(), descriptor);
		WizardDialog wizardDialog = new WizardDialog(new Shell(), wizard);
		wizardDialog.open();
	}

	/**
	 * Open wizard to edit palette configuration model for a read-only palette.
	 * 
	 * @param descriptor
	 *            the provider descriptor of the palette
	 */
	protected void editExtendedPalette(final ExtendedProviderDescriptor descriptor) {
		if (!isRedefined(descriptor)) {
			// create a local redefinition of this palette contribution
			PapyrusPalettePreferences.createPaletteRedefinition(descriptor);
		}
		editPaletteConfiguration(descriptor);
	}

	/**
	 * Test if a palette redefinition exist for the descriptor.
	 * 
	 * @param descriptor
	 *            the exttended provider desciptor to test
	 * @return true if redefined
	 */
	public Boolean isRedefined(final ProviderDescriptor descriptor) {
		// check the file in plugin state area.
		String contributionID = descriptor.getContributionID();
		String paletteRedefinition = PapyrusPalettePreferences.getPaletteRedefinition(contributionID);
		return null != paletteRedefinition;
	}


	/**
	 * Changes the visibility of the given provider
	 *
	 * @param descriptor
	 *            the provider to hide/show
	 * @param isChecked
	 *            <code>true</code> if the descriptor should be visible
	 */
	protected void changeProviderVisibility(final PapyrusPaletteService.ProviderDescriptor descriptor, final boolean isChecked) {
		PapyrusPalettePreferences.changePaletteVisibility(descriptor.getContributionID(), getActiveSashPage().getClass().getName(), isChecked);
	}

	/**
	 * Creates the main composite for the dialog area
	 *
	 * @param parent
	 *            the parent of the createrd composite
	 * @return the newly created Composite
	 */
	protected Composite createMainComposite(final Composite parent) {
		// dialog window
		// create a composite with standard margins and spacing
		Composite composite = new Composite(parent, SWT.NONE);
		FormLayout layout = new FormLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(composite);
		return composite;
	}

	/**
	 * Content provider for available tools viewer
	 */
	public class PalettesTableContentProvider implements IStructuredContentProvider {

		/** the palette root */
		private PapyrusPaletteService paletteService;

		/** tree viewer to fill */
		private final TableViewer viewer;

		/**
		 * Constructor
		 *
		 * @param tableViewer
		 *            The TableViewer whose ContentProvider this PaletteTreeProvider is
		 */
		public PalettesTableContentProvider(final TableViewer tableViewer) {
			this.viewer = tableViewer;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispose() {
			paletteService = null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
			if (newInput != null) {
				paletteService = (PapyrusPaletteService) newInput;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof PapyrusPaletteService) {
				List<PapyrusPaletteService.ProviderDescriptor> providers = ((PapyrusPaletteService) inputElement).getContributingProviders(getActiveSashPage(), getPaletteRoot());

				return providers.toArray();
			}
			return null;
		}
	}

	/**
	 * Returns the current active sash page
	 *
	 * @return the current active sash page
	 */
	protected IEditorPart getActiveSashPage() {
		// Lookup ServiceRegistry
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editorPart = page.getActiveEditor();
		assert editorPart != null;
		ISashWindowsContainer sashWindowsContainer = editorPart.getAdapter(ISashWindowsContainer.class);
		if (sashWindowsContainer != null) {
			return sashWindowsContainer.getActiveEditor();
		}
		return null;
	}

	/**
	 * provider in charge of the check boxes in the available palettes table viewer
	 */
	protected class AvailablePalettesCheckStateProvider implements ICheckStateProvider {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isChecked(final Object element) {
			boolean checked = false;
			if (element instanceof PapyrusPaletteService.ProviderDescriptor) {
				checked = !PapyrusPalettePreferences.getHiddenPalettes(getActiveSashPage()).contains(((PapyrusPaletteService.ProviderDescriptor) element).getContributionID());
			}
			return checked;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isGrayed(final Object element) {
			return false;
		}

	}

	/**
	 * Label provider for available tools viewer
	 */
	protected class PaletteLabelProvider implements ILabelProvider {

		/**
		 * Creates a new PaletteLabelProvider.
		 *
		 * @param viewer
		 *            the table viewer where the labels are displayed
		 */
		public PaletteLabelProvider(final TableViewer viewer) {
		}

		private Map<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>();

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Image getImage(final Object element) {
			if (element instanceof PapyrusPaletteService.LocalProviderDescriptor) {
				return Activator.getInstance().getBundledImage(LOCAL_DESCRIPTOR);
			} else if (element instanceof PapyrusPaletteService.ExtendedProviderDescriptor) {
				// icon should be decorated if it is already defined in a local way or not.
				return Activator.getInstance().getBundledImage(EXTENDED_PLUGIN_DESCRIPTOR);
			} else if (element instanceof PapyrusPaletteService.ProviderDescriptor) {
				return Activator.getInstance().getBundledImage(PLUGIN_DESCRIPTOR);
			}
			return Activator.getInstance().getBundledImage(DEFAULT_IMAGE);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getText(final Object element) {
			String text = null;
			if (element instanceof PapyrusPaletteService.ProviderDescriptor) {
				text = ((PapyrusPaletteService.ProviderDescriptor) element).getContributionName();
			}
			return null != text ? text : "<undefined>";//$NON-NLS-1$
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addListener(final ILabelProviderListener listener) {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispose() {
			Iterator<Image> images = imageCache.values().iterator();
			while (images.hasNext()) {
				images.next().dispose();
			}
			imageCache = null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isLabelProperty(final Object element, final String property) {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removeListener(final ILabelProviderListener listener) {
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void preferenceChange(final PreferenceChangeEvent event) {
		String id = event.getKey();
		if (IPapyrusPaletteConstant.PALETTE_CUSTOMIZATIONS_ID.equals(id)) {
			// refresh available palette table viewer
			availablePalettesTableViewer.setInput(PapyrusPaletteService.getInstance());
		}

	}

}
