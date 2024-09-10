/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) - Initial API and implementation
 *   Vincent LORENZO (CEA LIST) - Bug 535070
 *****************************************************************************/

package org.eclipse.papyrus.emf.resources;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * @author Vincent LORENZO
 *         Abstract XMI Resource to extends for EMF model Resource. The goal is to provides the same options (save and load) for the Papyrus EMF models:
 *         <ul>
 *         <li>use ID, instead of position to reference element</li>
 *         <li>common formating rules (ignoring the options given by the editors)</li>
 *         <li>encoding : UTF-8</li>
 *         <li>default values are saved (allow to prevent troubles when default value changed)</li>
 *         <li>...</li>
 *         </ul>
 */
public abstract class AbstractEMFResourceWithUUID extends AbstractEMFResource {

	/**
	 * 
	 * Constructor.
	 *
	 * @param uri
	 */
	public AbstractEMFResourceWithUUID(final URI uri) {
		super(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#useUUIDs()
	 *
	 * @return
	 */

	@Override
	protected boolean useUUIDs() {
		return true;
	}

	/**
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#assignIDsWhileLoading()
	 *
	 * @return
	 */
	@Override
	protected boolean assignIDsWhileLoading() {
		// bug 535070
		// if the resource is read-only, we don't assign XMI_ID (to avoid that writeable resource saves assigned XMI_ID of the current read-only resource which won't be saved.
		// doing that, we avoid trouble when we reopen the models
		return !isReadOnly();// overridden to avoid to assign XMI_ID to element of previous configuration which are stilling use position to be saved
	}

	/**
	 * 
	 * @return
	 * 		<code>true</code> is the resource is read-only
	 */
	private boolean isReadOnly() {
		final Map<?, ?> options = Collections.singletonMap(URIConverter.OPTION_REQUESTED_ATTRIBUTES, Collections.singleton(URIConverter.ATTRIBUTE_READ_ONLY));
		final Map<String, ?> attributes = (getResourceSet()).getURIConverter().getAttributes(getURI(), options);
		return Boolean.TRUE.equals(attributes.get(URIConverter.ATTRIBUTE_READ_ONLY));
	}
}
