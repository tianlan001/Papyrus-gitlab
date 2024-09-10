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

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DragAndDropCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Configuration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.DrawerConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.IconDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteconfigurationPackage;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ToolConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.utils.CreatePaletteItemUtil;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.utils.DragAndDropCompoundCommand;

/**
 * Custom item provider for drawer configuration.
 */
public class CustomDrawerConfigurationItemProvider extends DrawerConfigurationItemProvider {

	/**
	 * The constructor.
	 */
	public CustomDrawerConfigurationItemProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Command createDragAndDropCommand(final EditingDomain domain, final Object owner, final float location, final int operations, final int operation, final Collection<?> collection) {
		Command command = null;
		Object entry = collection.iterator().next();
		// Move element or create new one
		if (entry instanceof ToolEntry) {
			command = new DragAndDropCompoundCommand();

			// Create the new entry
			ToolConfiguration newTool = CreatePaletteItemUtil.createToolConfiguration((ToolEntry) entry, domain.getResourceSet());

			// Create the drag and drop command with the new tool to insert
			DragAndDropCommand dadCommand = new DragAndDropCommand(domain, owner, location, operations, operation, Collections.singleton(newTool)) {
				/**
				 * {@inheritDoc}
				 */
				@Override
				protected boolean isCrossDomain() {
					return false;
				}

				/**
				 * {@inheritDoc}
				 */
				@Override
				public boolean validate(
						final Object owner,
						final float location,
						final int operations,
						final int operation,
						final Collection<?> collection) {
					return super.validate(owner, location, operations, operation, this.collection);
				}

				/**
				 * {@inheritDoc}
				 * Only perform the drop.
				 */
				@Override
				protected boolean prepareDropMoveOn() {
					dropCommand = AddCommand.create(domain, owner, null, collection);
					if (analyzeForNonContainment(dropCommand)) {
						dropCommand.dispose();
						dropCommand = UnexecutableCommand.INSTANCE;
						dragCommand = IdentityCommand.INSTANCE;
					}

					boolean result = dropCommand.canExecute();
					return result;
				}
			};
			Command commandElemenType = CreatePaletteItemUtil.createElementTypesElement(domain, (ToolEntry) entry, newTool);

			((CompoundCommand) command).append(dadCommand);
			((CompoundCommand) command).append(commandElemenType);
		} else {
			command = new DragAndDropCommand(domain, owner, location, operations, operation, collection);
		}

		return null != command && command.canExecute() ? command : UnexecutableCommand.INSTANCE;
	}

	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		FilteredElementUtil.collectNewFilters(newChildDescriptors, object);
	}


	/**
	 * Override to not show icon descriptor. <br>
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public Collection<?> getChildren(final Object object) {
		DrawerConfiguration configuration = (DrawerConfiguration) object;
		List<EObject> children = new ArrayList<>();

		if (configuration.getFilter() != null) {
			children.add(configuration.getFilter());
		}
		children.addAll(configuration.getOwnedConfigurations());

		return children;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Command createMoveCommand(final EditingDomain domain, final EObject owner, final EStructuralFeature feature, final Object value, int index) {
		if (owner instanceof Configuration) {

			// Adapt the index due to icon set a invisible
			IconDescriptor icon = ((Configuration) owner).getIcon();
			if (null != icon) {
				// if there is a icon increment the index
				index++;
			}
		}
		return super.createMoveCommand(domain, owner, feature, value, index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getImage(final Object object) {
		Object result = null;

		if (object instanceof Configuration) {
			result = overlayImage(object, CreatePaletteItemUtil.iconDescriptorToImage(((Configuration) object).getIcon()));
		}

		return result != null ? result : super.getImage(object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyChanged(final Notification notification) {
		super.notifyChanged(notification);

		switch (notification.getFeatureID(DrawerConfiguration.class)) {
		// Refresh label to refresh the icon
		case PaletteconfigurationPackage.DRAWER_CONFIGURATION__ICON:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(final Object object) {
		return ((Configuration) object).getLabel();
	}

}
