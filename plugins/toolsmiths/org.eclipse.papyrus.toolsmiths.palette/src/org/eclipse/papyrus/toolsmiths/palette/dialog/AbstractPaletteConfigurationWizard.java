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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 507654 Change load order and add EcoreUtil.resolveAll(resource) to avoid absolute references
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.palette.dialog;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceSetItemProvider;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;
import org.eclipse.gmf.runtime.common.core.service.ProviderPriority;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PaletteUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPalettePreferences;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.PapyrusPaletteService.ProviderDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteRessourcesConstants;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider.CustomPaletteconfigurationItemProviderAdapterFactory;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.utils.CreatePaletteItemUtil;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory;
import org.eclipse.papyrus.toolsmiths.palette.Messages;
import org.eclipse.papyrus.toolsmiths.palette.PaletteConstants;
import org.eclipse.papyrus.toolsmiths.palette.PaletteConstants.PaletteModelContextEnum;
import org.eclipse.papyrus.toolsmiths.palette.utils.PaletteUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.ui.IEditorPart;

/**
 * Abstract class for wizard of edition of palette configuration models.
 */
public abstract class AbstractPaletteConfigurationWizard extends Wizard {

	/**
	 * The dot string constant.
	 */
	protected static final String STR_DOT = "."; //$NON-NLS-1$

	/** the palette resource */
	protected Resource paletteResource;

	/** the element type resource for the ui */
	protected Resource elementTypeUIResource;

	/** the element type resource for the semantic */
	protected Resource elementTypeSemResource;

	/** the string builder */
	protected StringBuilder stringBuilder = new StringBuilder();

	/** customizer used by the palette viewer */
	protected ProviderDescriptor descriptor;

	/** the editing domain */
	protected AdapterFactoryEditingDomain editingDomain;

	/** the adapter factory */
	protected ComposedAdapterFactory adapterFactory;

	/** The context of the palette contribution */
	protected PaletteModelContextEnum paletteContext;

	/** Editor part in which the palette is displayed */
	protected IEditorPart editorPart;

	/** the provider priority */
	protected ProviderPriority priority;

	/**
	 * Constructor.
	 */
	public AbstractPaletteConfigurationWizard(final IEditorPart part, final ProviderDescriptor descriptor) {
		this.editorPart = part;
		this.descriptor = descriptor;
		// initialize the context
		paletteContext = PaletteUtils.getPaletteModelContext(descriptor);

		ProviderPriority localPalettePriority = null;
		if (PaletteModelContextEnum.New.equals(paletteContext)) {
			// Create New Palette
			setWindowTitle(Messages.PaletteConfigurationWizard_NewPaletteWizardLabel);
		} else {
			// Edit Palette
			localPalettePriority = PapyrusPalettePreferences.getLocalExtendedPalettePriority(descriptor.getContributionID());
			setWindowTitle(Messages.PaletteConfigurationWizard_EditPaletteWizardLabel);
		}

		initPriority(localPalettePriority);

		initializeEditingDomain();

		createResources();
	}

	/**
	 * inits the priority value
	 *
	 * @param value
	 *            value to set
	 */
	protected void initPriority(final ProviderPriority value) {
		if (null != value) {
			this.priority = value;
		} else {
			// by default, Medium
			this.priority = ProviderPriority.MEDIUM;
		}
	}

	/**
	 * unloads resources.
	 */
	protected void unloadRessources() {
		if (null != paletteResource && paletteResource.isLoaded()) {
			paletteResource.unload();
		}
		if (null != elementTypeSemResource && elementTypeSemResource.isLoaded()) {
			elementTypeSemResource.unload();
		}
		if (null != elementTypeUIResource && elementTypeUIResource.isLoaded()) {
			elementTypeUIResource.unload();
		}
	}


	/**
	 * delete resources.
	 */
	public void deleteResource() {
		unloadRessources();
		try {
			if (null != paletteResource) {
				paletteResource.delete(Collections.EMPTY_MAP);
			}
			if (null != elementTypeSemResource) {
				elementTypeSemResource.delete(Collections.EMPTY_MAP);
			}
			if (null != elementTypeUIResource) {
				elementTypeUIResource.delete(Collections.EMPTY_MAP);
			}
		} catch (IOException e) {
			Activator.log.error(e);
		}
	}



