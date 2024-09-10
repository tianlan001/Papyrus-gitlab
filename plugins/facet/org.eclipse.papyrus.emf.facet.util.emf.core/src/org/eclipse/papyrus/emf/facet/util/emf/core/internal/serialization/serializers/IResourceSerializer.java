/*******************************************************************************
 * Copyright (c) 2011 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.util.emf.core.internal.serialization.serializers;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.papyrus.emf.facet.util.emf.core.serialization.ISerializer;

public class IResourceSerializer implements ISerializer<IResource> {

	public IResourceSerializer() {
		//
	}

	public Class<IResource> getType() {
		return IResource.class;
	}

	public String serialize(final IResource value) {
		return value.getFullPath().toPortableString();
	}

	public IResource deserialize(final String serializedValue) {
		return ResourcesPlugin.getWorkspace().getRoot().findMember(serializedValue);
	}
}
