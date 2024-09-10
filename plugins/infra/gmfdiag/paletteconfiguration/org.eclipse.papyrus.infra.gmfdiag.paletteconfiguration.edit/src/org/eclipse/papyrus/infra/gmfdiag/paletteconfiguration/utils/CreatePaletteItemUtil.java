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

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectCreationEntry;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeConnectionTool;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.IPapyrusPaletteConstant;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.DrawerConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.IconDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteRessourcesConstants;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationFactory;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationPackage;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolKind;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.IConfiguredHintedElementType;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.graphics.Image;

/**
 * Utility class for the palette configuration edition.
 */
public class CreatePaletteItemUtil {

	/**
	 * Constructor.
	 */
	private CreatePaletteItemUtil() {
		// Utilities class, not had to be implemented
	}

	/** The default label for drawers. */
	private static final String DRAWER_DEFAULT_LABEL = "Default";//$NON-NLS-1$

	/** The prefix for image descriptor URL. */
	private final static String URL_IMAGE_DESCRIPTOR_BEGIN = "URLImageDescriptor(";//$NON-NLS-1$

	/**
	 * Creates a set {@link ToolConfiguration} from the given entry.
	 *
	 * @param entry
	 *            the palette entry from which to create the node
	 * @param resourceSet
	 *            the resource set
	 * @return the created {@link ToolConfiguration}
	 */
	public static ToolConfiguration createToolConfiguration(final PaletteEntry entry, final ResourceSet resourceSet) {

		ToolConfiguration tool = PaletteconfigurationFactory.eINSTANCE.createToolConfiguration();
		// Set label
		tool.setLabel(entry.getLabel());
		// Set Identifier used for tool and for semantic specialized type
		String toolIdendtifier = generateID(entry.getId());
		tool.setId(toolIdendtifier);
		// Set description
		tool.setDescription(entry.getDescription());
		// Set the icon
		tool.setIcon(createIconDescriptorFromPaletteEntry(entry));
		// Set Kind
		tool.setKind(getKindFromPaletteEntry(entry));

		return tool;
	}

	/**
	 * Create element types Elements in Element type model from the domain for the tool configuration.
	 * 
	 * @param domain
	 *            the editing domain
	 * @param entry
	 *            the Tool entry
	 * @param tool
	 *            the too configuration
	 */
	public static Command createElementTypesElement(final EditingDomain domain, final ToolEntry entry, final ToolConfiguration tool) {
		CompoundCommand cc = new CompoundCommand();
		ResourceSet resourceSet = domain.getResourceSet();
		String toolIdendtifier = tool.getId();

		// Create if needed Semantic and UI Specialized Type
		SpecializationTypeConfiguration semanticSpecializedType = createSemanticSpecializedType(toolIdendtifier, entry, resourceSet);

		// Add semantic specialized type to element type model.
		Command setSemCommand = new AddCommand(domain, getSemanticElementTypeModel(resourceSet), ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS, semanticSpecializedType);
		cc.append(setSemCommand);

		if (null != semanticSpecializedType) {

			List<SpecializationTypeConfiguration> uiSpecializedTypes = createUISpecializedTypes(semanticSpecializedType, entry, resourceSet);

			// Add semantic specialized type to element type model.
			Command setuiCommand = new AddCommand(domain, getUIElementTypeModel(resourceSet), ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS, uiSpecializedTypes);
			cc.append(setuiCommand);

			if (null != uiSpecializedTypes) {
				// Create palette Element descriptors
				Collection<ElementDescriptor> paletteElementDescriptors = createPaletteElementDescriptors(uiSpecializedTypes);
				// getPaletteConfigurationModel(resourceSet)
				Command setpaletteCommand = new AddCommand(domain, tool, PaletteconfigurationPackage.Literals.TOOL_CONFIGURATION__ELEMENT_DESCRIPTORS, paletteElementDescriptors);
				cc.append(setpaletteCommand);

				// Create Stereotype advice if needed
				// Get stereotype to apply
				// TODO manage it
				if (entry instanceof AspectCreationEntry) {
					List<?> advices = (List<?>) ((AspectCreationEntry) entry).getAspectProperties(IPapyrusPaletteConstant.ADVICES_TO_APPLY);
					if (null != advices && !((List<?>) advices).isEmpty()) {
						// Add Advice to element type model.
						for (Object advice : advices) {
							if (advice instanceof AbstractAdviceBindingConfiguration) {
								((AbstractAdviceBindingConfiguration) advice).setTarget(semanticSpecializedType);
								((AbstractAdviceBindingConfiguration) advice).setIdentifier(semanticSpecializedType.getIdentifier() + "_ASAC");

								Command setAdviceCommand = new AddCommand(domain, getSemanticElementTypeModel(resourceSet), ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION__ADVICE_BINDINGS_CONFIGURATIONS, advice);
								cc.append(setAdviceCommand);
							}
						}
					}
				}
			}
		}
		return cc;
	}

