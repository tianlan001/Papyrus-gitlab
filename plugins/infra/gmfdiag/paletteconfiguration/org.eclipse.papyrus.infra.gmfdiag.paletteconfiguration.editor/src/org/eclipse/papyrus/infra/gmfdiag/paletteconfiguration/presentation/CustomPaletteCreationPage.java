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

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.ExtendedPluginPaletteProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.LocalExtendedPaletteProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.ProviderDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.WorkspaceExtendedPaletteProvider;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Activator;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationPackage;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.editor.messages.Messages;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.utils.CreatePaletteItemUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Page for the creation of a new palette configuration model.
 */
// TODO replace it be properties view from papyrus framework or use EMFObservableValue
public class CustomPaletteCreationPage extends PaletteconfigurationModelWizard.PaletteconfigurationModelWizardInitialObjectCreationPage implements Listener {

	/** The palette id fiels. */
	private Text paletteIDField;

	/** The palette name field. */
	private Text paletteNameField;

	/** The palette description field. */
	private Text paletteDescriptionField;

	/** The palette required profile field. */
	private Text paletteRequiredProfilesField;

	/** The palette id. */
	private String paletteID;

	/** The palette names. */
	private String paletteName;

	/** The palette description. */
	private String paletteDescription;

	/** The palette required profiles. */
	private String paletteRequiredProfiles;

	/** Flag set to true of the identifier is read only */
	private boolean readOnlyIdentifier = false;

	/** the editing domain */
	private EditingDomain editingDomain;

	/**
	 * The constructor.
	 * 
	 * @param editingDomain
	 *            The editong domain.
	 */
	public CustomPaletteCreationPage(PaletteconfigurationModelWizard paletteconfigurationModelWizard, String pageId, EditingDomain editingDomain) {
		paletteconfigurationModelWizard.super(pageId);
		this.editingDomain = editingDomain;
		intializeValues();
	}

	/**
	 * Constructor.
	 */
	public CustomPaletteCreationPage(CustomPaletteconfigurationModelWizard customPaletteconfigurationModelWizard, String pageId) {
		this(customPaletteconfigurationModelWizard, pageId, null);
	}

	/**
	 * Get the palette id as {@link String}.
	 * 
	 * @return The palette id
	 */
	public String getPaletteID() {
		return paletteIDField.getText();
	}

	/**
	 * Get the palette name as {@link String}.
	 * 
	 * @return The palette name
	 */
	public String getPaletteName() {
		return paletteNameField.getText();
	}

	/**
	 * Get the palette description as {@link String}.
	 * 
	 * @return The palette description
	 */
	public String getPaletteDescription() {
		return paletteDescriptionField.getText();
	}

