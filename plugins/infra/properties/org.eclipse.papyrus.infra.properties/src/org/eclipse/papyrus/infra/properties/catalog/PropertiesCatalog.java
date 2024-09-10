/*****************************************************************************
 * Copyright (c) 2011, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 516180
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.catalog;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * A catalog for property view URIs
 *
 * Handles URIs with the ppe:/ scheme
 *
 * @author Camille Letavernier
 */
public class PropertiesCatalog implements Factory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Resource createResource(URI sourceURI) {
		Resource result = null;

		PropertiesURIHandler handler = new PropertiesURIHandler();

		// Get the real URI
		URI realURI = handler.getConvertedURI(sourceURI);

		if (!realURI.equals(sourceURI)) {
			// And get the best resource for this URI, which now is not ppe scheme.
			// As we do not have a resource-set context, we have to use the registry.
			// But, whatever that gives us is better than imposing the fits-all
			// PropertiesXMIResource
			Resource.Factory delegate = Resource.Factory.Registry.INSTANCE.getFactory(realURI);
			if (delegate != null) {
				result = delegate.createResource(realURI);
				result.setURI(sourceURI);

				// When it gets its resource-set context, install the URI handler
				result.eAdapters().add(new URIHandlerInstaller(handler));
			}
		}

		if (result == null) {
			// This will handle the URIs internally, albeit not optimally
			result = new PropertiesXMIResource(sourceURI);
		}

		return result;
	}

	/**
	 * An XMIResource with a specific URI Converter, for handling
	 * the {@code ppe:} scheme. This is a work-around for cases
	 * where it is not possible to use the adapter mechanism to
	 * install the URI handler on the resource set.
	 *
	 * @author Camille Letavernier
	 */
	// Non-xmi resources which are relative to a XMI resource cannot be handled
	// Typically, xwt files cannot be handled by an XMIResource
	// Problem : local calls to getURIConverter will skip the encapsulation...
	public class PropertiesXMIResource extends XMIResourceImpl {

		/**
		 *
		 * Constructor.
		 *
		 * @param sourceURI
		 *            The URI to associate to this resource
		 */
		public PropertiesXMIResource(URI sourceURI) {
			super(sourceURI);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public URIConverter getURIConverter() {
			return new ExtensibleURIConverterImpl() {

				@Override
				public org.eclipse.emf.ecore.resource.URIHandler getURIHandler(URI uri) {
					return new PropertiesURIHandler();
				}
			};
		}
	}

	/**
	 * An adapter for a {@link Resource} that installs an {@link URIHandler}
	 * in the {@link URIConverter} of the {@link ResourceSet} that eventually
	 * the resource becomes attached to.
	 */
	private static class URIHandlerInstaller extends AdapterImpl {
		private final URIHandler handler;

		URIHandlerInstaller(URIHandler handler) {
			super();

			this.handler = handler;
		}

		@Override
		public void notifyChanged(Notification msg) {
			if (msg.getNotifier() instanceof Resource) {
				if (msg.getFeatureID(Resource.class) == Resource.RESOURCE__RESOURCE_SET) {
					if (msg.getNewValue() instanceof ResourceSet) {

						installURIHandler((ResourceSet) msg.getNewValue());
						((Resource) msg.getNotifier()).eAdapters().remove(this);
					}
				}
			}
		}

		void installURIHandler(ResourceSet resourceSet) {
			List<URIHandler> handlers = resourceSet.getURIConverter().getURIHandlers();
			if (!handlers.stream().anyMatch(PropertiesURIHandler.class::isInstance)) {
				// Needs to go ahead of the generic URIHandlerImpl
				handlers.add(Math.max(0, handlers.size() - 1), handler);
			}
		}
	}
}
