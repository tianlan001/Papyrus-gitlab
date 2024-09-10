/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.presentation;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AdvancedPropertySection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * Dynamic section for all implementations of {@link Filter}
 */
public class FilterSection extends AdvancedPropertySection implements IPropertySourceProvider {

	@Override
	public IPropertySource getPropertySource(Object object) {
		if (object instanceof IPropertySource) {
			return (IPropertySource) object;
		}
		AdapterFactory af = getAdapterFactory(object);
		if (af != null) {
			IItemPropertySource ips = (IItemPropertySource) af.adapt(object, IItemPropertySource.class);
			if (ips != null) {
				return new PropertySource(object, ips);
			}
		}
		if (object instanceof IAdaptable) {
			return ((IAdaptable) object).getAdapter(IPropertySource.class);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.eclipse.gmf.runtime.diagram.ui.properties.sections.
	 * AdvancedPropertySection# getPropertySourceProvider()
	 */
	@Override
	protected IPropertySourceProvider getPropertySourceProvider() {
		return this;
	}

	/**
	 * Modify/unwrap selection.
	 *
	 */
	protected Object transformSelection(Object selected) {
		if (selected == null) {
			return null;
		}
		if (selected instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) selected;

			Object adapter = adaptable.getAdapter(IPropertySource.class);
			if (adapter != null) {
				// This is a terminal transformation
				return adapter;
			}
			adapter = adaptable.getAdapter(EObject.class);
			if (adapter != null) {
				// This is a terminal transformation
				return adapter;
			}
		}

		EObject elem = EMFHelper.getEObject(selected);
		if (elem != null) {
			return elem;
		}

		return selected;
	}

	/**
	 * Get the adapterFactory of the given object
	 *
	 * @param Object
	 * @return the adapter factory
	 */
	protected AdapterFactory getAdapterFactory(Object object) {
		if (getEditingDomain() instanceof AdapterFactoryEditingDomain) {
			return ((AdapterFactoryEditingDomain) getEditingDomain()).getAdapterFactory();
		}
		EditingDomain domain = EMFHelper.resolveEditingDomain(object);
		if (domain instanceof AdapterFactoryEditingDomain) {
			return ((AdapterFactoryEditingDomain) domain).getAdapterFactory();
		}
		return null;
	}

}
