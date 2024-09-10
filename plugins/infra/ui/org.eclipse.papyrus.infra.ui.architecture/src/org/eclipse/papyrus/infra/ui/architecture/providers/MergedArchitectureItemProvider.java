/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.providers;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.provider.DelegatingWrapperItemProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedADElement;
import org.eclipse.papyrus.infra.core.architecture.provider.ArchitectureEditPlugin;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;

/**
 * An item provider for the <em>Merged Architecture Description</em> façade API.
 */
public abstract class MergedArchitectureItemProvider extends DelegatingWrapperItemProvider implements Adapter, IItemStyledLabelProvider {

	public MergedArchitectureItemProvider(AdapterFactory adapterFactory, MergedADElement owner) {
		super(owner.getAdapter(ADElement.class), owner, null, Notification.NO_INDEX, adapterFactory);
	}

	protected ADElement getADElement() {
		return (ADElement) getValue();
	}

	protected MergedADElement getMergedElement() {
		return (MergedADElement) getOwner();
	}

	@Override
	public String getText(Object object) {
		return ((MergedADElement) object).getName();
	}

	@Override
	public Object getStyledText(Object object) {
		return new StyledString(getText(object));
	}

	@Override
	public Object getImage(Object object) {
		URI result = null;

		@SuppressWarnings("deprecation")
		ADElement imageObject = ((MergedADElement) object).getImageObject();
		if (imageObject != null && imageObject.getIcon() != null) {
			result = URI.createURI(imageObject.getIcon());

			// Normalize for workspace mappings
			ResourceSet rset = EMFHelper.getResourceSet(imageObject);
			if (rset != null) {
				result = rset.getURIConverter().normalize(result);
			}

			// The Eclipse platform will try to find a 2x image on hi-DPI displays, which will
			// fail because it only supports platform:/plugin URLs (not platform:/resource). So,
			// further resolve to a file on its behalf
			result = Optional.ofNullable(result).filter(URI::isPlatformResource)
					.map(uri -> ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true))).getLocationURI())
					.map(java.net.URI::toString)
					.map(URI::createURI)
					.orElse(result);
		}

		return result;
	}

	protected Stream<?> wrap(Object owner, Collection<?> objects) {
		return objects.stream().map(value -> createWrapper(value, owner, getRootAdapterFactory()));
	}

	protected void wrapInto(Object owner, Collection<?> objects, Collection<Object> wrappers) {
		wrap(owner, objects).forEach(wrappers::add);
	}

	protected Predicate<? super IItemPropertyDescriptor> propertyDescriptorFilter(Object object) {
		return __ -> false;
	}

	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (propertyDescriptors == null) {
			propertyDescriptors = super.getPropertyDescriptors(object);

			// Remove unwanted properties
			propertyDescriptors.removeIf(propertyDescriptorFilter(object));
		}

		return propertyDescriptors;
	}

	protected String getString(String key) {
		return ArchitectureEditPlugin.INSTANCE.getString(key);
	}

	//
	// Adapter protocol
	//

	@Override
	public boolean isAdapterForType(Object type) {
		return type == adapterFactory || (type instanceof Class<?> && ((Class<?>) type).isInstance(this));
	}

	@Override
	public Notifier getTarget() {
		return getMergedElement();
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// Pass
	}

}
