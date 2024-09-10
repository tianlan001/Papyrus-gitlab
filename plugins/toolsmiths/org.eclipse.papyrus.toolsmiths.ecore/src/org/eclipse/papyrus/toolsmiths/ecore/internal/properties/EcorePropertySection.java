/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.ecore.internal.properties;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor.Registry;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection;

/**
 * This class provides the section for the elements coming from the Ecore metamodel (and filtered by {@link EcorePropertySectionFilter}
 */
public class EcorePropertySection extends AdvancedPropertySection {

	/**
	 *
	 * Constructor.
	 *
	 */
	public EcorePropertySection() {
		super();
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection#setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 *
	 * @param part
	 * @param selection
	 */
	@Override
	public final void setInput(final IWorkbenchPart part, final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final Object object = ((IStructuredSelection) selection).getFirstElement();

			// we get the real selected element
			final EObject selectedElement = EMFHelper.getEObject(object);
			if (null != selectedElement) {
				final Registry registry = ComposedAdapterFactory.Descriptor.Registry.INSTANCE;
				final Collection<Object> types = new ArrayList<>();
				types.add(selectedElement.eClass().getEPackage());
				types.add(IItemPropertySource.class);


				// we look for the adapter factory registered for the metamodel of the object we want to edit
				final Descriptor descriptor = registry.getDescriptor(types);
				final AdapterFactory adapterFactory = descriptor.createAdapterFactory();
				this.page.setPropertySourceProvider(new AdapterFactoryContentProvider(adapterFactory) {

					/**
					 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#createPropertySource(java.lang.Object, org.eclipse.emf.edit.provider.IItemPropertySource)
					 *
					 * @param object
					 * @param itemPropertySource
					 * @return
					 */
					@Override
					protected IPropertySource createPropertySource(final Object object, final IItemPropertySource itemPropertySource) {
						return EcorePropertySection.this.createPropertySource(object, itemPropertySource);
					}
				});
				super.setInput(part, new StructuredSelection(selectedElement));
				return;
			}
		}

		this.page.setPropertySourceProvider(null);
		super.setInput(part, selection);
	}


	/**
	 *
	 * @param object
	 *            an objectS
	 * @param itemPropertySource
	 *            the item property source
	 * @return
	 *         the created {@link IPropertySource}
	 */
	public IPropertySource createPropertySource(final Object object, final IItemPropertySource itemPropertySource) {
		return new PropertySource(object, itemPropertySource);
	}



}
