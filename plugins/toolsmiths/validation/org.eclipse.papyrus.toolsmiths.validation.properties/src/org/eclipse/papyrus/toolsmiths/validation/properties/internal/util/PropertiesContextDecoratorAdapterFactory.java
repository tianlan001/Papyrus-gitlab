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

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.util;

import java.util.function.Function;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.DecoratorAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemProviderDecorator;
import org.eclipse.emf.edit.provider.ItemProviderDecorator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.contexts.UnknownProperty;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.messages.Messages;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Decorate the item providers for the <em>Properties Context</em> model to make object labels
 * better suited for problem messages.
 */
public class PropertiesContextDecoratorAdapterFactory extends DecoratorAdapterFactory {

	public PropertiesContextDecoratorAdapterFactory(AdapterFactory decoratedAdapterFactory) {
		super(decoratedAdapterFactory);
	}

	@Override
	protected IItemProviderDecorator createItemProviderDecorator(Object target, Object type) {
		return new ContextItemProviderDecorator(getDecoratedAdapterFactory());
	}

	//
	// Nested types
	//

	private static class ContextItemProviderDecorator extends ItemProviderDecorator implements Adapter, IItemLabelProvider {

		ContextItemProviderDecorator(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}

		@Override
		public String getText(Object object) {
			String result = null;
			if (object instanceof DataContextElement || object instanceof Property) {
				result = getQualifiedName(object);
			}

			if (result == null) {
				result = super.getText(object);
			}

			if (object instanceof UnknownProperty) {
				// Humansneed a bit more context to locate these objects that are contained in XWT files
				result = decorateUnknownPropertyText((UnknownProperty) object, result);
			}

			return result;
		}

		String getQualifiedName(Object contextElement) {
			StringBuilder result = new StringBuilder();
			qualifiedName(contextElement, result);

			if (result != null) {
				EObject object = (EObject) contextElement;
				String key = String.format("_UI_%s_type", object.eClass().getName()); //$NON-NLS-1$
				String type = ((ResourceLocator) getDecoratedItemProvider()).getString(key);
				result.insert(0, " "); //$NON-NLS-1$
				result.insert(0, type);
			}

			return result == null ? null : result.toString();
		}

		private StringBuilder qualifiedName(Object contextElement, StringBuilder buf) {
			if (contextElement instanceof Property) {
				Property property = (Property) contextElement;
				return qualifiedName(property, buf, Property::getContextElement, Property::getName);
			} else if (contextElement instanceof DataContextElement) {
				DataContextElement element = (DataContextElement) contextElement;
				return qualifiedName(element, buf, DataContextElement::getPackage, DataContextElement::getName);
			}

			return null;
		}

		private <T> StringBuilder qualifiedName(T contextElement, StringBuilder buf, Function<? super T, ?> ownerFunction, Function<? super T, String> nameFunction) {
			Object owner = ownerFunction.apply(contextElement);
			if (owner != null) {
				qualifiedName(owner, buf);
			}
			if (buf == null) {
				// There is no qualified name
				return null;
			}

			if (buf.length() > 0) {
				buf.append(NamedElement.SEPARATOR);
			}
			String name = nameFunction.apply(contextElement);
			if (name == null) {
				// There is no qualified name
				return null;
			}

			buf.append(name);
			return buf;
		}

		String decorateUnknownPropertyText(UnknownProperty unknownProperty, String text) {
			String result = text;

			Resource resource = unknownProperty.eResource();
			URI uri = resource == null ? null : resource.getURI();
			if (uri != null) {
				result = NLS.bind(Messages.PropertiesContextDecoratorAdapterFactory_0, text, uri.lastSegment());
			}

			return result;
		}

		@Override
		public Notifier getTarget() {
			// I don't track any target
			return null;
		}

		@Override
		public void setTarget(Notifier newTarget) {
			// I don't track any target
		}

	}

}
