/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.textedit.properties.internal.emf;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor.Registry;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection;

/**
 * TextDocument Property View section.
 */
public class TextDocumentSection extends AdvancedPropertySection {

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection#setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 *
	 * @param part
	 * @param selection
	 */
	@Override
	public final void setInput(final IWorkbenchPart part, ISelection selection) {
		Object first = null;
		if (selection instanceof IStructuredSelection) {
			first = EMFHelper.getEObject(((IStructuredSelection) selection).getFirstElement());
		}
		if (first instanceof EObject) {
			// update the selection with the resolved element
			selection = new StructuredSelection(first);

			final EObject selectedElement = (EObject) first;
			final Registry registry = ComposedAdapterFactory.Descriptor.Registry.INSTANCE;
			final Collection<Object> types = new ArrayList<>(2);
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
					return TextDocumentSection.this.createPropertySource(object, itemPropertySource);
				}
			});

		} else {
			this.page.setPropertySourceProvider(null);
		}

		super.setInput(part, selection);
	}


	/**
	 *
	 * @param object
	 *            an object
	 * @param itemPropertySource
	 *            the item property source
	 * @return
	 *         the created {@link IPropertySource}
	 */
	private IPropertySource createPropertySource(Object object, IItemPropertySource itemPropertySource) {
		return new TextDocumentPropertySource(object, itemPropertySource);
	}
}