	/**
	 * Create palette element descriptors from a {@link List} of {@link SpecializationTypeConfiguration}.
	 * 
	 * @param uiSpecializedTypes
	 *            The {@link List} of {@link SpecializationTypeConfiguration}.
	 * @return The {@link List} of {@link ElementDescriptor}
	 */
	protected static List<ElementDescriptor> createPaletteElementDescriptors(final List<SpecializationTypeConfiguration> uiSpecializedTypes) {

		// The return list
		List<ElementDescriptor> descriptors = new ArrayList<ElementDescriptor>();
		for (SpecializationTypeConfiguration uiSpecializationType : uiSpecializedTypes) {
			ElementDescriptor descriptor = PaletteconfigurationFactory.eINSTANCE.createElementDescriptor();
			descriptor.setElementType(uiSpecializationType);
			descriptors.add(descriptor);
		}

		return descriptors;
	}

	/**
	 * Create a {@link List} of {@link SpecializationTypeConfiguration} for the UI part.
	 * 
	 * @param semanticSpecializedType
	 *            The related SpecializationTypeConfiguration from semantic part as a {@link String}.
	 * @param entry
	 *            The {@link PaletteEntry}.
	 * @param resourceSet
	 *            The Related {@link ResourceSet}.
	 * @return the {@link List} of {@link SpecializationTypeConfiguration}
	 */
	public static List<SpecializationTypeConfiguration> createUISpecializedTypes(final SpecializationTypeConfiguration semanticSpecializedType, final PaletteEntry entry, final ResourceSet resourceSet) {
		// Stereotyped tools case
		Tool tool = ((ToolEntry) entry).createTool();
		// Gets element types from tools
		List<IElementType> elementTypes = getToolElementTypes(tool);
		ElementTypeSetConfiguration uiElementTypeModel = getUIElementTypeModel(resourceSet);

		ArrayList<SpecializationTypeConfiguration> uiSpecializationTypes = new ArrayList<SpecializationTypeConfiguration>();

		if (null != elementTypes && null != uiElementTypeModel) {

			// Create UI Specialize Type
			for (IElementType elementType : elementTypes) {
				if (elementType instanceof IConfiguredHintedElementType) {
					// Create or get UI Specialized Type
					uiSpecializationTypes.add(createUISpecializedType(entry.getLabel(), uiElementTypeModel, semanticSpecializedType, (IConfiguredHintedElementType) elementType));
				}
			}
		}
		return uiSpecializationTypes;
	}

	/**
	 * Create a {@link SpecializationTypeConfiguration} for the semantic part.
	 * 
	 * @param identifier
	 *            Its identifier.
	 * @param paletteEntry
	 *            The related {@link PaletteEntry}.
	 * @param resourceSet
	 *            The related {@link ResourceSet}.
	 * @return The created {@link SpecializationTypeConfiguration}.
	 */
	public static SpecializationTypeConfiguration createSemanticSpecializedType(final String identifier, final PaletteEntry paletteEntry, final ResourceSet resourceSet) {
		String name = paletteEntry.getLabel();
		SpecializationTypeConfiguration specializedType = null;
		if (paletteEntry instanceof ToolEntry) {
			IElementType toolSpecializedType = (IElementType) getToolElementTypes(((ToolEntry) paletteEntry).createTool()).get(0);
			if (toolSpecializedType instanceof IConfiguredHintedElementType) {

				String id = getPaletteConfigurationModel(resourceSet).getId();

				StringBuilder newSpecializedTypeId = new StringBuilder();
				newSpecializedTypeId.append(id);
				newSpecializedTypeId.append(".");//$NON-NLS-1$
				newSpecializedTypeId.append(identifier);

				List<ElementTypeConfiguration> elementTypeConfigurations = Arrays.asList(((IConfiguredHintedElementType) toolSpecializedType).getConfiguration());
				ElementTypeSetConfiguration semanticElementTypeModel = getSemanticElementTypeModel(resourceSet);
				if (null != semanticElementTypeModel) {
					specializedType = createSpecializedType(newSpecializedTypeId.toString(), name, elementTypeConfigurations);
				}
			}
		}
		return specializedType;
	}