	/**
	 * Clear the string builder.
	 */
	protected void clearStringBuilder() {
		stringBuilder.setLength(0); // set length of buffer to 0
		stringBuilder.trimToSize(); // trim the underlying buffer
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performFinish() {

		// Save files
		saveResources();

		// Load models on papyrus( Palettes, element types, etc...)
		deployModels();

		// unload ressources
		unloadRessources();

		return true;
	}

	/**
	 * Load models on papyrus( Palettes, element types, etc...).
	 */
	protected abstract void deployModels();

	/**
	 * save resources()
	 */
	protected abstract void saveResources();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performCancel() {
		unloadRessources();
		return super.performCancel();
	}

	/**
	 * create the resource and set it the palette configuration model
	 */
	protected void createResources() {
		createPaletteResource();
		createElementTypesResources();
	}


	/**
	 * Create Element type resources.
	 */
	protected void createElementTypesResources() {

		URI elementTypeUIURI = null;
		URI elementTypeSemURI = null;

		File elementTypeUIFile = null;
		File elementTypeSemFile = null;

		switch (paletteContext) {

		case New:
			PaletteConfiguration paletteModel = (PaletteConfiguration) paletteResource.getContents().get(0);
			clearStringBuilder();
			stringBuilder.append(paletteModel.getId());
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_UI_IDENTIFIER_POSTFIX);
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_EXTENSION);
			String pathUI = Activator.getInstance().getStateLocation().append(stringBuilder.toString()).toString();

			clearStringBuilder();
			stringBuilder.append(paletteModel.getId());
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_SEMENTIC_IDENTIFIER_POSTFIX);
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_EXTENSION);
			String pathSem = Activator.getInstance().getStateLocation().append(stringBuilder.toString()).toString();

			elementTypeUIFile = new File(pathUI);
			elementTypeSemFile = new File(pathSem);
			break;

		case Local:
			clearStringBuilder();
			stringBuilder.append(((PapyrusPaletteService.LocalExtendedProviderDescriptor) descriptor).getContributionID());
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_UI_IDENTIFIER_POSTFIX);
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_EXTENSION);
			elementTypeUIFile = Activator.getInstance().getStateLocation().append(stringBuilder.toString()).toFile();

			clearStringBuilder();
			stringBuilder.append(((PapyrusPaletteService.LocalExtendedProviderDescriptor) descriptor).getContributionID());
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_SEMENTIC_IDENTIFIER_POSTFIX);
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_EXTENSION);
			elementTypeSemFile = Activator.getInstance().getStateLocation().append(stringBuilder.toString()).toFile();
			break;

		case Plugin:
			String path = PapyrusPalettePreferences.getPaletteRedefinition(descriptor.getContributionID());
			path = path.substring(0, path.length() - PaletteConstants.PALETTECONFIGURATION_EXTENSION.length() - 1);

			clearStringBuilder();
			stringBuilder.append(path);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_UI_IDENTIFIER_POSTFIX);
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_EXTENSION);
			elementTypeUIFile = Activator.getInstance().getStateLocation().append(stringBuilder.toString()).toFile();

			clearStringBuilder();
			stringBuilder.append(path);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_SEMENTIC_IDENTIFIER_POSTFIX);
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.ELEMENTTYPE_EXTENSION);
			elementTypeSemFile = Activator.getInstance().getStateLocation().append(stringBuilder.toString()).toFile();
			break;

		case Workspace:
			Object contributions = ((PapyrusPaletteService.LocalProviderDescriptor) descriptor).getDescription().getContributions();
			if (contributions instanceof String) {

				elementTypeUIFile = new File(((String) contributions).replace(STR_DOT + PaletteConstants.PALETTECONFIGURATION_EXTENSION, PaletteConstants.ELEMENTTYPE_UI_IDENTIFIER_POSTFIX + STR_DOT +
						PaletteConstants.ELEMENTTYPE_EXTENSION));
				elementTypeSemFile = new File(((String) contributions).replace(STR_DOT + PaletteConstants.PALETTECONFIGURATION_EXTENSION, PaletteConstants.ELEMENTTYPE_SEMENTIC_IDENTIFIER_POSTFIX + STR_DOT + PaletteConstants.ELEMENTTYPE_EXTENSION));

				elementTypeUIURI = URI.createPlatformResourceURI(elementTypeUIFile.getPath(), false);
				elementTypeSemURI = URI.createPlatformResourceURI(elementTypeSemFile.getPath(), false);
			}
			break;

		default:
			break;
		}

		if (null == elementTypeUIFile || null == elementTypeSemURI) {
			elementTypeUIURI = URI.createFileURI(elementTypeUIFile.getAbsolutePath());
			elementTypeSemURI = URI.createFileURI(elementTypeSemFile.getAbsolutePath());
		}

		// Create resource
		elementTypeSemResource = editingDomain.getResourceSet().createResource(elementTypeSemURI);
		elementTypeUIResource = editingDomain.getResourceSet().createResource(elementTypeUIURI);

		if (!PaletteUtils.notErrorOnFile(elementTypeUIFile) && !PaletteUtils.notErrorOnFile(elementTypeSemFile)) {

			ElementTypeSetConfiguration emptyElementTypeModelSem = getEmptyElementTypeModel(elementTypeSemResource.getURI().lastSegment().replace(STR_DOT + PaletteConstants.ELEMENTTYPE_EXTENSION, ""));//$NON-NLS-1$
			ElementTypeSetConfiguration emptyElementTypeModelUI = getEmptyElementTypeModel(elementTypeUIResource.getURI().lastSegment().replace(STR_DOT + PaletteConstants.ELEMENTTYPE_EXTENSION, ""));//$NON-NLS-1$

			// Add it to the resource
			CompoundCommand compoundCommand = new CompoundCommand();
			compoundCommand.append(new AddCommand(editingDomain, elementTypeSemResource.getContents(), emptyElementTypeModelSem));
			compoundCommand.append(new AddCommand(editingDomain, elementTypeUIResource.getContents(), emptyElementTypeModelUI));
			editingDomain.getCommandStack().execute(compoundCommand);
		}

		if (null != elementTypeUIResource || null != elementTypeSemResource) {
			try {
				elementTypeSemResource.load(Collections.emptyMap());
				EcoreUtil.resolveAll(elementTypeSemResource);
				elementTypeUIResource.load(Collections.emptyMap());
				EcoreUtil.resolveAll(elementTypeUIResource);
				elementTypeSemResource.getResourceSet().getLoadOptions().put(PaletteRessourcesConstants.ELEMENTTYPE_SEMENTIC_RESSOURCE_IDENTIFIER, elementTypeSemResource);
				elementTypeUIResource.getResourceSet().getLoadOptions().put(PaletteRessourcesConstants.ELEMENTTYPE_UI_RESSOURCE_IDENTIFIER, elementTypeUIResource);
			} catch (IOException e) {
				Activator.log.error(e);
			}
		}
	}



	/**
	 * @return An empty Element type model with intialIdentifier as identifier.
	 */
	protected ElementTypeSetConfiguration getEmptyElementTypeModel(final String initialIdentifer) {
		// Create elementType models
		ElementTypeSetConfiguration elementTypeUI = ElementTypesConfigurationsFactory.eINSTANCE.createElementTypeSetConfiguration();
		elementTypeUI.setIdentifier(initialIdentifer);
		elementTypeUI.setName(initialIdentifer);
		elementTypeUI.setMetamodelNsURI(initialIdentifer);
		return elementTypeUI;
	}

	/**
	 * Create palette resource.
	 */
	protected void createPaletteResource() {

		URI fileURI = null;

		switch (paletteContext) {

		case New:
			// Create a volatile default file (not to be save)
			// Create a initial void model
			PaletteConfiguration paletteModel = CreatePaletteItemUtil.createInitialModel();

			clearStringBuilder();
			stringBuilder.append("NewPalette");//$NON-NLS-1$
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.PALETTECONFIGURATION_EXTENSION);

			String path = Activator.getInstance().getStateLocation().append(stringBuilder.toString()).toString();// $NON-NLS-1$
			fileURI = URI.createFileURI(path);

			paletteResource = editingDomain.getResourceSet().createResource(fileURI);
			// Add it to the resource
			Command addCommand = new AddCommand(editingDomain, paletteResource.getContents(), paletteModel);
			editingDomain.getCommandStack().execute(addCommand);
			break;

		case Local:
			clearStringBuilder();
			stringBuilder.append(((PapyrusPaletteService.LocalExtendedProviderDescriptor) descriptor).getContributionID());
			stringBuilder.append(STR_DOT);
			stringBuilder.append(PaletteConstants.PALETTECONFIGURATION_EXTENSION);

			File file = Activator.getInstance().getStateLocation().append(stringBuilder.toString()).toFile();

			if (PaletteUtils.notErrorOnFile(file)) {
				fileURI = URI.createFileURI(file.getAbsolutePath());
				paletteResource = editingDomain.getResourceSet().createResource(fileURI);
			}
			break;

		case Plugin:
			fileURI = PaletteUtil.getRedefinitionFileURI(descriptor.getContributionID());
			if (null != fileURI) {
				paletteResource = editingDomain.getResourceSet().createResource(fileURI);
			}
			break;

		case Workspace:
			Object contributions = ((PapyrusPaletteService.LocalProviderDescriptor) descriptor).getDescription().getContributions();
			if (contributions instanceof String) {
				fileURI = URI.createPlatformResourceURI((String) contributions, false);
				paletteResource = editingDomain.getResourceSet().createResource(fileURI);
			}
			break;

		default:
			break;

		}

		if (null != paletteResource) {
			try {
				paletteResource.load(Collections.emptyMap());
				paletteResource.getResourceSet().getLoadOptions().put(PaletteRessourcesConstants.PALETTE_RESSOURCE_IDENTIFIER, paletteResource);
				EcoreUtil.resolveAll(paletteResource);

			} catch (IOException e) {
				Activator.log.error(e);
			}
		}
	}

	/**
	 * This sets up the editing domain for the model editor.
	 */
	protected void initializeEditingDomain() {
		// Create an adapter factory that yields item providers.
		//
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory() {
			@Override
			public Adapter createResourceSetAdapter() {
				return new ResourceSetItemProvider(this) {
					@Override
					public Collection<?> getChildren(final Object object) {
						ResourceSet resourceSet = (ResourceSet) object;
						if (!resourceSet.getResources().isEmpty() && resourceSet.getResources().get(0).getContents().get(0) instanceof PaletteConfiguration) {
							return ((PaletteConfiguration) resourceSet.getResources().get(0).getContents().get(0)).getDrawerConfigurations(); // For Drawers as root
						}
						return Collections.emptyList();
					}
				};
			}
		});
		adapterFactory.addAdapterFactory(new CustomPaletteconfigurationItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

		// Create the editing domain with a special command stack.
		editingDomain = new TransactionalEditingDomainImpl(adapterFactory);
	}

}