	/**
	 * Get the palette required profiles as {@link String}.
	 * 
	 * @return The palette required profiles
	 */
	public String getPaletteRequiredProfiles() {
		return paletteRequiredProfilesField.getText();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		{
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.verticalSpacing = 5;
			composite.setLayout(layout);

			GridData data = new GridData();
			data.verticalAlignment = GridData.FILL;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			composite.setLayoutData(data);
		}

		createNameControl(composite);
		createDescriptionControl(composite);

		createIdentifierControl(composite);
		createRequiredProfilesControl(composite);

		initialObjectField = new Combo(composite, SWT.BORDER);

		// hide initial object field which is not required because it will always be a palette configuration
		initialObjectField.setVisible(false);

		Label encodingLabel = new Label(composite, SWT.LEFT);
		{
			encodingLabel.setText(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_XMLEncoding"));//$NON-NLS-1$

			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			encodingLabel.setLayoutData(data);
		}
		encodingField = new Combo(composite, SWT.BORDER);
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			encodingField.setLayoutData(data);
		}

		for (String encoding : getEncodings()) {
			encodingField.add(encoding);
		}

		encodingField.select(0);
		encodingField.addModifyListener(validator);

		// hide encoding label to set it by default in UTF-8
		encodingLabel.setVisible(false);
		encodingField.setVisible(false);

		setPageComplete(validatePage());
		setControl(composite);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean validatePage() {
		// validate if there is a name and an identifier
		return !getPaletteName().isEmpty() && !getPaletteID().isEmpty() && getEncodings().contains(encodingField.getText());
	}

	/**
	 * Creates the control area for the name definition.
	 *
	 * @param control
	 *            the parent composite
	 */
	protected void createIdentifierControl(final Composite control) {
		final Label indentifierLabel = new Label(control, SWT.NONE);
		indentifierLabel.setText(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_ModelIdentifier"));//$NON-NLS-1$
		indentifierLabel.setToolTipText(Messages.CustomPaletteCreationPage_Palette_Identifier_Tooltip);

		paletteIDField = new Text(control, SWT.BORDER);
		paletteIDField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// paletteIdentifier.setToolTipText(Messages.Local_Palette_Name_Tooltip);

		// initialize, then add the listener...
		paletteIDField.setText(null != paletteID ? paletteID : "");//$NON-NLS-1$
		paletteIDField.setEnabled(!readOnlyIdentifier);
		paletteIDField.addListener(SWT.Modify, this);
	}

	/**
	 * Get the initial palette Id
	 * 
	 * @return the initial palette id.
	 */
	private String getInitPaletteIDValue() {
		return CreatePaletteItemUtil.generateInitPaletteIDValue();
	}

	/**
	 * creates the control area for the name definition
	 *
	 * @param control
	 *            the parent composite
	 */
	protected void createNameControl(final Composite control) {

		final Label indentifierLabel = new Label(control, SWT.NONE);
		indentifierLabel.setText(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_ModelLabel"));//$NON-NLS-1$
		// indentifierLabel.setToolTipText(Messages.Local_Palette_Name_Tooltip);//TODO tooltip

		paletteNameField = new Text(control, SWT.BORDER);
		paletteNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// paletteIdentifier.setToolTipText(Messages.Local_Palette_Name_Tooltip);

		// initialize, then add the listener...
		paletteNameField.setText(null != paletteName ? paletteName : "");//$NON-NLS-1$

		paletteNameField.addListener(SWT.Modify, this);
	}

	/**
	 * @return the initial palette name Value.
	 */
	protected String getInitPaletteNameValue() {
		return "";//$NON-NLS-1$
	}

	/**
	 * Creates the control area for the name definition.
	 *
	 * @param control
	 *            the parent composite
	 */
	protected void createDescriptionControl(final Composite control) {
		final Label indentifierLabel = new Label(control, SWT.NONE);
		indentifierLabel.setText(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_ModelDescription"));//$NON-NLS-1$
		// indentifierLabel.setToolTipText(Messages.Local_Palette_Name_Tooltip);//TODO tooltip

		paletteDescriptionField = new Text(control, SWT.BORDER);
		paletteDescriptionField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// paletteIdentifier.setToolTipText(Messages.Local_Palette_Name_Tooltip);

		// initialize, then add the listener...
		paletteDescriptionField.setText(null != paletteDescription ? paletteDescription : "");//$NON-NLS-1$

		paletteDescriptionField.addListener(SWT.Modify, this);
	}

	/**
	 * @return the initial palette description Value.
	 */
	protected String getInitPaletteDescValue() {
		return "";//$NON-NLS-1$
	}

	/**
	 * creates the control area for the name definition
	 *
	 * @param control
	 *            the parent composite
	 */
	protected void createRequiredProfilesControl(final Composite control) {
		final Label indentifierLabel = new Label(control, SWT.NONE);
		indentifierLabel.setText(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_ModelRequiredProfiles"));//$NON-NLS-1$
		// indentifierLabel.setToolTipText(Messages.Local_Palette_Name_Tooltip);//TODO tooltip

		paletteRequiredProfilesField = new Text(control, SWT.BORDER);
		paletteRequiredProfilesField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// paletteIdentifier.setToolTipText(Messages.Local_Palette_Name_Tooltip);

		// initialize, then add the listener...
		paletteRequiredProfilesField.setText(null != paletteRequiredProfiles ? paletteRequiredProfiles : "");//$NON-NLS-1$

		paletteRequiredProfilesField.addListener(SWT.Modify, this);
	}

	/**
	 * @return the initial palette required profiles Value.
	 */
	private String getInitRequiredProfilesValue() {
		return "";//$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	@Override
	public void handleEvent(Event event) {
		setPageComplete(validatePage());
	}

	/**
	 * Initialize the wizard value with default value.
	 */
	public void intializeValues() {
		paletteID = getInitPaletteIDValue();
		paletteName = getInitPaletteNameValue();
		paletteDescription = getInitPaletteDescValue();
		paletteRequiredProfiles = getInitPaletteNameValue();
	}

	/**
	 * Initialize the wizard value from information of descriptor and palette model from resource.
	 */
	public void intializeValues(final ProviderDescriptor descriptor, final Resource resource) {
		intializeValues();

		PaletteConfiguration paletteConfiguration = null;

		if (null != resource && !resource.getContents().isEmpty()) {
			paletteConfiguration = (PaletteConfiguration) resource.getContents().get(0);
		} else {
			try {
				paletteConfiguration = ((ExtendedPluginPaletteProvider) descriptor.getProvider()).loadConfigurationModel(resource).get(0);
			} catch (IOException e) {
				Activator.log.error(e);
			}
		}

		// set Id
		if (null != descriptor && descriptor.getProvider() instanceof ExtendedPluginPaletteProvider) {
			paletteID = descriptor.getContributionID();
			if ((descriptor.getProvider() instanceof LocalExtendedPaletteProvider) || (descriptor.getProvider() instanceof WorkspaceExtendedPaletteProvider)) {
				readOnlyIdentifier = false;
			} else {
				readOnlyIdentifier = true;
			}
		} else {
			paletteID = paletteConfiguration.getId();
			readOnlyIdentifier = false;
		}
		// Set Name
		paletteName = paletteConfiguration.getLabel();
		// Set description
		paletteDescription = paletteConfiguration.getDescription();
		// Set Required profiles
		paletteRequiredProfiles = paletteConfiguration.getRequiredProfiles().stream().collect(Collectors.joining(", "));//$NON-NLS-1$

		paletteConfiguration.eAdapters().add(new AdapterImpl() {

			@Override
			public void notifyChanged(Notification notification) {
				if (notification.getNotifier() instanceof PaletteConfiguration) {
					String profiles = ((PaletteConfiguration) notification.getNotifier()).getRequiredProfiles().stream().collect(Collectors.joining(", ")); //$NON-NLS-1$
					paletteRequiredProfilesField.setText(profiles);
				}
			}
		});

	}

	/**
	 * Update the {@link PaletteConfiguration} model with information from wizard fields.
	 * 
	 * @param palette
	 * @return the updated palette
	 */
	public EObject updateModel(final PaletteConfiguration palette) {
		if (null != editingDomain) {

			// Set the palette configuration
			CompoundCommand command = new CompoundCommand("update Model"); //$NON-NLS-1$

			// palette.setId(getPaletteID());
			command.append(new SetCommand(editingDomain, palette, PaletteconfigurationPackage.eINSTANCE.getConfiguration_Id(), getPaletteID()));

			// palette.setLabel(getPaletteName());
			command.append(new SetCommand(editingDomain, palette, PaletteconfigurationPackage.eINSTANCE.getConfiguration_Label(), getPaletteName()));

			// palette.setDescription(getPaletteDescription());
			command.append(new SetCommand(editingDomain, palette, PaletteconfigurationPackage.eINSTANCE.getConfiguration_Description(), getPaletteDescription()));

			String requiredProfiles = getRequiredProfiles();
			requiredProfiles = requiredProfiles.replace("\\s+", ""); //$NON-NLS-1$ //$NON-NLS-2$
			if (!requiredProfiles.isEmpty()) {
				command.append(new SetCommand(editingDomain, palette, PaletteconfigurationPackage.eINSTANCE.getPaletteConfiguration_RequiredProfiles(), Arrays.asList(requiredProfiles.split(","))));
			}
			if (command.canExecute()) {
				editingDomain.getCommandStack().execute(command);
			}
		}
		return palette;
	}

	/**
	 * @return the list of required profiles as a text. Profiles are separeted with commat.
	 */
	private String getRequiredProfiles() {
		return paletteRequiredProfilesField.getText();
	}

}