	/**
	 * Create a {@link SpecializationTypeConfiguration}.
	 * 
	 * @param identifier
	 *            The identifier to set.
	 * @param name
	 *            The name to set.
	 * @param specializedTypes
	 *            The {@link List} of SpecializedTypesID as {@link String}.
	 * @return The created {@link SpecializationTypeConfiguration}.
	 */
	public static SpecializationTypeConfiguration createSpecializedType(final String identifier, final String name, final List<ElementTypeConfiguration> specializedTypes) {
		SpecializationTypeConfiguration semSpecializationType;
		semSpecializationType = ElementTypesConfigurationsFactory.eINSTANCE.createSpecializationTypeConfiguration();

		semSpecializationType.setIdentifier(identifier);
		semSpecializationType.setKind("org.eclipse.gmf.runtime.emf.type.core.IHintedType");//$NON-NLS-1$
		semSpecializationType.setName(name);

		semSpecializationType.getSpecializedTypes().addAll(specializedTypes);
		return semSpecializationType;
	}

	/**
	 * Create an initial model with a single empty drawer.
	 */
	public static PaletteConfiguration createInitialModel() {

		// create the root
		PaletteConfiguration palette = PaletteconfigurationFactory.eINSTANCE.createPaletteConfiguration();

		// Set Id
		palette.setId(generateInitPaletteIDValue());

		// Create the first void drawer
		DrawerConfiguration drawer = PaletteconfigurationFactory.eINSTANCE.createDrawerConfiguration();
		drawer.setLabel(DRAWER_DEFAULT_LABEL);
		drawer.setId(generateID(DRAWER_DEFAULT_LABEL));
		// Set the drawer on the palette configuration
		palette.getDrawerConfigurations().add(drawer);

		return palette;
	}

	/**
	 * @return a initial palette id value, the name of the user as a {@link String}.
	 */
	public static String generateInitPaletteIDValue() {
		return generateID(System.getProperty("user.name"));//$NON-NLS-1$
	}


