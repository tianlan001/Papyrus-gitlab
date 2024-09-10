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
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.modelelement;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Configuration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.IconDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteRessourcesConstants;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationFactory;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.messages.Messages;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.IconEntry;
import org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableValue;
import org.eclipse.papyrus.infra.widgets.Activator;

/**
 * An {@link EMFObservableValue} for {@link IconDescriptor} as {@link String}.
 *
 */
public class IconDescriptorObservableValue extends EMFObservableValue {

	/**
	 * Constructor.
	 */
	public IconDescriptorObservableValue(final EObject eObject, final EStructuralFeature eStructuralFeature, final EditingDomain domain) {
		super(eObject, eStructuralFeature, domain);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doSetValue(final Object value) {
		Command command = null;

		// normal case: the value is a string
		if (value instanceof String) {
			command = new CompoundCommand("set value"); //$NON-NLS-1$

			// create the Icon descriptor to set
			IconDescriptor descriptor = PaletteconfigurationFactory.eINSTANCE.createIconDescriptor();
			descriptor.setPluginID(Activator.retrieveBundleId((String) value));
			descriptor.setIconPath(Activator.retrieveLocalPath((String) value));

			// Set the new value as the descriptor
			((CompoundCommand) command).append(getSetCommand(descriptor));

			// set the icon of the associated element type model.
			Command setIconCommand = getSetElementTypeIconsCommand(descriptor);
			if (null != setIconCommand && setIconCommand.canExecute()) {
				((CompoundCommand) command).append(setIconCommand);
			}
		} else {
			EObject eObject = EMFHelper.getEObject(value);
			if (null != eObject) {
				command = getSetCommand(eObject);
			} else {
				command = getSetCommand(value);
			}
		}
		if (null != command && command.canExecute()) {
			domain.getCommandStack().execute(command);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object doGetValue() {
		String result = null;
		Object observed = getObserved();
		if (observed instanceof Configuration && null != ((Configuration) observed).getIcon()) {
			IconDescriptor icon = ((Configuration) observed).getIcon();
			// get the path of the icon as a string
			result = Activator.getPath(icon.getPluginID(), icon.getIconPath());
		}
		return null != result ? result : Messages.IconDescriptorObservableValue_Undefined;
	}

	/**
	 * update the icons of ui element types.
	 * 
	 * @param descriptor
	 *            the icon descriptor
	 * @return the command to set the icon descriptors
	 */
	private Command getSetElementTypeIconsCommand(final IconDescriptor descriptor) {
		CompoundCommand command = new CompoundCommand();
		ElementTypeSetConfiguration uiElementTypeModel = getUIElementTypeModel();
		if (null != uiElementTypeModel) {
			if (eObject instanceof ToolConfiguration) {
				for (ElementDescriptor elementDescriptor : ((ToolConfiguration) eObject).getElementDescriptors()) {
					ElementTypeConfiguration elementType = elementDescriptor.getElementType();
					if (null != elementType) {
						IconEntry icon = ElementTypesConfigurationsFactory.eINSTANCE.createIconEntry();
						// paletteModel
						icon.setBundleId(descriptor.getPluginID());
						icon.setIconPath(descriptor.getIconPath());
						command.append(new SetCommand(domain, elementType, ElementTypesConfigurationsPackage.eINSTANCE.getElementTypeConfiguration_IconEntry(), icon));
					}
				}
			}
		}
		return command;
	}


	/**
	 * Gets the UI ElementType model.
	 * 
	 * @return get {@link ElementTypeSetConfiguration} model for the UI part.
	 */
	private ElementTypeSetConfiguration getUIElementTypeModel() {

		ElementTypeSetConfiguration elementTypeUI = null;
		Object object = domain.getResourceSet().getLoadOptions().get(PaletteRessourcesConstants.ELEMENTTYPE_UI_RESSOURCE_IDENTIFIER);
		if (object instanceof Resource) {
			Resource resource = (Resource) object;

			EObject eObject = resource.getContents().get(0);
			if (eObject instanceof ElementTypeSetConfiguration) {
				elementTypeUI = (ElementTypeSetConfiguration) eObject;
			}
		}
		return elementTypeUI;
	}


}