	/**
	 * Create The graphical {@link SpecializationTypeConfiguration} linked to the semanticSpecilizedTypeIdentifier which contains stereotype advice.
	 * 
	 * @param name
	 *            The name of the {@link SpecializationTypeConfiguration}.
	 * @param uiElementTypeModel
	 *            The model container.
	 * @param semanticSpecializedType
	 *            The identifier of the related {@link SpecializationTypeConfiguration} from semantic model.
	 * @param uiElementType
	 *            The UI Element type.
	 * @return
	 */
	public static SpecializationTypeConfiguration createUISpecializedType(final String name, final ElementTypeSetConfiguration uiElementTypeModel, final SpecializationTypeConfiguration semanticSpecializedType,
			final IConfiguredHintedElementType uiElementType) {
		String semanticHint = uiElementType.getSemanticHint();

		// List of necessary SpecializedType to apply
		List<ElementTypeConfiguration> elementTypes = new ArrayList<ElementTypeConfiguration>();
		elementTypes.add(semanticSpecializedType);
		elementTypes.add(uiElementType.getConfiguration());

		SpecializationTypeConfiguration uiSpecializationType = null;

		// Test if SpecializedType exist
		// -> If semantic hint and specializedTypesID are the same than a existing SpecializedType
		EList<ElementTypeConfiguration> elementTypeConfigurations = uiElementTypeModel.getElementTypeConfigurations();
		for (ElementTypeConfiguration elementTypeConfiguration : elementTypeConfigurations) {
			if (elementTypeConfiguration instanceof SpecializationTypeConfiguration) {
				String hint = elementTypeConfiguration.getHint();
				EList<ElementTypeConfiguration> specializedTypesID = ((SpecializationTypeConfiguration) elementTypeConfiguration).getSpecializedTypes();

				if (hint.equals(semanticHint) && specializedTypesID.containsAll(elementTypes) && elementTypes.containsAll(specializedTypesID)) {
					uiSpecializationType = (SpecializationTypeConfiguration) elementTypeConfiguration;
					continue;
				}
			}
		}

		if (null == uiSpecializationType) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(semanticSpecializedType.getIdentifier());
			stringBuilder.append("_");//$NON-NLS-1$
			stringBuilder.append(semanticHint);

			// Create UI Specialized Type:
			String newUISpecilizedTypeIdentifier = stringBuilder.toString();

			uiSpecializationType = ElementTypesConfigurationsFactory.eINSTANCE.createSpecializationTypeConfiguration();
			uiSpecializationType.setHint(semanticHint);
			uiSpecializationType.setIdentifier(newUISpecilizedTypeIdentifier);
			uiSpecializationType.setKind("org.eclipse.gmf.runtime.emf.type.core.IHintedType");//$NON-NLS-1$
			uiSpecializationType.setName(name);
			uiSpecializationType.getSpecializedTypes().addAll(elementTypes);

		}
		return uiSpecializationType;
	}

	/**
	 * @param resourceSet
	 *            The resource set to look at.
	 * @return The {@link PaletteConfiguration} model from the {@link ResourceSet}.
	 */
	public static PaletteConfiguration getPaletteConfigurationModel(final ResourceSet resourceSet) {

		PaletteConfiguration palette = null;

		Object object = resourceSet.getLoadOptions().get(PaletteRessourcesConstants.PALETTE_RESSOURCE_IDENTIFIER);

		if (object instanceof Resource) {
			Resource resource = (Resource) object;

			EList<EObject> contents = resource.getContents();
			if (!contents.isEmpty()) {
				EObject eObject = contents.get(0);
				if (eObject instanceof PaletteConfiguration) {
					palette = (PaletteConfiguration) eObject;
				}
			}
		}
		return palette;
	}

	/**
	 * @param resourceSet
	 *            The resource set to look at.
	 * @return The {@link ElementTypeSetConfiguration} model for the semantic part from the {@link ResourceSet}.
	 */
	public static ElementTypeSetConfiguration getSemanticElementTypeModel(final ResourceSet resourceSet) {

		ElementTypeSetConfiguration elementTypeSem = null;

		Object object = resourceSet.getLoadOptions().get(PaletteRessourcesConstants.ELEMENTTYPE_SEMENTIC_RESSOURCE_IDENTIFIER);

		if (object instanceof Resource) {
			Resource resource = (Resource) object;

			EList<EObject> contents = resource.getContents();
			if (!contents.isEmpty()) {
				EObject eObject = contents.get(0);
				if (eObject instanceof ElementTypeSetConfiguration) {
					elementTypeSem = (ElementTypeSetConfiguration) eObject;
				}
			}
		}

		return elementTypeSem;
	}

	/**
	 * @param resourceSet
	 *            The resource set to look at.
	 * @return The {@link ElementTypeSetConfiguration} model for the UI part from the {@link ResourceSet}.
	 */
	public static ElementTypeSetConfiguration getUIElementTypeModel(final ResourceSet resourceSet) {

		ElementTypeSetConfiguration elementTypeUI = null;
		Object object = resourceSet.getLoadOptions().get(PaletteRessourcesConstants.ELEMENTTYPE_UI_RESSOURCE_IDENTIFIER);
		if (object instanceof Resource) {
			Resource resource = (Resource) object;

			EList<EObject> contents = resource.getContents();
			if (!contents.isEmpty()) {
				EObject eObject = contents.get(0);
				if (eObject instanceof ElementTypeSetConfiguration) {
					elementTypeUI = (ElementTypeSetConfiguration) eObject;
				}
			}
		}

		return elementTypeUI;
	}

	/**
	 * @return The list of {@link IElementType} from the {@link Tool} tool parameter.
	 */
	public static List<IElementType> getToolElementTypes(final Tool tool) {
		List<IElementType> elementTypes = null;
		if (tool instanceof AspectUnspecifiedTypeCreationTool) {
			elementTypes = ((AspectUnspecifiedTypeCreationTool) tool).getElementTypes();
		} else if (tool instanceof AspectUnspecifiedTypeConnectionTool) {
			elementTypes = ((AspectUnspecifiedTypeConnectionTool) tool).getElementTypes();
		}
		return elementTypes;
	}

	// /**
	// * @param entry
	// * The {@link PaletteEntry}.
	// * @return The list of stereotype to apply from a {@link PaletteEntry} as String.
	// */
	// public static List<String> getStereotypeToApply(final PaletteEntry entry) {
	// List<String> stereotypesToApply = new ArrayList<String>();
	//
	// Tool tool = ((ToolEntry) entry).createTool();
	//
	// // Check for post actions
	// // TODO
	// // List<IPostAction> postActions = getPostActions(tool);
	// //
	// // // Check for stereotype to apply
	// // if (null != postActions) {
	// // for (IPostAction iPostAction : postActions) {
	// // if (iPostAction instanceof StereotypePostAction) {
	// // stereotypesToApply.addAll(((StereotypePostAction) iPostAction).getStereotypesToApply());
	// // }
	// // }
	// // }
	// return stereotypesToApply;
	// }

	/**
	 * @param tool
	 *            The {@link Tool}.
	 * @return The list of {@link IPostAction} from a {@link Tool}.
	 */
	// public static List<IPostAction> getPostActions(final Tool tool) {
	// List<IPostAction> postActions = null;
	// if (tool instanceof AspectUnspecifiedTypeCreationTool) {
	// postActions = ((AspectUnspecifiedTypeCreationTool) tool).getPostActions();
	// } else if (tool instanceof AspectUnspecifiedTypeConnectionTool) {
	// postActions = ((AspectUnspecifiedTypeConnectionTool) tool).getPostActions();
	// }
	// return postActions;
	// }

	/**
	 * Get the {@link ToolKind} from an {@link PaletteEntry}.
	 * 
	 * @param entry
	 *            The palette entry
	 * @return the tool kind of the entry
	 */
	public static ToolKind getKindFromPaletteEntry(final PaletteEntry entry) {
		// TODO do it by getting the semantic kind
		ToolKind kind = ToolKind.CREATION_TOOL;
		PaletteContainer parent = entry.getParent();
		if (null == parent && entry instanceof AspectCreationEntry) {
			parent = ((AspectCreationEntry) entry).getReferencedEntry().getParent();
		}
		if (null != parent) {
			if (parent.getLabel().contains("Edges")) {//$NON-NLS-1$
				kind = ToolKind.CONNECTION_TOOL;
			} else if (parent.getLabel().contains("Nodes")) {//$NON-NLS-1$
				kind = ToolKind.CREATION_TOOL;
			}
		}
		return kind;
	}



	/**
	 * Create an {@link IconDescriptor} from a {@link PaletteEntry}.
	 * 
	 * @return the {@link IconDescriptor}.
	 */
	public static IconDescriptor createIconDescriptorFromPaletteEntry(final PaletteEntry entry) {
		String iconPath = entry.getSmallIcon().toString();
		IconDescriptor iconDescriptor = null;
		if (iconPath.startsWith(URL_IMAGE_DESCRIPTOR_BEGIN)) {
			iconPath = iconPath.substring(URL_IMAGE_DESCRIPTOR_BEGIN.length(), iconPath.length() - 1);

			iconDescriptor = PaletteconfigurationFactory.eINSTANCE.createIconDescriptor();

			iconDescriptor.setPluginID(org.eclipse.papyrus.infra.widgets.Activator.retrieveBundleId(iconPath));
			iconDescriptor.setIconPath(org.eclipse.papyrus.infra.widgets.Activator.retrieveLocalPath(iconPath));
		}
		return iconDescriptor;
	}

	/**
	 * Convert {@link IconDescriptor} to {@link Image}.
	 * 
	 * @param IconDescriptor
	 *            The icon descriptor to convert.
	 * @return The image.
	 */
	public static Image iconDescriptorToImage(final IconDescriptor icon) {
		Image image = null;
		// get the Icon
		if (null != icon) {
			// Get the image descriptor
			final ImageDescriptor imageDescriptor = Activator.getDefault().getImageDescriptor(icon.getPluginID(), icon.getIconPath());
			if (null != imageDescriptor) {
				image = imageDescriptor.createImage();
			}
		}
		return image;
	}

	/**
	 * Generates the ID with the prefix append to the current time in milliseconds.
	 *
	 * @param prefix
	 *            The prefix of the id.
	 * @return The generated id.
	 */
	public static String generateID(String prefix) {
		StringBuffer id = new StringBuffer();
		id.append(prefix);
		id.append("_");//$NON-NLS-1$
		id.append(System.currentTimeMillis());

		return id.toString();
	}
}
